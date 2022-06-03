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
                Id = "Idog770", Password = "grossman", Name = "gross", Server = "localhost:7225", Contacts = new Dictionary<Contact, List<Message>>()
            };
            User santi = new User
            {
                Id = "Santiago", Password = "santi", Name = "Santi", Server = "localhost:7225", Contacts = new Dictionary<Contact, List<Message>>()
            };
            User eli = new User
            {
                Id = "EliZil", Password = "eli", Name = "eli", Server = "localhost:7225", Contacts = new Dictionary<Contact, List<Message>>()
            };
            gross.Contacts.Add(new Contact
            {
                Id = santi.Id, Name = santi.Name, Server = santi.Server, LastMessageRead = 0, LastMessageId = 0
            }, new List<Message>());
            gross.Contacts.Add(new Contact
            {
                Id = eli.Id, Name = eli.Name, Server = eli.Server
            }, new List<Message>());
            eli.Contacts.Add(new Contact
            {
                Id = gross.Id, Name = gross.Name, Server = gross.Server
            }, new List<Message>());
            santi.Contacts.Add(new Contact
            {
                Id = gross.Id, Name = gross.Name, Server = gross.Server
            }, new List<Message>());
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

        /**
         * Gets friendName contact from userName user, returns null if not exists.
         */
        public Contact? GetContact(string userName, string friendName)
        {
            var user = Get(userName);
            return user.Contacts.Keys.ToList().Find(x => x.Id == friendName);
        }

        /**
         * Returns specific message from the user and contact.
         */
        public Message? GetMessage(string userName, string friendName, int messageId)
        {
            var user = Get(userName);
            var friend = user.Contacts.Keys.ToList().Find(x => x.Name == friendName);
            if (friend == null)
            {
                return null;
            }
            return user.Contacts[friend].Find(x => x.Id == messageId);
        }
    }
}