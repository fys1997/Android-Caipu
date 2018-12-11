package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.Interface.SModel;
import com.example.ado.cookbookuser.model.StepModel;
import com.example.ado.cookbookuser.view.Interface.StepViewI;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class StepPresenter {
    private SModel model=new StepModel();
    private StepViewI stepViewI;
    private ArrayList<RecyclerItem> datas=new ArrayList<>();
    public StepPresenter(StepViewI viewI){stepViewI=viewI;}
    public void operation(final String menu){
        Observable.create(new ObservableOnSubscribe<StepViewI>() {
            @Override
            public void subscribe(ObservableEmitter<StepViewI> e) throws Exception {
                datas=model.getData(menu);
                e.onNext(stepViewI);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<StepViewI>() {
                    @Override
                    public void accept(StepViewI viewI) throws Exception {
                        viewI.initStepUI(datas);
                    }
                });
    }
    public void destroy(){stepViewI=null;}
}
