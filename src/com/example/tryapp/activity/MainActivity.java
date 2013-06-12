package com.example.tryapp.activity;

import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.tryapp.R;
import com.example.tryapp.fragments.AddContactsFragment;
import com.example.tryapp.fragments.ViewContactsFragment;
import com.example.tryapp.ui.ViewPagerAdapter;

public class MainActivity extends FragmentActivity{

	private ViewPagerAdapter pageAdapter;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        final ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        List<Fragment> fragments = initPages();
    	
    	pageAdapter  = new ViewPagerAdapter(super.getSupportFragmentManager(), fragments);
    	
    	ViewPager pager = (ViewPager)super.findViewById(R.id.viewPager);
        pager.setAdapter(pageAdapter);
    	
    }

	private List<Fragment> initPages() {
		
		List<Fragment> fragments = new Vector<Fragment>();
		
        fragments.add(Fragment.instantiate(this, AddContactsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ViewContactsFragment.class.getName()));
	
        return fragments;
	}

}
