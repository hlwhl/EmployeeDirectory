package com.soton.mobiledev.employeedirectory.activities;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.soton.mobiledev.employeedirectory.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tvAuthor= (TextView) findViewById(R.id.tvAuthor);

        tvAuthor.setText("Hegang Xu & Linwei Hao");
//        boolean temp=true;
//        while (true){
//            if(temp=true){
//                tvAuthor.setText("Linwei Hao & Hegang Xu");
//                temp=false;
//            }else{
//
//                temp=true;
//            }
//            SystemClock.sleep(1000);
//        }
    }
}
