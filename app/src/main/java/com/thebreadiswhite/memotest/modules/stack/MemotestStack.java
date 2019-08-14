package com.thebreadiswhite.memotest.modules.stack;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.thebreadiswhite.memotest.db.util.FavouriteConstConverter;
import com.thebreadiswhite.memotest.db.DatabaseAdapter;
import com.thebreadiswhite.memotest.modules.memotest.Memotest;
import com.thebreadiswhite.memotest.util.FavouriteConst;

import java.util.List;

@Entity(tableName = "memotest_stack")
public class MemotestStack extends DatabaseAdapter
{

    // This is the primary key that's
    // assigned by the Room Database
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int pk = 0;

    // The name(title) of the stack
    @ColumnInfo(name = "name")
    private String name;

    // The description of the stack
    // This is optional and doesn't get set when the stack is created.
    @ColumnInfo(name = "desc")
    private String description;

    @TypeConverters(FavouriteConstConverter.class)
    @ColumnInfo(name = "fav")
    private FavouriteConst favourite;

    // The items of the stack.
    // TODO: implement methods to sort and do other fnctionalities on the list.
    @TypeConverters(MemotestStackConverter.class)
    @ColumnInfo(name = "childs")
    private List<Memotest> items = null;


    public MemotestStack(int pk, String name, String description, FavouriteConst favourite)
    {
        this.pk = pk;
        this.name = name;
        this.description = description;
        this.favourite = favourite;
    }

    public int getPk()
    {
        return pk;
    }

    public void setPk(int pk)
    {
        this.pk = pk;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public FavouriteConst getFavourite()
    {
        return favourite;
    }

    public void setFavourite(FavouriteConst favourite)
    {
        this.favourite = favourite;
    }


    public List<Memotest> getItems()
    {
        return items;
    }

    public void setItems(List<Memotest> items)
    {
        this.items = items;
    }
}
