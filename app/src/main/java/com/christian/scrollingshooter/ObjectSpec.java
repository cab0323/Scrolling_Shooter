package com.christian.scrollingshooter;

import android.graphics.PointF;

abstract class ObjectSpec {
    private String mTag;
    private String mBitMapName;
    private float mSpeed;
    private PointF mSizeScale;
    private String[] mComponents;

    ObjectSpec(String tag, String bitmapName, float speed, PointF relativeScale, String[] components){
        mTag = tag;
        mBitMapName = bitmapName;
        mSpeed = speed;
        mSizeScale = relativeScale;
        mComponents = components;
    }

    String getTag(){
        return mTag;
    }

    String getBitmapName(){
        return mBitMapName;
    }

    float getSpeed(){
        return mSpeed;
    }

    PointF getScale(){
        return mSizeScale;
    }

    String[] getComponents(){
        return mComponents;
    }
}
