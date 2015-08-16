namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Categories
    /// </summary>
    class Categories
    {
        public string Background { get; set; }
        public string Name { get; set; }
        /// <summary>
        /// Public Constructor
        /// </summary>
        /// <param name="mName">Title of the Category</param>
        /// <param name="mBackground">Background to be used for the tile of this Category</param>
        public Categories(string mName, string mBackground)
        {
            Name = mName;
            Background = mBackground;
        }
    }
}
