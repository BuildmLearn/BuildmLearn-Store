namespace AppStore.Models
{
    /// <summary>
    /// It is related to the FlashCard App-Template. It is the model of cards.
    /// </summary>
    class Card
    {
        private string mQuestion, mAnswer, mHint, mImagePath;

        /// <summary>
        /// Public constructor
        /// </summary>
        /// <param name="question">String question</param>
        /// <param name="answer">String answer</param>
        /// <param name="hint">String hint</param>
        /// <param name="imagePath">String imagePath</param>
        public Card(string question, string answer, string hint, string imagePath)
        {
            this.mQuestion = question;
            this.mAnswer = answer;
            this.mHint = hint;
            this.mImagePath = imagePath;
        }

        /// <summary>
        /// Gets the question.
        /// </summary>
        /// <returns>String Question</returns>
        public string getQuestion()
        {
            return mQuestion;
        }

        /// <summary>
        /// Sets the Question.
        /// </summary>
        /// <param name="mQuestion">String Question</param>
        public void setQuestion(string mQuestion)
        {
            this.mQuestion = mQuestion;
        }

        /// <summary>
        /// Gets the answer.
        /// </summary>
        /// <returns>String Answer</returns>
        public string getAnswer()
        {
            return mAnswer;
        }

        /// <summary>
        /// Sets the answer
        /// </summary>
        /// <param name="mAnswer">String answer</param>
        public void setAnswer(string mAnswer)
        {
            this.mAnswer = mAnswer;
        }

        /// <summary>
        /// Gets the hint
        /// </summary>
        /// <returns>String Hint</returns>
        public string getHint()
        {
            return mHint;
        }

        /// <summary>
        /// Sets the hint
        /// </summary>
        /// <returns>String Hint</returns>
        public void setHint(string mHint)
        {
            this.mHint = mHint;
        }

        /// <summary>
        /// Gets the ImagePath
        /// </summary>
        /// <returns>String ImagePath</returns>
        public string getImagePath()
        {
            return mImagePath;
        }

        /// <summary>
        /// Sets the ImagePath
        /// </summary>
        /// <returns>String Imagepath</returns>
        public void setImagePath(string mImagePath)
        {
            this.mImagePath = mImagePath;
        }

    }
}
