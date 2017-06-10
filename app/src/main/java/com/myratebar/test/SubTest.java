package com.myratebar.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Shao on 2017/5/26.
 */

public class SubTest extends View {
    Paint mPaint = null;
    private float[] floats = new float[]{50, 50, 50, 100};

    public SubTest(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
    }

    public void setfloat(float[] f) {
        floats = f;
    }

    public SubTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SubTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLines(floats, mPaint);
        System.out.println("drawing");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)floats[3], View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec((int)floats[3], View.MeasureSpec.EXACTLY));
    }
}
