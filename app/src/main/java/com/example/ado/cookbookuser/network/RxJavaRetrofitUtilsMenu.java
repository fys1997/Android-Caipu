package com.example.ado.cookbookuser.network;

import com.example.ado.cookbookuser.network.Api.MenuApi;
import com.example.ado.cookbookuser.network.DTO.MenuDto;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaRetrofitUtilsMenu {
    private static final String Base_url="http://apis.juhe.cn/cook/";
    private static RxJavaRetrofitUtilsMenu utils=new RxJavaRetrofitUtilsMenu();
    MenuApi menuApi;
    public static RxJavaRetrofitUtilsMenu getUtils(){
        return utils;
    }
    public RxJavaRetrofitUtilsMenu(){
        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        menuApi =retrofit.create(MenuApi.class);
    }
    //提供接口方法
    public void getCallBack(Map<String,String> query,final CallBack call)
    {
        Observable<MenuDto>menuDtoObservable= menuApi.getCall(query);
        menuDtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MenuDto>() {
                    @Override
                    public void accept(MenuDto menuDto) throws Exception {
                        if(menuDto==null)
                            call.onError();
                        else
                            call.onSuccess(menuDto);
                    }
                });
    }
    //这是一个回调接口
    public interface  CallBack{
        void onSuccess(MenuDto dto);
        void onError();
    }
}
