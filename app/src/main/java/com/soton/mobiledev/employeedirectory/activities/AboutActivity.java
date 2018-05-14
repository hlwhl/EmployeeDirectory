package com.soton.mobiledev.employeedirectory.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.soton.mobiledev.employeedirectory.R;

public class AboutActivity extends AppCompatActivity {
    TextView tvAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvAuthor= (TextView) findViewById(R.id.tvAuthor);
    }

}
