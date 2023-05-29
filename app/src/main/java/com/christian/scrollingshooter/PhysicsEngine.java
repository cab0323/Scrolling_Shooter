package com.christian.scrollingshooter;

import java.util.ArrayList;

public class PhysicsEngine {
    boolean update(long fps, ArrayList<GameObject> objects,
            GameState gs, SoundEngine se, ParticleSystem ps){

        //updating the game objects
        for(GameObject object : objects){
            if(object.checkActive()){
                object.update(fps, objects.get(
                        Level.PLAYER_INDEX).getTransform());
            }
        }

        if(ps.mIsRunning){
            ps.update(fps);
        }
        return false;
    }
}
