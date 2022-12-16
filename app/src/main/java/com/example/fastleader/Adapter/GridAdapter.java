package com.example.fastleader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fastleader.Model.GridData;
import com.example.fastleader.R;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<GridData> {


    private Context context;
    public GridAdapter(@NonNull Context context, ArrayList<GridData> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }
        GridData courseModel = getItem(position);
        ImageView courseIV = listitemView.findViewById(R.id.GridImage);
        courseIV.setImageResource(courseModel.getColor());

       // Toast.makeText(context, courseModel.getColor(), Toast.LENGTH_SHORT).show();

        return listitemView;
    }

}
