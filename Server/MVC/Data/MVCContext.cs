using Microsoft.EntityFrameworkCore;
using MVC.Models;

namespace MVC.Data;

public class MVCContext : DbContext
{

    private const string connectionString = "server=localhost;port=3306;user=root;password=root;database=ChatOS";

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseMySql(connectionString, MariaDbServerVersion.LatestSupportedServerVersion);
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<User>().HasKey(e => e.Id);
        modelBuilder.Entity<Contact>().HasKey(e => e.Id);
        modelBuilder.Entity<Message>().HasKey(e => e.Id);
        modelBuilder.Entity<UserContact>().HasKey(e => e.Contact);
    }

    public DbSet<Contact> Contacts { get; set; }
    public DbSet<Message> Messages { get; set; }
    public DbSet<User> Users { get; set; }
    public DbSet<UserContact> UserContacts { get; set; }
}