package itbour.onetouchshow.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by onetouch on 2017/11/17.
 */

public class VideoBackInService extends Service {

    private VideoBackInServiceBinder binder;
    private static final Intent SERVICE_INTENT = new Intent();
    // Binder
    public class VideoBackInServiceBinder extends Binder {
        public VideoBackInService getService() {
            return VideoBackInService.this;
        }
    }

    static {
        SERVICE_INTENT.setComponent(
                new ComponentName("com.lsjr.zizi",
                        "com.lsjr.zizisteward.service.XChatService"));
    }


    public static Intent getIntent() {
        return SERVICE_INTENT;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        binder = new VideoBackInServiceBinder();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
