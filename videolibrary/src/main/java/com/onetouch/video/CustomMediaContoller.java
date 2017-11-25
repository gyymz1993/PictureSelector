package com.onetouch.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onetouch.media.IMediaController;
import com.onetouch.media.IjkVideoView;
import com.onetouch.videolibrary.R;

import java.util.Formatter;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class CustomMediaContoller implements IMediaController ,SeekBar.OnSeekBarChangeListener{

    private static final int SET_VIEW_HIDE = 1;
    private static final int TIME_OUT = 5000;
    private static final int MESSAGE_SHOW_PROGRESS = 2;
    private static final int PAUSE_IMAGE_HIDE = 3;
    private View itemView;
    private View view;
    private boolean isShow;
    private IjkVideoView videoView;
    private boolean isScroll;

    private SeekBar seekBar;
    AudioManager audioManager;
    private ProgressBar progressBar;

    private boolean isSound;
    private boolean isDragging;

    private boolean isPause;

    private boolean isShowContoller;
    private ImageView sound, full, play;
    private TextView time, allTime;
    private PointF lastPoint;
    private Context context;
    private ImageView pauseImage;
    private Bitmap bitmap;
    //检测视频播放完成

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SET_VIEW_HIDE:
                    isShow = false;
                    itemView.setVisibility(View.GONE);
                    break;
                case MESSAGE_SHOW_PROGRESS:
                    setProgress();
                    if (!isDragging && isShow) {
                        msg = obtainMessage(MESSAGE_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000);
                    }
                    break;
                case PAUSE_IMAGE_HIDE:
                    pauseImage.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };


    public CustomMediaContoller(Context context, View view) {
        this.view = view;
        itemView = view.findViewById(R.id.media_contoller);
        this.videoView = (IjkVideoView) view.findViewById(R.id.main_video);
        itemView.setVisibility(View.GONE);
        isShow = false;
        isDragging = false;

        isShowContoller = true;
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        initView();
        initAction();
    }

    public void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.loading);
        seekBar = (SeekBar) itemView.findViewById(R.id.seekbar);
        allTime = (TextView) itemView.findViewById(R.id.all_time);
        time = (TextView) itemView.findViewById(R.id.time);
        full = (ImageView) itemView.findViewById(R.id.full);
        sound = (ImageView) itemView.findViewById(R.id.sound);
        play = (ImageView) itemView.findViewById(R.id.player_btn);
        pauseImage = (ImageView) view.findViewById(R.id.pause_image);
    }

    public void start() {
        pauseImage.setVisibility(View.GONE);
        itemView.setVisibility(View.GONE);
        play.setImageResource(R.mipmap.video_stop_btn);
        progressBar.setVisibility(View.VISIBLE);

    }

    public void pause() {
        play.setImageResource(R.mipmap.video_play_btn);
        videoView.pause();
        bitmap = videoView.getBitmap();
        if (bitmap != null) {
            pauseImage.setImageBitmap(bitmap);
            pauseImage.setVisibility(View.VISIBLE);
        }
    }

    public void reStart() {
        play.setImageResource(R.mipmap.video_stop_btn);
        videoView.start();
        if (bitmap != null) {
            handler.sendEmptyMessageDelayed(PAUSE_IMAGE_HIDE, 100);
            bitmap.recycle();
            bitmap = null;
//                        pauseImage.setVisibility(View.GONE);
        }
    }

    private long duration;

    private void initAction() {
        isSound = false;
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(this);

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect seekRect = new Rect();
                seekBar.getHitRect(seekRect);
                if ((event.getY() >= (seekRect.top - 50)) && (event.getY() <= (seekRect.bottom + 50))) {
                    float y = seekRect.top + seekRect.height() / 2;
                    //seekBar only accept relative x
                    float x = event.getX() - seekRect.left;
                    if (x < 0) {
                        x = 0;
                    } else if (x > seekRect.width()) {
                        x = seekRect.width();
                    }
                    MotionEvent me = MotionEvent.obtain(event.getDownTime(), event.getEventTime(),
                            event.getAction(), x, y, event.getMetaState());
                    return seekBar.onTouchEvent(me);
                }
                return false;
            }
        });

        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {

                Log.e("setOnInfoListener", what + "");
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //开始缓冲
                        if (progressBar.getVisibility() == View.GONE) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //开始播放
                        progressBar.setVisibility(View.GONE);
                        break;

                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                        statusChange(STATUS_PLAYING);
                        progressBar.setVisibility(View.GONE);
                        break;

                    case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                        progressBar.setVisibility(View.GONE);
                        break;
                        default:break;
                }
                return false;
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSound) {
                    //静音
                    sound.setImageResource(R.mipmap.sound_mult_icon);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                } else {
                    //取消静音
                    sound.setImageResource(R.mipmap.sound_open_icon);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                }
                isSound = !isSound;
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    pause();
                } else {
                    reStart();
                }
            }
        });

        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullScreenChangeListener != null) {
                    fullScreenChangeListener.change();
                }

            }

        });
    }

    FullScreenChangeListener fullScreenChangeListener;

    public void setFullScreenChangeListener(FullScreenChangeListener listener) {
        this.fullScreenChangeListener = listener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String string = generateTime((long) (duration * progress * 1.0f / 100));
        Log.e("initAction ","onProgressChanged"+string);
        long duration = videoView.getDuration();
        long newposition = (duration * progress) / 1000L;
       // videoView.seekTo( (int) newposition);
        if (time != null) {
            //拖动显示时间
            time.setText(string);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e("initAction ","onProgressChanged"+seekBar.getProgress()+"");
        setProgress();
        isDragging = true;
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        handler.removeMessages(MESSAGE_SHOW_PROGRESS);
        show();
        handler.removeMessages(SET_VIEW_HIDE);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isDragging = false;
        videoView.seekTo((int) (duration * seekBar.getProgress() * 1.0f / 100));
        Log.e("initAction ","onStopTrackingTouch"+(int) (duration * seekBar.getProgress() * 1.0f / 100)+"");
        handler.removeMessages(MESSAGE_SHOW_PROGRESS);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        isDragging = false;
        handler.sendEmptyMessageDelayed(MESSAGE_SHOW_PROGRESS, 1000);
        show();
    }

    public interface FullScreenChangeListener {
        void change();
    }

    public void setShowContoller(boolean isShowContoller) {
        this.isShowContoller = isShowContoller;
        handler.removeMessages(SET_VIEW_HIDE);
        itemView.setVisibility(View.GONE);
    }


    @Override
    public void hide() {
        if (isShow) {
            handler.removeMessages(MESSAGE_SHOW_PROGRESS);
            isShow = false;
            handler.removeMessages(SET_VIEW_HIDE);
            itemView.setVisibility(View.GONE);
        }
    }




    @Override
    public boolean isShowing() {
        return isShow;
    }

    @Override
    public void setAnchorView(View view) {
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
    }

    @Override
    public void show(int timeout) {
        handler.sendEmptyMessageDelayed(SET_VIEW_HIDE, timeout);
    }

    @Override
    public void show() {
        if (!isShowContoller) {
            return;
        }
        isShow = true;
        progressBar.setVisibility(View.GONE);
        itemView.setVisibility(View.VISIBLE);
        handler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS);
        show(TIME_OUT);
    }

    @Override
    public void showOnce(View view) {
    }

    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public void setVisiable() {
        show();
    }

    private long setProgress() {
        if (isDragging) {
            return 0;
        }
        long position = videoView.getCurrentPosition();
        long duration = videoView.getDuration();
        this.duration = duration;
        if (!generateTime(duration).equals(allTime.getText().toString())) {
            allTime.setText(generateTime(duration));
        }
        if (seekBar != null) {
            if (duration > 0) {
                long pos = 100L * position / duration;
                seekBar.setProgress((int) pos);
            }
            int percent = videoView.getBufferPercentage();
            seekBar.setSecondaryProgress(percent);
        }
//        String string = generateTime((long) (duration * seekBar.getProgress() * 1.0f / 100));
//        time.setText(string);
      //  Log.e("initAction",string);

        if (time != null) {
            time.setText(stringForTime((int) position));
        }
        return position;
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    private VedioIsPause vedioIsPause;

    public interface VedioIsPause {
        void pause(boolean pause);
    }

    public void setPauseImageHide() {
        pauseImage.setVisibility(View.GONE);
    }
}
