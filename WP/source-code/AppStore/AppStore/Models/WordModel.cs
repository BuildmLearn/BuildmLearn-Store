using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class WordModel
    {
        private string mWord;
        private string mDescription;
        public WordModel(string word, string description)
        {
            mWord = word;
            mDescription = description;

        }
        public string getWord()
        {
            return mWord;
        }
        public void setWord(string mWord)
        {
            this.mWord = mWord;
        }
        public string getDescription()
        {
            return mDescription;
        }
        public void setDescription(string mDescription)
        {
            this.mDescription = mDescription;
        }
    }
}
