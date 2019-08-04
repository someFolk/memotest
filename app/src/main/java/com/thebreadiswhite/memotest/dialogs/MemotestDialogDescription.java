package com.thebreadiswhite.memotest.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import com.thebreadiswhite.memotest.R;

public class MemotestDialogDescription extends DialogFragment
{

    public static final String TAG = "MemotestDialogDesc";

    private MemotestDialogInterfaceDesription parentActivity;

    // Interface for the purpose of communication
    public interface MemotestDialogInterfaceDesription
    {
        void setDescription(String description);
    }

    // Getting communication with the parent activity
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        parentActivity = (MemotestDialogInterfaceDesription) getActivity();
    }

    // Max Width size for the dialog.
    @Override
    public void onStart()
    {
        super.onStart();
        super.getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    // Creating the view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate(R.layout.dialog_memotest_description, container, false);

        final EditText xmlInput = (EditText) view.findViewById(R.id.memotest_dialog_description_input);


        // Initializing close button
        view.findViewById(R.id.memotest_dialog_description_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getDialog().dismiss();
            }
        });

        // Initializing submit button
        view.findViewById(R.id.memotest_dialog_description_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String data = xmlInput.getText().toString();
                parentActivity.setDescription(data);
                getDialog().dismiss();
            }
        });

        return view;
    }
}
