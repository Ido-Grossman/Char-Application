using MVC.Models;

namespace MVC.Services
{

    public class UserService : IUserService
    {
        private static readonly List<User> Users = new List<User>();

        public UserService()
        {
            
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