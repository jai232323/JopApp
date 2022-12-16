package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fastleader.Adapter.CustomerAdapter;
import com.example.fastleader.Adapter.ShowCustomerNameAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowCustomerNameActivity extends AppCompatActivity {


    RecyclerView ShowCustomerRecyclerView;

    private ArrayList<CustomerData> list;
    private ShowCustomerNameAdapter adapter;

    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer_name);

        activity=this;
        ShowCustomerRecyclerView =findViewById(R.id.ShowCustomerNameRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ShowCustomerNameActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        ShowCustomerRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new ShowCustomerNameAdapter(activity,ShowCustomerNameActivity.this,list);
        ShowCustomerRecyclerView.setAdapter(adapter);

        getCustomerName();


    }

    private void getCustomerName() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Customers");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CustomerData data = dataSnapshot.getValue(CustomerData.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowCustomerNameActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}