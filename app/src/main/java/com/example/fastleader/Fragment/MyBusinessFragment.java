package com.example.fastleader.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fastleader.Activity.AddCompanyActivity;
import com.example.fastleader.Activity.CompanyEditActivity;
import com.example.fastleader.Activity.CreateLabelActivity;
import com.example.fastleader.Activity.PriceProposalsActivity;
import com.example.fastleader.Activity.ShowLabelActivity;
import com.example.fastleader.Activity.ShowUsersActivity;
import com.example.fastleader.Activity.UploadCsvOrExcelActivity;
import com.example.fastleader.Activity.UserLoginActivity;
import com.example.fastleader.Model.Company;
import com.example.fastleader.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MyBusinessFragment extends Fragment {


    ExtendedFloatingActionButton fab;

    LinearLayout Companies;
    LinearLayout PriceProposals;
    LinearLayout Users;
    LinearLayout Logout;
    LinearLayout Labelvalue;
    LinearLayout ShowLabelvalue;
    LinearLayout UploadCsv;
    TextView textcsv;
    public static final int requestcode = 1;



    ImageView Companylogo;
    TextView CompanyName, CompanyContent, CompanyAddress, CompanyMobileNumber;
    Button EditCompanyDetails, SendBusiness;

    String Cid, U_MobileNumber;

    FirebaseAuth firebaseAuth;

    Uri imageuri = null;
    String myurl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_business, container, false);

        Companies = view.findViewById(R.id.Companies);
        Companylogo = view.findViewById(R.id.Companylogo);
        CompanyName = view.findViewById(R.id.CompanyName);
        CompanyContent = view.findViewById(R.id.CompanyContent);
        CompanyAddress = view.findViewById(R.id.CompanyAddress);
        CompanyMobileNumber = view.findViewById(R.id.CompanyMobileNumber);
        EditCompanyDetails = view.findViewById(R.id.EditCompanyDetails);
        SendBusiness = view.findViewById(R.id.SendBusiness);
        UploadCsv = view.findViewById(R.id.UploadCsv);
        textcsv = view.findViewById(R.id.textcsv);

        UploadCsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getContext(), UploadCsvOrExcelActivity.class));

//                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
//                fileintent.setType("text/*");
//                // startActivityForResult(fileintent, requestcode);
//
//
////                Intent galleryIntent = new Intent();
////                galleryIntent.setType("application/pdf");
////                //galleryIntent.setType("text/csv");
////                //  galleryIntent.setType("application/word");
////                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//                // We will be redirected to choose pdf
//
//                startActivityForResult(Intent.createChooser
//                        (fileintent, "Select PDF Files..."), 1);
            }
        });


        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Cid = prefs1.getString("Cid", "none");

        SharedPreferences prefs2 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber = prefs2.getString("U_MobileNumber", "none");


        getCompanyProfile(Cid, U_MobileNumber);


        EditCompanyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddCompanyActivity.class));
            }
        });

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddCompanyActivity.class));

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        PriceProposals = view.findViewById(R.id.PriceProposals);
        PriceProposals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PriceProposalsActivity.class);
                startActivity(intent);

            }
        });

        Users = view.findViewById(R.id.Users);
        Users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowUsersActivity.class);
                startActivity(intent);
            }
        });
        Logout = view.findViewById(R.id.Logout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();


                //   reference1.removeValue();

                Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), UserLoginActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);


                getActivity().finish();
            }
        });

        Labelvalue = view.findViewById(R.id.Labelvalue);
        Labelvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateLabelActivity.class);
                startActivity(intent);
            }
        });

        ShowLabelvalue = view.findViewById(R.id.ShowLabelvalue);
        ShowLabelvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowLabelActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void getCompanyProfile(String cid, String u_mobileNumber) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Company").child(u_mobileNumber);

        reference.child(cid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((getContext() == null)) {
                    return;
                }

                Company data = snapshot.getValue(Company.class);


                //TextView CompanyName,CompanyContent,CompanyAddress,CompanyMobileNumber;

                assert data != null;

                Glide.with(getContext()).load(data.getClogo()).into(Companylogo);

                CompanyName.setText(data.getCname());
                CompanyContent.setText(data.getCcontent());
                CompanyAddress.setText(data.getCaddress());
                CompanyMobileNumber.setText(data.getCmobilenumber());

                SendBusiness.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plane");
                        share.putExtra(Intent.EXTRA_TEXT, "Company Name : " + data.getCname() +
                                "\n" + "Company Content : " + data.getCcontent() +
                                "\n" + "Company Address : " + data.getCaddress() +
                                "\n" + "Company Mobile Number : " + data.getCmobilenumber());
//                share.putExtra(Intent.EXTRA_TEXT,"E-BloodBank\n\n"+"Blood Group : "+list.get(position).getBloodGroup()+"\n" +
//                        "Phone Number : " + list.get(position).getPhoneNumber() + "\n" +
//                        "Another Phone Number : " +list.get(position).getAnotherPhoneNumber() + "\n" +
//                        "Name : " +list.get(position).getName() + "\n" +
//                        "Age : " +list.get(position).getAge() + "\n"+
//                        "Gender : " +list.get(position).getGender() + "\n" +
//                        "Address : " +list.get(position).getAddress() + "\n" +
//                        "Location : " +list.get(position).getLocation() + "\n\n" + "This App Developed By Joyful Jai");
                        startActivity(share);
                    }
                });

                EditCompanyDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String cid = data.getCid();
                        String cname = data.getCname();
                        String ccontent = data.getCcontent();
                        String caddress = data.getCaddress();
                        String clogo = data.getClogo();
                        String cemail = data.getCemail();
                        String cmobilenumber = data.getCmobilenumber();

                        Intent intent = new Intent(getContext(), CompanyEditActivity.class);

                        intent.putExtra("cid", cid);
                        intent.putExtra("cname", cname);
                        intent.putExtra("ccontent", ccontent);
                        intent.putExtra("caddress", caddress);
                        intent.putExtra("clogo", clogo);
                        intent.putExtra("cemail", cemail);
                        intent.putExtra("cmobilenumber", cmobilenumber);

                        startActivity(intent);


                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("wait...");
        pd.show();


        imageuri = data.getData();
        final String timestamp = "" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final String messagePushID = timestamp;
        // Toast.makeText(CreateRentalActivity.this, imageuri.toString(), Toast.LENGTH_SHORT).show();


        // Here we are uploading the pdf in firebase storage with the name of current time
        final StorageReference filepath = storageReference.child("Csv").child(messagePushID + "." + "pdf");
        //  Toast.makeText(CreateRentalActivity.this, filepath.getName(), Toast.LENGTH_SHORT).show();
        filepath.putFile(imageuri).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    // After uploading is done it progress
                    // dialog box will be dismissed
                    //  dialog.dismiss();
                    Uri uri = task.getResult();

                    myurl = uri.toString();


                    DatabaseReference referenceCsv = FirebaseDatabase.getInstance().getReference("CSV");

                    String Csvid = referenceCsv.push().getKey();

                    assert Csvid != null;
                    referenceCsv.child(Csvid).setValue(myurl);
                    pd.dismiss();
                  //  textcsv.setText(myurl);


                    //  DocuImage.setVisibility(View.INVISIBLE);

                    //   DocText.setText(filepath.getName());
                    //   DocText.setText("Success");
                    // Glide.with(CreateRentalActivity.this).load(R.drawable.pdf_icon).into(DocuImage);
                    //   Toast.makeText(CreateRentalActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //  dialog.dismiss();
                    //  Toast.makeText(CreateRentalActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


//
//        if (data == null)
//
//            return;
//        switch (requestCode) {
//
//            case requestcode:
//
//                String filepath = data.getData().getPath().toString();
//
//
//                if (filepath.contains("/root_path"))
//                    filepath = filepath.replace("/root_path", "");
//
//
//
//                try {
//
//                    if (resultCode == RESULT_OK) {
//                        try {
//                            FileReader file = new FileReader(filepath);
//                            BufferedReader buffer = new BufferedReader(file);
//
//                            String line = "";
//
//                            while ((line = buffer.readLine()) != null) {
//
//                                ProgressDialog pd = new ProgressDialog(getContext());
//                                pd.setMessage("wait...");
//                                pd.show();
//
//                                //String[] str = line.split(",", 3); // defining 3 columns with null or blank field //values acceptance
//
////Id, Company,Name,Price
//                                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//                                final StorageReference filepath1 = storageReference.child("Csv").child("1"+ "pdf");
//                                //  Toast.makeText(CreateRentalActivity.this, filepath.getName(), Toast.LENGTH_SHORT).show();
//                                filepath1.putFile(Uri.parse(filepath)).continueWithTask(new Continuation() {
//                                    @Override
//                                    public Object then(@NonNull Task task) throws Exception {
//                                        if (!task.isSuccessful()) {
//                                            throw task.getException();
//                                        }
//                                        return filepath1.getDownloadUrl();
//                                    }
//                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//
//                                        if (task.isSuccessful()) {
//                                            // After uploading is done it progress
//                                            // dialog box will be dismissed
//                                            //  dialog.dismiss();
//                                            Uri uri = task.getResult();
//
//                                            myurl = uri.toString();
//
//
//                                            DatabaseReference referenceCsv = FirebaseDatabase.getInstance().getReference("CSV");
//
//                                            String Csvid = referenceCsv.getKey();
//
//                                            assert Csvid != null;
//                                            referenceCsv.child(Csvid).setValue(myurl);
//                                            pd.dismiss();
//                                        } else {
//                                            pd.dismiss();
//                                            Toast.makeText(getContext(), "UploadedFailed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                            }
//                            ;
//
//                        } catch (IOException e) {
//                            Log.e("IOException", e.getMessage().toString());
//
//                        }
//                    } else {
//                        Log.e("RESULT CODE", "InValid");
//                        Toast.makeText(getContext(), "Only CSV files allowed.", Toast.LENGTH_LONG).show();
//
//
//                    }
//                }catch (Exception e){
//
//                }
//        }
//    }
//}




//     //   String filepath = data.getData().getPath();
//        if (filepath.contains("/root_path"))
//            filepath = filepath.replace("/root_path", "");
//        FileReader file = null;
//
//
//        try {
//            file = new FileReader(filepath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try (BufferedReader buffer = new BufferedReader(file)) {
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String line = "";
//        while ((line = buffer.readLine()) != null) {

