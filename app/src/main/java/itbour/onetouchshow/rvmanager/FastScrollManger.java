package itbour.onetouchshow.rvmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;


public class FastScrollManger extends StaggeredGridLayoutManager {


    public FastScrollManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastScrollManger(int spanCount, int orientation) {
        super(spanCount, orientation);
        mOrientation = orientation;
        createOrientationHelpers();
    }

    public int findFirstTopCompletelyEmergedItemPosition() {
        final View child = findOneBorderCompleteVisibleChild(getChildCount() - 1, -1, false);
        return child == null ? -1 : getPosition(child);
    }

    public int findLastBottomCompletelyEmergedItemPosition() {
        final View child = findOneBorderCompleteVisibleChild(0, getChildCount(), true);
        return child == null ? -1 : getPosition(child);
    }

    private View findOneBorderCompleteVisibleChild(int fromIndex, int toIndex, boolean requestTop) {
        final int start = mSecondaryOrientation.getStartAfterPadding();
        final int end = mSecondaryOrientation.getEndAfterPadding();

        final int next = toIndex > fromIndex ? 1 : -1;
        for (int i = fromIndex; i != toIndex; i += next)

        {
            final View child = getChildAt(i);
            final int childStart = mSecondaryOrientation.getDecoratedStart(child);

            final int childEnd = mSecondaryOrientation.getDecoratedEnd(child);
            if (childStart < end && childEnd > start)

            {
                if (requestTop) {
                    if (childStart >= start) {
                        return child;
                    }
                } else if (childEnd <= end) {
                    return child;
                }
            }
        }
        return null;
    }
    private int mOrientation;
    @NonNull
    OrientationHelper mSecondaryOrientation;
    private void createOrientationHelpers() {
        mSecondaryOrientation = OrientationHelper
                .createOrientationHelper(this, 1 - mOrientation);
    }
}