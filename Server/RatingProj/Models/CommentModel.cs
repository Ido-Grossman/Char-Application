namespace RatingProj.Models;

public class CommentModel
{
        public int Id { get; set; }
                
        public string? Name { get; set; }

        public int Stars { get; set; } //from 1 to 5

        public string? FeedbackText { get; set; }

        public DateTime DateTime { get; set; }
}