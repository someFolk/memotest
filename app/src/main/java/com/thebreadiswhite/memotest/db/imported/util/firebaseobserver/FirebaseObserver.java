package com.morsela.tel.db.util.firebaseobserver;

// This interface will be inplemented to the class that recieves the update call
// When data is changed.
public interface FirebaseObserver<T>
{
    public void update(FirebaseObserverHandler<T> handler);
}
