package com.example.ado.cookbookuser.data;

import java.util.ArrayList;

public class SearchRecyclerItem {
    private int id;
    private String imgsrc;
    private String description;
    private String ingredients;
    private String tags;
    private ArrayList<String> IngredientsName=new ArrayList<>();
    private ArrayList<String>IngredientsNumber=new ArrayList<>();
    public SearchRecyclerItem(String img,String des,String ingredients)
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
    public void seprateIngredients(){
        boolean nameOrnumber=true;
        String name=new String();
        String number=new String();
        for(int i=0;i<ingredients.length();i++){
            if(ingredients.charAt(i)==','&&nameOrnumber){
                IngredientsName.add(name);
                nameOrnumber=false;
                name=new String();
            }
            else if(nameOrnumber){
                name=name+ingredients.charAt(i);
            }
            else if(ingredients.charAt(i)==';'&&!nameOrnumber){
                IngredientsNumber.add(number);
                number=new String();
                nameOrnumber=true;
            }
            else {
                number=number+ingredients.charAt(i);
            };
        }
        ingredients=new String();
        for(int i=0;i<IngredientsName.size();i++){
            ingredients=ingredients+IngredientsName.get(i)+" ";
        }
    }
    public void setTags(String Tags){tags=Tags;}
    public void seprateTags(){
        String newTags=new String();
        int count=0;
        for(int i=0;i<tags.length();i++){
            if(tags.charAt(i)==';'){
                newTags=newTags+" ";
                count++;
            }
            else
            {
                newTags=newTags+tags.charAt(i);
            }
            if(count==3)
                break;
        }
        tags=newTags;
    }
    public String getTags(){return tags;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
