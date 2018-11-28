package com.example.ado.cookbookuser.network;


import com.example.ado.cookbookuser.network.Api.IndexApi;
import com.example.ado.cookbookuser.network.DTO.IndexDto;


import java.io.IOException;
import java.util.Map;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaRetrofitUtilsIndex {
    private static final String Base_url="http://apis.juhe.cn/cook/";
    public static RxJavaRetrofitUtilsIndex index=new RxJavaRetrofitUtilsIndex();
    public static RxJavaRetrofitUtilsIndex getIndex(){return index;}
    private IndexDto indexDto=new IndexDto();
    private  IndexApi indexApi;
  public  IndexDto getCallBack(int cid,Map<String,String>query)
   {
       Retrofit retrofit=new Retrofit.Builder()
               .addConverterFactory(GsonConverterFactory.create())
               .baseUrl(Base_url)
               .build();
       indexApi=retrofit.create(IndexApi.class);
       Call<IndexDto> indexDtoCall=indexApi.getCall(cid, query);
       try{
           Response<IndexDto> response=indexDtoCall.execute();
           indexDto=response.body();
       }
       catch (IOException e)
       {
           e.printStackTrace();
       }
       return indexDto;
   }

}
