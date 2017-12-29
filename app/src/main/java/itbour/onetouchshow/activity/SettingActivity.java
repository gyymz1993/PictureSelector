package itbour.onetouchshow.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itbour.onetouchshow.App;
import itbour.onetouchshow.AppCache;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.userinfo.FeedBackActivity;
import itbour.onetouchshow.activity.web.CommWebActivity;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.MyRyItemListener;
import itbour.onetouchshow.custom.DialogUtils;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.FileUtils;
import itbour.onetouchshow.utils.GetFileSizeUtil;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/30.
 */

public class SettingActivity extends MVPBaseActivity {

    @BindView(R.id.id_login_out)
    TextView idLoginOut;
    private List<String> mdata = new ArrayList<>();

    SettingAdapter settingAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void initView() {
//        xRefreshView.stopLoadMore(false);
//        xRefreshView.setPullLoadEnable(false);
//        xRefreshView.setMoveForHorizontal(false);
//        xRefreshView.setPullRefreshEnable(false);

        initRecycleView();

    }

    public void initRecycleView() {
        mdata.add("意见反馈");
        mdata.add("关于我们");
        mdata.add("用户协议");
        mdata.add("清除缓存");
        settingAdapter = new SettingAdapter(getApplicationContext(), mdata, R.layout.item_me_frag);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int mSpace = UIUtils.dip2px(10);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childCount = parent.getChildCount();
                int pos = parent.getChildAdapterPosition(view);
                outRect.left = 0;
                outRect.top = 0;
                outRect.bottom = 0;
                if (pos == 3) {
                    outRect.top = mSpace;
                } else {
                    if (pos != (childCount - 1)) {
                        outRect.right = mSpace;
                    } else {
                        outRect.right = 0;
                    }
                }

            }

        });
        recyclerView.setAdapter(settingAdapter);

        settingAdapter.setOnItemClickListener(new MyRyItemListener<String>() {
            @Override
            public void onItemSelect(String o) {
                if (o.equals("意见反馈")) {
                    openActivity(FeedBackActivity.class);
                } else if (o.equals("关于我们")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConst.WEBTYPE, AppConst.TYPE_ABOUT_ME);
                    openActivity(CommWebActivity.class, bundle);
                } else if (o.equals("用户协议")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConst.WEBTYPE, AppConst.TYPE_USERAGREEMENT);
                    openActivity(CommWebActivity.class, bundle);
                } else if (o.equals("清除缓存")) {
                    long cacheSize = GetFileSizeUtil.getFileSize(new File(AppCache.getInstance().mAppDir));
                    // com.shuyu.gsyvideoplayer.utils.FileUtils.deleteFiles();
                    FileUtils.recursionDeleteFile(new File(AppCache.getInstance().mAppDir));
                    T_.showCustomToast("清除缓存成功");
                    //T_.showToastReal("缓存大小"+	GetFileSizeUtil.formatFileSize(cacheSize)+"M");
                    // oivSetting.setRightText("缓存大小"+	GetFileSizeUtil.formatFileSize(cacheSize)+"M");
                }
            }
        });
    }


    @Override
    protected void initTitle() {
        super.initTitle();
        setTitleText("设置");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.id_login_out)
    public void onViewClicked() {

        DialogUtils.getInstance(new DialogUtils.Builder().setTitle(" ")
                .setCancletext("取消").setCancleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtils.getInstance().dismiss();
                    }
                })
                .setConfirmText("确定").setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.getInstance().dismiss();
                        App.loginOut();
                        finish();
                        //openActivity(MainActivity.class);
                        //jumpToActivityAndClearTask(LoginActivity.class);
                    }
                }).setContext("确定退出").setPlatform(DialogUtils.DialogPlatform.TWO_BTN))
                .builder(this);
    }

    public class SettingAdapter extends BaseRefreshAdapter<String> {

        public SettingAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder baseRecyclerHolder, String var2, int var3) {
            TextView tv_name = baseRecyclerHolder.getView(R.id.tv_name);
            tv_name.setText(var2);
            baseRecyclerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemSelect(var2);
                    }
                }
            });

        }
    }

}
