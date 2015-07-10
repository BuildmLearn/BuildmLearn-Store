using System;
using System.Collections.Generic;
using System.Text;

namespace AppStore.Models
{
    class AppList
    {
        private static AppList obAppList = null;
        public static AppList getAppList()
        {
            if (obAppList == null) obAppList= new AppList();
            return obAppList;
        }
        public List<Apps> appList;
        public AppList()
        {
            appList = new List<Apps>();
        }
    }
}
