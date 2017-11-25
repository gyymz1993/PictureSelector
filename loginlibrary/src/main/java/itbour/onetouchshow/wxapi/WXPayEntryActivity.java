package itbour.onetouchshow.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.three.pay.ThirdPayUtils;
import com.three.pay.instance.WxPayInstance;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private WxPayInstance payInstance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payInstance = (WxPayInstance) ThirdPayUtils.initialize(this).payInstance;
        payInstance.mIWXAPI.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        payInstance.mIWXAPI.handleIntent(getIntent(),this);
    }


    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        ThirdPayUtils.initialize(this).handleResult(resp.getType(),resp.errCode,getIntent());
        finish();
    }

}