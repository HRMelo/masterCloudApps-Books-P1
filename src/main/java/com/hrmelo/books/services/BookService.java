package com.hrmelo.books.services;

import java.util.Collection;

import com.hrmelo.books.exceptions.BookException;
import com.hrmelo.books.models.Book;
import com.hrmelo.books.models.Comment;

public interface BookService {

    Collection<Book> getAllBooks();

    Book getBook(long bookId);

    Book createBook(Book book);

    Book addComment(long bookId, Comment comment) throws BookException;

    Book deleteComment(long bookId, long commentId) throws BookException;

    void deleteBook(long bookId);
}
