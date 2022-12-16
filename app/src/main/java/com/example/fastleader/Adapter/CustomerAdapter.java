package com.example.fastleader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastleader.Activity.EditCustomerActivity;
import com.example.fastleader.Activity.FullImagerActivity;
import com.example.fastleader.Activity.ShowJobDetailAssignbyActivity;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.TaskData;
import com.example.fastleader.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {




    private Context context;
    private ArrayList<CustomerData> items;
    private ArrayList<TaskData> items1;

    public CustomerAdapter(Context context, ArrayList<CustomerData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_adapter,parent,false);

        return new CustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        CustomerData data = items.get(position);
//        TaskData data1 = items1.get(position);
//        data1.getJobname();
//        Toast.makeText(context, data1.getTid(), Toast.LENGTH_SHORT).show();

//        holder.DataTime.setText(data.getDataTime());
//        holder.L_LeadName.setText(data.getC_CustomerName());

        Glide.with(context)
                .load(data.getC_Image())
                .into(holder.CustomerImage);
        holder.CustomerName.setText(data.getC_CustomerName());
        holder.ShopName.setText(data.getC_Company());
        holder.MobileNumber.setText(data.getC_MobileNumber());
        holder.Area.setText(data.getC_Address());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String id = data.getC_ID();
                String cn = data.getC_CustomerName();
                String sn = data.getC_Company();
                String mn = data.getC_MobileNumber();
                String area = data.getC_Address();
                String ci = data.getC_Image();
                String email = data.getC_Email();
                String ae = data.getC_AddEvent();


                Intent intent = new Intent(context, EditCustomerActivity.class);

                intent.putExtra("id",id);
                intent.putExtra("customername",cn);
                intent.putExtra("shopname",sn);
                intent.putExtra("mobile",mn);
                intent.putExtra("area",area);
                intent.putExtra("cimage",ci);
                intent.putExtra("email",email);
                intent.putExtra("addevent",ae);

                context.startActivity(intent);





            }
        });

        holder.CustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImagerActivity.class);
                intent.putExtra("image",data.getC_Image());
                context.startActivity(intent);
            }
        });

        holder.EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = data.getC_ID();
                String cn = data.getC_CustomerName();
                String sn = data.getC_Company();
                String mn = data.getC_MobileNumber();
                String area = data.getC_Address();
                String ci = data.getC_Image();
                String email = data.getC_Email();
                String ae = data.getC_AddEvent();


                Intent intent = new Intent(context, EditCustomerActivity.class);

                intent.putExtra("id",id);
                intent.putExtra("customername",cn);
                intent.putExtra("shopname",sn);
                intent.putExtra("mobile",mn);
                intent.putExtra("area",area);
                intent.putExtra("cimage",ci);
                intent.putExtra("email",email);
                intent.putExtra("addevent",ae);

                context.startActivity(intent);

            }
        });

        holder.DetailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, ShowJobDetailAssignbyActivity.class);






                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


//        TextView DataTime,L_LeadName;
//        ImageView LeadsImage,Edit;
//
        ImageView CustomerImage;
        TextView CustomerName,ShopName,MobileNumber,Area;
        TextView EditText,DetailText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            DataTime = itemView.findViewById(R.id.DataTime);
//            L_LeadName = itemView.findViewById(R.id.L_LeadName);
//            LeadsImage = itemView.findViewById(R.id.LeadsImage);
//            Edit = itemView.findViewById(R.id.Edit);

            CustomerImage = itemView.findViewById(R.id.CustomerImage);
            CustomerName = itemView.findViewById(R.id.CustomerName);
            ShopName = itemView.findViewById(R.id.ShopName);
            MobileNumber = itemView.findViewById(R.id.MobileNumber);
            Area = itemView.findViewById(R.id.Area);

            EditText = itemView.findViewById(R.id.EditText);
            DetailText = itemView.findViewById(R.id.DetailText);

        }
    }
}
