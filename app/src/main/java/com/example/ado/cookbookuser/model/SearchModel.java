package com.example.ado.cookbookuser.model;

import com.example.ado.cookbookuser.data.SearchRecyclerItem;
import com.example.ado.cookbookuser.network.DTO.MenuDto;
import com.example.ado.cookbookuser.network.RxJavaRetrofitUtilsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchModel implements com.example.ado.cookbookuser.model.Interface.SearchModel {
    private Map<String,String>query=new HashMap<>();
    private MenuDto dto=new MenuDto();
    private ArrayList<SearchRecyclerItem>data=new ArrayList<>();
    @Override
    public ArrayList<SearchRecyclerItem>getData(String count,String menu){
        query.put("key", "0513f3cf3e6df9e3a40268ba6a43e3d0");
        query.put("menu",menu);
        query.put("pn",count);
        query.put("rn","8");
        data.clear();
        dto=RxJavaRetrofitUtilsMenu.getMenu().getCallBack(query);
        if(Integer.parseInt(dto.getResultcode())==200) {
            for (int i = 0; i < dto.getResult().getData().size(); i++) {
                SearchRecyclerItem item = new SearchRecyclerItem(dto.getResult().getData().get(i).getAlbums().get(0), dto.getResult().getData().get(i).getTitle(), dto.getResult().getData().get(i).getIngredients() + ";" + dto.getResult().getData().get(i).getBurden() + ";");
                item.setTags(dto.getResult().getData().get(i).getTags());
                item.seprateIngredients();
                item.seprateTags();
                item.setId(dto.getResult().getData().get(i).getId());
                data.add(item);
            }
        }
        return data;
    }
}
