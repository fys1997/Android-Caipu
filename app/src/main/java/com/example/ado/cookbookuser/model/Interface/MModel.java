package com.example.ado.cookbookuser.model.Interface;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.network.DTO.IndexDto;

import java.util.ArrayList;

public interface MModel {
    ArrayList<RecyclerItem> getData(String count, int id);
}
