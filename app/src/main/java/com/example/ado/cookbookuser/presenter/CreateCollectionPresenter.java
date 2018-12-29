package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.CollectionModel;
import com.example.ado.cookbookuser.model.FavCookBook;
import com.example.ado.cookbookuser.model.Interface.CreateModelI;
import com.example.ado.cookbookuser.model.Interface.MModel;
import com.example.ado.cookbookuser.model.Interface.SModel;
import com.example.ado.cookbookuser.model.MainModel;
import com.example.ado.cookbookuser.view.Interface.CreateCollectionInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CreateCollectionPresenter {
    private CreateModelI model=new CollectionModel();
    private CreateCollectionInterface anInterface;
    private ArrayList<RecyclerItem>datas=new ArrayList<>();

    public CreateCollectionPresenter(CreateCollectionInterface anInterface){this.anInterface=anInterface;}
    public void getData(final List<FavCookBook>favCookBookList){
        Observable.create(new ObservableOnSubscribe<CreateCollectionInterface>() {
            @Override
            public void subscribe(ObservableEmitter<CreateCollectionInterface> e)throws  Exception{
                datas=model.getData(favCookBookList);
                e.onNext(anInterface);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<CreateCollectionInterface>() {
                    @Override
                    public void accept(CreateCollectionInterface createCollectionInterface) throws Exception {
                        createCollectionInterface.initRecyclerView(datas);
                        createCollectionInterface.setRecyclerItemClickListener();
                    }
                });
    }
    public void destroy(){anInterface=null;}
}
