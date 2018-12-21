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
import com.example.ado.cookbookuser.data.SearchRecyclerItem;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchHolder> {
    private ArrayList<SearchRecyclerItem>data=new ArrayList<>();
    private Context context;
    private int mResId;
    RoundedCorners roundedCorners= new RoundedCorners(10);
    private RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
    private OnClickListener monItemClickListener;
    public SearchResultAdapter(Context context1,int mResId,ArrayList<SearchRecyclerItem>mDatas){
        this.data=mDatas;
        this.context=context1;
        this.mResId=mResId;
    }
    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(context).inflate(mResId,parent,false);
        return new SearchHolder(view);
    }
    @Override
    public void onBindViewHolder(final SearchHolder holder,int position){
        holder.textView1.setText(data.get(position).getDescription());
        Glide.with(context).load(data.get(position).getImgsrc()).apply(options).into(holder.imageView);
        holder.textView2.setText(data.get(position).getIngredients());
        holder.textView3.setText(data.get(position).getTags());
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
    public int getItemCount(){return data==null?0:data.size();}
    public class SearchHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        public SearchHolder(View itemView){
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.RItemImage);
            textView1=(TextView)itemView.findViewById(R.id.RItemText);
            textView2=(TextView)itemView.findViewById(R.id.Ringredients);
            textView3=(TextView)itemView.findViewById(R.id.Rtags);
        }
    }
    public void setOnItemClickListener(OnClickListener onItemClickListener)
    {
        monItemClickListener=onItemClickListener;
    }
    public void addData(ArrayList<SearchRecyclerItem>newData){data.addAll(newData);}
    public void changeData(ArrayList<SearchRecyclerItem>newData){data=newData;}

    public ArrayList<SearchRecyclerItem> getData() {
        return data;
    }
    public String getDatasCount(){return Integer.toString(data.size());}
}
