package itbour.onetouchshow.fragment.designlist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
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
import itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel.VerticalmodelActivity;
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
 * @author ymz
 */

@SuppressLint("ValidFragment")
public class DesignlistFragment extends BaseRefreshFragment<DesignlistContract.View, DesignlistPresenter> implements DesignlistContract.View {

    BaseRefreshAdapter baseRefreshAdapter;
    /**
     * 根据ID 获取数据
     */
    private List<Integer> mids;
    /**
     * 数据源
     */
    List<DesignListBean.ListBean> data = new ArrayList<>();
    /**
     * 后台数据
     */
    DesignListBean designListBean;

    public DesignlistFragment(List<Integer> ids) {
        this.mids = ids;
        EventBus.getDefault().register(this);
    }

    public DesignlistFragment() {
    }

    public void onfresh() {
        pullStatus = ON_REFRESH;
        mPageIndex = 0;
        if (mPresenter != null) {
            mPresenter.getDetailsList(this, mids);
        }
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
        //L_.e("--------", result);
        designListBean = new Gson().fromJson(result, DesignListBean.class);
        List<DesignListBean.ListBean> resultData = designListBean.getList();
        DetailsAction detailsAction=new DetailsAction("design"+DesignlistFragment.this,resultData);
        ImagePorviderService.startService(getContext(),detailsAction);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(DetailsAction detailsAction) {
        //避免多个Fragment同时执行刷新
        if (!detailsAction.getSubType().equals("design"+DesignlistFragment.this)){
            return;
        }
        if (designListBean==null){
            endNetRequse(detailsAction.getData(), 0);
        }else {
            endNetRequse(detailsAction.getData(), designListBean.getTotalPage());
        }

    }

    public void endNetRequse(List<DesignListBean.ListBean> mdata, int totalPage) {
        showContentView();
        if (pullStatus == ON_LOAD) {
            data.addAll(mdata);
        } else if (pullStatus == ON_REFRESH) {
            data = mdata;
            xRefreshView.stopRefresh();
            xRefreshView.setLoadComplete(false);
        }
        L_.e("当前页---"+mPageIndex+"/"+totalPage);
        if (mPageIndex >= totalPage-1) {
                /*再无数据*/
            xRefreshView.setLoadComplete(true);
        } else {
            xRefreshView.stopLoadMore(true);
        }
        baseRefreshAdapter.notifyDataSetChanged(data);
        pullStatus = 0;
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
        //recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        baseRefreshAdapter.setHeaderView(getHeadView(), recyclerView);
        recyclerView.setAdapter(baseRefreshAdapter);
        baseRefreshAdapter.setOnItemClickListener(new MyRyItemListener() {
            @Override
            public void onItemSelect(Object o) {

//                openActivity(VideoListActivity.class);
                DesignListBean.ListBean o1 = (DesignListBean.ListBean) o;
                int opType = o1.getOpType();
                switch (opType) {
                    case AppConst.VERTICAL_ORTYPE:
                        Intent itToVertical = new Intent(getActivity(), VerticalmodelActivity.class);
                        itToVertical.putExtra("pageContentList", new Gson().toJson(data));
                        getActivity().startActivity(itToVertical);

                        break;
                    case AppConst.VIDIO_ORTYPE:
                        break;
                    case AppConst.PPT_ORTYPE:
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public BaseRefreshAdapter getBaseRefreshAdapter() {
        return baseRefreshAdapter = new DesignlistFragment.MainAdapter(getContext(), data, R.layout.itme_main_frag);
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

        public MainAdapter(Context context, List<DesignListBean.ListBean> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
