package com.christian.scrollingshooter;

import android.graphics.PointF;

public class PlayerMovementComponent implements MovementComponent{
    @Override
    public boolean move(long fps, Transform t, Transform playerTransform){
        //find out how high the screen is
        float screenHeight = t.getmScreenSize().y;
        //find where the player is
        PointF location = t.getLocation();

        //find how fast it's going
        float speed = t.getSpeed();

        float height = t.getObjectHeight();

        //move ship up or down as needed
        if(t.headingDown()){
            location.y += speed / fps;
        }
        else if(t.headingUp()){
            location.y -= speed / fps;
        }

        //make sure ship cant go offscreen
        if(location.y > screenHeight - height){
            location.y = screenHeight - height;
        }
        else if(location.y < 0){
            location.y = 0;
        }

        //update collider
        t.updateCollider();

        return true;
    }
}
