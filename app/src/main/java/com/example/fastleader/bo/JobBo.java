package com.example.fastleader.bo;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fastleader.Model.LabelData;
import com.example.fastleader.Model.Labelvalue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JobBo {


    public Map<String,List<String>> getLablesMap(){

       Map <String,List<String>> updatedMap=new LinkedHashMap<>();
       Map<String, String> labelValuesMap=new HashMap();
       String name="";
       List<String> spinnerList=null;
        try{

            List<Labelvalue> keyDataList=getLabelValuesData();
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
}
