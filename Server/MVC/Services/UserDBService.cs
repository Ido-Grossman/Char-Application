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
        return await _context.Users.FindAsync(name);
    }

    public async Task<ICollection<Contact>> GetContacts(string userName)
    {
        var user = await Get(userName);
        return user.Contacts;
    }

    public async Task<Contact?> GetContact(string userName, string friendName)
    {
        var contacts = await GetContacts(userName);
        return contacts.FirstOrDefault(e => e.Id == friendName);
    }

    public async void AddContact(string userName, string id, string name, string server)
    {
        var user = await Get(userName);
        user.Contacts.Add(new Contact
        {
            Id = id, Name = name, Server = server, UnreadMessages = 0
        });
        await _context.SaveChangesAsync();
    }

    public async void RemoveContact(string userName, string friendName)
    {
        var user = await Get(userName);
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return;
        user?.Contacts.Remove(contact);
        await _context.SaveChangesAsync();
    }

    public async Task<List<Message>?> GetMessages(string userName, string friendName)
    {
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return null;
        contact.UnreadMessages = 0;
        await _context.SaveChangesAsync();
        return contact.Messages.ToList();
    }

    public async Task<Message?> GetMessage(string userName, string friendName, int messageId)
    {
        var messages = await GetMessages(userName, friendName);
        var message = messages?.Find(e => e.Id == messageId);
        return message ?? null;
    }

    public async Task<Message?> AddMessage(string userName, string friendName, string message, bool sent)
    {
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return null;
        Message msg;
        var time = DateTime.Now.ToString();
        contact.Messages.Add(msg = new Message
        {
            Content = message, Sent = sent, Created = time
        });
        if (sent == false)
            contact.UnreadMessages++;
        contact.LastDate = time;
        contact.Last = message;
        await _context.SaveChangesAsync();
        return msg;
    }

    public void AddUser(User user)
    {
        _context.Users.Add(user);
        _context.SaveChangesAsync();
    }
}