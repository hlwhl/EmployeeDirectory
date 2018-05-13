package com.soton.mobiledev.employeedirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.entities.User;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {

    private final int SKIP_DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Bmob.initialize(getApplicationContext(),"2ae5aa70c8dd027d6052bf2bd4126e8c");

        Timer time = new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                BmobUser bmobUser = BmobUser.getCurrentUser();
                if (bmobUser != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        };
        time.schedule(task, SKIP_DELAY_TIME);


    }

}
