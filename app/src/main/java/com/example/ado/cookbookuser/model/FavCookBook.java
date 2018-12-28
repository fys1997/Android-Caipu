package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.model.User;

import org.litepal.crud.DataSupport;

public class FavCookBook extends DataSupport {
    private String cookBook_name;
    private User user;

    public String getCookBook_name() {
        return cookBook_name;
    }

    public void setCookBook_name(String cookBook_name) {
        this.cookBook_name = cookBook_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
