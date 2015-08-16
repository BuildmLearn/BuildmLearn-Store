using System;
using Windows.ApplicationModel.DataTransfer;
using Windows.Foundation;

namespace AppStore.Common
{
    /// <summary>
    /// Common class for the App.
    /// </summary>
    class AppCommon
    {
        /// <summary>
        /// It composes the email from the default app-client installed in the phone.
        /// </summary>
        public static async void ComposeEmail()
        {
            var emailMessage = new Windows.ApplicationModel.Email.EmailMessage();
            emailMessage.Body = "I liked the following features in this app:";
            var emailRecipient = new Windows.ApplicationModel.Email.EmailRecipient("srujanjha.jha@gmail.com");
            emailMessage.To.Add(emailRecipient);
            emailMessage.CC.Add(emailRecipient);
            await Windows.ApplicationModel.Email.EmailManager.ShowComposeNewEmailAsync(emailMessage);
        }

        /// <summary>
        /// It builds the framework for the sharing purposes.
        /// </summary>
        public static void RegisterForShare()
        {
            DataTransferManager dataTransferManager = DataTransferManager.GetForCurrentView();
            dataTransferManager.DataRequested += new TypedEventHandler<DataTransferManager, DataRequestedEventArgs>(ShareTextHandler);
        }

        /// <summary>
        /// This is the text handler for the sharing purposes.
        /// </summary>
        /// <param name="sender">Object Sender is a parameter called Sender that contains a reference to the control/object that raised the event.</param>
        /// <param name="e">DataRequestedEventArgs e is a parameter called e that contains the event data, see the DataRequestedEventArgs MSDN page for more information.</param>
        public static void ShareTextHandler(DataTransferManager sender, DataRequestedEventArgs e)
        {
            DataRequest request = e.Request;
            request.Data.Properties.Title = "BuildmLearn Store";
            request.Data.Properties.Description = "BuildmLearn Store Share";
            request.Data.SetText("Share Text");
        }

    }
}
