using Microsoft.EntityFrameworkCore;
using MVC.Models;

namespace MVC.Data;

public class MVCContext : DbContext
{

    public MVCContext (DbContextOptions<MVCContext> options)
        : base(options)
    {
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<User>().HasKey(e => e.Id);
        modelBuilder.Entity<User>().HasMany(e => e.UserContacts).WithOne(e => e.User);
        modelBuilder.Entity<UserContact>().HasMany(e => e.MsgList).WithOne(e => e.UserContact);
        modelBuilder.Entity<Contact>().HasKey(e => e.Id);
        modelBuilder.Entity<Message>().HasKey(e => e.Id);
    }

    public DbSet<Contact> Contacts { get; set; }
    public DbSet<Message> Messages { get; set; }
    public DbSet<User> Users { get; set; }
    public DbSet<UserContact> UserContacts { get; set; }
}