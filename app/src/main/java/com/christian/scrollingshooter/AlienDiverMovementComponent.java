package com.christian.scrollingshooter;

import android.graphics.PointF;

import java.util.Random;

public class AlienDiverMovementComponent implements MovementComponent{
    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {
        //find the ship
        PointF location = t.getLocation();

        //get speed of ship
        float speed = t.getSpeed();

        //relative speed difference with player
        float slowDownRelativeToPlayer = 1.8f;

        //compensate for movement relative to player
        //but only when in view
        if(!playerTransform.getFacingRight()){
            location.x += speed * slowDownRelativeToPlayer / fps;
        }
        else {
            location.x -= speed * slowDownRelativeToPlayer / fps;
        }

        //fall down then respawn at the top
        location.y += speed / fps;

        //checks if alien fell off bottom of screen
        //if it did then it respawns at top of screen
        if(location.y > t.getmScreenSize().y){
            //respawn at the top
            Random random = new Random();

            location.y = random.nextInt(300)
                    - t.getObjectHeight();

            location.x = random.nextInt((int)t.getmScreenSize().x);
        }

        //update collider
        t.updateCollider();

        return true;
    }
}
