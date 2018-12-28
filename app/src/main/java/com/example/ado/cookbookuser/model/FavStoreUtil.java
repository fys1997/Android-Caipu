package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.view.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class FavStoreUtil {

    public void saveCookbookToFav(String cookbookName){
        FavCookBook newCookbook = new FavCookBook();
        newCookbook.setCookBook_name(cookbookName);
        newCookbook.setUser(BaseActivity.userForNow);
        newCookbook.save();
    }

    public void deleteCookbookFromFav(int cookbookId){
        DataSupport.delete(FavCookBook.class,cookbookId);
    }

    public List<FavCookBook> getAllCookbookFromFav(){
        List<FavCookBook> favCookBookList;
        int user_id = BaseActivity.userForNow.getId();
        String user_id_string = user_id + "";
        favCookBookList = DataSupport.where("user_id = ?",user_id_string).find(FavCookBook.class);
        return favCookBookList;
    }

}
