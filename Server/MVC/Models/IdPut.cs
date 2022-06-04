using System.ComponentModel.DataAnnotations.Schema;

namespace MVC.Models;


[NotMapped]
public class IdPut
{
    public string Name { get; set; }

    public string Server { get; set; }
}