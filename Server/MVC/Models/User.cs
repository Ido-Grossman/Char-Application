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
    public string Server { get; set; }

    [Required]
    public Dictionary<Contact, List<Message>> Contacts { get; set; }
    
    public List<UserContact> UserContacts { get; set; }

}