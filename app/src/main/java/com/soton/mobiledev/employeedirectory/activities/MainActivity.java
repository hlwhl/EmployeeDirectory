package com.soton.mobiledev.employeedirectory.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soton.mobiledev.employeedirectory.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager MyPager;
    private List<Fragment> fragmentList;
    private TabLayout mTab;

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

        MyPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        mTab.setupWithViewPager(MyPager);

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
    }

}
