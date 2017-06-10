package com.myratebar.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Shao on 2017/5/27.
 */

public class MyBar extends FrameLayout {
    public MyBar(@NonNull Context context) {
        super(context);
    }

    public MyBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
