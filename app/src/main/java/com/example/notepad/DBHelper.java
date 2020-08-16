package com.example.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Data.db";
    public static final String NOTES_TABLE_NAME = "Notes";
    public static final String NOTES_COLUMN_ID = "id";
    public static final String NOTES_COLUMN_TITLE = "title";
    public static final String NOTES_COLUMN_TEXT = "txt";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Notes " +
                        "(id integer primary key, title text,txt text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Notes");
        onCreate(db);
    }

    public boolean insertNote (String title, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("txt", text);
        db.insert("Notes", null, contentValues);
        return true;
    }

    public void getData(String text) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Notes WHERE txt LIKE '%" + text + "%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Notes m1 = new Notes();
            m1.title = res.getString(res.getColumnIndex(NOTES_COLUMN_TITLE));
            m1.text = res.getString(res.getColumnIndex(NOTES_COLUMN_TEXT));
            m1.id = res.getString(res.getColumnIndex(NOTES_COLUMN_ID));
            G.nodes.add(m1);
            res.moveToNext();
        }
    }

    public boolean updateNote (String id, String title, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("txt", text);
        db.update("Notes", contentValues, "id = ? ", new String[] { id } );
        return true;
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Notes", "id = ? ", new String[] { id });
    }

    public void getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Notes", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Notes m1 = new Notes();
            m1.title = res.getString(res.getColumnIndex(NOTES_COLUMN_TITLE));
            m1.text = res.getString(res.getColumnIndex(NOTES_COLUMN_TEXT));
            m1.id = res.getString(res.getColumnIndex(NOTES_COLUMN_ID));
            G.nodes.add(m1);
            res.moveToNext();
        }
    }
}
