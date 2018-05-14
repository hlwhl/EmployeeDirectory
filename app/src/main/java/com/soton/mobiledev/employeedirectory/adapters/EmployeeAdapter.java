package com.soton.mobiledev.employeedirectory.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.activities.PersonDetailActivity;
import com.soton.mobiledev.employeedirectory.entities.Employee;
import com.soton.mobiledev.employeedirectory.entities.User;

import java.io.Serializable;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> mEmployeeList;
    private List<Employee> mlist;
    private List<Employee> elist;
    private boolean flag=true;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View employeeView;
        ImageView employeeImage;
        TextView employeeName;
        public ViewHolder(View view){
            super(view);
            employeeView=view;
            employeeImage = view.findViewById(R.id.employee_image);
            employeeName = view.findViewById(R.id.employee_name);
        }
    }
    public EmployeeAdapter(List<Employee> EmployeeList){
         mEmployeeList=EmployeeList;
     }
    public EmployeeAdapter(List<Employee> EmployeeList,boolean flag){
        mEmployeeList=EmployeeList;
        this.flag=flag;
    }
    public EmployeeAdapter(List<Employee> EmployeeList, List<Employee> mlist, List<Employee> elist) {
        mEmployeeList = EmployeeList;
        this.mlist = mlist;
        this.elist = elist;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int ViewType){
         View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item,parent,false);
         final ViewHolder holder=new ViewHolder(view);
         holder.employeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Employee employee = mEmployeeList.get(position);
                if (!employee.getIsManager()) {
                    Intent intent = new Intent(parent.getContext(), PersonDetailActivity.class);
                    //intent传递数据
                    intent.putExtra("detail", employee);
                    intent.putExtra("allowClick",flag);
                    parent.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(parent.getContext(), PersonDetailActivity.class);
                    //intent传递数据
                    intent.putExtra("detail", employee);
                    intent.putExtra("allowClick",flag);
                  //  intent.putExtra("manager of", (Serializable) elist);
                    parent.getContext().startActivity(intent);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee=mEmployeeList.get(position);

      //  holder.employeeName.setTextColor(2);
        holder.employeeImage.setImageResource(employee.getId());
        holder.employeeName.setText(employee.getName());

    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

}
