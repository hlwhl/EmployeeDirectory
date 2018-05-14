package com.soton.mobiledev.employeedirectory.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.adapters.EmployeeAdapter;
import com.soton.mobiledev.employeedirectory.entities.Employee;
import com.soton.mobiledev.employeedirectory.entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PersonDetailActivity extends AppCompatActivity {
    Employee e;
    private List<Employee> mlist;
    private List<Employee> elist;
    private TextView tvFriends;
    private boolean allowClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mlist = new ArrayList<>();
        elist = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondetail);
        //设置标题栏的返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView photo = (ImageView) findViewById(R.id.icon_image);


        //接收intent传来数据
        Intent intent = getIntent();
        e = (Employee) intent.getSerializableExtra("detail");
        //Toast.makeText(getApplicationContext(), e.getDepartment(), Toast.LENGTH_LONG).show();
        allowClick=true;
        allowClick = (Boolean) intent.getSerializableExtra("allowClick");
        actionBar.setTitle(e.getName() + "'s Detail Information");
        TextView tvUsername = (TextView) findViewById(R.id.username);
        TextView tvEmail = (TextView) findViewById(R.id.mail);


        tvUsername.setText(e.getName());
        tvEmail.setText(e.getMail());


        query(e);
       // Toast.makeText(getApplicationContext(), e.getDepartment(), Toast.LENGTH_LONG).show();
        //设置头像
        if (e.getPhoto() != null) {
            //头像为空
            String photoUrl = e.getPhoto().getFileUrl();
            Glide.with(getApplicationContext()).load(photoUrl).into(photo);
        }

        //在按钮上显示详细信息
        TextView tvCall = (TextView) findViewById(R.id.item_textviewcall);
        tvCall.setText("Call: " + e.getPhonenum());
        TextView tvMaill = (TextView) findViewById(R.id.item_textviewmail);
        tvMaill.setText("Mail: " + e.getMail());
        TextView tvSms = (TextView) findViewById(R.id.item_textviewsms);
        tvSms.setText("SMS: " + e.getPhonenum());
        tvFriends = (TextView) findViewById(R.id.friends);
        TextView tvLocation = (TextView) findViewById(R.id.tv_loaction);
        tvLocation.setText(e.getLocation());



        //获取电话 短信 邮件图标 设置点击事件
        LinearLayout call=(LinearLayout) findViewById(R.id.item_call);
        LinearLayout sendMessage=(LinearLayout) findViewById(R.id.item_sms);
        LinearLayout sendMail=(LinearLayout) findViewById(R.id.item_mail);
        LinearLayout f = (LinearLayout) findViewById(R.id.item_friends);


        //朋友
        if(allowClick) {
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonDetailActivity.this, FriendsActivity.class);
                    if (mlist.isEmpty()) {
                        Bundle b = new Bundle();
                        b.putSerializable("list", (Serializable) elist);
                        intent.putExtras(b);
                    } else {
                        Bundle b = new Bundle();
                        b.putSerializable("list", (Serializable) mlist);
                        intent.putExtras(b);
                    }
                    startActivity(intent);
                }
            });
        }

        //打电话
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PersonDetailActivity.this, Manifest.
                       permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
               }else{
                    if (e.getPhonenum() != null) {
                        call(e.getPhonenum());
                    } else {
                        Toast.makeText(getApplicationContext(), "Phone Number is not Exist", Toast.LENGTH_LONG);
                    }
               }
            }
        });
        //发短信
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + e.getPhonenum()));
                //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //发邮件
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + e.getMail()));
                //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void call(String phonenum) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phonenum));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    //由于是直接拨打电话，所以要先申请运行时权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(e.getPhonenum());
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    //标题栏的返回键 返回上级
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void query(final Employee e) {
        //Toast.makeText(getApplicationContext(),search,Toast.LENGTH_LONG).show();
        elist.clear();
        mlist.clear();
        BmobQuery<User> query = new BmobQuery<User>();
        //  query.addWhereEqualTo("username", search);
        //先查所有的,在结果里根据名字判断
        query.addWhereEqualTo("Department", e.getDepartment());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException ex) {
                for (User u : list) {
                    if (e.getIsManager()) {
                        if (!u.getIsManager()) {
                            //elist.clear();
                            elist.add(new Employee(u.getUsername(), R.drawable.e, u.getEmail(), u.getPhoto(), u.getPhonenum(), false, u.getAddress(),u.getDepartment()));
                        }
                    } else if (!e.getIsManager()) {
                        if (u.getIsManager()) {
                           // mlist.clear();
                            mlist.add(new Employee(u.getUsername(), R.drawable.m, u.getEmail(), u.getPhoto(), u.getPhonenum(), true, u.getAddress(),u.getDepartment()));
                        }
                    }
                    //Toast.makeText(getApplicationContext(),elist.size()+" "+mlist.size(),Toast.LENGTH_LONG).show();
                }
                if (!e.getIsManager()) {
                    tvFriends.setText("Managed by: " + mlist.get(0).getName() + " and....");
                 //   Toast.makeText(getApplicationContext(),e.getIsManager()+","+e.getName(),Toast.LENGTH_LONG).show();
                } else {
                    tvFriends.setText("Manager of: " + elist.get(0).getName() + " and....");
                    //Toast.makeText(getApplicationContext(),e.getIsManager()+","+e.getName(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
