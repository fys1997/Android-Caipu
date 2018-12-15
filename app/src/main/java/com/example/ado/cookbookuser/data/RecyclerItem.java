package com.example.ado.cookbookuser.data;



public class RecyclerItem {
    private String imgsrc;
    private String description;
    private String ingredients;
    public RecyclerItem(String img,String des,String ingredients)
    {
        this.imgsrc=img;
        this.description=des;
        this.ingredients=ingredients;
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

    public String getIngredients() {
        return ingredients;
    }
}
