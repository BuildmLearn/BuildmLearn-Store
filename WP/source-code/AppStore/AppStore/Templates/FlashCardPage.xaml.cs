using AppStore.Common;
using System;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Storage.Streams;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore.Templates
{
    /// <summary>
    /// This page deals with the Flash-Card App template.
    /// </summary>
    public sealed partial class FlashCardPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        private Models.FlashModel flash;
        int iQuestionIndex = 0;
        private bool isFlipped = false;

        /// <summary>
        /// Public Construtor to the FlashCardPage. Populates the views pertaining to this page.
        /// </summary>
        public FlashCardPage()
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
            flash = Models.FlashModel.getInstance();
            pageTitle.Text = flash.getFlashName();
            iQuestionIndex = 0;
            populateQuestion(iQuestionIndex);
            this.navigationHelper.OnNavigatedTo(e);
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
        /// Executed when the Previous Button is tapped. It populates previous flash card question to the view.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Previous_Click(object sender, RoutedEventArgs e)
        {
            if (iQuestionIndex != 0)
            {
                isFlipped = false;
                iQuestionIndex--;
                populateQuestion(iQuestionIndex);
            }
        }

        /// <summary>
        /// Executed when the Flip Button is tapped. It flips the flash card in the view.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Flip_Click(object sender, RoutedEventArgs e)
        {
            if (isFlipped)
            {
                VisualStateManager.GoToState(this, "FlipCardBack", true);
                frontCard.Visibility = Visibility.Visible;
                backCard.Visibility = Visibility.Collapsed;
                isFlipped = false;
            }
            else
            {
                VisualStateManager.GoToState(this, "FlipCardFront", true);
                backCard.Visibility = Visibility.Visible;
                frontCard.Visibility = Visibility.Collapsed;
                isFlipped = true;
            }
        }

        /// <summary>
        /// Executed when the Next Button is tapped. It populates next flash card question to the view.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Next_Click(object sender, RoutedEventArgs e)
        {
            if (iQuestionIndex < flash.getCardList().Count - 1)
            {
                frontCard.Visibility = Visibility.Visible;
                backCard.Visibility = Visibility.Collapsed;
                isFlipped = false;
                iQuestionIndex++;
                populateQuestion(iQuestionIndex);
            }
            else if (iQuestionIndex == flash.getCardList().Count - 2) Next.Content = "Quit";
            else
            {
                Frame.GoBack();
            }
        }
        
        /// <summary>
        /// It populates the flash card question of the index passed to the current view.
        /// </summary>
        /// <param name="index">The index of flash card, which is to be populated in the view</param>
        public async void populateQuestion(int index)
        {
            if (index == 0) Previous.IsEnabled = false;
            else Previous.IsEnabled = true;
            int cardNum = index + 1;
            FlashNumber.Text = "Card #" + cardNum + " of " + flash.getCardList().Count;
            if (flash.getCardList().ElementAt(index).getImagePath() != null)
            {
                try
                {
                    FlashCard.Source = await base64image(flash.getCardList().ElementAt(index).getImagePath());
                }
                catch (Exception) { }
            }
            FlashText.Text = flash.getCardList().ElementAt(index).getAnswer();
            FlashHint.Text = flash.getCardList().ElementAt(index).getHint();
            if (flash.getCardList().ElementAt(index).getQuestion() != null)
            {
                FlashQuestion.Text = flash.getCardList().ElementAt(index).getQuestion();
            }

        }
       
        /// <summary>
        /// It converts string in Base64 to the Bitmap image.
        /// </summary>
        /// <param name="base64string"></param>
        /// <returns></returns>
        public static async System.Threading.Tasks.Task<BitmapImage> base64image(string base64string)
        {
            byte[] fileBytes = Convert.FromBase64String(base64string);
            using (InMemoryRandomAccessStream stream = new InMemoryRandomAccessStream())
            {
                await stream.WriteAsync(fileBytes.AsBuffer(0, fileBytes.Length));
                stream.Seek(0);
                BitmapImage image = new BitmapImage();
                await image.SetSourceAsync(stream);
                return image;
            }
        }
    }
}
