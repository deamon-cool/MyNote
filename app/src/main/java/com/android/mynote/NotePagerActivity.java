package com.android.mynote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class NotePagerActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE_INDEX = "com.android.mynote.note_index";
    private ViewPager mViewPager;
    private List<Note> mNotes;

    public static Intent newIntent(Context packageContext, String index) {
        Log.d("App:MyNote", "NotePagerActivity -> newIntent()");
        Intent intent = new Intent(packageContext, NotePagerActivity.class);
        intent.putExtra(EXTRA_NOTE_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NotePagerActivity -> onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_pager);

        mViewPager = findViewById(R.id.note_view_pager);

        mNotes = NoteLab.getInstance(getApplicationContext()).getNotes();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d("App:MyNote", "NotePagerActivity -> FragmentStatePagerAdapter(.) -> getItem()");
                return NoteFragment.getInstance(mNotes.get(position).getIndex());
            }

            @Override
            public int getCount() {
                Log.d("App:MyNote", "NotePagerActivity -> FragmentStatePagerAdapter(.) -> getCount()");
                return mNotes.size();
            }
        });

        String noteIndex = getIntent().getStringExtra(EXTRA_NOTE_INDEX);

        for (int i = 0; i < mNotes.size(); i++) {
            if (mNotes.get(i).getIndex().equals(noteIndex)) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    protected void onResume() {
        Log.d("App:MyNote", "NotePagerActivity -> onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("App:MyNote", "NotePagerActivity -> onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("App:MyNote", "NotePagerActivity -> onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("App:MyNote", "NotePagerActivity -> onDestroy()");
        super.onDestroy();
    }
}
