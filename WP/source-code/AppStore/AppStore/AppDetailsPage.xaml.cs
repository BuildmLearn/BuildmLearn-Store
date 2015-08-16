using AppStore.Common;
using AppStore.Models;
using AppStore.Templates;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.DataTransfer;
using Windows.UI.Notifications;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;


// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore
{
    /// <summary>
    /// This class deals with the App-Details page, which is shown when the user taps on any app cards displayed on various pages.
    /// </summary>
    public sealed partial class AppDetailsPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        bool selectionGridScreenshots = false, appInstalled = false, txtShowMore = false;
        Uri appReviewsUri;
        Apps app;

        /// <summary>
        /// Public Constructor to the Details Page.
        /// </summary>
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
        /// Builds the html content which has to loaded into the WebView for Reviews Section.
        /// </summary>
        /// <returns>Returns the HTML content to be loaded into the WebView of the Reviews Section</returns>
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
       
        /// <summary>
        /// Deals with populating images(screenshots) in the App-Details Page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">ContainerContentChangingEventArgs args is a parameter called e that contains the event data, see the ContainerContentChangingEventArgs MSDN page for more information.</param>
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

        /// <summary>
        /// Executed when the more button is tapped in the details section. It toggles between showing complete and less details of the app.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
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
      
        /// <summary>
        /// Executed when the user taps/selects a screenshot from the App-Details Page. The screenshots zoom in to the fullscreen view.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">SelectionChangedEventArgs e an instance of SelectionChangedEventArgs including, in many cases, an object which inherits from SelectionChangedEventArgs. Contains additional information about the event, and sometimes provides ability for code handling the event to alter the event somehow.</param>
        private void GridScreenshots_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridScreenshots) return;
            FullScreenshots.SelectedIndex = GridScreenshots.SelectedIndex;
            FullScreen.Visibility = Visibility.Visible;
            LayoutRoot.Visibility = Visibility.Collapsed;
            selectionGridScreenshots = true;GridScreenshots.SelectedIndex = -1; selectionGridScreenshots = false;
        }
       
        /// <summary>
        /// Executed when the Close Button is tapped in the full screen view of the screenshots. It closes the full-screen view.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            FullScreen.Visibility = Visibility.Collapsed;
            LayoutRoot.Visibility = Visibility.Visible;
        }
       
        /*void HardwareButtons_BackPressed(object sender, Windows.Phone.UI.Input.BackPressedEventArgs e)
        {
            if (FullScreen.Visibility == Visibility.Visible)
            {
                FullScreen.Visibility = Visibility.Collapsed;
                LayoutRoot.Visibility = Visibility.Visible;
                e.Handled = true;
            }
        }*/

        /// <summary>
        /// Executed when the Install Button is tapped from the App-Details section. It installs the app.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
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
        
        /// <summary>
        /// Executed when the Share Button is tapped from the App-Details section. The app prepares content form the app to be shared using default installed apps on the phone.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void btnAppShare_Click(object sender, RoutedEventArgs e)
        {
            var datacontext = (e.OriginalSource as FrameworkElement).DataContext;
            DataTransferManager.ShowShareUI();
        }
  
        /// <summary>
        /// Executed when the Search Button is tapped from the Application bar. The app navigates to the Search Results page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Search_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(SearchPage)); }
    
        /// <summary>
        /// Executed when the Home Button is tapped from the Application bar. The app navigates to the Home page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Home_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MainPage)); }
     
        /// <summary>
        /// Executed when the Settings Button is tapped from the Application bar. The app navigates to the Settings page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private async void Settings_Click(object sender, RoutedEventArgs e) { ContentDialog dlg = new ContentDialog(); dlg=new SettingsDialog(); await dlg.ShowAsync(); }
        
        /// <summary>
        /// Executed when the My-Apps Button is tapped from the Application bar. The app navigates to the My-Apps page.
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
        /// Executed when the Feedback Button is tapped from the Application bar. The app prepares the content and uses the default mail apps to give feedback.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Feedback_Click(object sender, RoutedEventArgs e) { AppCommon.ComposeEmail(); }

        /// <summary>
        /// It opens the Reviews section of the app in an external browser.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private async void Browser_Click(object sender, RoutedEventArgs e) { await Windows.System.Launcher.LaunchUriAsync(appReviewsUri); }
        
        /// <summary>
        /// It opens the Reviews section of the app in an external browser.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private async void HyperlinkButton_Click(object sender, RoutedEventArgs e) { await Windows.System.Launcher.LaunchUriAsync(appReviewsUri); }

        /// <summary>
        /// This method is executed when the contents of the Reviews section is loaded.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">WebViewDOMContentLoadedEventArgs is a parameter called args that contains the event data, see the WebViewDOMContentLoadedEventArgs MSDN page for more information.</param>
        private void webAppReviews_FrameDOMContentLoaded(WebView sender, WebViewDOMContentLoadedEventArgs args)
        {
            OpenInBrowser.IsEnabled = true;
            appReviewsUri = args.Uri;
        }
    }
}
