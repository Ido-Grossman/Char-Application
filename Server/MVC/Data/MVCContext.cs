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
        modelBuilder.Entity<Contact>().HasKey(e => new {e.Id, e.UserId});
    }

    public DbSet<Contact> Contacts { get; set; }
    public DbSet<Message> Messages { get; set; }
    public DbSet<User> Users { get; set; }
}