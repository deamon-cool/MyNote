package com.android.mynote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteQuestionDialogFragment extends DialogFragment {
    public static final String EXTRA_CHOICE = "com.android.mynote.choice";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("App:MyNote", "DeleteQuestionDialogFragment -> onCreateDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_question).setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK, true);
                    }
                }).
                setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
}

    private void sendResult(int resultCode, boolean choice) {
        Log.d("App:MyNote", "DeleteQuestionDialogFragment -> sendResult()");

//        if (getTargetFragment() == null) {        SPRAWDŹ
//            return;
//        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CHOICE, choice);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
