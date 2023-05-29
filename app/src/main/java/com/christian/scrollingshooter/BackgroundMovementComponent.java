package com.christian.scrollingshooter;

public class BackgroundMovementComponent implements MovementComponent{
    @Override
    public boolean move(long fps, Transform t, Transform playerTransform){
        int currentXClip = t.getXClip();

        if(playerTransform.getFacingRight()){
            currentXClip -= t.getSpeed() / fps;
            t.setXClip(currentXClip);
        }
        else {
            currentXClip += t.getSpeed() / fps;
            t.setXClip(currentXClip);
        }
        if(currentXClip >= t.getSize().x){
            t.setXClip(0);
            t.flipReverseFirst();
        }
        else if(currentXClip <= 0){
            t.setXClip((int) t.getSize().x);
            t.flipReverseFirst();
        }

        return true;
    }
}
