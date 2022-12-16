package com.example.fastleader.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.LabelData;
import com.example.fastleader.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {


    private Context context;
    private ArrayList<LabelData> items;

    public LabelAdapter(Context context, ArrayList<LabelData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.label_adapter,parent,false);

        return new LabelAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LabelData data = items.get(position);


      //  holder.L_Color.setBackgroundColor(Integer.parseInt(data.getL_Color()));

//        if (data.getL_NewLabelName().equals("")){
//            holder.itemView.setVisibility(View.INVISIBLE);
//        }else {
//            holder.itemView.setVisibility(View.VISIBLE);
//
//        }
        holder.NewLabelName.setText(data.getL_NewLabelName());
        holder.NewLabelName.setBackgroundColor(Color.parseColor(data.getL_Color()));
        Log.i("colorcode",data.getL_Color()+"s");

        holder.deletelabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                DatabaseReference referenceDelete = FirebaseDatabase.getInstance().
                                        getReference("Labels");


                                String id = data.getL_ID();
                                referenceDelete.child(id).removeValue();

                                Toast.makeText(context, "Label Deleted", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView L_Color;
        TextView  NewLabelName;
        ImageView deletelabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          //  L_Color = itemView.findViewById(R.id.L_Color);
            NewLabelName = itemView.findViewById(R.id.NewLabelName);
            deletelabel = itemView.findViewById(R.id.deletelabel);

        }
    }

}
