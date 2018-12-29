package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.Interface.CreateModelI;
import com.example.ado.cookbookuser.network.DTO.IDDto;
import com.example.ado.cookbookuser.network.DTO.MenuDto;
import com.example.ado.cookbookuser.network.RxJavaRetrofitUtilsID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionModel implements CreateModelI {
    private Map<String,String> query=new HashMap<>();
    private MenuDto dto=new MenuDto();
    private IDDto idDto=new IDDto();
    private String imageUrl;
    private ArrayList<RecyclerItem> data=new ArrayList<>();

    @Override
    public ArrayList<RecyclerItem> getData(List<FavCookBook> favCookBookList){
        query.clear();
        query.put("key", "0513f3cf3e6df9e3a40268ba6a43e3d0");
        data.clear();
        for (int i=0;i<favCookBookList.size();i++) {
            idDto = RxJavaRetrofitUtilsID.getId().getCallBack(favCookBookList.get(i).getCookBook_name_id(), query);
            RecyclerItem item=new RecyclerItem(idDto.getResult().getData().get(0).getAlbums(),idDto.getResult().getData().get(0).getTitle(),idDto.getResult().getData().get(0).getIngredients()+";"+idDto.getResult().getData().get(0).getBurden()+";");
            item.seprateIngredients();
            item.setId(favCookBookList.get(i).getCookBook_name_id());
            data.add(item);
        }
        return data;
    }
    @Override
    public ArrayList<RecyclerItem>getDatas(List<CreateCookBook>createCookBookList){
        for (int i=0;i<createCookBookList.size();i++){
            RecyclerItem item=new RecyclerItem(createCookBookList.get(i).getCover(),createCookBookList.get(i).getName(),createCookBookList.get(i).getMaterial());
            item.setId(createCookBookList.get(i).getId());
            data.add(item);
        }
        return data;
    }
}
