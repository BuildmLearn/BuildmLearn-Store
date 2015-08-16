using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Info App-Template.
    /// </summary>
    class InfoModel
    {
        private string mInfoName;
        private string mInfoVersion;
        private string mInfoDescription;
        private string mInfoAuthor;
        private string mInfoAuthorEmail;
        private List<string> mInfoTitleList=new List<string>();
        private List<string> mInfoDescriptionList=new List<string>();
        public static InfoModel mModel;

        /// <summary>
        /// Gets the object for InfoModel.
        /// </summary>
        /// <returns></returns>
        public static InfoModel getInstance()
        {
            if (mModel == null)
                mModel = new InfoModel();
            return mModel;
        }

        /// <summary>
        /// Gets the InfoDescription List.
        /// </summary>
        /// <returns></returns>
        public List<string> getInfoDescriptionList()
        {
            return mInfoDescriptionList;
        }

        /// <summary>
        /// Sets the InfoDescription List.
        /// </summary>
        /// <param name="mInfoDescriptionList"></param>
        public void setInfoDescriptionList(List<string> mInfoDescriptionList)
        {
            this.mInfoDescriptionList = mInfoDescriptionList;
        }

        /// <summary>
        /// Gets the Author of the Info App-Template
        /// </summary>
        /// <returns>Returns the Author of the Info App-Template</returns>
        public string getInfoAuthor()
        {
            return mInfoAuthor;
        }

        /// <summary>
        /// Sets the AuthorEmail of the Info App-Template
        /// </summary>
        /// <param name="mInfoAuthorEmail">Email fo the Author</param>
        public void setInfoAuthorEmail(string mInfoAuthorEmail)
        {
            this.mInfoAuthorEmail = mInfoAuthorEmail;
        }
        /// <summary>
        /// Gets the Description of the Info App-Template
        /// </summary>
        /// <returns>Returns the Description</returns>
        public string getInfoDescription()
        {
            return mInfoDescription;
        }
        /// <summary>
        /// Sets the Description of the Info App-Template
        /// </summary>
        /// <param name="mInfoDescription"></param>
        public void setInfoDescription(string mInfoDescription)
        {
            this.mInfoDescription = mInfoDescription;
        }
        /// <summary>
        /// Gets the Email of the Author of the Info App-Template
        /// </summary>
        /// <returns>Returns the InfoAuthorEmail</returns>
        public string getInfoAuthorEmail()
        {
            return mInfoAuthorEmail;
        }
        /// <summary>
        /// Sets the Author of the Info App-Template
        /// </summary>
        /// <param name="mInfoAuthor">Author of the Info App-Template</param>
        public void setInfoAuthor(string mInfoAuthor)
        {
            this.mInfoAuthor = mInfoAuthor;
        }
        /// <summary>
        /// Gets the Name of the Info App-Template
        /// </summary>
        /// <returns></returns>
        public string getInfoName()
        {
            return mInfoName;
        }
        /// <summary>
        /// Sets the name of the Info App-Tmeplate
        /// </summary>
        /// <param name="mInfoName">Name of the Info App-Template</param>
        public void setInfoName(string mInfoName)
        {
            this.mInfoName = mInfoName;
        }
        /// <summary>
        /// Gets the version of the Info App-Template
        /// </summary>
        /// <returns>Returns the version of the Info App-Template</returns>
        public string getInfoVersion()
        {
            return mInfoVersion;
        }
        /// <summary>
        /// Sets the InfoVersion
        /// </summary>
        /// <param name="mInfoVersion">Version of the Info App-Template</param>
        public void setInfoVersion(string mInfoVersion)
        {
            this.mInfoVersion = mInfoVersion;
        }
        /// <summary>
        /// Gets the InfoTitleList
        /// </summary>
        /// <returns>Return the list of the InfoTitle</returns>
        public List<string> getInfoTitleList()
        {
            return mInfoTitleList;
        }
        /// <summary>
        /// Sets the InfoTitle List
        /// </summary>
        /// <param name="mInfoTitleList">List of the Title List.</param>
        public void setInfoTitleList(List<string> mInfoTitleList)
        {
            this.mInfoTitleList = mInfoTitleList;
        }

    }
}
