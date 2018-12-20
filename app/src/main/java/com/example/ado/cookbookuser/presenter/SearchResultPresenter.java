package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.data.SearchRecyclerItem;
import com.example.ado.cookbookuser.model.Interface.SearchModel;
import com.example.ado.cookbookuser.model.SearchMode;
import com.example.ado.cookbookuser.view.Interface.SearchResultViewI;

import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchResultPresenter {
   private SearchModel model=new SearchMode();
   private SearchResultViewI viewI;
   private ArrayList<SearchRecyclerItem>data=new ArrayList<>();
   public void operations(final String count,final String menu){
       Observable.create(new ObservableOnSubscribe<SearchResultViewI>() {
           @Override
           public void subscribe(ObservableEmitter<SearchResultViewI>e)throws Exception{
               data=model.getData(count, menu);
               e.onNext(viewI);
           }
       }).observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(new Consumer<SearchResultViewI>() {
                   @Override
                   public void accept(SearchResultViewI searchResultViewI) throws Exception {
                       viewI.initRefreshListening();
                       viewI.setRecyclerItemData(data);
                   }
               });
   }
   public void destroy(){
       viewI=null;
   }
}
