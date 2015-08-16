using AppStore.Common;
using AppStore.Models;
using System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore.Templates
{
    /// <summary>
    /// This page deals with the Score Page related to the Spellings Puzzle and the Quiz App-Template.
    /// </summary>
    public sealed partial class ScorePage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        private SpellingsModel puzzle;
        private QuizModel quiz;
        /// <summary>
        /// Public Construtor to the ScorePage. Populates the views pertaining to this page.
        /// </summary>
        public ScorePage()
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
            Frame.BackStack.RemoveAt(Frame.BackStackDepth - 1);
            Apps app = AppInstance.app;
            if (app.Type.Contains("Spellings"))
            {
                puzzle = SpellingsModel.getInstance();
                ScoreText.Text = "You have completed the Spellings Puzzle: "+puzzle.getPuzzleName();
                TotalCorrect.Text = "Total Correct: " + puzzle.getTotalCorrect();
                TotalWrong.Text = "Total Wrong: " + puzzle.getTotalWrong();
                TotalUnanswered.Text = "Total Unanswered: " + (puzzle.getSpellingsList().Count - (puzzle.getTotalWrong()+puzzle.getTotalCorrect()));
                SpellingsModel.clearInstance();
            }
            else if (app.Type.Contains("Quiz"))
            {
                quiz = QuizModel.getInstance();
                ScoreText.Text = "You have completed the Quiz: "+quiz.getQuizName();
                TotalCorrect.Text = "Total Correct: " + quiz.getTotalCorrect();
                TotalWrong.Text = "Total Wrong: " + quiz.getTotalWrong();
                TotalUnanswered.Text = "Total Unanswered: " + (quiz.getQueAnsList().Count - (quiz.getTotalWrong() + quiz.getTotalCorrect()));
                QuizModel.clearInstance();
            }
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
        /// It restarts the current quiz or spellings puzzle app.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Restart_Click(object sender, RoutedEventArgs e)
        {
            if (StartPage.removeParameter.Equals("remove"))
                Frame.Navigate(typeof(StartPage), "remove");
            else Frame.Navigate(typeof(StartPage));
        }

        /// <summary>
        /// It quits the current app and navigates back to its details page.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">RoutedEventArgs e is a parameter called e that contains the event data, see the RoutedEventArgs MSDN page for more information.</param>
        private void Quit_Click(object sender, RoutedEventArgs e)
        {
            Frame.GoBack();
        }
    }
}
