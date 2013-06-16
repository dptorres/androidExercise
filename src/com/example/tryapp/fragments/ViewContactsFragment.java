package com.example.tryapp.fragments;

import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tryapp.R;
import com.example.tryapp.sqlite.DatabaseAdapter;
import com.example.tryapp.ui.ContactsAdapter;

public class ViewContactsFragment extends Fragment{
	
	private DatabaseAdapter dbAdapter;
	private View view;
	private Cursor[] cursors;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_view_contacts, container, false);
		
		ListView list = (ListView) view.findViewById(R.id.listView);
		
		ContactsAdapter adapter = new ContactsAdapter(this.getActivity(), readContent());
		list.setAdapter(adapter);
		
		return view;
	}
	
	private Cursor readContent() {
		cursors = new Cursor[3];
		dbAdapter = new DatabaseAdapter(this.getActivity());
		dbAdapter.openToRead();
		
		cursors[0] = dbAdapter.getTrainee();
		cursors[1] = dbAdapter.getHourlyEmployee();
		cursors[2] = dbAdapter.getExecutive();
		
		Cursor merged = new MergeCursor(cursors);
		merged.moveToFirst();
		
		return merged;
		
	}
	
}
