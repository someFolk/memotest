package com.morsela.tel.db.util.firebaseobserver;

import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdate;

// R = return type of the data
public class FirebaseObserverHandler<E>
{
    // The key which the observer will be notify through
    private String key;
    private FirebaseObserver observer;
    private E data;
    private LocalUpdate localUpdate;
    private String node;

    public FirebaseObserverHandler(String key, FirebaseObserver observer, LocalUpdate localUpdate)
    {
        this.key = key;
        this.observer = observer;
        this.localUpdate = localUpdate;
    }

    // To retrieve data from OnChildAdded
    public FirebaseObserverHandler(FirebaseObserver observer, String node)
    {
        this.observer = observer;
        this.node = node;
    }

    public String getKey()
    {
        return key;
    }

    public FirebaseObserver getObserver()
    {
        return observer;
    }

    public LocalUpdate getLocalUpdate()
    {
        return localUpdate;
    }

    public void setData(E data)
    {
        this.data = data;
    }

    public E getData()
    {
        return data;
    }

    public String getNode()
    {
        return node;
    }
}
