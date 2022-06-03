using System.ComponentModel.DataAnnotations.Schema;

namespace MVC.Models;

[NotMapped]
public class ContentClass
{
    public string Content { get; set; }
}