package com.morsela.tel.db.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.firebase.database.*;
import com.morsela.tel.user.User;
import com.morsela.tel.db.util.Table;

import java.util.ArrayList;

public class FirebaseTESTING<T>
{
    // * The Firebase console does not require empty constructor body.
    //   It only need you to have the fields Public OR
    //   Have getters to the fields that you want to store.
    //
    // * If you want to ignore some field use < @Exclude >

    private Context context;
    private DatabaseReference mDatabase;
    private T returnData;
    private boolean returnBoolean;

    public FirebaseTESTING(Context context)
    {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private String key;

    public String insert(Table table, T object)
    {
        DatabaseReference mDatabaseTemp = FirebaseDatabase.getInstance().getReference(table.getName());

        // Creating new user node, which returns the unique key value
        String key = mDatabaseTemp.push().getKey();

        // pushing user to 'users' node using the userId
        mDatabaseTemp.child(key).setValue(object);

        // Returning the String key on FireBase Database
        return key;
    }

    public void pull(Table table,int idn )
    {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child(table.getName())
                .orderByChild("idn")
                .equalTo(idn)
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        //User user = dataSnapshot.getValue(User.class);
                        //Toast.makeText(context, user.getName(), Toast.LENGTH_LONG).show();
                        //System.out.println("@@@@@@@@@@@@@@@@@@  " + dataSnapshot.getKey());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(context, "It CRASHED BECAUSE OF HIM: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void pullAll(Table table)
    {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("users").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
                {
                    User user = noteDataSnapshot.getValue(User.class);
                    users.add(user);

                }
                Toast.makeText(context, users.get(1).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "It CRASHED BECAUSE OF HIM: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void pullAllByIdn(Table table,int idn )
    {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child(table.getName())
                .orderByChild("idn").
                equalTo(idn)
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        ArrayList<User> users = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
                        {
                            User user = noteDataSnapshot.getValue(User.class);
                            users.add(user);

                        }
                        Toast.makeText(context, users.get(0).getName(), Toast.LENGTH_LONG).show();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "It CRASHED BECAUSE OF HIM: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    public T pullAllByIdn2(Table table,int idn, final Class<T> c )
//    {
//
//        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        returnBoolean = false;
//        database.child(table.getName())
//                .orderByChild("idn").
//                equalTo(idn)
//                .addValueEventListener(new ValueEventListener()
//                {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot)
//                    {
//                        ArrayList<T> values = new ArrayList<>();
//                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
//                        {
//                            T data = noteDataSnapshot.getValue(c);
//                            values.add(data);
//
//                        }
//                        returnData = values.get(0);
//                        Toast.makeText(context, "It's set!", Toast.LENGTH_LONG).show();
//                        Log.e(TAG, "it's set ;)");
//                        returnBoolean = true;
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError)
//                    {
//                        Toast.makeText(context, "It CRASHED BECAUSE OF HIM: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e(TAG, "Failed to read app title value.", databaseError.toException());
//                    }
//                     return returnData;
//                });
//
//        if(returnBoolean) return returnData;
//        else return null;
//    }
}
