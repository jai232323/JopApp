package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fastleader.Fragment.CustomerFragment;
import com.example.fastleader.Fragment.LabelFragment;
import com.example.fastleader.Fragment.LeadsFragment;
import com.example.fastleader.Fragment.MyBusinessFragment;
import com.example.fastleader.Fragment.TaskFragment;
import com.example.fastleader.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.Tasks);

    }


    LeadsFragment leadsFragment = new LeadsFragment();
    CustomerFragment customerFragment = new CustomerFragment();
    LabelFragment labelFragment = new LabelFragment();
    TaskFragment taskFragment = new TaskFragment();
    MyBusinessFragment myBusinessFragment = new MyBusinessFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Leads:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,leadsFragment).commit();
                return true;
            case R.id.Customers:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, customerFragment).commit();
                return true;
            case R.id.Labels:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, labelFragment).commit();
                return true;
            case R.id.Tasks:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, taskFragment).commit();
                return true;
            case R.id.MyBusiness:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myBusinessFragment).commit();
                return true;
            default:
                break;
        }
        return false;
    }
}