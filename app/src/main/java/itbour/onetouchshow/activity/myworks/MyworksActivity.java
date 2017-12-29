package itbour.onetouchshow.activity.myworks;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;
import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;
import com.ys.uilibrary.swip.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.IntentCode;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct.VerticalproductActivity;
import itbour.onetouchshow.activity.video.videopreview.VideoPreviewActivity;
import itbour.onetouchshow.base.BaseRefreshActivity;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.bean.MyWorksBean;
import itbour.onetouchshow.custom.RoundImageView;
import itbour.onetouchshow.evenbus.UpdateUserAction;
import itbour.onetouchshow.page.Page;
import itbour.onetouchshow.platform.DataPlatform;
import itbour.onetouchshow.platform.WorkPlatform;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

import static itbour.onetouchshow.evenbus.UpdateUserAction.TYPE_CREATEWORK;


/**
 * MVPPlugin
 * 我的作品
 */

public class MyworksActivity extends BaseRefreshActivity<MyworksContract.View, MyworksPresenter> implements MyworksContract.View {

    private List<MyWorksBean.ListBean> mdata;
    private Page page;
    //private WorkAdapter workAdapter;

    @Override
    public void loadSucceed(String result) {
        // 一定要调用，加载成功
        page.finishLoad(true);
        showContentView();
        MyWorksBean myWorksBean = new Gson().fromJson(result, MyWorksBean.class);
        List<MyWorksBean.ListBean> data = myWorksBean.getList();
        if (pullStatus == ON_LOAD) {
            mdata.addAll(data);
            xRefreshView.stopLoadMore();
        } else if (pullStatus == ON_REFRESH) {
            //mdata.clear();
            mdata = data;
            xRefreshView.stopRefresh();
        }
        if (myWorksBean != null && mPageIndex == myWorksBean.getTotalPage() - 1) {
            xRefreshView.setLoadComplete(true);
        } else {
            xRefreshView.setLoadComplete(false);
        }
        baseRefreshAdapter.notifyDataSetChanged(mdata);
        pullStatus = 0;
    }

    @Override
    public void loadFaild(String error) {
        // 一定要调用，加载失败
        page.finishLoad(false);
        if (error.equals(AppConst.NoData)) {
            showEmptyView();
        }

    }


    @Override
    protected void initTitle() {
        super.initTitle();
        setTitleText("我的作品");
    }


    @Override
    protected void onResume() {
        super.onResume();
        onLoadData();
    }

    @Override
    protected void initView() {
        super.initView();
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {


                UIUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullStatus = ON_REFRESH;
                        if (mPresenter != null) {
                            page.loadPage(true);
                        }
                    }

                }, AppConst.DELAYED_TIME);

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                UIUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullStatus = ON_LOAD;
                        if (mPresenter != null) {
                            page.loadPage(false);
                        }
                    }

                }, AppConst.DELAYED_TIME);

            }
        });

    }


    private void onLoadData() {
        page = new Page() {
            @Override
            public void load(int param1, int param2) {
                L_.e("load" + param1);
                mPageIndex = param1;
                mPresenter.loadMyWorksList(MyworksActivity.this);
            }
        };
        pullStatus = ON_REFRESH;
        if (mPresenter != null) {
            page.loadPage(true);
        }
    }

    @Override
    public BaseRefreshAdapter getBaseRefreshAdapter() {
        mdata = new ArrayList<>();
        return baseRefreshAdapter = new WorkAdapter(getApplicationContext(), mdata, R.layout.item_work_adapter);
    }

    @Override
    public View getHeadView() {
        return null;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        onLoadData();
    }

    @Override
    public void deleteSuccess(MyWorksBean.ListBean listBean) {
        if (mdata.size() == 1) {
            mdata.clear();
            pullStatus = ON_REFRESH;
            if (mPresenter != null) {
                page.loadPage(true);
            }
        } else {
            mdata.remove(listBean);
            baseRefreshAdapter.notifyDataSetChanged(mdata);
            if (mdata.size() == 0) {
                showEmptyView();
            } else {
                showContentView();
            }
        }
    }

    @Override
    public void reNameSuccess(MyWorksBean.ListBean listBean) {
        baseRefreshAdapter.notifyDataSetChanged(mdata);
    }

    public class WorkAdapter extends BaseRefreshAdapter<MyWorksBean.ListBean> {

        public WorkAdapter(Context context, List<MyWorksBean.ListBean> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, MyWorksBean.ListBean var2, int var3) {
            SwipeMenuLayout swipeMenuLayout = var1.getView(R.id.id_root_swp);
            RelativeLayout rootItem = var1.getView(R.id.id_root_ry);
            RoundImageView imageView = var1.getView(R.id.id_ig_av);
            TextView tvName = var1.getView(R.id.id_tv_name);
            TextView tvTag = var1.getView(R.id.id_tv_tag);
            TextView tvType = var1.getView(R.id.id_tv_type);
            TextView tvTime = var1.getView(R.id.id_tv_time);
            LinearLayout btnDelete = var1.getView(R.id.btnDelete_ly);
            LinearLayout btnReName = var1.getView(R.id.btnReName_ly);
            btnDelete.setOnClickListener(v -> {
                swipeMenuLayout.quickClose();
                mPresenter.deleteWork(MyworksActivity.this, var2);
                //作品制作完成  通知我的页面刷新作品
                UpdateUserAction updateUserAction = new UpdateUserAction();
                updateUserAction.setSubType(TYPE_CREATEWORK);
                EventBus.getDefault().post(updateUserAction);
                //DialogUtils.getInstance(MyworksActivity.this)
            });
            btnReName.setOnClickListener(v -> {
                swipeMenuLayout.quickClose();
                mPresenter.reNameWork(MyworksActivity.this, var2);
                //DialogUtils.getInstance(MyworksActivity.this)
            });

            tvType.setText(var2.getTypeDesc());
            tvTag.setText("");
            switch (var2.getWatermark()) {
                case 1:
                    //已付款watermark
                    switch (var2.getWatermarkPay()) {
                        case 1:
                            //有水印 重新发起去水印
                            tvTag.setText("去水印(已支付)");
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 0:
                    tvTag.setText("已去水印");
                    break;

            }

            ImageLoader.getInstance().showImage(var2.getThumb(), imageView.getImageView());
            tvName.setText(var2.getName());
            tvTime.setText(var2.getTime());
            rootItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int opType = var2.getOpType();
                    switch (opType) {
                        case DataPlatform.ORTYPE_VERTICAL:
                            Intent intent = new Intent(MyworksActivity.this, VerticalproductActivity.class);
                            intent.putExtra(AppConst.DOCID, var2.getId());
                            intent.putExtra(AppConst.OPTYPE, opType);
                            intent.putExtra(WorkPlatform.TYPE, IntentCode.getProductBundle(WorkPlatform.WOEK));
                            startActivity(intent);
                            break;
                        case DataPlatform.ORTYPE_VIDIO:
//                            Bundle bundle = new Bundle();
//                            bundle.putString(AppConst.DOCID, var2.getId() + "");
//                            bundle.putString(AppConst.MOUDLE, AppConst.W0RK);
                            // Bundle bundle = IntentCode.getBundle(var2.getId(), WorkPlatform.WOEK);
                            //openActivity(VideoplayActivity.class, bundle);
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConst.DOCID, var2.getId() + "");
                            // String docId = extras.getString(AppConst.DOCID);
                            bundle.putString(WorkPlatform.TYPE, WorkPlatform.W0RK);
                            openActivity(VideoPreviewActivity.class, bundle);
                            // openActivity(VideoProudctPreviewActivity.class, bundle);
                            break;
                        case DataPlatform.ORTYPE_PPT:
                            break;
                        default:
                            break;
                    }

                }
            });
        }
    }


}
