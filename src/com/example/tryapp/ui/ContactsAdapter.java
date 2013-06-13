package com.example.tryapp.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.tryapp.R;
import com.example.tryapp.sqlite.DatabaseHelper;

public class ContactsAdapter extends CursorAdapter {
	
	public ContactsAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		setValues(cursor, view);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View rowView = LayoutInflater.from(context).inflate(R.layout.contacts_row, null); 
		setValues(cursor, rowView);
		
		return rowView;
	}

	private void setValues(Cursor cursor, View rowView) {
		TextView nameRow = (TextView) rowView.findViewById(R.id.name);
		TextView eaddRow = (TextView) rowView.findViewById(R.id.eadd);
		TextView cellNumRow = (TextView) rowView.findViewById(R.id.cellnum);
		TextView birthdayRow = (TextView) rowView.findViewById(R.id.birthday);
		TextView incomeRow = (TextView) rowView.findViewById(R.id.income);
		
		nameRow.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
		eaddRow.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
		cellNumRow.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CELLNUM)));
		birthdayRow.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTHDAY)));
		incomeRow.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME))));
	}
	
}
