using AppStore.Common;
using AppStore.Models;
using AppStore.Templates;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.ApplicationModel.DataTransfer;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.UI.Core;
using Windows.UI.Notifications;
using Windows.UI.Popups;
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
    public sealed partial class AppDetailsPage : Page
    {
        bool selectionGridScreenshots = false;
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        Apps app;
        bool appInstalled = false;
        public AppDetailsPage()
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
        bool txtShowMore = false;
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
            app = AppInstance.app;
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            if (localSettings.Values.ContainsKey(app.Name))
            appInstalled=(bool)(localSettings.Values[app.Name]);
            if (appInstalled) btnAppInstall.Content = "LAUNCH";
            else btnAppInstall.Content = "INSTALL";

            webAppReviews.NavigateToString(getHtmlComment());
           

            txtAppName.Text = app.Name;
            txtAppAuthor.Text = app.Author;
            if (app.Description.Length > 50)
            {
                txtShowMore = false;
                txtAppDetails.Text = app.Description.Substring(0, 50) + "...";
                detailsShowMore.Content = "more";
            }
            else
            { txtAppDetails.Text = app.Description;
            detailsShowMore.Visibility = Visibility.Collapsed;
            }
            txtAddInfo1.Text=("Author: \nAuthor Email: \nCategory: \nType: ");
            txtAddInfo2.Text=(app.Author + "\n" + app.AuthorEmail + "\n" + app.Category + "\n" + app.Type);
            
            List<Image> images = new List<Image>();
            foreach(string screenshot in app.Screenshots)
            {
                Image image = new Image();
                image.Source = new BitmapImage(new Uri(screenshot));
                images.Add(image);
            }
            FullScreenshots.ItemsSource = images;
            imgAppIcon.Source = new BitmapImage(new Uri(app.AppIcon));
            GridScreenshots.ItemsSource = app.Screenshots;
            this.navigationHelper.OnNavigatedTo(e);
        }
        private string getHtmlComment()
        {
            return "<div id='disqus_thread'></div>"
                    + "<script type='text/javascript'>"
                    + "var disqus_identifier = '" + app.Name + "_" + app.Author + "';"
                    + "var disqus_title = '" + app.Name + "';"
                    + "var disqus_url='http://www.buildmlearn.org/appstore/" + app.Name + "_" + app.Author + "';"
                    + "var disqus_shortname = 'buildmlearn';"
                    + " (function() { var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;"
                    + "dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';"
                    + "(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq); })();"
                    + "</script>";
        }
        private void GridScreenshots_ContainerContentChanging(ListViewBase sender, ContainerContentChangingEventArgs args)
        {
            args.Handled = true;
            if (args.Phase != 0)
            {
                throw new Exception("Not in phase 0.");
            }
            StackPanel templateRoot = (StackPanel)args.ItemContainer.ContentTemplateRoot;
            Image appIcon = (Image)templateRoot.FindName("appScreenshots");
            appIcon.Opacity = 0;
            args.RegisterUpdateCallback(ShowImages);
        }
        private void ShowImages(ListViewBase sender, ContainerContentChangingEventArgs args)
        {
            if (args.Phase != 1)
            {
                throw new Exception("Not in phase 1.");
            }
            SelectorItem itemContainer = (SelectorItem)args.ItemContainer;
            StackPanel templateRoot = (StackPanel)itemContainer.ContentTemplateRoot;
            Image appImage = (Image)templateRoot.FindName("appScreenshots");
            appImage.Source = new BitmapImage(new Uri(args.Item.ToString()));
            appImage.Opacity = 1;
        }
        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion

        private void detailsShowMore_Click(object sender, RoutedEventArgs e)
        {
            if (txtShowMore)
            {
                txtShowMore = false;
                txtAppDetails.Text=(app.Description.Substring(0, 50))+"...";
                detailsShowMore.Content="more";
            }
            else
            {
                txtShowMore = true;
                txtAppDetails.Text = (app.Description);
                detailsShowMore.Content = "less";
            }
        }
        
        private void GridScreenshots_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridScreenshots) return;
            FullScreenshots.SelectedIndex = GridScreenshots.SelectedIndex;
            FullScreen.Visibility = Visibility.Visible;
            LayoutRoot.Visibility = Visibility.Collapsed;
            selectionGridScreenshots = true;GridScreenshots.SelectedIndex = -1; selectionGridScreenshots = false;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            FullScreen.Visibility = Visibility.Collapsed;
            LayoutRoot.Visibility = Visibility.Visible;
        }

        void HardwareButtons_BackPressed(object sender, Windows.Phone.UI.Input.BackPressedEventArgs e)
        {
            if (FullScreen.Visibility == Visibility.Visible)
            {
                FullScreen.Visibility = Visibility.Collapsed;
                LayoutRoot.Visibility = Visibility.Visible;
                e.Handled = true;
            }
        }

        private void btnAppInstall_Click(object sender, RoutedEventArgs e)
        {
            if (appInstalled)
            {
                Frame.Navigate(typeof(StartPage));
            }
            else
            {
                var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
                localSettings.Values[app.Name] = true;
                AppList.getMyAppList().myappList.Add(app);
                appInstalled = true;
                var toastTemplate = ToastTemplateType.ToastImageAndText01;
                var toastXml = ToastNotificationManager.GetTemplateContent(toastTemplate);
                var toastTextElements = toastXml.GetElementsByTagName("text");
                toastTextElements[0].AppendChild(toastXml.CreateTextNode(app.Name+" is installed."));
                var toast = new ToastNotification(toastXml);
                ToastNotificationManager.CreateToastNotifier().Show(toast);
                btnAppInstall.Content = "LAUNCH";
            }
        }
        private void btnAppShare_Click(object sender, RoutedEventArgs e)
        {
            var datacontext = (e.OriginalSource as FrameworkElement).DataContext;
            DataTransferManager.ShowShareUI();
        }
        private void Search_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(SearchPage)); }
        private void Home_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MainPage)); }
        private async void Settings_Click(object sender, RoutedEventArgs e) { ContentDialog dlg = new ContentDialog(); dlg=new SettingsDialog(); await dlg.ShowAsync(); }
        private void MyApps_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MyAppsPage)); }
        private void Categories_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(CategoriesPage)); }
        private void About_Click(object sender, RoutedEventArgs e) { }
        private void Feedback_Click(object sender, RoutedEventArgs e) { AppCommon.ComposeEmail(); }

        private async void Browser_Click(object sender, RoutedEventArgs e) { await Windows.System.Launcher.LaunchUriAsync(appReviewsUri); }
        private async void HyperlinkButton_Click(object sender, RoutedEventArgs e)
        {
            await Windows.System.Launcher.LaunchUriAsync(appReviewsUri);
        }
        Uri appReviewsUri;

        private void webAppReviews_FrameDOMContentLoaded(WebView sender, WebViewDOMContentLoadedEventArgs args)
        {
            OpenInBrowser.IsEnabled = true;
            appReviewsUri = args.Uri;
        }
    }
}
