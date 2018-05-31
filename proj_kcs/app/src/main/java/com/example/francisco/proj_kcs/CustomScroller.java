package com.example.francisco.proj_kcs;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Francisco on 10/05/2018.
 */

public class CustomScroller extends Scroller {

    private int mDuration;

    public CustomScroller(Context context, Interpolator interpolator, int duration) {
        super(context,interpolator);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
