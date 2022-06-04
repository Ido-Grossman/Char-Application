using System.ComponentModel.DataAnnotations;

namespace MVC.Models;

public class UserContact
{
    [Key]
    public string Id { get; set; }
    
    public User User { get; set; }
    
    public Contact Contact { get; set; }
    
    public ICollection<Message> MsgList { get; set; }
}