package com.christian.scrollingshooter;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Random;

public class AlienChaseMovementComponent implements MovementComponent {
    private Random mShotRandom = new Random();

    //give this class the ability to tell game engine to spawn laser
    private AlienLaserSpawner alienLaserSpawner;

    AlienChaseMovementComponent(AlienLaserSpawner als) {
        alienLaserSpawner = als;
    }

    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {
        //arbitrary number that value must equal to shoot
        final int TAKE_SHOT = 0;

        //the chance alien will fire a laser 1% chance
        final int SHOT_CHANCE = 100;

        float screenWidth = t.getmScreenSize().x;

        PointF playerLocation = playerTransform.getLocation();

        //height of ship
        float height = t.getObjectHeight();

        boolean facingRight = t.getFacingRight();

        //at what distance alien will hone in on and
        //at what distance it will decide to shoot
        //hone distance
        float mChasingDistance = t.getmScreenSize().x / 3f;
        //shooting distance
        float mSeeingDistance = t.getmScreenSize().x / 1.5f;

        //where is ship?
        PointF location = t.getLocation();

        float speed = t.getSpeed();

        //variables to regulate movement speed of alien relative to player
        float verticalSpeedDifference = .3f;
        float slowDownRelativeToPlayer = 1.8f;
        //prevent ship from locking on too accurately
        float verticalSearchBounce = 20f;

        //more code next

        //check if alien distance from player is within the
        //distance at which it should chase player
        if (Math.abs(location.x - playerLocation.x) > mChasingDistance) {
            //if it is then check weather the alien should head right
            if (location.x < playerLocation.x) {
                t.headRight();
            }
            //or if alien should head left
            else if (location.x > playerLocation.x) {
                t.headLeft();
            }
        }

        //if alien can see player
        //it will check that by seeing if distance alien is to player
        //is within seeingDistance limits
        if (Math.abs(location.x - playerLocation.x) <= mSeeingDistance) {
            if ((int) location.y - playerLocation.y < -verticalSearchBounce) {
                t.headDown();
            } else if ((int) location.y - playerLocation.y > verticalSearchBounce) {
                t.headUp();
            }

            //adjust the speed according to the direction the alien is going
            if (!playerTransform.getFacingRight()) {
                location.x += speed * slowDownRelativeToPlayer / fps;
            } else {
                location.x -= speed * slowDownRelativeToPlayer / fps;
            }
        } else {
            //stop vertical movement so alien won't disappear off top or bottom of screen
            t.stopVertical();
        }

        //moving vertically is harder than moving horizontally
        if(t.headingDown()){
            location.y += speed * verticalSpeedDifference / fps;
        }
        else if(t.headingUp()){
            location.y -= verticalSpeedDifference / fps;
        }

        //moving horizontally
        if(t.headingLeft()){
            location.x -= (speed) / fps;
        }
        if(t.headingRight()){
            location.x += (speed) / fps;
        }

        t.updateCollider();

        //shoot if the aline is within a ships height above, below,
        // or in line with the player, could be hit or miss
        if(mShotRandom.nextInt(SHOT_CHANCE) == TAKE_SHOT){
            if(Math.abs(playerLocation.y - location.y) < height){
                //check if alien is facing right direction and close enough to the player
                if((facingRight && playerLocation.x > location.x
                || !facingRight && playerLocation.x < location.x)
                && Math.abs(playerLocation.x - location.x)
                < screenWidth){
                    //fire!
                    alienLaserSpawner.spawnAlienLaser(t);
                }
            }
        }
        return true;
    }

}
