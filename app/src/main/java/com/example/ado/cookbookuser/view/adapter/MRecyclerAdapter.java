package com.example.ado.cookbookuser.view.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;

import java.net.URL;
import java.util.ArrayList;

public class MRecyclerAdapter extends RecyclerView.Adapter<MRecyclerAdapter.MHolder> {
    private ArrayList<RecyclerItem>data=new ArrayList<>();
    private Context mcontext;
    private int mResID;
    private OnClickListener monItemClickListener;
    public MRecyclerAdapter(Context context,int mResID,ArrayList<RecyclerItem>mDatas) {
        this.data=mDatas;
        this.mcontext=context;
        this.mResID=mResID;
    }
    @Override
    public MHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view= LayoutInflater.from(mcontext).inflate(mResID,parent,false);
        return new MHolder(view);
    }
    @Override
    public void onBindViewHolder(final MHolder holder,int position)
    {
        holder.textView.setText(data.get(position).getDescription());
        Glide.with(mcontext).load(data.get(position).getImgsrc()).into(holder.imageView);
        View itemview=((LinearLayout)holder.itemView).getChildAt(0);
        if(monItemClickListener!=null){
            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int position=holder.getLayoutPosition();
                    monItemClickListener.onItemClick(holder.imageView,position);
                }
            });
        }
    }
    @Override
    public int getItemCount(){ return data==null?0:data.size();}
    public class MHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView textView;
        public MHolder(View itemView)
        {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.RItemImage);
            textView=(TextView)itemView.findViewById(R.id.RItemText);
        }

    }
    public void addData(ArrayList<RecyclerItem>newData)
    {
        data.addAll(newData);
        //notifyDataSetChanged();
    }
    public ArrayList<RecyclerItem> getData() {
        return data;
    }
    public void setOnItemClickListener(OnClickListener onItemClickListener)
    {
        monItemClickListener=onItemClickListener;
    }
}
