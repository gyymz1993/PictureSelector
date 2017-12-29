package itbour.onetouchshow.base;

import android.content.res.Configuration;
import android.view.View;

import com.lsjr.utils.NetUtils;
import com.onetouch.view.StandardVideoPlayer;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import itbour.onetouchshow.activity.login.LoginActivity;
import itbour.onetouchshow.custom.DialogUtils;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.mvp.BaseView;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;

/**
 * 详情模式播放页面基础类
 */
@Deprecated
public abstract class BaseVideoDetailActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseActivity<V, T> implements BaseView, StandardVideoAllCallBack {

    protected boolean isPlay;

    protected boolean isPause;

    protected OrientationUtils orientationUtils;

    /**
     * 选择普通模式
     */
    public void initVideo() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, getGSYVideoPlayer());
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        if (getGSYVideoPlayer().getFullscreenButton() != null) {
            getGSYVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //直接横屏
                   // orientationUtils.resolveByClick();
                    //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                    getGSYVideoPlayer().startWindowFullscreen(BaseVideoDetailActivity.this, true, true);

                    clickForFullScreen();
                }
            });
            getGSYVideoPlayer().setLockClickListener(new LockClickListener() {
                @Override
                public void onClick(View view, boolean lock) {
                   // orientationUtils.setEnable(lock);
                }
            });
            getGSYVideoPlayer().setShowCustomWifiDialog(new StandardVideoPlayer.ShowCustomWifiDialog() {
                @Override
                public void showDialog() {
                    DialogUtils.getInstance(new DialogUtils.Builder().setTitle("")
                            .setCancletext("取消观看")
                            .setCancleListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    T_.showToastReal("取消");
                                    getGSYVideoPlayer().startPlayLogic();
                                    DialogUtils.getInstance().dismiss();
                                }
                            }).setConfirmText("确定观看").setConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    T_.showToastReal("我确认");
                                    DialogUtils.getInstance().dismiss();
                                }
                            }).setContext("目前为非WIFI环境，继续观看会产生流量？")).builder(BaseVideoDetailActivity.this);
                }
            });
        }
    }


    public void startPlayVideo() {
//        if (NetUtils.isWifi(getApplication())) {
//            getGSYVideoPlayer().startPlayLogic();
//        } else {
//            DialogUtils.getInstance(new DialogUtils.Builder().setTitle("")
//                    .setCancletext("取消观看")
//                    .setCancleListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            // T_.showToastReal("取消");
//
//                            DialogUtils.getInstance().dismiss();
//                        }
//                    }).setConfirmText("确定观看").setConfirmListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            // T_.showToastReal("我确认");
//                            getGSYVideoPlayer().startPlayLogic();
//                            DialogUtils.getInstance().dismiss();
//                        }
//                    }).setContext("目前为非WIFI环境，继续观看会产生流量？").
//                            setPlatform(DialogUtils.DialogPlatform.TWO_BTN))
//                    .builder(BaseVideoDetailActivity.this);
//        }

        getGSYVideoPlayer().startPlayLogic();
    }


    /**
     * 选择builder模式
     */
    public void initVideoBuilderMode() {
        initVideo();
        getGSYVideoOptionBuilder().
                setVideoAllCallBack(this)
                .build(getGSYVideoPlayer());
    }

    @Override
    public void onBackPressed() {
//        if (orientationUtils != null) {
//            orientationUtils.backToProtVideo();
//        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            //getGSYVideoPlayer().onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }

    @Override
    public void onPrepared(String url, Object... objects) {

//        if (orientationUtils == null) {
//            throw new NullPointerException("initVideo() or initVideoBuilderMode() first");
//        }
//        //开始播放了才能旋转和全屏
//        orientationUtils.setEnable(false);
        //orientationUtils.setEnable(getDetailOrientationRotateAuto());
        isPlay = true;
    }

    @Override
    public void onClickStartIcon(String url, Object... objects) {

    }

    @Override
    public void onClickStartError(String url, Object... objects) {

    }

    @Override
    public void onClickStop(String url, Object... objects) {

    }

    @Override
    public void onClickStopFullscreen(String url, Object... objects) {

    }

    @Override
    public void onClickResume(String url, Object... objects) {

    }

    @Override
    public void onClickResumeFullscreen(String url, Object... objects) {

    }

    @Override
    public void onClickSeekbar(String url, Object... objects) {

    }

    @Override
    public void onClickSeekbarFullscreen(String url, Object... objects) {

    }

    @Override
    public void onAutoComplete(String url, Object... objects) {

    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {

    }

    @Override
    public void onQuitFullscreen(String url, Object... objects) {
//        if (orientationUtils != null) {
//            orientationUtils.backToProtVideo();
//        }
    }

    @Override
    public void onQuitSmallWidget(String url, Object... objects) {

    }

    @Override
    public void onEnterSmallWidget(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekVolume(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekPosition(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekLight(String url, Object... objects) {

    }

    @Override
    public void onPlayError(String url, Object... objects) {

    }

    @Override
    public void onClickStartThumb(String url, Object... objects) {

    }

    @Override
    public void onClickBlank(String url, Object... objects) {

    }

    @Override
    public void onClickBlankFullscreen(String url, Object... objects) {

    }

    /**
     * 播放控件
     */
    public abstract StandardVideoPlayer getGSYVideoPlayer();

    /**
     * 配置播放器
     */
    public abstract GSYVideoOptionBuilder getGSYVideoOptionBuilder();

    /**
     * 点击了全屏
     */
    public abstract void clickForFullScreen();

    /**
     * 是否启动旋转横屏，true表示启动
     */
    public abstract boolean getDetailOrientationRotateAuto();

    @Override
    public void finish() {
        if (isPlay) {
            getGSYVideoPlayer().release();
        }
//        if (orientationUtils != null) {
//            orientationUtils.releaseListener();
//        }
        super.finish();
        L_.e("finish");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        L_.e("onPostResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L_.e("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L_.e("onStop");
    }


    @Override
    protected void onPause() {
        if (getGSYVideoPlayer() != null) {
            getGSYVideoPlayer().onVideoPause();
        }
        //getGSYVideoPlayer().onVideoPause();
        isPause = true;
        super.onPause();
        L_.e("onPause");
    }

    @Override
    protected void onResume() {
        if (getGSYVideoPlayer() != null) {
            getGSYVideoPlayer().onVideoResume();
            //getGSYVideoPlayer().onVideoReset();
        }

        isPause = false;
        super.onResume();
        L_.e("onResume");
    }


    @Override
    protected void onDestroy() {
        if (isPlay) {
            getGSYVideoPlayer().release();
        }
//        if (orientationUtils != null) {
//            orientationUtils.releaseListener();
//        }
        super.onDestroy();
        L_.e("onDestroy");
    }


}
