package com.example.ado.cookbookuser.network;


import com.example.ado.cookbookuser.network.Api.IDApi;
import com.example.ado.cookbookuser.network.DTO.IDDto;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaRetrofitUtilsID {
    private static final String Base_url="http://apis.juhe.cn/cook/";
    public static RxJavaRetrofitUtilsID id=new RxJavaRetrofitUtilsID();
    public static RxJavaRetrofitUtilsID getId(){return id;}
    private IDDto idDto=new IDDto();
    private IDApi idApi;
    public IDDto getCallBack(int id, Map<String,String>query){
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Base_url)
                .build();
        idApi=retrofit.create(IDApi.class);
        Call<IDDto>idDtoCall=idApi.getCall(id, query);
        try{
            Response<IDDto>response=idDtoCall.execute();
            idDto=response.body();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return idDto;
    }
}
