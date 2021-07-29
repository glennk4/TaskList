package com.glendall.tasklist;

/*
    TITLE: TASK LIST
    ACTIVITY: DATABASE MANAGER =
    AUTHOR: GLENN KENDALL
    DATE 26/02/2021
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ToDo.db";
    public static final String TABLE_NAME = "task_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "DUE";
    public static final String COL_5 = "COMPLETED";
    public static final String COL_6 = "DONE_DATE";

//Database manager Super Constructor
    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT," +
                "DESCRIPTION TEXT,DUE DATE, COMPLETED BOOLEAN, DONE_DATE DATE) ");
    }

//Overwrites current version if already exists upon upgrade of application
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String description, String due){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, due);
        contentValues.put(COL_5, 0);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result== -1) {
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT* FROM "+TABLE_NAME+" ORDER BY due",null);

      Log.d("TAG", DatabaseUtils.dumpCursorToString(result));
        return result;
    }

    public Cursor getTaskData(int taskId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT* FROM "+TABLE_NAME+" WHERE ID=="+taskId,null);
        return result;
    }

    public boolean update(String name, String description, String due, Boolean completed, String done_date){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, due);
        contentValues.put(COL_5, completed);
        contentValues.put(COL_6, done_date) ;

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result== -1) {
            return false;
        }
        else
            return true;
    }

    public boolean deleteTask(int deletedTask){
        SQLiteDatabase db =this.getWritableDatabase();

        long result = db.delete(TABLE_NAME,"ID=="+deletedTask,null);

        if (result== -1) {
            return false;
        }
        else
            return true;
    }

    public boolean completeTask(int completedTask){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, 1);
        long result = db.update(TABLE_NAME,contentValues,"ID=="+completedTask,null);

        if (result== -1) {
            return false;
        }
        else
            return true;
    }


    public boolean editTask(String name, String description, String due, int editedTask){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, due);

        long result = db.update(TABLE_NAME, contentValues, "ID=="+editedTask,null);

        if (result== -1) {
            return false;
        }
        else
            return true;
    }

}
