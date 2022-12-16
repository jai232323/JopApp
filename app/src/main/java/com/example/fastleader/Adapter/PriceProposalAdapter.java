package com.example.fastleader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastleader.Model.LabelData;
import com.example.fastleader.Model.PriceProposalData;
import com.example.fastleader.R;

import java.util.ArrayList;

public class PriceProposalAdapter extends RecyclerView.Adapter<PriceProposalAdapter.ViewHolder> {



    private Context context;
    private ArrayList<PriceProposalData> items;

    public PriceProposalAdapter(Context context, ArrayList<PriceProposalData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pricepropsal_adapter,parent,false);

        return new PriceProposalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PriceProposalData data = items.get(position);
        holder.Date.setText(data.getP_Date());
        holder.id.setText("Price Proposal #"+data.getP_ID());
        holder.Amount.setText("$"+data.getP_Amount());
        holder.Name.setText(" . "+data.getP_CLName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView Date,id,Amount,Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.Date);
            id = itemView.findViewById(R.id.id);
            Amount = itemView.findViewById(R.id.Amount);
            Name = itemView.findViewById(R.id.Name);
        }
    }

}
