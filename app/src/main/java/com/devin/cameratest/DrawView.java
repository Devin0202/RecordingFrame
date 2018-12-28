package com.devin.cameratest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import static java.lang.Math.min;

public class DrawView extends View {
    private final String TAG = this.getClass().toString() + "-DY";
    private Paint mPaint;
    private Bitmap imageBitmap;
    public static double mHWratio = 1;
    private int mViewH = 0;
    private int mViewW = 0;
    public static double mStandardRatio = 0.5;
    private int mDrawH = 0;

    public DrawView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
//        imageBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (0 < mDrawH) {
            canvas.drawBitmap(drawRect(mDrawH, (int)(mDrawH / mHWratio)), 0, 0, mPaint);
        }
    }

    public Pair setRect(double fHWratio, int fViewH, int fViewW) {
        Log.d(TAG, fViewH + " " + fViewW + " " + fHWratio);
        mHWratio = fHWratio;
        mViewH = fViewH;
        mViewW = fViewW;
        mDrawH = (int)(min(fViewH, fViewW * mHWratio) * mStandardRatio);

        int left = (fViewH - mDrawH) / 2;
        int top = (fViewW - (int)(mDrawH / mHWratio)) / 2;
        return (new Pair(left, top));
    }

    public Bitmap drawRect(int height, int width){
        Log.d(TAG, height + " " + width);
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint p = new Paint();
        p.setStrokeWidth(8);
        p.setColor(Color.rgb(0, 127, 255));
        canvas.drawLine(0, 0, 0, height - 1, p);
        canvas.drawLine(0, 0, width - 1, 0, p);
        canvas.drawLine(width - 1, height - 1, 0, height - 1, p);
        canvas.drawLine(width - 1, height - 1, width - 1, 0, p);

//        canvas.drawLine(0, height / 2, width, height / 2, p);
//        canvas.drawLine(width / 2, 0, width / 2, height, p);

        return b;
    }
}
