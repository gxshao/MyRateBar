package com.myratebar.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;

import com.myratebar.R;

/**
 * Created by Shao on 2017/5/26.
 */

public class BaseBar extends View {
    int mLen = 0;
    int[] mScores = null;
    float[] mScoresSum = null;
    float mBlockScore = 0;
    float mBlockLen = 0;
    int mSelectIndex = 0;
    int BLOCK_NUM = 0;//每个标记代表0.5
    final int MARK_WIDTH = 2, BIGMARK_HEIGHT = 40, SMALLMARK_HEIGHT = 20;
    Context mContext;
    int mSumBlockNum = 0;
    int mMarkSum = 0;
    Paint mPaint = null;
    float SMALL_BASELINE = 0, MIDDLE_BASELINE = 0, BIG_BASELINE = 0;
    float[] arr = new float[]{100, 0, 100, SMALLMARK_HEIGHT};
    private Drawable mDrawable, mDrawBG;
    private int BAR_HIGHT = 100;
    private float mStartPixel = 0;
    private float mEndPixel = 0;
    private float mProgress = 0;
    private int mSelectedVal=0;


    public void setBaseBarListener(BaseBarListener baseBarListener) {
        this.baseBarListener = baseBarListener;
    }

    private BaseBar.BaseBarListener baseBarListener = null;

    public void setHeight(int Height) {
        this.mHeight = Height;
    }

    int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public BaseBar(Context context) {
        super(context);
    }


    public BaseBar(Context context, int[] scores) {
        super(context);
        mContext = context;
        mScores = scores;
        if (mScores.length >= 2) {
            float temp = ((float) Math.abs(mScores[1] - mScores[0]));
            float s = temp / 5.0f;
            if (s >= 1.0f) {
                BLOCK_NUM = (int) (temp / 5);
            } else if (s >= 0.1) {
                s *= 10;
                BLOCK_NUM = (int) s;
            }
            if (BLOCK_NUM == 0)
                return;
            mBlockScore = ((float) Math.abs(mScores[1] - mScores[0]) / BLOCK_NUM);
            BigDecimal bd = new BigDecimal((double) mBlockScore);
            bd = bd.setScale(2, 4);  //保留2位小数的四舍五入
            mBlockScore = bd.floatValue();
        }
        mSumBlockNum = (mScores.length - 1) * (BLOCK_NUM);
        mMarkSum = mSumBlockNum + 1;
        mScoresSum = new float[mMarkSum]; //每个节点的分数
        //分析递增数据或者非递增数据 算出所有点的分数
        int index = findMax(mScores);
        if (index == mScores.length) {
            System.out.println("递增情况");
            //递增情况
            mScoresSum[0] = mScores[0];
            for (int i = 1; i < mMarkSum; i++) {
                mScoresSum[i] = mScoresSum[i - 1] + mBlockScore;
            }
            SMALL_BASELINE=mScores[mScores.length/3];
            MIDDLE_BASELINE=mScores[(mScores.length/3)*2];
        } else {
            //非递增情况
            System.out.println("非递增情况");
            mScoresSum[0] = mScores[0];
            boolean flag = false;
            for (int i = 1; i < mMarkSum; i++) {
                if (!flag)
                    mScoresSum[i] = mScoresSum[i - 1] + mBlockScore;
                else
                    mScoresSum[i] = mScoresSum[i - 1] - mBlockScore;
                if (mScoresSum[i] == mScores[index - 1]) {
                    MIDDLE_BASELINE=mScoresSum[i-1];
                    flag = true;
                }
            }
            SMALL_BASELINE=mScores[mScores.length/4];
            System.out.println("base "+MIDDLE_BASELINE);

        }
        this.setOnTouchable(true);


    }

    private int findMax(int a[]) {
        int i, k = 0;
        int j;
        j = a[0];
        for (i = 0; i < a.length; i++) {
            if (a[i] > j) {
                j = a[i];
                k = i;
            }
        }
        return k + 1;
    }

    public BaseBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float baseX = mStartPixel;
        int start = 0;
        for (int i = 0; i < mMarkSum; i++) {
            arr[0] = baseX;
            arr[1] = start + BIGMARK_HEIGHT - SMALLMARK_HEIGHT;
            arr[2] = baseX;
            arr[3] = BIGMARK_HEIGHT;
            for (int mScore : mScores) {
                if (mScoresSum[i] == mScore) {
                    arr[1] = start;
                    break;
                }
            }
            canvas.drawLines(arr, mPaint);
            baseX += (mBlockLen + 1);
        }
        mDrawBG.setBounds((int) mStartPixel, BIGMARK_HEIGHT, (int) (mLen + mStartPixel), BAR_HIGHT);
        mDrawBG.draw(canvas);
        mDrawable.setBounds((int) mStartPixel, BIGMARK_HEIGHT, (int) (mProgress + mStartPixel), BAR_HIGHT);
        mDrawable.draw(canvas);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View v = (View) getParent();
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(v.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(BAR_HIGHT, View.MeasureSpec.EXACTLY));
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(MARK_WIDTH);
        mLen = this.getMeasuredWidth();
        mLen = (int) (mLen - mStartPixel - mEndPixel);
        mBlockLen = ((float) (mLen - mMarkSum)) / mSumBlockNum;
        mDrawable = getResources().getDrawable(R.drawable.myratebar_top_red);
        mDrawBG = getResources().getDrawable(R.drawable.myratebar_bg_layer);
        mProgress=mSelectedVal*(mBlockLen+MARK_WIDTH)*BLOCK_NUM;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setHrizontalPadding(float mStartPixel, float mEndPixel) {
        this.mStartPixel = mStartPixel;
        this.mEndPixel = mEndPixel;
        System.out.println("测量左边距和右边距为" + mStartPixel + "  " + mEndPixel);
    }

    public void setOnTouchable(boolean onTouchable) {
        if (onTouchable)
            this.setOnTouchListener(onTouchListener);
        else
            this.setOnTouchListener(null);
    }

    private boolean onTouchable = true;
    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //计算当前点击位置
            int avg = ((v.getWidth()) / (mLen));
            int[] locations = new int[2];
            v.getLocationOnScreen(locations);
            float parentBounds = locations[0];
            float x, width, c;
            int how;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getRawX();
                    width = x - parentBounds - mStartPixel;
                    how = (int) width / avg;
                    if (width % avg > avg / 2)
                        how++;
                    if (how + mStartPixel >= mLen)
                        how = mLen;
                    mProgress = how;
                    mSelectIndex = (int) (mProgress / mBlockLen);
                    if (mSelectIndex < 0)
                        mSelectIndex = 0;
                    c = mScoresSum[mSelectIndex];
                    System.out.println(c);
                    if (c < SMALL_BASELINE)
                        mDrawable = (getResources().getDrawable(R.drawable.myratebar_top_red));
                    else if (c < MIDDLE_BASELINE)
                        mDrawable = (getResources().getDrawable(R.drawable.myratebar_top_yellow));
                    else if (c >= MIDDLE_BASELINE)
                        mDrawable = (getResources().getDrawable(R.drawable.myratebar_top_green));
                    BaseBar.this.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    x = event.getRawX();
                    width = x - parentBounds - mStartPixel;
                    how = (int) width / avg;
                    if (width % avg > avg / 2)
                        how++;
                    if (how > mLen)
                        how = mLen;
                    mProgress = how;
                    mSelectIndex = (int) (mProgress / (mBlockLen + MARK_WIDTH));
                    if (mSelectIndex < 0)
                        mSelectIndex = 0;
                    c = mScoresSum[mSelectIndex];
                    if (c < SMALL_BASELINE)
                        mDrawable = (getResources().getDrawable(R.drawable.myratebar_top_red));
                    else if (c < MIDDLE_BASELINE)
                        mDrawable = (getResources().getDrawable(R.drawable.myratebar_top_yellow));
                    else if (c >= MIDDLE_BASELINE)
                        mDrawable = (getResources().getDrawable(R.drawable.myratebar_top_green));

                    BaseBar.this.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    adjustProgress();
                    //传入大刻度的值
                    baseBarListener.OnSelectChanged(v, getBaseIndex(mSelectIndex), mScoresSum[mSelectIndex]);
                    break;

            }
            return true;
        }
    };

    private int getBaseIndex(int Index){
        return Index/BLOCK_NUM;
    }
    private int getMiddleIndex(int base){
        return base*BLOCK_NUM;
    }
    private void adjustProgress() {
        float rest = (mProgress + mStartPixel) % (mBlockLen + MARK_WIDTH);

        if (rest == 0 || mProgress == 0)
            return;
        if (mProgress == mLen) {
            mSelectIndex = mMarkSum - 1;
            return;
        }
        if (rest >= (mBlockLen) / 2) {
            rest = mBlockLen - rest + MARK_WIDTH;
            mProgress += rest;
            mProgress -= mSelectIndex - mStartPixel;
            BaseBar.this.invalidate();
        } else {
            mProgress -= rest;
            mProgress -= mSelectIndex - mStartPixel;
            BaseBar.this.invalidate();
        }
        mSelectIndex = (int) ((mProgress) / mBlockLen);
    }

    public void setSelectedVal(int mSelectedVal) {
        if(mSelectedVal<0||mSelectedVal>=mScores.length){
            return;
        }
        this.mSelectedVal = mSelectedVal;
    }

    public interface BaseBarListener {
        void OnSelectChanged(View v, int index, float scores);
    }
}
