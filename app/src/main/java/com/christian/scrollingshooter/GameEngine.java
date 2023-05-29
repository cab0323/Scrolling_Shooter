package com.christian.scrollingshooter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameEngine extends SurfaceView implements Runnable, GameStarter,
        GameEngineBroadcaster, PlayerLaserSpawner, AlienLaserSpawner{
    private Thread mThread = null;
    private long mFPS;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    private GameState mGameState;
    private SoundEngine mSoundEngine;
    UIController mUIController;
    HUD mHUD;
    Renderer mRenderer;
    ParticleSystem mParticleSystem;
    PhysicsEngine mPhysicsEngine;
    Level mLevel;

    public GameEngine(Context context, Point size){
        super(context);
        mUIController = new UIController(this);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
        mParticleSystem = new ParticleSystem();
        mPhysicsEngine = new PhysicsEngine();
        mLevel = new Level(context, new PointF(size.x, size.y), this);
        //particles
        mParticleSystem.init(100);
    }

    @Override
    public void run(){
        while(mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            ArrayList<GameObject> objects = mLevel.getGameObjects();
            mLevel.getGameObjects();

            if(!mGameState.getPaused()){
                //update all game objects here
                if(mPhysicsEngine.update(mFPS, objects, mGameState,
                        mSoundEngine, mParticleSystem)){
                    deSpawnReSpawn();
                }
            }
            mRenderer.draw(objects, mGameState, mHUD, mParticleSystem);
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        //handle players input


        for(InputObserver o : inputObservers){
            o.handleInput(motionEvent, mGameState, mHUD.getControls());
        }
        
        return true;
    }

    public void stopThread(){
        mGameState.stopEverything();
        try {
            mThread.join();
        } catch(InterruptedException e){
            Log.e("Exception", "stopThread()" + e.getMessage());
        }
    }

    public void startThread(){
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();
    }

    //implementing the interface methods
    //GameStarter
    public void deSpawnReSpawn(){
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        for(GameObject o : objects){
            o.setInactive();
        }

        objects.get(Level.PLAYER_INDEX).spawn(objects.get(Level.PLAYER_INDEX)
        .getTransform());

        for(int i = Level.FIRST_ALIEN; i != Level.LAST_ALIEN + 1; i++){
            objects.get(i).spawn(objects.get(Level.PLAYER_INDEX).getTransform());
        }

    }

    //GameEngineBroadcaster
    public void addObserver(InputObserver o){
        inputObservers.add(o);
    }

    //PlayerLaserSpawner
    @Override
    public boolean spawnPlayerLaser(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();
        mLevel.getGameObjects();

        if (objects.get(Level.mNextPlayerLaser).spawn(transform)) {
            Level.mNextPlayerLaser++;
            mSoundEngine.playShoot();

            if (Level.mNextPlayerLaser == Level.LAST_PLAYER_LASER + 1) {
                Level.mNextPlayerLaser = Level.FIRST_PLAYER_LASER;
            }
        }
        return true;
    }

        //AlienLaserSpawner
        public void spawnAlienLaser(Transform transform){
            ArrayList<GameObject> objects = mLevel.getGameObjects();

            mLevel.getGameObjects();

            //shoot the laser if available
            if(objects.get(Level.mNextAlienLaser).spawn(transform)){
                Level.mNextAlienLaser++;
                mSoundEngine.playShoot();

                if(Level.mNextAlienLaser == Level.LAST_ALIEN_LASER + 1){
                    //just used the last laser
                    Level.mNextAlienLaser = Level.FIRST_ALIEN_LASER;
                }
            }
        }


}

