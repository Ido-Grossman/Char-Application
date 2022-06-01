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
        private readonly IUserService _service;

        private readonly IHubContext<MessageHub> _hub;

        public TransferController(IUserService service, IHubContext<MessageHub> messageHub)
        {
            _service = service;
            _hub = messageHub;
        }

        /*
         * Adds the message to the user and notifies him a message has been sent.
         */
        [HttpPost]
        public IActionResult PostInv([FromBody] TraInv details)
        {
            var user = _service.Get(details.To);
            var contact = _service.GetContact(user.Id, details.From);
            // Makes sure the contact exists.
            if (contact == null)
                return NotFound();
            var contactMessages = user.Contacts[contact];
            int id = contactMessages.Count - 1;
            // Adds a new message to the user.
            var message = new Message(id, details.Content, DateTime.Now.ToString(), false);
            contactMessages.Add(message);
            // Updates the last message content and date.
            contact.Last = details.Content;
            contact.LastDate = DateTime.Now.ToString();
            contact.LastMessageId++;
            // if the client is connected to the user it updates him that a message has been received.
            _hub.Clients.Group(user.Id).SendAsync("MessageReceived");
            Uri uri = new Uri($"https://localhost:7225/api/Contacts/{contact.Id}/messages/{id}");
            return Created(uri, message);
        }
        
    }
}
