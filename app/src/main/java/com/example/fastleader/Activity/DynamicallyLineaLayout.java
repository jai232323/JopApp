package com.example.fastleader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fastleader.R;

import java.util.ArrayList;
import java.util.List;

public class DynamicallyLineaLayout extends AppCompatActivity implements View.OnClickListener{


    LinearLayout layoutList;
    Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamically_linea_layout);


       // layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_add:

                addView();

                break;


        }

    }
    private void addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add,null,false);

//        EditText editText = (EditText)cricketerView.findViewById(R.id.edit_cricketer_name);
//        AppCompatSpinner spinnerTeam = (AppCompatSpinner)cricketerView.findViewById(R.id.spinner_team);
//        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,teamList);
//        spinnerTeam.setAdapter(arrayAdapter);
//
//        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = spinnerTeam.getSelectedItem().toString();
//                Toast.makeText(DynamicallyLineaLayout.this, s, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        layoutList.addView(cricketerView);

    }
}