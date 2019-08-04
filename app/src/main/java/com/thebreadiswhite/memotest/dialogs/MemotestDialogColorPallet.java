package com.thebreadiswhite.memotest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.thebreadiswhite.memotest.R;

public class MemotestDialogColorPallet extends DialogFragment
{
    // The tag of the ialog
    public static final String TAG = "MemotestDialogColorPallet";

    // The parent Activity
    ChannelColorPallet parentActivity;

    //3// TODO: finish this dialog
    //4// TODO: get the scheme working from start to end
    //5// TODO: GOTO front page to display lists

    // the interface of which we interact through the dialog
    public interface ChannelColorPallet
    {
        // The method which the parent activity
        // implements for communication with the dialog.
        void getColorFromColorPalletDialog(int color);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        // Calling super, Getting dialog
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Flag for the "no title" feature, turning off the title at the top of the screen.
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Dialog is not disolve when clicking on hte ouside by mistake.
        // The back button still function as a closer.
        // For phones without the back button, there is a close button on the dialog.
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        // 95% width of screen
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Getting parent activity
        parentActivity = (ChannelColorPallet) getActivity();
    }

    private void chose(int color)
    {
        parentActivity.getColorFromColorPalletDialog(color);
        getDialog().dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // Inflating dialog layout
        View view = inflater.inflate(R.layout.dialog_memotest_color_pallet,container, false);

        // Initialize Close button
        view.findViewById(R.id.memotest_dialog_newmemo_button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        // No1. Blue Purple
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                chose(R.color.palletPurple);
            }
        });

                // No2. Blue Purple
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_2).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletBluePurple);
                    }
                });

                // No3. Blue
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_3).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletBlueStrong);
                    }
                });

        // No4. Blue Light
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                chose(R.color.palletBlueLight);
            }
        });

        // No4. Blue Cynan
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                chose(R.color.palletBlueCyan);
            }
        });

                // No5. BlueCyan
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_21).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletPink);
                    }
                });

                // No6. Blue Greenish
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_22).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletRed);
                    }
                });

                // No7. Green
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_23).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletOrange);
                    }
                });

                // No8. Green Light
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_24).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletOrangeYellowish);
                    }
                });

                // No9. Yellow Greenish
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_25).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletYellowStrong);
                    }
                });

                // No10. Black
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_31).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletBlueGreenish);
                    }
                });


                // No11. Gray
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_32).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletGreen);
                    }
                });

                // No12. White
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_33).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletGreenLight);
                    }
                });

                // No13. Pink
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_34).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletYellowGreenish);
                    }
                });

                // No14. Red
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_35).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletYellow);
                    }
                });

                // No15. Orange
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_41).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.blackText);
                    }
                });

                // No16.Orange Yellowish
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_42).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.palletGrey);
                    }
                });

                // No17.Yellow Strong
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_43).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        chose(R.color.veryLightGrey);
                    }
                });

        // No18. Grey scale 3
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_44).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                chose(R.color.primaryGreyBackgroundForWhite);
            }
        });

        // No18. While
        view.findViewById(R.id.memotest_dialog_new_memo_pallet_45).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                chose(R.color.colorWhite);
            }
        });

        // Showing the dialog to the user.
        return view;
    }

}
