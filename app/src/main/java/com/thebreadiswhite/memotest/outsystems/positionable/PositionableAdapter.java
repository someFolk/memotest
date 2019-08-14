package com.thebreadiswhite.memotest.outsystems.positionable;

// Every positionable object
// will implement this interface.
public interface PositionableAdapter {
    int getPosition();
    void setPosition(int count);
}
