package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastleader.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class AddLeadsActivity extends AppCompatActivity {

    MaterialCardView MC_LeadsImage;
    TextView LeadImageText;
    ImageView LeadsImage;
    EditText L_LeadName,L_Company,L_MobileNumber,L_Email,L_Address,L_AddEvent;
    Button L_OK;

    DatabaseReference reference;

    ProgressDialog pd;
    String downloadUrl="",l_leadname,l_company,l_mobilenumber,l_email,l_address,l_addevent;
    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leads);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Lead");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        pd=new ProgressDialog(this);

        MC_LeadsImage =findViewById(R.id.MC_LeadsImage);
        LeadImageText= findViewById(R.id.LeadImageText);
        LeadsImage = findViewById(R.id.LeadsImage);
        L_LeadName = findViewById(R.id.L_LeadName);
        L_Company = findViewById(R.id.L_Company);
        L_MobileNumber = findViewById(R.id.L_MobileNumber);
        L_Email = findViewById(R.id.L_Email);
        L_Address = findViewById(R.id.L_Address);
        L_AddEvent = findViewById(R.id.L_AddEvent);
        L_OK = findViewById(R.id.L_OK);

        reference = FirebaseDatabase.getInstance().getReference("Leads");
        storageReference= FirebaseStorage.getInstance().getReference();

        L_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_leadname = L_LeadName.getText().toString();
                l_company = L_Company.getText().toString();
                l_mobilenumber = L_MobileNumber.getText().toString();
                l_email = L_Email.getText().toString();
                l_address = L_Address.getText().toString();
                l_addevent = L_AddEvent.getText().toString();


                if (bitmap==null){
                    UploadLeadsData(l_leadname,l_company,l_mobilenumber,l_email,l_address,l_addevent);
                }else {
                    UploadImage();
                }


            }
        });


        MC_LeadsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void UploadLeadsData(String l_leadname, String l_company, String l_mobilenumber,
                                 String l_email, String l_address, String l_addevent) {


        String L_ID = reference.push().getKey().toString();

        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                .format(new Date());

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(todaysdate);




        HashMap <String,Object> hashMap = new HashMap<>();
        hashMap.put("L_ID",L_ID);
        hashMap.put("L_LeadName",l_leadname);
        hashMap.put("L_Company",l_company);
        hashMap.put("L_MobileNumber",l_mobilenumber);
        hashMap.put("L_Email",l_email);
        hashMap.put("L_Address",l_address);
        hashMap.put("L_AddEvent",l_addevent);
        hashMap.put("L_Image","https://firebasestorage.googleapis.com/v0/b/fastleader-dc4e0.appspot.com/o/leads.png?alt=media&token=527dade5-d0c8-4d59-aa45-2345920e21af");
        hashMap.put("DataTime",currentDateTimeString);


        reference.child(L_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddLeadsActivity.this, "Leads Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddLeadsActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadImage() {
        pd.setMessage("Wait a seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Leads").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddLeadsActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    //insertData(email, password);
                                    UploadLeadsData(l_leadname,l_company,l_mobilenumber,l_email,l_address,l_addevent,downloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddLeadsActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadLeadsData(String l_leadname, String l_company, String l_mobilenumber,
                                 String l_email, String l_address, String l_addevent , String downloadUrl) {

        String L_ID = reference.push().getKey().toString();

        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                .format(new Date());

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(todaysdate);




        HashMap <String,Object> hashMap = new HashMap<>();
        hashMap.put("L_ID",L_ID);
        hashMap.put("L_LeadName",l_leadname);
        hashMap.put("L_Company",l_company);
        hashMap.put("L_MobileNumber",l_mobilenumber);
        hashMap.put("L_Email",l_email);
        hashMap.put("L_Address",l_address);
        hashMap.put("L_AddEvent",l_addevent);
        hashMap.put("L_Image",downloadUrl);
        hashMap.put("DataTime",currentDateTimeString);


        reference.child(L_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddLeadsActivity.this, "Leads Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddLeadsActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {

            Uri uri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LeadsImage.setImageBitmap(bitmap);
            LeadImageText.setVisibility(View.INVISIBLE);
        }
    }
}