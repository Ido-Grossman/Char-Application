using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MVC.Hubs;
using MVC.Models;
using MVC.Services;

namespace MVC.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ContactsController : ControllerBase
    {
        private readonly IUserService _service;

        public ContactsController(IUserService service)
        {
            this._service = service;
        }

        /**
         * Returns all the user contacts.
         */
        [HttpGet]
        public IActionResult Index()
        {
            // Gets the username from the given token and returns the user contacts.
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            return Ok(user.Contacts.Keys.ToList());
        }

        /*
         * Adds a new Contact to the user contact list.
         */
        [HttpPost]
        public IActionResult IndexPost([FromBody] ContactPost contactPost)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            // Makes sure the contact isn't already in the user contacts list, returns NotFound if he is.
            if (_service.GetContact(userName, contactPost.id) != null)
                return NotFound();
            var friendContact = new Contact(contactPost);
            user.Contacts[friendContact] = new List<Message>();
            Uri uri = new Uri($"https://localhost:7225/api/Contacts/{friendContact.Id}");
            return Created(uri, friendContact);
        }
        
        /*
         * Gets a specific contact details and returns Ok with the contact details if found, notFound else.
         */
        [HttpGet("{Id}")]
        public IActionResult GetId(string? id)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            var contact = user.Contacts.Keys.ToList().Find(x => x.Id == id);
            // Makes sure the contact exists.
            if (contact == null)
                return NotFound();
            return Ok(contact);
        }

        /*
         * Changes the nickname of a specific contact.
         */
        [HttpPut("{Id}")]
        public IActionResult PutId(string id, [FromBody] IdPut idPut)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var contact = _service.GetContact(userName, id);
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
            var user = _service.Get(userName);
            var contact = user.Contacts.Keys.ToList().Find(x => x.Name == id);
            // Makes sure the contact exists.
            if (contact == null)
                return NotFound();
            user.Contacts.Remove(contact);
            return Ok();
        }

        /*
         * Gets all the messages between the user and the specific contact, NotFound if the contact doesn't exist.
         */
        [HttpGet("{Id}/messages")]
        public IActionResult GetMessages(string? id)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            var contact = _service.GetContact(userName, id);
            if (contact == null)
                return NotFound();
            var userMessages = user.Contacts[contact];
            contact.LastMessageRead = contact.LastMessageId;
            // Makes sure the contact exists.
            return Ok(userMessages);
        }
        
        /*
         * Adds a new message between the user and the contact.
         */
        [HttpPost("{Id}/messages")]
        public IActionResult PostMessages(string id, [FromBody] ContentClass content)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            var contact = _service.GetContact(userName, id);
            if (contact == null)
                return NotFound();
            // Adds a new message and sets the id to be the size of the list of messages.
            var messages = user.Contacts[contact];
            var newMessage = new Message(messages.Count, content.Content, DateTime.Now.ToString(), true);
            messages.Add(newMessage);
            // Updates the user of the last message content and date.
            contact.Last = content.Content;
            contact.LastDate = DateTime.Now.ToString();
            contact.LastMessageId++;
            contact.LastMessageRead = contact.LastMessageId;
            Uri uri = new Uri($"https://localhost:7225/api/Contacts/{contact.Id}/messages/{messages.Count}");
            return Created(uri, newMessage);
        }
        
        /*
         * Gets a specific message details between the user and contact, returns NotFound if the message doesn't exist.
         */
        [HttpGet("{Id}/messages/{Id2}")]
        public IActionResult GetMessage(string id, int id2)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var message = _service.GetMessage(userName, id, id2);
            // Makes sure the message exists.
            if (message == null)
                return NotFound();
            return Ok(message);
        }
        
        /*
         * Changes the content of the given id message.
         */
        [HttpPut("{Id}/messages/{Id2}")]
        public void PutMessage(string id, int id2, [FromBody] ContentClass message)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            var contact = _service.GetContact(userName, id);
            Message theMessage;
            // Makes sure the message and contact exists.
            if (contact == null || (theMessage = user.Contacts[contact].Find(x => x.Id == id2)) == null)
                return;
            theMessage.Content = message.Content;
        }
        
        /*
         * Deletes the given id message from the list of the user and contact.
         */
        [HttpDelete("{Id}/messages/{Id2}")]
        public void RemoveMessage(string id, int id2)
        {
            var userName = HttpContext.User.Claims.First(i => i.Type == "UserId").Value;
            var user = _service.Get(userName);
            var contact = _service.GetContact(userName, id);
            Message messageUser;
            // Makes sure the message and contact exists.
            if (contact == null || (messageUser = user.Contacts[contact].Find(x => x.Id == id2)) == null)
                return;
            user.Contacts[contact].Remove(messageUser);
        }
    }
}
