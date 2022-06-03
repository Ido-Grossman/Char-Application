using Microsoft.EntityFrameworkCore;

namespace RatingProj.Data
{
    public class RatingProjContext : DbContext
    {
        public RatingProjContext (DbContextOptions<RatingProjContext> options)
            : base(options)
        {
        }

        public DbSet<RatingProj.Models.CommentModel>? CommentModel { get; set; }
        // protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        // {
        // optionsBuilder.UseSqlServer("Server=(localdb)\\ProjectModels;Database=
        // }
    }
}
