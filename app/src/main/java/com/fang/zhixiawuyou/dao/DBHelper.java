package com.fang.zhixiawuyou.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "zhixiawuyou";
    private static final String CREATE_COLLECTION = "create table collection (" +
            "id integer primary key autoincrement, " +
            "title varchar(100), " +
            "date varchar(20), " +
            "image varchar(200), " +
            "link text )";
    private static final String CREATE_ITEM = "create table item (" +
            "id integer primary key autoincrement, " +
            "title varchar(100), " +
            "date varchar(20), " +
            "image varchar(200), " +
            "link text, " +
            " category varchar(20) )";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECTION);
        db.execSQL(CREATE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
