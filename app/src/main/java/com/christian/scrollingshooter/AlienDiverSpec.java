package com.christian.scrollingshooter;

import android.graphics.PointF;

public class AlienDiverSpec extends ObjectSpec{
    private static final String tag = "Alien";
    private static final String bitmapName = "alien_ship3";
    private static final float speed = 4f;
    private static final PointF relativeScale = new PointF(60f,30f);

    private static final String[] components = new String[]{
            "StdGraphicsComponent",
            "AlienDiverMovementComponent",
            "AlienVerticalSpawnComponent"
    };

    AlienDiverSpec(){
        super(tag, bitmapName, speed, relativeScale, components);
    }
}
