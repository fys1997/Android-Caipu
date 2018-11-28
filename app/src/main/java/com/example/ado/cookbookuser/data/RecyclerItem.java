package com.example.ado.cookbookuser.data;

import android.net.Uri;

import java.net.URL;

public class RecyclerItem {
    private String imgsrc;
    private String description;
    public RecyclerItem(String img,String des)
    {
        this.imgsrc=img;
        this.description=des;
    }
    public void setData(String i,String d)
    {
        this.imgsrc=i;
        this.description=d;
    }

    public String getDescription() {
        return description;
    }

    public String getImgsrc() {
        return imgsrc;
    }
}
