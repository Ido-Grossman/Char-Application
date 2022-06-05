using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

namespace MVC.Models;

public class Message
{
    public int Id { get; set; }
    public string Content { get; set; }

    public string Created { get; set; }
    
    public bool Sent { get; set; }

    [JsonIgnore]
    [ForeignKey("ContactForeignKey")]
    public Contact Contact { get; set; }
}