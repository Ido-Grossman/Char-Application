﻿using MVC.Models;

namespace MVC.Services
{
    public interface IUserService
    {
        public List<User> GetAll();

        public User? Get(string name);
        public List<Contact> GetContacts(string userName);
        
        public Contact? GetContact(string userName, string friendName);

        public void AddContact(string userName, string id, string name, string server);

        public void RemoveContact(string userName, string friendName);

        public List<Message>? GetMessages(string userName, string friendName);
        
        public Message? GetMessage(string userName, string friendName, int messageId);

        public Message? AddMessage(string userName, string friendName, string message, bool sent);

        public void AddUser(User user);
    }
}