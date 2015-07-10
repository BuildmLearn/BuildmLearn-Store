using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppStore.Models
{
    class Categories
    {
        public string Background { get; set; }
        public string Name { get; set; }
        public Categories(string mName, string mBackground)
        {
            Name = mName;
            Background = mBackground;
        }
    }
}
