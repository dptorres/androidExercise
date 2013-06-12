package com.example.tryapp.activity;

import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.example.tryapp.R;
import com.example.tryapp.fragments.AddContactsFragment;
import com.example.tryapp.fragments.ViewContactsFragment;
import com.example.tryapp.ui.ViewPagerAdapter;

public class MainActivity extends FragmentActivity{

	private ViewPagerAdapter pageAdapter;
	private ViewPager pager;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        List<Fragment> fragments = initPages();
    	
    	pageAdapter  = new ViewPagerAdapter(super.getSupportFragmentManager(), fragments);
    	
    	pager = (ViewPager)super.findViewById(R.id.viewPager);
        pager.setAdapter(pageAdapter);
        pager.setOnPageChangeListener(createPageChangeListener());
    	
        ActionBar.TabListener tabListener = createTabListener();
        actionBar.addTab(actionBar.newTab().setText("Add Employee").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("View Employees").setTabListener(tabListener));
          
    }

	private OnPageChangeListener createPageChangeListener() {
		ViewPager.SimpleOnPageChangeListener pageListener = new ViewPager.SimpleOnPageChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
			
		};
		return pageListener;
	}

	@SuppressLint("NewApi")
	private TabListener createTabListener() {
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				pager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				
			}
		};
		return tabListener;
	}

	private List<Fragment> initPages() {
		
		List<Fragment> fragments = new Vector<Fragment>();
		
        fragments.add(Fragment.instantiate(this, AddContactsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ViewContactsFragment.class.getName()));
	
        return fragments;
	}

}
