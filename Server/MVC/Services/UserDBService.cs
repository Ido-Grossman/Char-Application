using System.Data.Entity;
using Microsoft.EntityFrameworkCore;
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
        await _context.Entry(user).Collection(e => e.Contacts).LoadAsync();
        var contacts = user.Contacts;
        return contacts;
    }

    public async Task<Contact?> GetContact(string userName, string friendName)
    {
        var contact = _context.Contacts.Where(e => e.UserId == userName && e.Id == friendName);
        return contact.FirstOrDefault();
    }

    public async Task AddContact(string userName, string id, string name, string server)
    {
        var user = await Get(userName);
        user?.Contacts.Add(new Contact
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
        _context.Contacts.Remove(contact);
        await _context.SaveChangesAsync();
    }

    public async Task<ICollection<Message>?> GetMessages(string userName, string friendName)
    {
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return null;
        contact.UnreadMessages = 0;
        await _context.SaveChangesAsync();
        await _context.Entry(contact).Collection(e => e.Messages).LoadAsync();
        return contact.Messages.ToList();
    }

    public async Task<Message?> GetMessage(string userName, string friendName, int messageId)
    {
        var message = await _context.Messages.FindAsync(messageId);
        return message;
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

    public async Task AddUser(User user)
    {
        _context.Users.Add(user);
        await _context.SaveChangesAsync();
    }
}