package com.example.ado.cookbookuser.network.Api;

import com.example.ado.cookbookuser.network.DTO.IDDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IDApi {
    @GET("queryid")
    Call<IDDto>getCall(@Query("id")int id, @QueryMap Map<String,String>query);
}
