package com.example.tryapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.example.tryapp.R;
import com.example.tryapp.activity.EditActivity;
import com.example.tryapp.sqlite.DatabaseAdapter;
import com.example.tryapp.sqlite.DatabaseHelper;
import com.example.tryapp.ui.ContactsAdapter;

public class ViewContactsFragment extends Fragment{
	
	private DatabaseAdapter dbAdapter;
	private View view;
	private Cursor[] cursors;
	private ContactsAdapter adapter;
	private AddContactsFragment contactsFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_view_contacts, container, false);
		
		contactsFragment = new AddContactsFragment();
		ListView list = (ListView) view.findViewById(R.id.listView);
		dbAdapter = new DatabaseAdapter(this.getActivity());
		adapter = new ContactsAdapter(this.getActivity(), readContent());
		list.setAdapter(adapter);
		
		registerForContextMenu(list);
		
		return view;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
    	inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		dbAdapter.openToWrite();
		int position = info.position;
		Cursor c = (Cursor) adapter.getItem(position);
		int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
		int type = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_TYPE));
		
		switch(item.getItemId()) {
		case R.id.edit:
			
			Intent i = new Intent(getActivity(), EditActivity.class);  
			i.putExtra("id", id);
			i.putExtra("type", type);
			dbAdapter.close();
			startActivity(i);
			
			return true;
			
		case R.id.delete:
			dbAdapter.deleteEntry(id, type);
			contactsFragment.updateIncome(dbAdapter);
			dbAdapter.close();
			adapter.changeCursor(readContent());
			return true;
			
		default:
			return super.onContextItemSelected(item);
		}
		
	}

	@Override
	public void onResume() {
		adapter.changeCursor(readContent());
		super.onResume();
	}

	private Cursor readContent() {
		cursors = new Cursor[3];
		dbAdapter.openToRead();
		
		cursors[0] = dbAdapter.getTrainee();
		cursors[1] = dbAdapter.getHourlyEmployee();
		cursors[2] = dbAdapter.getExecutive();
		
		Cursor merged = new MergeCursor(cursors);
		merged.moveToFirst();
		
		dbAdapter.close();
		
		return merged;
		
	}
	
}
