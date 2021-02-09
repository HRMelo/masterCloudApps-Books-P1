package com.hrmelo.books.exceptions;

public class BookException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6407276381858196620L;

	public BookException() {
        super();
    }

    public BookException(String msg) {
        super(msg);
    }

    public BookException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
