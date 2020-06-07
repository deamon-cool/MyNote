package com.android.mynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.android.mynote.database.NoteDbSchema.*;

public class NoteBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "noteBase.db";

    public NoteBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // " _id integer primary key autoincrement,"
        db.execSQL("create table " + NoteTable.NAME +
                "(" +
                NoteTable.Cols.INDEX + ", " +
                NoteTable.Cols.TITLE + ", " +
                NoteTable.Cols.DATE + ", " +
                NoteTable.Cols.DESCRIPTION +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

    }
}
