package itbour.onetouchshow.activity.video.videoinching;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import itbour.onetouchshow.R;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;
import itbour.onetouchshow.view.ActivityUtils;
import onetouchshow.itbour.keybroadpanelswitchlib.util.KPSwitchConflictUtil;
import onetouchshow.itbour.keybroadpanelswitchlib.util.KeyboardUtil;
import onetouchshow.itbour.keybroadpanelswitchlib.widget.KPSwitchFSPanelLinearLayout;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * MVPBaseActivity<VideoinchingContract.View, VideoinchingPresenter>
 */

public class VideoinchingActivity extends AppCompatActivity implements VideoinchingContract.View {

    @BindView(R.id.tv_test_close)
    TextView tvTestClose;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.iv_inching_text_content)
    ImageView ivInchingTextContent;
    @BindView(R.id.tv_inching_text_content)
    TextView tvInchingTextContent;
    @BindView(R.id.ll_inching_text_content)
    LinearLayout llInchingTextContent;
    @BindView(R.id.iv_inching_text_color)
    ImageView ivInchingTextColor;
    @BindView(R.id.tv_inching_text_color)
    TextView tvInchingTextColor;
    @BindView(R.id.ll_inching_text_color)
    LinearLayout llInchingTextColor;
    @BindView(R.id.iv_inching_text_style)
    ImageView ivInchingTextStyle;
    @BindView(R.id.tv_inching_text_style)
    TextView tvInchingTextStyle;
    @BindView(R.id.ll_inching_text_style)
    LinearLayout llInchingTextStyle;
    @BindView(R.id.iv_inching_text_type)
    ImageView ivInchingTextType;
    @BindView(R.id.tv_inching_text_type)
    TextView tvInchingTextType;
    @BindView(R.id.ll_inching_text_type)
    LinearLayout llInchingTextType;
    @BindView(R.id.iv_inching_text_delete)
    ImageView ivInchingTextDelete;
    @BindView(R.id.tv_inching_text_delete)
    TextView tvInchingTextDelete;
    @BindView(R.id.ll_inching_text_delete)
    LinearLayout llInchingTextDelete;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    @BindView(R.id.ll_edit_bar)
    LinearLayout llEditBar;
    @BindView(R.id.sendMsgLayout)
    LinearLayout sendMsgLayout;
    @BindView(R.id.panel_content)
    RelativeLayout panelContent;
    @BindView(R.id.panel_root)
    KPSwitchFSPanelLinearLayout panelRoot;
    @BindView(R.id.ll_inching_root)
    LinearLayout llInchingRoot;
    private View type;
    private View color;
    private View style;

//    @Override
//    protected void initTitle() {
//        super.initTitle();
//        setTitleText("修改背景");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        getToolBarView().setRightText("存草稿").setRightTextOnClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                T_.showToastReal("存草稿");
//            }
//        }).setRightTextColor(Color.WHITE);
//
//        LinearLayout rootView = getRootView();
//        L_.i("rootViewId==="+rootView);
//    }

    private Unbinder unbinder;
    public VideoinchingPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_inching);
        unbinder = ButterKnife.bind(this);
        mPresenter = new VideoinchingPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initTitleBar();
        initKeyBroad();
        initTextBar();

    }

    private void initTitleBar() {
        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityUtils.removeActivity(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mPresenter = null;
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }


    private void initKeyBroad() {

        //注册面板的监听 这里可以拿到拿到键盘的状态
        KeyboardUtil.attach(this, panelRoot, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override
            public void onKeyboardShowing(boolean isShowing) {
                float x = tvTestClose.getX();
                float y = tvTestClose.getY();

                L_.i("onKeyboardShowing===" + isShowing + "x===" + x + "y===" + y);
                if (isShowing) {
                    L_.i("键盘弹出");


                } else {
                    L_.i("键盘收起或隐藏");
                    //需要这里是因为键盘自带的收起无法监听 目前就是要捕捉这种状态
                    //区分键盘收起和隐藏的状态
                    /**
                     * 1.当点击颜色 字体 样式这三种情况的时候 键盘是隐藏切换面板而不隐藏文字操作面板
                     * 2.猜想 当前editBar可见 且键盘隐藏被触发说明 该事件是键盘自带的收起
                     * 3.隐藏所有面板 有确定 点击收起的内容 删除 这三个
                     */
                    int visibility = llEditBar.getVisibility();
                    L_.i("visibility===" + visibility);
                    if (visibility == View.VISIBLE) {
                        if (sendMsgLayout != null) {
                            sendMsgLayout.setVisibility(View.GONE);
                        }
                        if (panelRoot != null) {
                            panelRoot.setVisibility(View.GONE);

                        }
                    }
                }
            }
        });
        KPSwitchConflictUtil.attach(panelRoot, etContent);
    }

    /**
     * 初始化三个view 用于切换不同的panl
     */
    private void initTextBar() {
//        panelContent.addV
        color = LayoutInflater.from(this).inflate(R.layout.merge_inching_opear_bar_color, null);
        type = LayoutInflater.from(this).inflate(R.layout.merge_inching_opear_bar_type, null);
        style = LayoutInflater.from(this).inflate(R.layout.merge_inching_opear_bar_style, null);
        if (panelContent != null) {
            color.setId(R.id.inching_text_color);
            type.setId(R.id.inching_text_type);
            style.setId(R.id.inching_text_style);
            panelContent.addView(color);
            panelContent.addView(type);
            panelContent.addView(style);
        }
    }

    @OnClick({R.id.tv_test_close, R.id.tv_test, R.id.tv_ensure,
            R.id.ll_inching_text_content, R.id.ll_inching_text_color,
            R.id.ll_inching_text_style, R.id.ll_inching_text_type,
            R.id.ll_inching_text_delete,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //点击文字操作面板中的文字
            case R.id.ll_inching_text_content:

                showKeyBroadHideOrderOperaPanel();
                break;
            //点击文字操作面板中的文字颜色
            case R.id.ll_inching_text_color:
                mPresenter.onInchingTextColorClick();
                changeTextParams(R.id.inching_text_color);
                break;
            //点击文字操作面板中的文字字体
            case R.id.ll_inching_text_style:
                mPresenter.onInchingTextStyleClick();
                changeTextParams(R.id.inching_text_style);
                break;
            //点击文字操作面板中的文字样式
            case R.id.ll_inching_text_type:
                mPresenter.onInchingTextTypeClick();
                changeTextParams(R.id.inching_text_type);
                break;
            //点击文字操作面板中的文字删除
            case R.id.ll_inching_text_delete:
                mPresenter.onInchingDeleteClick();
                closeKeyBroadAndPanel();
                break;
            //微调中点击了其它地方
            case R.id.tv_test_close:
                closeKeyBroadAndPanel();
                break;
            //点击弹出软键盘和控制面板
            case R.id.tv_test:
                showKeyBroadAndPanel();
                break;
            //编辑面板中的确认按钮
            case R.id.tv_ensure:
                closeKeyBroadAndPanel();
                break;
            default:
                break;
        }
    }

    /**
     * 切换文字操作面板的不同操作
     *
     * @param id
     */
    private int[] panelDetailIds =
            {R.id.inching_text_color, R.id.inching_text_style, R.id.inching_text_type};


    public void changeTextParams(int id) {
        if (panelContent != null) {
            for (int i = 0; i < panelDetailIds.length; i++) {
                int panelDetailId = panelDetailIds[i];
                View view = panelContent.findViewById(panelDetailId);

                if (id == panelDetailId) {
                    L_.i("idin===" + id);
                    view.setVisibility(View.VISIBLE);
                } else {
                    L_.i("idout===" + id);
                    view.setVisibility(View.GONE);
                }
            }
        }


        KPSwitchConflictUtil.showPanel(panelRoot);
        llEditBar.setVisibility(View.GONE);
    }

    /**
     * 当点击文字操作面板的文字界面时
     * 显示键盘隐藏其它的 文字操纵详情
     */
    @Override
    public void showKeyBroadHideOrderOperaPanel() {
        if (panelRoot != null && etContent != null) {
            KPSwitchConflictUtil.showKeyboard(panelRoot, etContent);

            if (!UIUtils.viewIsVisable(llEditBar)) {

                llEditBar.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * 关闭键盘和控制面板
     */
    @Override
    public void closeKeyBroadAndPanel() {
        if (panelRoot != null && sendMsgLayout != null) {

            KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot);
            if (UIUtils.viewIsVisable(sendMsgLayout)) {

                sendMsgLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示软键盘和文字编辑控制面板
     */
    @Override
    public void showKeyBroadAndPanel() {
        if (panelRoot != null && etContent != null && sendMsgLayout != null) {
            if (!UIUtils.viewIsVisable(sendMsgLayout)) {
                sendMsgLayout.setVisibility(View.VISIBLE);
            }
            if (!UIUtils.viewIsVisable(llEditBar)) {
                //同时显示edit的界面
                llEditBar.setVisibility(View.VISIBLE);
            }
            //显示keyBroad 同时不可见具体的操作面板
            KPSwitchConflictUtil.showKeyboard(panelRoot, etContent);
        }
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
            if (panelRoot.getVisibility() != View.GONE) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    //暂停时记录键盘的状态 防止切回时键盘显示不正确
    @Override
    protected void onPause() {
        super.onPause();
        if (panelRoot != null) {
            panelRoot.recordKeyboardStatus(getWindow());
        }
    }
}
