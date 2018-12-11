package com.example.ado.cookbookuser.network.Api;



import com.example.ado.cookbookuser.network.DTO.MenuDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MenuApi {
    @GET("query.php")
    Call<MenuDto> getCall(@QueryMap Map<String, String> query);
}
