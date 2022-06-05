using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

namespace MVC.Models;

public class Contact
{
    public string Id { get; set; }
    
    public string Name { get; set; }

    public string Server { get; set; }

    public string? Last { get; set; }
    
    public string? LastDate { get; set; }
    
    public int UnreadMessages { get; set; }
    
    [JsonIgnore]
    public ICollection<Message> Messages { get; set; }
    
    [JsonIgnore]
    public string UserId { get; set; }
    
    [JsonIgnore]
    public User User { get; set; }

    public Contact()
    {
        this.Messages = new List<Message>();
    }
}