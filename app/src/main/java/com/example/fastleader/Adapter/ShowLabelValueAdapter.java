package com.example.fastleader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.Labelvalue;
import com.example.fastleader.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowLabelValueAdapter extends RecyclerView.Adapter<ShowLabelValueAdapter.ViewHolder> {



    private Context context;
    private ArrayList<Labelvalue> items;

    public ShowLabelValueAdapter(Context context, ArrayList<Labelvalue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.showlabelvalue_adapter,parent,false);

        return new ShowLabelValueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Labelvalue data = items.get(position);
        holder.Labelkey.setText(data.getLabelname());
        holder.LabelValue.setText(data.getLabelvalue());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView Labelkey,LabelValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Labelkey = itemView.findViewById(R.id.LabelKey);
            LabelValue = itemView.findViewById(R.id.LabelValue);
        }
    }
}
