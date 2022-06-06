using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using MVC.Hubs;
using MVC.Models;
using MVC.Services;

namespace MVC.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TransferController : ControllerBase
    {
        private readonly IUserDBService _service;

        private readonly IHubContext<MessageHub> _hub;

        public TransferController(IUserDBService service, IHubContext<MessageHub> messageHub)
        {
            _service = service;
            _hub = messageHub;
        }

        /*
         * Adds the message to the user and notifies him a message has been sent.
         */
        [HttpPost]
        public async Task<IActionResult> PostInv([FromBody] TraInv details)
        {
            var user = await _service.Get(details.To);
            if (user == null)
                return NoContent();
            var contact = await _service.GetContact(user.Id, details.From);
            // Makes sure the contact exists.
            if (contact == null)
                return NotFound();
            // Adds a new message to the user.
            var message = await _service.AddMessage(user.Id, contact.Id, details.Content, false);
            // if the client is connected to the user it updates him that a message has been received.
            await _hub.Clients.Group(user.Id).SendAsync("MessageReceived");
            Uri uri = new Uri($"https://localhost:7225/api/Contacts/{contact.Id}/messages/{message.Id}");
            return Created(uri, message);
        }
        
    }
}
