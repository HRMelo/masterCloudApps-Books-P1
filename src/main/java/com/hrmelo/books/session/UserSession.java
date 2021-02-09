package com.hrmelo.books.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private String user;

    private int numComments;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumComments() {
        return numComments;
    }

    public void incNumComments() {
        this.numComments++;
    }
}
