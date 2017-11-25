package itbour.onetouchshow.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.evenbus.DetailsAction;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/21.
 */

public class ImagePorviderService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ImagePorviderService() {
        super("ImagePorviderService");
    }

    public static void startService(Context context, DetailsAction detailsAction) {
        //L_.e("designListBeans"+designListBeans.size());
        Intent intent = new Intent(context, ImagePorviderService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) detailsAction.getData());
        intent.putExtra("subType", detailsAction.getSubType());
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        List<DesignListBean.ListBean> datas = intent.getParcelableArrayListExtra("data");
        String subtype = intent.getStringExtra("subType");
        handlerData(datas, subtype);

    }

    private void handlerData(List<DesignListBean.ListBean> datas, String subtype) {
        if (datas.size() == 0) {
            EventBus.getDefault().post(datas);
            return;
        }
        for (DesignListBean.ListBean data : datas) {
            int width = UIUtils.WHD()[0] / 2 - 10;
            int height = width * data.getH() / data.getW();
            String imUrl = ImageLoader.getShrinkImageUrl(data.getThumb(), width, height);
            data.setW(width);
            data.setH(height);
            data.setThumb(imUrl);
        }
        DetailsAction action = new DetailsAction(subtype, datas);
        EventBus.getDefault().post(action);
    }
}
