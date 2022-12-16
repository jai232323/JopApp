package com.example.fastleader.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastleader.Activity.AddTaskActivity;
import com.example.fastleader.Activity.ShowCustomerNameActivity;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.R;

import java.util.ArrayList;

public class ShowCustomerNameAdapter extends RecyclerView.Adapter<ShowCustomerNameAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CustomerData> items;

    private Activity activity;

    public ShowCustomerNameAdapter(Context context, ArrayList<CustomerData> items) {
        this.context = context;
        this.items = items;
    }

    public ShowCustomerNameAdapter(Activity activity,Context context, ArrayList<CustomerData> items) {
        this.activity = activity;
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customername_adapter,parent,false);

        return new ShowCustomerNameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CustomerData data = items.get(position);
        holder.CustomerName.setText(data.getC_CustomerName());


        String cn = data.getC_CustomerName().toString();

        Log.i("cn>>",cn+"<<<");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(context, AddTaskActivity.class);

                intent.putExtra("CustomerName",cn);

                context.startActivity(intent);*/
              //  Toast.makeText(context, "ccccc"+cn, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("CustomerName",cn);
                activity.setResult(Activity.RESULT_OK, intent);


                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView CustomerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CustomerName = itemView.findViewById(R.id.CustomerName);

        }
    }
}
