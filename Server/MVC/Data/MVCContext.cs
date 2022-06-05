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
        modelBuilder.Entity<Contact>().HasOne(e => e.User).WithMany(e => e.Contacts);
        modelBuilder.Entity<Message>().HasOne(e => e.Contact).WithMany(e => e.Messages);
    }

    public DbSet<Contact> Contacts { get; set; }
    public DbSet<Message> Messages { get; set; }
    public DbSet<User> Users { get; set; }
}