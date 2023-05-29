package com.christian.scrollingshooter;

import java.util.Random;

public class AlienVerticalSpawnComponent implements SpawnComponent{
    public void spawn(Transform playerTransform, Transform t){
        //spawn off screen randomly but within screen width
        Random random = new Random();
        float xPosition = random.nextInt((int)t.getmScreenSize().x);

        //set the height to vertically just above visible game
        float spawnHeight = random.nextInt(300) - t.getObjectHeight();

        //spawn the ship
        t.setLocation(xPosition, spawnHeight);

        //always going down
        t.headDown();
    }
}
