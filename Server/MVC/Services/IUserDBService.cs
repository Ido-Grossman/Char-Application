using MVC.Models;

namespace MVC.Services;

public interface IUserDBService
{
    public Task<List<User>> GetAll();

    public Task<User?> Get(string name);

    public Task<ICollection<Contact>?> GetContacts(string userName);
        
    public Task<Contact?> GetContact(string userName, string friendName);

    public Task AddContact(string userName, string id, string name, string server);

    public void RemoveContact(string userName, string friendName);

    public Task<ICollection<Message>?> GetMessages(string userName, string friendName);
        
    public Task<Message?> GetMessage(string userName, string friendName, int messageId);

    public Task<Message?> AddMessage(string userName, string friendName, string message, bool sent);

    public Task AddUser(User user);
}