using MVC.Data;
using MVC.Models;

namespace MVC.Services;

public class UserDBService : IUserDBService
{
    private MVCContext _context;

    public UserDBService(MVCContext mvcContext)
    {
        _context = mvcContext;
    }

    public Task<List<User>> GetAll()
    {
        throw new NotImplementedException();
    }

    public Task<User?> Get(string name)
    {
        throw new NotImplementedException();
    }

    public Task<UserContact?> GetUserContact(string userName, string friendName)
    {
        throw new NotImplementedException();
    }

    public Task<List<Contact>> GetContacts(string userName)
    {
        throw new NotImplementedException();
    }

    public Task<Contact?> GetContact(string userName, string friendName)
    {
        throw new NotImplementedException();
    }

    public void AddContact(string userName, Contact contact)
    {
        throw new NotImplementedException();
    }

    public void RemoveContact(string userName, string friendName)
    {
        throw new NotImplementedException();
    }

    public Task<List<Message>?> GetMessages(string userName, string friendName)
    {
        throw new NotImplementedException();
    }

    public Task<Message?> GetMessage(string userName, string friendName, int messageId)
    {
        throw new NotImplementedException();
    }

    public Task<Message?> AddMessage(string userName, string friendName, string message, bool sent)
    {
        throw new NotImplementedException();
    }

    public void AddUser(User user)
    {
        throw new NotImplementedException();
    }
}