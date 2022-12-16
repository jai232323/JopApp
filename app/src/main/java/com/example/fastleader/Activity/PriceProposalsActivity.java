package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastleader.Adapter.LeadsAdapter;
import com.example.fastleader.Adapter.PriceProposalAdapter;
import com.example.fastleader.Model.LeadsData;
import com.example.fastleader.Model.PriceProposalData;
import com.example.fastleader.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PriceProposalsActivity extends AppCompatActivity {


    FloatingActionButton fab;

    RecyclerView PriceProposalsRecyclerView;

    private ArrayList<PriceProposalData> list;
    private PriceProposalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_proposals);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Price Proposal");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        PriceProposalsRecyclerView = findViewById(R.id.PriceProposalsRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(PriceProposalsActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        PriceProposalsRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new PriceProposalAdapter(PriceProposalsActivity.this,list);
        PriceProposalsRecyclerView.setAdapter(adapter);

        getPriceProposalData();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PriceProposalsActivity.this,AddPriceProposalsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getPriceProposalData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("PriceProposal");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PriceProposalData data = dataSnapshot.getValue(PriceProposalData.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PriceProposalsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}