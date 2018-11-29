package com.example.ado.cookbookuser.view.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.view.adapter.MRecyclerAdapter;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    ArrayList<RecyclerItem>items=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment,null);
        pullToRefreshLayout=view.findViewById(R.id.RefreshLayout);
        recyclerView=view.findViewById(R.id.Frecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MRecyclerAdapter(getContext(),R.layout.recycleritem,items));
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public ArrayList<RecyclerItem> getItems() {
        return items;
    }

    public void setRecyclerview(RecyclerView recyclerview)
    {
         RecyclerView r=view.findViewById(R.id.Frecyclerview);
         r=recyclerview;
    }

    public PullToRefreshLayout getPullToRefreshLayout() {
        return pullToRefreshLayout;
    }
}
