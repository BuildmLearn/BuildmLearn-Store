using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.DataTransfer;
using Windows.Foundation;

namespace AppStore.Common
{
    class AppCommon
    {
        public static async void ComposeEmail()
        {
            var emailMessage = new Windows.ApplicationModel.Email.EmailMessage();
            emailMessage.Body = "I liked the following features in this app:";
            var emailRecipient = new Windows.ApplicationModel.Email.EmailRecipient("srujanjha.jha@gmail.com");
            emailMessage.To.Add(emailRecipient);
            emailMessage.CC.Add(emailRecipient);
            await Windows.ApplicationModel.Email.EmailManager.ShowComposeNewEmailAsync(emailMessage);
        }

        public static void RegisterForShare()
        {
            DataTransferManager dataTransferManager = DataTransferManager.GetForCurrentView();
            dataTransferManager.DataRequested += new TypedEventHandler<DataTransferManager, DataRequestedEventArgs>(ShareTextHandler);
        }

        public static void ShareTextHandler(DataTransferManager sender, DataRequestedEventArgs e)
        {
            DataRequest request = e.Request;
            request.Data.Properties.Title = "BuildmLearn Store";
            request.Data.Properties.Description = "BuildmLearn Store Share";
            request.Data.SetText("Share Text");
        }

    }
}
