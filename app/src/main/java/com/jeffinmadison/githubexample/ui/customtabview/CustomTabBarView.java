package com.jeffinmadison.githubexample.ui.customtabview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.jeffinmadison.githubexample.R;

/**
 * Created by Jeff on 5/22/2014.
 * Copyright JeffInMadison 2014
 */
public class CustomTabBarView extends LinearLayout {
    private static final int DEFAULT_STRIP_HEIGHT = 6;

    public final Paint mPaint;
    private int mStripColor;
    private float mStripHeight;
    private float mOffset;
    private int mSelectedTab = -1;

//    public CustomTabBarView(Context context) {
//        this(context, null);
//    }

    public CustomTabBarView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.actionBarTabBarStyle);
    }

    public CustomTabBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTabBarView, 0,defStyle);
        try {
            mStripColor = typedArray.getColor(R.styleable.CustomTabBarView_stripColor, R.color.White);
            float defaultStripHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_STRIP_HEIGHT, getResources().getDisplayMetrics());
            mStripHeight = typedArray.getDimension(R.styleable.CustomTabBarView_stripHeight, defaultStripHeight);
        } finally {
            typedArray.recycle();
        }
        mPaint = new Paint();
        mPaint.setColor(mStripColor);
    }

    public void setStripColor(int color) {
        if (mPaint.getColor() != color) {
            mPaint.setColor(color);
            invalidate();
        }
    }

    public void setStripHeight(int height) {
        if (mStripHeight != height) {
            mStripHeight = height;
            invalidate();
        }
    }

    public void setSelectedTab(int tabIndex) {
        if (tabIndex < 0) {
            tabIndex = 0;
        }
        final int childCount = getChildCount();
        if (tabIndex >= childCount) {
            tabIndex = childCount - 1;
        }
        if (mSelectedTab != tabIndex) {
            mSelectedTab = tabIndex;
            invalidate();
        }
    }

    public void setOffset(float offset) {
        if (mOffset != offset) {
            mOffset = offset;
            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // Draw the strip manually
        final View child = getChildAt(mSelectedTab);
        if (child != null) {
            int left = child.getLeft();
            int right = child.getRight();
            if (mOffset > 0) {
                final View nextChild = getChildAt(mSelectedTab + 1);
                if (nextChild != null) {
                    left = (int) (child.getLeft() + mOffset * (nextChild.getLeft() - child.getLeft()));
                    right = (int) (child.getRight() + mOffset * (nextChild.getRight() - child.getRight()));
                }
            }
            canvas.drawRect(left, getHeight() - mStripHeight, right, getHeight(), mPaint);
        }
    }
}
