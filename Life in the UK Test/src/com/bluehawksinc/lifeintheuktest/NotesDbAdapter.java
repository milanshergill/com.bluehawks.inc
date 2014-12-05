/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bluehawksinc.lifeintheuktest;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class NotesDbAdapter {

    public static final String KEY_NAME = "name";
    public static final String MOCK1 = "mock1";
    public static final String MOCK2 = "mock2";
    public static final String MOCK3 = "mock3";
    public static final String MOCK4 = "mock4";
    public static final String MOCK5 = "mock5";
    public static final String MOCK6 = "mock6";
    public static final String MOCK7 = "mock7";
    public static final String MOCK8 = "mock8";
    public static final String MOCK9 = "mock9";
    public static final String MOCK10 = "mock10";
    public static final String MOCK11 = "mock11";
    public static final String MOCK12 = "mock12";
    public static final String MOCK13 = "mock13";
    public static final String MOCK14 = "mock14";
    public static final String MOCK15 = "mock15";
    public static final String CHAPTER2 = "chapter2";
    public static final String CHAPTER4 = "chapter4";
    public static final String CHAPTER3 = "chapter3";
    public static final String CHAPTER5 = "chapter5";
    public static final String CHAPTER6 = "chapter6";
    public static final String NAME = "name";
    public static final String SPECIAL = "special";
    public static final String KEY_ROWID = "_id";
    public static final String player1 = "player1";

    private static final String TAG = "NotesDbAdapter";
    private DatabaseHelper mdbHelper;
    private SQLiteDatabase mdb;

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "notes";
    private static final int DATABASE_VERSION = 22;
    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table notes (_id integer primary key autoincrement, "
        + "mock1 integer not null," +
        		"mock2 integer not null," +
        		"name text not null," +
        		"mock3 integer not null," +
        		"mock4 integer not null," +
        		"mock5 integer not null," +
        		"mock6 integer not null," +
        		"mock7 integer not null," +
        		"mock8 integer not null," +
        		"mock9 integer not null," +
        		"mock10 integer not null," +
        		"mock11 integer not null," +
        		"mock12 integer not null," +
        		"mock13 integer not null," +
        		"mock14 integer not null," +
        		"mock15 integer not null," +
        		"chapter3 integer not null," +
        		"chapter5 integer not null," +
        		"chapter4 integer not null," +
        		"chapter6 integer not null," +
        		"special integer not null," +
        		"chapter2 integer not null);";


    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public NotesDbAdapter(Context ctx) {
        this.mCtx = ctx;
        mdbHelper = new DatabaseHelper(ctx);
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public NotesDbAdapter open() throws SQLException {
        mdbHelper = new DatabaseHelper(mCtx);
        mdb = mdbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mdbHelper.close();
    }


    /**
     * Create a new note using the name and mock1 provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param name the name of the note
     * @param mock1 the mock1 of the note
     * @return rowId or -1 if failed
     */
    public long createDB() {
        ContentValues initialValues = new ContentValues();
     
        initialValues.put(MOCK1,0);
        initialValues.put(MOCK2,0);
        initialValues.put(MOCK3,0);
        initialValues.put(MOCK4,0);
        initialValues.put(MOCK5,0);
        initialValues.put(MOCK6,0);
        initialValues.put(MOCK7,0);
        initialValues.put(MOCK8,0);
        initialValues.put(MOCK9,0);
        initialValues.put(MOCK10,0);
        initialValues.put(MOCK11,0);
        initialValues.put(MOCK12,0);
        initialValues.put(MOCK13,0);
        initialValues.put(MOCK14,0);
        initialValues.put(MOCK15,0);
        initialValues.put(NAME,player1);
        initialValues.put(CHAPTER3,-1);
        initialValues.put(CHAPTER2,-1);
        initialValues.put(CHAPTER4,-1);
        initialValues.put(CHAPTER5,-1);
        initialValues.put(CHAPTER6,-1);
        initialValues.put(SPECIAL,0);

        return mdb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
  

    public boolean updateMock1(int mock1) {
   
    int rowId=1;
        ContentValues args = new ContentValues();
        args.put(MOCK1, mock1);
        return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;

       
    }
    public boolean updateSpecial(int score) {
    	   
        int rowId=1;
            ContentValues args = new ContentValues();
            args.put(SPECIAL, score);
            return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;

           
        }
    public boolean updateMock2(int mock2) {
   
    int rowId=1;
        ContentValues args = new ContentValues();
        args.put(MOCK2, mock2);

        return  mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
        
        
    }
    public boolean updateMock3(int mock3) {
   
    int rowId=1;
        ContentValues args = new ContentValues();
        args.put(MOCK3, mock3);

        return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    public boolean updateMock4(int mock4) {
   
    int rowId=1;
        ContentValues args = new ContentValues();
        args.put(MOCK4, mock4);

        return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

 public boolean updateMock5(int mock5) {
   
    int rowId=1;
        ContentValues args = new ContentValues();
        args.put(MOCK5, mock5);

        return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
 public boolean updateMock6(int mock6) {
 
  int rowId=1;
     ContentValues args = new ContentValues();
     args.put(MOCK6, mock6);

     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
 }
 public boolean updateMock7(int mock7) {
 
  int rowId=1;
     ContentValues args = new ContentValues();
     args.put(MOCK7, mock7);

     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
     
 }
 public boolean updateMock8(int mock8) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK8, mock8);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock9(int mock9) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK9, mock9);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock10(int mock10) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK10, mock10);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock11(int mock11) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK11, mock11);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock12(int mock12) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK12, mock12);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock13(int mock13) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK13, mock13);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock14(int mock14) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK14, mock14);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateMock15(int mock15) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(MOCK15, mock15);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateChapter2(int chapter) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(CHAPTER2, chapter);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateChapter3(int chapter) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(CHAPTER3, chapter);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateChapter4(int chapter) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(CHAPTER4, chapter);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateChapter5(int chapter) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(CHAPTER5, chapter);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateChapter6(int chapter) {
	 
	  int rowId=1;
	     ContentValues args = new ContentValues();
	     args.put(CHAPTER6, chapter);

	     return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	     
	 }
 public boolean updateName(String name) {
	   
	    int rowId=1;
	        ContentValues args = new ContentValues();
	        args.put(NAME, name);

	        return mdb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	    }
 
 public int getSpecial()
 {

 int rand=1;
     Cursor cursor = mdb.rawQuery(
                 "SELECT SPECIAL FROM notes WHERE _id = " + rand, null);
             if(cursor.moveToFirst()) {
                 return cursor.getInt(0);
             }
             return cursor.getInt(0);

 }   
 public int getMock1()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK1 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock2()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK2 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock3()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK3 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    
    public int getMock4()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK4 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock5()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK5 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock6()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK6 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock7()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK7 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock8()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK8 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock9()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK9 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock10()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK10 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock11()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK11 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock12()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK12 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock13()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK13 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock14()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK14 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getMock15()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT MOCK15 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getChapter2()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT CHAPTER2 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getChapter3()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT CHAPTER3 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getChapter4()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT CHAPTER4 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getChapter5()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT CHAPTER5 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
    public int getChapter6()
    {
   
    int rand=1;
        Cursor cursor = mdb.rawQuery(
                    "SELECT CHAPTER6 FROM notes WHERE _id = " + rand, null);
                if(cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return cursor.getInt(0);

    }
   
public String getSMock1()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK1 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);

}
public String getSName()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT NAME FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);

}
public String getSMock2()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK2 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);

}
public String getSMock3()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK3 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}
public String getSMock4()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK4 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}
public String getSMock5()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK5 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock6()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK6 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   

public String getSMock7()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK7 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock8()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK8 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}
public String getSMock9()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK9 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}  
public String getSMock10()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK10 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock11()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK11 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock12()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK12 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock13()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK13 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock14()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK14 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
public String getSMock15()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT MOCK15 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   



/*
public String getSSpecial_ques()
{
int rand=1;
    Cursor cursor = mdb.rawQuery(
                "SELECT CHAPTER4 FROM notes WHERE _id = " + rand, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            return cursor.getString(0);
}   
   
    */
}