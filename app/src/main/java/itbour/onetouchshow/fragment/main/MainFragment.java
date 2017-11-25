package itbour.onetouchshow.fragment.main;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.videoplay.VideoListActivity;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.BaseRefreshFragment;
import itbour.onetouchshow.base.MyRyItemListener;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.custom.ScaleImageView;
import itbour.onetouchshow.evenbus.DetailsAction;
import itbour.onetouchshow.service.ImagePorviderService;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 *
 * @author onetouch
 */

@SuppressLint("ValidFragment")
public class MainFragment extends BaseRefreshFragment<MainContract.View, MainPresenter> implements MainContract.View {

    BaseRefreshAdapter baseRefreshAdapter;

    /**
     * 根据ID 获取数据
     */
    private List<Integer> mids;
    /**
     * 数据源
     */
    List<DesignListBean.ListBean> mdata = new ArrayList<>();
    /**
     * 后台数据
     */
    DesignListBean designListBean;

    public MainFragment(List<Integer> ids) {
        this.mids = ids;
        EventBus.getDefault().register(this);
    }

    public void onLoadData() {
        //showLoadingView();
        if (pullStatus == ON_REFRESH) {
            mPageIndex = 0;
        } else if (mPageIndex < designListBean.getTotalPage()) {
            mPageIndex++;
        }
        mPresenter.getDetailsList(this, mids);
    }


    @Override
    public void loadSucceed(String result) {
        // L_.e("--------",result);
        designListBean = new Gson().fromJson(result, DesignListBean.class);
        List<DesignListBean.ListBean> resultData = designListBean.getList();
        DetailsAction detailsAction=new DetailsAction("main"+MainFragment.this,resultData);
        ImagePorviderService.startService(getContext(),detailsAction);
    }

    public void endNetRequse(List<DesignListBean.ListBean> data, int totalPage) {
        showContentView();
        if (pullStatus == ON_LOAD) {
            mdata.addAll(mdata);
        } else if (pullStatus == ON_REFRESH) {
            mdata = data;
            xRefreshView.stopRefresh();
            xRefreshView.setLoadComplete(false);
        }
        L_.e("当前页码"+mPageIndex+"/"+(totalPage-1));
        if (mPageIndex >= totalPage-1) {
            /*再无数据*/
            xRefreshView.setLoadComplete(true);
        } else {
            xRefreshView.stopLoadMore(true);
        }
        baseRefreshAdapter.notifyDataSetChanged(data);
        pullStatus = 0;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(DetailsAction detailsAction) {
        if (!detailsAction.getSubType().equals("main"+MainFragment.this)){
            return;
        }
        if (designListBean==null){
            endNetRequse(detailsAction.getData(), 0);
        }else {
            endNetRequse(detailsAction.getData(), designListBean.getTotalPage());
        }
    }

    @Override
    public void loadFaild(String error) {
        showNoNetworkView();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        L_.e("mPresenter", mPresenter + "");
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullStatus = ON_REFRESH;
                if (mPresenter != null && mids != null && mids.size() != 0) {
                    onLoadData();
                }
            }
        }, AppConst.DELAYED_TIME);
    }

    @Override
    protected void initView() {
        super.initView();
        pullStatus = ON_REFRESH;
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                pullStatus = ON_REFRESH;
                if (mPresenter != null) {
                    onLoadData();
                }
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                UIUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullStatus = ON_LOAD;
                        if (mPresenter != null) {
                            onLoadData();
                        }
                    }
                }, AppConst.DELAYED_TIME);

            }
        });

    }

    @Override
    public void initRecycleView() {
        baseRefreshAdapter = getBaseRefreshAdapter();
        if (baseRefreshAdapter == null) {
            return;
        }
        baseRefreshAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        recyclerView.setHasFixedSize(true);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        baseRefreshAdapter.setHeaderView(getHeadView(), recyclerView);
        recyclerView.setAdapter(baseRefreshAdapter);
        baseRefreshAdapter.setOnItemClickListener(new MyRyItemListener() {
            @Override
            public void onItemSelect(Object o) {
                openActivity(VideoListActivity.class);
            }
        });
    }

    @Override
    public BaseRefreshAdapter getBaseRefreshAdapter() {
        return baseRefreshAdapter = new MainAdapter(getContext(), mdata, R.layout.itme_main_frag);
    }

    @Override
    public View getHeadView() {
        return null;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (isFirstCreate){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showLoadingView();
                }
            });
        }
    }

    public class MainAdapter extends BaseRefreshAdapter<DesignListBean.ListBean> {

        public MainAdapter(Context context, List<DesignListBean.ListBean> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder viewHolder, DesignListBean.ListBean listBean, int position) {
            ScaleImageView imageView = viewHolder.getView(R.id.id_ig);
            TextView tvName = viewHolder.getView(R.id.id_tv_name);
            TextView tvCount = viewHolder.getView(R.id.id_tv_count);
            imageView.setInitSize(listBean.getW(),listBean.getH());
            ImageLoader.getInstance().showImage(listBean.getThumb(),imageView);
            tvName.setText(listBean.getName());
            tvCount.setText(listBean.getUseCounts() + "人使用");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemSelect(listBean);
                    }
                }
            });
        }
    }
}
