using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace MVC.Models;

public class User
{
    [Key]
    public string Id { get; set; }
    
    [DataType(DataType.Password), MinLength(8), Required, JsonIgnore]
    public string Password { get; set; }
    
    [Required]
    public string Name { get; set; }

    [Required]
    public string? Image { get; set; }
    
    [Required]
    public string Server { get; set; }

    [Required]
    public Dictionary<Contact, List<Message>> Contacts { get; set; }
    public User(string id, string password, string name, string? image, string server)
    {
        Id = id;
        Password = password;
        Name = name;
        Image = image;
        Server = server;
        Contacts = new Dictionary<Contact, List<Message>>();
    }
    
    public User(string id, string password, string name, string server)
    {
        Id = id;
        Password = password;
        Name = name;
        Server = server;
        Contacts = new Dictionary<Contact, List<Message>>();
    }

    public User(UserCred userCred)
    {
        Id = userCred.Username;
        Password = userCred.Password;
        Name = userCred.Nickname;
        Server = userCred.Server;
        Contacts = new Dictionary<Contact, List<Message>>();
    }
}