package itbour.onetouchshow.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import itbour.onetouchshow.R;
import itbour.onetouchshow.base.SPCTag;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by zjl_d on 2017/11/16.
 * 本类为微调文字操作面板类
 */

public class InchingTextOperaPanel extends LinearLayout {

    private LinearLayout wenben;
    private LinearLayout yanse;
    private LinearLayout ziti;
    private LinearLayout yangshi;
    private LinearLayout shanchu;
    private LinearLayout editBar;

    private RelativeLayout opearDetail;

    private RelativeLayout onWenBen;
    private RelativeLayout onColor;
    private RelativeLayout onType;
    private RelativeLayout onYangShi;
    private EditText etContent;
    private TextView tvEnsure;


    public RelativeLayout getOnWenBen() {
        return onWenBen;
    }

    public boolean editBarIsShow(){
        return UIUtils.viewIsVisable(editBar);
    }


    public void setWenBenParams(int height) {

        ViewGroup.LayoutParams layoutParams = onWenBen.getLayoutParams();
        layoutParams.height = height;
        onWenBen.setLayoutParams(layoutParams);

    }

    public int getWenBenHeight() {
        int height = onWenBen.getHeight();
        return height;
    }

    public InchingTextOperaPanel(Context context) {
        this(context, null);
    }

    public InchingTextOperaPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //初始化view
        findView();
        //初始化监听
        initClickListener();
    }

    public InchingTextOperaPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //实现该接口的activity 使用该界面的activity
        onPanelButtonClick = (OnPanelButtonClick) context;

    }

    private void hidAllOpearPanel() {
        onWenBen.setVisibility(GONE);
        onColor.setVisibility(GONE);
        onType.setVisibility(GONE);
        onYangShi.setVisibility(GONE);

    }


    private void initClickListener() {
        wenben.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要弹出键盘时
                //先改变布局 再切换键盘的状态
                onWenBen();
                onPanelButtonClick.onWenben();
            }
        });
        yanse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要隐藏键盘时 先隐藏键盘 再改变布局
                onYanSe();
                onPanelButtonClick.onYanse();

            }
        });

        ziti.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onZiTi();
                onPanelButtonClick.onZiti();

            }

        });

        yangshi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onYangShi();
                onPanelButtonClick.onYangshi();

            }
        });

        shanchu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePanelAndKeyBroad();
                onPanelButtonClick.onDelete();

            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                onPanelButtonClick.onEditTextChange(string);

            }
        });

        tvEnsure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePanelAndKeyBroad();
                onPanelButtonClick.onEnsure();
            }
        });
    }

    /**
     * 删除 确认 放大后的界面 收起键盘和整个操作面板
     */
    public void hidePanelAndKeyBroad() {
        UIUtils.hideKeyboard(etContent);
        this.setVisibility(GONE);
    }


    private void onYangShi() {
        UIUtils.hideKeyboard(etContent);

        hidAllOpearPanel();
        editBar.setVisibility(GONE);
        onYangShi.setVisibility(VISIBLE);
    }

    private void onZiTi() {
        UIUtils.hideKeyboard(etContent);

        hidAllOpearPanel();
        editBar.setVisibility(GONE);
        onType.setVisibility(VISIBLE);
    }

    private void onYanSe() {
        UIUtils.hideKeyboard(etContent);

        hidAllOpearPanel();
        editBar.setVisibility(GONE);
        onColor.setVisibility(VISIBLE);
    }

    public void onWenBen() {
        hidAllOpearPanel();
        onWenBen.setVisibility(VISIBLE);
        editBar.setVisibility(VISIBLE);
        UIUtils.showKeyboard(etContent);
    }

    public void setThisShow() {
        this.setVisibility(VISIBLE);
    }




    private void findView() {
        //文本按钮
        wenben = (LinearLayout) findViewById(R.id.ll_inching_text_content);
        //颜色按钮
        yanse = (LinearLayout) findViewById(R.id.ll_inching_text_color);
        //字体按钮
        ziti = (LinearLayout) findViewById(R.id.ll_inching_text_style);
        //样式按钮
        yangshi = (LinearLayout) findViewById(R.id.ll_inching_text_type);
        //删除按钮
        shanchu = (LinearLayout) findViewById(R.id.ll_inching_text_delete);
        //输入文字框整体
        editBar = (LinearLayout) findViewById(R.id.ll_edit_bar);
        etContent = (EditText) findViewById(R.id.et_content);
        tvEnsure = (TextView) findViewById(R.id.tv_ensure);


        //操作的详细界面
        opearDetail = (RelativeLayout) findViewById(R.id.rl_opear_detail_content);

        //输入详情的布局
        onWenBen = (RelativeLayout) findViewById(R.id.rl_on_text);
        //默认高度为0 如果这个高度小于300 那么认为
        if (onWenBen.getHeight() < 300) {
            //如果之前获取到过键盘高那么直接设置
            int height = SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0);
            if (height > 0) {
                //如果没有在微调界面的监听里会去设置
                setWenBenParams(height);
            }

        }
        onColor = (RelativeLayout) findViewById(R.id.rl_on_color);
        onType = (RelativeLayout) findViewById(R.id.rl_type);
        onYangShi = (RelativeLayout) findViewById(R.id.rl_style);
    }


    private OnPanelButtonClick onPanelButtonClick;

    public interface OnPanelButtonClick {
        void onWenben();

        void onYanse();

        void onZiti();

        void onYangshi();

        void onEnsure();

        void onDelete();

        void onEditTextChange(String string);
    }

}
