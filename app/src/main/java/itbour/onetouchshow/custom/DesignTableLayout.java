package itbour.onetouchshow.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.uilibrary.rv.RecyclerTabLayout;

import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/20.
 */

public class DesignTableLayout extends RecyclerTabLayout {


    public DesignTableLayout(Context context) {
        super(context);
    }

    public DesignTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DesignTableLayout(Context context, AttributeSet attrs, int defStyle) {
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

       // L_.e("paint.getMeasuredWidth" + textView.getMeasuredWidth());
        //L_.e("paint.getLeft" + textView.getLeft());
        if (mAdapter.getItemCount() > 0 && mAdapter.getItemCount() <= 5) {
            RelativeLayout root = (RelativeLayout) view;
            RelativeLayout matchRy = (RelativeLayout) root.getChildAt(0);
            TextView textView = (TextView) matchRy.getChildAt(0);
           // L_.e(" mAdapter.getItemCount()"+ mAdapter.getItemCount());
            int paddindLeft = UIUtils.WH()[0] / mAdapter.getItemCount() * mIndicatorPosition;
           // L_.e("paint.getLeft"+paddindLeft);
            int left;
            if (isLayoutRtl()) {
                left = textView.getLeft() - mScrollOffset - mIndicatorOffset + paddindLeft;
            } else {
                left = textView.getLeft() + mScrollOffset - mIndicatorOffset + paddindLeft;
            }
            int top = getHeight() - mIndicatorHeight;
            int bottom = getHeight();
            //新建矩形r2
            RectF r2 = new RectF();
            r2.left = left;
            r2.right = left + textView.getMeasuredWidth();
            r2.top = top;
            r2.bottom = bottom;
            canvas.drawRoundRect(r2, 10, 10, mIndicatorPaint);
        } else {
            super.onDraw(canvas);
        }
    }

}
