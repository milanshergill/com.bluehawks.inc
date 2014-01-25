package com.example.eng4kgestures;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_COMMENTS = "acceleration";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN2_COMMENT = "Ax";
  public static final String COLUMN3_COMMENT = "Ay";
  public static final String COLUMN4_COMMENT = "Az";
  public static final String COLUMN1_COMMENT = "name";

  private static final String DATABASE_NAME = "acceleration.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_COMMENTS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN1_COMMENT
      + " text not null ," + COLUMN2_COMMENT
      + " FLOAT not null," + COLUMN3_COMMENT
      + " FLOAT not null," + COLUMN4_COMMENT
      + " FLOAT not null  );";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
    onCreate(db);
  }

} 