package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.Interface.MModel;
import com.example.ado.cookbookuser.model.MainModel;
import com.example.ado.cookbookuser.network.Api.IndexApi;
import com.example.ado.cookbookuser.network.DTO.IndexDto;
import com.example.ado.cookbookuser.view.Interface.MainViewI;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter  {
    private MModel model=new MainModel();
    private MainViewI mainViewI;
    private ArrayList<RecyclerItem>data=new ArrayList<RecyclerItem>();
    public MainPresenter(MainViewI viewI)
    {
        mainViewI=viewI;
    }


    public void GetAndSetMovieData(final String count, final int id)
    {
        Observable.create(new ObservableOnSubscribe<MainViewI>() {
            @Override
            public void subscribe(ObservableEmitter<MainViewI> e) throws Exception {
                data=model.getData(count, id);
                e.onNext(mainViewI);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<MainViewI>() {
                    @Override
                    public void accept(MainViewI viewI) throws Exception {
                        viewI.initRefrshListening();
                        viewI.setRecyclerItemData(data);
                    }
                });
        //data=model.getData(count, id);
        //mainViewI.setRecyclerItemData(data);
    }
    public void destroy(){
        mainViewI=null;
    }
}
