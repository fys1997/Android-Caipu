package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    private SearchResultPresenter presenter=new SearchResultPresenter();
    private String menu;
    private Intent intent;
    private SearchResultAdapter recycleradapter;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.search_result);
        pullToRefreshLayout=findViewById(R.id.SearchRefreshLayout);
        intent=getIntent();
        menu=intent.getStringExtra("name");
    }
    @Override
    public void setRecyclerItemData(ArrayList<SearchRecyclerItem>data){
        recyclerView=findViewById(R.id.search_result_recyclerview);
        if(recycleradapter==null){
            recycleradapter=new SearchResultAdapter(this.getBaseContext(),R.layout.recycleritem,data);
            recyclerView.setAdapter(recycleradapter);
        }
        else {
            recycleradapter = (SearchResultAdapter) recyclerView.getAdapter();
            recycleradapter.addData(data);
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
                    if(recycleradapter!=null)
                    presenter.operations(Integer.toString(recycleradapter.getItemCount()),menu);
                    else
                        presenter.operations("0",menu);
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
                intent.putExtra("Id",recycleradapter.getData().get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
