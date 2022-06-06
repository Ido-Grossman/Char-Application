using System.Collections.ObjectModel;
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
                Id = "Idog770", Password = "grossman", Name = "gross", Server = "localhost:7225", Contacts = new List<Contact>()
            };
            User santi = new User
            {
                Id = "Santiago", Password = "santi", Name = "Santi", Server = "localhost:7225", Contacts = new List<Contact>()
            };
            User eli = new User
            {
                Id = "EliZil", Password = "eli", Name = "eli", Server = "localhost:7225", Contacts = new List<Contact>()
            };
            gross.Contacts.Add(new Contact
                {
                    Id = santi.Id, Name = santi.Name, Server = santi.Server, UnreadMessages = 0,
                    Messages = new List<Message>(), User = gross
            });
            gross.Contacts.Add(new Contact
            {
                Id = eli.Id, Name = eli.Name, Server = eli.Server, UnreadMessages = 0, 
                Messages = new List<Message>(), User = gross
            });
            eli.Contacts.Add(new Contact
            {
                Id = gross.Id, Name = gross.Name, Server = gross.Server, UnreadMessages = 0, 
                Messages = new List<Message>(), User = eli
                
            });
            santi.Contacts.Add(new Contact
            {
                Id = gross.Id, Name = gross.Name, Server = gross.Server, UnreadMessages = 0,
                Messages = new List<Message>(), User = santi
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
            return user.Contacts.ToList();
        }

        /**
         * Gets friendName contact from userName user, returns null if not exists.
         */
        public Contact? GetContact(string userName, string friendName)
        {
            var user = Get(userName);
            var contact =  user?.Contacts.ToList().Find(x => x.Id == friendName);
            return contact ?? null;
        }

        public void AddContact(string userName, string id, string name, string server)
        {
            var user = Get(userName);
            user?.Contacts.Add(new Contact
            {
                Id = id, Name = name, Server = server, UnreadMessages = 0
            });
        }
        
        public void RemoveContact(string userName, string friendName)
        {
            var user = Get(userName);
            var userContact = GetContact(userName, friendName);
            if (userContact == null)
                return;
            user?.Contacts.Remove(userContact);
        }

        public List<Message>? GetMessages(string userName, string friendName)
        {
            var userContact = GetContact(userName, friendName);
            if (userContact == null)
                return null;
            userContact.UnreadMessages = 0;
            return userContact.Messages.ToList();
        }

        /**
         * Returns specific message from the user and contact.
         */
        public Message? GetMessage(string userName, string friendName, int messageId)
        {
            var friendContact = GetContact(userName, friendName);
            if (friendContact == null)
                return null;
            return friendContact.Messages.ToList().Find(x => x.Id == messageId);
        }

        public Message? AddMessage(string userName, string friendName, string message, bool sent)
        {
            var userContact = GetContact(userName, friendName);
            if (userContact == null)
                return null;
            var time = DateTime.Now.ToString();
            Message msg = new Message
            {
                Id = userContact.Messages.Count, Content = message, Created = time, Sent = sent
            };
            if (sent == false)
                userContact.UnreadMessages++;
            userContact.Messages.Add(msg);
            userContact.LastDate = time;
            userContact.Last = message;
            return msg;
        }
    }
}