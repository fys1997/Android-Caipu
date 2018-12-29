package com.example.ado.cookbookuser.view.Interface;

import com.example.ado.cookbookuser.data.RecyclerItem;

import java.util.ArrayList;

public interface CreateCollectionInterface {
    void initRecyclerView(ArrayList<RecyclerItem>datas);
    void setRecyclerItemClickListener();
}
