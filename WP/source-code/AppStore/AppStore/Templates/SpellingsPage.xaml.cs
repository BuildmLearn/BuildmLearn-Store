using AppStore.Common;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.Media.SpeechSynthesis;
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
    public sealed partial class SpellingsPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        private Models.SpellingsModel puzzle;
        public SpellingsPage()
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
            puzzle = Models.SpellingsModel.getInstance();
            PuzzleNumber.Text = "Word #" + (puzzle.getActiveCount()+1) + " out of " + puzzle.getSpellingsList().Count;
            pageTitle.Text = puzzle.getPuzzleName();
            if (puzzle.getActiveCount() == puzzle.getSpellingsList().Count - 1) Next.Content = "Submit";
            Next.IsEnabled = false;
            Spell.IsEnabled = false;
            this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion

        private void Spell_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(WordInfoPage));
        }
        private void Next_Click(object sender, RoutedEventArgs e)
        {
            if (puzzle.getActiveCount() == puzzle.getSpellingsList().Count - 1)
            {
                Frame.Navigate(typeof(ScorePage));
            }
            puzzle.setActiveCount(puzzle.getActiveCount() + 1);
            PuzzleNumber.Text = "Word #" + (puzzle.getActiveCount() + 1) + " out of " + puzzle.getSpellingsList().Count;
            Next.IsEnabled = false; Spell.IsEnabled = false;
        }

        private async void Listen_Click(object sender, RoutedEventArgs e)
        {
            using (var speech = new SpeechSynthesizer())
            {
                if ((bool)Female.IsChecked) speech.Voice = SpeechSynthesizer.AllVoices.First(i => i.Gender == VoiceGender.Female);
                else if ((bool)Male.IsChecked) speech.Voice = SpeechSynthesizer.AllVoices.First(i => i.Gender == VoiceGender.Male);
                var voiceStream = await speech.SynthesizeTextToStreamAsync(puzzle.getSpellingsList().ElementAt(puzzle.getActiveCount()).getWord());
                player.SetSource(voiceStream, voiceStream.ContentType);
                player.Play();
            }
            Next.IsEnabled = true; Spell.IsEnabled = true;
        }
    }
}
