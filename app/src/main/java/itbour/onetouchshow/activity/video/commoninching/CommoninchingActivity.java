package itbour.onetouchshow.activity.video.commoninching;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.base.IntentCode;
import itbour.onetouchshow.base.SPCTag;
import itbour.onetouchshow.custom.InchingTextOperaPanel;
import itbour.onetouchshow.listener.SoftKeyBoardListener;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.T_;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * 用于竖屏的微调
 */

public class CommoninchingActivity extends MVPBaseActivity<CommoninchingContract.View, CommoninchingPresenter>
        implements CommoninchingContract.View, InchingTextOperaPanel.OnPanelButtonClick {

    @BindView(R.id.text_opera_panel)
    InchingTextOperaPanel textOperaPanel;
    @BindView(R.id.rl_inching_root)
    RelativeLayout rlInchingRoot;
    @BindView(R.id.tv_test_close)
    TextView tvTestClose;
    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected void initTitle() {
        super.initTitle();
        setTitleText("修改背景");
        getToolBarView().setRightText("存草稿").setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_.showToastReal("存草稿");
            }
        });
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_inching;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        initKeyBroadListener();
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(IntentCode.TAG, 0);
        int id = intent.getIntExtra(AppConst.ID, 0);

        switch (intExtra) {
            case IntentCode.INTENT_VERTICAL_MODEL:
                mPresenter.onVerticalModelEnter(id);
                break;
            case IntentCode.INTENT_VERTICAL_PRODUCT:
                break;
            case IntentCode.INTENT_VEDIO_MODEL:
                break;
            case IntentCode.INTENT_VEDIO_PRODUCT:
                break;
            default:
                break;
        }
    }

    private void initKeyBroadListener() {
        SoftKeyBoardListener.setListener(CommoninchingActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

                L_.i("键盘显示 高度" + height);
                int saveHeight = SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0);
                if (saveHeight == 0 || saveHeight < 200) {
                    SpUtils.putInt(SPCTag.KEYBROAD_HEIGHT, height);
                }
                if (textOperaPanel.getWenBenHeight() < 200) {
                    textOperaPanel.setWenBenParams(SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0));
                }


            }

            @Override
            public void keyBoardHide(int height) {
                L_.i("键盘隐藏 高度" + height);
                boolean isShow = textOperaPanel.editBarIsShow();

                if (isShow) {
                    textOperaPanel.hidePanelAndKeyBroad();
                }
            }
        });
    }


    @OnClick({R.id.tv_test_close, R.id.tv_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test_close:
                textOperaPanel.hidePanelAndKeyBroad();
                break;
            case R.id.tv_test:
                textOperaPanel.setThisShow();
                textOperaPanel.onWenBen();
                break;
            default:
                break;
        }
    }

    @Override
    public void onWenben() {
        L_.i("onWenben");
    }

    @Override
    public void onYanse() {
        L_.i("onYanse");
    }

    @Override
    public void onZiti() {
        L_.i("onZiti");
    }

    @Override
    public void onYangshi() {
        L_.i("onYangshi");
    }

    @Override
    public void onEnsure() {
        L_.i("onEnsure");

    }

    @Override
    public void onDelete() {
        L_.i("onDelete");
    }

    @Override
    public void onEditTextChange(String string) {
        L_.i(string);
    }

    /**
     * 监听按键当手势的时候隐藏键盘
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        L_.i("event===" + event);
        if (event.getAction() == KeyEvent.ACTION_UP &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            textOperaPanel.hidePanelAndKeyBroad();
            return true;

        }
        return super.dispatchKeyEvent(event);
    }

    //暂停时隐藏键盘
    @Override
    protected void onPause() {
        super.onPause();
        textOperaPanel.hidePanelAndKeyBroad();
    }


}
