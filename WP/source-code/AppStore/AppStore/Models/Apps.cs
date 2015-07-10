using System;
using System.Collections.Generic;
using System.Text;

namespace AppStore.Models
{
    class Apps
    {
        public string Name { get; set; }
        public string Author { get; set; }
        public string AuthorEmail { get; set; }
        public string Description { get; set; }
        public string AppIcon { get; set; }
        public string Category { get; set; }
        public string []Screenshots { get; set; }
        public string Type { get; set; }
        public Apps(string name,string description, string author, string authoremail,string appicon,string []screenshots,string type,string category)
        {
            Name = name.Trim();
            Description = description.Trim();
            Author = author.Trim();
            AuthorEmail = authoremail.Trim();
            AppIcon = appicon.Trim();
            Screenshots = screenshots;
            Category = category.Trim();
            Type = type.Trim();
        }

    }
}
