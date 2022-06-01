using System.ComponentModel.DataAnnotations;

namespace MVC.Models;

public class UserModel
{
    [Key]
    public string Id { get; set; }
    
    [DataType(DataType.Password), MinLength(8), Required]
    public string Password { get; set; }
    
    [Required]
    public string Name { get; set; }

    [Required]
    public string? Image { get; set; }
    
    [Required]
    public string Server { get; set; }

    [Required]
    // public Dictionary<Contact, List<Message>> Contacts { get; set; }
    public List<UserContactModel> Contacts { get; set; }
}