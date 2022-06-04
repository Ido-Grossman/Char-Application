using MVC.Models;
using MVC.Services;

namespace MVC.Services
{

    public class UserService : IUserService
    {
        private static readonly List<User> Users = new List<User>();

        public UserService()
        {
            User gross = new User
            {
                Id = "Idog770", Password = "grossman", Name = "gross", Server = "localhost:7225", UserContacts = new List<UserContact>()
            };
            User santi = new User
            {
                Id = "Santiago", Password = "santi", Name = "Santi", Server = "localhost:7225", UserContacts = new List<UserContact>()
            };
            User eli = new User
            {
                Id = "EliZil", Password = "eli", Name = "eli", Server = "localhost:7225", UserContacts = new List<UserContact>()
            };
            gross.UserContacts.Add(new UserContact
            {
                Contact = new Contact
                    {Id = santi.Id, Name = santi.Name, Server = santi.Server, LastMessageRead = 0, LastMessageId = 0},
                MsgList = new List<Message>()
            });
            gross.UserContacts.Add(new UserContact
            {
                Contact = new Contact {Id = eli.Id, Name = eli.Name, Server = eli.Server},
                MsgList = new List<Message>()
            });
            eli.UserContacts.Add(new UserContact
            {
                Contact = new Contact {Id = gross.Id, Name = gross.Name, Server = gross.Server},
                MsgList = new List<Message>()
                
            });
            santi.UserContacts.Add(new UserContact
            {
                Contact = new Contact {Id = gross.Id, Name = gross.Name, Server = gross.Server},
                MsgList = new List<Message>()
            });
            Users.Add(gross);
            Users.Add(santi);
            Users.Add(eli);
        }

        /**
         * Returns all the users.
         */
        public List<User> GetAll()
        {
            return Users;
        }

        /**
         * Returns a specific user if exists, null otherwise.
         */
        public User? Get(string name)
        {
            return Users.Find(x => x.Id == name);
        }
        
        public UserContact? GetUserContact(string userName, string friendName)
        {
            var user = Get(userName);
            if (user == null)
                return null;
            return user.UserContacts.Find(x => x.Contact.Id == friendName);
        }

        /**
         * Adds a user to the list of users.
         */
        public void AddUser(User user)
        {
            Users.Add(user);
        }

        public List<Contact> GetContacts(string userName)
        {
            var user = Get(userName);
            List<Contact> contacts = new List<Contact>();
            foreach (var contact in user.UserContacts)
                contacts.Add(contact.Contact);
            return contacts;
        }

        /**
         * Gets friendName contact from userName user, returns null if not exists.
         */
        public Contact? GetContact(string userName, string friendName)
        {
            var user = Get(userName);
            var contact =  user.UserContacts.Find(x => x.Contact.Id == friendName);
            if (contact == null)
                return null;
            return contact.Contact;
        }

        public void AddContact(string userName, Contact contact)
        {
            var user = Get(userName);
            user.UserContacts.Add(new UserContact{Contact = contact, MsgList = new List<Message>()});
        }
        
        public void RemoveContact(string userName, string friendName)
        {
            var user = Get(userName);
            var userContact = GetUserContact(userName, friendName);
            if (userContact == null)
                return;
            user.UserContacts.Remove(userContact);
        }

        public List<Message>? GetMessages(string userName, string friendName)
        {
            var userContact = GetUserContact(userName, friendName);
            if (userContact == null)
                return null;
            return userContact.MsgList;
        }

        /**
         * Returns specific message from the user and contact.
         */
        public Message? GetMessage(string userName, string friendName, int messageId)
        {
            var friendContact = GetUserContact(userName, friendName);
            if (friendContact == null)
                return null;
            return friendContact.MsgList.Find(x => x.Id == messageId);
        }

        public Message? AddMessage(string userName, string friendName, string message, bool sent)
        {
            var userContact = GetUserContact(userName, friendName);
            if (userContact == null)
                return null;
            var time = DateTime.Now.ToString();
            Message msg = new Message
            {
                Id = userContact.MsgList.Count, Content = message, Created = time, Sent = sent
            };
            userContact.MsgList.Add(msg);
            var contact = userContact.Contact;
            contact.LastMessageId++;
            contact.LastMessageRead = userContact.Contact.LastMessageId;
            contact.LastDate = time;
            contact.Last = message;
            return msg;
        }
    }
}