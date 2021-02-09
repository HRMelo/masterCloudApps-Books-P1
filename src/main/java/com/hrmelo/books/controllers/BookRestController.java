package com.hrmelo.books.controllers;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrmelo.books.exceptions.BookException;
import com.hrmelo.books.models.Book;
import com.hrmelo.books.models.Comment;
import com.hrmelo.books.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(
        		responseCode = "200", 
        		description = "Lists with all books", 
        		content = { @Content(
    				mediaType = "application/json", 
    				schema = @Schema(implementation = Book.class)
				)
            })
    })
    @JsonView(Book.Simplified.class)
    @GetMapping("/")
    public Collection<Book> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @Operation(summary = "Get a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(
        		responseCode = "200", 
        		description = "Found book", 
        		content = { @Content(
    				mediaType = "application/json", 
    				schema = @Schema(implementation = Book.class)
				)
            }),
            @ApiResponse(
        		responseCode = "404", 
        		description = "Book not found", 
        		content = @Content)
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(
    		@Parameter(description = "BookID to be searched") @PathVariable long bookId) {
        Book book = this.bookService.getBook(bookId);
        if(book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(
        		responseCode = "201", 
        		description = "Created book", 
        		content = { @Content(
    				mediaType = "application/json", 
    				schema = @Schema(implementation = Book.class))
            })
    })
    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book bookCreated = this.bookService.createBook(book);
        return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(bookCreated.getId()).toUri()).body(bookCreated);
    }

    @Operation(summary = "Create a new comment for a book")
    @ApiResponses(value = {
            @ApiResponse(
        		responseCode = "201", 
        		description = "Created comment", 
        		content = { @Content(
    				mediaType = "application/json", 
    				schema = @Schema(implementation = Book.class))
            }),
            @ApiResponse(
            		responseCode = "404",
            		description = "Book not Found",
            		content = @Content)
    })
    @PostMapping("/{bookId}/comments/")
    public ResponseEntity<Book> createComment(
    		@Parameter(description = "BookID to add the comment") @PathVariable long bookId, 
    		@RequestBody Comment comment) throws BookException {
        Book book = this.bookService.addComment(bookId, comment);
        URI location = fromCurrentRequest().path("/{bookId}").build(bookId);
        return ResponseEntity.created(location).body(book);
    }

    @Operation(summary = "Delete a comment by its bookID and its commentID")
    @ApiResponses(value = {
            @ApiResponse(
        		responseCode = "200", 
        		description = "Deleted comment", 
        		content = { @Content(
    				mediaType = "application/json", 
    				schema = @Schema(implementation = Book.class))
            }),
            @ApiResponse(
        		responseCode = "404", 
        		description = "Book not found", 
        		content = @Content)
    })
    @DeleteMapping("/{bookId}/comments/{commentId}")
    public ResponseEntity<Book> deleteComment(
    		@Parameter(description = "BookID to find its comment") @PathVariable long bookId, 
    		@Parameter(description = "CommentID to delete") @PathVariable long commentId) throws BookException {
        Book book = this.bookService.deleteComment(bookId, commentId);
        return ResponseEntity.ok(book);
    }
}
