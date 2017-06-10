package com.myratebar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Shao on 2017/5/26.
 */

public class MyRateBar extends LinearLayout implements BaseBar.BaseBarListener {

    Context mContext;
    String[] mSrc = null;
    int[] mScores = null;
    float mStartPixel = 0, mEndPixel = 0;
    private BaseBar.BaseBarListener basse;
    private int mSelectVal=0;
    public MyRateBar(Context context) {
        super(context);
        mContext = context;
    }
    public MyRateBar(Context context, String[] src, int[] scores) {
        super(context);
        mContext = context;
        mSrc = src;
        mScores = scores;
        init();
    }

    public MyRateBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRateBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);//设置布局参数
        this.setOrientation(LinearLayout.VERTICAL);
        this.setWillNotDraw(false);
        if (mSrc == null || mSrc.length <= 0) {
            throw new NullPointerException("指标数据为空！");
        }
        if (mScores == null || mScores.length <= 0) {
            throw new NullPointerException("分数数组为空！");
        }
        if (mSrc.length != mScores.length) {
            throw new ExceptionInInitializerError("初始化失败，指标长度与分数数组长度不匹配！");
        }
        initHead();
    }


    private void initHead() {
        BaseBar bar = new BaseBar(mContext, mScores);
       /* BaseHead baseHead = new BaseHead(mContext, mSrc);
        baseHead.setTopOffset(100);

        mStartPixel = baseHead.getmFirstTextWidth();
        mEndPixel = baseHead.getmLastTextWidth();

        this.addView(baseHead, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));*/
        bar.setHrizontalPadding(mStartPixel, mEndPixel);
        bar.setBaseBarListener(this);
        BaseBar barS = new BaseBar(mContext, mScores);
        barS.setHeight(200);
        barS.setSelectedVal(1);
        barS.setHrizontalPadding(mStartPixel, mEndPixel);
        barS.setBaseBarListener(this);
        this.addView(bar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(barS, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = this.getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            height = child.getMeasuredHeight();
        }
        View v = (View) getParent();
        setMeasuredDimension(v.getMeasuredWidth(), height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void addBar() {
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setls(BaseBar.BaseBarListener ls) {
        basse = ls;
    }

    @Override
    public void OnSelectChanged(View v, int index, float num) {
        basse.OnSelectChanged(v, index, num);
    }
}
