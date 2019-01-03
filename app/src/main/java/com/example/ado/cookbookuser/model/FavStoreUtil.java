package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.view.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class FavStoreUtil {

    //将收藏的食谱保存到数据库
    public static void saveCookbookToFav(int cookbookName){
        FavCookBook newCookbook = new FavCookBook();
        newCookbook.setCookBook_name_id(cookbookName);
        newCookbook.setUser(BaseActivity.userForNow);
        newCookbook.save();
    }

    //将收藏的食谱从数据库中删除
    public static void deleteCookbookFromFav(int cookbookId){
        DataSupport.delete(FavCookBook.class,cookbookId);
    }

    //从数据库中获取全部的食谱
    public static List<FavCookBook> getAllCookbookFromFav(){
        List<FavCookBook> favCookBookList;
        int user_id = BaseActivity.userForNow.getId();
        favCookBookList = DataSupport.where("user_id = ?",String.valueOf(user_id)).find(FavCookBook.class);
        return favCookBookList;
    }

    //判断该食谱是否已收藏
    public static boolean isCookbookInFav(int cookbookId){
        if(BaseActivity.userForNow == null) return false;
        List<FavCookBook> favCookBookList = getAllCookbookFromFav();
        for(int i = 0;i<favCookBookList.size();i++){
            if(favCookBookList.get(i).getCookBook_name_id() == cookbookId){
                return true;
            }
        }
        return false;

    }

    //将收藏的食谱从数据库中删除
    public static void deleteByCookId(int cookbook_name_id){
        int user_id = BaseActivity.userForNow.getId();
        DataSupport.deleteAll(FavCookBook.class,"user_id = ? and cookBook_name_id = ?",String.valueOf(user_id),String.valueOf(cookbook_name_id));
    }

}
