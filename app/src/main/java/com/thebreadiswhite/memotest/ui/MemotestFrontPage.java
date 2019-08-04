package com.thebreadiswhite.memotest.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.thebreadiswhite.memotest.MemotestStack;
import com.thebreadiswhite.memotest.R;
import com.thebreadiswhite.memotest.db.instance.room.RoomInstance;
import com.thebreadiswhite.memotest.dialogs.MemotestDialogNewStack;
import com.thebreadiswhite.memotest.util.FavouriteConst;
import com.thebreadiswhite.memotest.util.MemotestConfig;

import java.util.List;

/**
 * This is the front activity of the application.
 *
 * 1. This activity displays all the stacks of memos available.
 * TODO: implement a method which represent the last 20 plus stacks. when the user reach for the 70% of it, load more.
 *
 * 2. A search engine to search through the stack list.
 * TODO: Implement a tag search system that feeds from the stack title or descriptions. This about also memos.
 *
 * 3. A dialog for the purpose of creating a basic stack
 * @see MemotestDialogNewStack
 * ***/

public class MemotestFrontPage extends AppCompatActivity implements MemotestDialogNewStack.OnActionListener
{

    /**
     * This is a callback method which we implement from the interface
     * on the dialog class it is responsible for recieving the
     * description data from the dialog and process it.
     *
     * @param data represent the data which retrieved from the dialog.
     * */
    @Override
    public void memotestDialogNewStackListener(String data)
    {
        MemotestStack stack = new MemotestStack(0,data, MemotestConfig.MEMOTEST_DESCRIPTION_DATABASE_DEFAULT_VALUE, FavouriteConst.REGULAR);
        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());
        long id = db.memotestStack().insert(stack);

        // Sending us to the moon
        Intent gotothere = new Intent(getApplicationContext(), MemotestStackDisplay.class);
        gotothere.putExtra(MemotestConfig.MEMOTEST_EXTRA_ID_STACK_DISPLAY_CONST, (int)id);
        startActivity(gotothere);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memotest_front_page);

        // Hides the keyboard that is automatically opens when the activity starts
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Clears focus from the search box
        ((EditText) findViewById(R.id.memotest_frontpage_search)).setCursorVisible(false);
        // TODO: optimize the cursor and whne the edit text is visible

        // Getting items and inflating
        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());
        List<MemotestStack> items =  db.memotestStack().getAllStacks();
        if(items.size() > 1)
        {
            for(int i = 0; i < items.size(); i++)
            {
                xmlAddStackToUI(items.get(i),i);
            }
        }
        else // no items to show
        {
            ((TextView) findViewById(R.id.memotest_front_page_nothingtoshow)).setVisibility(View.VISIBLE);
        }

        // Back button initialize
        ConstraintLayout newStackButton = (ConstraintLayout) findViewById(R.id.memotest_front_page_new_stack_button);
        newStackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MemotestDialogNewStack dialog = new MemotestDialogNewStack();
                dialog.show(getSupportFragmentManager(),"TAG");
            }
        });

    }


    // This method responsible of taking a stack object
    // and construct a view to display to the user.
    private void xmlAddStackToUI(final MemotestStack stack, int position)
    {
        // The front of the memo
        String title = stack.getName();

        // The memo is favourited
        boolean favourited = stack.getFavourite() == FavouriteConst.FAVOURITE;

        // First Char for logo(circle) display
        // TODO: check if it's a number if its crashes or something, or what extacly happens
        String firstChar = Character.toString(title.charAt(0));

        // The parent layout to populate the items
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.memotest_front_page_general_container);

        // Inflating layout item
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.listitem_memotest_front_page_stack_preview,parentLayout,false);

        // Retrieve resources
        TextView xmlCircleLabel = view.findViewById(R.id.listitem_memotest_stack_single_circle_label);
        TextView xmlTitle = view.findViewById(R.id.listitem_memotest_stack_single_title);
        TextView xmlMemoCount = view.findViewById(R.id.listitem_memotest_stack_single_memo_count);
        final ImageView xmlImageFavourite = view.findViewById(R.id.listitem_memotest_stack_single_favourite);

        // Updating data on the User Interface
        xmlCircleLabel.setText(firstChar);
        xmlTitle.setText(title);

        // Getting memo count and displaying it
        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());
        int count = db.memotest().countStackItems(stack.getPk());
        xmlMemoCount.setText(Integer.toString(count));

        xmlImageFavourite.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                toggleFavourite(stack, xmlImageFavourite);
            }
        });

        // Attach on click listener to view a stack
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent gotothere = new Intent(getApplicationContext(), MemotestStackDisplay.class);
                gotothere.putExtra(MemotestConfig.MEMOTEST_EXTRA_ID_STACK_DISPLAY_CONST, stack.getPk());
                startActivity(gotothere);
            }
        });

        // Which one of the icons display to the user
        if(favourited)
        {
            xmlImageFavourite.setImageResource(R.drawable.memotest_star_gold);
            parentLayout.addView(view,1);
            return;
        }

        // Displaying the memo to the user
        parentLayout.addView(view,position + 1);
    }

    // This method responsible for toggeling the favourite of a stack
    // This methods handles the UI and DB part as well.
    public void toggleFavourite(MemotestStack stack, ImageView favouriteIcon)
    {
        if(stack.getFavourite() == FavouriteConst.FAVOURITE)
        {
            stack.setFavourite(FavouriteConst.REGULAR);
            favouriteIcon.setImageResource(R.drawable.memotest_star_gold);
        }else
            {
                stack.setFavourite(FavouriteConst.FAVOURITE);
                favouriteIcon.setImageResource(R.drawable.memotest_start_empty);
            }

        RoomInstance db = RoomInstance.getAppDatabase(getApplicationContext());
        db.memotestStack().update(stack);
    }
}
