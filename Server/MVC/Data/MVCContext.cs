using Microsoft.EntityFrameworkCore;
using MVC.Models;

namespace MVC.Data;

public class MVCContext : DbContext
{
    public MVCContext()
    {
        
    }
    
    public MVCContext (DbContextOptions<MVCContext> options)
        : base(options)
    {
    }
    
    public DbSet<Contact> Contacts { get; set; }
    public DbSet<Message> Messages { get; set; }
    public DbSet<User> Users { get; set; }
    public DbSet<UserContact> UserContacts { get; set; }
}