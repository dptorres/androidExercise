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
								   DatabaseHelper.COLUMN_CELLNUM, DatabaseHelper.COLUMN_BIRTHDAY, DatabaseHelper.COLUMN_INCOME, 
								   DatabaseHelper.COLUMN_TYPE};
	
	private String[] allHourlyEmpColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_EMAIL, 
								   DatabaseHelper.COLUMN_CELLNUM, DatabaseHelper.COLUMN_BIRTHDAY, DatabaseHelper.COLUMN_INCOME, 
								   DatabaseHelper.COLUMN_TYPE, DatabaseHelper.COLUMN_WAGE, DatabaseHelper.COLUMN_HOURS};
	
	private String[] allExecutiveColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_EMAIL, 
								   DatabaseHelper.COLUMN_CELLNUM, DatabaseHelper.COLUMN_BIRTHDAY, DatabaseHelper.COLUMN_INCOME, 
								   DatabaseHelper.COLUMN_TYPE, DatabaseHelper.COLUMN_BONUS};
	
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
	
	public void insertToTrainee(Employee data) {
	    ContentValues values = new ContentValues();
	    createValues(data, values);
	    sqlDB.insert(DatabaseHelper.TRAINEE_DATA, null, values);
	}

	public void insertToHourlyEmployee(HourlyEmployee data) {
	    ContentValues values = new ContentValues();
	    createValues(data, values);
	    values.put(DatabaseHelper.COLUMN_WAGE, data.getWage());
	    values.put(DatabaseHelper.COLUMN_HOURS, data.getHours());
	    sqlDB.insert(DatabaseHelper.HOURLY_EMP_DATA, null, values);
	}
	
	public void insertToExecutive(Executive data) {
	    ContentValues values = new ContentValues();
	    createValues(data, values);
	    values.put(DatabaseHelper.COLUMN_BONUS, data.getPercent());
	    sqlDB.insert(DatabaseHelper.EXECUTIVE_DATA, null, values);
	}
	
	private void createValues(Employee data, ContentValues values) {
		values.put(DatabaseHelper.COLUMN_NAME, data.getName());
		values.put(DatabaseHelper.COLUMN_EMAIL, data.getEadd());
		values.put(DatabaseHelper.COLUMN_CELLNUM, data.getCellNum());
		values.put(DatabaseHelper.COLUMN_BIRTHDAY, data.getBirthday());
		values.put(DatabaseHelper.COLUMN_INCOME, data.getIncome());
		values.put(DatabaseHelper.COLUMN_TYPE, data.getType());
	}
	
	public void updateIncome(double income, int id) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_INCOME, income);
		sqlDB.update(DatabaseHelper.EXECUTIVE_DATA, values, DatabaseHelper.COLUMN_ID + "=" + id, null);
	}
	
	public void deleteEntry(int id, int type) {
		if(type == 0) {
			sqlDB.delete(DatabaseHelper.TRAINEE_DATA, DatabaseHelper.COLUMN_ID + "=" + id, null);
		} else if (type == 1) {
			sqlDB.delete(DatabaseHelper.HOURLY_EMP_DATA, DatabaseHelper.COLUMN_ID + "=" + id, null);
		} else {
			sqlDB.delete(DatabaseHelper.EXECUTIVE_DATA, DatabaseHelper.COLUMN_ID + "=" + id, null);
		}
		
	}
	
	public Cursor getEmployeeData(int id, int type) {
		if(type == 0) {
			return sqlDB.query(DatabaseHelper.TRAINEE_DATA, allColumns, DatabaseHelper.COLUMN_ID + "=" + id, 
					null, null, null, null);
		} else if(type == 1) {
			return sqlDB.query(DatabaseHelper.HOURLY_EMP_DATA, allHourlyEmpColumns, DatabaseHelper.COLUMN_ID + "=" + id, 
					null, null, null, null);
		} else {
			return sqlDB.query(DatabaseHelper.EXECUTIVE_DATA, allExecutiveColumns, DatabaseHelper.COLUMN_ID + "=" + id, 
					null, null, null, null);
		}
		
	}
	
	public void updateTrainee(int id, Employee data) {
		ContentValues values = new ContentValues();
	    createValues(data, values);
	    sqlDB.update(DatabaseHelper.TRAINEE_DATA, values, DatabaseHelper.COLUMN_ID + "=" + id, null);
	}
	
	public void updateHourlyEmp(int id, HourlyEmployee data) {
		ContentValues values = new ContentValues();
	    createValues(data, values);
	    values.put(DatabaseHelper.COLUMN_WAGE, data.getWage());
	    values.put(DatabaseHelper.COLUMN_HOURS, data.getHours());
	    sqlDB.update(DatabaseHelper.HOURLY_EMP_DATA, values, DatabaseHelper.COLUMN_ID + "=" + id, null);
	}
	
	public void updateExecutive(int id, Executive data) {
		ContentValues values = new ContentValues();
	    createValues(data, values);
	    values.put(DatabaseHelper.COLUMN_BONUS, data.getPercent());
	    sqlDB.update(DatabaseHelper.EXECUTIVE_DATA, values, DatabaseHelper.COLUMN_ID + "=" + id, null);
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

	public Cursor getBonusPercentage() {
		return sqlDB.query(DatabaseHelper.EXECUTIVE_DATA, new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_BONUS, 
						   DatabaseHelper.COLUMN_TYPE},null, null, null, null, null);
	}

}
