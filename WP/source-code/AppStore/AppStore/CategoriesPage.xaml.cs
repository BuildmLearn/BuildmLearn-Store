using AppStore.Common;
using AppStore.Models;
using System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore
{

    /// <summary>
    /// This page deals with categorizing apps into certains groups.
    /// The categories are shown in tiles, which when tapped will navigate to the list of apps belonging to the particular category.
    /// </summary>
    public sealed partial class CategoriesPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        bool selectionGridCategories = false;

        /// <summary>
        /// Public constructor to the Categories Page.
        /// </summary>
        public CategoriesPage()
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
            GridCategories.ItemsSource = Models.Resources.getCategoriesList();
            this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion

        /// <summary>
        /// Executed when the user taps/selects a category from the Categoires Page. The app navigates to its category-specific page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">SelectionChangedEventArgs e an instance of SelectionChangedEventArgs including, in many cases, an object which inherits from SelectionChangedEventArgs. Contains additional information about the event, and sometimes provides ability for code handling the event to alter the event somehow.</param>
        private void GridCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (selectionGridCategories) return;
            CategoryInstance.category = (Categories)GridCategories.SelectedItem;
            Frame.Navigate(typeof(CategoryPage));
            selectionGridCategories = true; GridCategories.SelectedIndex = -1; selectionGridCategories = false;
        }
    
        /// <summary>
        /// Deals with populating categories in this page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">ContainerContentChangingEventArgs args is a parameter called e that contains the event data, see the ContainerContentChangingEventArgs MSDN page for more information.</param>
        private void GridCategories_ContentChanging(ListViewBase sender, ContainerContentChangingEventArgs args)
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
        private async void Settings_Click(object sender, RoutedEventArgs e) { ContentDialog dlg = new ContentDialog(); dlg = new SettingsDialog(); await dlg.ShowAsync(); }
    
        /// <summary>
        /// Executed when the Home Button is tapped from the Application bar. The app navigates to the Home page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Home_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MainPage)); }
    
        /// <summary>
        /// Executed when the My-Apps Button is tapped from the Application bar. The app navigates to the My-Apps page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void MyApps_Click(object sender, RoutedEventArgs e) { Frame.Navigate(typeof(MyAppsPage)); }
   
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
        private void Feedback_Click(object sender, RoutedEventArgs e) { }
    }
}
