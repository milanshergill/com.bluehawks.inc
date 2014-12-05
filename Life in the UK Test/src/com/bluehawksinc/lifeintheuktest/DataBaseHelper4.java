package com.bluehawksinc.lifeintheuktest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper4 extends SQLiteOpenHelper{
 
   
//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.bluehawksinc.lifeintheuktest/databases/";
    private static String DB_NAME = "SpecialSign";
    
    private SQLiteDatabase myDataBase; 
    private SQLiteDatabase myData; 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper4(Context context) {
     super(context, DB_NAME, null, 1);
        this.myContext = context;
    }
    


/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException
    {
     //myContext.deleteFile(DB_NAME);
     myContext.deleteDatabase(DB_NAME);
     //boolean dbExist = checkDataBase();
     //if(dbExist){
     //do nothing - database already exist
     //}else{
     CopyFiles();
     //}
    }
    
    private void CopyFiles()
    {
     try
        { 
       InputStream is = myContext.getAssets().open(DB_NAME); 
       File outfile = new File(DB_PATH,DB_NAME);
       outfile.getParentFile().mkdirs();
       outfile.createNewFile();
       
      if (is == null)
          throw new RuntimeException("stream is null");
      else
      {
             FileOutputStream out = new FileOutputStream(outfile);      
      byte buf[] = new byte[128];
           do {
              int numread = is.read(buf);
               if (numread <= 0)
                   break;
              out.write(buf, 0, numread);
               } while (true);
           
           is.close();
           out.close();
      }
        }
        catch (IOException e)
        {
          throw new RuntimeException(e); 
        }
        
    }    
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    /*private boolean checkDataBase(){
 
     SQLiteDatabase checkDB = null;
 
     try{
     String myPath = DB_PATH + DB_NAME;
     checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    
     }catch(SQLiteException e){
 
     }
 
     if(checkDB != null){
     checkDB.close();
     }
 
     return checkDB != null ? true : false;
    }*/
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    /*private void copyDataBase() throws IOException{
 
     //Open your local db as the input stream
     InputStream myInput = myContext.getAssets().open(DB_NAME);
 
     // Path to the just created empty db
     String outFileName = DB_PATH + DB_NAME;
 
     //Open the empty db as the output stream
     OutputStream myOutput = new FileOutputStream(outFileName);
 
     byte[] buffer = new byte[1024];
     int length;
     while ((length = myInput.read(buffer))>0){
     myOutput.write(buffer, 0, length);
     }
 
     //Close the streams
     myOutput.flush();
     myOutput.close();
     myInput.close();
 
    }*/
 
    public void openDataBase() throws SQLException{
 
     //Open the database
        String myPath = DB_PATH + DB_NAME;
     myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    
    }
 
    @Override
public synchronized void close() {
 
        if(myDataBase != null)
        myDataBase.close();
 
        super.close();
 
}
 
@Override
public void onCreate(SQLiteDatabase db) {
 
}
 
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
}
 
      public Cursor getQues(int quesId)     
      {
       String myPath = DB_PATH + DB_NAME;
       myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);    
    
       Cursor cur;
       cur=myData.rawQuery("select quiz from Quiz where quiz_id='"+quesId+"'",null);
       cur.moveToFirst();
      
       myData.close();
       return cur;
      }
         
      public Cursor getOption1(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select op1 from Quiz where quiz_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOption2(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select op2 from Quiz where quiz_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOption3(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select op3 from Quiz where quiz_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOption4(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select op4 from Quiz where quiz_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      
      
      public Cursor getCorrAns(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      Cursor cur;
      cur = myData.rawQuery("select corr_ans from Quiz where quiz_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      
      return cur;  
      }
      
      public Cursor getImage(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select sign_ques from Sign where sign_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOp1(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select sign_op1 from Sign where sign_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOp2(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select sign_op2 from Sign where sign_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOp3(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select sign_op3 from Sign where sign_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getOp4(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select sign_op4 from Sign where sign_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      
      public Cursor getCorr(int quizid)
      {
      String myPath = DB_PATH + DB_NAME;
      myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      
      Cursor cur;
      cur = myData.rawQuery("select sign_corrans from Sign where sign_id='"+quizid+"'", null);
      cur.moveToFirst();
      myData.close();
      return cur;
      }
      /***********mock2*****************/
      public Cursor getMock2_ques(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock_ques from Mock2 where mock2_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock2_Op1(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock2_op1 from Mock2 where mock2_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock2_Op2(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock2_op2 from Mock2 where mock2_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock2_Op3(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock2_op3 from Mock2 where mock2_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock2_Op4(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock2_op4 from Mock2 where mock2_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock2_Corr(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock2_corrans from Mock2 where mock2_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      /*************mock2 ends***********/
      /*************mock1***********/
      public Cursor getMock1_ques(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock_ques from Mock1 where mock1_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock1_Op1(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock1_op1 from Mock1 where mock1_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock1_Op2(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock1_op2 from Mock1 where mock1_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock1_Op3(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock1_op3 from Mock1 where mock1_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock1_Op4(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock1_op4 from Mock1 where mock1_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock1_Corr(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock1_corrans from Mock1 where mock1_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      /*************mock1 ends***********/
      /*************mock3***********/
      public Cursor getMock3_ques(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock_ques from Mock3 where mock3_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock3_Op1(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock3_op1 from Mock3 where mock3_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock3_Op2(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock3_op2 from Mock3 where mock3_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock3_Op3(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock3_op3 from Mock3 where mock3_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock3_Op4(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock3_op4 from Mock3 where mock3_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock3_Corr(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock3_corrans from Mock3 where mock3_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      /*************mock3 ends***********/
      /*************mock4***********/
      public Cursor getMock4_ques(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock_ques from Mock4 where mock4_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock4_Op1(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock4_op1 from Mock4 where mock4_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock4_Op2(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock4_op2 from Mock4 where mock4_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock4_Op3(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock4_op3 from Mock4 where mock4_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock4_Op4(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock4_op4 from Mock4 where mock4_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock4_Corr(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock4_corrans from Mock4 where mock4_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      /*************mock4 ends***********/
      /*************mock5***********/
      public Cursor getMock5_ques(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock_ques from Mock5 where mock5_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock5_Op1(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock5_op1 from Mock5 where mock5_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock5_Op2(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock5_op2 from Mock5 where mock5_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock5_Op3(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock5_op3 from Mock5 where mock5_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock5_Op4(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock5_op4 from Mock5 where mock5_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      
      public Cursor getMock5_Corr(int quizid)
      {
          String myPath = DB_PATH + DB_NAME;
          myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
          
          Cursor cur;
          cur = myData.rawQuery("select mock5_corrans from Mock5 where mock5_id='"+quizid+"'", null);
          cur.moveToFirst();
          myData.close();
          return cur;
      }
      /*************mock5 ends***********/
      
      
}
