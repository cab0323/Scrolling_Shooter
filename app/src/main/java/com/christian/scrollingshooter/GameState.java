package com.christian.scrollingshooter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.os.IResultReceiver;

final class GameState {
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    //object that will access the interface methods in GameEngine
    private GameStarter gameStarter;
    private int mScore;
    private int mHighScore;
    private int mNumShips;

    //this is how high score is kept
    private SharedPreferences.Editor mEditor;

    //constructor, only gives acess to the interface methods of GameEngine
    GameState(GameStarter gs, Context context){
        //initialize gamestarter
        gameStarter = gs;
        //get current highscore
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore", Context.MODE_PRIVATE);

        mEditor = prefs.edit();

        //if cant find high score then set to 0
        mHighScore = prefs.getInt("hi_score", 0);
    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;

        //check if your current score is greater than your highscore
        if(mScore > mHighScore){
            //if so, make current score your new highscore
            mHighScore = mScore;
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    void startNewGame(){
        mScore = 0;
        mNumShips = 3;
        stopDrawing();
        gameStarter.deSpawnReSpawn();
        resume();
        startDrawing();
    }

    void loseLife(SoundEngine se){
        mNumShips--;
        se.playPlayerExplode();
        if(mNumShips == 0){
            pause();
            endGame();
        }
    }

    int getNumShips(){
        return mNumShips;
    }

    void increaseScore(){
        mScore++;
    }

    int getScore(){
        return mScore;
    }

    int getHighScore(){
        return mHighScore;
    }

    void pause(){
        mPaused = true;
    }

    void resume(){
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning(){
        return mThreadRunning;
    }

    void startThread(){
        mThreadRunning = true;
    }

    private void stopDrawing(){
        mDrawing = false;
    }

    private void startDrawing(){
        mDrawing = true;
    }

    boolean getDrawing() {
        return mDrawing;
    }

    boolean getPaused(){
        return mPaused;
    }

    boolean getGameOver() {
        return mGameOver;
    }

}
