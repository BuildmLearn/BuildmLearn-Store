using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Quiz App-Template.
    /// </summary>
    class QuizModel
    {
        private string mQuizName;
        private string mQuizAuthor;
        private string mQuizDescription;
        private string mQuizVersion;
        private string mQuizAuthorEmail;
        private List<Question> mQueAnsList;
        private int totalCorrect = 0, totalWrong = 0;
        public static QuizModel mQuizModel;

        /// <summary>
        /// Clears the Instance of the Quiz App-Template.
        /// </summary>
        public static void clearInstance()
        {
            if (mQuizModel != null)
            {
                mQuizModel.totalCorrect = 0;
                mQuizModel.totalWrong = 0;
            }
        }

        /// <summary>
        /// Gets the Instance of the Quiz App-Template.
        /// </summary>
        /// <returns></returns>
        public static QuizModel getInstance()
        {
            if (mQuizModel == null)
                mQuizModel = new QuizModel();
            return mQuizModel;
        }
       
        /// <summary>
        /// Gets the List of Questions
        /// </summary>
        /// <returns>List of Questions</returns>
        public List<Question> getQueAnsList()
        {
            return mQueAnsList;
        }
     
        /// <summary>
        /// Sets the list of Questions
        /// </summary>
        /// <param name="mQueAnsList">List of Question Objects</param>
        public void setQueAnsList(List<Question> mQueAnsList)
        {
            this.mQueAnsList = mQueAnsList;
        }
    
        /// <summary>
        /// Gets the Author of the Quiz
        /// </summary>
        /// <returns>Author of the Quiz</returns>
        public string getQuizAuthor()
        {
            return mQuizAuthor;
        }
    
        /// <summary>
        /// Sets the Author of the quiz
        /// </summary>
        /// <param name="mQuizAuthor">Author of the Quiz</param>
        public void setQuizAuthor(string mQuizAuthor)
        {
            this.mQuizAuthor = mQuizAuthor;
        }
  
        /// <summary>
        /// Gets the Description of the quiz
        /// </summary>
        /// <returns>Description of the Quiz</returns>
        public string getQuizDescription()
        {
            return mQuizDescription;
        }
   
        /// <summary>
        /// Sets the Description of the Quiz
        /// </summary>
        /// <param name="mQuizDescription">Description of the Quiz</param>
        public void setQuizDescription(string mQuizDescription)
        {
            this.mQuizDescription = mQuizDescription;
        }
    
        /// <summary>
        /// Gets the Author Email of the Quiz
        /// </summary>
        /// <returns>Author Email
        public void setQuizAuthorEmail(string mQuizAuthorEmail)
        {
            this.mQuizAuthorEmail = mQuizAuthorEmail;
        }
   
        /// <summary>
        /// Gets the Version of the Quiz
        /// </summary>
        /// <returns>Version of the Quiz</returns>
        public string getQuizVersion()
        {
            return mQuizVersion;
        }
     
        /// <summary>
        /// Sets the version of the Quiz
        /// </summary>
        /// <param name="mQuizVersion">Version of the Quiz</param>
        public void setQuizVersion(string mQuizVersion)
        {
            this.mQuizVersion = mQuizVersion;
        }
     
        /// <summary>
        /// Gets the Name of the Quiz
        /// </summary>
        /// <returns>name of the Quiz</returns>
        public string getQuizName()
        {
            return mQuizName;
        }
    
        /// <summary>
        /// Sets the name of the Quiz
        /// </summary>
        /// <param name="mQuizName">Name of the Quiz</param>
        public void setQuizName(string mQuizName)
        {
            this.mQuizName = mQuizName;
        }
    
        /// <summary>
        /// Gets the total number of Wrong answers
        /// </summary>
        /// <returns>Number of wrong answers</returns>
        public int getTotalWrong()
        {
            return totalWrong;
        }
    
        /// <summary>
        /// Sets the total number of wrong answers
        /// </summary>
        /// <param name="totalWrong">Number of wrong answers</param>
        public void setTotalWrong(int totalWrong)
        {
            this.totalWrong = totalWrong;
        }
    
        /// <summary>
        /// Gets the total number of correct answers
        /// </summary>
        /// <returns>Total number of correct answers</returns>
        public int getTotalCorrect()
        {
            return totalCorrect;
        }
     
        /// <summary>
        /// Sets the total correct answers by the user.
        /// </summary>
        /// <param name="totalCorrect">Total number of correct answers</param>
        public void setTotalCorrect(int totalCorrect)
        {
            this.totalCorrect = totalCorrect;
        }
    }
}
