using AppStore.Common;
using AppStore.Models;
using System;
using Windows.Data.Xml.Dom;
using Windows.Foundation;
using Windows.UI.Notifications;
using Windows.UI.StartScreen;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore.Templates
{
    /// <summary>
    /// This page deals with the StartPage of all the App-Template.
    /// </summary>
    public sealed partial class StartPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        public static string removeParameter = "";
        private Type page;

        /// <summary>
        /// Public Construtor to the StartPage. Populates the views pertaining to this page.
        /// </summary>
        public StartPage()
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
            if (e.Parameter != null && e.Parameter.ToString().Equals("remove")) { removeParameter = "remove"; Frame.BackStack.Clear(); }
            Apps app = AppInstance.app;
            updateTile();
            Init();
            if (app.Type.Contains("Info"))
            { AppReader.readInfoFile(app.Name); page = typeof(InfoPage); }
            else if (app.Type.Contains("Flash"))
            { AppReader.readFlashFile(app.Name); page = typeof(FlashCardPage); }
            else if (app.Type.Contains("Quiz"))
            { AppReader.readQuizFile(app.Name); QuizModel.clearInstance(); page = typeof(QuizPage); }
            else if (app.Type.Contains("Spellings"))
            { AppReader.readSpellingsFile(app.Name); SpellingsModel.clearInstance(); page = typeof(SpellingsPage); }
            else
            {
                Frame.GoBack();
            }
            this.navigationHelper.OnNavigatedTo(e);
            AppLogo.Source = new BitmapImage(new Uri(app.AppIcon));
            AppName.Text = app.Name;
            AppAuthor.Text = app.Author;
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
        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }
        #endregion
        
        /// <summary>
        /// Executed when the Start button is tapped/pressed. Populates the app-template.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Start_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(page);
        }
      
        /// <summary>
        /// It updates the tile, which makes a live tile view in the StartPage.
        /// </summary>
        private void updateTile()
        {
            TileUpdateManager.CreateTileUpdaterForApplication().Clear();
            TileUpdateManager.CreateTileUpdaterForApplication().EnableNotificationQueue(true);
            var tileXml = TileUpdateManager.GetTemplateContent(TileTemplateType.TileSquare150x150Image);
            var tileImage = tileXml.GetElementsByTagName("image")[0] as XmlElement;
            tileImage.SetAttribute("src", "ms-appx:///Assets/aka.png");
            var tileNotification = new TileNotification(tileXml);
            TileUpdateManager.CreateTileUpdaterForApplication().Update(tileNotification);
            tileImage.SetAttribute("src", AppInstance.app.AppIcon);
            tileNotification = new TileNotification(tileXml);
            tileNotification.Tag = "Start";
            TileUpdateManager.CreateTileUpdaterForApplication().Update(tileNotification);

            tileXml = TileUpdateManager.GetTemplateContent(TileTemplateType.TileWide310x150ImageAndText01);
            XmlNodeList tileTextAttributes = tileXml.GetElementsByTagName("text");
            tileTextAttributes[0].InnerText = "BuildmLearn Store: Promoting mLearning";
            XmlNodeList tileImageAttributes = tileXml.GetElementsByTagName("image");
            ((XmlElement)tileImageAttributes[0]).SetAttribute("src", "ms-appx:///assets/aka.png");
            tileNotification = new TileNotification(tileXml);
            TileUpdateManager.CreateTileUpdaterForApplication().Update(tileNotification);
            tileTextAttributes[0].InnerText = AppInstance.app.Name + " By " + AppInstance.app.Author;
            tileNotification = new TileNotification(tileXml);
            tileNotification.Tag = "Start";
            ((XmlElement)tileImageAttributes[0]).SetAttribute("src", AppInstance.app.AppIcon);
            TileUpdateManager.CreateTileUpdaterForApplication().Update(tileNotification);
        }
    
        /// <summary>
        /// Toggles the appbar button in the application bar.
        /// </summary>
        /// <param name="showPinButton">bool showPinButton</param>
        private void ToggleAppBarButton(bool showPinButton)
        {
            if (showPinButton)
            {
                this.PinUnPinCommandButton.Label = "Pin to Start";
                this.PinUnPinCommandButton.Icon = new SymbolIcon(Symbol.Pin);
            }
            else
            {
                this.PinUnPinCommandButton.Label = "Unpin from Start";
                this.PinUnPinCommandButton.Icon = new SymbolIcon(Symbol.UnPin);
            }

            this.PinUnPinCommandButton.UpdateLayout();
        }
        
        /// <summary>
        /// The method which initializes the appbar button in the application bar, whether the app is pinned or not.
        /// </summary>
        void Init()
        {
            ToggleAppBarButton(!SecondaryTile.Exists(AppInstance.app.Name));
            this.PinUnPinCommandButton.Click += this.pinToAppBar_Click;
        }

        /// <summary>
        /// It pins the app to the StartPage.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        async void pinToAppBar_Click(object sender, RoutedEventArgs e)
        {
            if (SecondaryTile.Exists(AppInstance.app.Name.Replace(" ", "")))
            {
                SecondaryTile secondaryTile = new SecondaryTile(AppInstance.app.Name.Replace(" ", ""));
                bool isUnpinned = await secondaryTile.RequestDeleteForSelectionAsync(GetElementRect((FrameworkElement)sender), Windows.UI.Popups.Placement.Below);
                if (isUnpinned)
                {
                    ToggleAppBarButton(isUnpinned);
                }
            }
            else
            {
                Uri square150x150Logo = new Uri("ms-appx:///Assets/Logo.scale-100.png");
                string tileActivationArguments = AppInstance.app.Name;
                SecondaryTile secondaryTile = new SecondaryTile(AppInstance.app.Name.Replace(" ", ""),
                                                                AppInstance.app.Name,
                                                                tileActivationArguments,
                                                                square150x150Logo,
                                                                TileSize.Square150x150);
                secondaryTile.VisualElements.ShowNameOnSquare150x150Logo = true;
                secondaryTile.VisualElements.ForegroundText = ForegroundText.Dark;
                secondaryTile.RoamingEnabled = false;
                ToggleAppBarButton(false);
                await secondaryTile.RequestCreateAsync();

            }
        }
     
        /// <summary>
        /// This is the position of the retcangle where the app will be pinned on the Start Page.
        /// </summary>
        /// <param name="element">FrameworkElement element object</param>
        /// <returns>Returns the rect object</returns>
        public static Rect GetElementRect(FrameworkElement element)
        {
            GeneralTransform buttonTransform = element.TransformToVisual(null);
            Point point = buttonTransform.TransformPoint(new Point());
            return new Rect(point, new Size(element.ActualWidth, element.ActualHeight));
        }
    }
}
