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
        modelBuilder.Entity<Contact>().Property(e => e.Id).HasMaxLength(127);
        modelBuilder.Entity<Contact>().Property(e => e.Name).HasMaxLength(127);
        modelBuilder.Entity<Contact>().Property(e => e.Last).HasMaxLength(127);
        modelBuilder.Entity<Contact>().Property(e => e.LastDate).HasMaxLength(127);
        modelBuilder.Entity<Contact>().Property(e => e.UnreadMessages).HasMaxLength(127);
        modelBuilder.Entity<User>().Property(e => e.Id).HasMaxLength(127);
        modelBuilder.Entity<User>().Property(e => e.Password).HasMaxLength(127);
        modelBuilder.Entity<User>().Property(e => e.Name).HasMaxLength(127);
        modelBuilder.Entity<User>().Property(e => e.Server).HasMaxLength(127);
        modelBuilder.Entity<Contact>().HasKey(e => new {e.Id, e.UserId});
    }

    public DbSet<Contact> Contacts { get; set; }
    public DbSet<Message> Messages { get; set; }
    public DbSet<User> Users { get; set; }
}