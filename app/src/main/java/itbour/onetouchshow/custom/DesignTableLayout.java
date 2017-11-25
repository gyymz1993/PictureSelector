package itbour.onetouchshow.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

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
       // L_.e("onDraw"+view.getWidth());
        mRequestScrollToTab = false;

        int left;

        if (isLayoutRtl()) {
            left = view.getLeft() - mScrollOffset - mIndicatorOffset-view.getPaddingLeft();
        } else {
            left = view.getLeft() + mScrollOffset - mIndicatorOffset+view.getPaddingLeft();
        }

        int top = getHeight() - mIndicatorHeight;
        int bottom = getHeight();
        //新建矩形r2
        @SuppressLint("DrawAllocation") RectF r2 = new RectF();
        //测量自适应字体为80
        r2.left = left+UIUtils.WHD()[0]/3/2-30;
        r2.right = r2.left+65;
        r2.top = top;
        r2.bottom = bottom;
       // L_.e("left"+r2.left+"----"+"right"+r2.right+"----------Uiw"+UIUtils.WHD()[0]/5);
        canvas.drawRoundRect(r2, 10, 10, mIndicatorPaint);
        //360   180-10  180+10
        //720   720/2
    }

}
