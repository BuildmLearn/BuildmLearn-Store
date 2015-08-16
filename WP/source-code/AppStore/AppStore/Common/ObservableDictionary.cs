using System;
using System.Collections.Generic;
using System.Linq;
using Windows.Foundation.Collections;

namespace AppStore.Common
{
    /// <summary>
    /// Implementation of IObservableMap that supports reentrancy for use as a default view
    /// model.
    /// </summary>
    public class ObservableDictionary : IObservableMap<string, object>
    {
        /// <summary>
        /// ObservableDictionaryChangedEventArgs
        /// </summary>
        private class ObservableDictionaryChangedEventArgs : IMapChangedEventArgs<string>
        {
            public ObservableDictionaryChangedEventArgs(CollectionChange change, string key)
            {
                this.CollectionChange = change;
                this.Key = key;
            }

            public CollectionChange CollectionChange { get; private set; }
            public string Key { get; private set; }
        }

        private Dictionary<string, object> _dictionary = new Dictionary<string, object>();
        
        /// <summary>
        /// Handler to event if the app is changed.
        /// </summary>
        public event MapChangedEventHandler<string, object> MapChanged;

        /// <summary>
        /// Invokes the change in the map.
        /// </summary>
        /// <param name="change">CollectionChange object</param>
        /// <param name="key">String key</param>
        private void InvokeMapChanged(CollectionChange change, string key)
        {
            var eventHandler = MapChanged;
            if (eventHandler != null)
            {
                eventHandler(this, new ObservableDictionaryChangedEventArgs(change, key));
            }
        }

        /// <summary>
        /// Adds the collection of key and value to the list.
        /// </summary>
        /// <param name="key">String key</param>
        /// <param name="value">String value</param>
        public void Add(string key, object value)
        {
            this._dictionary.Add(key, value);
            this.InvokeMapChanged(CollectionChange.ItemInserted, key);
        }

        /// <summary>
        /// Adds the item to the list.
        /// </summary>
        /// <param name="item">KeyValuePair object</param>
        public void Add(KeyValuePair<string, object> item)
        {
            this.Add(item.Key, item.Value);
        }

        /// <summary>
        /// Removes the item with the key from the list.
        /// </summary>
        /// <param name="key">String key</param>
        /// <returns>True:If it is removed, False:If it is not</returns>
        public bool Remove(string key)
        {
            if (this._dictionary.Remove(key))
            {
                this.InvokeMapChanged(CollectionChange.ItemRemoved, key);
                return true;
            }
            return false;
        }

        /// <summary>
        /// Removes the item with the item from the list.
        /// </summary>
        /// <param name="item">KeyValuePair object</param>
        /// <returns>True:If it is removed, False:If it is not</returns>
        public bool Remove(KeyValuePair<string, object> item)
        {
            object currentValue;
            if (this._dictionary.TryGetValue(item.Key, out currentValue) &&
                Object.Equals(item.Value, currentValue) && this._dictionary.Remove(item.Key))
            {
                this.InvokeMapChanged(CollectionChange.ItemRemoved, item.Key);
                return true;
            }
            return false;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="key">String key</param>
        /// <returns></returns>
        public object this[string key]
        {
            get
            {
                return this._dictionary[key];
            }
            set
            {
                this._dictionary[key] = value;
                this.InvokeMapChanged(CollectionChange.ItemChanged, key);
            }
        }

        /// <summary>
        /// Clears the list
        /// </summary>
        public void Clear()
        {
            var priorKeys = this._dictionary.Keys.ToArray();
            this._dictionary.Clear();
            foreach (var key in priorKeys)
            {
                this.InvokeMapChanged(CollectionChange.ItemRemoved, key);
            }
        }

        /// <summary>
        /// Constructor of the Collection
        /// </summary>
        public ICollection<string> Keys
        {
            get { return this._dictionary.Keys; }
        }

        /// <summary>
        /// Checks whether the list contains the key.
        /// </summary>
        /// <param name="key">String key</param>
        /// <returns>True:if the list contains the key, false otherwise. </returns>
        public bool ContainsKey(string key)
        {
            return this._dictionary.ContainsKey(key);
        }

        /// <summary>
        /// Tries to get the value from the list with the key to the variable value.
        /// </summary>
        /// <param name="key">string key</param>
        /// <param name="value">Object variable where the list item is returned to</param>
        /// <returns>True: if the it gets the item, false otherwise.</returns>
        public bool TryGetValue(string key, out object value)
        {
            return this._dictionary.TryGetValue(key, out value);
        }

        /// <summary>
        /// Constructor of the collection of the values.
        /// </summary>
        public ICollection<object> Values
        {
            get { return this._dictionary.Values; }
        }

        /// <summary>
        /// Checks whether the collection contains the item.
        /// </summary>
        /// <param name="item">KeyValuePair item</param>
        /// <returns>True: if the list contains the item, false otherwise.</returns>
        public bool Contains(KeyValuePair<string, object> item)
        {
            return this._dictionary.Contains(item);
        }

        /// <summary>
        /// Returns the count of the KeyValuePair Colection.
        /// </summary>
        public int Count
        {
            get { return this._dictionary.Count; }
        }

        /// <summary>
        /// Returns whether the collection is ReadOnly.
        /// </summary>
        public bool IsReadOnly
        {
            get { return false; }
        }

        /// <summary>
        /// Gets the Enumerator of the Collection.
        /// </summary>
        /// <returns>Enumerator of the KeyValuePair</returns>
        public IEnumerator<KeyValuePair<string, object>> GetEnumerator()
        {
            return this._dictionary.GetEnumerator();
        }

        /// <summary>
        /// Gets the Enumerator of the Collection.
        /// </summary>
        /// <returns>Enumerator of the KeyValuePair</returns>
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this._dictionary.GetEnumerator();
        }

        /// <summary>
        /// It copies the array from the list with the arrayIndex.
        /// </summary>
        /// <param name="array">KeyValuePair object</param>
        /// <param name="arrayIndex">Integer index</param>
        public void CopyTo(KeyValuePair<string, object>[] array, int arrayIndex)
        {
            int arraySize = array.Length;
            foreach (var pair in this._dictionary)
            {
                if (arrayIndex >= arraySize) break;
                array[arrayIndex++] = pair;
            }
        }
    }
}
