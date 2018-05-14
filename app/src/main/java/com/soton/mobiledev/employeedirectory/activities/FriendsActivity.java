package com.soton.mobiledev.employeedirectory.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.adapters.EmployeeAdapter;
import com.soton.mobiledev.employeedirectory.entities.Employee;
import com.soton.mobiledev.employeedirectory.entities.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FriendsActivity extends AppCompatActivity {

    private List<Employee> resultList = new ArrayList<>();
    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;
    private View view;

    public FriendsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Bundle b = getIntent().getExtras();
        List<Employee> list = (List<Employee>) b.getSerializable("list");

        Log.e("listsize", list.size() + "");

        if (getActionBar() != null) {
            getActionBar().setTitle("Search Result");
        }


        recyclerView = (RecyclerView) findViewById(R.id.resultview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //adapter=new EmployeeAdapter(resultList);
        //recyclerView.setAdapter(adapter);
        adapter = new EmployeeAdapter(list,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}
