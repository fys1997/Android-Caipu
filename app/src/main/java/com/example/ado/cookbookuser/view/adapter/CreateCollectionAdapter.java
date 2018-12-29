package com.example.ado.cookbookuser.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.data.RecyclerItem;
import com.example.ado.cookbookuser.view.Interface.OnClickListener;



import java.util.ArrayList;

public class CreateCollectionAdapter extends RecyclerView.Adapter<CreateCollectionAdapter.CreateCollectionHolder> {
    private ArrayList<RecyclerItem> data=new ArrayList<>();
    private Context mcontext;
    private int mResID;
    RoundedCorners roundedCorners= new RoundedCorners(10);
    private RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
    private OnClickListener monItemClickListener;

    public CreateCollectionAdapter(Context context, int mResID, ArrayList<RecyclerItem> mDatas){
        this.data=mDatas;
        this.mcontext=context;
        this.mResID=mResID;
    }

    @Override
    public CreateCollectionHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(mcontext).inflate(R.layout.create_collection_caipu_page_item,parent,false);
        return new CreateCollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(final CreateCollectionHolder holder,int position){
        holder.COllectionText.setText(data.get(position).getTitlle());
        Glide.with(mcontext).load(data.get(position).getImgsrc()).apply(options).into(holder.CollectionImageView);
        holder.CollectionIngredients.setText(data.get(position).getIngredients());
        Glide.with(mcontext).load(R.drawable.cancel_collection).into(holder.DeleteImageView);
        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int position=holder.getLayoutPosition();
                    monItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount(){ return data==null?0:data.size();}

    public class  CreateCollectionHolder extends RecyclerView.ViewHolder{
        private ImageView CollectionImageView;
        private TextView COllectionText;
        private TextView CollectionIngredients;
        private ImageView DeleteImageView;
        public CreateCollectionHolder(View itemView){
            super(itemView);
            CollectionImageView=(ImageView)itemView.findViewById(R.id.RCollectionItemImage);
            COllectionText=(TextView)itemView.findViewById(R.id.RCollectionItemText);
            CollectionIngredients=(TextView)itemView.findViewById(R.id.RCollectioningredients);
            DeleteImageView=(ImageView)itemView.findViewById(R.id.deleteCollection);
        }
    }
}
