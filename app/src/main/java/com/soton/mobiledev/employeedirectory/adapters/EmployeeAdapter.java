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
import com.soton.mobiledev.employeedirectory.activities.Main2Activity;
import com.soton.mobiledev.employeedirectory.entities.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> mEmployeeList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View employeeView;
        ImageView employeeImage;
        TextView employeeName;
        public ViewHolder(View view){
            super(view);
            employeeView=view;
            employeeImage= (ImageView)view.findViewById(R.id.employee_image);
            employeeName=(TextView)view.findViewById(R.id.employee_name);
        }
    }
     public EmployeeAdapter(List<Employee> EmployeeList){
         mEmployeeList=EmployeeList;
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
                Intent intent=new Intent(parent.getContext(),Main2Activity.class);
                parent.getContext().startActivity(intent);
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
