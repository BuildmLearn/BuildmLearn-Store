﻿

#pragma checksum "C:\Users\Srujan\Documents\Visual Studio 2015\Projects\BuildmLearnStore\AppStore\AppStore\Templates\WordInfoPage.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "25C6C4CF86C17EF78242562FFFCED8C0"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace AppStore.Templates
{
    partial class WordInfoPage : global::Windows.UI.Xaml.Controls.Page, global::Windows.UI.Xaml.Markup.IComponentConnector
    {
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 4.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
 
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1:
                #line 35 "..\..\Templates\WordInfoPage.xaml"
                ((global::Windows.UI.Xaml.Controls.TextBox)(target)).TextChanged += this.Word_TextChanged;
                 #line default
                 #line hidden
                break;
            case 2:
                #line 36 "..\..\Templates\WordInfoPage.xaml"
                ((global::Windows.UI.Xaml.Controls.Primitives.ButtonBase)(target)).Click += this.Enter_Click;
                 #line default
                 #line hidden
                break;
            case 3:
                #line 43 "..\..\Templates\WordInfoPage.xaml"
                ((global::Windows.UI.Xaml.Controls.Primitives.ButtonBase)(target)).Click += this.Back_Click;
                 #line default
                 #line hidden
                break;
            case 4:
                #line 44 "..\..\Templates\WordInfoPage.xaml"
                ((global::Windows.UI.Xaml.Controls.Primitives.ButtonBase)(target)).Click += this.Next_Click;
                 #line default
                 #line hidden
                break;
            }
            this._contentLoaded = true;
        }
    }
}


