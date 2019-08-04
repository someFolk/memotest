package com.thebreadiswhite.memotest.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.thebreadiswhite.memotest.Memotest;
import com.thebreadiswhite.memotest.MemotestStack;
import com.thebreadiswhite.memotest.R;
import com.thebreadiswhite.memotest.db.instance.room.RoomInstance;
import com.thebreadiswhite.memotest.dialogs.MemotestDialogDescription;
import com.thebreadiswhite.memotest.dialogs.MemotestDialogColorPallet;
import com.thebreadiswhite.memotest.ui.classefied.Ui.Visibility;
import com.thebreadiswhite.memotest.util.FavouriteConst;
import com.thebreadiswhite.memotest.util.MemotestConfig;

import java.util.List;

public class MemotestStackDisplay extends AppCompatActivity implements MemotestDialogColorPallet.ChannelColorPallet, MemotestDialogDescription.MemotestDialogInterfaceDesription
{
    private int stackID;
    private MemotestStack stack;

    //
    private Visibility currentNewmemoContainerState = Visibility.NOT_SHOWING;


    // Flag to hold the requester
    private ColorPalletRequested flagPalletRequester = null;

    // Values to store the custom color values that places on the Memotest object
    private int newMemotestBackgroundColor = MemotestConfig.MEMOTEST_DEFAULT_MEMO_BACKGROUND_COLOR;
    private int newMemotestTextColor = MemotestConfig.MEMOTEST_DEFAULT_MEMO_TEXT_COLOR;

    // Preparing drawables on variables for clear ability
    private int drawableBackgroundBlank = R.drawable.memotest_icon_background_blank;
    private int drawableBackgroundColored = R.drawable.memotest_icon_background_colored;
    private int drawableTextColorBlank = R.drawable.memotest_icon_font_blank;
    private int drawableTextColored = R.drawable.memotest_icon_font_colored;
    private int drawableFavouriteBlank = R.drawable.memotest_start_empty;
    private int drawableFavouriteColored = R.drawable.memotest_star_gold;

    // This enum contains constants of which one
    // of the features requested the color pallet.
    public enum ColorPalletRequested
    {
        BACKGROUND,
        TEXT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memotest_stack_display);


        // Checking if an extra has been provided
        if( ! getIntent().hasExtra(MemotestConfig.MEMOTEST_EXTRA_ID_STACK_DISPLAY_CONST))
        {
            // TODO: REDIRECT to the previous activity
            return;
        }
        else
        {
            stackID = getIntent().getExtras().getInt(MemotestConfig.MEMOTEST_EXTRA_ID_STACK_DISPLAY_CONST);
        }

        // Getting the memo stack
        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());
        stack = db.memotestStack().getStackById(stackID);

        // Getting memos
        List<Memotest> items = db.memotest().findAllByKey(stackID);

        // Getting xml elements
        TextView xmlStackTitle = (TextView) findViewById(R.id.memotest_stack_display_title);
        ConstraintLayout xmlRequestDescription = (ConstraintLayout) findViewById(R.id.memotest_stack_display_request_description);
        TextView xmlDescription = (TextView) findViewById(R.id.memotest_stack_display_description);
        TextView xmlMemoCount = (TextView) findViewById(R.id.memotest_stack_display_memo_count);
        ConstraintLayout xmlPlayButton = (ConstraintLayout) findViewById(R.id.memotest_stack_display_play_button);
        ConstraintLayout xmlShuffleButton = (ConstraintLayout) findViewById(R.id.memotest_stack_display_shuffle_button);
        ConstraintLayout xmlAddMemoButton = (ConstraintLayout) findViewById(R.id.memotest_stack_display_add_button);

        // Updating UI with fresh data
        xmlStackTitle.setText(stack.getName());

        //xmlStackDescription.setText(stack.getDescription());
        xmlMemoCount.setText(Integer.toString(items.size()) + " Memos");

        if(stack.getDescription().equals(MemotestConfig.MEMOTEST_DESCRIPTION_DATABASE_DEFAULT_VALUE))
        {

            Toast.makeText(this, "equals 0", Toast.LENGTH_SHORT).show();
            // Show UI element for submiting description
            xmlRequestDescription.setVisibility(View.VISIBLE);

            // Add description to the stack
            xmlRequestDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    MemotestDialogDescription dialog = new MemotestDialogDescription();
                    dialog.show(getSupportFragmentManager(), MemotestDialogDescription.TAG);
                }
            });
        }
        else
        {
            Toast.makeText(this, "There is a description", Toast.LENGTH_SHORT).show();
            xmlDescription.setText(stack.getDescription());
            xmlDescription.setVisibility(View.VISIBLE);

        }


        // Play Button
        xmlPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent gotothere = new Intent(getApplicationContext(), MemotestPlay.class);
                gotothere.putExtra(MemotestPlay.EXTRAS_KEY_STACK, stackID);
                startActivity(gotothere);
            }
        });

        // Shuffle Button
        xmlShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MemotestStackDisplay.this, "Shuffle what?", Toast.LENGTH_SHORT).show();
            }
        });


        ((Button) findViewById(R.id.memotest_dialog_newmemo_button_back)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                hideNewMemoContainer();
            }
        });

        ((Button) findViewById(R.id.memotest_dialog_newmemo_button_forward)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                EditText inputFront = (EditText) findViewById(R.id.memotest_stack_display_newmemo_front);
                String front = inputFront.getText().toString();

                EditText inputBack = (EditText) findViewById(R.id.memotest_stack_display_newmemo_back);
                String back = inputFront.getText().toString();

                Memotest memo = new Memotest(0,front,back,FavouriteConst.REGULAR,stackID,1, newMemotestBackgroundColor, newMemotestTextColor);
                addMemo(memo);
                hideNewMemoContainer();
            }
        });

        // New Memo text color button
        ((ConstraintLayout)findViewById(R.id.memotest_stack_display_newmemo_textcolor_preview)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                openColorPallet(ColorPalletRequested.TEXT);
            }
        });

        // New memo background color button
        ((ConstraintLayout)findViewById(R.id.memotest_stack_display_newmemo_backgroundcolor_preview)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                openColorPallet(ColorPalletRequested.BACKGROUND);
            }
        });

        // Initializing add memo button
        xmlAddMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showNewMemoContainer();
                // TODO: add a reset to the add memo fields?
            }
        });

        // the stack contains at least one item, show.
        if(items.size() > 0) {
            for (int i = 0; i < items.size(); i ++) {
                Memotest single = items.get(i);
                xmlAddMemoToUI(single, i);
            }
        }else {} // No memos to be shown
    }


    @Override
    public void onBackPressed() { this.finish(); }


    private void addMemo(Memotest memotest) {

        // Updating UI with the new memo
        // * As a general rule, update the UI before any other tasks.
        xmlAddMemoToUI(memotest, memotest.getPosition());

        // Getting room database instance
        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());

        // Insert new memotest to the database
        db.memotest().insert(memotest);
    }



    private void xmlAddMemoToUI(Memotest memotest, int position)
    {

        String front = memotest.getFront();

        boolean customizedBackground = memotest.getBackgroundColor() != MemotestConfig.MEMOTEST_DEFAULT_MEMO_BACKGROUND_COLOR;
        boolean customizedText = memotest.getTextColor() != MemotestConfig.MEMOTEST_DEFAULT_MEMO_TEXT_COLOR;
        boolean favourited = memotest.getFavourite() == FavouriteConst.FAVOURITE;

        // First Char for logo(circle) display
        // TODO: check if it's a number if its crashes or something, or what extacly happens
        String firstChar = Integer.toString(position);

        // The parent layout to populate the items
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.memotest_stack_display_memo_container);

        // Inflating layout item
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.listitem_memotest_single_memo_preview,parentLayout,false);

        // Retrieve resources
        ConstraintLayout xmlCircle = view.findViewById(R.id.listitem_memotest_single_circle);
        TextView xmlCircleLabel = view.findViewById(R.id.listitem_memotest_single_circle_label);
        TextView xmlTitle = view.findViewById(R.id.listitem_memotest_single_title);
        ImageView xmlImageBackground = view.findViewById(R.id.listitem_memotest_single_customized_background);
        ImageView xmlImageText = view.findViewById(R.id.listitem_memotest_single_customized_text);
        ImageView xmlImageFavourite = view.findViewById(R.id.listitem_memotest_single_favourite);

        // Updating data on the User Interface
        xmlCircleLabel.setText(firstChar);
        xmlTitle.setText(front);

        // Which one of the icons display to the user
        if(favourited) xmlImageFavourite.setImageResource(drawableFavouriteColored);

        // Which one of the icons display to the user
        if(customizedText) xmlImageText.setImageResource(drawableTextColored);

        // Which one of the icons display to the user
        if(customizedBackground) xmlImageBackground.setImageResource(drawableBackgroundColored);

        // Displaying the memo to the user
        parentLayout.addView(view);
    }

    @Override
    public void setDescription(String description)
    {
        // Retriving XML elements
        ConstraintLayout xmlRequestDescription = (ConstraintLayout) findViewById(R.id.memotest_stack_display_request_description);
        TextView xmlDescription = (TextView) findViewById(R.id.memotest_stack_display_description);

        // Hidding request Description view
        xmlRequestDescription.setVisibility(View.GONE);

        // Updating UI
        xmlDescription.setText(description);

        // Showing the description view to the user
        xmlDescription.setVisibility(View.VISIBLE);

        // Updating data on the Stack object
        stack.setDescription(description);

        // Getting DB instance
        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());

        // Updating stack on the Data Base
        db.memotestStack().update(stack);
    }

    private void hideNewMemoContainer()
    {
        if(currentNewmemoContainerState == Visibility.NOT_SHOWING) return;
        ((ConstraintLayout) findViewById(R.id.memotest_stack_display_newmemo_container)).setVisibility(View.GONE);
        currentNewmemoContainerState = Visibility.NOT_SHOWING;


    }

    private void showNewMemoContainer()
    {
        if(currentNewmemoContainerState == Visibility.SHOWING) return;
        ((ConstraintLayout) findViewById(R.id.memotest_stack_display_newmemo_container)).setVisibility(View.VISIBLE);
        currentNewmemoContainerState = Visibility.SHOWING;
    }

    @Override
    public void getColorFromColorPalletDialog(int color)
    {
        // No color has been chosen
        if(color == 0)
        {
            flagPalletRequester = null;
            return;
        }

        // Parsing the color


        // Change icon images as indication that the user has chose
        // a customization for this feature
        if(flagPalletRequester == ColorPalletRequested.BACKGROUND)
        {
            color = getResources().getColor(color);
            // Apply alpha becuase it's a background color -> so the text will be visible
            ((ConstraintLayout) findViewById(R.id.memotest_stack_display_newmemo_backgroundcolor_preview_colorable)).setBackgroundColor(color);
            ((TextView) findViewById(R.id.memotest_stack_display_newmemo_backgroundcolor_select_label)).setVisibility(View.GONE);
            newMemotestBackgroundColor = color;
        }

        // The feature selected is Text color
        else if(flagPalletRequester == ColorPalletRequested.TEXT)
        {

            ((ConstraintLayout) findViewById(R.id.memotest_stack_display_newmemo_textcolor_preview_colorable)).setBackgroundColor(color);
            ((TextView) findViewById(R.id.memotest_stack_display_newmemo_textcolor_select_label)).setVisibility(View.GONE);
            newMemotestTextColor = color;
        }

        flagPalletRequester = null;
    }

    public void openColorPallet(ColorPalletRequested requester)
    {
        flagPalletRequester  = requester;
        MemotestDialogColorPallet dialog = new MemotestDialogColorPallet();
        dialog.show(getSupportFragmentManager(), MemotestDialogColorPallet.TAG);
    }
}
