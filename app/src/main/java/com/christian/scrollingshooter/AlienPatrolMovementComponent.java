package com.christian.scrollingshooter;

import android.graphics.PointF;
import java.util.Random;

public class AlienPatrolMovementComponent implements MovementComponent{
    private AlienLaserSpawner alienLaserSpawner;
    private Random mShotRandom = new Random();

    AlienPatrolMovementComponent(AlienLaserSpawner als){
        alienLaserSpawner = als;
    }

    @Override
    public boolean move(long fps,Transform t, Transform playerTransform){
        final int TAKE_SHOT = 0;

        //1 in 100 chance of shot being fired
        final int SHOT_CHANCE = 100;

        //find where player is
        PointF playerLocation = playerTransform.getLocation();

        //the top of the screen
        final float MIN_VERTICAL_BOUNDS = 0;

        //the width and height of the screen
        float screenX = t.getmScreenSize().x;
        float screenY = t.getmScreenSize().y;

        //how far ahead can the alien see?
        float mSeeingDistance = screenX * .5f;

        //location of alien
        PointF loc = t.getLocation();

        //how fast is the alien
        float speed = t.getSpeed();

        //how tall is the alien
        float height = t.getObjectHeight();

        //stop alien going too far
        float MAX_VERTICAL_BOUNDS = screenY - height;
        final float MAX_HORIZONTAL_BOUNDS = 2 * screenX;
        final float MIN_HORIZONTAL_BOUNDS = 2 * -screenX;

        //adjust the horizontal speed relative to the player's heading
        float horizontalSpeedAdjustmentRelativeToPlayer = 0;

        //how much to speed up
        float horizontalSpeedAdjustmentModifier = .8f;

        //code to check if alien can see the player
        //if the distance between alien and player is within the
        //distance alien can see then this if will execute
        if(Math.abs(loc.x - playerLocation.x) < mSeeingDistance){
            if(playerTransform.getFacingRight() != t.getFacingRight()){
                //facing different way speed up alien
                horizontalSpeedAdjustmentRelativeToPlayer = speed * horizontalSpeedAdjustmentModifier;
            }
            else {
                //facing the same way so slow down
                horizontalSpeedAdjustmentRelativeToPlayer = -(speed * horizontalSpeedAdjustmentModifier);
            }
        }

        //check limits of patrol area
        //first check horizontal limits
        if(t.headingLeft()){
            loc.x -= (speed + horizontalSpeedAdjustmentRelativeToPlayer) / fps;

            //turn the ship around when it reaches the extent of its horizontal
            //patrol area
            if(loc.x < MIN_HORIZONTAL_BOUNDS){
                loc.x = MIN_HORIZONTAL_BOUNDS;
                t.headRight();
            }
        }
        else {
            loc.x += (speed + horizontalSpeedAdjustmentRelativeToPlayer) / fps;

            //turn ship around when it reaches limit of its patrol area
            if(loc.x > MAX_HORIZONTAL_BOUNDS){
                loc.x = MAX_HORIZONTAL_BOUNDS;
                t.headLeft();
            }
        }

        //now check vertical limits
        if(t.headingDown()){
            loc.y += (speed) / fps;

            //turn around if reached the upper limit of patrol area
            /* top of screen is zero so bottom of screen would be
               the higher number, so bottom of screen would be the
                upper limit of patrol area */
            if(loc.y > MAX_VERTICAL_BOUNDS){
                t.headUp();
            }
        }
        //this is when headingUp()
        else {
            loc.y -= (speed) / fps;

            //turn down because you reached the top of screen limit
            if(loc.y < MIN_VERTICAL_BOUNDS){
                t.headDown();
            }
        }

        //update the collider
        t.updateCollider();

        //code to decide to shoot ship
        if(mShotRandom.nextInt(SHOT_CHANCE) == TAKE_SHOT){
            if(Math.abs(playerLocation.y - loc.y) < height){
                //check if alien is facing towards player
                //and weather it is close enough to shoot
                if((t.getFacingRight() && playerLocation.x > loc.x
                || !t.getFacingRight() && playerLocation.x < loc.x)
                && Math.abs(playerLocation.x - loc.x) < screenX){
                    //if facing towards player and within range, Fire!
                    alienLaserSpawner.spawnAlienLaser(t);
                }
            }
        }

        return true;
    } //end of move method


}
