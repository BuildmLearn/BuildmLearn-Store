using AppStore.Common;
using AppStore.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.ApplicationModel.DataTransfer;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.UI.Notifications;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class SearchPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        bool selectionGridApps = false;
        public SearchPage()
        {
            this.InitializeComponent();
            AppCommon.RegisterForShare();
            this.navigationHelper = new NavigationHelper(this);
            this.navigationHelper.LoadState += this.NavigationHelper_LoadState;
            this.navigationHelper.SaveState += this.NavigationHelper_SaveState;
        }

        /// <summary>
        /// Gets the <see cref="NavigationHelper"/> associated with this <see cref="Page"/>.
        /// </summary>
        public NavigationHelper NavigationHelper
        {
            get { return this.navigationHelper; }
        }

        /// <summary>
        /// Gets the view model for this <see cref="Page"/>.
        /// This can be changed to a strongly typed view model.
        /// </summary>
        public ObservableDictionary DefaultViewModel
        {
            get { return this.defaultViewModel; }
        }

        /// <summary>
        /// Populates the page with content passed during navigation.  Any saved state is also
        /// provided when recreating a page from a prior session.
        /// </summary>
        /// <param name="sender">
        /// The source of the event; typically <see cref="NavigationHelper"/>
        /// </param>
        /// <param name="e">Event data that provides both the navigation parameter passed to
        /// <see cref="Frame.Navigate(Type, Object)"/> when this page was initially requested and
        /// a dictionary of state preserved by this page during an earlier
        /// session.  The state will be null the first time a page is visited.</param>
        private void NavigationHelper_LoadState(object sender, LoadStateEventArgs e)
        {
        }

        /// <summary>
        /// Preserves state associated with this page in case the application is suspended or the
        /// page is discarded from the navigation cache.  Values must conform to the serialization
        /// requirements of <see cref="SuspensionManager.SessionState"/>.
        /// </summary>
        /// <param name="sender">The source of the event; typically <see cref="NavigationHelper"/></param>
        /// <param name="e">Event data that provides an empty dictionary to be populated with
        /// serializable state.</param>
        private void NavigationHelper_SaveState(object sender, SaveStateEventArgs e)
        {
        }

        #region NavigationHelper registration

        /// <summary>
        /// The methods provided in this section are simply used to allow
        /// NavigationHelper to respond to the page's navigation methods.
        /// <para>
        /// Page specific logic should be placed in event handlers for the  
        /// <see cref="NavigationHelper.LoadState"/>
        /// and <see cref="NavigationHelper.SaveState"/>.
        /// The navigation parameter is available in the LoadState method 
        /// in addition to page state preserved during an earlier session.
        /// </para>
        /// </summary>
        /// <param name="e">Provides data for navigation methods and event
        /// handlers that cannot cancel the navigation request.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            if (AppList.getMyAppList().myappList.Count > 0)
                btnMyApps.Visibility = Visibility.Visible;
            else btnMyApps.Visibility = Visibility.Collapsed;
            GridFeaturedApps.ItemsSource = AppList.getAppList().appList;
            this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
            foreach(var frames in Frame.BackStack)
            {
                if(frames.SourcePageType.FullName.Equals("AppStore.SearchPage"))
                {
                    Frame.BackStack.Remove(frames);break;
                }
            }
        }

        #endregion
        private void GridFeaturedApps_ContentChanging(ListViewBase sender, ContainerContentChangingEventArgs args)
        {
            args.Handled = true;
            if (args.Phase != 0)
            {
                throw new Exception("Not in phase 0.");
            }
            Apps app = (Apps)args.Item;
            StackPanel templateRoot = (StackPanel)args.ItemContainer.ContentTemplateRoot;
            TextBlock appName = (TextBlock)templateRoot.FindName("appName");
            TextBlock appAuthor = (TextBlock)templateRoot.FindName("appAuthor");
            Image appIcon = (Image)templateRoot.FindName("appIcon");
            appName.Text = app.Name;
            appAuthor.Text = app.Author;
            appIcon.Source = new BitmapImage(new Uri("ms-appx:///Assets/notavailable.png"));
            args.RegisterUpdateCallback(ShowImage);
        }
        private void ShowImage(ListViewBase sender, ContainerContentChangingEventArgs args)
        {
            if (args.Phase != 1)
            {
                throw new Exception("Not in phase 1.");
            }
            Apps app = (Apps)args.Item;
            StackPanel templateRoot = (StackPanel)args.ItemContainer.ContentTemplateRoot;
            Image appIcon = (Image)templateRoot.FindName("appIcon");
            appIcon.Source = new BitmapImage(new Uri(app.AppIcon));
        }
        private void GridFeaturedApps_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridApps) return;
            AppInstance.app = (Apps)GridFeaturedApps.SelectedItem;
            Frame.Navigate(typeof(AppDetailsPage));
            selectionGridApps = true;
            GridFeaturedApps.SelectedIndex = -1;
            selectionGridApps = false;
        }
        private void Home_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MainPage)); }
        private void Settings_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(Settings)); }
        private void MyApps_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MyAppsPage)); }
        private void Categories_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(CategoriesPage)); }
        private void About_Click(object sender, RoutedEventArgs e) { }
        Apps appHolding;
        private void StackPanel_Holding(object sender, HoldingRoutedEventArgs e)
        {
            FrameworkElement senderElement = sender as FrameworkElement;
            StackPanel stackPanel = sender as StackPanel;
            TextBlock txt = (TextBlock)stackPanel.Children.ElementAt(1);
            foreach (Apps app in AppList.getAppList().appList)
            {
                if (app.Name.Equals(txt.Text))
                {
                    appHolding = app; break;
                }
            }
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            FlyoutBase flyoutBase = FlyoutBase.GetAttachedFlyout(senderElement);
            flyoutBase.ShowAt(senderElement);
        }
        private void Feedback_Click(object sender, RoutedEventArgs e) { AppCommon.ComposeEmail(); }
        private void InstallButton_Click(object sender, RoutedEventArgs e)
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            if ((bool)localSettings.Values[appHolding.Name]) return;
            localSettings.Values[appHolding.Name] = true;
            AppList.getMyAppList().myappList.Add(appHolding);
            btnMyApps.Visibility = Visibility.Visible;
            var toastTemplate = ToastTemplateType.ToastImageAndText01;
            var toastXml = ToastNotificationManager.GetTemplateContent(toastTemplate);
            var toastTextElements = toastXml.GetElementsByTagName("text");
            toastTextElements[0].AppendChild(toastXml.CreateTextNode(appHolding.Name + " is installed."));
            var toast = new ToastNotification(toastXml);
            ToastNotificationManager.CreateToastNotifier().Show(toast);
        }
        private void ShareButton_Click(object sender, RoutedEventArgs e)
        {
            var datacontext = (e.OriginalSource as FrameworkElement).DataContext;
            DataTransferManager.ShowShareUI();
        }

        private void Search_TextChanged(object sender, TextChangedEventArgs e)
        {
            List<Apps> list = new List<Apps>();
            foreach(Apps app in AppList.getAppList().appList)
            {
                if (app.Name.Contains(Search.Text)) { list.Add(app); }
            }
            GridFeaturedApps.ItemsSource = list;
        }
    }
}
