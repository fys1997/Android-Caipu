package com.example.ado.cookbookuser.model.Interface;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.FavCookBook;

import java.util.ArrayList;
import java.util.List;

public interface CreateModelI {
    ArrayList<RecyclerItem> getData(List<FavCookBook> favCookBookList);
}
