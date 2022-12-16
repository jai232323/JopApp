package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastleader.Adapter.CustomerAdapter;
import com.example.fastleader.Adapter.UsersAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.Users;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowUsersActivity extends AppCompatActivity {



    RecyclerView UsersRecyclerView;
    private ArrayList<Users> list;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Users");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        UsersRecyclerView = findViewById(R.id.UsersRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ShowUsersActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        UsersRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new UsersAdapter(ShowUsersActivity.this,list);
        UsersRecyclerView.setAdapter(adapter);

        getUserData();

    }

    private void getUserData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users data = dataSnapshot.getValue(Users.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowUsersActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}