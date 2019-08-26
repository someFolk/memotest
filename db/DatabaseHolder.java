package com.thebreadiswhite.memotest.db;

import android.content.Context;
import androidx.room.ColumnInfo;
import com.thebreadiswhite.memotest.db.instance.multi.MultiDatabase;

import java.util.List;

// TODO: 1. instead of databaseholder extending firestoredbh you could do the opposite
// TODO:    so when app need to use the local DB only it's easy possible without modifications
// TODO:    and also you wont need to pass the holder downstream and the tag info as well
// TODO:    which is redundant.

//TODO: more modification suggestions are welcome

// The database holder is an container of a
// relevant information of ones object that's
// desiring to establish a connection with a database.
abstract public class DatabaseHolder<T extends DatabaseAdapter> extends FirestoreDatabaseHolder<DatabaseAdapter>
{
    // Database Instance
    protected MultiDatabase db;

    // The tag representing the holder
    // This will help us determine where
    // the problem came from if it came ;)
    public final String TAG;

    @ColumnInfo(name = "server_key")
    public static final String KEY_SERVER_KEY = "firebaseKey";

    @ColumnInfo(name = "timestamp")
    public static final String KEY_TIMESTAMP = "timestamp";

    // The table that is connected to the current DBH
    protected String tableName;

    // The class for firebase configuration
    protected Class<T> klass;

    // Init
    protected DatabaseHolder(Context context,String tag, String tableName, Class<T> klass)
    {
        super(tag);
        db = MultiDatabase.getInstance(context);
        TAG = tag;
        this.tableName = tableName;
        this.klass = klass;
    }



    public String getTAG()
    {
        return TAG;
    }

    public String getTableName()
    {
        return tableName;
    }

    public MultiDatabase getMultiDatabase()
    {
        return db;
    }

    public Class<T> getKlass(){ return klass; }

    // CRUD functionality

    abstract public int insert(T object);
    abstract public T selectById(int id);
    abstract public List<T> selectAll();
    abstract public void update(T object);
    abstract public void delete(T object);
}
