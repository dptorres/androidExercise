package com.example.tryapp.sqlite;

import com.example.tryapp.object.Employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	
	private DatabaseHelper dbHelper;
	private SQLiteDatabase sqlDB;
	private String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_EMAIL, 
								   DatabaseHelper.COLUMN_CELLNUM, DatabaseHelper.COLUMN_BIRTHDAY, DatabaseHelper.COLUMN_INCOME};
	
	private Context context;
	
	public DatabaseAdapter(Context c) {
		context = c;
		dbHelper = new DatabaseHelper(context);
	}
	
	public DatabaseAdapter openToRead() throws SQLException {
		sqlDB = dbHelper.getReadableDatabase();
		return this;
	}
	
	public DatabaseAdapter openToWrite() throws SQLException {
		sqlDB = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long insertContact(Employee data) {
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COLUMN_NAME, data.getName());
	    values.put(DatabaseHelper.COLUMN_EMAIL, data.getEadd());
	    values.put(DatabaseHelper.COLUMN_CELLNUM, data.getCellNum());
	    values.put(DatabaseHelper.COLUMN_BIRTHDAY, data.getBirthday());
	    values.put(DatabaseHelper.COLUMN_INCOME, data.getIncome());
	    return sqlDB.insert(DatabaseHelper.TABLE_DATA, null, values);
	}
	
	public Cursor getAllContacts() {
	    return sqlDB.query(DatabaseHelper.TABLE_DATA, allColumns, null, null, null, null, null);
	  }
	
}
