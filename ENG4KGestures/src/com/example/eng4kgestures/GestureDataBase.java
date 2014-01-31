package com.example.eng4kgestures;

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

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long insertGesture(Gesture gesture) {
		ContentValues values = new ContentValues();
		byte[] blob = Gesture.serializeGesture(gesture);
		values.put(MySQLiteHelper.COLUMN_GESTURE, blob);
		
		return database.insert(MySQLiteHelper.GESTURE_TABLE, null,
				values);
	}
	
	public Gesture retriveGesture() throws SQLException {
		Cursor cur = database.query(true, MySQLiteHelper.GESTURE_TABLE, allColumns, null, null, null, null, null, null);
		if (cur.moveToFirst()) {
			byte[] blob = cur.getBlob(cur.getColumnIndex(MySQLiteHelper.COLUMN_GESTURE));
			cur.close();
			return Gesture.deserializeGesture(blob);
		}
		cur.close();
		return null;
	}

	public void deleteGesture(long id) {
		System.out.println("Gesture deleted with id: " + id);
		database.delete(MySQLiteHelper.GESTURE_TABLE, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

//	public List<Acceleration> getAllAccelerations() {
//		List<Acceleration> accelerations = new ArrayList<Acceleration>();
//
//		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//				allColumns, null, null, null, null, null);
//
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Acceleration acceleration = cursorToAcceleration(cursor);
//			accelerations.add(acceleration);
//			cursor.moveToNext();
//		}
//		// make sure to close the cursor
//		cursor.close();
//		return accelerations;
//	}

//	private Acceleration cursorToAcceleration(Cursor cursor) {
//		Acceleration acceleration = new Acceleration();
//		acceleration.setId(cursor.getLong(0));
//		acceleration.setName(cursor.getString(1));
//		acceleration.setAccelerationX(cursor.getFloat(2));
//		acceleration.setAccelerationY(cursor.getFloat(3));
//		acceleration.setAccelerationZ(cursor.getFloat(4));
//		return acceleration;
//	}
}