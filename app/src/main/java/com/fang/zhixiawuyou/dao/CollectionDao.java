package com.fang.zhixiawuyou.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.zhixiawuyou.bean.Collection;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/6/16.
 */
public class CollectionDao {
    private DBHelper dbHelper;

    public CollectionDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addCollection(Collection collection) {
        String sql = "insert into collection (title, date, image, link) values (?, ?, ?, ?)";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new String[] {collection.getTitle(), collection.getDate(),
                collection.getImage(), collection.getLink()});
        db.close();
    }

    public void deleteCollection(String title) {
        String sql = "delete from collection where title = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new String[] {title});
    }

    public List<Collection> queryCollection() {
        List<Collection> collectionList = new ArrayList<>();

        String sql = "select * from collection";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()) {
            Collection collection = new Collection();
            collection.setTitle(cursor.getString(1));
            collection.setDate(cursor.getString(2));
            collection.setImage(cursor.getString(3));
            collection.setLink(cursor.getString(4));
            collectionList.add(collection);
        }

        cursor.close();
        db.close();
        return collectionList;
    }

    public boolean isExistCollection(String title) {
        String sql = "select * from collection where title = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {title});
        if(cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
