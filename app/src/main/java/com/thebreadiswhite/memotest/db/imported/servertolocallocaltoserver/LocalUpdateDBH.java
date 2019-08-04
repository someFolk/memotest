package com.morsela.tel.db.servertolocallocaltoserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.database.*;
import com.morsela.tel.db.DatabaseHolder;
import com.thebreadiswhite.memotest.db.instance.firebase.FirebaseInstance;
import com.morsela.tel.db.util.Field;
import com.morsela.tel.db.util.FieldType;
import com.morsela.tel.taskpackage.TaskPackage;

// This
public class LocalUpdateDBH extends DatabaseHolder<LocalUpdate>
{

    // Builder class
    private static final Class aClass = com.morsela.tel.db.servertolocallocaltoserver.LocalUpdate.class;

    // Table Name - DO NOT CHANGE IN ANY CASE AFTER DEPLOY
    public static final String TABLE_NAME = "localupdate";

    private static final String KEY_IS_DONE = "isdone";
    private static final String KEY_NODE = "node";
    private static final String KEY_TARGET_KEY = "targetkey";
    private static final String KEY_TYPE = "type";


    public LocalUpdateDBH(Context context)
    {
        // Init `fields array list` variable.
        super(context);

        fields.add(new Field(KEY_IS_DONE, FieldType.BOOL));
        fields.add(new Field(KEY_NODE, FieldType.TEXT));
        fields.add(new Field(KEY_TARGET_KEY, FieldType.TEXT));
        fields.add(new Field(KEY_TYPE, FieldType.INT));

        super.init(TABLE_NAME, aClass);
    }

    public static String getTableName()
    {
        return TABLE_NAME;
    }

    // Content values to put on DB
    @Override
    public ContentValues getContentValues(LocalUpdate update)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_KEY, update.getFirebaseKey());
        values.put(KEY_TIMESTAMP , update.getTimestamp());
        values.put(KEY_IS_DONE, update.isDone());
        values.put(KEY_NODE, update.getNode());
        values.put(KEY_TARGET_KEY, update.getTargetKey());
        values.put(KEY_TYPE, update.getType().value);

        return values;
    }

    @Override
    protected LocalUpdate extract(Cursor cursor)
    {

        LocalUpdate update = new LocalUpdate(
                cursor.getString(cursor.getColumnIndex(KEY_KEY)),
                cursor.getInt(cursor.getColumnIndex(KEY_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(KEY_NODE)),
                (cursor.getInt(cursor.getColumnIndex(KEY_IS_DONE)) > 0), //TODO: check that it's working
                cursor.getString(cursor.getColumnIndex(KEY_TARGET_KEY)),
                LocalUpdateType.getTypeByValue(cursor.getInt(cursor.getColumnIndex(KEY_TYPE)))
        );

        return update;
    }

    // This method get override because it doesn't need to insert into the firebase database.
    // just pulling data from there.
    @Override
    public String insert(LocalUpdate object, boolean firebaseInsert)
    {
        // Set timestamp before inserting to firebase
        object.setTimestamp(System.currentTimeMillis() / 1000);

        // Inserting to firebase
        String key = firebaseInsert(object);
        object.setFirebaseKey(key);

        // Inserting to local db
        ContentValues cv = getContentValues(object);
        db.add(table, cv);

        return key;
    }

    @Override
    public void firebaseGetAllDataOnce(boolean installation)
    {

        // Initializing listener, not shooting it yet.
        final ChildEventListener eventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                // This is where we receive all the data from the server one-by-one
                LocalUpdate single = dataSnapshot.getValue(LocalUpdate.class);
                String key = dataSnapshot.getKey();
                single.setFirebaseKey(key);
                insertLocally(single);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                // Not important as we need only to load all the data
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
                // Not important as we need only to load all the data
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                // Not important as we need only to load all the data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e("INSTALLATION T.P", databaseError.getMessage());
            }
        };

        // Getting firebase reference
        DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference();

        // Getting child reference
        final DatabaseReference childRef = rootRef.child(TABLE_NAME);

        // Start getting data
        childRef.limitToLast(50).addChildEventListener(eventListener);

        // This shoots when the Child Event Listener is done loading
        // That way we know when to detach the listener
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Removing the OnChildEventListener
                // which consumes maximum amount of data
                childRef.removeEventListener(eventListener);

                Log.d("INSTALLATION  T.P", aClass.getName() + " Data has been fully loaded");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e("INSTALLATION  T.P", databaseError.getMessage());
            }
        };

        // Adding a stopper for ChildEventListener
        childRef.addListenerForSingleValueEvent(valueListener);
    }

}
