package com.soton.mobiledev.employeedirectory.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.soton.mobiledev.employeedirectory.R;
import com.soton.mobiledev.employeedirectory.entities.User;
import com.soton.mobiledev.employeedirectory.utilities.File;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {
    private ViewPager MyPager;
    private List<Fragment> fragmentList;
    private TabLayout mTab;
    private ImageView iv_user;
    private DrawerLayout drawerLayout;
    MaterialSearchBar searchBar;
    FloatingActionButton addButton;
    ImageView imageViewSide;
    TextView sideUsername;
    String photoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //三个department对应三个fragment，在fragmentone，two，three的initemployee里传入雇员信息
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentOne());
        fragmentList.add(new FragmentTwo());
        fragmentList.add(new FragmentThree());
        initView();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        //如果是雇员，就隐藏添加按钮
        boolean isManager=(boolean)User.getObjectByKey("isManager");
        if(isManager==false){
            addButton.setVisibility(View.INVISIBLE);
        }
        JSONObject photoFile = (JSONObject) User.getObjectByKey("Photo");
        photoUrl = null;
        try {
            photoUrl = photoFile.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Glide.with(getApplicationContext()).load(photoUrl).into(iv_user);
        MyPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        mTab.setupWithViewPager(MyPager);
        if(isManager==true) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployee.class);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(), "No Result", Toast.LENGTH_LONG).show();
            }
        });
       }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            BmobUser.logOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("search", text.toString());
        startActivity(intent);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }


    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> list;
        String[] mTitle;

        public MyViewPagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            mTitle=getResources().getStringArray(R.array.departments_name);
            this.list = list;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mTitle[position];
        }

        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
    }

    private void initView(){
        MyPager=(ViewPager) findViewById(R.id.viewpager);
        mTab=(TabLayout) findViewById(R.id.tab);
        iv_user = (ImageView) findViewById(R.id.bgimagemainuser);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        addButton = (FloatingActionButton) findViewById(R.id.add_employee);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                sideUsername = (TextView) findViewById(R.id.userNameSideMenu);
                sideUsername.setText(User.getCurrentUser().getUsername());
                imageViewSide = (ImageView) findViewById(R.id.imageViewSideMenu);
                Glide.with(getApplicationContext()).load(photoUrl).into(imageViewSide);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

}
