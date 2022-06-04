using System.ComponentModel.DataAnnotations;
using System.Data.Entity;
using System.Text.Json.Serialization;

namespace MVC.Models;

public class User
{
    [Key]
    public string Id { get; set; }
    
    [DataType(DataType.Password), MinLength(8), Required, JsonIgnore]
    public string Password { get; set; }
    
    public string Name { get; set; }
    
    
    public string Server { get; set; }

    public ICollection<UserContact> UserContacts { get; set; }

}