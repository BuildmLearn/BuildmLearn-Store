using System.Collections.Generic;

namespace AppStore.Models
{
    /// <summary>
    /// It is model for the all the string resources for catefories.
    /// </summary>
    class Resources
    {
        public static string[] CategoryName = { "Science", "Mathematics", "Literature", "SocialStudies", "History", "Geography", "English", "Physics", "Chemistry", "Biology" };
        public static string[] CategoryImage ={"ms-appx:///Assets/card_science.png","ms-appx:///Assets/card_mathematics.png","ms-appx:///Assets/card_literature.png","ms-appx:///Assets/card_socialstudies.png",
            "ms-appx:///Assets/card_history.png","ms-appx:///Assets/card_geography.png","ms-appx:///Assets/card_english.png","ms-appx:///Assets/card_physics.png","ms-appx:///Assets/card_chemistry.png","ms-appx:///Assets/card_biology.png"};
        
        /// <summary>
        /// Gets the list of Categories List
        /// </summary>
        /// <returns></returns>
        public static List<Categories> getCategoriesList()
        {
            List<Categories> list = new List<Categories>();
            for (int i = 0; i < CategoryName.Length; i++)
            {
                Categories ob = new Categories(CategoryName[i], CategoryImage[i]);
                list.Add(ob);
            }
            return list;
        }
    }
}
