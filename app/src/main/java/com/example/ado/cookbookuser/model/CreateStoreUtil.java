package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.view.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CreateStoreUtil {

    public static void deleteCookbookFromCreate(int cookbookId){
        DataSupport.delete(CreateCookBook.class,cookbookId);
    }

    public static void saveCookbookToCreate(CreateCookBook createCookBook){
        createCookBook.setUser(BaseActivity.userForNow);
        createCookBook.save();
    }

    public static List<CreateCookBook> getCookbookFromCreate(){
        List<CreateCookBook> createCookBookList;
        int user_id = BaseActivity.userForNow.getId();
        createCookBookList = DataSupport.where("user_id = ?",String.valueOf(user_id)).find(CreateCookBook.class);
        return createCookBookList;

    }
}
