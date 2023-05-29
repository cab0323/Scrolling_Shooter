package com.christian.scrollingshooter;

import android.graphics.PointF;

public class LaserMovementComponent implements MovementComponent{
    @Override
    public boolean move(long fps, Transform t, Transform playerTransform){
        float range = t.getmScreenSize().x * 2;
        PointF location = t.getLocation();

        float speed = t.getSpeed();

        if(t.headingRight()){
            location.x += speed / fps;
        }
        else if(t.headingLeft()){
            location.x -= speed / fps;
        }

        //check if laser is out of range
        if(location.x < - range || location.x > range){
            return false;
        }

        t.updateCollider();
        return true;
    }
}
