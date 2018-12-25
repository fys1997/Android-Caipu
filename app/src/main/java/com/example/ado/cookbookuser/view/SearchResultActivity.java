package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.SearchRecyclerItem;
import com.example.ado.cookbookuser.presenter.SearchResultPresenter;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;
import com.example.ado.cookbookuser.view.Interface.SearchResultViewI;
import com.example.ado.cookbookuser.view.adapter.SearchResultAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity implements SearchResultViewI {
    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private SearchResultPresenter presenter;
    private String menu;
    private Intent intent;
    private TextView textView;
    private EditText editText;
    private SearchResultAdapter recycleradapter;
    private Toolbar toolbarSearchResult;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.search_result);
        pullToRefreshLayout=findViewById(R.id.SearchRefreshLayout);
        toolbarSearchResult = findViewById(R.id.toolbar_search_result);

        setToolbar(toolbarSearchResult);

        intent=getIntent();
        menu=intent.getStringExtra("name");
        presenter=new SearchResultPresenter(this);
        initRecyclerView();
        initTextViewListener();
    }
    @Override
    public void setRecyclerItemData(ArrayList<SearchRecyclerItem>data,boolean setadapter){

        if(recycleradapter==null){
            recycleradapter=new SearchResultAdapter(this.getBaseContext(),R.layout.recycleritem,data);
            recyclerView.setAdapter(recycleradapter);
        }
        else {
            recycleradapter = (SearchResultAdapter) recyclerView.getAdapter();
            if(setadapter)
                recycleradapter.changeData(data);
            else {
                recycleradapter.addData(data);
            }
            recycleradapter.notifyDataSetChanged();
        }
        setRecyclerItemClickListener();
        if(pullToRefreshLayout!=null)
            pullToRefreshLayout.finishLoadMore();
    }
    @Override
    public void initRefreshListening(){
        if(pullToRefreshLayout!=null){
            pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
                @Override
                public void refresh() {
                    pullToRefreshLayout.finishRefresh();
                }

                @Override
                public void loadMore() {
                    presenter.operations(recycleradapter.getDatasCount(),menu,false);
                }
            });
        }
    }
    @Override
    public void setRecyclerItemClickListener(){
        recycleradapter.setOnItemClickListener(new OnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(SearchResultActivity.this, StepActivity.class);//此处要改
                int id=recycleradapter.getData().get(position).getId();
                intent.putExtra("name",Integer.toString(id));
                //Bundle bundle=new Bundle();
                //bundle.putInt("type",1);
                //bundle.putString("name",Integer.toString(recycleradapter.getData().get(position).getId()));
                //intent.putExtra("content",bundle);
                intent.putExtra("type","1");//1代表网络请求调用使用id查值
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter!=null){
            presenter.destroy();
            presenter=null;
        }
    }
    public void initRecyclerView(){
        recyclerView=findViewById(R.id.search_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        presenter.operations("0",menu,true);
    }
    public void initTextViewListener(){
        textView=findViewById(R.id.search_result);
        editText=findViewById(R.id.search_result_edit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()){
                    menu=editText.getText().toString();
                    presenter.operations("0",menu,true);
                }
            }
        });
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
