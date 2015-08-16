using AppStore.Common;
using AppStore.Models;
using System;
using System.Linq;
using Windows.ApplicationModel.DataTransfer;
using Windows.UI.Notifications;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore
{
    /// <summary>
    /// This activity deals with the apps shown when "more" button on the Home Page is pressed.
    /// </summary>
    public sealed partial class AppsPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        private bool selectionGridCategories = false, selectionGridApps = false;
        Apps appHolding;
    
        /// <summary>
        /// Public constructor to the Apps Page.
        /// </summary>
        public AppsPage()
        {
            this.InitializeComponent();

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

        /// <summary>
        /// The methods provided in this section are simply used to allow
        /// NavigationHelper to respond to the page's navigation methods.
        /// <para>
        /// Page specific logic should be placed in event handlers for the  
        /// <see cref="NavigationHelper.LoadState"/>
        /// and <see cref="NavigationHelper.SaveState"/>.
        /// The navigation parameter is available in the SaveState method 
        /// in addition to page state preserved during an earlier session.
        /// </para>
        /// </summary>
        /// <param name="e">Provides data for navigation methods and event
        /// handlers that cannot cancel the navigation request.</param>
        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion
       
        /// <summary>
        /// Deals with populating apps in the My-Apps section.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">ContainerContentChangingEventArgs args is a parameter called e that contains the event data, see the ContainerContentChangingEventArgs MSDN page for more information.</param>
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
      
        /// <summary>
        /// Deals with populating images(app icons) in the AppsPage.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">ContainerContentChangingEventArgs args is a parameter called e that contains the event data, see the ContainerContentChangingEventArgs MSDN page for more information.</param>
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
   
        /// <summary>
        /// Executed when the user taps/selects an app from the Apps Page. The app navigates to the Details page of the app.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">SelectionChangedEventArgs e an instance of SelectionChangedEventArgs including, in many cases, an object which inherits from SelectionChangedEventArgs. Contains additional information about the event, and sometimes provides ability for code handling the event to alter the event somehow.</param>
        private void GridFeaturedApps_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridApps) return;
            AppInstance.app = (Apps)GridFeaturedApps.SelectedItem;
            Frame.Navigate(typeof(AppDetailsPage));
            selectionGridApps = true;
            GridFeaturedApps.SelectedIndex = -1;
            selectionGridApps = false;
        }
    
        /// <summary>
        /// Executed when the Search Button is tapped from the Application bar. The app navigates to the Search Results page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Search_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(SearchPage)); }
   
        /// <summary>
        /// Executed when the Settings Button is tapped from the Application bar. The app navigates to the Settings page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private async void Settings_Click(object sender, RoutedEventArgs e) { ContentDialog dlg = new ContentDialog(); dlg = new SettingsDialog(); await dlg.ShowAsync(); }
  
        /// <summary>
        /// Executed when the My Apps Button is tapped from the Application bar. The app navigates to the My-Apps page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void MyApps_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MyAppsPage)); }
  
        /// <summary>
        /// Executed when the Categories Button is tapped from the Application bar. The app navigates to the Categories page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Categories_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(CategoriesPage)); }
 
        /// <summary>
        /// Executed when the About Button is tapped from the Application bar. The app navigates to the About page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void About_Click(object sender, RoutedEventArgs e) { }
   
        /// <summary>
        /// Executed when the app is long tapped. Context menu appears now.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
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
            //var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            FlyoutBase flyoutBase = FlyoutBase.GetAttachedFlyout(senderElement);
            flyoutBase.ShowAt(senderElement);
        }
    
        /// <summary>
        /// Executed when the Feedback Button is tapped from the Application bar. The app prepares the content and uses the default mail apps to give feedback.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Feedback_Click(object sender, RoutedEventArgs e) { AppCommon.ComposeEmail(); }
    
        /// <summary>
        /// Executed when the Install Button is tapped from the Context menu which appears when the app is long tapped. It installs the installed app.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
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
    
        /// <summary>
        /// Executed when the Share Button is tapped from the Application bar. The app prepares content form the app to be shared using default installed apps on the phone.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void ShareButton_Click(object sender, RoutedEventArgs e)
        {
            var datacontext = (e.OriginalSource as FrameworkElement).DataContext;
            DataTransferManager.ShowShareUI();
        }
    }
}
