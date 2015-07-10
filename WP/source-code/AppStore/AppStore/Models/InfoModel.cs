using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class InfoModel
    {
        private string mInfoName;
        private string mInfoVersion;
        private string mInfoDescription;
        private string mInfoAuthor;
        private string mInfoAuthorEmail;
        private List<string> mListTitleList=new List<string>();
        private List<string> mInfoDescriptionList=new List<string>();
        public static InfoModel mModel;
        public static InfoModel getInstance()
        {
            if (mModel == null)
                mModel = new InfoModel();
            return mModel;
        }
        public List<string> getInfoDescriptionList()
        {
            return mInfoDescriptionList;
        }
        public void setInfoDescriptionList(List<string> mInfoDescriptionList)
        {
            this.mInfoDescriptionList = mInfoDescriptionList;
        }
        public string getInfoAuthor()
        {
            return mInfoAuthor;
        }
        public void setInfoAuthorEmail(string mInfoAuthorEmail)
        {
            this.mInfoAuthorEmail = mInfoAuthorEmail;
        }
        public string getInfoDescription()
        {
            return mInfoDescription;
        }
        public void setInfoDescription(string mInfoDescription)
        {
            this.mInfoDescription = mInfoDescription;
        }
        public string getInfoAuthorEmail()
        {
            return mInfoAuthorEmail;
        }
        public void setInfoAuthor(string mInfoAuthor)
        {
            this.mInfoAuthor = mInfoAuthor;
        }
        public string getInfoName()
        {
            return mInfoName;
        }
        public void setInfoName(string mInfoName)
        {
            this.mInfoName = mInfoName;
        }
        public string getInfoVersion()
        {
            return mInfoVersion;
        }
        public void setInfoVersion(string mInfoVersion)
        {
            this.mInfoVersion = mInfoVersion;
        }
        public List<string> getListTitleList()
        {
            return mListTitleList;
        }
        public void setListTitleList(List<string> mListTitleList)
        {
            this.mListTitleList = mListTitleList;
        }

    }
}
