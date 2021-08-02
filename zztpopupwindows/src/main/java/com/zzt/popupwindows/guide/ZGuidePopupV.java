
package com.zzt.popupwindows.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zzt.popupwindows.R;
import com.zzt.popupwindows.library.ZBasePopup;
import com.zzt.popupwindows.library.ZPopFrameLayout;
import com.zzt.popupwindows.util.ScreenUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * @author: zeting
 * @date: 2021/6/21
 * PopupWindow 对View做蒙版指引
 */
public class ZGuidePopupV extends ZBasePopup {
    private static final String TAG = ZGuidePopupV.class.getSimpleName();
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;

    @IntDef({DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    private int mEdgeProtectionTop;
    private int mEdgeProtectionLeft;
    private int mEdgeProtectionRight;
    private int mEdgeProtectionBottom;
    private int mOffsetX = 0;
    private int mOffsetYIfTop = 0;
    private int mOffsetYIfBottom = 0;
    private @Direction
    int mPreferredDirection = DIRECTION_BOTTOM;
    protected final int mInitWidth;
    protected final int mInitHeight;
    private View mContentView;

    public ZGuidePopupV(Context context, int width, int height) {
        super(context);
        mInitWidth = width;
        mInitHeight = height;
    }

    public ZGuidePopupV edgeProtection(int distance) {
        mEdgeProtectionLeft = distance;
        mEdgeProtectionRight = distance;
        mEdgeProtectionTop = distance;
        mEdgeProtectionBottom = distance;
        return this;
    }

    public ZGuidePopupV edgeProtection(int left, int top, int right, int bottom) {
        mEdgeProtectionLeft = left;
        mEdgeProtectionTop = top;
        mEdgeProtectionRight = right;
        mEdgeProtectionBottom = bottom;
        return this;
    }

    public ZGuidePopupV offsetX(int offsetX) {
        mOffsetX = offsetX;
        return this;
    }

    public ZGuidePopupV offsetYIfTop(int y) {
        mOffsetYIfTop = y;
        return this;
    }

    public ZGuidePopupV offsetYIfBottom(int y) {
        mOffsetYIfBottom = y;
        return this;
    }

    public ZGuidePopupV preferredDirection(@Direction int preferredDirection) {
        mPreferredDirection = preferredDirection;
        return this;
    }

    public ZGuidePopupV view(View contentView) {
        mContentView = contentView;
        return this;
    }

    public ZGuidePopupV view(@LayoutRes int contentViewResId) {
        return view(LayoutInflater.from(mContext).inflate(contentViewResId, null));
    }


    class ShowInfo {
        private int[] anchorRootLocation = new int[2];
        private int[] anchorLocation = new int[2];
        Rect visibleWindowFrame = new Rect();
        int width;
        int height;
        int x;
        int y;
        View anchor;
        int anchorCenter;
        int direction = mPreferredDirection;
        int contentWidthMeasureSpec;
        int contentHeightMeasureSpec;


        ShowInfo(View anchor) {
            this.anchor = anchor;
            // for muti window
            anchor.getRootView().getLocationOnScreen(anchorRootLocation);
            anchor.getLocationOnScreen(anchorLocation);
            anchorCenter = anchorLocation[0] + anchor.getWidth() / 2;
            anchor.getWindowVisibleDisplayFrame(visibleWindowFrame);
        }


        float anchorProportion() {
            return (anchorCenter - x) / (float) width;
        }

        int windowWidth() {
            return width;
        }

        int windowHeight() {
            return height;
        }

        int getVisibleWidth() {
            return visibleWindowFrame.width();
        }

        int getVisibleHeight() {
            return visibleWindowFrame.height();
        }

        int getWindowX() {
            return x - visibleWindowFrame.left;
        }

        int getWindowY() {
            return y - visibleWindowFrame.top;
        }

    }

    @Override
    public void show(@NonNull View anchor) {
        if (mContentView == null) {
            throw new RuntimeException("you should call view() to set your content view");
        }
        ShowInfo showInfo = new ShowInfo(anchor);
        calculateWindowSize(showInfo);
        calculateXY(showInfo);
        decorateContentView(showInfo);
        mWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        mWindow.setWidth(ScreenUtil.getScreenWidth(mContext));
//        mWindow.setHeight(ScreenUtil.getScreenHeight(mContext));
        showAtLocation(anchor, 0, 0);
    }


    private void decorateContentView(ShowInfo showInfo) {
        ContentView contentView = ContentView.wrap(mContentView, mInitWidth, mInitHeight);
        Log.d(TAG, "contentView:" + contentView.getWidth() + " - " + contentView.getHeight());

        DecorRootView decorRootView = new DecorRootView(mContext, showInfo);
        decorRootView.setBackgroundColor(Color.parseColor("#553700B3"));
        decorRootView.setContentView(contentView);
        mWindow.setContentView(decorRootView);
        mWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_test_v1));
    }


    private void calculateXY(ShowInfo showInfo) {
        if (showInfo.anchorCenter < showInfo.visibleWindowFrame.left + showInfo.getVisibleWidth() / 2) { // anchor point on the left
            showInfo.x = Math.max(mEdgeProtectionLeft + showInfo.visibleWindowFrame.left, showInfo.anchorCenter - showInfo.width / 2 + mOffsetX);
        } else { // anchor point on the left
            showInfo.x = Math.min(
                    showInfo.visibleWindowFrame.right - mEdgeProtectionRight - showInfo.width,
                    showInfo.anchorCenter - showInfo.width / 2 + mOffsetX);
        }
        int nextDirection = DIRECTION_BOTTOM;
        if (mPreferredDirection == DIRECTION_BOTTOM) {
            nextDirection = DIRECTION_TOP;
        } else if (mPreferredDirection == DIRECTION_TOP) {
            nextDirection = DIRECTION_BOTTOM;
        }
        handleDirection(showInfo, mPreferredDirection, nextDirection);
    }

    private void handleDirection(ShowInfo showInfo, int currentDirection, int nextDirection) {
        if (currentDirection == DIRECTION_TOP) {
            showInfo.y = showInfo.anchorLocation[1] - showInfo.height - mOffsetYIfTop;
        } else if (currentDirection == DIRECTION_BOTTOM) {
            showInfo.y = showInfo.anchorLocation[1] + showInfo.anchor.getHeight() + mOffsetYIfBottom;
        }
    }

    protected int proxyWidth(int width) {
        return width;
    }

    protected int proxyHeight(int height) {
        return height;
    }

    private void calculateWindowSize(ShowInfo showInfo) {
        boolean needMeasureForWidth = false, needMeasureForHeight = false;
        if (mInitWidth > 0) {
            showInfo.width = proxyWidth(mInitWidth);
            showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    showInfo.width, View.MeasureSpec.EXACTLY);
        } else {
            int maxWidth = showInfo.getVisibleWidth() - mEdgeProtectionLeft - mEdgeProtectionRight;
            if (mInitWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
                showInfo.width = proxyWidth(maxWidth);
                showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        showInfo.width, View.MeasureSpec.EXACTLY);
            } else {
                needMeasureForWidth = true;
                showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        proxyWidth(maxWidth), View.MeasureSpec.AT_MOST);
            }
        }
        if (mInitHeight > 0) {
            showInfo.height = proxyHeight(mInitHeight);
            showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    showInfo.height, View.MeasureSpec.EXACTLY);
        } else {
            int maxHeight = showInfo.getVisibleHeight() - mEdgeProtectionTop - mEdgeProtectionBottom;
            if (mInitHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                showInfo.height = proxyHeight(maxHeight);
                showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        showInfo.height, View.MeasureSpec.EXACTLY);
            } else {
                needMeasureForHeight = true;
                showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        proxyHeight(maxHeight), View.MeasureSpec.AT_MOST);
            }
        }

        if (needMeasureForWidth || needMeasureForHeight) {
            mContentView.measure(
                    showInfo.contentWidthMeasureSpec, showInfo.contentHeightMeasureSpec);
            if (needMeasureForWidth) {
                showInfo.width = proxyWidth(mContentView.getMeasuredWidth());
            }
            if (needMeasureForHeight) {
                showInfo.height = proxyHeight(mContentView.getMeasuredHeight());
            }
        }
    }

    static class ContentView extends ZPopFrameLayout {
        private ContentView(Context context) {
            super(context);
        }

        static ContentView wrap(View businessView, int width, int height) {
            ContentView contentView = new ContentView(businessView.getContext());
            if (businessView.getParent() != null) {
                ((ViewGroup) businessView.getParent()).removeView(businessView);
            }
            contentView.addView(businessView, new LayoutParams(width, height));
            return contentView;
        }
    }

    class DecorRootView extends FrameLayout {
        private ShowInfo mShowInfo;
        private View mContentView;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                if (isTouchInBlack(event)) {
                    Log.d(TAG, "点击了空白区域，说明这个可以关掉");
                }
                dismiss();
            }
            return true;
        }

        private boolean isTouchInBlack(MotionEvent event) {
            View childView = findChildViewUnder(event.getX(), event.getY());
            return childView == null;
        }

        private View findChildViewUnder(float x, float y) {
            final int count = getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View child = getChildAt(i);
                final float translationX = child.getTranslationX();
                final float translationY = child.getTranslationY();
                if (x >= child.getLeft() + translationX
                        && x <= child.getRight() + translationX
                        && y >= child.getTop() + translationY
                        && y <= child.getBottom() + translationY) {
                    return child;
                }
            }
            return null;
        }

        private int mPendingWidth;
        private int mPendingHeight;
        private Runnable mUpdateWindowAction = new Runnable() {
            @Override
            public void run() {
                mShowInfo.width = mPendingWidth;
                mShowInfo.height = mPendingHeight;
                calculateXY(mShowInfo);
                mWindow.update(mShowInfo.getWindowX(), mShowInfo.getWindowY(), mShowInfo.windowWidth(), mShowInfo.windowHeight());
            }
        };

        private DecorRootView(Context context, ShowInfo showInfo) {
            super(context);
            mShowInfo = showInfo;
        }

        public void setContentView(View contentView) {
            if (mContentView != null) {
                removeView(mContentView);
            }
            if (contentView.getParent() != null) {
                ((ViewGroup) contentView.getParent()).removeView(contentView);
            }
            mContentView = contentView;
            addView(contentView);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            removeCallbacks(mUpdateWindowAction);
            if (mContentView != null) {
                mContentView.measure(mShowInfo.contentWidthMeasureSpec, mShowInfo.contentHeightMeasureSpec);
                int measuredWidth = mContentView.getMeasuredWidth();
                int measuredHeight = mContentView.getMeasuredHeight();
                if (mShowInfo.width != measuredWidth || mShowInfo.height != measuredHeight) {
                    mPendingWidth = measuredWidth;
                    mPendingHeight = measuredHeight;
                    post(mUpdateWindowAction);
                }
            }
//            setMeasuredDimension(mShowInfo.windowWidth(), mShowInfo.windowHeight());
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (mContentView != null) {
                Log.w(TAG, "位置 1 anchorLocation:" + Arrays.toString(mShowInfo.anchorLocation));
                Log.w(TAG, "位置 2 anchorRootLocation:" + Arrays.toString(mShowInfo.anchorRootLocation));
                Log.w(TAG, "位置 3 visibleWindowFrame:" + mShowInfo.visibleWindowFrame.toString());
                mContentView.layout(mShowInfo.getWindowX(), mShowInfo.getWindowY(), mShowInfo.getWindowX() + mShowInfo.width, mShowInfo.getWindowY() + mShowInfo.height);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            removeCallbacks(mUpdateWindowAction);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
        }
    }

}
