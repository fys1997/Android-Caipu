package com.example.ado.cookbookuser.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;

import java.util.ArrayList;

/*public class FFragmentAdapter extends  FragmentPaperAdapter{
    private ArrayList<Fragment>fragments;
    private ArrayList<String>titles;
    public FFragmentAdapter(ArrayList<Fragment>fragments, FragmentManager fm,ArrayList<String>titles)
    {
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }
    @Override
    public int getCount(){return fragments.size();}
    @Override
    public Fragment getItem(int position){return fragments.get(position);}
    @Override
    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }
}*/
