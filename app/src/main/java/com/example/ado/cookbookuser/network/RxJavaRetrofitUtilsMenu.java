package com.example.ado.cookbookuser.network;

import com.example.ado.cookbookuser.network.Api.MenuApi;
import com.example.ado.cookbookuser.network.DTO.MenuDto;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaRetrofitUtilsMenu {
    private static final String Base_url="http://apis.juhe.cn/cook/";
    public static RxJavaRetrofitUtilsMenu menu=new RxJavaRetrofitUtilsMenu();
    public static RxJavaRetrofitUtilsMenu getMenu(){return menu;}
    private MenuDto menuDto=new MenuDto();
    private MenuApi menuApi;
    public MenuDto getCallBack(Map<String,String> query){
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Base_url)
                .build();
        menuApi=retrofit.create(MenuApi.class);
        Call<MenuDto>menuDtoCall=menuApi.getCall(query);
        try{
            Response<MenuDto>response=menuDtoCall.execute();
            menuDto=response.body();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return menuDto;
    }
}
