package com.christian.scrollingshooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

public class Renderer {
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    Renderer(SurfaceView sh){
        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
    }

    void draw(ArrayList<GameObject> objects, GameState gs, HUD hud, ParticleSystem ps){
        if(mSurfaceHolder.getSurface().isValid()){
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255,0,0,0));

            if(gs.getDrawing()){
                //draw game objects here
                for(GameObject object : objects){
                    if(object.checkActive()){
                        object.draw(mCanvas, mPaint);
                    }
                }
            }
            if(gs.getGameOver()){
                //draw background graphic here
                objects.get(Level.BACKGROUND_INDEX).draw(mCanvas, mPaint);
            }
            if(ps.mIsRunning){
                ps.draw(mCanvas, mPaint);
            }

            hud.draw(mCanvas, mPaint, gs);
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
