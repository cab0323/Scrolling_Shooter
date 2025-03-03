package com.christian.scrollingshooter;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Random;

public class ParticleSystem {
    //how long should effect last
    float mDuration;
    ArrayList<Particle> mParticles;
    Random random = new Random();

    //track weather effect is being currently shown
    boolean mIsRunning = false;

    void init(int numParticles){
        mParticles = new ArrayList<>();

        //creating the particles
        for(int i = 0; i < numParticles; i++){
            float angle = (random.nextInt(360));
            angle = angle * 3.14f / 180.f;
            float speed = (random.nextInt(20) + 1);
            PointF direction;
            direction = new PointF((float)Math.cos(angle) * speed,
                    (float)Math.sin(angle) * speed);
            mParticles.add(new Particle(direction));
        }
    }

    void update(long fps){
        mDuration -= (1f/fps);
        for(Particle p : mParticles){
            p.update();
        }
        if(mDuration < 0){
            mIsRunning = false;
        }
    }

    //this method will show the particles effect onscreen
    //it will be called each time the effect must be shown
    void emitParticles(PointF startPosition){
        mIsRunning = true;
        mDuration = 1f;

        for(Particle p : mParticles){
            p.setPosition(startPosition);
        }
    }

    void draw(Canvas canvas, Paint paint){
        for(Particle p : mParticles){
            paint.setARGB(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            canvas.drawRect(p.getPosition().x, p.getPosition().y, p.getPosition().x+25, p.getPosition().y+25, paint);
        }
    }
}
