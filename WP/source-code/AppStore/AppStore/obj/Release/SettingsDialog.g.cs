﻿

#pragma checksum "C:\Users\Srujan\Documents\Visual Studio 2015\Projects\BuildmLearnStore\AppStore\AppStore\SettingsDialog.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "7342358910CF860CDB06435D024036CE"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace AppStore
{
    partial class SettingsDialog : global::Windows.UI.Xaml.Controls.ContentDialog, global::Windows.UI.Xaml.Markup.IComponentConnector
    {
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 4.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
 
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1:
                #line 12 "..\..\SettingsDialog.xaml"
                ((global::Windows.UI.Xaml.Controls.ContentDialog)(target)).PrimaryButtonClick += this.ContentDialog_PrimaryButtonClick;
                 #line default
                 #line hidden
                #line 13 "..\..\SettingsDialog.xaml"
                ((global::Windows.UI.Xaml.Controls.ContentDialog)(target)).SecondaryButtonClick += this.ContentDialog_SecondaryButtonClick;
                 #line default
                 #line hidden
                break;
            }
            this._contentLoaded = true;
        }
    }
}


