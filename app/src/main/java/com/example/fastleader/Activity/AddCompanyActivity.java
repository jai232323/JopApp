package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashMap;

public class AddCompanyActivity extends AppCompatActivity {

    MaterialCardView MC_CompanyImage;
    TextView CompanyImageText;
    ImageView C_Image;
    EditText CompanyName,CompanyContent,CompanyEmail,CompanyMobileNumber,CompanyAddress;
    private String companyName,companyContent,companyEmail,companyMobileNumber,companyAddress;
    Button OK;

//
//    DatabaseReference reference = FirebaseDatabase.getInstance().
//            getReferenceFromUrl("https://fastleader-dc4e0-default-rtdb.firebaseio.com/");

    ProgressDialog pd;
    String downloadUrl="";
    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;

    String Cid,U_MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);


        pd = new ProgressDialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Customer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber= prefs1.getString("U_MobileNumber","none");

        MC_CompanyImage =findViewById(R.id.MC_CompanyImage);
        CompanyImageText= findViewById(R.id.CompanyImageText);
        C_Image = findViewById(R.id.C_Image);
        CompanyName = findViewById(R.id.CompanyName);
        CompanyContent = findViewById(R.id.CompanyContent);
        CompanyEmail = findViewById(R.id.CompanyEmail);
        CompanyMobileNumber = findViewById(R.id.CompanyMobileNumber);
        CompanyAddress = findViewById(R.id.CompanyAddress);
        OK = findViewById(R.id.OK);

        storageReference= FirebaseStorage.getInstance().getReference();

        MC_CompanyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //private String companyName,companyContent,companyEmail,companyMobileNumber,companyAddress;

                companyName = CompanyName.getText().toString();
                companyContent = CompanyContent.getText().toString();
                companyEmail = CompanyEmail.getText().toString();
                companyMobileNumber = CompanyMobileNumber.getText().toString();
                companyAddress = CompanyAddress.getText().toString();


                if (bitmap==null){
                    UploadWithoutImage(companyName,companyContent,companyEmail,companyMobileNumber,companyAddress);
                }
                else {
                    UploadImage();
                }


            }
        });


    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
    }

    private void UploadImage() {
        pd.setMessage("Wait a seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Company").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddCompanyActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    UploadDataWithImage(companyName,companyContent,
                                            companyEmail,companyMobileNumber,companyAddress,downloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddCompanyActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadDataWithImage(String companyName, String companyContent,
                                     String companyEmail, String companyMobileNumber, String companyAddress,String downloadUrl) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Company");

        Cid = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Cid",Cid);
        hashMap.put("Cname",companyName);
        hashMap.put("Ccontent",companyContent);
        hashMap.put("Cemail",companyEmail);
        hashMap.put("Cmobilenumber",companyMobileNumber);
        hashMap.put("Caddress",companyAddress);
        hashMap.put("Clogo",downloadUrl);
        hashMap.put("Umobilenumber",U_MobileNumber);

        reference.child(U_MobileNumber).child(Cid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCompanyActivity.this, "Company Created Successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("Cid", Cid);
                editor.apply();

                SharedPreferences.Editor editor1 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor1.putString("U_MobileNumber", U_MobileNumber);
                editor1.apply();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCompanyActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadWithoutImage(String companyName, String companyContent,
                                    String companyEmail, String companyMobileNumber, String companyAddress) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Company");

        Cid = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Cid",Cid);
        hashMap.put("Cname",companyName);
        hashMap.put("Ccontent",companyContent);
        hashMap.put("Cemail",companyEmail);
        hashMap.put("Cmobilenumber",companyMobileNumber);
        hashMap.put("Caddress",companyAddress);
        hashMap.put("Umobilenumber",U_MobileNumber);
        hashMap.put("Clogo","https://firebasestorage.googleapis.com/v0/b/fastleader-dc4e0.appspot.com/o/company_logo.png?alt=media&token=0a30a771-c6ff-4d06-9e9f-e82c6f0e2ceb");

        reference.child(U_MobileNumber).child(Cid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCompanyActivity.this, "Company Created Successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("Cid", Cid);
                editor.apply();

                SharedPreferences.Editor editor1 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor1.putString("U_MobileNumber", U_MobileNumber);
                editor1.apply();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCompanyActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
            CompanyImageText.setVisibility(View.INVISIBLE);
        }
    }
}