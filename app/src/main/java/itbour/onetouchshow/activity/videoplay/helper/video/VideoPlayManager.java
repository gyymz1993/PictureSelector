package itbour.onetouchshow.activity.videoplay.helper.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.onetouch.video.CustomMediaContoller;
import com.onetouch.video.VideoPlayView;

import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.videoplay.FullscreenActivity;
import itbour.onetouchshow.service.NetworkStateService;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;
import tv.danmaku.ijk.media.player.IMediaPlayer;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;


public class VideoPlayManager {
    private static VideoPlayManager instance;
    private Application mapplication;
    private  static  DisPlayThread disPlayThread;
    private VideoPlayManager(){
    }
    public static VideoPlayManager instance(){
        if (instance==null){
            synchronized (VideoPlayManager.class){
                if (instance==null){
                    instance=new VideoPlayManager();
                }
            }
        }
        return instance;
    }


    public  class DisPlayThread extends Thread {
        private boolean check = false;
        public synchronized  void startDisplay() {
            if (disPlayThread != null) {
                stopDisplay();
            }
            disPlayThread = new DisPlayThread();
            disPlayThread.start();
        }

        public synchronized  void stopDisplay() {
            if (disPlayThread != null) {
                disPlayThread.cancel();
                disPlayThread = null;
            }
        }

        private void cancel() {
            check = false;
        }

        private DisPlayThread() {

        }


        @Override
        public void run() {
            while (check) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果在后台运行，直接退出
                if (runOnBack) {
                    check = false;
                    stopDisplay();
                    return;
                }

                //检查是否有正在播放的Item，如果没有则不显示任何播放界面
                if (sPlayingItem == null) {
                    continue;
                }

                //检查是否有可播放的容器，通过Tag查找,不能通过id查找
                //因为在ListView或者RecycleView中View是会复用的，因此需要在ListView，或RecycleView中每次
                //创建holder的时候把tag设置到需要展示Video的FrameLayout上。
                //使用正在播放的item作为tag;
                if (currentActivity != null) {
                    View contentView = currentActivity.findViewById(android.R.id.content);
                    View playView = contentView.findViewWithTag(sPlayingItem);

                    //判断正在播放的view是否是显示在界面的,在ListView或RecycleView中会有移除屏幕的情况发生
                    if (isShowInWindow(playView)) {
                        //如果显示,判断是否和之前显示的是否是同一个View
                        //如果不是则切换到当前view中
                        exitFromSmallWindowMode();
                        if (sPlayingHolder != playView) {
                            UIUtils.getHandler().post(() -> {
                                //关闭屏幕常亮
                                if (sPlayingHolder != null) {
                                    sPlayingHolder.setKeepScreenOn(false);
                                }
                                removeVideoPlayViewFromParent();
                                ViewGroup viewGroup = (ViewGroup) playView;
                                viewGroup.addView(sVideoPlayView, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                                sPlayingHolder = viewGroup;
                                //保持屏幕常亮
                                sPlayingHolder.setKeepScreenOn(true);
                            });

                        }
                    } else {
                        //TODO 删除
                        //  如果不显示，则在小窗中播放
                        //enterSmallWindowMode();
                    }
                }
            }
        }

        Rect r = new Rect();

        private boolean isShowInWindow(View view) {
            if (view == null) {
                return false;
            }
            boolean localVisibleRect = view.getLocalVisibleRect(r);
            boolean show = localVisibleRect && view.isShown();
            return show;
        }

        @Override
        public synchronized void start() {
            check = true;
            super.start();
        }
    }


    public  void initialize(Application application) {
        if (haveInit) {
            return;
        }
        disPlayThread=new DisPlayThread();
        mapplication = application;
        windowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        //初始化播放容器
        initVideoPlayView();
        //创建小窗播放容器
        // createSmallWindow();
        //注册事件 处理
        registerEvent();
        //监听应用前后台的切换
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks);
        haveInit = true;
    }

    private WindowManager windowManager;
    private  boolean haveInit = false;

    //小窗播放
    private  FrameLayout smallPlayHolder;
    private  RelativeLayout smallWindow;
    private  LayoutParams smallWindowParams;
    //小窗关闭的button
    private  ImageView iv_close;


    private  VideoPlayView sVideoPlayView;
    //正在播放的Item
    private  VideoItem sPlayingItem = null;
    //正在暂时视频的容器
    private  ViewGroup sPlayingHolder = null;
    //当前的Activity
    private  Activity currentActivity;

    //标识是否在后台运行
    private  boolean runOnBack = false;

    /**
     * 用于播放完成的监听器
     */
    private  CompletionListener completionListener = new CompletionListener(){

    };


    //标识是否在小窗模式
    private  boolean sPlayInSmallWindowMode = false;

    //用于在主线程中更新UI

    //记录在小窗中按下的位置
    private  float xDownInSmallWindow, yDownInSmallWindow;

    /**
     * 记录在小窗中上一次触摸的位置
     */
    private  float lastX, lastY = 0;

   // private static VideoAdapter.VideoClickListener videoClickListener = new VideoAdapter.VideoClickListener();




    /**
     * 初始化播放控件
     */
    private  void initVideoPlayView() {
        sVideoPlayView = new VideoPlayView(mapplication);
        sVideoPlayView.setCompletionListener(completionListener);
        sVideoPlayView.setFullScreenChangeListener(fullScreenChangeListener);
        sVideoPlayView.setOnErrorListener(onErrorListener);

    }

    private  IMediaPlayer.OnErrorListener onErrorListener = (mp, what, extra) -> {
        T_.showToastReal("播放失败");
        completionListener.completion(null);
        return true;
    };

    /**
     * 用于显示视频的线程
     * 在应用进入前台的时候启动，在切换到后台的时候停止
     * 负责，判断当前的显示状态并显示到正确位置
     */
    @SuppressLint("ClickableViewAccessibility")
    private  void createSmallWindow() {
        smallWindow = (RelativeLayout) View.inflate(mapplication, R.layout.view_small_holder, null);
        smallPlayHolder = (FrameLayout) smallWindow.findViewById(R.id.small_holder);
        //关闭button
        iv_close = (ImageView) smallWindow.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v ->
        {
            if (sVideoPlayView.isPlay()) {
                sVideoPlayView.stop();
                sVideoPlayView.release();
            }
            completionListener.completion(null);
        });
        smallWindowParams = new LayoutParams();
        int width = UIUtils.dip2px(160);
        int height = UIUtils.dip2px( 90);
        smallWindowParams.width = width;
        smallWindowParams.height = height;
        smallWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
        smallWindowParams.x = 0;
        smallWindowParams.y = 0;
      /*  if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            smallWindowParams.type = LayoutParams.TYPE_TOAST;
        } else {
            smallWindowParams.type = LayoutParams.TYPE_PHONE;
        }*/
        smallWindowParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
        smallWindowParams.flags = FLAG_NOT_FOCUSABLE | FLAG_KEEP_SCREEN_ON;
        // 设置期望的bitmap格式
        smallWindowParams.format = PixelFormat.RGBA_8888;
        //实现view可拖动
        smallWindow.setOnTouchListener((v, event) -> {

            switch (event.getAction()) {
                case ACTION_DOWN:
                    xDownInSmallWindow = event.getRawX();
                    yDownInSmallWindow = event.getRawY();
                    lastX = xDownInSmallWindow;
                    lastY = yDownInSmallWindow;
                    break;
                case ACTION_MOVE:
                    float moveX = event.getRawX() - lastX;
                    float moveY = event.getRawY() - lastY;
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    if (Math.abs(moveX) > 10 || Math.abs(moveY) > 10) {
                        //更新
                        smallWindowParams.x += moveX;
                        smallWindowParams.y += moveY;
                        windowManager.updateViewLayout(smallWindow, smallWindowParams);
                        return true;
                    }
                    break;
                case ACTION_UP:
                    moveX = event.getRawX() - xDownInSmallWindow;
                    moveY = event.getRawY() - yDownInSmallWindow;
                    //实现点击事件
                    if (Math.abs(moveX) < 10 && Math.abs(moveY) < 10) {
                      //  videoClickListener.onVideoClick(currentActivity, sPlayingItem);
                        return true;
                    }
                    break;
                    default:
                        break;
            }
            return false;
        });
    }


    /**
     * 请求用户给予悬浮窗的权限
     */
    public  boolean askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(currentActivity)) {
                //   Toast.makeText(TestFloatWinActivity.this, "当前无权限，请授权！", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + currentActivity.getPackageName()));
//                currentActivity.startActivityForResult(intent,OVERLAY_PERMISSION_REQ_CODE);
                currentActivity.startActivity(intent);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    /**
     * 用于监控应用前后台的切换
     */
    private  Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        private int count = 0;
        private boolean videoPause = false;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (count == 0) {
                //切换到前台
                runOnBack = false;
                if (sPlayInSmallWindowMode) {
                    windowManager.addView(smallWindow, smallWindowParams);
                }
                //继续播放视频
                if (videoPause) {
                    sVideoPlayView.pause();
                    videoPause = false;
                }
                if (disPlayThread!=null){
                    disPlayThread.startDisplay();
                }

            }
            count++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            count--;
            if (count == 0) {
                //切换到后台
                runOnBack = true;
                //停止检测线程
                if (disPlayThread!=null){
                    disPlayThread.stopDisplay();
                }
                //如果是小窗模式移除window
                if (sPlayInSmallWindowMode) {
                    windowManager.removeView(smallWindow);
                }

                //视频暂停
                if (sVideoPlayView.isPlay()) {
                    sVideoPlayView.pause();
                    videoPause = true;
                }

            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    /**
     * 退出全屏
     */
    private  void exitFromFullScreenMode() {
        currentActivity.finish();
    }

    private  CustomMediaContoller.FullScreenChangeListener fullScreenChangeListener = () -> {
        if (!(currentActivity instanceof FullscreenActivity)) {
            enterFullScreenMode();
        } else {
            exitFromFullScreenMode();
        }
    };


    private  void enterFullScreenMode() {
        currentActivity.startActivity(new Intent(currentActivity, FullscreenActivity.class));
    }


    private  class CompletionListener implements VideoPlayView.CompletionListener {

        @Override
        public void completion(IMediaPlayer mp) {
            T_.showToastReal("播放完毕");

            //重播
            if (sVideoPlayView!=null) {
                T_.showToastReal("重播");
                sVideoPlayView.start(sPlayingItem.getVideoUrl());
            }

            if (currentActivity instanceof FullscreenActivity) {
                currentActivity.finish();
            }

            //如果是小窗播放则退出小窗
            if (sPlayInSmallWindowMode) {
                if (mp != null) {
                    //mp不等于null表示正常的播放完成退出
                    //在小窗消失之前给用户一个提示消息，防止太突兀
                    T_.showToastReal("播放完毕");
                }
               //TODO  小窗模式
                exitFromSmallWindowMode();
            }
           // onDestroyVedioPaly();

        }

    }

    private void onDestroyVedioPaly(){
        //将播放控件从器父View中移出
        removeVideoPlayViewFromParent();

        sPlayingItem = null;
        if (sPlayingHolder != null) {
            sPlayingHolder.setKeepScreenOn(false);
        }
        sPlayingHolder = null;
        //释放资源
        sVideoPlayView.release();
    }

    /**
     * 注册事件处理
     */
    private  void registerEvent() {

        //处理在View中播放
        RxBus.getDefault().toObserverable(PlayInViewEvent.class).subscribe(playInViewEvent -> {


            //表示播放容器，和视频内容是否变化
            boolean layoutChange = sPlayingHolder == null || !sPlayingHolder.equals(playInViewEvent.getPlayLayout());
            boolean videoChange = sPlayingItem == null || !sPlayingItem.equals(playInViewEvent.getNewsItem());


            //重置状态，保存播放的Holder
            if (videoChange) {
                sPlayingItem = playInViewEvent.getNewsItem();

            }

            if (layoutChange) {
                removeVideoPlayViewFromParent();
                if (sPlayingHolder != null) {
                    //关闭之前View的屏幕常亮
                    sPlayingHolder.setKeepScreenOn(false);
                }
                sPlayingHolder = playInViewEvent.getPlayLayout();
                //将播放的Item设置为播放view的tag，就可以通过displayThread检查当前Activity中是否
                //包含了这个tag的View存在，而直到是否有播放容器存在，如果没有的话就使用小窗播放。
                sPlayingHolder.setTag(sPlayingItem);
                //显示控制条
                sVideoPlayView.setShowContoller(true);
                //开启屏幕常亮
                sVideoPlayView.setKeepScreenOn(true);
                sPlayingHolder.addView(sVideoPlayView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            if (videoChange) {
                //播放新视频
                if (sVideoPlayView.isPlay()) {
                    sVideoPlayView.stop();
                    sVideoPlayView.release();
                }
                sPlayingHolder.setTag(sPlayingItem);

                //判断网络,如果在移动网络则提示用户
                ViedoPlayChecker.checkPlayNet(currentActivity, () -> {
                    sVideoPlayView.start(sPlayingItem.getVideoUrl());
                }, () -> {
                    completionListener.completion(null);
                });

            } else {
                //重播
                if (!sVideoPlayView.isPlay()) {
                    sVideoPlayView.start(sPlayingItem.getVideoUrl());
                }
            }
        });

        //处理视频回退
        RxBus.getDefault().toObserverable(PlayVideoBackEvent.class).subscribe(playVideoBackEvent -> {
            sPlayingHolder = null;
        });

        //播放页面destory
        RxBus.getDefault().toObserverable(PlayVideoExit.class).subscribe(playVideoBackEvent -> {
            onDestroyVedioPaly();
            sPlayingHolder = null;
        });

        //处理网络变化
        RxBus.getDefault().toObserverable(NetworkStateService.NetStateChangeEvent.class).subscribe(netStateChangeEvent -> {
            if (netStateChangeEvent.getState() == NetworkStateService.NetStateChangeEvent.NetState.NET_4G && sVideoPlayView.isPlay()) {
                sVideoPlayView.pause();
                //如果在移动网络播放，则提示用户
                ViedoPlayChecker.checkPlayNet(currentActivity, () -> {
                    sVideoPlayView.pause();
                }, () -> {
                    completionListener.completion(null);
                });
            }
        });

        //处理取消播放事件
        RxBus.getDefault().toObserverable(PlayCancleEvent.class).subscribe(playCancleEvent -> {
            completionListener.completion(null);
        });

    }


    /**
     * 进入小窗播放模式
     */
    private  void enterSmallWindowMode() {
        //检查权限
        if (!askForPermission()) {
            //ToastUtil.getInstance().showToast("小窗播放需要浮窗权限");
            T_.showToastReal("小窗播放需要浮窗权限");
            return;
        }

        if (!sPlayInSmallWindowMode) {
            UIUtils.getHandler().post(() -> {
                removeVideoPlayViewFromParent();
                //隐藏控制条
                sVideoPlayView.setShowContoller(false);
                smallPlayHolder.addView(sVideoPlayView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                try {
                    windowManager.addView(smallWindow, smallWindowParams);
                } catch (Exception e) {
                    e.printStackTrace();
                    //已经添加了，则更新
                    //TODO
                    L_.e("TODO","smallWindow"+smallWindow+"windowManager:"+windowManager);
                    windowManager.updateViewLayout(smallWindow, smallWindowParams);
                }
                sPlayingHolder = smallPlayHolder;
                sPlayInSmallWindowMode = true;
            });
        }
    }


    /**
     * 退出小窗播放模式
     */
    private  void exitFromSmallWindowMode() {
        if (sPlayInSmallWindowMode) {
            UIUtils.getHandler().post(() -> {

                windowManager.removeView(smallWindow);

                sPlayInSmallWindowMode = false;
                //显示控制条
                sVideoPlayView.setShowContoller(true);
            });
        }
    }


    private  void removeVideoPlayViewFromParent() {
        if (sVideoPlayView != null) {
            if (sVideoPlayView.getParent() != null) {
                ViewGroup parent = (ViewGroup) sVideoPlayView.getParent();
                parent.removeView(sVideoPlayView);
            }
        }
    }


    public  VideoItem getPlayingItem() {
        return sPlayingItem;
    }


    /**
     * 取消播放事件，比如应用程序退出时发出这个时间
     */
    public  class PlayCancleEvent {
    }

    /**
     * 视频播放退出
     */
    public static class PlayVideoBackEvent {
    }


    /**
     * 视频播放退出
     */
    public static class PlayVideoExit {
    }

    /**
     * 将视频显示在指定的View中
     * 如果视频发生改变则播放视频
     * 如果view发生改变但是视频没有改变，则只是切换播放的view。
     */
    public static class PlayInViewEvent {
        FrameLayout playLayout;
        VideoItem newsItem;
        boolean playInList;

        public PlayInViewEvent(FrameLayout playLayout, VideoItem newsItem) {
            this(playLayout, newsItem, false);
        }

        public PlayInViewEvent(FrameLayout playLayout, VideoItem newsItem, boolean playInList) {
            this.playLayout = playLayout;
            this.newsItem = newsItem;
            this.playInList = playInList;
        }

        public VideoItem getNewsItem() {
            return newsItem;
        }

        public void setNewsItem(VideoItem newsItem) {
            this.newsItem = newsItem;
        }

        public FrameLayout getPlayLayout() {
            return playLayout;
        }

        public void setPlayLayout(FrameLayout playLayout) {
            this.playLayout = playLayout;
        }
    }
}
