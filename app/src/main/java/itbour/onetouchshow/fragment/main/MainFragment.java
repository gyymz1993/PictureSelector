package itbour.onetouchshow.fragment.main;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import itbour.onetouchshow.IntentCode;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.video.videoplay.VideoplayActivity1;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.BaseRefreshFragment;
import itbour.onetouchshow.base.MyRyItemListener;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.bean.ModelVideoBean;
import itbour.onetouchshow.evenbus.DetailsAction;
import itbour.onetouchshow.page.Page;
import itbour.onetouchshow.platform.WorkPlatform;
import itbour.onetouchshow.service.ImagePorviderService;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 *
 * @author onetouch
 *         <p>
 *         设计
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
    Page page;


    public MainFragment(List<Integer> ids) {
        this.mids = ids;
        EventBus.getDefault().register(this);
    }

    public MainFragment() {
        if (designListBean != null && mPageIndex >= designListBean.getTotalPage() - 1) {
            xRefreshView.setLoadComplete(true);
            return;
        }
    }

    public void onLoadData() {
        page = new Page() {
            @Override
            public void load(int param1, int param2) {
                //L_.e("load"+param1);
                mPageIndex = param1;
                if (designListBean != null && mPageIndex >= designListBean.getTotalPage() - 1) {
                    xRefreshView.setLoadComplete(true);
                    return;
                }
                mPresenter.getDetailsList(MainFragment.this, mids);
            }
        };
        pullStatus = ON_REFRESH;
        page.loadPage(true);
    }


    @Override
    public void loadSucceed(String result) {
        page.finishLoad(true);
        designListBean = new Gson().fromJson(result, DesignListBean.class);
        List<DesignListBean.ListBean> resultData = designListBean.getList();
        DetailsAction detailsAction = new DetailsAction("main" + MainFragment.this, resultData);
        ImagePorviderService.startService(getContext(), detailsAction);
    }

    public void endNetRequse(List<DesignListBean.ListBean> data) {
        showContentView();
        if (pullStatus == ON_LOAD) {
            mdata.addAll(data);
            xRefreshView.stopLoadMore();
        } else if (pullStatus == ON_REFRESH) {
            mdata = data;
            xRefreshView.stopRefresh();
            xRefreshView.setLoadComplete(false);
        }
        if (designListBean != null && mPageIndex >= designListBean.getTotalPage() - 1) {
            xRefreshView.setLoadComplete(true);
        }
        baseRefreshAdapter.notifyDataSetChanged(mdata);
        pullStatus = 0;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(DetailsAction detailsAction) {
        if (!detailsAction.getSubType().equals("main" + MainFragment.this)) {
            return;
        }
        if (designListBean == null) {
            endNetRequse(detailsAction.getData());
        } else {
            endNetRequse(detailsAction.getData());
        }
    }

    @Override
    public void loadFaild(String error) {
        page.finishLoad(true);
        // showErrorView();
        showEmptyView(R.mipmap.icon_no_network);


    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        // L_.e("mPresenter", mPresenter + "");
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadData();
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
                    page.loadPage(true);
                }
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
                DesignListBean.ListBean listBean = (DesignListBean.ListBean) o;
//                Bundle bundle = new Bundle();
//                bundle.putString(AppConst.DOCID, listBean.getId() + "");
//                bundle.putString(WorkPlatform.TYPE, WorkPlatform.getWorkPlatform(WorkPlatform.MODULE));
                Bundle bundle = IntentCode.getBundle(listBean.getId(), WorkPlatform.MODULE);
                openActivity(VideoplayActivity1.class, bundle);
                // JumpUtils.goToVideoPlayer(getActivity(),recyclerView,var2);
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
        if (isFirstCreate) {
            showLoadingView();
        }
    }


    @Override
    public void loadPageInfoSuccess(String result) {
        L_.e(result);
        ModelVideoBean modelPreviewInfoBean = new Gson().fromJson(result, ModelVideoBean.class);
        //JumpUtils.goToVideoPlayer(getActivity(),recyclerView,modelPreviewInfoBean);
        // Intent intent = new Intent(getContext(), VideoplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", modelPreviewInfoBean);
        //  intent.putExtras(bundle);
        openActivity(VideoplayActivity1.class, bundle);
    }

    @Override
    public void loadPageInfoFail(String result) {

    }

    public class MainAdapter extends BaseRefreshAdapter<DesignListBean.ListBean> {

        public MainAdapter(Context context, List<DesignListBean.ListBean> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder viewHolder, DesignListBean.ListBean listBean, int position) {
            ImageView imageView = viewHolder.getView(R.id.id_ig);
            TextView tvName = viewHolder.getView(R.id.id_tv_name);
            TextView tvCount = viewHolder.getView(R.id.id_tv_count);
            // imageView.setInitSize(listBean.getW(),listBean.getH());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = listBean.getW();
            layoutParams.height = listBean.getH();
            ImageLoader.getInstance().showImage(listBean.getThumb(), imageView);
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
