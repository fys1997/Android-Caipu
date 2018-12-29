package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.Interface.SModel;
import com.example.ado.cookbookuser.network.DTO.IDDto;
import com.example.ado.cookbookuser.network.DTO.MenuDto;
import com.example.ado.cookbookuser.network.RxJavaRetrofitUtilsID;
import com.example.ado.cookbookuser.network.RxJavaRetrofitUtilsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StepModel implements SModel {
    private Map<String,String> query=new HashMap<>();
    private MenuDto dto=new MenuDto();
    private IDDto idDto=new IDDto();
    private String imageUrl;
    private ArrayList<RecyclerItem> data=new ArrayList<>();
    @Override
    public ArrayList<RecyclerItem> getData(String menu){
        query.clear();
        query.put("key", "0513f3cf3e6df9e3a40268ba6a43e3d0");
        query.put("menu",menu);
        data.clear();
        dto= RxJavaRetrofitUtilsMenu.getMenu().getCallBack(query);
        imageUrl=dto.getResult().getData().get(0).getAlbums().get(0);
        for (int i = 0; i < dto.getResult().getData().get(0).getSteps().size(); i++) {
            RecyclerItem item=new RecyclerItem(dto.getResult().getData().get(0).getSteps().get(i).getImg(),dto.getResult().getData().get(0).getSteps().get(i).getStep(),dto.getResult().getData().get(0).getIngredients()+";"+dto.getResult().getData().get(0).getBurden()+";");
            item.seprateIngredients();
            item.setTitlle(dto.getResult().getData().get(0).getTitle());
            item.setId(dto.getResult().getData().get(0).getId());
            data.add(item);
        }
        return data;
    }
    @Override
    public String getImageUrl() {
        return imageUrl;
    }
    @Override
    public ArrayList<RecyclerItem>getData(int id){
        query.clear();
        query.put("key", "0513f3cf3e6df9e3a40268ba6a43e3d0");
        data.clear();
        idDto=RxJavaRetrofitUtilsID.getId().getCallBack(id,query);
        imageUrl=idDto.getResult().getData().get(0).getAlbums().get(0);
        for(int i=0;i<idDto.getResult().getData().get(0).getSteps().size();i++){
            RecyclerItem item=new RecyclerItem(idDto.getResult().getData().get(0).getSteps().get(i).getImg(),idDto.getResult().getData().get(0).getSteps().get(i).getStep(),idDto.getResult().getData().get(0).getIngredients()+";"+idDto.getResult().getData().get(0).getBurden()+";");
            item.setTitlle(idDto.getResult().getData().get(0).getTitle());
            item.seprateIngredients();
            item.setId(id);
            data.add(item);
        }
        return data;
    }
}
