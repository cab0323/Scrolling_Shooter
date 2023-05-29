package com.christian.scrollingshooter;

import android.graphics.PointF;

public class Particle {
    PointF mVelocity;
    PointF mPosition;

    Particle(PointF direction){
        mVelocity = new PointF();
        mPosition = new PointF();

        //determine direction
        mVelocity.x = direction.x;
        mVelocity.y = direction.y;
    }

    void update(){
        //move the particle
        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
    }

    void setPosition(PointF position){
        mPosition.x = position.x;
        mPosition.y = position.y;
    }

    PointF getPosition(){
        return mPosition;
    }
}
