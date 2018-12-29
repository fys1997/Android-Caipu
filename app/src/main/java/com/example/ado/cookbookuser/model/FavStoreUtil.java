package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.view.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class FavStoreUtil {

    public static void saveCookbookToFav(int cookbookName){
        FavCookBook newCookbook = new FavCookBook();
        newCookbook.setCookBook_name_id(cookbookName);
        newCookbook.setUser(BaseActivity.userForNow);
        newCookbook.save();
    }

    public static void deleteCookbookFromFav(int cookbookId){
        DataSupport.delete(FavCookBook.class,cookbookId);
    }

    public static List<FavCookBook> getAllCookbookFromFav(){
        List<FavCookBook> favCookBookList;
        int user_id = BaseActivity.userForNow.getId();
        favCookBookList = DataSupport.where("user_id = ?",String.valueOf(user_id)).find(FavCookBook.class);
        return favCookBookList;
    }

}
