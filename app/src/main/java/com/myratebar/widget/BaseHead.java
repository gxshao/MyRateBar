package com.myratebar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Shao on 2017/5/26.
 */

public class BaseHead extends View {
    float mLen = 0, mBlockLen = 0;
    Context mContext;
    String[] mSrc = null;
    Paint mPaint = null;
    int txtSumLens;

    public float getmFirstTextWidth() {
        return mFirstTextWidth;
    }

    public float getmLastTextWidth() {
        return mLastTextWidth;
    }

    float mFirstTextWidth = 0, mLastTextWidth = 0;

    public void setTopOffset(int TopOffset) {
        this.mTopOffset = TopOffset;
    }

    int mTopOffset = 0;

    public void setTextColor(int TextColor) {
        this.mTextColor = TextColor;
    }

    int mTextColor = Color.BLACK;

    public void setTextSize(float TextSize) {
        this.mTextSize = TextSize;
    }

    float mTextSize = 90;

    public BaseHead(Context context) {
        super(context);
    }

    public BaseHead(Context context, String[] src) {
        super(context);
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);

        txtSumLens = 0;
        mSrc = src;
        for (String temp : mSrc) {
            float txtLen = mPaint.measureText(temp);
            if (temp.equals(mSrc[0]))
                mFirstTextWidth = mPaint.measureText(temp) / 2;
            if (temp.equals(mSrc[mSrc.length - 1]))
                mLastTextWidth = mPaint.measureText(temp) / 2;
            txtSumLens += txtLen;

        }
    }

    public BaseHead(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseHead(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int) mPaint.measureText(mSrc[0])*2);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mLen = this.getMeasuredWidth();
        mBlockLen = (mLen - txtSumLens) / (mSrc.length - 1);
        int baseX = 0;
        int baseY = mTopOffset;
        for (String temp : mSrc) {
            canvas.drawText(temp, baseX, baseY, mPaint);
            baseX += mBlockLen + mPaint.measureText(temp);
        }
        super.onDraw(canvas);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
