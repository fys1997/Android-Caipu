package com.example.ado.cookbookuser.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ado.cookbookuser.R;

import java.util.ArrayList;
import java.util.List;

public class CookStepAdapter extends RecyclerView.Adapter<CookStepAdapter.ViewHolder>{

    private List<String> stepData;
    private ViewHolder viewHolder;



    public CookStepAdapter(List<String> data){
        this.stepData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.create_cookbook_step,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.stepId.setText(stepData.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return stepData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView stepId;
        public EditText stepDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepId = itemView.findViewById(R.id.step_id);
            stepDetail = itemView.findViewById(R.id.step_detail);

        }
    }

    public void addNewItem() {

        stepData.add(stepData.size(), "步骤" + (stepData.size()+1));

        ////更新数据集不是用adapter.notifyDataSetChanged()而是notifyItemInserted(position)与notifyItemRemoved(position) 否则没有动画效果。
        notifyItemInserted(stepData.size());
    }

    public void deleteItem() {
        stepData.remove(stepData.size()-1);
        notifyItemRemoved(stepData.size());
    }
}
