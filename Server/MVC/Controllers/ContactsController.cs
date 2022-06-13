using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MVC.Models;
using MVC.Services;

namespace MVC.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ContactsController : ControllerBase
    {
        private readonly IUserDBService _service;

        public ContactsController(IUserDBService service)
        {
            _service = service;
        }

        /**
         * Returns all the user contacts.
         */
        [HttpGet]
        public async Task<IActionResult> Index()
        {
            // Gets the username from the given token and returns the user contacts.
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var contacts = await _service.GetContacts(userName);
            return Ok(contacts);
        }

        /*
         * Adds a new Contact to the user contact list.
         */
        [HttpPost]
        public async Task<IActionResult> IndexPost([FromBody] ContactPost contactPost)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            // Makes sure the contact isn't already in the user contacts list, returns NotFound if he is.
            if (await _service.GetContact(userName, contactPost.id) != null)
                return NotFound();
            var friendContact = new Contact{Id = contactPost.id, Name = contactPost.name, Server = contactPost.server};
            await _service.AddContact(userName, contactPost.id, contactPost.name, contactPost.server);
            Uri uri = new Uri($"https://localhost:7225/api/Contacts/{friendContact.Id}");
            return Created(uri, friendContact);
        }
        
        /*
         * Gets a specific contact details and returns Ok with the contact details if found, notFound else.
         */
        [HttpGet("{Id}")]
        public async Task<IActionResult> GetId(string? id)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            if (id == null)
                return NotFound();
            var contact = await _service.GetContact(userName, id);
            // Makes sure the contact exists.
            if (contact == null)
                return NotFound();
            return Ok(contact);
        }

        /*
         * Changes the nickname of a specific contact.
         */
        [HttpPut("{Id}")]
        public async Task<IActionResult> PutId(string id, [FromBody] IdPut idPut)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var contact = await _service.GetContact(userName, id);
            // Makes sure the contact exists.
            if (contact == null)
                return NotFound();
            contact.Name = idPut.Name;
            contact.Server = idPut.Server;
            return Ok();
        }

        /*
         * Deletes a specific contact from the user contacts list.
         */
        [HttpDelete("{Id}")]
        public IActionResult DeleteId(string? id)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            if (id == null)
                return NotFound();
            _service.RemoveContact(userName, id);
            return Ok();
        }

        /*
         * Gets all the messages between the user and the specific contact, NotFound if the contact doesn't exist.
         */
        [HttpGet("{Id}/messages")]
        public async Task<IActionResult> GetMessages(string? id)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var contact = await _service.GetContact(userName, id);
            if (contact == null)
                return NotFound();
            var userMessages = await _service.GetMessages(userName, id);
            // Makes sure the contact exists.
            return Ok(userMessages);
        }
        
        /*
         * Adds a new message between the user and the contact.
         */
        [HttpPost("{Id}/messages")]
        public async Task<IActionResult> PostMessages(string id, [FromBody] ContentClass content)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var message = await _service.AddMessage(userName, id, content.Content, true);
            var uri = new Uri($"https://localhost:7225/api/Contacts/{id}/messages/{message.Id}");
            return Created(uri, message);
        }
        
        /*
         * Gets a specific message details between the user and contact, returns NotFound if the message doesn't exist.
         */
        [HttpGet("{Id}/messages/{Id2}")]
        public async Task<IActionResult> GetMessage(string id, int id2)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var message = await _service.GetMessage(userName, id, id2);
            // Makes sure the message exists.
            if (message == null)
                return NotFound();
            return Ok(message);
        }
        
        /*
         * Changes the content of the given id message.
         */
        [HttpPut("{Id}/messages/{Id2}")]
        public async Task PutMessage(string id, int id2, [FromBody] ContentClass message)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var contact = await _service.GetContact(userName, id);
            Message? theMessage;
            // Makes sure the message and contact exists.
            if (contact == null || (theMessage = await _service.GetMessage(userName, id, id2)) == null)
                return;
            theMessage.Content = message.Content;
        }
        
        /*
         * Deletes the given id message from the list of the user and contact.
         */
        [HttpDelete("{Id}/messages/{Id2}")]
        public async Task RemoveMessage(string id, int id2)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var contact = await _service.GetContact(userName, id);
            Message? messageUser;
            // Makes sure the message and contact exists.
            if (contact == null || (messageUser = await _service.GetMessage(userName, id, id2)) == null)
                return;
            await _service.RemoveMessage(messageUser);
        }
    }
}
