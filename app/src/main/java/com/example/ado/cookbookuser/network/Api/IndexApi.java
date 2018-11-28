package com.example.ado.cookbookuser.network.Api;

import com.example.ado.cookbookuser.network.DTO.IndexDto;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IndexApi {
    @GET("index")
    Call<IndexDto> getCall(@Query("cid") int cid, @QueryMap Map<String, String> query);
}
