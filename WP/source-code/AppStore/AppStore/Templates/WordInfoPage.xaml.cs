using AppStore.Common;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.UI.Core;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore.Templates
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class WordInfoPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        Models.SpellingsModel puzzle;

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

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            Answer.Visibility = Visibility.Collapsed;
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion
        private void Back_Click(object sender, RoutedEventArgs e)
        {
            Frame.GoBack();
        }
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
        private void Next_Click(object sender, RoutedEventArgs e)
        {
            if (puzzle.getActiveCount() + 1 == puzzle.getSpellingsList().Count) Frame.Navigate(typeof(ScorePage));
            puzzle.setActiveCount(puzzle.getActiveCount() + 1);
            Frame.GoBack();
        }

        private void Word_TextChanged(object sender, TextChangedEventArgs e)
        {
            Enter.IsEnabled = true;
        }
    }
}
