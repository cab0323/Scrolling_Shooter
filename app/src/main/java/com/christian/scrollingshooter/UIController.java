package com.christian.scrollingshooter;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public class UIController implements InputObserver{
    public UIController(GameEngineBroadcaster b){
        b.addObserver(this);
    }

    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> buttons){
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        if(eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP){
            if(buttons.get(HUD.PAUSE).contains(x, y)){
                //player pressed the pause button
                //therefore different actions will take place depending on current state of game
                //game is not paused so pause it
                if(!gameState.getPaused()){
                    gameState.pause();
                }
                //game is over therefore start a new game
                else if(gameState.getGameOver()){
                    gameState.startNewGame();
                }
                //paused and not game over
                else if(gameState.getPaused() && !gameState.getGameOver()){
                    gameState.resume();
                }
            }
        }
    }
}
