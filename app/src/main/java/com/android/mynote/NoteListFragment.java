package com.android.mynote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class NoteListFragment extends Fragment {
    private RecyclerView mNoteRecyclerView;
    private NoteAdapter mNoteAdapter;
//    private int mClickedItemIndex;

    @Override
    public void onAttach(Context context) {
        Log.d("App:MyNote", "NoteListFragment -> onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NoteListFragment -> onCreate()");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NoteListFragment -> onCreateView()");
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);

        mNoteRecyclerView = v.findViewById(R.id.note_recycler_view);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        Log.d("App:MyNote", "NoteListFragment -> updateUI()");
        NoteLab noteLab = NoteLab.getInstance(getActivity());
        List<Note> notes = noteLab.getNotes();

        if (mNoteAdapter == null) {
            mNoteAdapter = new NoteAdapter(notes);
            mNoteRecyclerView.setAdapter(mNoteAdapter); // setAdapter() ustawia widok listy od początku
        } else {
//            mNoteAdapter.notifyItemChanged(mClickedItemIndex); // dla odświeżania jednego itemu z listy recyclerView
            mNoteAdapter.setNotes(notes);
            mNoteAdapter.notifyDataSetChanged();    // dodano, ponieważ po usunięciu notatki następował błąd
        }
    }


    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Note mNote;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_note, parent, false));
            Log.d("App:MyNote", "NoteListFragment -> NoteHolder -> konstruktor()");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    // zaokrąglenie rogów widoku kolejnych itemów
                itemView.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
                    }
                });
                itemView.setClipToOutline(true);
            }
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mDateTextView = itemView.findViewById(R.id.date_text_view);     // UWAGA ! date_text_view jest dublowane w fragment_note.xml i list_item_note.xml
        }

        public void bind(Note note) {
            Log.d("App:MyNote", "NoteListFragment -> NoteHolder -> bind()");
            mNote = note;
            mTitleTextView.setText(mNote.getTitle());

            Date date = mNote.getDate();
            String dateText = new SimpleDateFormat("'Data:' dd.MM.yyyy 'Czas:' HH:mm:ss").format(date);

            mDateTextView.setText(dateText);
        }

        @Override
        public void onClick(View view) {
            Log.d("App:MyNote", "NoteListFragment -> NoteHolder -> onClick()");
//            mClickedItemIndex = NoteLab.getInstance().getNotes().indexOf(mNote); // dla odświeżania jednego itemu z listy recyclerView
//            Intent intent = NotePagerActivity.newIntent(getActivity(), mClickedItemIndex); // dla odświeżania jednego itemu z listy recyclerView
            Intent intent = NotePagerActivity.newIntent(getActivity(), mNote.getIndex());
            startActivity(intent);
        }
    }


    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes) {  // przypisuje dane z modelu NoteLab
            Log.d("App:MyNote", "NoteListFragment -> NoteAdapter -> konstruktor()");
            mNotes = notes;
        }

        @NonNull
        @Override
        public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("App:MyNote", "NoteListFragment -> NoteAdapter -> onCreateViewHolder()");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new NoteHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
            Log.d("App:MyNote", "NoteListFragment -> NoteAdapter -> onBindViewHolder()");
            Note note = mNotes.get(position);
            holder.bind(note);
        }

        @Override
        public int getItemCount() {
            Log.d("App:MyNote", "NoteListFragment -> NoteAdapter -> getItemCount()");
            return mNotes.size();
        }

        public void setNotes(List<Note> notes) {
            mNotes = notes;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NoteListFragment -> onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("App:MyNote", "NoteListFragment -> onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("App:MyNote", "NoteListFragment -> onResume()");
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("App:MyNote", "NoteListFragment -> onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("App:MyNote", "NoteListFragment -> onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.new_note_item:
                Note note = new Note(); // data ustawiana w konstruktorze Note
                note.setDate(GregorianCalendar.getInstance().getTime());

                NoteLab noteLab = NoteLab.getInstance(getActivity());

                int index = 0;
                if (noteLab.getNotes().size() != 0) {
                    String indexString = noteLab.getNotes().get(noteLab.getNotes().size() - 1).getIndex();
                    if (indexString.contains("-")) {
                        String[] tabString = indexString.split("-");
                        index = Integer.parseInt(tabString[1]) + 1;
                    } // else {
//                    throw new IllegalArgumentException("String " + indexString + " does not contain -");
//                  }
                }
                note.setIndex("INDEX-" + index);
                noteLab.addNote(note);

                Intent intent = NotePagerActivity.newIntent(getActivity(), note.getIndex());
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        Log.d("App:MyNote", "NoteListFragment -> onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("App:MyNote", "NoteListFragment -> onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("App:MyNote", "NoteListFragment -> onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("App:MyNote", "NoteListFragment -> onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("App:MyNote", "NoteListFragment -> onDetach()");
        super.onDetach();
    }
}
