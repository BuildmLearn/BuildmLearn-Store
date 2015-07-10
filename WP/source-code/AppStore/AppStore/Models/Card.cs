using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class Card
    {
        private string mQuestion, mAnswer, mHint, mImagePath;

        public Card(string question, string answer, string hint, string imagePath)
        {
            this.mQuestion = question;
            this.mAnswer = answer;
            this.mHint = hint;
            this.mImagePath = imagePath;
        }

        public string getQuestion()
        {
            return mQuestion;
        }

        public void setQuestion(string mQuestion)
        {
            this.mQuestion = mQuestion;
        }

        public string getAnswer()
        {
            return mAnswer;
        }

        public void setAnswer(string mAnswer)
        {
            this.mAnswer = mAnswer;
        }

        public string getHint()
        {
            return mHint;
        }

        public void setHint(string mHint)
        {
            this.mHint = mHint;
        }

        public string getImagePath()
        {
            return mImagePath;
        }

        public void setImagePath(string mImagePath)
        {
            this.mImagePath = mImagePath;
        }

    }
}
