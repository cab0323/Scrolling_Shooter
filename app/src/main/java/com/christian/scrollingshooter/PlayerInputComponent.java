package com.christian.scrollingshooter;

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public class PlayerInputComponent implements InputComponent, InputObserver{
    private Transform mTransform;
    private PlayerLaserSpawner mPLS;

    PlayerInputComponent(GameEngine ger){
        ger.addObserver(this);
        mPLS = ger;
    }

    @Override
    public void setTransform(Transform transform){
        mTransform = transform;
    }

    //InputObserver method
    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> buttons){
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getX(i);

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                if(buttons.get(HUD.UP).contains(x,y)
                    || buttons.get(HUD.DOWN).contains(x,y)) {
                    //player has released up or down
                    mTransform.stopVertical();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(buttons.get(HUD.UP).contains(x,y)){
                    //player pressed the up button
                    mTransform.headUp();
                }
                else if(buttons.get(HUD.DOWN).contains(x,y)){
                    //player pressed the down button
                    mTransform.headDown();
                }
                else if(buttons.get(HUD.FLIP).contains(x,y)){
                    //player has released the flip button
                    mTransform.flip();
                }
                else if(buttons.get(HUD.SHOOT).contains(x,y)){
                    mPLS.spawnPlayerLaser(mTransform);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(buttons.get(HUD.UP).contains(x,y)
                || buttons.get(HUD.DOWN).contains(x,y)){
                    //player released either up or down button
                    mTransform.stopVertical();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(buttons.get(HUD.UP).contains(x,y)){
                    //player pressed up
                    mTransform.headUp();
                }
                else if(buttons.get(HUD.DOWN).contains(x,y)){
                    //player pressed down
                    mTransform.headDown();
                }
                else if(buttons.get(HUD.FLIP).contains(x,y)){
                    mTransform.flip();
                }
                else if(buttons.get(HUD.SHOOT).contains(x,y)){
                    mPLS.spawnPlayerLaser(mTransform);
                }
                break;
        }
    }
}
