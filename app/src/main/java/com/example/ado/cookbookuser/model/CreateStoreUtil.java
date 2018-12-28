package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.view.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CreateStoreUtil {

    public void deleteCookbookFromCreate(int cookbookId){
        DataSupport.delete(CreateCookBook.class,cookbookId);
    }

    public void saveCookbookToCreate(CreateCookBook createCookBook){
        createCookBook.setUser(BaseActivity.userForNow);
        createCookBook.save();
    }

    public List<CreateCookBook> getCookbookFromCreate(){
        List<CreateCookBook> createCookBookList;
        int user_id = BaseActivity.userForNow.getId();
        String user_id_string = user_id + "";
        createCookBookList = DataSupport.where("user_id = ?",user_id_string).find(CreateCookBook.class);
        return createCookBookList;

    }
}
