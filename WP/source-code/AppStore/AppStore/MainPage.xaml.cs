using AppStore.Common;
using AppStore.Models;
using AppStore.Templates;
using System;
using System.Collections.Generic;
using System.Linq;
using Windows.ApplicationModel.DataTransfer;
using Windows.UI.Core;
using Windows.UI.Notifications;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=391641

namespace AppStore
{
    /// <summary>
    /// This page is the Home Page.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        bool selectionGridCategories = false, selectionGridApps = false;
        HashSet<Apps> featuredApps = new HashSet<Apps>();
        HashSet<Categories> featuredCategories = new HashSet<Categories>();
        List<int> nofeaturedApps = new List<int>();
        List<int> nofeaturedCategories = new List<int>();
        Apps appHolding;
     
        /// <summary>
        /// Public Contructor to MainPage
        /// </summary>
        public MainPage()
        {
            this.InitializeComponent();
            this.NavigationCacheMode = NavigationCacheMode.Required;
            AppCommon.RegisterForShare();
        }
    
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
        protected override async void OnNavigatedTo(NavigationEventArgs e)
        {
            if (AppList.getMyAppList().myappList.Count > 0)
                btnMyApps.Visibility = Visibility.Visible;
            else btnMyApps.Visibility = Visibility.Collapsed;
            int fa = 6, fc = 4;
            try
            {
                var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
                if (localSettings.Values.ContainsKey("Featured_Categories"))
                {Int32.TryParse(localSettings.Values["Featured_Categories"].ToString(),out fc); }
                else localSettings.Values.Add("Featured_Categories", "4");
                if (localSettings.Values.ContainsKey("Featured_Apps"))
                { Int32.TryParse(localSettings.Values["Featured_Apps"].ToString(), out fa); }
                else localSettings.Values.Add("Featured_Apps", "6");
            }
            catch (Exception ex)
            { }
            Random rnd = new Random();
            featuredCategories.Clear();
            featuredApps.Clear();
                int l = AppList.getAppList().appList.Count;
                for (int i = 0; i < fa; i++)
                {
                while (true)
                {
                    int x = rnd.Next(0, l);
                    Apps ob = AppList.getAppList().appList.ElementAt(x);
                    if (!nofeaturedApps.Contains(x))
                    { featuredApps.Add(ob);nofeaturedApps.Add(x); break;}
                }
                }
                rnd = new Random();
                for (int i = 0; i < fc; i++)
                {
                while (true)
                {
                    int x = rnd.Next(0, 10);
                    Categories ob = Models.Resources.getCategoriesList().ElementAt(x);
                    if (!nofeaturedCategories.Contains(x))
                    { featuredCategories.Add(ob); nofeaturedCategories.Add(x); break; }
                }
                }
            
            GridFeaturedApps.ItemsSource = featuredApps;
            GridFeaturedCategories.ItemsSource = featuredCategories;
            if(e.Parameter!=null)
             if (!String.IsNullOrWhiteSpace(e.Parameter.ToString()))
            {
                string appName = e.Parameter.ToString();
                foreach (Apps app in featuredApps)
                {
                    if (app.Name.Equals(appName))
                    {
                        AppInstance.app = app;
                        CoreDispatcher dispatcher = CoreWindow.GetForCurrentThread().Dispatcher;
                        await dispatcher.RunAsync(CoreDispatcherPriority.Normal, () =>
                        { Frame.Navigate(typeof(StartPage),"remove"); });
                    }
                }
            }
            if(Frame.BackStack.Count>0)
                Frame.BackStack.Clear();
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
        }
    
        /// <summary>
        /// Deals with populating apps in the Featured-Apps section.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">EventArgs args is a parameter called e that contains the event data, see the ContainerContentChangingEventArgs MSDN page for more information.</param>
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
        /// Deals with populating images(app icons) in the Featured-Apps section.
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
        /// Executed when the user taps/selects an app from the Featured-Apps section.
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
        /// Executed when the user taps/selects a category from the Featured-Categories section.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">SelectionChangedEventArgs e an instance of SelectionChangedEventArgs including, in many cases, an object which inherits from SelectionChangedEventArgs. Contains additional information about the event, and sometimes provides ability for code handling the event to alter the event somehow.</param>
        private void GridFeaturedCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridCategories) return;
            CategoryInstance.category = (Categories)GridFeaturedCategories.SelectedItem;
            Frame.Navigate(typeof(CategoryPage));
            selectionGridCategories = true;GridFeaturedCategories.SelectedIndex = -1;selectionGridCategories = false;
        }
    
        /// <summary>
        /// Deals with populating categories in the Featured-Categories section.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">EventArgs args is a parameter called e that contains the event data, see the ContainerContentChangingEventArgs MSDN page for more information.</param>
        private void GridFeaturedCategories_ContentChanging(ListViewBase sender, ContainerContentChangingEventArgs args)
        {
            args.Handled = true;
            if (args.Phase != 0)
            {
                throw new Exception("Not in phase 0.");
            }
            Categories category = (Categories)args.Item;
            Grid templateRoot = (Grid)args.ItemContainer.ContentTemplateRoot;
            Image categoryIcon = (Image)templateRoot.FindName("categoryIcon");
            TextBlock categoryName = (TextBlock)templateRoot.FindName("categoryName");
            categoryName.Text = category.Name;
            categoryIcon.Source = new BitmapImage(new Uri(category.Background));
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
        private async void Settings_Click(object sender, RoutedEventArgs e) { ContentDialog dlg = new ContentDialog(); dlg=new SettingsDialog(); await dlg.ShowAsync(); }
   
        /// <summary>
        /// Executed when the user taps/selects an app from the search results.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">SelectionChangedEventArgs e an instance of SelectionChangedEventArgs including, in many cases, an object which inherits from SelectionChangedEventArgs. Contains additional information about the event, and sometimes provides ability for code handling the event to alter the event somehow.</param>
        private void MyApps_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MyAppsPage)); }
    
        /// <summary>
        /// Executed when the Categories Button is tapped from the Application bar. The app navigates to the Categories page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
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
        private void About_Click(object sender, RoutedEventArgs e) {  }
   
        /// <summary>
        /// Executed when the app is long tapped. Context menu appears now.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void StackPanel_Holding(object sender, HoldingRoutedEventArgs e)
        {
            FrameworkElement senderElement = sender as FrameworkElement;
            StackPanel stackPanel=sender as StackPanel;
            TextBlock txt=(TextBlock)stackPanel.Children.ElementAt(1);
            foreach (Apps app in featuredApps)
            {
                if (app.Name.Equals(txt.Text))
                {
                    appHolding = app;break;
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
        /// <summary>
        /// Executed when the Feedback Button is tapped from the Application bar. The app prepares the content and uses the default mail apps to give feedback.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Feedback_Click(object sender, RoutedEventArgs e) { AppCommon.ComposeEmail(); }
   
        /// <summary>
        /// Executed when the Install button is tapped from the Context Menu, which appears when the app is long tapped.
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
   
        /// <summary>
        /// Executed when the "more" button is tapped in the Featured Categories section. It navigates to the Categories Page, which lists all the categories.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void MoreCategories_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(CategoriesPage));
        }
   
        /// <summary>
        /// Executed when the "more" button is tapped in the Featured Apps section. It navigates to the AppsPage, which lists all the apps.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void MoreApps_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(AppsPage));
        }

    }
}
