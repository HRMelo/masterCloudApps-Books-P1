package com.hrmelo.books.services.impl;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.hrmelo.books.exceptions.BookException;
import com.hrmelo.books.models.Book;
import com.hrmelo.books.models.Comment;
import com.hrmelo.books.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOK_NOT_FOUND = "That book does not exist";

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextBookId = new AtomicLong();

    public BookServiceImpl() {
        createBook(new Book("Lord of the Rings", "J.R.R. Tolkien", "resume", "editorial", 1954));
        createBook(new Book("Game of Thrones", "George R.R. Martin", "resume", "editorial", 1996));
        createBook(new Book("Teo va al Zoo", "Violeta Denou", "resume", "editorial", 2005));
    }

    @Override
    public Collection<Book> getAllBooks() {
        return this.books.values();
    }

    @Override
    public Book getBook(long bookId) {
        return this.books.get(bookId);
    }

    @Override
    public Book createBook(Book book) {
        long bookId = nextBookId.getAndIncrement();
        book.setId(bookId);
        this.books.put(bookId, book);
        return this.books.get(bookId);
    }

    @Override
    public Book addComment(long bookId, Comment comment) throws BookException {
        if(this.books.get(bookId) == null) {
            throw new BookException(BOOK_NOT_FOUND);
        }
        this.books.get(bookId).createComment(comment);
        return this.books.get(bookId);
    }

    @Override
    public Book deleteComment(long bookId, long commentId) throws BookException {
        if(this.books.get(bookId) == null) {
            throw new BookException(BOOK_NOT_FOUND);
        }
        this.books.get(bookId).deleteComment(commentId);
        return this.books.get(bookId);
    }

    @Override
    public void deleteBook(long bookId) {
        this.books.remove(bookId);
    }
}
