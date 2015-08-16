using AppStore.Models;
using System;
using Windows.UI.Xaml.Controls;

// The Content Dialog item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore
{
    /// <summary>
    /// This class deals with Settings Dialog and implements all the important user-defined settings required by the app.
    /// </summary>
    public sealed partial class SettingsDialog : ContentDialog
    {
        /// <summary>
        /// Public constructor for the SettingsDialog
        /// </summary>
        public SettingsDialog()
        {
            this.InitializeComponent();
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            featured_categories.Text = localSettings.Values["Featured_Categories"].ToString();
            featured_apps.Text=localSettings.Values["Featured_Apps"].ToString();
        }

        /// <summary>
        /// Method which is executed when the user taps on the "apply" button in the SettingsDialog.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">EventArgs e is a parameter called e that contains the event data, see the EventArgs MSDN page for more information.</param>
        private void ContentDialog_PrimaryButtonClick(ContentDialog sender, ContentDialogButtonClickEventArgs args)
        {
            int fc = 4, fa = 6;
            if (Int32.TryParse(featured_categories.Text, out fc))
            {
                if (fc >= 10)
                    featured_categories.Text = 10 + "";
                if (fc <= 4)
                    featured_categories.Text = 4 + "";
            }
            else featured_categories.Text = 4 + "";
            if (Int32.TryParse(featured_apps.Text, out fa))
            {
                if (fa >= AppList.getAppList().appList.Count)
                    featured_apps.Text = AppList.getAppList().appList.Count + "";
                if (fa <= 6)
                    featured_apps.Text = 6 + "";
            }
            else featured_apps.Text = 6 + "";
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            localSettings.Values["Featured_Categories"] = featured_categories.Text;
            localSettings.Values["Featured_Apps"] = featured_apps.Text;
        }
        
        /// <summary>
        /// Method which is executed when the user taps on the "cancel" button in the SettingsDialog
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="args">EventArgs e is a parameter called e that contains the event data, see the EventArgs MSDN page for more information.</param>
        private void ContentDialog_SecondaryButtonClick(ContentDialog sender, ContentDialogButtonClickEventArgs args)
        {
        }
    }
}
