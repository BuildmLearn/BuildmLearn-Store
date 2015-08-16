using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Flash card App-Template
    /// </summary>
    class FlashModel
    {
        private string mFlashName;
        private string mFlashAuthor;
        private string mFlashVersion;
        private string mFlashDescription;
        private string mFlashAuthorEmail;
        private List<Card> mCardList;
        public static FlashModel mFlashModel;

        /// <summary>
        /// Gets the Instance of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the Instance of the Flash-Card App-Template</returns>
        public static FlashModel getInstance()
        {
            if (mFlashModel == null)
                mFlashModel = new FlashModel();
            return mFlashModel;

        }
        /// <summary>
        /// Gets the Author of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the Author of the Flash-Card App-Template</returns>
        public string getFlashAuthor()
        {
            return mFlashAuthor;
        }
        /// <summary>
        /// Sets the Author of the Flash-Card App-Template
        /// </summary>
        /// <param name="mFlashAuthor">Author of the Flash-Card App-Template</param>
        public void setFlashAuthor(string mFlashAuthor)
        {
            this.mFlashAuthor = mFlashAuthor;
        }
        /// <summary>
        /// Gets the Name of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the Name of the Flash-Card App-Template</returns>
        public string getFlashName()
        {
            return mFlashName;
        }
        /// <summary>
        /// Sets the Name of the Flash-Card App-Template
        /// </summary>
        /// <param name="mFlashName">Name of the Flash-Card App-Template</param>
        public void setFlashName(string mFlashName)
        {
            this.mFlashName = mFlashName;
        }
        /// <summary>
        /// Gets the CardList of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the CardList of the Flash-Card App-Template</returns>
        public List<Card> getCardList()
        {
            return mCardList;
        }
        /// <summary>
        /// Sets the CardList of the Flash-Card App-Template
        /// </summary>
        /// <param name="mCardList">CardList of the Flash-Card App-Template</param>
        public void setCardList(List<Card> mCardList)
        {
            this.mCardList = mCardList;
        }
        /// <summary>
        /// Gets the Description of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the Description of the Flash-Card App-Template</returns>
        public string getFlashDescription()
        {
            return mFlashDescription;
        }
        /// <summary>
        /// Sets the Description of the Flash-Card App-Template
        /// </summary>
        /// <param name="mFlashDescription">Description of the Flash-Card App-Template</param>
        public void setFlashDescription(string mFlashDescription)
        {
            this.mFlashDescription = mFlashDescription;
        }
        /// <summary>
        /// Gets the Author-Email of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the Author_Email of the Flash-Card App-Template</returns>
        public string getFlashAuthorEmail()
        {
            return mFlashAuthorEmail;
        }
        /// <summary>
        /// Sets the AuthorEmail of the Flash-Card App-Template
        /// </summary>
        /// <param name="mFlashAuthorEmail">Author Email of the Flash-Card App-Template</param>
        public void setFlashAuthorEmail(string mFlashAuthorEmail)
        {
            this.mFlashAuthorEmail = mFlashAuthorEmail;
        }
        /// <summary>
        /// Gets the version of the Flash-Card App-Template
        /// </summary>
        /// <returns>Returns the Version of the Flash-Card App-Template</returns>
        public string getFlashVersion()
        {
            return mFlashVersion;
        }
        /// <summary>
        /// Sets the Version of the Flash-Card App-Template
        /// </summary>
        /// <param name="mFlashVersion">Version of the Flash-Card App-Template</param>
        public void setFlashVersion(string mFlashVersion)
        {
            this.mFlashVersion = mFlashVersion;
        }
    }
}
