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
using Windows.UI.Core;
using Windows.UI.Notifications;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=391641

namespace AppStore
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        bool selectionGridCategories = false, selectionGridApps = false;
        HashSet<Apps> featuredApps = new HashSet<Apps>();
        HashSet<Categories> featuredCategories = new HashSet<Categories>();
        List<int> nofeaturedApps = new List<int>();
        List<int> nofeaturedCategories = new List<int>();


        public MainPage()
        {
            this.InitializeComponent();
            this.NavigationCacheMode = NavigationCacheMode.Required;
            AppCommon.RegisterForShare();
        }
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
        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
        }
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
        private void GridFeaturedCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridCategories) return;
            CategoryInstance.category = (Categories)GridFeaturedCategories.SelectedItem;
            Frame.Navigate(typeof(CategoryPage));
            selectionGridCategories = true;GridFeaturedCategories.SelectedIndex = -1;selectionGridCategories = false;
        }
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
        private void Search_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(SearchPage)); }
        private async void Settings_Click(object sender, RoutedEventArgs e) { ContentDialog dlg = new ContentDialog(); dlg=new SettingsDialog(); await dlg.ShowAsync(); }
        private void MyApps_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MyAppsPage)); }
        private void Categories_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(CategoriesPage)); }
        private void About_Click(object sender, RoutedEventArgs e) {  }
        Apps appHolding;
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

        private void MoreCategories_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(CategoriesPage));
        }
        private void MoreApps_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(AppsPage));
        }

        private void ShareButton_Click(object sender, RoutedEventArgs e)
        {
            var datacontext = (e.OriginalSource as FrameworkElement).DataContext;
            DataTransferManager.ShowShareUI();
        }
    }
}
