package com.example.fastleader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastleader.Model.Users;
import com.example.fastleader.R;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Users> items;

    public UsersAdapter(Context context, ArrayList<Users> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.useradapter,parent,false);

        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Users data = items.get(position);

        holder.UserName.setText(data.getUname());
        holder.MobileNumber.setText(data.getUmobilenumber());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView UserName,MobileNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            UserName = itemView.findViewById(R.id.UserName);
            MobileNumber = itemView.findViewById(R.id.MobileNumber);
        }
    }

}
