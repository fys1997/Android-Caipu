package com.example.ado.cookbookuser.model.Interface;

import com.example.ado.cookbookuser.data.SearchRecyclerItem;

import java.util.ArrayList;

public interface SearchModel {
     ArrayList<SearchRecyclerItem>getData(String count,String menu);
}
