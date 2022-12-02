package com.zzt.floating.action;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author: zeting
 * @date: 2021/10/8
 */
public class ScrollFABBehavior extends FloatingActionButton.Behavior {
    private static final String TAG = ScrollFABBehavior.class.getSimpleName();

    public ScrollFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.w(TAG, "child:" + child.getClass().getSimpleName() + " directTargetChild:" + directTargetChild.getClass().getSimpleName() + " target:" + target.getClass().getSimpleName() +
                "\n axes:" + axes +
                "\n type:" + type);
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);

        Log.d(TAG, "child:" + child.getClass().getSimpleName() + " target:" + target.getClass().getSimpleName() +
                "\n dxConsumed:" + dxConsumed + " dyConsumed:" + dyConsumed +
                "\n dxUnconsumed:" + dxUnconsumed + " dyUnconsumed:" + dyUnconsumed +
                "\n type:" + type + " consumed:" + consumed);
        if (dyConsumed >= 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }

}
