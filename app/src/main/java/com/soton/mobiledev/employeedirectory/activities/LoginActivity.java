package com.soton.mobiledev.employeedirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soton.mobiledev.employeedirectory.R;

import cn.bmob.v3.BmobUser;
import rx.Subscriber;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn=findViewById(R.id.loginBtn);
        final EditText etUsername=findViewById(R.id.etUsername);
        final EditText etPassword=findViewById(R.id.etPwd);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser u=new BmobUser();
                u.setUsername(etUsername.getText().toString());
                u.setPassword(etPassword.getText().toString());
                u.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
                    @Override
                    public void onCompleted() {
                        Log.e("66","----onCompleted----");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onNext(BmobUser bmobUser) {
                        Toast.makeText(getApplicationContext(),bmobUser.getUsername() + "登陆成功",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                });
            }
        });
    }

}
