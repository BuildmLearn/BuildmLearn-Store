using AppStore.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Core;
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
        bool selectionGridCategories= false, selectionGridApps = false, selectionListMenus = false;
        public MainPage()
        {
            this.InitializeComponent();
            DrawerLayout.InitializeDrawerLayout();
            string[] menuItems = new string[8] { "Home", "My Apps", "Categories", "Settings", "", "Feedback", "Rate my app", "About" };
            ListMenuItems.ItemsSource = menuItems.ToList();
            this.NavigationCacheMode = NavigationCacheMode.Required;
        }
        private void DrawerIcon_Tapped(object sender, TappedRoutedEventArgs e)
        {
            if (DrawerLayout.IsDrawerOpen)
                DrawerLayout.CloseDrawer();
            else
                DrawerLayout.OpenDrawer();
        }
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            GridFeaturedApps.ItemsSource = AppList.getAppList().appList;
            GridFeaturedCategories.ItemsSource = Models.Resources.getCategoriesList();
            Windows.Phone.UI.Input.HardwareButtons.BackPressed += HardwareButtons_BackPressed;
        }
        void HardwareButtons_BackPressed(object sender, Windows.Phone.UI.Input.BackPressedEventArgs e)
        {
            if (DrawerLayout.IsDrawerOpen)
            {
                DrawerLayout.CloseDrawer();
                e.Handled = true;
            }
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
        private async void GridFeaturedApps_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridApps) return;
            AppInstance.app = (Apps)GridFeaturedApps.SelectedItem;
            CoreDispatcher dispatcher = CoreWindow.GetForCurrentThread().Dispatcher;
            await dispatcher.RunAsync(CoreDispatcherPriority.Normal, () =>
            {
                Frame.Navigate(typeof(AppDetailsPage));
            });
            selectionGridApps = true;
            GridFeaturedApps.SelectedIndex = -1;
            selectionGridApps = false;
        }
        private async void ListMenuItems_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionListMenus) return;
            switch (ListMenuItems.SelectedIndex)
            {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    CoreDispatcher dispatcher = CoreWindow.GetForCurrentThread().Dispatcher;
                    await dispatcher.RunAsync(CoreDispatcherPriority.Normal, () =>
                    {
                        Frame.Navigate(typeof(CategoriesPage));
                    });
                    selectionListMenus = true;
                    ListMenuItems.SelectedIndex = -1;
                    selectionListMenus = false;
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;

            }
        }
        private async void GridFeaturedCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridCategories) return;
            CategoryInstance.category = (Categories)GridFeaturedCategories.SelectedItem;
            CoreDispatcher dispatcher = CoreWindow.GetForCurrentThread().Dispatcher;
            await dispatcher.RunAsync(CoreDispatcherPriority.Normal, () =>
            {
                Frame.Navigate(typeof(CategoryPage));
            });
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
    }
}
