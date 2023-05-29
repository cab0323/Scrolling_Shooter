package com.christian.scrollingshooter;

import android.content.Context;
import android.graphics.PointF;

public class GameObjectFactory {
    private Context mContext;
    private PointF mScreenSize;
    private GameEngine mGameEngineReference;

    GameObjectFactory(Context c, PointF screenSize, GameEngine gameEngine){
        this.mContext = c;
        this.mScreenSize = screenSize;
        mGameEngineReference = gameEngine;
    }

    GameObject create(ObjectSpec spec){
        GameObject object = new GameObject();
        int numComponents = spec.getComponents().length;
        final float HIDDEN = -2000f;

        object.setmTag(spec.getTag());

        float speed = mScreenSize.x / spec.getSpeed();

        PointF objectSize = new PointF(mScreenSize.x / spec.getScale().x,
                mScreenSize.y / spec.getScale().y);

        PointF location = new PointF(HIDDEN, HIDDEN);
        object.setTransform(new Transform(speed, objectSize.x, objectSize.y,
                location, mScreenSize));

        //loop through initialize components
        for(int i = 0; i < numComponents; i++){
            switch (spec.getComponents()[i]){
                case "PlayerInputComponent":
                    object.setInput(new PlayerInputComponent(mGameEngineReference));
                    break;
                case "StdGraphicsComponent":
                    object.setGraphics(new StdGraphicsComponent(),
                            mContext, spec, objectSize);
                    break;
                case "PlayerMovementComponent":
                    object.setMovement(new PlayerMovementComponent());
                    break;
                case "LaserSpawnComponent":
                    object.setSpawner(new LaserSpawnComponent());
                    break;
                case "BackgroundGraphicsComponent":
                    object.setGraphics(new BackgroundGraphicsComponent(),
                            mContext, spec, objectSize);
                    break;
                case "BackgroundMovementComponent":
                    object.setMovement(new BackgroundMovementComponent());
                    break;
                case "BackgroundSpawnComponent":
                    object.setSpawner(new BackgroundSpawnComponent());
                    break;
                case "AlienChaseMovementComponent":
                    object.setMovement(new AlienChaseMovementComponent(mGameEngineReference));
                    break;
                case "AlienPatrolMovementComponent":
                    object.setMovement(new AlienPatrolMovementComponent(
                            mGameEngineReference));
                    break;
                case "AlienDiverMovementComponent":
                    object.setMovement(new AlienDiverMovementComponent());
                    break;
                case "AlienHorizontalSpawnComponent":
                    object.setSpawner(new AlienHorizontalSpawnComponent());
                    break;
                case "AlienVerticalSpawnComponent":
                    object.setSpawner(new AlienVerticalSpawnComponent());
                    break;
                default:
                    //error unidentified component
                    break;
            }
        }

        return object;
    }
}
