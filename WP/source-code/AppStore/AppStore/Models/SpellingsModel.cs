using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class SpellingsModel
    {
        private string mPuzzleName;
        private string mPuzzleAuthor;
        private string mPuzzleAuthorEmail;
        private string mPuzzleVersion;
        private string mPuzzleDescription;
        private List<WordModel> mSpellingsList;
        private int totalCorrect = 0, totalWrong = 0, activeCount = 0;


        public static SpellingsModel mSpellingsModel;
        public static void clearInstance()
        {
            if (mSpellingsModel != null)
            {
                mSpellingsModel.totalCorrect = 0;
                mSpellingsModel.totalWrong = 0;
                mSpellingsModel.activeCount = 0;
            }
        }
        public static SpellingsModel getInstance()
        {
            if (mSpellingsModel == null)
                mSpellingsModel = new SpellingsModel();
            return mSpellingsModel;
        }

        public string getPuzzleName()
        {
            return mPuzzleName;
        }

        public void setPuzzleName(string mPuzzleName)
        {
            this.mPuzzleName = mPuzzleName;
        }
        public string getPuzzleDescription()
        {
            return mPuzzleDescription;
        }

        public void setPuzzleDescription(string mPuzzleDescription)
        {
            this.mPuzzleDescription = mPuzzleDescription;
        }
        public string getPuzzleAuthorEmail()
        {
            return mPuzzleAuthorEmail;
        }

        public void setPuzzleAuthorEmail(string mPuzzleAuthorEmail)
        {
            this.mPuzzleAuthorEmail = mPuzzleAuthorEmail;
        }
        public string getPuzzleVersion()
        {
            return mPuzzleVersion;
        }

        public void setPuzzleVersion(string mPuzzleVersion)
        {
            this.mPuzzleVersion = mPuzzleVersion;
        }

        public string getPuzzleAuthor()
        {
            return mPuzzleAuthor;
        }

        public void setPuzzleAuthor(string mPuzzleAuthor)
        {
            this.mPuzzleAuthor = mPuzzleAuthor;
        }

        public List<WordModel> getSpellingsList()
        {
            return mSpellingsList;
        }

        public void setSpellingsList(List<WordModel> mSpellingsList)
        {
            this.mSpellingsList = mSpellingsList;
        }

        public int getTotalCorrect()
        {
            return totalCorrect;
        }

        public void setTotalCorrect(int totalCorrect)
        {
            this.totalCorrect = totalCorrect;
        }

        public int getTotalWrong()
        {
            return totalWrong;
        }

        public void setTotalWrong(int totalWrong)
        {
            this.totalWrong = totalWrong;
        }

        public int getActiveCount()
        {
            return activeCount;
        }

        public void setActiveCount(int activeCount)
        {
            this.activeCount = activeCount;
        }
    }
}
