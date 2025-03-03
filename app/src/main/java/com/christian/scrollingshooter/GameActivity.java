package com.christian.scrollingshooter;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
    GameEngine mGameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mGameEngine = new GameEngine(this, size);

        setContentView(mGameEngine);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameEngine.startThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameEngine.stopThread();
    }
}