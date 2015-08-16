using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is model which contains the list of all the apps both installed and else.
    /// </summary>
    class AppList
    {
        private static AppList obAppList = null;
        public List<Apps> myappList;
        public List<Apps> appList;

        /// <summary>
        /// Gets the list of all the apps in BuildmLearn Store.
        /// </summary>
        /// <returns>Returns the AppList</returns>
        public static AppList getAppList()
        {
            if (obAppList == null) obAppList = new AppList();
            return obAppList;
        }
        
        /// <summary>
        /// Gets the list of all the installed apps form BuildmLearn Store.
        /// </summary>
        /// <returns></returns>
        public static AppList getMyAppList()
        {
            if (obAppList == null) obAppList = new AppList();
            return obAppList;
        }
        
        /// <summary>
        /// Public Constructor
        /// </summary>
        public AppList()
        {
            appList = new List<Apps>();
            myappList = new List<Apps>();
        }        
    }
}
