package com.soton.mobiledev.employeedirectory.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.adapters.EmployeeAdapter;
import com.soton.mobiledev.employeedirectory.entities.Employee;
import com.soton.mobiledev.employeedirectory.entities.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentThree extends Fragment{
    private List<Employee> employeeList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_three, null);
        //initEmployee();
        if (employeeList.size() == 0) {
            query();
        }
        adapter = new EmployeeAdapter(employeeList);
        recyclerView = view.findViewById(R.id.recycler_view3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    //在这里传入后端云获的employee信息

    //查询函数
    private void query() {
        BmobQuery<User> query = new BmobQuery<User>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
        query.addWhereEqualTo("Department", "Software");
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                employeeList.clear();
                for (User u : list) {
                    if (u.getIsManager()) {
                        employeeList.add(new Employee(u.getUsername(), R.drawable.m, u.getEmail(), u.getPhoto(), u.getMobilePhoneNumber()));
                    }
                }
                for (User u : list) {
                    if (!u.getIsManager()) {
                        employeeList.add(new Employee(u.getUsername(), R.drawable.e, u.getEmail(), u.getPhoto(), u.getMobilePhoneNumber()));
                    }
                }
                adapter = new EmployeeAdapter(employeeList);
                recyclerView.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.swapAdapter(adapter, true);
        employeeList.clear();
        query();
    }

}
