package com.soton.mobiledev.employeedirectory.activities;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.soton.mobiledev.employeedirectory.R;

public class AboutActivity extends AppCompatActivity {
    private boolean state=true;
    TextView tvAuthor;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            updateUI();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvAuthor= (TextView) findViewById(R.id.tvAuthor);
        Thread thread=new updateUI();
        thread.run();
    }
    private void updateUI(){
        if(state){
            tvAuthor.setText("Hegang Xu & Linwei Hao");
            state=false;
        }else{
            tvAuthor.setText("Linwei Hao & Hegang Xu");
            state=true;
        }
    }
    class updateUI extends Thread{
        @Override
        public void run() {
            super.run();
            Message message=new Message();
            message.what=1;
            while(true){
                handler.sendMessage(message);
                SystemClock.sleep(1000);
            }
        }
    }
}

