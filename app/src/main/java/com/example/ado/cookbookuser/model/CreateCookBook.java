package com.example.ado.cookbookuser.model;

import org.litepal.crud.DataSupport;

public class CreateCookBook extends DataSupport{

    private String name;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

