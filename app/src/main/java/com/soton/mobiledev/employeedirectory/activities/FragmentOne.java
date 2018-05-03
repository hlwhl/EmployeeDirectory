package com.soton.mobiledev.employeedirectory.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.adapters.EmployeeAdapter;
import com.soton.mobiledev.employeedirectory.entities.Employee;
import com.soton.mobiledev.employeedirectory.entities.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class FragmentOne extends Fragment{
    private List<Employee> employeeList=new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= LayoutInflater.from(getContext()).inflate(R.layout.fragment_one,null);
        //initEmployee();
        query();
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    //在这里传入后端云获的employee信息


    private void query(){
        BmobQuery<BmobUser> query=new BmobQuery<BmobUser>();
        query.addWhereEqualTo("Department","Software");
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                Log.e("query","查询完成"+list.size());
                for(BmobUser u:list){
                    employeeList.add(new Employee(u.getUsername(),R.drawable.m));
                }
                EmployeeAdapter adapter=new EmployeeAdapter(employeeList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}