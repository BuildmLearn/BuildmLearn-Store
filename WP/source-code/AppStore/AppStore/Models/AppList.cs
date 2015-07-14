using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Text;

namespace AppStore.Models
{
    class AppList
    {
        private static AppList obAppList = null;
        public static AppList getAppList()
        {
            if (obAppList == null) obAppList = new AppList();
            return obAppList;
        }
        public List<Apps> myappList;
        public static AppList getMyAppList()
        {
            if (obAppList == null) obAppList = new AppList();
            return obAppList;
        }
        public List<Apps> appList;
        public AppList()
        {
            appList = new List<Apps>();
            myappList = new List<Apps>();
        }        
    }
}
