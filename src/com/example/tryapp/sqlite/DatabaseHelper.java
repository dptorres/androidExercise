package com.example.tryapp.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_DATA = "data";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_CELLNUM = "cellnum";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_INCOME = "income";
	
	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table " + TABLE_DATA + "(" + COLUMN_ID + " integer primary key autoincrement, " +
												   COLUMN_NAME + " text not null, " + COLUMN_EMAIL + " text not null, " 
												   + COLUMN_CELLNUM + " text not null, " + COLUMN_BIRTHDAY + " text not null, "
												   + COLUMN_INCOME + " double);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DATABASE_CREATE);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		    onCreate(db);
	}

}
