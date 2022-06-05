using System.Data.Entity;
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
        return _context.Users.ToListAsync();
    }

    public async Task<User?> Get(string name)
    {
        var user = await _context.Users.FindAsync(name);
        return user;
    }

    public async Task<ICollection<Contact>> GetContacts(string userName)
    {
        var user = await Get(userName);
        var getFriend = _context.Contacts.Where(x => x.User == user);
        return getFriend.ToList();
    }

    public async Task<Contact?> GetContact(string userName, string friendName)
    {
        var user = await Get(userName);
        var contact = _context.Contacts.Where(x => x.User == user).Where(x => x.Id == friendName);
        return contact.FirstOrDefault();
    }

    public void AddContact(string userName, Contact contact)
    {
        
        _context.Contacts.Add(new Contact
        {
            
        });
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