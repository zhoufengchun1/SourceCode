package com.example.qq1296821114.Data.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.qq1296821114.Data.Bean.Letter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDB {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "yesbutter";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    //保证全局只有一个数据库
    private static MyDB myDB;


    private SQLiteDatabase sqLiteDatabase;

    private MyDB(Context context) {
        DBOpenHelper myDBOpenHelper = new DBOpenHelper(context, DB_NAME, null, VERSION);
        sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
    }

    public synchronized static MyDB getMyDB(Context context) {
        if (myDB == null)
            myDB = new MyDB(context);
        return myDB;
    }

    public boolean saveLetter(Letter letter) {
        if (letter != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", letter.getId());
            contentValues.put("date", letter.getDate().toString());
            contentValues.put("email", letter.getEmail());
            contentValues.put("html", letter.getHtml());
            sqLiteDatabase.insert("LETTER", null, contentValues);

        }
        return true;
    }

    public List<Letter> loadLetter() {
        List<Letter> letters = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("LETTER", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Letter letter = new Letter();
                letter.setDate(new Date());
                letter.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                letter.setHtml(cursor.getString(cursor.getColumnIndex("html")));
                letter.setId(cursor.getInt(cursor.getColumnIndex("id")));
                letters.add(letter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return letters;
    }

}
