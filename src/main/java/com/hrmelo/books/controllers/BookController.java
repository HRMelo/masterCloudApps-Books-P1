package com.hrmelo.books.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hrmelo.books.exceptions.BookException;
import com.hrmelo.books.models.Book;
import com.hrmelo.books.models.Comment;
import com.hrmelo.books.services.BookService;
import com.hrmelo.books.session.UserSession;

@Controller
public class BookController {

    private BookService bookService;

    private UserSession userSession;
    
    public BookController(BookService bookService, UserSession userSession) {
    	this.bookService = bookService;
    	this.userSession = userSession;
	}

    @GetMapping("/")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/book/{bookId}")
    public String showBook(Model model, @PathVariable long bookId) {
        model.addAttribute("book", this.bookService.getBook(bookId));
        model.addAttribute("user", this.userSession.getUser());
        model.addAttribute("noComments", this.bookService.getBook(bookId).getComments().isEmpty());

        return "book";
    }

    @GetMapping("/book/new")
    public String newBookForm() {
        return "new_bookForm";
    }

    @PostMapping("/book/new")
    public String newBook(Book book) {
        this.bookService.createBook(book);

        return "created_book";
    }

    @PostMapping("/book/comment/new/{bookId}")
    public String newComment(Model model, @PathVariable long bookId, Comment comment) throws BookException {
        this.bookService.addComment(bookId, comment);

        this.userSession.setUser(comment.getUser());
        this.userSession.incNumComments();

        model.addAttribute("bookId", bookId);
        return "created_comment";
    }

    @GetMapping("/book/{bookId}/comment/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable long bookId, @PathVariable long commentId) throws BookException {
        this.bookService.deleteComment(bookId,commentId);

        model.addAttribute("bookId", bookId);

        return "deleted_comment";
    }
}
