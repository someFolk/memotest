package com.thebreadiswhite.memotest.modules.memotest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.thebreadiswhite.memotest.db.util.FavouriteConstConverter;
import com.thebreadiswhite.memotest.db.DatabaseAdapter;
import com.thebreadiswhite.memotest.util.FavouriteConst;
import com.thebreadiswhite.memotest.outsystems.positionable.Positionable;


@Entity(tableName = "memotest")
public class Memotest extends DatabaseAdapter
{

    // This is primary key which is assign
    // automatically by the Room Database.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    private int pk = 0;

    // This represent the FRONT
    // data of the memo card.
    @ColumnInfo(name = "front")
    private String front;

    // This represent the BACK
    // data of the memo card.
    @ColumnInfo(name = "back")
    private String back;

    // This
    @TypeConverters(FavouriteConstConverter.class)
    @ColumnInfo(name = "fav")
    private FavouriteConst favourite;

    // The key of the parent entity
    // that this memo belongs to.
    @ColumnInfo(name = "stack")
    private int stack;

    // The position of the memo
    // in the stack provided by
    /** @see Positionable **/
    @ColumnInfo(name = "pos")
    private int position;

    @ColumnInfo(name = "bg")
    private int backgroundColor;

    @ColumnInfo(name="tc")
    private int textColor;

    public Memotest(int pk, String front, String back, FavouriteConst favourite, int stack, int position, int backgroundColor, int textColor)
    {
        this.pk = pk;
        this.front = front;
        this.back = back;
        this.favourite = favourite;
        this.stack = stack;
        this.position = position;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public int getPk()
    {
        return pk;
    }

    public void setPk(int pk)
    {
        this.pk = pk;
    }

    public String getFront()
    {
        return front;
    }

    public void setFront(String front)
    {
        this.front = front;
    }

    public String getBack()
    {
        return back;
    }

    public void setBack(String back)
    {
        this.back = back;
    }

    public FavouriteConst getFavourite()
    {
        return favourite;
    }

    public void setFavourite(FavouriteConst favourite)
    {
        this.favourite = favourite;
    }

    public int getStack()
    {
        return stack;
    }

    public void setStack(int stack)
    {
        this.stack = stack;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }
}
