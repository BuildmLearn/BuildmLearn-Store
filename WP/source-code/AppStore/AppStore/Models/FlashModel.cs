using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class FlashModel
    {
        private string mFlashName;
        private string mFlashAuthor;
        private string mFlashVersion;
        private string mFlashDescription;
        private string mFlashAuthorEmail;
        private List<Card> mCardList;
        public static FlashModel mFlashModel;

        public static FlashModel getInstance()
        {
            if (mFlashModel == null)
                mFlashModel = new FlashModel();
            return mFlashModel;

        }
        public string getFlashAuthor()
        {
            return mFlashAuthor;
        }
        public void setFlashAuthor(string mFlashAuthor)
        {
            this.mFlashAuthor = mFlashAuthor;
        }
        public string getFlashName()
        {
            return mFlashName;
        }
        public void setFlashName(string mFlashName)
        {
            this.mFlashName = mFlashName;
        }
        public List<Card> getCardList()
        {
            return mCardList;
        }
        public void setCardList(List<Card> mCardList)
        {
            this.mCardList = mCardList;
        }
        public string getFlashDescription()
        {
            return mFlashDescription;
        }
        public void setFlashDescription(string mFlashDescription)
        {
            this.mFlashDescription = mFlashDescription;
        }
        public string getFlashAuthorEmail()
        {
            return mFlashAuthorEmail;
        }
        public void setFlashAuthorEmail(string mFlashAuthorEmail)
        {
            this.mFlashAuthorEmail = mFlashAuthorEmail;
        }
        public string getFlashVersion()
        {
            return mFlashVersion;
        }
        public void setFlashVersion(string mFlashVersion)
        {
            this.mFlashVersion = mFlashVersion;
        }
    }
}
