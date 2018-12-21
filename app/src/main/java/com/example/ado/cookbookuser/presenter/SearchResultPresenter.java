package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.data.SearchRecyclerItem;
import com.example.ado.cookbookuser.model.SearchModel;
import com.example.ado.cookbookuser.view.Interface.SearchResultViewI;

import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchResultPresenter {
   private com.example.ado.cookbookuser.model.Interface.SearchModel model=new SearchModel();
   private SearchResultViewI viewI;
   private ArrayList<SearchRecyclerItem>data=new ArrayList<>();
   public SearchResultPresenter(SearchResultViewI searchResultViewI){viewI=searchResultViewI;}
   public void operations(final String count,final String menu,final boolean setadapter){
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
                       if (!data.isEmpty())
                       viewI.initRefreshListening();
                       viewI.setRecyclerItemData(data,setadapter);
                   }
               });
   }
   public void destroy(){
       viewI=null;
   }
}
