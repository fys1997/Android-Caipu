package com.example.ado.cookbookuser.view.Interface;

import com.example.ado.cookbookuser.data.SearchRecyclerItem;

import java.util.ArrayList;

public interface SearchResultViewI {
    void setRecyclerItemData(ArrayList<SearchRecyclerItem>data);;
    void initRefreshListening();
    void setRecyclerItemClickListener();
}
