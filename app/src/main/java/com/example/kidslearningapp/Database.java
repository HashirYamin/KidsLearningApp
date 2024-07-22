package com.example.kidslearningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "create table childrens(childname text, age text, password text)";
        String qry2 = "create table alphabetProgress(childname text, letter text)";
        String qry3 = "create table shapesProgress(childname text, shape text)";
        String qry4 = "create table mathProgress(childname text, level text)";
        db.execSQL(qry1);
        db.execSQL(qry2);
        db.execSQL(qry3);
        db.execSQL(qry4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

    public void register(String childname, String age, String password) {
        ContentValues values = new ContentValues();
        values.put("childname", childname);
        values.put("age", age);
        values.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("childrens", null, values);
        db.close();
    }

    public int login(String childname, String password) {
        int result = 0;
        String[] str = {childname, password};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from childrens where childname=? and password=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close();
        db.close();
        return result;
    }

    public void addAlphabetProgress(String childname, char letter) {
        if (!isLetterLearned(childname, letter)) {
            ContentValues values = new ContentValues();
            values.put("childname", childname);
            values.put("letter", String.valueOf(letter));
            SQLiteDatabase db = getWritableDatabase();
            db.insert("alphabetProgress", null, values);
            db.close();
        }
    }

    public boolean isLetterLearned(String childname, char letter) {
        String[] str = {childname, String.valueOf(letter)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from alphabetProgress where childname=? and letter=?", str);
        boolean result = c.moveToFirst();
        c.close();
        db.close();
        return result;
    }

    public int getAlphabetProgress(String childname) {
        String[] str = {childname};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from alphabetProgress where childname=?", str);
        int count = c.getCount();
        c.close();
        db.close();
        return count;
    }

    public void addMathProgress(String childname, String level) {
        ContentValues values = new ContentValues();
        values.put("childname", childname);
        values.put("level", level);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("mathProgress", null, values);
        db.close();
    }

    public int getMathProgress(String childname) {
        String[] str = {childname};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from mathProgress where childname=?", str);
        int count = c.getCount();
        c.close();
        db.close();
        return count;
    }

    public void addShapesProgress(String childname, String shape) {
        if (!isShapeLearned(childname, shape)) {
            ContentValues values = new ContentValues();
            values.put("childname", childname);
            values.put("shape", shape);
            SQLiteDatabase db = getWritableDatabase();
            db.insert("shapesProgress", null, values);
            db.close();
        }
    }

    public boolean isShapeLearned(String childname, String shape) {
        String[] str = {childname, shape};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from shapesProgress where childname=? and shape=?", str);
        boolean result = c.moveToFirst();
        c.close();
        db.close();
        return result;
    }

    public int getShapesProgress(String childname) {
        String[] str = {childname};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from shapesProgress where childname=?", str);
        int count = c.getCount();
        c.close();
        db.close();
        return count;
    }
}
