using Microsoft.AspNetCore.Mvc;

namespace MVC.Controllers;

public class RegisterController : Controller
{
    // GET
    public IActionResult Index()
    {
        return Redirect("/");
    }
}