package com.thebreadiswhite.memotest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.thebreadiswhite.memotest.R;

public class MemotestDialogNewStack extends DialogFragment
{

    // A TAG for the system for ease error troubleshooting.
    private static final String TAG = "___Dialog New Stack___";

    // This represent the activity that calling the dialog
    private OnActionListener parentActivityListener;

    // This is the interface which the activity
    // uses to implement a listener to this dialog.
    public interface OnActionListener
    {
        void memotestDialogNewStackListener(String name);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    // Setting the dialog to max width by setting the FILL_PARENT value
    @Override
    public void onStart()
    {
        super.onStart();
        super.getDialog().getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            // Getting the implemented method from the activity the dialog has been opened
            parentActivityListener = (OnActionListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        // XML init
        final View view = inflater.inflate(R.layout.dialog_new_stack, container, false);

        // Commit button initialize
        Button commitBtn = view.findViewById(R.id.dialog_new_stack_submit);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String data = ((EditText)view.findViewById(R.id.dialog_new_stack_input)).getText().toString();
                parentActivityListener.memotestDialogNewStackListener(data);
                getDialog().dismiss();
            }
        });

        // Commit button initialize
        Button closeBtn = view.findViewById(R.id.dialog_new_stack_cancel);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
