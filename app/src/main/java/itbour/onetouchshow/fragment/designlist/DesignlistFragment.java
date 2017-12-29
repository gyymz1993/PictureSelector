package itbour.onetouchshow.fragment.designlist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.ys.uilibrary.rv.AutoRecycleLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel.VerticalmodelActivity;
import itbour.onetouchshow.base.ABaseRefreshFragment;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.MyRyItemListener;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.evenbus.DetailsAction;
import itbour.onetouchshow.page.Page;
import itbour.onetouchshow.platform.DataPlatform;
import itbour.onetouchshow.rvmanager.FastScrollManger;
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
public class DesignlistFragment extends ABaseRefreshFragment<DesignlistContract.View, DesignlistPresenter> implements DesignlistContract.View {

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

    private int mColumn;

    private Page page;

    @BindView(R.id.id_recyview_type)
    AutoRecycleLayout idRvType;
    List<HomeDetailslBean.TypesBean.ChildrenBean> mTypeData;
    private XRefreshViewFooter xRefreshViewFooter;

    //StaggeredGridLayoutManager recyclerViewLayoutManager;

    public DesignlistFragment(List<HomeDetailslBean.TypesBean.ChildrenBean> data, int column, List<Integer> ids) {
        this.mids = ids;
        this.mColumn = column;
        this.mTypeData = data;
        EventBus.getDefault().register(this);
    }

    public DesignlistFragment() {
    }


    public void onLoadData() {
        page = new Page() {
            @Override
            public void load(int param1, int param2) {
                //  L_.e("load"+param1);
                mPageIndex = param1;

                mPresenter.getDetailsList(DesignlistFragment.this, mids);
            }
        };
        pullStatus = ON_REFRESH;
        page.loadPage(true);
    }

    public void onfreshData(List<Integer> mids) {
        this.mids = mids;
        onLoadData();
    }


    @Override
    public void loadSucceed(String result) {
        page.finishLoad(true);
        designListBean = new Gson().fromJson(result, DesignListBean.class);
        List<DesignListBean.ListBean> resultData = designListBean.getList();
        DetailsAction detailsAction = new DetailsAction("design" + DesignlistFragment.this, resultData);
        ImagePorviderService.startService(getContext(), detailsAction);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(DetailsAction detailsAction) {
        //避免多个Fragment同时执行刷新
        if (!detailsAction.getSubType().equals("design" + DesignlistFragment.this)) {
            return;
        }
        endNetRequse(detailsAction.getData());
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
            L_.e("没有更多数据了");
        }

        baseRefreshAdapter.notifyDataSetChanged(mdata);
        if (pullStatus == ON_REFRESH) {
            recyclerView.getLayoutManager().scrollToPosition(0);
        }
        pullStatus = 0;

    }


    @Override
    public void loadFaild(String error) {
        page.finishLoad(true);
        //showNoNetworkView();
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
    protected int getChirdLayoutId() {
        return R.layout.fragment_designlist;
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
        if (xRefreshViewFooter==null){
            xRefreshViewFooter = new XRefreshViewFooter(getContext());
        }
        baseRefreshAdapter.setCustomLoadMoreView(xRefreshViewFooter);
        recyclerView.setHasFixedSize(true);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        FastScrollManger recyclerViewLayoutManager = new FastScrollManger(mColumn, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

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
                    case DataPlatform.ORTYPE_VERTICAL:
                        Intent itToVertical = new Intent(getActivity(), VerticalmodelActivity.class);
                        int postion = mdata.indexOf(o1);
                        itToVertical.putExtra("pageContentList", new Gson().toJson(mdata));
                        itToVertical.putExtra("postion", postion);
                        getActivity().startActivity(itToVertical);
                        break;
                    case DataPlatform.ORTYPE_VIDIO:
                        break;
                    case DataPlatform.ORTYPE_PPT:
                        break;
                    default:
                        break;
                }

            }
        });

        mImageViewRebackTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewLayoutManager.scrollToPositionWithOffset(0, 0);
                mImageViewRebackTop.setVisibility(View.INVISIBLE);
            }
        });

        recyclerView.addOnScrollListener(new MyRecyclerViewScrollListener());
    }


    //滑动监听
    private class MyRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            FastScrollManger manager = (FastScrollManger) recyclerView.getLayoutManager();
            manager.invalidateSpanAssignments();
            // 当不滚动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 判断是否滚动超过一屏
                int lastBottomCompletelyEmergedItemPosition = manager.findLastBottomCompletelyEmergedItemPosition();
                if (lastBottomCompletelyEmergedItemPosition > 0) {
                    mImageViewRebackTop.setVisibility(View.VISIBLE);
                } else {
                    mImageViewRebackTop.setVisibility(View.INVISIBLE);
                }
            } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                //mImageViewRebackTop.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public BaseRefreshAdapter getBaseRefreshAdapter() {
        return baseRefreshAdapter = new DesignlistFragment.MainAdapter(getContext(), mdata, R.layout.itme_main_frag);
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
        initType();
    }


    public void initType() {
        //二级小分类
        //设置布局管理器
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        // linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //idRvType.setLayoutManager(linearLayoutManager);

        //二级小分类
        if (mTypeData == null || mTypeData.size() == 0) {
            idRvType.setVisibility(View.GONE);
            return;
        }
        //  List<HomeDetailslBean.TypesBean.ChildrenBean> children = mdata.get(0).getChildren();
        idRvType.setHasFixedSize(true);
        TypeAdapter typeAdapter = new TypeAdapter(getContext(), mTypeData, R.layout.item_design_type_adapter);
        idRvType.setAdapter(typeAdapter);
        // idRvType.setAutoSelectionMode(false);
        typeAdapter.setOnItemClickListener(new MyRyItemListener<HomeDetailslBean.TypesBean.ChildrenBean>() {
            @Override
            public void onItemSelect(HomeDetailslBean.TypesBean.ChildrenBean bean) {
                L_.e("请求数据ID" + bean.getSetIds().toString());
                onfreshData(bean.getSetIds());
            }
        });
    }


    private class TypeAdapter extends BaseRefreshAdapter<HomeDetailslBean.TypesBean.ChildrenBean> {

        private int selectedPos = 0;

        public TypeAdapter(Context context, List<HomeDetailslBean.TypesBean.ChildrenBean> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
            selectedPos = 0;
        }

        @Override
        public void notifyDataSetChanged(List<HomeDetailslBean.TypesBean.ChildrenBean> list) {
            selectedPos = 0;
            super.notifyDataSetChanged(list);
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, HomeDetailslBean.TypesBean.ChildrenBean childrenBean, int position) {
            TextView textView = var1.getView(R.id.id_tv_type_name);
            textView.setText(childrenBean.getName());
            if (selectedPos == position) {
                textView.setTextColor(UIUtils.getColor(R.color.tab_selected));
            } else {
                textView.setTextColor(UIUtils.getColor("#FFFFFFFF"));
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        selectedPos = position;
                        idRvType.scrollToTab(selectedPos);
                        onItemClickListener.onItemSelect(childrenBean);
                        notifyDataSetChanged();
                    }
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
            ImageView imageView = viewHolder.getView(R.id.id_ig);
            TextView tvName = viewHolder.getView(R.id.id_tv_name);
            TextView tvCount = viewHolder.getView(R.id.id_tv_count);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = listBean.getW();
            layoutParams.height = listBean.getH();
            //imageView.setInitSize(listBean.getW(), listBean.getH());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
