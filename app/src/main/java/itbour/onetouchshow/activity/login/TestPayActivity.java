package itbour.onetouchshow.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;
import com.three.pay.PayPlatform;
import com.three.pay.ThirdPayUtils;
import com.three.pay.bean.PayInfo;
import com.three.pay.listener.PayListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.R;
import itbour.onetouchshow.base.ABaseActivity;
import itbour.onetouchshow.bean.PayInfoBean;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;

/**
 * Created by onetouch on 2017/11/24.
 */

public class TestPayActivity extends ABaseActivity {
    @BindView(R.id.ed_order)
    EditText edOrder;
    @BindView(R.id.btn_pay)
    Button btnPay;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_pay_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edOrder.getText().length() == 0) {
                    T_.showToastReal("请输入单号");
                }
                onOrderPay(edOrder.getText().toString());

            }
        });
    }


    public void onOrderPay(String orderId) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", 610);
        stringObjectHashMap.put("userId", "299188");
        stringObjectHashMap.put("orderId", orderId + "");
        HttpUtils.getInstance().executeGet(AppConfig.GETORDERPAYPARAM_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {

                PayInfoBean payInfoBean = new Gson().fromJson(response, PayInfoBean.class);
                if (payInfoBean.getPaymentPlatform() == 10) {
                    PayInfo payInfo = new PayInfo();
                    payInfo.setNonceStr(payInfoBean.getWechat().getNonceStr());
                    payInfo.setPrepayId(payInfoBean.getWechat().getPrepayId());
                    payInfo.setTimeStamp(payInfoBean.getWechat().getTimeStamp());
                    payInfo.setSign(payInfoBean.getWechat().getSign());
                    ThirdPayUtils.initialize(TestPayActivity.this).pay(PayPlatform.WxPay, payInfo, new PayListener() {
                        @Override
                        public void paySuccess() {
                            T_.showToastReal("支付成功");
                            L_.e("支付成功");
                        }

                        @Override
                        public void payFailure(Exception e) {
                            L_.e("支付失败");
                            T_.showToastReal("支付失败" + e.getMessage());
                        }

                        @Override
                        public void userCancel() {
                            T_.showToastReal("取消支付");
                            L_.e("取消支付");
                        }
                    });
                }
                L_.e(response);
            }
        });
    }


    @Override
    public int getFragmentContentId() {
        return 0;
    }

}
