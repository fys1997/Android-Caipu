package com.example.ado.cookbookuser.model;

import org.litepal.crud.DataSupport;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


//用户（数据库相关）
public class User extends DataSupport{

    private int id;
    private String name;
    private String password;
    private String gender; //0为未设定，1为男，2为女
    private byte[] headShot;
    private String birthday;
    private List<FavCookBook> cookBookList = new ArrayList<>();
    private List<CreateCookBook> createCookBookList = new ArrayList<>();

    public int getId() { return id; }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getGender() { return gender; }

    public byte[] getHeadShot() {return headShot; }

    public String getBirthday() { return birthday; }

    public List<FavCookBook> getCookBookList() { return cookBookList; }

    public List<CreateCookBook> getCreateCookBookList() { return createCookBookList; }

    public void setId(int id) { this.id = id; }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setGender(String gender) { this.gender = gender; }

    public void setHeadShot(byte[] headShot) {
        this.headShot = headShot;
    }

    public void setBirthday(String birthday) { this.birthday = birthday; }

    public void setCookBookList(List<FavCookBook> cookBookList) { this.cookBookList = cookBookList; }

    public void setCreateCookBookList(List<CreateCookBook> createCookBookList) { this.createCookBookList = createCookBookList; }

}
