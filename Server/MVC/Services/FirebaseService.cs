using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace MVC.Services;

public class FirebaseService : IFirebaseService
{
    private readonly FirebaseApp _firebaseApp;

    public FirebaseService()
    {
        _firebaseApp = FirebaseApp.Create(new AppOptions()
        {
            Credential = GoogleCredential.FromFile("privatekey.json")
                .CreateScoped("https://www.googleapis.com/auth/firebase.messaging")
        });
    }
    
    public async Task SendMessage(string content, string title, string token)
    {
        FirebaseMessaging messaging = FirebaseMessaging.GetMessaging(_firebaseApp);
        await messaging.SendAsync(new FirebaseAdmin.Messaging.Message
            {
                Notification = new Notification()
                {
                    Body = content,
                    Title = title,
                    
                },
                Token = token
        });
    }
}