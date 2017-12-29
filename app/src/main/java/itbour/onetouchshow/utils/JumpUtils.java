package itbour.onetouchshow.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import itbour.onetouchshow.activity.video.videoplay.VideoplayActivity1;
import itbour.onetouchshow.bean.ModelVideoBean;

/**
 * Created by onetouch on 2017/11/25.
 */

public class JumpUtils {

    /**
     * 跳转到视频播放
     *
     * @param activity
     * @param view
     */
    public static void goToVideoPlayer(Activity activity, View view, ModelVideoBean videoItem) {
        Intent intent = new Intent(activity, VideoplayActivity1.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("item",  videoItem);
        bundle.putBoolean(VideoplayActivity1.TRANSITION, true);
        intent.putExtras(bundle);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Pair pair = new Pair<>(view, VideoplayActivity1.IMG_TRANSITION);
//            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity, pair);
//            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
//        } else {
//            activity.startActivity(intent);
//            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
//        }
    }

}
