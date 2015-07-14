using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class MenuItem
    {
        public string Icon { get; set; }
        public string Title { get; set; }
        public MenuItem(string mTitle,string mIcon)
        {
            Icon = mIcon;
            Title = mTitle;
        }
    }
}
