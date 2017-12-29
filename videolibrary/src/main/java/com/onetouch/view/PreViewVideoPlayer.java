package com.onetouch.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.onetouch.videolibrary.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;


/**
 * 进度图小图预览的另类实现
 * Created by shuyu on 2016/12/10.
 */

public class PreViewVideoPlayer extends NormalVideoPlayer {

    private RelativeLayout mPreviewLayout;

    private ImageView mPreView;

    /**
     * 是否因为用户点击
     */
    private boolean mIsFromUser;

    //是否打开滑动预览
    private boolean mOpenPreView = false;

    private int mPreProgress = -2;

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public PreViewVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public PreViewVideoPlayer(Context context) {
        super(context);
    }

    public PreViewVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        initView();
    }

    private void initView() {
        mPreviewLayout = (RelativeLayout) findViewById(R.id.preview_layout);
        mPreView = (ImageView) findViewById(R.id.preview_image);
        mLoadingProgressBar =  findViewById(R.id.loading);
        //mLoadingProgressBar.setVisibility(GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_paly_preview;
    }


    @Override
    protected void prepareVideo() {
        super.prepareVideo();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
        super.onProgressChanged(seekBar, progress, fromUser);
        if (fromUser && mOpenPreView) {
            int width = seekBar.getWidth();
            int time = progress * getDuration() / 100;
            int offset = (int) (width - (getResources().getDimension(R.dimen.seek_bar_image) / 2)) / 100 * progress;
            Debuger.printfError("***************** " + progress);
            Debuger.printfError("***************** " + time);
            showPreView(mOriginUrl, time);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPreviewLayout.getLayoutParams();
            layoutParams.leftMargin = offset;
            //设置帧预览图的显示位置
            mPreviewLayout.setLayoutParams(layoutParams);
            if (GSYVideoManager.instance().getMediaPlayer() != null
                    && mHadPlay && (mOpenPreView)) {
                mPreProgress = progress;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        if (mOpenPreView) {
            mIsFromUser = true;
            mPreviewLayout.setVisibility(VISIBLE);
            mPreProgress = -2;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mOpenPreView) {
            if (mPreProgress >= 0) {
                seekBar.setProgress(mPreProgress);
            }
            super.onStopTrackingTouch(seekBar);
            mIsFromUser = false;
            mPreviewLayout.setVisibility(GONE);
        } else {
            super.onStopTrackingTouch(seekBar);
        }
    }

    @Override
    protected void setTextAndProgress(int secProgress) {
        if (mIsFromUser) {
            return;
        }
        super.setTextAndProgress(secProgress);
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        PreViewVideoPlayer customGSYVideoPlayer = (PreViewVideoPlayer) gsyBaseVideoPlayer;
        customGSYVideoPlayer.mOpenPreView = mOpenPreView;
        return gsyBaseVideoPlayer;
    }


    @Override
    public void onPrepared() {
        super.onPrepared();
        startDownFrame(mOriginUrl);
    }

    public boolean isOpenPreView() {
        return mOpenPreView;
    }

    /**
     * 如果是需要进度条预览的设置打开，默认关闭
     */
    public void setOpenPreView(boolean localFile) {
        this.mOpenPreView = localFile;
    }


    private void showPreView(String url, long time) {
        int width = CommonUtil.dip2px(getContext(), 150);
        int height = CommonUtil.dip2px(getContext(), 100);
//        Glide.with(getContext().getApplicationContext())
//                .setDefaultRequestOptions(
//                        new RequestOptions()
//                                //这里限制了只从缓存读取
//                                .onlyRetrieveFromCache(true)
//                                .frame(1000 * time)
//                                .override(width, height)
//                                .dontAnimate()
//                                .centerCrop())
//                .load(url)
//                .into(mPreView);
    }


    private void startDownFrame(String url) {
        for (int i = 1; i <= 100; i++) {
            int time = i * getDuration() / 100;
            int width = CommonUtil.dip2px(getContext(), 150);
            int height = CommonUtil.dip2px(getContext(), 100);
//            Glide.with(getContext().getApplicationContext())
//                    .setDefaultRequestOptions(
//                            new RequestOptions()
//                                    .frame(1000 * time)
//                                    .override(width, height)
//                                    .centerCrop())
//                    .load(url).preload(width, height);

        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void updateStartImage() {
        if (mStartButton instanceof ImageView) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(R.drawable.video_pause_click_selector);
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                imageView.setImageResource(R.drawable.video_play_click_selector);
            } else {
                imageView.setImageResource(R.drawable.video_play_click_selector);
            }
        }
    }

    /*全屏的图片*/
    @Override
    public int getShrinkImageRes() {
        if (mShrinkImageRes == -1) {
            return R.mipmap.video_enlarge;
        }
        return mShrinkImageRes;
    }
    /*半屏的图片*/
    @Override
    public int getEnlargeImageRes() {
        if (mEnlargeImageRes == -1) {
            return R.mipmap.video_enlarge;
        }
        return mEnlargeImageRes;
    }

}
