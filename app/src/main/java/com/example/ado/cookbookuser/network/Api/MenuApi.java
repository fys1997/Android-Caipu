package com.example.ado.cookbookuser.network.Api;



import com.example.ado.cookbookuser.network.DTO.MenuDto;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MenuApi {
    @GET("query.php")
    Observable<MenuDto>getCall(@QueryMap Map<String, String> query);
}
