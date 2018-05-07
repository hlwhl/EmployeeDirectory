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
import com.soton.mobiledev.employeedirectory.entities.Employee;

public class PersonDetailActivity extends AppCompatActivity {
    Employee e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        actionBar.setTitle(e.getName() + "'s Detail Information");
        TextView tvUsername = (TextView) findViewById(R.id.username);
        TextView tvEmail = (TextView) findViewById(R.id.mail);
        tvUsername.setText(e.getName());
        tvEmail.setText(e.getMail());

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



        //获取电话 短信 邮件图标 设置点击事件
        LinearLayout call=(LinearLayout) findViewById(R.id.item_call);
        LinearLayout sendMessage=(LinearLayout) findViewById(R.id.item_sms);
        LinearLayout sendMail=(LinearLayout) findViewById(R.id.item_mail);


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
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + 12123456));
                //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //发邮件
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "123456@gmail.com"));
                //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void call(String phonenum) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:12312456"));
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
}
