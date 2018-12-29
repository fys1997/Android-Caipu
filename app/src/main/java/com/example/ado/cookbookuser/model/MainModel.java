package com.example.ado.cookbookuser.model;



import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.Interface.MModel;
import com.example.ado.cookbookuser.network.DTO.IndexDto;
import com.example.ado.cookbookuser.network.RxJavaRetrofitUtilsIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class MainModel implements MModel {
    private Map<String, String> query = new HashMap<String, String>();
    private IndexDto dto = new IndexDto();
    private ArrayList<RecyclerItem> data = new ArrayList<RecyclerItem>();

    @Override
    public ArrayList<RecyclerItem> getData(String count, int id) {
        query.put("key", "0513f3cf3e6df9e3a40268ba6a43e3d0");
        query.put("pn",count);
        query.put("rn","8");
        data.clear();
        dto=RxJavaRetrofitUtilsIndex.getIndex().getCallBack(id,query);
        for (int i = 0; i < dto.getResult().getData().size(); i++) {
            RecyclerItem item=new RecyclerItem(dto.getResult().getData().get(i).getAlbums().get(0),dto.getResult().getData().get(i).getTitle(),dto.getResult().getData().get(i).getIngredients()+";"+dto.getResult().getData().get(i).getBurden()+";");
            item.setTags(dto.getResult().getData().get(i).getTags());
            item.seprateIngredients();
            item.seprateTags();
            data.add(item);
        }
        return data;
    }
}
