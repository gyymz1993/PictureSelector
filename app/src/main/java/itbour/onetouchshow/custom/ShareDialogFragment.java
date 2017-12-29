package itbour.onetouchshow.custom;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;
import com.lsjr.net.Encrypt;
import com.three.share.SharePlatform;
import com.three.share.ThirdShareUtils;
import com.three.share.listener.ShareListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.R;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.MyRyItemListener;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/22.
 */

public class ShareDialogFragment extends DialogFragment {

    private static ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
    @BindView(R.id.gvShare)
    RecyclerView gvShare;
    @BindView(R.id.btnCancel)
    ImageView btnCancel;
    Unbinder unbinder;
    private static Builder builder;

    /**
     * 初始化的时候用
     *
     * @return
     */
    public static ShareDialogFragment getInstance(Builder param) {
        builder = param;
        return shareDialogFragment;
    }

    public ShareDialogFragment setBuilder(Builder param) {
        builder = param;
        return shareDialogFragment;
    }


    public static ShareDialogFragment getInstance() {
        return shareDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.diglot_share_layout, null);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    List<Integer> icons = new ArrayList<>();

    private void initView() {
        List<String> titles = new ArrayList<>();
        titles.add("QQ好友");
        titles.add("QQ空间");
        titles.add("微信好友");
        titles.add("朋友圈");
        titles.add("微博");
        icons.add(R.mipmap.icon_qq_friend);
        icons.add(R.mipmap.icon_qq_space);
        icons.add(R.mipmap.icon_wx_friend);
        icons.add(R.mipmap.icon_wx_space);
        icons.add(R.mipmap.icon_wb);
        gvShare.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), titles.size()));
        GvShareAdapter gvShareAdapter = new GvShareAdapter(getActivity().getApplication(), titles, R.layout.item_share_dialog);
        gvShare.setAdapter(gvShareAdapter);

        gvShareAdapter.setOnItemClickListener(new MyRyItemListener<Integer>() {
            @Override
            public void onItemSelect(Integer o) {
                List<Long> list = new ArrayList<>();
                list.add(Long.valueOf(builder.mDocId));
                list.add(Long.valueOf(App.getUserId()));
                L_.e(App.getUserId()+"--------"+builder.mDocId);
                String encrpty = Encrypt.shareCustomEncryptNumbers(list);
                String shareUrl = AppConfig.SHARE + encrpty;
                builder.setContext("10s帮你做大片");
                if (builder.mTitle==null){
                    builder.setTitle("我的作品");
                }
                L_.e("分享的URL"+shareUrl+"builder.mTitle"+builder.mTitle+"builder.mContext："+builder.mContext+"builder.mAvUrl"+builder.mAvUrl);
                switch (o) {
                    /**
                     *  因为图片路径加载失败问题
                     *
                     * 倒是分享失败
                     */
                    case 0:
                        ThirdShareUtils.initialize().shareMedia(getActivity(),SharePlatform.QQ, builder.mTitle, builder.mContext, shareUrl, builder.mAvUrl, shareListener);
                        break;
                    case 1:
                        ThirdShareUtils.initialize().shareMedia(getActivity(),SharePlatform.QZONE, builder.mTitle, builder.mContext, shareUrl, builder.mAvUrl, shareListener);
                        break;
                    case 2:
                        ThirdShareUtils.initialize().shareMedia(getActivity(),SharePlatform.WX, builder.mTitle, builder.mContext, shareUrl, builder.mAvUrl, shareListener);
                        break;
                    case 3:
                        ThirdShareUtils.initialize().shareMedia(getActivity(),SharePlatform.WX_TIMELINE, builder.mTitle, builder.mContext, shareUrl, builder.mAvUrl, shareListener);
                        break;
                    case 4:
                        ThirdShareUtils.initialize().shareMedia(getActivity(),SharePlatform.WEIBO, builder.mTitle, builder.mContext, shareUrl, builder.mAvUrl, shareListener);
                        break;
                    default:
                        break;
                }
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    ShareListener shareListener = new ShareListener() {
        @Override
        public void shareSuccess() {
            T_.showToastReal("分享成功");
        }

        @Override
        public void shareFailure(Exception e) {
            T_.showToastReal("分享失败" + e.getMessage());
        }

        @Override
        public void shareCancel() {
            T_.showToastReal("取消分享");
        }
    };

    public class GvShareAdapter extends BaseRefreshAdapter<String> {

        private List<String> mdata;

        public GvShareAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
            this.mdata = datas;
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, String var2, int var3) {
            TextView textView = var1.getView(R.id.id_tv_title);
            ImageView imageView = var1.getView(R.id.id_tg_icon);
            imageView.setImageResource(icons.get(var3));
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            layoutParams.width = UIUtils.WH()[0] / mdata.size();
            textView.setText(var2);
            var1.getView(R.id.ly_root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemSelect(var3);
                    }
                }
            });
        }
    }

    public void onShow(Activity activity) {
        show(activity.getFragmentManager().beginTransaction(), "ShareDialogFragment");
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //底部动画
        window.getAttributes().windowAnimations = R.style.shareDialogStyle;
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static class Builder {
        private String mTitle;
        private String mContext;
        private String mDocId;
        private String mAvUrl;


        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContext(String context) {
            mContext = context;
            return this;
        }

        public Builder setDocId(String docId) {
            mDocId = docId;
            return this;
        }

        public Builder setAvUrl(String avUrl) {
            mAvUrl = avUrl;
            return this;
        }
    }
}
