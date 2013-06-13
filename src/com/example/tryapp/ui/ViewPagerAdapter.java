package com.example.tryapp.ui;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter{
	
	private List<Fragment> fragments;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);		
		this.fragments = fragments;
		
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
	
//	 public Fragment getItem(int i) {
//	        Fragment fragment = new DemoObjectFragment();
//	        Bundle args = new Bundle();
//	        // Our object is just an integer :-P
//	        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
//	        fragment.setArguments(args);
//	        return fragment;
//	    }


	@Override
	public Fragment getItem(int pos) {
		return this.fragments.get(pos);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	

}
