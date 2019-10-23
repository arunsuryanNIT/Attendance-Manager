package com.suryan.arun.attendancemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arun on 01-Jul-16.
 */

public class MyDatabase {
    public static final String DB_NAME="Attendance.db";
    public static final int DB_VERSION=2;
    public static final String DB_TABLE="admin";
    public static final String C_TASK="username";
    public static final String C_DESC="password";
    public static final String C_Course="course";
    public static final String C_Attend="Attended";
    public static final String C_Total="Total";
    public static final String C_Online="Online";
    public static final String Q_CREATE="CREATE" + " TABLE " + DB_TABLE + "(" + C_TASK +
            " TEXT, " + C_DESC + " TEXT, " + C_Course + " TEXT, " + C_Attend + " INTEGER, " + C_Total + " INTEGER, "+C_Online+" INTEGER)";
    Context c;
    DBHelper dbh;
    SQLiteDatabase database;
    public MyDatabase(Context context) {
        c=context;
    }

    public MyDatabase open() {
        dbh=new DBHelper(c);
        database= dbh.getWritableDatabase();
        return this;
    }

    public boolean write(String task, String desc,String Course) {
    ContentValues cv= new ContentValues();
        cv.put(C_TASK,task);
        cv.put(C_DESC,desc);
        cv.put(C_Course,Course);
        cv.put(C_Attend,0);
        cv.put(C_Total,0);
        cv.put(C_Online,0);
         database.insert(DB_TABLE,null,cv);
        return true;
    }

    public void close() {
        database.close();
    }

    public Cursor read() {
        String[] column=new String[]{C_TASK,C_DESC,C_Course,C_Attend,C_Total};
        Cursor cur=database.rawQuery("select * from "+DB_TABLE,null);
        return cur;
    }

    public void update(int attend,int total){
        database=dbh.getWritableDatabase();
        ContentValues val=new ContentValues();
        val.put(C_Attend,attend);
        val.put(C_Total,total);
        database.update(DB_TABLE,val,"Online=1",null);
    }

    public void update_Login(int status,String username){
        ContentValues values=new ContentValues();
        if (status==1)
        {values.put(C_Online,1);database.update(DB_TABLE,values,C_TASK+"='"+username+"'",null);}
        else if (username.equals("all"))
        { values.put(C_Online,0);
        database.update(DB_TABLE,values,null,null);}
    }

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Q_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
