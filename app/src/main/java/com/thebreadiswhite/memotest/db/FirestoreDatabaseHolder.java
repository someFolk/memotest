package com.thebreadiswhite.memotest.db;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

public abstract class FirestoreDatabaseHolder<T extends DatabaseAdapter>
{
    /**
     * A string constant to use in calls to the "log" methods. Its
     * value is often given by the name of the class, as this will
     * allow you to easily determine where log methods are coming
     * from when you analyze your logcat output.
     */
    private final String TAG;

    private String SERVER_APP_PREFIX;

    private boolean INITIALIZED = false;

    private Class<T> klass;
    private DatabaseHolder<T> holder;

    public FirestoreDatabaseHolder(@NonNull String tag)
    {
        TAG = tag + "FiresoreDatabaseHolder --> ";
    }


    // IMPORTANT:
    // Call Init in order to use this utility.
    // Without calling this setup you will get Runtime Exception
    protected void init(@NonNull DatabaseHolder<T> holder, @NonNull Class<T> klass, String appPrefix)
    {
        if (INITIALIZED) { return; }

        this.holder = holder;
        this.klass = klass;
        SERVER_APP_PREFIX = appPrefix;
        INITIALIZED = true;
    }

    private void isInitialized()
    {
        if (INITIALIZED) { return; }
        throw new RuntimeException(TAG + " Is NOT Initialized.");
    }

    // Pushing new document onto the collection
    public String firestoreInsert(T object)
    {
        isInitialized();

        // Reserve a document in the specified location.
        // This happens because we call .document() and than .set()
        DocumentReference rootRef = holder.getMultiDatabase().getFirestoreInstance().collection(SERVER_APP_PREFIX).document(SERVER_APP_PREFIX).collection(holder.getTableName()).document();

        rootRef
                .set(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override public void onSuccess(Void aVoid) {
                Log.d(TAG, "Document Snapshot successfully written!!");
            }})
                .addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing Document Snapshot", e);
            }
        });

        return rootRef.getId();
    }

    // This method is inserting a document into the database
    // Without checking if there is a copy of it on the database
    // THIS MIGHT BE ERROR PRONE
    public String firestoreBruteAdd(@NonNull T object)
    {
        isInitialized();

        // Reserve a document in the specified location.
        // This happens because we call .document() and than .set()
        CollectionReference rootRef = holder.getMultiDatabase().getFirestoreInstance().collection(holder.getTableName());

        rootRef
                .add(object)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        return rootRef.getId();
    }

    public void firestoreGetByKey(@NonNull String serverKey)
    {
        isInitialized();

        DocumentReference rootRef = holder.getMultiDatabase().getFirestoreInstance().collection(holder.getTableName()).document(serverKey);
        rootRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        T Object = document.toObject(holder.getKlass());
                        // TODO: deliver to the listener
                        return;
                    }
                    else
                    {
                        // TODO: decide whether to delete locally or add on the server
                        Log.d(TAG, "No such document");
                    }
                }
                else
                {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    // Be careful when using this method
    // It can cost you alot on each call.
    public void firestoreGetWholeCollection()
    {
        isInitialized();
        CollectionReference rootRef = holder.getMultiDatabase().getFirestoreInstance().collection(holder.getTableName());
        rootRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                T Object = document.toObject(holder.getKlass());
                            }
                        }
                        else
                        {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
