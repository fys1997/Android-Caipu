package com.example.ado.cookbookuser.view.Interface;

import com.example.ado.cookbookuser.data.SearchRecyclerItem;

import java.util.ArrayList;

public interface SearchResultViewI {
    void setRecyclerItemData(ArrayList<SearchRecyclerItem>data,boolean setadapter);;
    void initRefreshListening();
    void setRecyclerItemClickListener();
}
