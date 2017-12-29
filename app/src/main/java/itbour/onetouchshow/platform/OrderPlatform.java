package itbour.onetouchshow.platform;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by onetouch on 2017/11/22.
 */

public class OrderPlatform {
    @IntDef({YJX_ORDER_TYPE_WATERMARK, YJX_ORDER_TYPE_SECKILL,
            YJX_ORDER_TYPE_INVOICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Platform {
    }

    public static final int YJX_ORDER_TYPE_WATERMARK  = 10;
    public static final int YJX_ORDER_TYPE_SECKILL   = 11;
    public static final int YJX_ORDER_TYPE_INVOICE   = 2;
}
