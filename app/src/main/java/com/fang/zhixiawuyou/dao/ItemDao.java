package com.fang.zhixiawuyou.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.zhixiawuyou.bean.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class ItemDao {
    private DBHelper dbHelper;

    public ItemDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addItem(List<Item> itemList) {
        String sql = "insert into item (title, date, image, link, category) values (?, ?, ?, ?, ?)";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (Item item : itemList) {
            db.execSQL(sql, new String[] {item.getTitle(), item.getDate(),
                    item.getImageUrl(), item.getLink(), item.getCategory()});
        }
        db.close();
    }

    public void deleteItem(String category) {
        String sql = "delete from item where category = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new String[] {category});
    }

    public List<Item> queryItem(String category) {
        List<Item> itemList = new ArrayList<>();

        String sql = "select * from item where category = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {category});

        while(cursor.moveToNext()) {
            Item item= new Item();
            item.setTitle(cursor.getString(1));
            item.setDate(cursor.getString(2));
            item.setImageUrl(cursor.getString(3));
            item.setLink(cursor.getString(4));
            itemList.add(item);
        }

        cursor.close();
        db.close();
        return itemList;
    }

}
