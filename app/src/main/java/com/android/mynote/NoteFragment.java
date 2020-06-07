package com.android.mynote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteFragment extends Fragment {
    private static final String NOTE_INDEX = "note_index";
    private static final String DIALOG_DELETE_QUESTION = "DialogDeleteQuestion";
    private static final int REQUEST_CHOICE = 1;
    private EditText mTitleEditText;
    private TextView mDateTextView;
    private EditText mDescriptionEditText;
    private Note mNote;

    public static NoteFragment getInstance(String index) {   // indeks będzie z RecyclerView NoteLab.getNotes().indexof(mNote)
        Log.d("App:MyNote", "NoteFragment -> getInstance()");
        Bundle args = new Bundle();
        args.putSerializable(NOTE_INDEX, index);
        NoteFragment noteFragment = new NoteFragment();
        noteFragment.setArguments(args);
        return noteFragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.d("App:MyNote", "NoteFragment -> onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NoteFragment -> onCreate()");
        super.onCreate(savedInstanceState);

        String noteIndex = getArguments().getString(NOTE_INDEX);
        mNote = NoteLab.getInstance(getActivity()).getNote(noteIndex);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NoteFragment -> onCreateView()");
        View v = inflater.inflate(R.layout.fragment_note, container, false);

        mTitleEditText = v.findViewById(R.id.title_edit_text);
        mTitleEditText.setText(mNote.getTitle());
        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateTextView = v.findViewById(R.id.date_text_view);
        String dateText = new SimpleDateFormat("'Data:' dd.MM.YYYY 'Czas:' HH:mm:ss").
                format(mNote.getDate());
        mDateTextView.setText(dateText);

        mDescriptionEditText = v.findViewById(R.id.description_edit_text);
        mDescriptionEditText.setText(mNote.getDescription());
        mDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "NoteFragment -> onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("App:MyNote", "NoteFragment -> onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("App:MyNote", "NoteFragment -> onResume()");
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("App:MyNote", "NoteFragment -> onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("App:MyNote", "NoteFragment -> onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.save_note_item:
                NoteLab.getInstance(getActivity()).updateNote(mNote);   // zapis notatki do danych

                Toast.makeText(getActivity(), R.string.note_saved, Toast.LENGTH_SHORT).show();

                return true;
            case R.id.delete_note_item:
                FragmentManager manager = getFragmentManager();
                DeleteQuestionDialogFragment dialog = new DeleteQuestionDialogFragment();
                dialog.setTargetFragment(this, REQUEST_CHOICE);
                dialog.show(manager, DIALOG_DELETE_QUESTION);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        Log.d("App:MyNote", "NoteFragment -> onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("App:MyNote", "NoteFragment -> onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("App:MyNote", "NoteFragment -> onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("App:MyNote", "NoteFragment -> onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("App:MyNote", "NoteFragment -> onDetach()");
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("App:MyNote", "NoteFragment -> onActivityResult()");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CHOICE) {
            boolean choice = data.getBooleanExtra(DeleteQuestionDialogFragment.EXTRA_CHOICE,
                    false);
            if (choice) {
                NoteLab.getInstance(getActivity()).removeNote(mNote);                             // usuwanie notatki
                Toast.makeText(getActivity(), R.string.note_removed, Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        }
    }
}
