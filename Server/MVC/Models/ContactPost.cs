using System.ComponentModel.DataAnnotations.Schema;

namespace MVC.Models;

[NotMapped]
public class ContactPost
{
    public string id { get; set; }

    public string name { get; set; }

    public string server { get; set; }
}