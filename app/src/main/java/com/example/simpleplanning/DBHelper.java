package com.example.simpleplanning;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "SimplePlanningBD";
    public static final String TABLE_DO = "DO";
    public static final String TABLE_BUY = "BUY";
    public static final String TABLE_MONTH = "TMONTH";



    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_NOTE = "note";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DO + "(" + KEY_ID + " integer primary key autoincrement,"
                + KEY_DATE + " text," + KEY_NOTE + " text" + ")");
        db.execSQL("create table " + TABLE_BUY + "(" + KEY_ID + " integer primary key autoincrement,"
                + KEY_DATE + " text," + KEY_NOTE + " text" + ")");
        db.execSQL("create table " + TABLE_MONTH + "(" + KEY_ID + " integer primary key autoincrement,"
                + KEY_DATE + " text," + KEY_NOTE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_DO);
        db.execSQL("drop table if exists " + TABLE_BUY);
        db.execSQL("drop table if exists " + TABLE_MONTH);
        onCreate(db);
    }
}
