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
        query.put("key", "58cbf7e9925f75636f1e9a4b214a010c");
        query.put("pn",count);
        query.put("rn","8");
        data.clear();
        dto=RxJavaRetrofitUtilsIndex.getIndex().getCallBack(id,query);
        for (int i = 0; i < dto.getResult().getData().size(); i++) {
            RecyclerItem item=new RecyclerItem(dto.getResult().getData().get(i).getAlbums().get(0),dto.getResult().getData().get(i).getTitle());
            data.add(item);
        }
        return data;
    }
}
