using MVC.Models;

namespace MVC.Services
{
    public interface IUserService
    {
        public List<User> GetAll();

        public User? Get(string name);

        public Contact? GetContact(string userName, string friendName);

        public Message? GetMessage(string userName, string friendName, int messageId);

        public void AddUser(User user);
    }
}