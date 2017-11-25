package itbour.onetouchshow.activity.videoplay;


import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.View;

import com.onetouch.listener.OnTransitionListener;
import com.onetouch.view.PreViewVideoPlayer;
import com.onetouch.view.StandardVideoPlayer;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import butterknife.BindView;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.videoplay.helper.video.VideoItem;
import itbour.onetouchshow.base.BaseVideoDetailActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VideoplayActivity extends BaseVideoDetailActivity<VideoplayContract.View, VideoplayPresenter> implements VideoplayContract.View {


    @BindView(R.id.detail_player)
    PreViewVideoPlayer detailPlayer;
    VideoItem videoItem;


    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";
    private boolean isTransition;
    private Transition transition;


    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        //设置缩略图
        //   Glide.with(this).load(videoItem.getImgUrl()).into(iv_video);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            videoItem = (VideoItem) extras.getSerializable("item");
            isTransition = extras.getBoolean(TRANSITION, false);
        } else {
            videoItem = new VideoItem();
            videoItem.setVideoUrl("http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8");
            videoItem.setTitle("11111");
            videoItem.setDocUrl("http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8");
        }

        initView(videoItem);
        //过渡动画
        initTransition();


    }

    private void initView(VideoItem videoItem) {

        resolveNormalVideoUI();

        initVideoBuilderMode();

        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                    detailPlayer.getCurrentPlayer().setRotateViewAuto(!lock);
                }
            }
        });


        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public StandardVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        new GSYVideoModel(videoItem.getVideoUrl(), "标题1");
        //不需要builder的
        return new GSYVideoOptionBuilder().setUrl(videoItem.getVideoUrl()).setCacheWithPlay(false).setVideoTitle("测试视频").setIsTouchWiget(true).setRotateViewAuto(getDetailOrientationRotateAuto()).setLockLand(false).setShowFullAnimation(false).setNeedLockFull(true).setLooping(true);
    }


    @Override
    public void clickForFullScreen() {

    }

    /**
     * 是否启动旋转横屏，true表示启动
     *
     * @return true
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
    }


    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(detailPlayer, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            detailPlayer.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener(){
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    detailPlayer.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }


}
