using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using MVC.Models;
using MVC.Services;

namespace MVC.Controllers
{
    [Route("/api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        
        private readonly IUserDBService _service;
        

        private readonly IConfiguration _configuration;

        public UsersController(IUserDBService service, IConfiguration configuration)
        {
            _service = service;
            _configuration = configuration;
        }

        /**
         * Checks if the user name and password are valid, if they are it creates a jwt token and returns it with ok
         * status, if they aren't it returns NotFound.
         */
        [HttpPost("Login")]
        public async Task<IActionResult> Login([FromBody]UserCred userCred)
        {
            // Checks if the user exists in the DB.
            var user = await _service.Get(userCred.Username);
            if (user != null && user.Password == userCred.Password)
            {
                // Makes the key with all the claims.
                var claims = new[]
                {
                    new Claim(JwtRegisteredClaimNames.Sub, _configuration["JWTParams:Subject"]),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                    new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString()),
                    new Claim("UserId", userCred.Username)
                };
                var secretKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWTParams:SecretKey"]));
                var mac = new SigningCredentials(secretKey, SecurityAlgorithms.HmacSha256);
                var token = new JwtSecurityToken(
                    _configuration["JWTParams:Issuer"],
                    _configuration["JWTParams:Audience"],
                    claims,
                    expires: DateTime.UtcNow.AddMinutes(1),
                    signingCredentials: mac);
                return Ok(new JwtSecurityTokenHandler().WriteToken(token));
            }
            return NotFound();
        }

        /**
         * Checks if the user already logged in or not, returns Ok if he logged in with his username, returns NotFound
         * if not.
         */
        [HttpGet("LoggedIn")]
        [Authorize]
        public async Task<IActionResult> IsLoggedIn()
        {
            var user = await _service.Get(HttpContext.User.Claims.First(i => i.Type == "UserId").Value);
            return Ok(user.Id);
        }

        /**
         * Logs the user out of the system by deleting his clientId.
         */
        [HttpGet("Logout")]
        [Authorize]
        public async Task<IActionResult> Logout()
        {
            var user = await _service.Get(HttpContext.User.Claims.First(i => i.Type == "UserId").Value);
            return Ok();
        }
        
        // Checks if the username exists or not.
        [HttpPost("Exists")]
        public async Task<IActionResult> Exists([FromBody]string username)
        {
            var user = await _service.Get(username);
            if (user != null)
                return Ok();
            return NotFound();
        }
        
        /*
         * Registers the user to the server.
         */
        [HttpPost("Register")]
        public async Task<IActionResult> Register([FromBody] UserCred userCred)
        {
            // Adds the user to the service and creates claims for the user.
            await _service.AddUser(new User
            {
                Id = userCred.Username, Password = userCred.Password, Name = userCred.Nickname, Server = userCred.Server
            });

            var claims = new[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, _configuration["JWTParams:Subject"]),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString()),
                new Claim("UserId", userCred.Username)
            };
            var secretKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWTParams:SecretKey"]));
            var mac = new SigningCredentials(secretKey, SecurityAlgorithms.HmacSha256);
            var token = new JwtSecurityToken(
                _configuration["JWTParams:Issuer"],
                _configuration["JWTParams:Audience"],
                claims,
                expires: DateTime.UtcNow.AddMinutes(1),
                signingCredentials: mac);
            // Returns ok and creates the user.
            return Ok(new JwtSecurityTokenHandler().WriteToken(token));
        }
    }
}
