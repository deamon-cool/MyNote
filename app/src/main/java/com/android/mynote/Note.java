package com.android.mynote;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Note { // struktura danych Notatnika
    private String mIndex;
    private String mTitle;
    private Date mDate;
    private String mDescription;

    public String getIndex() {
//        Log.d("App:MyNote", "Note -> getIndex()");
        return mIndex;
    }

    public void setIndex(String indexString) {
//        Log.d("App:MyNote", "Note -> setIndex()");
        mIndex = indexString;
    }

    public String getTitle() {
//        Log.d("App:MyNote", "Note -> getTitle()");
        return mTitle;
    }

    public void setTitle(String title) {
//        Log.d("App:MyNote", "Note -> setTitle()");
        mTitle = title;
    }

    public Date getDate() {
//        Log.d("App:MyNote", "Note -> getDate()");
        return mDate;
    }

    public void setDate(Date date) {
//        Log.d("App:MyNote", "Note -> setDate()");
        mDate = date;
    }

    public String getDescription() {
//        Log.d("App:MyNote", "Note -> getDescription()");
        return mDescription;
    }

    public void setDescription(String description) {
//        Log.d("App:MyNote", "Note -> setDescription()");
        mDescription = description;
    }
}
