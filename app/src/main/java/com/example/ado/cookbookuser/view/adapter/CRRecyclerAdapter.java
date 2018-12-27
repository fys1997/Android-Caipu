package com.example.ado.cookbookuser.view.adapter;

import android.annotation.SuppressLint;
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
import com.example.ado.cookbookuser.view.CreateRecipeActivity;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;

import java.util.ArrayList;
import java.util.List;


public class CRRecyclerAdapter extends RecyclerView.Adapter<CRRecyclerAdapter.ViewHolder>{
    private List<CreateRecipeActivity> mStepList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView stepImage;
        TextView stepText;

        public ViewHolder(View view){
            super(view);
            stepImage=(ImageView) view.findViewById(R.id.step_pic);
            stepText=(TextView) view.findViewById(R.id.step_text);
        }
    }

    public CRRecyclerAdapter(List<CreateRecipeActivity> stepList){
        mStepList= stepList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_create_recipe, parent,false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        CreateRecipeActivity step =mStepList.get(position);


    }

    @Override
    public int getItemCount(){
        return mStepList.size();
    }

}
