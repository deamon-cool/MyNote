package com.android.mynote;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class NoteListActivity extends ConstructorFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }

    @Override
    protected void onStart() {
        Log.d("App:MyNote", "NoteListActivity -> onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("App:MyNote", "NoteListActivity -> onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("App:MyNote", "NoteListActivity -> onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("App:MyNote", "NoteListActivity -> onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("App:MyNote", "NoteListActivity -> onDestroy()");
        super.onDestroy();
    }
}
