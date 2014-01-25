package com.example.eng4kgestures;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccelerationDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN1_COMMENT, MySQLiteHelper.COLUMN2_COMMENT,
			MySQLiteHelper.COLUMN3_COMMENT, MySQLiteHelper.COLUMN4_COMMENT };

	public AccelerationDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Acceleration createAcceleration(String name,Float accelerationX,Float accelerationY,Float accelerationZ) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN1_COMMENT, name);
		values.put(MySQLiteHelper.COLUMN2_COMMENT, accelerationX);
		values.put(MySQLiteHelper.COLUMN2_COMMENT, accelerationY);
		values.put(MySQLiteHelper.COLUMN3_COMMENT, accelerationZ);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
				values);
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		
		cursor.moveToFirst();
		
		Acceleration newAcceleration = cursorToAcceleration(cursor);
		cursor.close();
		return newAcceleration;
	}

	public void deleteComment(Acceleration acceleration) {
		long id = acceleration.getId();
		System.out.println("acceleration deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Acceleration> getAllAccelerations() {
		List<Acceleration> accelerations = new ArrayList<Acceleration>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Acceleration acceleration = cursorToAcceleration(cursor);
			accelerations.add(acceleration);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return accelerations;
	}

	private Acceleration cursorToAcceleration(Cursor cursor) {
		Acceleration acceleration = new Acceleration();
		acceleration.setId(cursor.getLong(0));
		acceleration.setName(cursor.getString(1));
		acceleration.setAccelerationX(cursor.getFloat(2));
		acceleration.setAccelerationY(cursor.getFloat(3));
		acceleration.setAccelerationZ(cursor.getFloat(4));
		return acceleration;
	}
}