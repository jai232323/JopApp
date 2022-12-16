package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fastleader.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class JobEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button button;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    String DT,c_cn,t_taskdec,jobCreateBy,jobname;
    String billamount;

    Spinner JobStatus,JobAssignBy;
    EditText T_TaskDecription,BillAmount,JobCreateBy,JobName;
    Button T_OK,JobCreateDate,JobCloseDate,C_CustomerName,Delete;

    DatePickerDialog.OnDateSetListener onDateSetListener2;
    DatePickerDialog.OnDateSetListener onDateSetListener3;


    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference();
    DatabaseReference referenceCustomers = FirebaseDatabase.getInstance().getReference("Customers");


    ArrayList<String> arrayListUser = new ArrayList<>();

    String CustomerName;

    private Context context;


    MaterialCardView MC_CustomerImage;
    TextView C_CustomersImageText;
    ImageView Job_Image;

    String downloadUrl="";
    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;


    ProgressDialog pd;
    String Uname;

    String id,customerName,billamount1,jobcreateby,
            jobassignby,jobcreatedate,jclosedate,jobstatus,jobname1,jobimage,taskdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Job");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        pd=new ProgressDialog(this);

        id = getIntent().getStringExtra("id");
        customerName = getIntent().getStringExtra("customerName");
        billamount1 = getIntent().getStringExtra("billamount");
        jobcreateby = getIntent().getStringExtra("jobcreateby");
        jobassignby = getIntent().getStringExtra("jobassignby");
        jobcreatedate = getIntent().getStringExtra("jobcreatedate");
        jclosedate = getIntent().getStringExtra("jclosedate");
        jobstatus = getIntent().getStringExtra("jobstatus");
        jobname1 = getIntent().getStringExtra("jobname");
        jobimage = getIntent().getStringExtra("jobimage");
        taskdesc = getIntent().getStringExtra("taskdesc");




        context=this;
        C_CustomerName = findViewById(R.id.C_CustomerName);
        T_TaskDecription = findViewById(R.id.T_TaskDecription);
        BillAmount = findViewById(R.id.BillAmount);
        JobCreateBy =findViewById(R.id.JobCreateBy);
        JobStatus = findViewById(R.id.JobStatus);
        JobAssignBy = findViewById(R.id.JobAssignBy);
        JobCreateDate = findViewById(R.id.JobCreateDate);
        JobCloseDate = findViewById(R.id.JobCloseDate);
        JobName = findViewById(R.id.JobName);
        T_OK = findViewById(R.id.T_OK);
        MC_CustomerImage =findViewById(R.id.MC_CustomerImage);
        C_CustomersImageText= findViewById(R.id.C_CustomersImageText);
        Job_Image = findViewById(R.id.Job_Image);
        Delete = findViewById(R.id.Delete);


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                DatabaseReference referenceDelete = FirebaseDatabase.getInstance().getReference("Tasks");

                                referenceDelete.child(id).removeValue();

                                finish();
                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        T_TaskDecription.setText(taskdesc);
        JobName.setText(jobname1);
        BillAmount.setText(billamount1);




        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Uname= prefs1.getString("Uname","none");

        JobCreateBy.setText(Uname);

        storageReference= FirebaseStorage.getInstance().getReference();


        C_CustomerName.setText(customerName);

        C_CustomerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(JobEditActivity.this,ShowCustomerNameActivity.class);
                // startActivity(intent);
                startActivityForResult(intent,2001);
            }
        });

//        CustomerName = getIntent().getStringExtra("CustomerName");
//        C_CustomerName.setText(CustomerName);


        Glide.with(context)
                .load(jobimage)
                .into(Job_Image);
        MC_CustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        JobCloseDate.setText(jclosedate);
        JobCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
                        onDateSetListener3,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                JobCloseDate.setText(date);
            }
        };
        JobCreateDate.setText(jobcreatedate);
        JobCreateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
                        onDateSetListener2,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                JobCreateDate.setText(date);
            }
        };




        String [] rent_date = new String[]{jobstatus,"Open","Close","Progress"};
        JobStatus.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rent_date));

        JobStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   RentType.getSelectedItem();
                JobStatus.getSelectedItem().toString();
                //  Double rent_type = Double.valueOf(RentType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Get Tenant Names

        referenceUsers.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListUser.clear();
                arrayListUser.add(jobassignby);
                arrayListUser.add("Job AssignBY");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListUser.add(items.child("Uname").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(JobEditActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListUser);
                JobAssignBy.setAdapter(arrayAdapter);
                JobAssignBy.getSelectedItem().toString();

                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });

        button = findViewById(R.id.btnPick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
                        JobEditActivity.this,year, month,day);
                datePickerDialog.show();
            }
        });
        T_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   c_cn = C_CustomerName.getText().toString();
                t_taskdec = T_TaskDecription.getText().toString();
                billamount = BillAmount.getText().toString();
                jobCreateBy = JobCreateBy.getText().toString();
                jobname = JobName.getText().toString();



                if (bitmap==null){
//                    UploadWithoutImage(c_cn,t_taskdec,billamount,jobCreateBy,jobname);

                    UploadJobData(jobimage);
                }
                else {
                    UploadImage();
                }
            }
        });
    }

    private void UploadJobData(String jobimage) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tasks");

//
//        Date d = new Date();
//        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());

        String createdate = JobCreateDate.getText().toString();
        String cdate = JobCloseDate.getText().toString();




        String cn = C_CustomerName.getText().toString();


        String T_ID = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("C_CustomerName",cn);
        hashMap.put("T_TaskDecription",t_taskdec);
        hashMap.put("T_ReminderDate","Year: " + myYear  +
                " , Month: " + myMonth +
                " , Day: " + myday );



        hashMap.put("Billamount",billamount);
        hashMap.put("Jobcreateby",jobCreateBy);
        hashMap.put("Jobassignby",JobAssignBy.getSelectedItem().toString());
        hashMap.put("Jobcreatedate",createdate);
        hashMap.put("Jobclosedate",cdate);
        hashMap.put("Jobstatus",JobStatus.getSelectedItem().toString());
        hashMap.put("Jobname",jobname);
//        hashMap.put("Jobimage",jobimage);
        hashMap.put("Tid",id);

        reference.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(JobEditActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JobEditActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void UploadWithoutImage(String c_cn, String t_taskdec,String billAMount,String jobCreateBy,String jobname) {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tasks");
//
//
//        Date d = new Date();
//        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
//
//
//        String cdate = JobCloseDate.getText().toString();
//
//
//
//
//        String cn = C_CustomerName.getText().toString();
//
//
//        String T_ID = reference.push().getKey().toString();
//
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("C_CustomerName",cn);
//        hashMap.put("T_TaskDecription",t_taskdec);
//        hashMap.put("T_ReminderDate","Year: " + myYear  +
//                " , Month: " + myMonth +
//                " , Day: " + myday );
//
//
//
//        hashMap.put("Billamount",billAMount);
//        hashMap.put("Jobcreateby",jobCreateBy);
//        hashMap.put("Jobassignby",JobAssignBy.getSelectedItem().toString());
//        hashMap.put("Jobcreatedate",s);
//        hashMap.put("Jobclosedate",cdate);
//        hashMap.put("Jobstatus",JobStatus.getSelectedItem().toString());
//        hashMap.put("Jobname",jobname);
//        hashMap.put("Tid",id);
//        hashMap.put("Jobimage","https://firebasestorage.googleapis.com/v0/b/fastleader-dc4e0.appspot.com/o/job.png?alt=media&token=271e388b-ef10-45d6-b322-3d436807ea99");
//
//        reference.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(JobEditActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(JobEditActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void UploadImage() {
        pd.setMessage("Wait a seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Task").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(JobEditActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    UploadJobData(downloadUrl);
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                            child("Tasks").child(id);


                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("Jobimage",""+downloadUrl);
                                    reference1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pd.dismiss();
                                            finish();
                                        }
                                    });

//                                    UploadData(c_cn,t_taskdec,billamount,jobCreateBy,jobname);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(JobEditActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(JobEditActivity.this,
                JobEditActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        button.setText("Year: " + myYear + "\n" +
                "Month: " + myMonth + "\n" +
                "Day: " + myday + "\n" +
                "Hour: " + myHour + "\n" +
                "Minute: " + myMinute);


        // DT = String.valueOf(myYear+myMonth+myday+myHour+myMinute);



    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.i("reqCode**", requestCode + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GAFOOR");

        //   Toast.makeText(context,"dataaXXX"+requestCode+data.getExtras(),Toast.LENGTH_SHORT).show();
        if (requestCode == 2001 && resultCode == RESULT_OK) {

            CustomerName = data.getStringExtra("CustomerName");
            C_CustomerName.setText(CustomerName);
//             finish();
//            Long holdOrderNo = data.getLongExtra("OrderID", 0l);
//            selectedTable = data.getStringExtra("HoldStatus");
        }

        if (requestCode == REQ && resultCode == RESULT_OK) {

            Uri uri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Job_Image.setImageBitmap(bitmap);
            C_CustomersImageText.setVisibility(View.INVISIBLE);
        }
    }

}
