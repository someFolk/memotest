package com.morsela.tel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.morsela.tel.db.instance.local.LocalDatabase;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdate;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdateDBH;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdateType;
import com.morsela.tel.db.util.Field;
import com.morsela.tel.db.util.FieldType;
import com.morsela.tel.db.util.Table;
import com.morsela.tel.db.util.firebaseobserver.FirebaseObserver;
import com.morsela.tel.db.util.firebaseobserver.FirebaseObserverHandler;

import java.util.ArrayList;

abstract public class DatabaseHolder<T extends com.morsela.tel.db.DatabaseAdapter> extends com.morsela.tel.db.FirebaseDatabaseHolder<com.morsela.tel.db.DatabaseAdapter>
{

    // Database Instance
    protected static LocalDatabase db;

    // The fields of the table
    protected ArrayList<Field> fields;

    // Default column for every table
    public static final String KEY_KEY = "firebaseKey";
    public static final String KEY_TIMESTAMP = "timestamp";

    // The table that is connected to the current DBH
    protected Table table;

    // The class for firebase configuration
    protected Class<T> aClass;

    // Init
    protected DatabaseHolder(Context context)
    {
        db = LocalDatabase.getInstance(context);

        fields = new ArrayList<>();
        fields.add(new Field(KEY_KEY, FieldType.TEXT));
        fields.add(new Field(KEY_TIMESTAMP, FieldType.INT));
    }

    protected DatabaseHolder(LocalDatabase localDatabase)
    {
        db = localDatabase;

        fields = new ArrayList<>();
        fields.add(new Field(KEY_KEY, FieldType.TEXT));
        fields.add(new Field(KEY_TIMESTAMP, FieldType.INT));
    }

    // This method should retrieve a unique
    // ContentValues table for each DBH subclass.
    public abstract ContentValues getContentValues(T object);

    // A method that extract data from a Cursor.
    protected abstract T extract(Cursor cursor);

    protected void init(String tableName, Class<T> aClass)
    {
        table = new Table(tableName, fields);
        this.aClass = aClass;
        super.init(this, aClass);
        install();
    }

    public Table getTable()
    {
        return table;
    }

    // A method to handle Data pulled by ID.
    public T selectById(String key)
    {
        return extract(db.getItemById(table, key));
    }

    // A method to handle Data pulled by ID.
    public T getItemByField(Field field, String key)
    {
        return extract(db.getItemByField(table, field,  key));
    }

    public ArrayList<T> selectAllByKey(Field field, String key)
    {
        Cursor cursor = db.getItemByField(table, field,  key);
        ArrayList<T> items = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            try
            {
                do
                {
                    T single = extract(cursor);
                    items.add(single);
                    //                    String key = single.getFirebaseKey();
                    //                    items.put(key, single);
                }
                while (cursor.moveToNext());
            }
            finally
            {
                if (cursor != null)
                {
                    cursor.close();
                }
            }
        }
        return items;
    }

    // A method to handle Data pulled by ID.
    public T getLastItemsOrderByTimestampWithLimit(int limit)
    {
        return extract(db.getLastItemsOrderByTimestampWithLimit(table, limit));
    }


    // Method to extract all table rows.
    public ArrayList<T> selectAll()
    {
        Cursor cursor = db.getAllItems(table);
        ArrayList<T> items = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            try
            {
                do
                {
                    T single = extract(cursor);
                    items.add(single);
//                    String key = single.getFirebaseKey();
//                    items.put(key, single);
                }
                while (cursor.moveToNext());
            }
            finally
            {
                cursor.close();
            }
        }
        return items;
    }



    public void install()
    {
        db.createTable(table);
    }

    public String insert(T object, boolean firebaseInsert)
    {
        long timestamp = System.currentTimeMillis() / 1000;

        // Set timestamp before firebase insert
        object.setTimestamp(timestamp);

        // Inserting to firebase
        String key = firebaseInsert(object);
        object.setFirebaseKey(key);

        // Realtime adjustment
        LocalUpdate update = new LocalUpdate(table.getName(), key, LocalUpdateType.ADDED);
        update.setTimestamp(timestamp);

        db.updateWithLocalUpdate(update);

        // Inserting to local db
        ContentValues cv = getContentValues(object);
        db.add(table, cv);

        return key;
    }

    public void update(T object)
    {
        db.update(table, getContentValues(object), object.getFirebaseKey());
        firebaseUpdate(object);
    }

    public void updateLocally(T object)
    {
        db.update(table, getContentValues(object), object.getFirebaseKey());
    }

    public void insertLocally(T object)
    {
        ContentValues cv = getContentValues(object);
        db.add(table, cv);
    }

    public Class<T> getaClass()
    {
        return aClass;
    }

    public boolean checkIsDataAlreadyInDBorNot(String targetKey)
    {
        Cursor cursor = db.getItemById(table,targetKey);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
