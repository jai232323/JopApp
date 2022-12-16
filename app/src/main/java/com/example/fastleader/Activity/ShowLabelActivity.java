package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastleader.Adapter.CustomerAdapter;
import com.example.fastleader.Adapter.ShowLabelValueAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.Labelvalue;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowLabelActivity extends AppCompatActivity {



    RecyclerView ShowRecyclerView;

    private ArrayList<Labelvalue> list;
    private ShowLabelValueAdapter adapter;


    String Labelname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_label);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Show Label");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Labelname= prefs1.getString("Labelname","none");

        ShowRecyclerView = findViewById(R.id.ShowRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ShowLabelActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        ShowRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new ShowLabelValueAdapter(ShowLabelActivity.this,list);
        ShowRecyclerView.setAdapter(adapter);

        getShowLabelValueData();

    }

    private void getShowLabelValueData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("NewLabel");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Labelvalue data = dataSnapshot.getValue(Labelvalue.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowLabelActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}