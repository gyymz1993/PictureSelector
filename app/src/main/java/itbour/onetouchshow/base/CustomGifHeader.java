package itbour.onetouchshow.base;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;

import itbour.onetouchshow.R;


public class CustomGifHeader extends LinearLayout implements IHeaderCallBack {
    private TextView mHintTextView;
    private ProgressBar id_pro_gress;

    public CustomGifHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomGifHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.gif_header, this);
        mHintTextView = (TextView) findViewById(R.id.gif_header_hint);
        id_pro_gress=findViewById(R.id.id_pro_gress);
    }

    @Override
    public void setRefreshTime(long lastRefreshTime) {
    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        id_pro_gress.setVisibility(VISIBLE);
        mHintTextView.setText(R.string.xrefreshview_header_hint_laoding);
    }

    @Override
    public void onStateReady() {
      //  mHintTextView.setText(R.string.xrefreshview_header_hint_ready);
    }

    @Override
    public void onStateRefreshing() {
      //  mHintTextView.setText(R.string.xrefreshview_header_hint_refreshing);
    }

    @Override
    public void onStateFinish(boolean success) {
        id_pro_gress.setVisibility(GONE);
        mHintTextView.setText(success ? R.string.xrefreshview_header_hint_loaded : R.string.xrefreshview_header_hint_loaded_fail);
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
        //
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
