namespace MVC.Hubs;
using Microsoft.AspNetCore.SignalR;

public class MessageHub : Hub
{
    public Task JoinRoom(string roomName)
    { 
        return Groups.AddToGroupAsync(Context.ConnectionId, roomName);
    }
    
    public Task LeaveRoom(string roomName) 
    {
        return Groups.RemoveFromGroupAsync(Context.ConnectionId, roomName);
    }
    
}