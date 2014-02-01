package com.example.testgestureapp;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GestureDataBase {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_GESTURE };

	public GestureDataBase(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void openWriteable() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void openReadable() throws SQLException {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long insertGesture(Gesture_Object gesture) {
		ContentValues values = new ContentValues();
		byte[] gestureBlob = Gesture_Object.serializeGesture(gesture);
		values.put(MySQLiteHelper.COLUMN_GESTURE, gestureBlob);
		
		return database.insert(MySQLiteHelper.GESTURE_TABLE, null,
				values);
	}
	
	public Gesture_Object retriveGesture(long id) throws SQLException {
		Cursor cur = database.query(true, MySQLiteHelper.GESTURE_TABLE, allColumns, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null, null, null, null, null);
		if (cur.moveToFirst()) {
			byte[] gestureBlob = cur.getBlob(cur.getColumnIndex(MySQLiteHelper.COLUMN_GESTURE));
			cur.close();
			return Gesture_Object.deserializeGesture(gestureBlob);
		}
		cur.close();
		return null;
	}

	public void deleteGesture(long id) {
		System.out.println("Gesture deleted with id: " + id);
		database.delete(MySQLiteHelper.GESTURE_TABLE, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public ArrayList<Gesture_Object> getAllGestures() {
		ArrayList<Gesture_Object> gestureList = new ArrayList<Gesture_Object>();
		Cursor cur = database.query(MySQLiteHelper.GESTURE_TABLE, allColumns, null, null, null, null, null);
		cur.moveToFirst();
		while (!cur.isAfterLast()) {
			byte[] gestureBlob = cur.getBlob(cur.getColumnIndex(MySQLiteHelper.COLUMN_GESTURE));
			gestureList.add(Gesture_Object.deserializeGesture(gestureBlob));
			cur.moveToNext();
		}
		// make sure to close the cursor
		cur.close();
		return gestureList;
	}
}