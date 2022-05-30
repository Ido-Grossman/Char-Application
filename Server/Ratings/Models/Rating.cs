namespace Ratings.Models;

public class Rating
{
    public int CommentId { get; set; }
                
    public string? Name { get; set; }

    public int Stars { get; set; } //from 1 to 5

    public string? FeedbackText { get; set; }
}

    /*public class Comment
    {
        public int CommentId { get; set; }
                
        public string? Name { get; set; }

        public int Stars { get; set; } //from 1 to 5

        public string? FeedbackText { get; set; }

        }*/