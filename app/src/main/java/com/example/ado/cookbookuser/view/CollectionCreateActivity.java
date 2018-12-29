package com.example.ado.cookbookuser.view;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.FavCookBook;
import com.example.ado.cookbookuser.model.FavStoreUtil;
import com.example.ado.cookbookuser.presenter.CreateCollectionPresenter;
import com.example.ado.cookbookuser.view.Interface.CreateCollectionInterface;
import com.example.ado.cookbookuser.view.adapter.CreateCollectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class CollectionCreateActivity extends BaseActivity implements CreateCollectionInterface {
    private RecyclerView recyclerView;
    private List<FavCookBook> favCookBookList;
    private CreateCollectionPresenter presenter;
    private Toolbar toolbarCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_collection_page);
        toolbarCollection = findViewById(R.id.toolbar_collection);
        setToolbar(toolbarCollection);
        favCookBookList = FavStoreUtil.getAllCookbookFromFav();
        presenter=new CreateCollectionPresenter(this);
        presenter.getData(favCookBookList);
    }

    @Override
    public void initRecyclerView(ArrayList<RecyclerItem>datas){
        recyclerView=findViewById(R.id.create_collection_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        CreateCollectionAdapter adapter=new CreateCollectionAdapter(this.getBaseContext(),R.layout.create_collection_caipu_page_item,datas);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter!=null){
            presenter.destroy();
            presenter=null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }
}
