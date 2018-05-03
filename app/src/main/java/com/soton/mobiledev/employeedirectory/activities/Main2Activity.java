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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soton.mobiledev.employeedirectory.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //设置标题栏的返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //获取电话 短信 邮件图标 设置点击事件
        LinearLayout call=(LinearLayout) findViewById(R.id.item_call);
        LinearLayout sendMessage=(LinearLayout) findViewById(R.id.item_sms);
        LinearLayout sendMail=(LinearLayout) findViewById(R.id.item_mail);
        //打电话
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.
                       permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.CALL_PHONE},1);
               }else{
                   call();
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
    private void call() {
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
                    call();
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
