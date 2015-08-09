using AppStore.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage.Pickers.Provider;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Content Dialog item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AppStore
{
    public sealed partial class SettingsDialog : ContentDialog
    {
        public SettingsDialog()
        {
            this.InitializeComponent();
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            featured_categories.Text = localSettings.Values["Featured_Categories"].ToString();
            featured_apps.Text=localSettings.Values["Featured_Apps"].ToString();
        }

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

        private void ContentDialog_SecondaryButtonClick(ContentDialog sender, ContentDialogButtonClickEventArgs args)
        {
        }
    }
}
