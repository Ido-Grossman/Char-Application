namespace MVC.Models;

public class UserContactModel
{
    public ContactModel Contact { get; set; }

    public List<MessageModel> MsgList { get; set; }
}