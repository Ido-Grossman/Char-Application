using Domain;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using Services;
using MVC.Hubs;

namespace MVC.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class InvitationsController : ControllerBase
    {
        
        private readonly IUserService _service;

        private readonly IHubContext<MessageHub> _hub;

        public InvitationsController(IUserService service, IHubContext<MessageHub> messageHub)
        {
            _service = service;
            _hub = messageHub;
        }

        /**
         * Adds a new contact to the user.
         */
        [HttpPost]
        public IActionResult PostInv([FromBody] TraInv details)
        {
            // Gets the user from the service and makes sure he exists and the friend isn't already in his friend list.
            var user = _service.Get(details.To);
            if (user == null || user.Contacts.Keys.ToList().Find(x => x.Id == details.From) != null)
                return NotFound();
            // Adds the contact to the user contacts.
            var friendContact = new Contact(details.From, details.From, details.Server);
            user.Contacts.Add(friendContact, new List<Message>());
            // If the user is connected it will update him that a new user contact has been added.
            _hub.Clients.Group(user.Id).SendAsync("FriendAdded");
            Uri uri = new Uri($"https://localhost:7225/api/Contacts/{friendContact.Id}");
            return Created(uri, friendContact);
        }
    }
}
