namespace MVC.Services;

public interface IFirebaseService
{
    public Task SendMessage(string content, string title, string token);
}