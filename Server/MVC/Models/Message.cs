
using System.Text.Json.Serialization;

namespace MVC.Models;

public class Message
{
    public int Id { get; set; }
    public string Content { get; set; }

    public string Created { get; set; }
    
    public bool Sent { get; set; }

    [JsonIgnore]
    public Contact Contact { get; set; }
}