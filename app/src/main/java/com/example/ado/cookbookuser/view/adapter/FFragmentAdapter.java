package com.example.ado.cookbookuser.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class FFragmentAdapter entends FragmentPaperAdapter{
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
}
