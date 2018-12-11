package com.example.ado.cookbookuser.view.Refresh;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {
    int lastVisibleItem=0;
    LinearLayoutManager linearLayoutManager;
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newStata) {
        super.onScrollStateChanged(recyclerView, newStata);
        if (recyclerView.getAdapter() != null && newStata == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {

        }//滚动到底部
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy){
        super.onScrolled(recyclerView, dx, dy);
        linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
        lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
    }
}
