package com.christian.scrollingshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public interface GraphicsComponent {
    void initialize(Context c, ObjectSpec s, PointF screensize);

    void draw(Canvas canvas, Paint paint, Transform t);
}
