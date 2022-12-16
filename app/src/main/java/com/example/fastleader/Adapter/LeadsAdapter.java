package com.example.fastleader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastleader.Activity.FullImagerActivity;
import com.example.fastleader.Model.LeadsData;
import com.example.fastleader.R;

import java.util.ArrayList;

public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LeadsData> items;

    public LeadsAdapter(Context context, ArrayList<LeadsData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leads_adapter,parent,false);

        return new LeadsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LeadsData data = items.get(position);
        holder.DataTime.setText(data.getDataTime());
        holder.L_LeadName.setText(data.getL_LeadName());



//        if (data.getL_LeadName().equals("")){
//            holder.itemView.setVisibility(View.INVISIBLE);
//        }else {
//            holder.itemView.setVisibility(View.VISIBLE);
//        }


        Glide.with(context)
                .load(data.getL_Image())
                .into(holder.LeadsImage);

        holder.LeadsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImagerActivity.class);
                intent.putExtra("image",data.getL_Image());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView DataTime,L_LeadName;
        ImageView LeadsImage,Edit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            DataTime = itemView.findViewById(R.id.DataTime);
            L_LeadName = itemView.findViewById(R.id.L_LeadName);
            LeadsImage = itemView.findViewById(R.id.LeadsImage);
            Edit = itemView.findViewById(R.id.Edit);

        }
    }

}
