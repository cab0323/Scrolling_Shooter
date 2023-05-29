package com.christian.scrollingshooter;

public class BackgroundSpawnComponent implements SpawnComponent{
    @Override
    public void spawn(Transform playerLTransform, Transform t){
        t.setLocation(0f, 0f);
    }
}
