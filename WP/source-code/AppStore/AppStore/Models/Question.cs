using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class Question
    {
        private string mQuestion;
        private List<string> mAnswerOption;
        private int mOptionNumber;

        public string getQuestion()
        {
            return mQuestion;
        }

        public void setQuestion(string mQuestion)
        {
            this.mQuestion = mQuestion;
        }

        public List<string> getAnswerOption()
        {
            return mAnswerOption;
        }

        public void setAnswerOption(List<string> mAnswerOption)
        {
            this.mAnswerOption = mAnswerOption;
        }

        public int getOptionNumber()
        {
            return mOptionNumber;
        }

        public void setOptionNumber(int mOptionNumber)
        {
            this.mOptionNumber = mOptionNumber;
        }

    }
}
