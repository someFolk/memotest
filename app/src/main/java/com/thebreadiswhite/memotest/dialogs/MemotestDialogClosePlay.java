package com.thebreadiswhite.memotest.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.thebreadiswhite.memotest.R;

public class MemotestDialogClosePlay extends DialogFragment
{

    public static final String TAG = "CLOSE_PLAY_DIALOG";

    private boolean exitQuestionmark = false;

    ClosePlayInterface parentActivity;

    public interface ClosePlayInterface
    {
        public void returnToPreviousActivityFromDialog();
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Flag for the "no title" feature, turning off the title at the top of the screen.
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        // 95% WIDTH of screen
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Getting parent activity
        parentActivity = (MemotestDialogClosePlay.ClosePlayInterface) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_memotest_close_play, container, false);

        view.findViewById(R.id.memotest_dialog_close_play_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getDialog().dismiss();
            }
        });

        view.findViewById(R.id.memotest_dialog_close_play_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                parentActivity.returnToPreviousActivityFromDialog();
            }
        });

        return view;
    }
}
