package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class AddPriceProposalsActivity extends AppCompatActivity {


    EditText id,Description,Quantity,Amount;
    Button Add,Previous;

    LinearLayout layoutList;
    int i;
    String orderno;

    Spinner GetCustomerNameLead;
    EditText To,Comments;
    String getcl;

    DatabaseReference referenceCustomer = FirebaseDatabase.getInstance().getReference();
    DatabaseReference referenceStaff = FirebaseDatabase.getInstance().getReference();


    private ArrayList<String> arrayListCustomer = new ArrayList<>();
    private ArrayList<String> arrayListLeads = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price_proposals);


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


        id = findViewById(R.id.id);
        Description = findViewById(R.id.Description);
        Quantity = findViewById(R.id.Quantity);
        Amount = findViewById(R.id.Amount);
        Add = findViewById(R.id.Add);
       // Previous = findViewById(R.id.Previous);



        layoutList = findViewById(R.id.layout_list);

        GetCustomerNameLead = findViewById(R.id.GetCustomerNameLead);
        To = findViewById(R.id.To);
        Comments = findViewById(R.id.Comments);


        for (i=0;i>100000;i++){
            i++;
        }

        orderno = String.valueOf(i);
        id.setText(orderno);




        //Get Customer Name
        referenceCustomer.child("Customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                arrayListCustomer.clear();
                arrayListCustomer.add("Select Customer/Lead");
                for (DataSnapshot items : snapshot.getChildren()){

                    if (items.exists()){
                        arrayListCustomer.add(items.child("C_CustomerName").getValue(String.class));
                        GetCustomerNameLead.setVisibility(View.VISIBLE);
                    }else if (!items.exists()){
                        GetCustomerNameLead.setVisibility(View.INVISIBLE);
                    }

                }

                ArrayAdapter<String> arrayAdapterCustomer = new ArrayAdapter<>(AddPriceProposalsActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListCustomer);

                GetCustomerNameLead.setAdapter(arrayAdapterCustomer);

                getcl = GetCustomerNameLead.getSelectedItem().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Get Customer Name
//        referenceCustomer.child("Leads").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                arrayListCustomer.clear();
//
//                for (DataSnapshot items : snapshot.getChildren()){
//                    arrayListCustomer.add(items.child("L_LeadName").getValue(String.class));
//                }
//
//                ArrayAdapter<String> arrayAdapterCustomer = new ArrayAdapter<>(AddPriceProposalsActivity.this,
//                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListCustomer);
//
//                GetCustomerNameLead.setAdapter(arrayAdapterCustomer);
//
//                getcl = GetCustomerNameLead.getSelectedItem().toString();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

       // id.setText(i);




//        for (int i=0;i<100000;i++){
//            id.setText(i);
//            i++;
//        }


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id1 = id.getText().toString();
                String desc = Description.getText().toString();
                String qu = Quantity.getText().toString();
                String am = Amount.getText().toString();
                String to  = To.getText().toString();
                String comment = Comments.getText().toString();

//                if (id1.isEmpty()){
//                    id.setError("# Empty");
//                    id.requestFocus();
//                }else if (desc.isEmpty()){
//                    Description.setError("Description is Empty");
//                    Description.requestFocus();
//                }else if (qu.isEmpty()){
//                    Quantity.setError("Quantity is Empty");
//                    Quantity.requestFocus();
//                }else if (am.isEmpty()){
//                    Amount.setError("Amount is Empty");
//                    Amount.requestFocus();
//                }else {
//                    UploadData(desc,qu,am);
//                }

                UploadData(desc,qu,am,to,comment);

            }
        });

    }

    private void UploadData(String desc, String qu, String am,String to , String comment) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PriceProposal");



        final Random random = new Random();
        String RandomNumber = String.valueOf(random.nextInt(9999));


        Date d = new Date();
        CharSequence s  = DateFormat.format("d MMMM, yyyy ", d.getTime());


        String P_ID = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("P_ID",orderno);
        hashMap.put("P_Description",desc);
        hashMap.put("P_Quantity",qu);
        hashMap.put("P_Amount",am);
        hashMap.put("P_RandomNo",RandomNumber);
        hashMap.put("P_Date",s);
        hashMap.put("P_To",to);
        hashMap.put("P_Comment",comment);
        hashMap.put("P_CLName",GetCustomerNameLead.getSelectedItem().toString());


        Log.i("s",hashMap+"s");

       // Toast.makeText(this, hashMap, Toast.LENGTH_SHORT).show();

//
        reference.child(P_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddPriceProposalsActivity.this, "Price Proposal Added Successfully", Toast.LENGTH_SHORT).show();

                addView();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddPriceProposalsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addView() {

        View view = getLayoutInflater().inflate(R.layout.row_add,null,false);



        EditText id = findViewById(R.id.id);
        EditText Description = findViewById(R.id.Description);
        EditText Quantity = findViewById(R.id.Quantity);
        EditText Amount = findViewById(R.id.Amount);
        Button Addrow = findViewById(R.id.Add);


        id.setText(orderno);

        Addrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id1 = id.getText().toString();


                String desc1 = Description.getText().toString();
                String qu1 = Quantity.getText().toString();
                String am1 = Amount.getText().toString();
                String to1  = To.getText().toString();
                String comment1 = Comments.getText().toString();

//                if (id1.isEmpty()){
//                    id.setError("# Empty");
//                    id.requestFocus();
//                }else if (desc.isEmpty()){
//                    Description.setError("Description is Empty");
//                    Description.requestFocus();
//                }else if (qu.isEmpty()){
//                    Quantity.setError("Quantity is Empty");
//                    Quantity.requestFocus();
//                }else if (am.isEmpty()){
//                    Amount.setError("Amount is Empty");
//                    Amount.requestFocus();
//                }else {
//                    UploadData(desc,qu,am,to,comment);
//                }
                UploadData1(desc1,qu1,am1,to1,comment1);
            }
        });


//        EditText editText = (EditText)view.findViewById(R.id.edit_cricketer_name);
//        AppCompatSpinner spinnerTeam = (AppCompatSpinner)view.findViewById(R.id.spinner_team);
//        ImageView imageClose = (ImageView)view.findViewById(R.id.image_remove);

        layoutList.addView(view);

    }

    private void UploadData1(String desc1, String qu1, String am1, String to1, String comment1) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PriceProposal");

        final Random random = new Random();
        String RandomNumber = String.valueOf(random.nextInt(9999));


        Date d = new Date();
        CharSequence s  = DateFormat.format("d MMMM, yyyy ", d.getTime());

        String P_ID = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("P_ID",P_ID);
        hashMap.put("P_Description",desc1);
        hashMap.put("P_Quantity",qu1);
        hashMap.put("P_Amount",am1);
        hashMap.put("P_RandomNo",RandomNumber);
        hashMap.put("P_Date",s);
        hashMap.put("P_To",to1);
        hashMap.put("P_Comment",comment1);
        hashMap.put("P_CLName",GetCustomerNameLead.getSelectedItem().toString());


        Log.i("s",hashMap+"s");

        reference.child(P_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddPriceProposalsActivity.this, hashMap+"\nPrice Proposal Added Successfully", Toast.LENGTH_SHORT).show();

                addView();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddPriceProposalsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
//        Previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }

}