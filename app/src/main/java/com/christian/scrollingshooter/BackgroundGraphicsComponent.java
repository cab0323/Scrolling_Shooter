package com.christian.scrollingshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class BackgroundGraphicsComponent implements GraphicsComponent{
    private Bitmap mBitmap;
    private Bitmap mBitmapReversed;

    @Override
    public void initialize(Context c, ObjectSpec s, PointF objectSize){
        int resID = c.getResources().
                getIdentifier(s.getBitmapName(),"drawable", c.getPackageName());

        //load bitmap using id
        mBitmap = BitmapFactory.decodeResource(
                c.getResources(), resID);

        mBitmap = Bitmap.createScaledBitmap(
                mBitmap, (int)objectSize.x,
                (int)objectSize.y, false);

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        mBitmapReversed = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
        mBitmap.getHeight(), matrix, true);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Transform t){
        int xClip = t.getXClip();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        int startY = 0;
        int endY = (int)t.getmScreenSize().y + 20;

        //regular background
        Rect fromRect1 = new Rect(0, 0, width - xClip, height);
        Rect toRect1 = new Rect(xClip, startY, width, endY);

        //reversed background
        Rect fromRect2 = new Rect(width - xClip, 0, width, height);
        Rect toRect2 = new Rect(0, startY, xClip, endY);
        //draw background bitmaps
        if(!t.getReverseFirst()){
            canvas.drawBitmap(mBitmap, fromRect1, toRect1, paint);
            canvas.drawBitmap(mBitmapReversed, fromRect2, toRect2, paint);
        }
        else {
            canvas.drawBitmap(mBitmap, fromRect2, toRect2, paint);
            canvas.drawBitmap(mBitmapReversed, fromRect1, toRect1, paint);
        }
    }
}
