package com.example.tryapp.sqlite;

import com.example.tryapp.object.Employee;
import com.example.tryapp.object.Executive;
import com.example.tryapp.object.HourlyEmployee;

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
	
	public long insertToTrainee(Employee data) {
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COLUMN_NAME, data.getName());
	    values.put(DatabaseHelper.COLUMN_EMAIL, data.getEadd());
	    values.put(DatabaseHelper.COLUMN_CELLNUM, data.getCellNum());
	    values.put(DatabaseHelper.COLUMN_BIRTHDAY, data.getBirthday());
	    values.put(DatabaseHelper.COLUMN_INCOME, data.getIncome());
	    return sqlDB.insert(DatabaseHelper.TRAINEE_DATA, null, values);
	}
	
	public long insertToHourlyEmployee(HourlyEmployee data) {
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COLUMN_NAME, data.getName());
	    values.put(DatabaseHelper.COLUMN_EMAIL, data.getEadd());
	    values.put(DatabaseHelper.COLUMN_CELLNUM, data.getCellNum());
	    values.put(DatabaseHelper.COLUMN_BIRTHDAY, data.getBirthday());
	    values.put(DatabaseHelper.COLUMN_INCOME, data.getIncome());
	    values.put(DatabaseHelper.COLUMN_WAGE, data.getWage());
	    values.put(DatabaseHelper.COLUMN_HOURS, data.getHours());
	    return sqlDB.insert(DatabaseHelper.HOURLY_EMP_DATA, null, values);
	}
	
	public long insertToExecutive(Executive data) {
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COLUMN_NAME, data.getName());
	    values.put(DatabaseHelper.COLUMN_EMAIL, data.getEadd());
	    values.put(DatabaseHelper.COLUMN_CELLNUM, data.getCellNum());
	    values.put(DatabaseHelper.COLUMN_BIRTHDAY, data.getBirthday());
	    values.put(DatabaseHelper.COLUMN_INCOME, data.getIncome());
	    values.put(DatabaseHelper.COLUMN_BONUS, data.getPercent());
	    values.put(DatabaseHelper.COLUMN_TYPE, data.getType());
	    return sqlDB.insert(DatabaseHelper.EXECUTIVE_DATA, null, values);
	}
	
	public Cursor getTrainee() {
	    return sqlDB.query(DatabaseHelper.TRAINEE_DATA, allColumns, null, null, null, null, null);
	}
	
	public Cursor getHourlyEmployee() {
		return sqlDB.query(DatabaseHelper.HOURLY_EMP_DATA, allColumns, null, null, null, null, null);
	}
	
	public Cursor getExecutive() {
		return sqlDB.query(DatabaseHelper.EXECUTIVE_DATA, allColumns, null, null, null, null, null);
	}
	
	public Cursor getTraineeIncome() {
		return sqlDB.query(DatabaseHelper.TRAINEE_DATA, new String[] {DatabaseHelper.COLUMN_INCOME}, null, null, null, null, null);
	}
	
	public Cursor getHourlyEmpIcome() {
		return sqlDB.query(DatabaseHelper.HOURLY_EMP_DATA, new String[] {DatabaseHelper.COLUMN_INCOME}, null, null, null, null, null);
	}
	
}
