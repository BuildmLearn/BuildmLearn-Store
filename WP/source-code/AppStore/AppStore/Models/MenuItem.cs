namespace AppStore.Models
{
    /// <summary>
    /// It is model for the menu items.
    /// </summary>
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
