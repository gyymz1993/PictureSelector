package itbour.onetouchshow.custom;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by onetouch on 2017/11/21.
 */

public class ScaleImageView extends android.support.v7.widget.AppCompatImageView {

    private int initWidth;
    private int initHeight;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInitSize(int initWidth, int initHeight) {
        this.initWidth = initWidth;
        this.initHeight = initHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (initWidth > 0 && initHeight > 0) {
            setMeasuredDimension(initWidth, initHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
