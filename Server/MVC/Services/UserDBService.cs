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

    public async Task<List<User>> GetAll()
    {
        // Gets the list of users from the database.
        return await _context.Users.ToListAsync();
    }

    public async Task<User?> Get(string name)
    {
        // Gets a specific user from the database.
        return await _context.Users.FindAsync(name);
    }

    public async Task<ICollection<Contact>?> GetContacts(string userName)
    {
        // Gets the user.
        var user = await Get(userName);
        // Adds is contacts to the user class and returns the contacts.
        await _context.Entry(user).Collection(e => e!.Contacts).LoadAsync();
        var contacts = user?.Contacts;
        return contacts;
    }

    public async Task<Contact?> GetContact(string userName, string friendName)
    {
        // Searches in the database of contacts to the first contact with the userId and contact name that matches.
        return await _context.Contacts.FindAsync(friendName, userName);
    }

    public async Task AddContact(string userName, string id, string name, string server)
    {
        // Gets the user from the database.
        var user = await Get(userName);
        var contact = await _context.Contacts.FindAsync(userName, id);
        // Makes sure the contact isn't in the user contacts.
        if (contact != null)
            return;
        // Adds the contact to the user contacts and saves the changes to the DB.
        user?.Contacts.Add(new Contact
        {
            Id = id, Name = name, Server = server, UnreadMessages = 0
        });
        await _context.SaveChangesAsync();
    }

    public async Task RemoveContact(string userName, string friendName)
    {
        // Gets the contact from the DB.
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return;
        // Remove the contact from the DB and saves the changes.
        _context.Contacts.Remove(contact);
        await _context.SaveChangesAsync();
    }

    public async Task<ICollection<Message>?> GetMessages(string userName, string friendName)
    {
        // Gets the contact from the DB and makes sure he is valid.
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return null;
        // Sets the number of unread messages to 0 and saves the change to the DB.
        contact.UnreadMessages = 0;
        await _context.SaveChangesAsync();
        // Adds the messages to the contact object and returns the messages.
        await _context.Entry(contact).Collection(e => e.Messages).LoadAsync();
        return contact.Messages;
    }

    public async Task<Message?> GetMessage(string userName, string friendName, int messageId)
    {
        // Gets the message from the DB and returns it.
        return await _context.Messages.FindAsync(messageId);
    }

    public async Task<Message?> AddMessage(string userName, string friendName, string message, bool sent)
    {
        // Gets the contact from the DB and makes sure he exists.
        var contact = await GetContact(userName, friendName);
        if (contact == null)
            return null;
        // Creates a new message and adds it to the contact messages.
        Message msg;
        var time = DateTime.Now.ToString();
        contact.Messages.Add(msg = new Message
        {
            Content = message, Sent = sent, Created = time
        });
        // If the user didn't send the message it adds 1 to the unread messages.
        if (sent == false)
            contact.UnreadMessages++;
        // Sets the lastdate and last message to date and time, saves the DB and returns the msg.
        contact.LastDate = time;
        contact.Last = message;
        await _context.SaveChangesAsync();
        return msg;
    }

    public async Task RemoveMessage(Message message)
    {
        _context.Messages.Remove(message);
        await _context.SaveChangesAsync();
    }

    public async Task AddUser(User user)
    {
        // Adds the user to the DB and saves the changes.
        _context.Users.Add(user);
        await _context.SaveChangesAsync();
    }
}