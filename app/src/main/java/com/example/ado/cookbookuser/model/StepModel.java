package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.Interface.SModel;
import com.example.ado.cookbookuser.network.DTO.MenuDto;
import com.example.ado.cookbookuser.network.RxJavaRetrofitUtilsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StepModel implements SModel {
    private Map<String,String> query=new HashMap<>();
    private MenuDto dto=new MenuDto();
    private ArrayList<RecyclerItem> data=new ArrayList<>();
    @Override
    public ArrayList<RecyclerItem> getData(String menu){
        query.put("key", "721ffff371fef1638076d2e53e4a09f2");
        query.put("menu",menu);
        data.clear();
        dto= RxJavaRetrofitUtilsMenu.getMenu().getCallBack(query);
        for (int i = 0; i < dto.getResult().getData().get(0).getSteps().size(); i++) {
            RecyclerItem item=new RecyclerItem(dto.getResult().getData().get(0).getSteps().get(i).getImg(),dto.getResult().getData().get(0).getSteps().get(i).getStep(),dto.getResult().getData().get(0).getIngredients()+";"+dto.getResult().getData().get(0).getBurden()+";");
            item.seprateIngredients();
            data.add(item);
        }
        return data;
    }
}
