namespace MVC.Models;

public class ContactModel
{
    public string Id { get; set; }
    
    public string Name { get; set; }

    public string Server { get; set; }

    public string? Last { get; set; }
    
    public string? LastDate { get; set; }
    
    public int LastMessageRead { get; set; }
    
    public int LastMessageId { get; set; }
}