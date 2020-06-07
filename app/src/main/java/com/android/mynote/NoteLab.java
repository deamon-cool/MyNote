package com.android.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.mynote.database.NoteBaseHelper;
import com.android.mynote.database.NoteCursorWrapper;
import com.android.mynote.database.NoteDbSchema.NoteTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteLab {
    private static NoteLab sNoteLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static NoteLab getInstance(Context context) {   // tworzy statyczną isntancję klasy jeżeli nie została jeszcze stworzona
        Log.d("App:MyNote", "NoteLab -> getInstance()");
        if (sNoteLab == null) {
            Log.d("App:MyNote", "NoteLab -> sNoteLab doesn't exist, creating new sNoteLab()");
            sNoteLab = new NoteLab(context);
        }
        return sNoteLab;
    }

    private NoteLab(Context context) { // Tutaj tworzenie listy notatek
        Log.d("App:MyNote", "NoteLab -> konstruktor NoteLab()");
        mContext = context.getApplicationContext();
        mDatabase = new NoteBaseHelper(mContext).getWritableDatabase();
    }

    public List<Note> getNotes() {
        Log.d("App:MyNote", "NoteLab -> getNotes()");
        List<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursor = queryNotes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return notes;
    }

    public Note getNote(String index) {
        Log.d("App:MyNote", "NoteLab -> getNote()");

        NoteCursorWrapper cursor = queryNotes( NoteTable.Cols.INDEX + " = ? ",
                new String[] {index});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();

            Note note = cursor.getNote();
            Log.d("coś", "sa");

            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

//    public Note getNote(String index) {
//        Cursor cursor = mDatabase.query(NoteTable.NAME, null,
//                NoteTable.Cols.INDEX + " = ?", new String[] {index},
//                null, null, null);
//
//        Log.d("App:MyNote", "");
//
//        try {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//
//            cursor.moveToFirst();
//            String indexString = cursor.getString(cursor.getColumnIndex(NoteTable.Cols.INDEX));
//            String title = cursor.getString(cursor.getColumnIndex(NoteTable.Cols.TITLE));
//            Long date = cursor.getLong(cursor.getColumnIndex(NoteTable.Cols.DATE));
//            String description = cursor.getString(cursor.getColumnIndex(NoteTable.Cols.DESCRIPTION));
//
//            Note note = new Note();
//            note.setIndex(indexString);
//            note.setTitle(title);
//            note.setDate(new Date(date));
//            note.setDescription(description);
//
//            return note;
//        } finally {
//            cursor.close();
//        }
//    }

    public void addNote(Note note) {
        Log.d("App:MyNote", "NoteLab -> addNote()");

        ContentValues values = getContentValues(note);

        long ID = mDatabase.insert(NoteTable.NAME, null, values);
        if (ID == -1) {
            Log.d("App:MyNote", "NoteLab -> addNote() -> ERROR inserting the data. This can happen if you have a conflict with pre-existing data in the database");
        }
    }

    public void updateNote(Note note) {
        Log.d("App:MyNote", "NoteLab -> updateNote()");
        String index = String.valueOf(note.getIndex());
        ContentValues values = getContentValues(note);

        mDatabase.update(NoteTable.NAME, values, NoteTable.Cols.INDEX + " = ?",
                new String[] {index});
    }

    public void removeNote(Note note) {
        mDatabase.delete(NoteTable.NAME, NoteTable.Cols.INDEX + " = ?",
                new String[] {note.getIndex()});
    }

    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(NoteTable.NAME, null, whereClause, whereArgs,
                null, null, null);

        return new NoteCursorWrapper(cursor);
    }

    private ContentValues getContentValues(Note note) {
        Log.d("App:MyNote", "NoteLab -> getContentValues()");
        ContentValues values = new ContentValues();
        values.put(NoteTable.Cols.INDEX, note.getIndex());
        values.put(NoteTable.Cols.TITLE, note.getTitle());
        values.put(NoteTable.Cols.DATE, String.valueOf(note.getDate().getTime()));  // uwaga !!! datę zapisuje do formatu Long (time)
        values.put(NoteTable.Cols.DESCRIPTION, note.getDescription());

        return values;
    }
}
