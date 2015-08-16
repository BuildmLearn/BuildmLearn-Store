using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is related to the Quiz App-Template. It is model for questions.
    /// </summary>
    class Question
    {
        private string mQuestion;
        private List<string> mAnswerOption;
        private int mOptionNumber;

        /// <summary>
        /// Gets the Question of the current QuizModel Object
        /// </summary>
        /// <returns> Question of the current QuizModel Object</returns>
        public string getQuestion()
        {
            return mQuestion;
        }

        /// <summary>
        /// Sets the Question of the current QuizModel Object
        /// </summary>
        /// <param name="mQuestion">Question of the current QuizModel Object</param>
        public void setQuestion(string mQuestion)
        {
            this.mQuestion = mQuestion;
        }

        /// <summary>
        /// Gets the AnswerOption of the current QuizModel Object
        /// </summary>
        /// <returns> AnswerOption of the current QuizModel Object</returns>
        public List<string> getAnswerOption()
        {
            return mAnswerOption;
        }

        /// <summary>
        /// Sets the AnswerOption of the current QuizModel Object
        /// </summary>
        /// <param name="mAnswerOption">AnswerOption of the current QuizModel Object</param>
        public void setAnswerOption(List<string> mAnswerOption)
        {
            this.mAnswerOption = mAnswerOption;
        }

        /// <summary>
        /// Gets the OptionNumber of the current QuizModel Object
        /// </summary>
        /// <returns> OptionNumber of the current QuizModel Object</returns>
        public int getOptionNumber()
        {
            return mOptionNumber;
        }

        /// <summary>
        /// Sets the Option Number of the current QuizModel Object
        /// </summary>
        /// <param name="mOptionNumber">OptionNumber of the current QuizModel Object</param>
        public void setOptionNumber(int mOptionNumber)
        {
            this.mOptionNumber = mOptionNumber;
        }

    }
}
