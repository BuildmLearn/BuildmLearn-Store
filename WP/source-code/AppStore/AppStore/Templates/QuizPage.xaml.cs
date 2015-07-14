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
    public sealed partial class QuizPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        private Models.QuizModel quiz;
        private int iQuestionIndex = 0;
        private int iCurrentCorrectAnswer=0;
        private int iNumberofQuestions = 0;
        public QuizPage()
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
            quiz = Models.QuizModel.getInstance();
            pageTitle.Text = quiz.getQuizName();
            iNumberofQuestions = quiz.getQueAnsList().Count;
            populateQuestion(iQuestionIndex);
            this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }
        #endregion

        private void Options_Checked(object sender, RoutedEventArgs e)
        {
            Next.IsEnabled = true;
            RadioButton originalAnswer = (RadioButton)QuestionContent.Children.ElementAt(iCurrentCorrectAnswer+2);
            RadioButton yourAnswer = sender as RadioButton;
            if (yourAnswer.Tag.ToString().Equals(iCurrentCorrectAnswer + ""))
            {
                quiz.setTotalCorrect(quiz.getTotalCorrect() + 1);
            }
            else
            {
                quiz.setTotalWrong(quiz.getTotalWrong() + 1);
                yourAnswer.BorderBrush = new SolidColorBrush(Windows.UI.Colors.Red);
                yourAnswer.Background = new SolidColorBrush(Windows.UI.Colors.Red);
            }
            originalAnswer.BorderBrush = new SolidColorBrush(Windows.UI.Colors.DarkGreen);
            originalAnswer.Background = new SolidColorBrush(Windows.UI.Colors.DarkGreen);
            Option1.IsEnabled = false;
            Option2.IsEnabled = false;
            Option3.IsEnabled = false;
            Option4.IsEnabled = false;
        }

        private void Next_Click(object sender, RoutedEventArgs e)
        {
            if (iQuestionIndex == iNumberofQuestions - 2) { iQuestionIndex++; populateQuestion(iQuestionIndex); Next.Content = "Submit"; }
            else if (iQuestionIndex == iNumberofQuestions - 1)
            {
                Frame.Navigate(typeof(ScorePage));
            }
            else
            {
                iQuestionIndex++; populateQuestion(iQuestionIndex);
            }
            Next.IsEnabled = false;
            Option1.IsChecked = false;
            Option2.IsChecked = false;
            Option3.IsChecked = false;
            Option4.IsChecked = false;
            Option1.IsEnabled = true;
            Option2.IsEnabled = true;
            Option3.IsEnabled = true;
            Option4.IsEnabled = true;
            Option1.BorderBrush = new SolidColorBrush(Windows.UI.Colors.White);
            Option2.BorderBrush = new SolidColorBrush(Windows.UI.Colors.White);
            Option3.BorderBrush = new SolidColorBrush(Windows.UI.Colors.White);
            Option4.BorderBrush = new SolidColorBrush(Windows.UI.Colors.White);
            Option1.Background = new SolidColorBrush(Windows.UI.Colors.White);
            Option2.Background = new SolidColorBrush(Windows.UI.Colors.White);
            Option3.Background = new SolidColorBrush(Windows.UI.Colors.White);
            Option4.Background = new SolidColorBrush(Windows.UI.Colors.White);
        }
        public void populateQuestion(int index)
        {
            QuestionNumber.Text="Question #" + (index+1) + " of " + iNumberofQuestions;
            Question.Text=quiz.getQueAnsList().ElementAt(index).getQuestion();
            for (int i = 0; i < 4; i++)
            {
                ((RadioButton)QuestionContent.Children.ElementAt(i+3)).Content=quiz.getQueAnsList().ElementAt(index).getAnswerOption().ElementAt(i);
            }
            iCurrentCorrectAnswer = quiz.getQueAnsList().ElementAt(index).getOptionNumber();
        }
    }
}