using System.ComponentModel.DataAnnotations;

namespace MVC.Models;

public class UserContact
{
    [Key]
    public Contact Contact { get; set; }
    
    public List<Message> MsgList { get; set; }
}