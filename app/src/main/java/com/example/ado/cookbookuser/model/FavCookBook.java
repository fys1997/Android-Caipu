package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.model.User;

import org.litepal.crud.DataSupport;

//收藏的食谱（数据库相关）
public class FavCookBook extends DataSupport {
    private int cookBook_name_id;
    private User user;

    public int getCookBook_name_id() {
        return cookBook_name_id;
    }

    public void setCookBook_name_id(int cookBook_name_id) {
        this.cookBook_name_id = cookBook_name_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
