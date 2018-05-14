package com.soton.mobiledev.employeedirectory.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class SearchActivity extends AppCompatActivity {

    private List<Employee> resultList = new ArrayList<>();
    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;
    private View view;

    public SearchActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String search = getIntent().getStringExtra("search");


        if (getActionBar() != null) {
            getActionBar().setTitle("Search Result");
        }


        recyclerView = (RecyclerView) findViewById(R.id.resultview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //adapter=new EmployeeAdapter(resultList);
        //recyclerView.setAdapter(adapter);

        query(search);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void query(final String search) {
        //Toast.makeText(getApplicationContext(),search,Toast.LENGTH_LONG).show();
        BmobQuery<User> query = new BmobQuery<User>();
        //  query.addWhereEqualTo("username", search);
        //先查所有的,在结果里根据名字判断
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {

                    for (User u : list) {
                        if (u.getUsername().toLowerCase().contains(search.toLowerCase())) {
                            if(u.getIsManager()) {
                                Employee employee = new Employee(u.getUsername(), R.drawable.m, u.getEmail(), u.getPhoto(), u.getPhonenum(), u.getIsManager(), u.getAddress(), u.getDepartment());
                                resultList.add(employee);
                            }else{
                                Employee employee = new Employee(u.getUsername(), R.drawable.e, u.getEmail(), u.getPhoto(), u.getPhonenum(), u.getIsManager(), u.getAddress(), u.getDepartment());
                                resultList.add(employee);
                            }
                        }
                    }

                adapter = new EmployeeAdapter(resultList);
                        recyclerView.setAdapter(adapter);
                if (resultList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No Result", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
