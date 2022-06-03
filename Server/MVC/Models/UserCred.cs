using System.ComponentModel.DataAnnotations.Schema;

namespace MVC.Models;

[NotMapped]
public class UserCred
{
    public string Username { get; set; }

    public string Password { get; set; }

    public string? Nickname { get; set; }
    
    public string? Server { get; set; }
}