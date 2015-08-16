using AppStore.Common;
using System;
using System.Linq;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore.Templates
{
    /// <summary>
    /// The page deals with shwoing the word info based on the word which comes up while running the Spellings Puzzle app-template.
    /// </summary>
    public sealed partial class WordInfoPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        Models.SpellingsModel puzzle;

        /// <summary>
        /// Public Construtor to the WordInfoPage. Populates the views pertaining to this page.
        /// </summary>
        public WordInfoPage()
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
            puzzle = Models.SpellingsModel.getInstance();
            PuzzleNumber.Text = "Word #" + (puzzle.getActiveCount()+1) + " out of " + puzzle.getSpellingsList().Count;
            if ((puzzle.getActiveCount() + 1) == puzzle.getSpellingsList().Count) Next.Content = "Submit";
            pageTitle.Text = puzzle.getPuzzleName();
            OriginalWord.Text = "Word: "+ puzzle.getSpellingsList().ElementAt(puzzle.getActiveCount()).getWord();
            WordDescription.Text = "Meaning: "+ puzzle.getSpellingsList().ElementAt(puzzle.getActiveCount()).getDescription();
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
            Answer.Visibility = Visibility.Collapsed;
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion

        /// <summary>
        /// The app navigates to the previous page which is the word page of the Spellings App-Template.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Back_Click(object sender, RoutedEventArgs e)
        {
            Frame.GoBack();
        }

        /// <summary>
        /// Executed when the Enter button is tapped/pressed. It checks the correct word with the spelling entered by the user.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Enter_Click(object sender, RoutedEventArgs e)
        {
            Enter.IsEnabled = false;
            Back.IsEnabled = false;
            Answer.Visibility = Visibility.Visible;
            string yourWord = Word.Text;
            string originalWord = puzzle.getSpellingsList().ElementAt(puzzle.getActiveCount()).getWord();
            if (string.Equals(yourWord,originalWord, StringComparison.OrdinalIgnoreCase))
            {
                puzzle.setTotalCorrect(puzzle.getTotalCorrect() + 1);
                Response.Text = "You got the right word !";
            }
            else {
                puzzle.setTotalWrong(puzzle.getTotalWrong() + 1);
                Response.Text = "Sorry, the word you entered is wrong!";
            }
        }

        /// <summary>
        /// It populates the next word question in the current view.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Next_Click(object sender, RoutedEventArgs e)
        {
            if (puzzle.getActiveCount() + 1 == puzzle.getSpellingsList().Count) Frame.Navigate(typeof(ScorePage));
            puzzle.setActiveCount(puzzle.getActiveCount() + 1);
            Frame.GoBack();
        }

        /// <summary>
        /// Executed when the text in the textbox is changed by the user.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">TextChangedEventArgs e is a parameter called e that contains the event data, see the TextChangedEventArgs MSDN page for more information.</param>
        private void Word_TextChanged(object sender, TextChangedEventArgs e)
        {
            Enter.IsEnabled = true;
        }
    }
}
