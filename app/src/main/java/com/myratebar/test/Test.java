package com.myratebar.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Shao on 2017/5/26.
 */

public class Test extends LinearLayout {
    public Test(Context context) {
        super(context);
        this.setOrientation(VERTICAL);
    }

    public Test(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Test(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
