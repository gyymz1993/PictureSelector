package itbour.onetouchshow.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itbour.onetouchshow.R;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/18.
 */

public class DialogUtils extends DialogFragment {

    @BindView(R.id.id_text_title)
    TextView idTextTitle;
    @BindView(R.id.id_tv_content)
    TextView idTvContent;

    @BindView(R.id.id_ed_content)
    EditText idEdContent;

    @BindView(R.id.id_btn_cancle)
    TextView idBtnCancle;
    @BindView(R.id.id_btn_cofirm)
    TextView idBtnCofirm;
    @BindView(R.id.id_btn_olny_cofirm)
    TextView idTvOlnyConfirm;

    @BindView(R.id.id_ly_twobtncontra)
    LinearLayout idLyTowContext;

    @BindView(R.id.id_ly_onebtncontra)
    RelativeLayout idLyOneContext;

    @BindView(R.id.id_ed_ly)
    RelativeLayout idEdLy;


    Unbinder unbinder;
    private View rootView;
    static Builder builder;

    private static DialogUtils dialogUtils = new DialogUtils();


    @SuppressLint("ValidFragment")
    private DialogUtils() {
    }

    /**
     * 初始化的时候用
     *
     * @return
     */
    public static DialogUtils getInstance(Builder param) {
        builder = param;
        return dialogUtils;
    }

    public static void setBuilder(Builder builder) {
        DialogUtils.builder = builder;
    }

    /**
     * 取消的时候用
     *
     * @return
     */
    public static DialogUtils getInstance() {
        return dialogUtils;
    }

    public void dismissClean() {
        L_.i(dialogUtils.idEdContent.getText());
        L_.i(idEdContent.getText());
        idEdContent.setText("");
        dialogUtils.dismiss();
        L_.i(builder);
    }

    public String getIdEdContent() {
        String edString = idEdContent.getText().toString();
        idEdContent.setText("");
        return edString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = UIUtils.inflate(R.layout.dialog_com);
        unbinder = ButterKnife.bind(this, rootView);
        L_.i(builder);
        L_.i(this);
        L_.i(dialogUtils);

        if (!TextUtils.isEmpty(builder.mTitle) && idTextTitle != null) {
            idTextTitle.setText(builder.mTitle);
        }
        if (!TextUtils.isEmpty(builder.mContext) && idTvContent != null) {
            idTvContent.setText(builder.mContext);
        }
        if (!TextUtils.isEmpty(builder.mCancletext) && idBtnCancle != null) {
            idBtnCancle.setText(builder.mCancletext);
        }
        if (!TextUtils.isEmpty(builder.mConfirmText) && idBtnCofirm != null) {
            idBtnCofirm.setText(builder.mConfirmText);
        }
        if (null != builder.mConfirmListener && idBtnCofirm != null) {
            idBtnCofirm.setOnClickListener(builder.mConfirmListener);
        }
        if (null != builder.mCancleListener && idBtnCancle != null) {
            idBtnCancle.setOnClickListener(builder.mCancleListener);
        }

        if (null != builder.mConfirmListener && idTvOlnyConfirm != null) {
            idTvOlnyConfirm.setOnClickListener(builder.mConfirmListener);
        }

        if (null != builder.mOlnyOneConfirmText && idTvOlnyConfirm != null) {
            idTvOlnyConfirm.setText(builder.mOlnyOneConfirmText);
        }

        if (null != builder.mContext && idEdContent != null) {
            String text = builder.mContext;
            int length = text.length();
            idEdContent.setText(text);
            idEdContent.setSelection(length);
        }


        switch (builder.platform) {
            case DialogPlatform.ONE_BTN:
                idLyTowContext.setVisibility(View.GONE);
                idLyOneContext.setVisibility(View.VISIBLE);
                idEdLy.setVisibility(View.GONE);
                break;
            case DialogPlatform.TWO_BTN:
                idLyTowContext.setVisibility(View.VISIBLE);
                idLyOneContext.setVisibility(View.GONE);
                idEdLy.setVisibility(View.GONE);
                break;
            case DialogPlatform.ONE_EDIT:
                idLyTowContext.setVisibility(View.VISIBLE);
                idLyOneContext.setVisibility(View.GONE);
                idEdLy.setVisibility(View.VISIBLE);
                idTvContent.setVisibility(View.GONE);
                break;
            default:
                idLyTowContext.setVisibility(View.GONE);
                idLyOneContext.setVisibility(View.VISIBLE);
                break;

        }
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //此处可以设置Dialog的style等等
        super.onCreate(savedInstanceState);
        //无法直接点击外部取消dialog
        setCancelable(false);
        //NO_FRAME就是dialog无边框，0指的是默认系统Theme
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog_transparent_style);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //windowParams.dimAmount = 0.0f;
        // windowParams.y = 100;
        window.setAttributes(windowParams);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(UIUtils.WHD()[0] / 4 * 3, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        if (null != builder.mContext && idEdContent != null) {
            String text = builder.mContext;
            int length = text.length();
            idEdContent.setText(text);
            idEdContent.setSelection(length);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public DialogUtils builder(Activity activity) {
        show(activity.getFragmentManager().beginTransaction(), "dialogUtils");
        return this;
    }


    public static class Builder {
        private String mTitle;
        private String mContext;
        private String mCancletext;
        private String mConfirmText;
        private String mOlnyOneConfirmText;
        private View.OnClickListener mCancleListener;
        private View.OnClickListener mConfirmListener;
        private int platform;

        public Builder setOlnyOneConfirmText(String confirmText) {
            mOlnyOneConfirmText = confirmText;
            return this;
        }

        public Builder setPlatform(@DialogPlatform.Platform int platform) {
            this.platform = platform;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContext(String context) {
            mContext = context;
            return this;
        }

        public Builder setCancletext(String cancletext) {
            mCancletext = cancletext;
            return this;
        }

        public Builder setConfirmText(String confirmText) {
            mConfirmText = confirmText;
            return this;
        }

        public Builder setCancleListener(View.OnClickListener onClickListener) {
            mCancleListener = onClickListener;
            return this;
        }

        public Builder setConfirmListener(View.OnClickListener onClickListener) {
            mConfirmListener = onClickListener;
            return this;
        }
    }


    public static class DialogPlatform {
        @IntDef({ONE_BTN, TWO_BTN,
                ONE_EDIT})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Platform {
        }

        public static final int ONE_BTN = 10;
        public static final int TWO_BTN = 11;
        public static final int ONE_EDIT = 2;
    }


}
