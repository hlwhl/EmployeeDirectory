package com.soton.mobiledev.employeedirectory.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.entities.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddEmployee extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public String imagePath = null;

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etPhonenum;
    private RadioGroup rgRole;
    private RadioGroup rgDepartment;
    private AppCompatButton add;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemployee);
        ImageButton chooseFromAlbum = (ImageButton) findViewById(R.id.selectPhoto);

        initview();

        add = (AppCompatButton) findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_LONG).show();
                //向服务器更新(添加)数据
                if (isnull()) {
                    Snackbar.make(getCurrentFocus(), "Please complete the user information", Snackbar.LENGTH_LONG).show();
                } else {
                    pd = new ProgressDialog(AddEmployee.this);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setTitle("Uploading...");
                    pd.show();
                    add.setEnabled(false);
                    final User newUser = new User();
                    newUser.setUsername(etUsername.getText().toString());
                    newUser.setPassword(etPassword.getText().toString());
                    newUser.setEmail(etEmail.getText().toString());
                    newUser.setAddress(etAddress.getText().toString());
                    newUser.setPhonenum(etPhonenum.getText().toString());
                    //处理照片上传
                    final BmobFile photo = new BmobFile(new File(imagePath));
                    photo.uploadblock(new UploadFileListener() {
                        @Override
                        public void onProgress(Integer value) {
                            super.onProgress(value);
                            pd.setProgress(value);
                        }

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Upload Succeed", Toast.LENGTH_LONG).show();
                                newUser.setPhoto(photo);
                                if (rgDepartment.getCheckedRadioButtonId() == R.id.btn1) {
                                    newUser.setDepartment("Software");
                                } else if (rgDepartment.getCheckedRadioButtonId() == R.id.btn2) {
                                    newUser.setDepartment("Hardware");
                                } else if (rgDepartment.getCheckedRadioButtonId() == R.id.btn3) {
                                    newUser.setDepartment("Testing");
                                }

                                if (rgRole.getCheckedRadioButtonId() == R.id.role_manager) {
                                    newUser.setIsManager(true);
                                } else if (rgRole.getCheckedRadioButtonId() == R.id.role_employee) {
                                    newUser.setIsManager(false);
                                }
                                newUser.signUp(new SaveListener<User>() {
                                    @Override
                                    public void done(User u, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(getApplicationContext(), "Add new employee succeed", Toast.LENGTH_LONG).show();
                                            pd.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), e.getMessage() + "Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddEmployee.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddEmployee.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }


    private boolean isnull() {
        return (etPhonenum.length() == 0) || (etUsername.length() == 0) || (etPassword.length() == 0) || (etAddress.length() == 0) || (etEmail.length() == 0) || rgRole.getCheckedRadioButtonId() == -1 || rgDepartment.getCheckedRadioButtonId() == -1 || imagePath == null;
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {

        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (uri.getAuthority().equals("com.android.providers.downloads.documents")) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        //  displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        //   displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    private void initview() {
        etUsername = (EditText) findViewById(R.id.input_username);
        etPassword = (EditText) findViewById(R.id.input_password);
        etEmail = (EditText) findViewById(R.id.input_email);
        etAddress = (EditText) findViewById(R.id.input_location);
        rgRole = (RadioGroup) findViewById(R.id.rgRole);
        rgDepartment = (RadioGroup) findViewById(R.id.rgDepartment);
        etPhonenum = (EditText) findViewById(R.id.input_phonenum);
    }

}
