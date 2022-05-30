using System.Diagnostics;
using System.Security.Cryptography;
using Microsoft.AspNetCore.Mvc;
using Ratings.Models;

namespace Ratings.Controllers;

public class RatingController : Controller
{
    private readonly ILogger<RatingController> _logger;

    public RatingController(ILogger<RatingController> logger)
    {
        _logger = logger;
    }

    public IActionResult Index()
    {
      //  Comment comment0 = new Comment() { CommentId = 1, Name = "Eli", Stars = 5, FeedbackText = "great app" };
       // Comment comment1 = new Comment() { CommentId = 2, Name = "Santi", Stars = 4, FeedbackText = "great app" };
        Rating rating = new Rating();// { AllComments = new List<Comment> { comment0, comment1 } };
        return View(rating);
    }

//    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]

}