package com.example.ado.cookbookuser.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.CreateCookBook;

import java.util.List;
import java.util.zip.Inflater;

public class CookStepShowAdapter extends RecyclerView.Adapter<CookStepShowAdapter.ViewHolder> {

    private List<String> createCookBookSteps;

    public CookStepShowAdapter(List<String> createCookBookSteps){
        this.createCookBookSteps = createCookBookSteps;
    }

    @NonNull
    @Override
    public CookStepShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.create_show_step,viewGroup,false);
        return new CookStepShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CookStepShowAdapter.ViewHolder viewHolder, int i) {
        viewHolder.stepId.setText("步骤" + (i+1));
        viewHolder.stepDetail.setText(createCookBookSteps.get(i));
    }

    @Override
    public int getItemCount() {
        return createCookBookSteps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView stepId;
        public TextView stepDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepId = itemView.findViewById(R.id.step_id);
            stepDetail = itemView.findViewById(R.id.step_detail);
        }
    }
}
