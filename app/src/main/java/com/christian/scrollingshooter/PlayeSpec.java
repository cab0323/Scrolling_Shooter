package com.christian.scrollingshooter;

import android.graphics.PointF;

public class PlayeSpec extends ObjectSpec{
    //variables specific to the player spec class
    private static final String tag = "Player";
    private static final String bitmapName = "player_ship";
    private static final float speed = 1f;
    private static final PointF relativeScale = new PointF(15f,15f);
    private static final String[] components = new String[] {
            "PlayerInputComponent",
            "StdGraphicsComponent",
            "PlayerMovementComponent",
            "PlayerSpawnComponent"
    };

    PlayeSpec(){
        super(tag, bitmapName, speed, relativeScale, components);
    }

}
