package itbour.onetouchshow.activity.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.main.MainActivity;
import itbour.onetouchshow.activity.web.CommWebActivity;
import itbour.onetouchshow.base.SPCTag;
import itbour.onetouchshow.custom.CodeButton;
import itbour.onetouchshow.custom.DialogUtils;
import itbour.onetouchshow.listener.EdittextListener;
import itbour.onetouchshow.listener.SoftKeyBoardListener;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.RegexpUtils;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.id_code_btn)
    Button idCodeBtn;
    CodeButton codeButton;
    public final int M = 1000;
    @BindView(R.id.id_tv_confir)
    TextView idTvConfir;
    @BindView(R.id.id_ly_wechat_login)
    LinearLayout lyWeiChat;
    @BindView(R.id.id_ed_number)
    EditText idEdNumber;
    @BindView(R.id.id_ed_code)
    EditText idEdCode;
    @BindView(R.id.id_ig_colse)
    ImageView igClose;
    @BindView(R.id.id_user_greement)
    TextView userAgreement;

    @BindView(R.id.id_root_ry)
    LinearLayout rootLy;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (SpUtils.getInstance().getBoolean(AppConst.ISLOGIN, false)) {
//            openActivity(MainActivity.class);
//            finish();
//            return;
//        }
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
       // mPresenter.postDataTest();
        setOnclickListener();
        initSeachBar();
        initKeyBroadListener();

    }


    private void initKeyBroadListener() {
        SoftKeyBoardListener.setListener(LoginActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //  L_.i("键盘显示 高度" + height);
                int saveHeight = SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0);
                if (saveHeight == 0 || saveHeight < 300) {
                    SpUtils.putInt(SPCTag.KEYBROAD_HEIGHT, height);
                }

            }

            @Override
            public void keyBoardHide(int height) {
                //L_.i("键盘隐藏 高度" + height);
                idEdNumber.clearFocus();
                idEdCode.clearFocus();
            }
        });
    }

    public void setOnclickListener() {
        codeButton = new CodeButton(idCodeBtn, 60 * M, M);
        idEdNumber.addTextChangedListener(new EdittextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && !codeButton.isOnTick() && RegexpUtils.isMobile(s.toString())) {
                    idCodeBtn.setEnabled(true);
                } else {
                    idCodeBtn.setEnabled(false);
                }
                if (s.length() != 0 && idEdCode.getText().length() != 0 && RegexpUtils.isMobile(s.toString())) {
                    idTvConfir.setEnabled(true);
                } else {
                    idTvConfir.setEnabled(false);
                }
            }
        });

        idEdCode.addTextChangedListener(new EdittextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && idEdNumber.getText().length() != 0 && RegexpUtils.isMobile(idEdNumber.getText().toString())) {
                    idTvConfir.setEnabled(true);
                } else {
                    idTvConfir.setEnabled(false);
                }
            }
        });
    }


    @Override
    protected void initTitle() {
        super.initTitle();
        // setTitleText("登录页面");
        testDilag();
    }

    public void testDilag() {
        getToolBarView().setLeftText("555555").setLetfTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testAlertDialog();

            }
        }).setRightText("1111111").setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // testOnlyOneButtonDialog();
                // T_.showToastReal("Right");

            }
        }).setRightTextColor(UIUtils.getColor(R.color.colorAccent)).getLeftimageView().setVisibility(View.GONE);
    }


    public void testAlertDialog() {
        DialogUtils.getInstance(new DialogUtils.Builder().setTitle("去水印").setCancletext("你确定吗？").setCancleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_.showToastReal("取消");
                DialogUtils.getInstance().dismiss();
            }
        }).setConfirmText("确定").setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_.showToastReal("我确认");
                DialogUtils.getInstance().dismiss();
              //  mPresenter.onOrderPay("", LoginActivity.this);
            }
        }).setContext("我好看吗").setPlatform(DialogUtils.DialogPlatform.TWO_BTN)).
                builder(LoginActivity.this);
    }

    public void testOnlyOneButtonDialog() {
        DialogUtils.getInstance(new DialogUtils.Builder().setTitle("测试赛").setOlnyOneConfirmText("只有一个哦").setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_.showToastReal("只有一个哦");
                DialogUtils.getInstance().dismiss();
            }
        }).setContext("我好看吗").setPlatform(DialogUtils.DialogPlatform.ONE_BTN)).builder(LoginActivity.this);
    }

    @Override
    public void loadSucceed(String result) {
        showContentView();
        T_.showToastReal(result);
    }

    @Override
    public void loadFaild(String error) {
        T_.showToastReal(error);
    }


    @OnClick({R.id.id_code_btn, R.id.id_tv_confir, R.id.id_ly_wechat_login, R.id.id_ig_colse, R.id.id_user_greement})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_code_btn:
                String number = idEdNumber.getText().toString();
                if (!TextUtils.isEmpty(number) && RegexpUtils.isMobile(number)) {
                    showProgressDialogWithText("获取验证码");
                    mPresenter.getCode(number);
                } else {
                    T_.showCustomToast("请输入正确的手机号码");
                }

                break;
            case R.id.id_tv_confir:
                String number1 = idEdNumber.getText().toString();
                String code = idEdCode.getText().toString();
                if (TextUtils.isEmpty(number1) && !RegexpUtils.isMobile(number1)) {
                    T_.showCustomToast("请输入正确的手机号码");
                } else if (TextUtils.isEmpty(code)) {
                    T_.showCustomToast("请输入验证码");
                } else {
                    mPresenter.doCodeLogin(number1, code);
                }
                break;
            case R.id.id_ly_wechat_login:
                mPresenter.weichatLogin(this);
                break;
            case R.id.id_ig_colse:
                finish();
                break;
            case R.id.id_user_greement:
                Bundle bundle = new Bundle();
                bundle.putString(AppConst.WEBTYPE, AppConst.TYPE_USERAGREEMENT);
                openActivity(CommWebActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    private void initSeachBar() {
        idEdNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void getCodeSuccess(boolean isSucceed) {
        dismissProgressDialog();
        codeButton.start();
        idCodeBtn.setEnabled(false);
        idTvConfir.setEnabled(true);
    }

    @Override
    public void getCodefailed() {
        dismissProgressDialog();
    }

    @Override
    public void loginSuccess(String result) {
        // showProgressSuccess("登录成功");
        SpUtils.getInstance().saveString(AppConst.USER_INFO, result);
        SpUtils.getInstance().saveBoolean(AppConst.ISLOGIN, true);
        // LoginBean loginBean = new Gson().fromJson(result, LoginBean.class);
        // openActivity(MainActivity.class);
        finish();

//        LogingStatusListener.getInstance().checkLogin(new LogingStatusListener.LoginForCallBack() {
//            @Override
//            public void callBack() {
//                openActivity(MainActivity.class);
//            }
//        });
    }

    @Override
    public void loginfailed(String msg) {
    }

}
