using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using RatingProj.Data;
using RatingProj.Models;

namespace RatingProj
{
    public class Comments : Controller
    {
        private readonly RatingProjContext _context;

        public Comments(RatingProjContext context)
        {
            _context = context;
        }

        // GET: Comments
        // public async Task<IActionResult> Index()
        // {
        //       return _context.CommentModel != null ? 
        //                   View(await _context.CommentModel.ToListAsync()) :
        //                   Problem("Entity set 'RatingProjContext.CommentModel'  is null.");
        // }
        
        
        // GET: Comments/Search
        public async Task<IActionResult> Index()
        {
            return _context.CommentModel != null ? 
                View(await _context.CommentModel.ToListAsync()) :
                Problem("Entity set 'RatingProjContext.CommentModel'  is null.");
        }
        [HttpPost]
        public async Task<IActionResult> Index(string query)
        {
            var q = from comment in _context.CommentModel 
                where comment.Name.Contains(query) || comment.FeedbackText.Contains(query)
                select comment;
            return View(await q.ToListAsync());
        }

        public async Task<IActionResult> Search2(string query)
        {
            var q = _context.CommentModel.Where(comment => comment.Name.Contains(query) ||
                                                           comment.FeedbackText.Contains(query));
            return PartialView(await q.ToListAsync());
        }

        // GET: Comments/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.CommentModel == null)
            {
                return NotFound();
            }

            var commentModel = await _context.CommentModel
                .FirstOrDefaultAsync(m => m.Id == id);
            if (commentModel == null)
            {
                return NotFound();
            }

            return View(commentModel);
        }

        // GET: Comments/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Comments/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Name,Stars,FeedbackText")] CommentModel commentModel)
        {
            commentModel.DateTime=DateTime.Now;
            if (ModelState.IsValid)
            {
                _context.Add(commentModel);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(commentModel);
        }

        // GET: Comments/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.CommentModel == null)
            {
                return NotFound();
            }

            var commentModel = await _context.CommentModel.FindAsync(id);
            if (commentModel == null)
            {
                return NotFound();
            }
            return View(commentModel);
        }

        // POST: Comments/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Name,Stars,FeedbackText")] CommentModel commentModel)
        {
            if (id != commentModel.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(commentModel);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CommentModelExists(commentModel.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(commentModel);
        }

        // GET: Comments/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.CommentModel == null)
            {
                return NotFound();
            }

            var commentModel = await _context.CommentModel
                .FirstOrDefaultAsync(m => m.Id == id);
            if (commentModel == null)
            {
                return NotFound();
            }

            return View(commentModel);
        }

        // POST: Comments/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.CommentModel == null)
            {
                return Problem("Entity set 'RatingProjContext.CommentModel'  is null.");
            }
            var commentModel = await _context.CommentModel.FindAsync(id);
            if (commentModel != null)
            {
                _context.CommentModel.Remove(commentModel);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CommentModelExists(int id)
        {
          return (_context.CommentModel?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
