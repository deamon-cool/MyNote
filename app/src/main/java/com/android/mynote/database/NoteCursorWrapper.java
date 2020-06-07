package com.android.mynote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.mynote.Note;
import com.android.mynote.database.NoteDbSchema.NoteTable;

import java.util.Date;

public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String index = getString(getColumnIndex(NoteTable.Cols.INDEX));
        String title = getString(getColumnIndex(NoteTable.Cols.TITLE));
        long dateLong = getLong(getColumnIndex(NoteTable.Cols.DATE));
        String description = getString(getColumnIndex(NoteTable.Cols.DESCRIPTION));

        Note note = new Note();
        note.setIndex(index);
        note.setTitle(title);
        note.setDate(new Date(dateLong));
        note.setDescription(description);

        return note;
    }
}
