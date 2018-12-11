package com.example.ado.cookbookuser.view.Interface;

import com.example.ado.cookbookuser.data.RecyclerItem;

import java.util.ArrayList;

public interface MainViewI  {
    void setRecyclerItemData(ArrayList<RecyclerItem> data);
    void initRefrshListening();
    void setRecyclerItemClickListener();
}
