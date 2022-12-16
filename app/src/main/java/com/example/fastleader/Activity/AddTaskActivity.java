package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fastleader.Adapter.JobLabelAdapter;
import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.LabelData;
import com.example.fastleader.Model.Labelvalue;
import com.example.fastleader.Model.TaskData;
import com.example.fastleader.R;
import com.example.fastleader.bo.JobBo;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    TextView textView;
    Button button;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    String DT,c_cn,t_taskdec,jobCreateBy,jobname;
    String billamount;

    Spinner JobStatus,JobAssignBy;
    EditText T_TaskDecription,BillAmount,JobCreateBy,JobName;
    Button C_CustomerName,T_OK,JobCloseDate,JobCreateDate;

    DatePickerDialog.OnDateSetListener onDateSetListener3;
    DatePickerDialog.OnDateSetListener onDateSetListener2;

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

    RecyclerView JobLabelRecyclerView;

    private ArrayList<TaskData> list;
    private JobLabelAdapter adapter;

    private LinearLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Job");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainContainer=findViewById(R.id.mainContainer);
        Log.i("mainContainer>>>",mainContainer+"xxx");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      //  JobLabelRecyclerView = findViewById(R.id.JobLabelRecyclerView);

        pd=new ProgressDialog(this);


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


        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Uname= prefs1.getString("Uname","none");

        JobCreateBy.setText(Uname);

        storageReference= FirebaseStorage.getInstance().getReference();

        C_CustomerName.setText("Get Customer Leave");
        C_CustomerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTaskActivity.this,ShowCustomerNameActivity.class);
               // startActivity(intent);
                startActivityForResult(intent,2001);
            }
        });

        CustomerName = getIntent().getStringExtra("CustomerName");
        C_CustomerName.setText(CustomerName);


        MC_CustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        JobCreateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
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
        JobCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
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
        String [] rent_date = new String[]{"Open","Close","Progress"};

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

                arrayListUser.add("Job AssignBY");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListUser.add(items.child("Uname").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddTaskActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListUser);
                JobAssignBy.setAdapter(arrayAdapter);
                JobAssignBy.getSelectedItem().toString();

                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddTaskActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, AddTaskActivity.this,year, month,day);
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
                    UploadWithoutImage(c_cn,t_taskdec,billamount,jobCreateBy,jobname);
                }
                    else {
                    UploadImage();
                }
            }
        });
//
//        JobBo jobBo=new JobBo();
//        Map<String,List<String>> updateMap=jobBo.getLablesMap();
        //loadDynamicFields(getLablesMap());
        getLabelValuesData();
    }

    private void UploadWithoutImage(String c_cn, String t_taskdec,String billAMount,String jobCreateBy,String jobname) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tasks");


        Date d = new Date();

       // CharSequence s  = DateFormat.format("d/MMMM/yyyy ", d.getTime());

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

        hashMap.put("Billamount",billAMount);
        hashMap.put("Jobcreateby",jobCreateBy);
        hashMap.put("Jobassignby",JobAssignBy.getSelectedItem().toString());
        hashMap.put("Jobcreatedate",createdate);
        hashMap.put("Jobclosedate",cdate);
        hashMap.put("Jobstatus",JobStatus.getSelectedItem().toString());
        hashMap.put("Jobname",jobname);
        hashMap.put("Tid",T_ID);
        hashMap.put("Jobimage",
                "https://firebasestorage.googleapis.com/v0/b/fastleader-dc4e0.appspot.com/o/job.png?alt=media&token=271e388b-ef10-45d6-b322-3d436807ea99");

        reference.child(T_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddTaskActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor edito3 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                edito3.putString("Tid", T_ID);
                edito3.apply();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTaskActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
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
        filePath=storageReference.child("Task").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddTaskActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    UploadData(c_cn,t_taskdec,billamount,jobCreateBy,jobname);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddTaskActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this,
                AddTaskActivity.this, hour, minute, DateFormat.is24HourFormat(this));
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

    private void UploadData(String c_cn, String t_taskdec,String billAMount,String jobCreateBy,String jobname) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tasks");


        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());


        String cdate = JobCloseDate.getText().toString();
        String createdate = JobCreateDate.getText().toString();




        String cn = C_CustomerName.getText().toString();


        String T_ID = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("C_CustomerName",cn);
        hashMap.put("T_TaskDecription",t_taskdec);
        hashMap.put("T_ReminderDate","Year: " + myYear  +
                " , Month: " + myMonth +
                " , Day: " + myday );

        hashMap.put("Billamount",billAMount);
        hashMap.put("Jobcreateby",jobCreateBy);
        hashMap.put("Jobassignby",JobAssignBy.getSelectedItem().toString());
        hashMap.put("Jobcreatedate",createdate);
        hashMap.put("Jobclosedate",cdate);
        hashMap.put("Jobstatus",JobStatus.getSelectedItem().toString());
        hashMap.put("Jobname",jobname);
        hashMap.put("Jobimage",downloadUrl);
        hashMap.put("Tid",T_ID);

        reference.child(T_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddTaskActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor edito3 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                edito3.putString("Tid", T_ID);
                edito3.apply();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTaskActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void loadDynamicFields(Map<String, List<String>> jobfieldListMap){
        Set<String> jobfieldList=jobfieldListMap.keySet();
        Log.i("dataMpa",jobfieldListMap+"zzzz");
        for(String jobfield:jobfieldList){
            loadDropDownField(jobfield,jobfieldListMap.get(jobfield));
        }
    }
    private void loadDropDownField(String fieldName,List<String> stringList)
    {
        try {
           // List<Jobfieldvalue>     fields=sqlliteHandler.getFieldValueList(jobfield.getFieldname());

            if(null!=stringList && stringList.size()>0) {
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutView = inflater.inflate(R.layout.layout_dropdpwn, null);
                TextView txtQuestion = layoutView.findViewById(R.id.questionTxt);
                txtQuestion.setText(fieldName);

                Spinner spinner = layoutView.findViewById(R.id.fieldNameSpinner);

                ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_row, stringList);
                spinner.setAdapter(arrayAdapter);


                mainContainer.addView(layoutView);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private List<LabelData> getLabelsData() {
        List<LabelData> labelDataList=new ArrayList<>();
        try {

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Labels");
            reference1.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        LabelData data = dataSnapshot.getValue(LabelData.class);
                        labelDataList.add(data);
                        Log.i("1234567890987654321",data.getL_ID()+"s");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return labelDataList;
    }

    private List<Labelvalue> getLabelValuesData() {
        List<Labelvalue> labelDataList=new ArrayList<>();
        try {

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("NewLabel");
            reference1.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Labelvalue data = dataSnapshot.getValue(Labelvalue.class);
                        labelDataList.add(data);

                        Log.i("data : ",data+"s");
                        Log.i("id : ",data.getLid()+"s");
                        Log.i("Labelname : ",data.getLabelname()+"s");
                        Log.i("Labelvalue : " ,data.getLabelvalue()+"s");

                      //  Toast.makeText(context, "12345", Toast.LENGTH_SHORT).show();

                    }
                    loadDynamicFields(getLablesMap(labelDataList));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return labelDataList;
    }


    public Map<String,List<String>> getLablesMap(List<Labelvalue> keyDataList){

        Map <String,List<String>> updatedMap=new LinkedHashMap<>();
        Map<String, String> labelValuesMap=new HashMap();
        String name="";
        List<String> spinnerList=null;
        try{

           // List<Labelvalue> keyDataList=getLabelValuesData();
            Log.i("scccccfff",keyDataList+"<<<");
            for(Labelvalue labelvalue: keyDataList){

                name=labelvalue.getLabelname();
                spinnerList= updatedMap.get(name);
                if(null==spinnerList){
                    spinnerList=new ArrayList();
                }
                spinnerList.add(labelvalue.getLabelvalue());
                updatedMap.put(name,spinnerList);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }

        return updatedMap;
    }

    public Map<String,List<String>> getLablesMap(){

        Map <String,List<String>> updatedMap=new LinkedHashMap<>();
        Map<String, String> labelValuesMap=new HashMap();
        String name="";
        List<String> spinnerList=null;
        try{

            List<Labelvalue> keyDataList=getLabelValuesData();
            Log.i("scccccfff",keyDataList+"<<<");
            for(Labelvalue labelvalue: keyDataList){

                name=labelvalue.getLabelname();
                spinnerList= updatedMap.get(name);
                if(null==spinnerList){
                    spinnerList=new ArrayList();
                }
                spinnerList.add(labelvalue.getLabelvalue());
                updatedMap.put(name,spinnerList);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }

        return updatedMap;
    }
}