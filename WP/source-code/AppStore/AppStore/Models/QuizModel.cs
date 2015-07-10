using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
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
        public static void clearInstance()
        {
            if (mQuizModel != null)
            {
                mQuizModel.totalCorrect = 0;
                mQuizModel.totalWrong = 0;
            }
        }
        public static QuizModel getInstance()
        {
            if (mQuizModel == null)
                mQuizModel = new QuizModel();
            return mQuizModel;
        }
        public List<Question> getQueAnsList()
        {
            return mQueAnsList;
        }
        public void setQueAnsList(List<Question> mQueAnsList)
        {
            this.mQueAnsList = mQueAnsList;
        }
        public string getQuizAuthor()
        {
            return mQuizAuthor;
        }
        public void setQuizAuthor(string mQuizAuthor)
        {
            this.mQuizAuthor = mQuizAuthor;
        }
        public string getQuizDescription()
        {
            return mQuizDescription;
        }
        public void setQuizDescription(string mQuizDescription)
        {
            this.mQuizDescription = mQuizDescription;
        }
        public string getQuizAuthorEmail()
        {
            return mQuizAuthorEmail;
        }
        public void setQuizAuthorEmail(string mQuizAuthorEmail)
        {
            this.mQuizAuthorEmail = mQuizAuthorEmail;
        }
        public string getQuizVersion()
        {
            return mQuizVersion;
        }
        public void setQuizVersion(string mQuizVersion)
        {
            this.mQuizVersion = mQuizVersion;
        }
        public string getQuizName()
        {
            return mQuizName;
        }
        public void setQuizName(string mQuizName)
        {
            this.mQuizName = mQuizName;
        }
        public int getTotalWrong()
        {
            return totalWrong;
        }
        public void setTotalWrong(int totalWrong)
        {
            this.totalWrong = totalWrong;
        }
        public int getTotalCorrect()
        {
            return totalCorrect;
        }
        public void setTotalCorrect(int totalCorrect)
        {
            this.totalCorrect = totalCorrect;
        }
    }
}
