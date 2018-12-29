package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.model.CreateCookBook;
import com.example.ado.cookbookuser.model.CreateStoreUtil;
import com.example.ado.cookbookuser.model.FavCookBook;
import com.example.ado.cookbookuser.model.FavStoreUtil;
import com.example.ado.cookbookuser.presenter.CreateCollectionPresenter;
import com.example.ado.cookbookuser.view.Interface.CreateCollectionInterface;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;
import com.example.ado.cookbookuser.view.adapter.CreateCollectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class CollectionCreateActivity extends BaseActivity implements CreateCollectionInterface {
    private RecyclerView recyclerView;
    private List<FavCookBook> favCookBookList;
    private List<CreateCookBook> createCookBookList;
    private CreateCollectionPresenter presenter;
    private Toolbar toolbarCollection;
    private CreateCollectionAdapter recyclerViewadapter;
    private String which;
    private TextView displayTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_collection_page);
        Intent intent=getIntent();
        which=intent.getStringExtra("which");
        toolbarCollection = findViewById(R.id.toolbar_collection);
        setToolbar(toolbarCollection);

        displayTitle = findViewById(R.id.display_title);

        presenter=new CreateCollectionPresenter(this);
        if(which.equals("like")) {
            displayTitle.setText("我的收藏");
            favCookBookList = FavStoreUtil.getAllCookbookFromFav();
            presenter.getData(favCookBookList);
        } else{
            displayTitle.setText("我的创建");
            createCookBookList = CreateStoreUtil.getCookbookFromCreate();
            presenter.getDatas(createCookBookList);
        }

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
    @Override
    public void setRecyclerItemClickListener(){
        recyclerViewadapter=(CreateCollectionAdapter)recyclerView.getAdapter();
        if(which.equals("like")) {
            recyclerViewadapter.setOnItemClickListener(new OnClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(CollectionCreateActivity.this, StepActivity.class);
                    ArrayList<Integer> list = new ArrayList<>();
                    for (int i = 0; i < recyclerViewadapter.getData().size(); i++) {
                        list.add(recyclerViewadapter.getData().get(i).getId());
                    }
                    intent.putExtra("name", Integer.toString(recyclerViewadapter.getData().get(position).getId()));
                    intent.putExtra("type", "1");//1代表网络请求调用使用id查值
                    intent.putExtra("position", Integer.toString(position));
                    intent.putIntegerArrayListExtra("list", list);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        else if(which.equals("create")){
            recyclerViewadapter.setOnItemClickListener(new OnClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent=new Intent(CollectionCreateActivity.this,ShowCreateActivity.class);
                    intent.putExtra("id",recyclerViewadapter.getData().get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
