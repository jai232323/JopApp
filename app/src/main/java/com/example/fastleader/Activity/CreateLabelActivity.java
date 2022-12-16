package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fastleader.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateLabelActivity extends AppCompatActivity {


    DatabaseReference referenceLabel = FirebaseDatabase.getInstance().getReference();
    DatabaseReference referenceNewLabel = FirebaseDatabase.getInstance().getReference("NewLabel");
    ArrayList<String> arrayListLabelValue = new ArrayList<>();

    Spinner SelectLabel;
    EditText LabelValue;
    Button Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_label);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Label");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SelectLabel = findViewById(R.id.SelectLabel);
        LabelValue  = findViewById(R.id.LabelValue);
        Save = findViewById(R.id.Save);

        referenceLabel.child("Labels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListLabelValue.clear();

                for (DataSnapshot items : snapshot.getChildren()){
                    //   String portionid = items.child("P_ID").getValue(String.class);
                    arrayListLabelValue.add(items.child("L_NewLabelName").getValue(String.class));
                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreateLabelActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListLabelValue);
                SelectLabel.setAdapter(arrayAdapter);
                SelectLabel.getSelectedItem().toString();

                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateLabelActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = LabelValue.getText().toString();

                UploadDataToFirebase(value);
            }
        });
    }

    private void UploadDataToFirebase(String value) {

        String Lid = referenceNewLabel.push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();


        String Labelname = SelectLabel.getSelectedItem().toString();
        hashMap.put("Lid",Lid);
        hashMap.put("Labelname",SelectLabel.getSelectedItem().toString());
        hashMap.put("Labelvalue",value);

        referenceNewLabel.child(Lid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateLabelActivity.this, "Label Added Successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("Labelname", Labelname);
                editor.apply();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateLabelActivity.this, "something went wrong<<>>", Toast.LENGTH_SHORT).show();
            }
        });
    }
}