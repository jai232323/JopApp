package com.example.fastleader.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fastleader.Activity.AddTaskActivity;
import com.example.fastleader.Adapter.LeadsAdapter;
import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.LeadsData;
import com.example.fastleader.Model.TaskData;
import com.example.fastleader.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TaskFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{



    ExtendedFloatingActionButton fab;

    RecyclerView TaskRecyclerView;

    private ArrayList<TaskData> list;
    private TaskAdapter adapter;

    Button SelectDate;
    ImageView search,cleardata;

    LinearLayout no_jobs;

    DatePickerDialog.OnDateSetListener onDateSetListener3;


    String Tid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task, container, false);




        fab = view.findViewById(R.id.fab);
        SelectDate = view.findViewById(R.id.SelectDate);
        search = view.findViewById(R.id.search);
        no_jobs = view.findViewById(R.id.no_jobs);
        cleardata = view.findViewById(R.id.cleardata);


        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Tid=prefs1.getString("Tid","none");



        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate.clearComposingText();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        onDateSetListener3,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                SelectDate.setText(date);
            }
        };


        TaskRecyclerView = view.findViewById(R.id.TaskRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(getContext(),list);
        TaskRecyclerView.setAdapter(adapter);

        getTaskData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddTaskActivity.class));

            }
        });
        cleardata.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {

                SelectDate.setText("");
//                SelectDate.setText("Select with Date");


//                list.clear();
//                adapter.notifyDataSetChanged();
//                SelectDate.setText("Select with Date");
//                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tasks");
//                reference1.addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            Toast.makeText(getContext(), "<<<>>>", Toast.LENGTH_SHORT).show();
//                            TaskData data = dataSnapshot.getValue(TaskData.class);
//                            list.add(0, data);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });


//                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tasks");
//                reference1.addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//
//                        if (!snapshot.exists()){
//                            no_jobs.setVisibility(View.VISIBLE);
//                            TaskRecyclerView.setVisibility(View.INVISIBLE);
//                        }else {
//                            no_jobs.setVisibility(View.INVISIBLE);
//                            TaskRecyclerView.setVisibility(View.VISIBLE);
//
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                TaskData data = dataSnapshot.getValue(TaskData.class);
//                                list.add(0, data);
//
//                            }
//                            Collections.reverse(list);
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        return view;
    }

    private void getTaskData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tasks");

        Query query = reference1.orderByChild("Jobcreatedate").limitToFirst(10);

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                if (!snapshot.exists()){
                    no_jobs.setVisibility(View.VISIBLE);
                    TaskRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    no_jobs.setVisibility(View.INVISIBLE);
                    TaskRecyclerView.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TaskData data = dataSnapshot.getValue(TaskData.class);
                        list.add(0, data);

                    }
                  //  Collections.reverse(list);
                    adapter.notifyDataSetChanged();


                    SelectDate.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            filter(editable.toString());
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filter(String text) {

        ArrayList<TaskData> filterlist = new ArrayList<>();
        for (TaskData item : list){
            if (item.getJobcreatedate().toLowerCase().contains(text.toLowerCase())){

                filterlist.add(item);

            }else if (item.getJobname().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(item);

            }

//            else if (item.getJobclosedate().toLowerCase().contains(text.toLowerCase())){
//                filterlist.add(item);
//            }

        }


        adapter.Filteredlist(filterlist);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

}