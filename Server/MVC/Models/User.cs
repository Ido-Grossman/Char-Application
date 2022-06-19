using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace MVC.Models;

public class User
{
    public string Id { get; set; }
    
    [DataType(DataType.Password), MinLength(8), Required]
    [JsonIgnore]
    public string Password { get; set; }
    
    public string Name { get; set; }
    
    
    public string Server { get; set; }
    
    //TODO: Firebase token
    [JsonIgnore]
    public string? FirebaseToken { get; set; }
    
    [JsonIgnore]
    public ICollection<Contact> Contacts { get; set; }

    public User()
    {
        this.Contacts = new List<Contact>();
    }

}