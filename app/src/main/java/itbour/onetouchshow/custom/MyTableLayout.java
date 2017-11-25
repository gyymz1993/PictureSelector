package itbour.onetouchshow.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.ys.uilibrary.rv.RecyclerTabLayout;


public class MyTableLayout extends RecyclerTabLayout {

    public MyTableLayout(Context context) {
        super(context);
    }

    public MyTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onDraw(Canvas canvas) {
        View view = mLinearLayoutManager.findViewByPosition(mIndicatorPosition);
        if (view == null) {
            if (mRequestScrollToTab) {
                mRequestScrollToTab = false;
                scrollToTab(mViewPager.getCurrentItem());
            }
            return;
        }
        mRequestScrollToTab = false;

        int left;
        int right;
        if (isLayoutRtl()) {
            left = view.getLeft() - mScrollOffset - mIndicatorOffset-view.getPaddingLeft();
            right = view.getRight() - mScrollOffset + mIndicatorOffset-view.getPaddingRight();
        } else {
            left = view.getLeft() + mScrollOffset - mIndicatorOffset+view.getPaddingLeft();
            right = view.getRight() + mScrollOffset + mIndicatorOffset-view.getPaddingRight();
        }

        int top = getHeight() - mIndicatorHeight;
        int bottom = getHeight();
        canvas.drawRect(left, top, right, bottom, mIndicatorPaint);
    }

}
