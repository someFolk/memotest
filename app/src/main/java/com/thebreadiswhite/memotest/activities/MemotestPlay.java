package com.thebreadiswhite.memotest.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.thebreadiswhite.memotest.modules.memotest.Memotest;
import com.thebreadiswhite.memotest.R;
import com.thebreadiswhite.memotest.modules.memotest.MemotestDBH;
import com.thebreadiswhite.memotest.dialogs.MemotestDialogClosePlay;
import com.thebreadiswhite.memotest.util.FavouriteConst;
import com.thebreadiswhite.memotest.util.MemotestConfig;

import java.util.List;

public class MemotestPlay extends AppCompatActivity implements MemotestDialogClosePlay.ClosePlayInterface
{
    // The key for the Extras
    public static final String EXTRAS_KEY_STACK = "stackid";

    private boolean favButtonIsFav = false;

    // This enum defines the state of which of the side of the memo is showing.
    // The purpose is to determining which next step is necessary to perform.
    // 1. The card is currently displaying the Front, therefore the next step is to display the Back.
    // 2. The card is currently displaying the Back, therefore the next step is to display the next card.
    public enum CurrentMemoDisplayingState{FRONT,BACK,FINISHED}

    // This var is being assigned by Extra from the previous activity
    // It determines which stack suppose to be played.
    private int stackId;

    // This var represent the memo cards to be played
    private List<Memotest> items;

    // This var represent the position of the card that is currently being displayed
    private int currentMemoCount = 0;

    private boolean scrollViewButtonVisible = false;

    // The current background color
    // This var help us determine if the background of
    // the current memo should be customize
    private int currentMemoBackgroundColor = MemotestConfig.MEMOTEST_DEFAULT_MEMO_BACKGROUND_COLOR;

    private int currentMemoTextColor = MemotestConfig.MEMOTEST_DEFAULT_MEMO_TEXT_COLOR;

    private Handler handler = new Handler();

    private Runnable getoutofhere;

    // The total amount of memotest in this play
    private int totalMemos;

    // This keeps an eye on the current state;
    private CurrentMemoDisplayingState currentDisplayState = CurrentMemoDisplayingState.FRONT;

    // Resource references
     private LinearLayout transitionsContainer;
     private ConstraintLayout constraintContainer;
     private ScrollView scrollviewContainer;
     private TextView memotestBack;
     private TextView memotestFront;
     private TextView memotestNumericPosition;

    // Overriding the back button so when the user want to get to the previous question
    @Override
    public void onBackPressed() {

        // Killer
        if(currentDisplayState == CurrentMemoDisplayingState.FINISHED)
        {
            // Removing the auto redirect
            handler.removeCallbacks(getoutofhere);

            // Finishing the activity
            this.finish();
            return;
        }

        MemotestDialogClosePlay exitDialog = new MemotestDialogClosePlay();
        exitDialog.show(getSupportFragmentManager(),MemotestDialogClosePlay.TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memotest_play);

        // Retrieve resource references
        transitionsContainer = (LinearLayout) findViewById(R.id.memotest_play_linear_container);
        constraintContainer = (ConstraintLayout) findViewById(R.id.memotest_play_clickable_container);
        scrollviewContainer = (ScrollView) findViewById(R.id.memotest_play_scrollview_container);
        memotestBack = (TextView) findViewById(R.id.memotest_play_label_back);
        memotestFront = (TextView) findViewById(R.id.memotest_play_label_front);
        memotestNumericPosition = (TextView) findViewById(R.id.memotest_play_numeric_position);

//        scrollviewContainer.getViewTreeObserver()
//                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                    @Override
//                    public void onScrollChanged() {
//                        scrollviewContainer = (ScrollView) findViewById(R.id.memotest_play_scrollview_container);
//                        Toast.makeText(MemotestPlay.this, "inside listener", Toast.LENGTH_SHORT).show();
//                        if(Ui.canScroll(scrollviewContainer))
//                        {
//                            Toast.makeText(MemotestPlay.this, "can scroll", Toast.LENGTH_SHORT).show();
//                            scrollviewContainer.fullScroll(View.FOCUS_DOWN);
////                            if (scrollviewContainer.getChildAt(0).getBottom()
////                                    == (scrollviewContainer.getHeight() + scrollviewContainer.getScrollY())) {
////                                showScrollButton(false);
////                            } else {
////                                showScrollButton(true);
////                            }
//                        }
//                    }
//                });

        // Getting the stack ID of which to extract the relevant memos
        int memoStackID = getIntent().getExtras().getInt(EXTRAS_KEY_STACK);

        MemotestDBH dbh = new MemotestDBH(getApplicationContext());
        items = dbh.selectAllChilds(memoStackID);

        // Total memo count
        totalMemos = items.size();

        // Show first memo
        showMemo(0);
        
        // Initialize Forward Buttons
        ((ImageView) findViewById(R.id.memotest_play_forward_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showMemo(currentMemoCount+1);
            }
        });

        // Initialize Back Buttons
        ((ImageView) findViewById(R.id.memotest_play_back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(currentDisplayState == CurrentMemoDisplayingState.FINISHED) return;
                showMemo(currentMemoCount-1);
            }
        });


        // Response to overall screen clicks while on Play.
        // In case the memo is on the Front side it displays the back of it.
        // In case the memo is on the Back side, it displaying the next one.

        final Activity currentActivity = this;
        getoutofhere = new Runnable()
        {
            @Override
            public void run()
            {
                currentActivity.finish();
            }
        };


        constraintContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(currentDisplayState == CurrentMemoDisplayingState.FINISHED)
                {
                    currentActivity.finish();
                    return;
                }
                // The card is currently on the Front side and needs to display the Back
                else if(currentDisplayState == CurrentMemoDisplayingState.FRONT)
                {
                    // Changing state
                    currentDisplayState = CurrentMemoDisplayingState.BACK;
                    // Apply Transition animation
                    TransitionManager.beginDelayedTransition(transitionsContainer);

                    // Showing the Back of the memo to the user
                    memotestBack.setVisibility(View.VISIBLE);

                    final LinearLayout transitionsContainer = (LinearLayout) findViewById(R.id.memotest_play_linear_container);
                    if(canScroll(scrollviewContainer, transitionsContainer))
                    {
                        Toast.makeText(currentActivity, "Can scroll", Toast.LENGTH_SHORT).show();
                        scrollviewContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollviewContainer.postDelayed(new Runnable() {
                                    public void run() {
                                        ObjectAnimator.ofInt(scrollviewContainer, "scrollY",  scrollviewContainer.getBottom() - 50).setDuration(500).start();
                                        //scrollviewContainer.fullScroll(View.FOCUS_DOWN);
                                        //scrollviewContainer.scrollTo(0, scrollviewContainer.getBottom());
                                    }
                                }, 500);
                            }
                        });
                    }
                }
                // Switching card
                else
                {
                    showMemo(currentMemoCount+1);
                }
            }
        });


    }
    public static boolean canScroll(ScrollView scrollView, LinearLayout linear) {
        View childFront = (View) linear.getChildAt(0);
        View childBack = (View) linear.getChildAt(1);

        if (childFront != null && childBack != null) {

            int childsHeight = (childFront).getHeight() + (childBack).getHeight();
            return scrollView.getHeight() < childsHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
        }
        return false;
    }
    private void showMemo(int itemNo)
    {

        // Killer
        if(itemNo < 0)
        {
            // There is no previous memos
            // You are currently on the first memo
            // apply opacity to the previous button
            Toast.makeText(this, "No previous memos", Toast.LENGTH_SHORT).show();

        }
        // Killer
        else if(itemNo >= totalMemos)
        {
            // Changing state
            currentDisplayState = CurrentMemoDisplayingState.FINISHED;

            ConstraintLayout favButton = (ConstraintLayout) findViewById(R.id.memotest_play_favourite_button);
            favButton.setVisibility(View.GONE);

            //TODO: Continue from here
            // a dao of the play data has been created.
            // just make it work and continue with the work, have a great day dear ;)


            // Hidding the play container
            scrollviewContainer.setVisibility(View.INVISIBLE);

            // Showing the finished view
            ((ConstraintLayout) findViewById(R.id.memotest_play_finish_container)).setVisibility(View.VISIBLE);


            // Setting auto redirect to the Stack activity
            handler.postDelayed(getoutofhere, 5000);

        }
        // Clean
        else
            {
                // Changing state
                currentDisplayState = CurrentMemoDisplayingState.FRONT;

                // Setting the counter
                currentMemoCount = itemNo;

                // Hiding the Back of the memo from the user
                memotestBack.setVisibility(View.GONE);

                // Getting the target item
                final Memotest target = items.get(currentMemoCount);

                changeMemoBackGroundColor(target);

                if(currentMemoTextColor != target.getTextColor())
                {
                    memotestBack.setTextColor(target.getTextColor());
                    memotestFront.setTextColor(target.getTextColor());
                }

                // Setting new data as a memo
                memotestFront.setText(target.getFront());
                memotestBack.setText(target.getBack());
                memotestNumericPosition.setText((currentMemoCount + 1) + " of " + totalMemos);


                ConstraintLayout favButton = (ConstraintLayout) findViewById(R.id.memotest_play_favourite_button);
                favButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        toggleFavourite(target);
                    }
                });

                ImageView favImage = (ImageView) findViewById(R.id.memotest_play_favourite_image);
                if(target.getFavourite() == FavouriteConst.REGULAR) {
                    if(favButtonIsFav) {
                        favImage.setImageResource(R.drawable.memotest_start_empty);
                        favButtonIsFav = false;
                    }

                }else
                    {
                        if( ! favButtonIsFav) {
                            favImage.setImageResource(R.drawable.memotest_star_gold);
                            favButtonIsFav = true;
                        }
                    }


                // Applying priority
//                if(target.getFavourite().value > 0)
//                {
//                    ((ImageView) findViewById(R.id.memotest_play_favourite)).setImageResource(R.drawable.memotest_star_gold);
//                }
            }
    }

    private void changeMemoBackGroundColor(Memotest target)
    {
        // Background Customization
        if(currentMemoBackgroundColor != target.getBackgroundColor())
        {
            int colorFrom = currentMemoBackgroundColor;
            int colorTo = adjustAlpha(target.getBackgroundColor(),0.8f);
            currentMemoBackgroundColor = target.getBackgroundColor();
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(500); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    scrollviewContainer.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        }
    }

    @Override
    public void returnToPreviousActivityFromDialog()
    {
        this.finish();
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void toggleFavourite(Memotest memotest)
    {
        Toast.makeText(this, "I's in toggeling", Toast.LENGTH_SHORT).show();
        ImageView favImage = (ImageView) findViewById(R.id.memotest_play_favourite_image);
        if(memotest.getFavourite() == FavouriteConst.FAVOURITE)
        {
            memotest.setFavourite(FavouriteConst.REGULAR);
            favImage.setImageResource(R.drawable.memotest_start_empty);
            favButtonIsFav = false;

        }else
        {
            memotest.setFavourite(FavouriteConst.FAVOURITE);
            favImage.setImageResource(R.drawable.memotest_star_gold);
            favButtonIsFav = true;
        }

        MemotestDBH dbh = new MemotestDBH(getApplicationContext());
        dbh.update(memotest);
    }
}
