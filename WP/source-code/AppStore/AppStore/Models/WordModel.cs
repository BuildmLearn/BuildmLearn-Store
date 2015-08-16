namespace AppStore.Models
{
    /// <summary>
    /// It is model for the Spelings App-Template.
    /// </summary>
    class WordModel
    {
        private string mWord;
        private string mDescription;
    
        /// <summary>
        /// Public Construtor to the model.
        /// </summary>
        /// <param name="word">String Word</param>
        /// <param name="description">String Description</param>
        public WordModel(string word, string description)
        {
            mWord = word;
            mDescription = description;

        }
      
        /// <summary>
        /// Gets the word with the WordModel object.
        /// </summary>
        /// <returns>Returns the word</returns>
        public string getWord()
        {
            return mWord;
        }
    
        /// <summary>
        /// Sets the word to the WordModel object.
        /// </summary>
        /// <param name="mWord">String word</param>
        public void setWord(string mWord)
        {
            this.mWord = mWord;
        }
    
        /// <summary>
        /// Gets the description with the WordModel object.
        /// </summary>
        /// <returns></returns>
        public string getDescription()
        {
            return mDescription;
        }
      
        /// <summary>
        /// Sets the description to the WordModel object.
        /// </summary>
        /// <param name="mDescription">String description</param>
        public void setDescription(string mDescription)
        {
            this.mDescription = mDescription;
        }
    }
}
