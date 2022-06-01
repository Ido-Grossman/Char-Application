namespace MVC.Models;

public class Contact
{
    public string Id { get; set; }
    
    public string Name { get; set; }

    public string Server { get; set; }

    public string? Last { get; set; }
    
    public string? LastDate { get; set; }
    
    public int LastMessageRead { get; set; }
    
    public int LastMessageId { get; set; }

    public Contact(User user)
    {
        Id = user.Id;
        Name = user.Name;
        Server = user.Server;
        LastMessageRead = 0;
        LastMessageId = 0;
    }

    public Contact(ContactPost contactPost)
    {
        Id = contactPost.id;
        Name = contactPost.name;
        Server = contactPost.server;
    }
    
    public Contact(string id, string name, string server)
    {
        Id = id;
        Name = name;
        Server = server;
    }
}