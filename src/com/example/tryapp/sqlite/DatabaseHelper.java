package com.example.tryapp.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	public static final String TRAINEE_DATA = "trainee";
	public static final String HOURLY_EMP_DATA = "hourlyEmployee";
	public static final String EXECUTIVE_DATA = "executive";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_CELLNUM = "cellnum";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_INCOME = "income";
	public static final String COLUMN_WAGE = "wage";
	public static final String COLUMN_HOURS = "hours";
	public static final String COLUMN_BONUS = "bonus";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_SALARY = "salary";
	
	
	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TRAINEE_TABLE = "create table " + TRAINEE_DATA + "(" + COLUMN_ID + " integer primary key autoincrement, " +
												   COLUMN_NAME + " text not null, " + COLUMN_EMAIL + " text not null, " 
												   + COLUMN_CELLNUM + " text not null, " + COLUMN_BIRTHDAY + " text not null, "
												   + COLUMN_INCOME + " double, " + COLUMN_TYPE + " integer);";
	
	private static final String HOURLY_EMP_TABLE = "create table " + HOURLY_EMP_DATA + "(" + COLUMN_ID + " integer primary key autoincrement, " +
			   										COLUMN_NAME + " text not null, " + COLUMN_EMAIL + " text not null, " 
												    + COLUMN_CELLNUM + " text not null, " + COLUMN_BIRTHDAY + " text not null, "
												    + COLUMN_INCOME + " double, " + COLUMN_TYPE + " integer, " + COLUMN_WAGE + " double, " + COLUMN_HOURS + " integer);";
	
	private static final String EXECUTIVE_TABLE = "create table " + EXECUTIVE_DATA + "(" + COLUMN_ID + " integer primary key autoincrement, " +
												    COLUMN_NAME + " text not null, " + COLUMN_EMAIL + " text not null, " 
												    + COLUMN_CELLNUM + " text not null, " + COLUMN_BIRTHDAY + " text not null, "
												    + COLUMN_INCOME + " double, " + COLUMN_TYPE + " integer, " + COLUMN_BONUS + " integer, "
												    + COLUMN_SALARY + " double);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(TRAINEE_TABLE);
			db.execSQL(HOURLY_EMP_TABLE);
			db.execSQL(EXECUTIVE_TABLE);
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TRAINEE_DATA);
		    db.execSQL("DROP TABLE IF EXISTS " + HOURLY_EMP_DATA);
		    db.execSQL("DROP TABLE IF EXISTS " + EXECUTIVE_DATA);
		    onCreate(db);
	}

}
