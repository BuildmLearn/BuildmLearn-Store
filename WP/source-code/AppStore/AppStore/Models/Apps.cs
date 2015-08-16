namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Info App-Template.
    /// </summary>
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

        /// <summary>
        /// Public Constructor
        /// </summary>
        /// <param name="name">Name of the app</param>
        /// <param name="description">Description of the app</param>
        /// <param name="author">Author of the app</param>
        /// <param name="authoremail">AuthorEmail </param>
        /// <param name="appicon">AppIcon</param>
        /// <param name="screenshots">Screenshots</param>
        /// <param name="type">Type of the app</param>
        /// <param name="category">Category of the app</param>
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
