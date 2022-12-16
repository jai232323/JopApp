package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fastleader.Adapter.GridAdapter;
import com.example.fastleader.Model.GridData;
import com.example.fastleader.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddLabelActivity extends AppCompatActivity {


    ImageView Cancel,Save;
    EditText NewLabelName;
    Button ChooseColor;

    GridView Color1;
    List<Integer> colorList;

    String getcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_label);



        Cancel = findViewById(R.id.Cancel);
        Save = findViewById(R.id.Save);
        NewLabelName = findViewById(R.id.NewLabelName);
        ChooseColor = findViewById(R.id.ChooseColor);

        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        getcolor=prefs1.getString("getcolor","none");



        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       // Save.setOnClickListener(new );


        //GridView
      //  Color1 = findViewById(R.id.Color1);




        ChooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetColor();
            }
        });


    }



    private void GetColor() {
        final ColorPicker colorPicker=new ColorPicker(this);
        ArrayList<String> colors=new ArrayList<>();

        colors.add("#3C8D2F");
        colors.add("#20724E");
        colors.add("#6a3ab2");
        colors.add("#323299");
        colors.add("#800080");

        colors.add("#b79716");
        colors.add("#966d37");
        colors.add("#b77231");
        colors.add("#808000");
        colors.add("#800080");

        colors.add("#3C8D2F");
        colors.add("#20724E");
        colors.add("#6a3ab2");
        colors.add("#323299");
        colors.add("#800080");

        colorPicker
                .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                      //  Log.d("position", "" + position);// will be fired only when OK button was tapped
                 //       Toast.makeText(AddLabelActivity.this, colors.get(position), Toast.LENGTH_SHORT).show();

                        //ChooseColor.setBackgroundColor(colors.get(position));

                        String newl = NewLabelName.getText().toString();


                        String getColor = colors.get(position);

                        SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                        editor.putString("getColor",getColor);
                        editor.apply();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Labels");

                        String L_ID = reference.push().getKey().toString();



                     //   InternalError color2 = colors.get(position);

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("L_ID",L_ID);
                        hashMap.put("L_NewLabelName",newl);
                        hashMap.put("L_Color",colors.get(position));

                        reference.child(L_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(AddLabelActivity.this, "Label Added Successfully", Toast.LENGTH_SHORT).show();

                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AddLabelActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();

                            }
                        });







                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

}