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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.Date;
import java.util.HashMap;

public class EditCustomerActivity extends AppCompatActivity {

    MaterialCardView MC_CustomerImage;
    TextView C_CustomersImageText;
    ImageView C_Image;
    EditText C_CustomerName,C_Company,C_MobileNumber,C_Email,C_Address,C_AddEvent;
    Button C_OK;

    DatabaseReference reference;

    ProgressDialog pd;
    String downloadUrl="",c_leadname,c_company,c_mobilenumber,c_email,c_address,c_addevent;
    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;

    String id,customername,shopname,mobile,area,cimage,email,addevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);



        pd=new ProgressDialog(this);

        MC_CustomerImage =findViewById(R.id.MC_CustomerImage);
        C_CustomersImageText= findViewById(R.id.C_CustomersImageText);
        C_Image = findViewById(R.id.C_Image);
        C_CustomerName = findViewById(R.id.C_CustomerName);
        C_Company = findViewById(R.id.C_Company);
        C_MobileNumber = findViewById(R.id.C_MobileNumber);
        C_Email = findViewById(R.id.C_Email);
        C_Address = findViewById(R.id.C_Address);
        C_AddEvent = findViewById(R.id.C_AddEvent);
        C_OK = findViewById(R.id.C_OK);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Customer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        id = getIntent().getStringExtra("id");
        customername = getIntent().getStringExtra("customername");
        shopname = getIntent().getStringExtra("shopname");
        mobile = getIntent().getStringExtra("mobile");
        area = getIntent().getStringExtra("area");
        cimage = getIntent().getStringExtra("cimage");
        email = getIntent().getStringExtra("email");
        addevent = getIntent().getStringExtra("addevent");

        C_CustomerName.setText(customername);
        C_Company.setText(shopname);
        C_MobileNumber.setText(mobile);
        C_Address.setText(area);
        C_Email.setText(email);
        C_AddEvent.setText(addevent);

        Glide.with(EditCustomerActivity.this)
                .load(cimage)
                .into(C_Image);




        reference = FirebaseDatabase.getInstance().getReference("Customers");


        storageReference= FirebaseStorage.getInstance().getReference();

        C_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_leadname = C_CustomerName.getText().toString();
                c_company = C_Company.getText().toString();
                c_mobilenumber = C_MobileNumber.getText().toString();
                c_email = C_Email.getText().toString();
                c_address = C_Address.getText().toString();
                c_addevent = C_AddEvent.getText().toString();


                if (bitmap==null){
//                    UploadLeadsData(c_leadname,c_company,c_mobilenumber,c_email,
//                            c_address,c_addevent);
                    UploadDataToFirebase(cimage);
                }else {
                    UploadImage();
                }
            }
        });


        MC_CustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void UploadDataToFirebase(String cimage) {
        String L_ID = reference.push().getKey().toString();

        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                .format(new Date());


        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("C_ID",id);
        hashMap.put("C_CustomerName",c_leadname);
        hashMap.put("C_Company",c_company);
        hashMap.put("C_MobileNumber",c_mobilenumber);
        hashMap.put("C_Email",c_email);
        hashMap.put("C_Address",c_address);
        hashMap.put("C_AddEvent",c_addevent);
        hashMap.put("C_Image",cimage);
        hashMap.put("DataTime",currentDateTimeString);


        reference.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditCustomerActivity.this, "Leads Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditCustomerActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
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
        filePath=storageReference.child("Customers").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(EditCustomerActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    UploadDataToFirebase(downloadUrl);
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                            child("Customers").child(id);


                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("C_Image",""+downloadUrl);
                                    reference1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pd.dismiss();
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(EditCustomerActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
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
            C_Image.setImageBitmap(bitmap);
            C_CustomersImageText.setVisibility(View.INVISIBLE);
        }

    }
}