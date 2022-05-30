using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using RatingProj.Models;

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
