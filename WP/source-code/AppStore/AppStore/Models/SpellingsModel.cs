using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Spellings App-Template.
    /// </summary>
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
    
        /// <summary>
        /// Clears the instance of the SpellingsModel
        /// </summary>
        public static void clearInstance()
        {
            if (mSpellingsModel != null)
            {
                mSpellingsModel.totalCorrect = 0;
                mSpellingsModel.totalWrong = 0;
                mSpellingsModel.activeCount = 0;
            }
        }
   
        /// <summary>
        /// Gets the instance of the SpellingsModel
        /// </summary>
        /// <returns>Instance of SpellingsModel</returns>
        public static SpellingsModel getInstance()
        {
            if (mSpellingsModel == null)
                mSpellingsModel = new SpellingsModel();
            return mSpellingsModel;
        }
    
        /// <summary>
        /// Gets the name of the Puzzle
        /// </summary>
        /// <returns>Name of the Puzzle</returns>
        public string getPuzzleName()
        {
            return mPuzzleName;
        }

        /// <summary>
        /// Sets the name of the puzzle
        /// </summary>
        /// <param name="mPuzzleName">Name of the puzzle</param>
        public void setPuzzleName(string mPuzzleName)
        {
            this.mPuzzleName = mPuzzleName;
        }
     
        /// <summary>
        /// Gets the description of the puzzle
        /// </summary>
        /// <returns>Description of the puzzle</returns>
        public string getPuzzleDescription()
        {
            return mPuzzleDescription;
        }

        /// <summary>
        /// Sets the description of the puzzle
        /// </summary>
        /// <param name="mPuzzleDescription">Description of the Puzzle</param>
        public void setPuzzleDescription(string mPuzzleDescription)
        {
            this.mPuzzleDescription = mPuzzleDescription;
        }
     
        /// <summary>
        /// Gets the AuthorEmail of the puzzle
        /// </summary>
        /// <returns>AuthorEmail of the puzzle</returns>
        public string getPuzzleAuthorEmail()
        {
            return mPuzzleAuthorEmail;
        }

        /// <summary>
        /// Sets the AuthorEmail of the puzzle
        /// </summary>
        /// <param name="mPuzzleAuthorEmail">AuthorEmail of the Puzzle</param>
        public void setPuzzleAuthorEmail(string mPuzzleAuthorEmail)
        {
            this.mPuzzleAuthorEmail = mPuzzleAuthorEmail;
        }
    
        /// <summary>
        /// Gets the version of the puzzle
        /// </summary>
        /// <returns>Version of the Puzzle</returns>
        public string getPuzzleVersion()
        {
            return mPuzzleVersion;
        }

        /// <summary>
        /// Sets the version of the puzzle
        /// </summary>
        /// <param name="mPuzzleVersion">Version of the puzzle</param>
        public void setPuzzleVersion(string mPuzzleVersion)
        {
            this.mPuzzleVersion = mPuzzleVersion;
        }

        /// <summary>
        /// Gets the author of the Puzzle
        /// </summary>
        /// <returns>Author of the Puzzle</returns>
        public string getPuzzleAuthor()
        {
            return mPuzzleAuthor;
        }

        /// <summary>
        /// Sets the author of the puzzle
        /// </summary>
        /// <param name="mPuzzleAuthor">Author of the puzzle</param>
        public void setPuzzleAuthor(string mPuzzleAuthor)
        {
            this.mPuzzleAuthor = mPuzzleAuthor;
        }

        /// <summary>
        /// Gets the SpellingsList of the puzzle
        /// </summary>
        /// <returns>SpellingsList of the Puzzle</returns>
        public List<WordModel> getSpellingsList()
        {
            return mSpellingsList;
        }

        /// <summary>
        /// Sets the SpellingsList of the puzzle
        /// </summary>
        /// <param name="mSpellingsList">SpellingsList of the Puzzle</param>
        public void setSpellingsList(List<WordModel> mSpellingsList)
        {
            this.mSpellingsList = mSpellingsList;
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
        /// Sets the total number of the correct answers
        /// </summary>
        /// <param name="totalCorrect">Total number of the correct answers</param>
        public void setTotalCorrect(int totalCorrect)
        {
            this.totalCorrect = totalCorrect;
        }

        /// <summary>
        /// Gets the total number of the wrong answers
        /// </summary>
        /// <returns>Total number of the wrong answers</returns>
        public int getTotalWrong()
        {
            return totalWrong;
        }

        /// <summary>
        /// Sets the total number of the wrong answers
        /// </summary>
        /// <param name="totalWrong"></param>
        public void setTotalWrong(int totalWrong)
        {
            this.totalWrong = totalWrong;
        }

        /// <summary>
        /// Gets the cuurent index
        /// </summary>
        /// <returns>Current index of the question</returns>
        public int getActiveCount()
        {
            return activeCount;
        }

        /// <summary>
        /// Sets the active index
        /// </summary>
        /// <param name="activeCount">Current index of the question</param>
        public void setActiveCount(int activeCount)
        {
            this.activeCount = activeCount;
        }
    }
}
