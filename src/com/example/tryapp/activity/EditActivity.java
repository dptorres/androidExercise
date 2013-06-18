package com.example.tryapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import com.example.tryapp.fragments.AddContactsFragment;

public class EditActivity extends FragmentActivity{
	
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new FrameLayout(this));
		if (getSupportFragmentManager().findFragmentByTag("addContact") == null) {
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, new AddContactsFragment(), "addContact").commit();	
		}
		
	}

}
