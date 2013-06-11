package com.example.tryapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.tryapp.R;
import com.example.tryapp.sqlite.DatabaseAdapter;
import com.example.tryapp.ui.ContactsAdapter;

public class ViewContactsActivity extends Activity {
	
	private DatabaseAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contacts);
		
		ListView list = (ListView) findViewById(R.id.listView);
		
		ContactsAdapter adapter = new ContactsAdapter(this, readContent());
		list.setAdapter(adapter);
		
	}
	
	private Cursor readContent() {
		dbAdapter = new DatabaseAdapter(this);
		dbAdapter.openToRead();
		Cursor cursor = dbAdapter.getAllContacts();
		cursor.moveToFirst();
		return cursor;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.create_item:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
