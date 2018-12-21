package com.example.ado.cookbookuser.model.Interface;

import com.example.ado.cookbookuser.data.RecyclerItem;

import java.util.ArrayList;

public interface SModel {
    ArrayList<RecyclerItem> getData(String menu);
    String getImageUrl();
    ArrayList<RecyclerItem>getData(int id);
}
