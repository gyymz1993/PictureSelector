package itbour.onetouchshow.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;


import butterknife.BindView;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.mvp.BaseView;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.R;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/7/21 15:14
 */
public abstract class BaseRefreshFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseFragment<V, T> implements BaseView {

    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.xrefreshview)
    public XRefreshView xRefreshView;
    @BindView(R.id.id_back_top)
    public ImageView mImageViewRebackTop;

    public int mPageIndex = 0;
    public static final int ON_REFRESH = 1;
    public static final int ON_LOAD = 2;
    public int pullStatus;
    public BaseRefreshAdapter baseRefreshAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_refresh_fragment;
    }

    @Override
    protected void initView() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        //refreshView.setMoveHeadWhenDisablePullRefresh(true);
        // 设置时候可以自动刷新
        //xRefreshView.setAutoRefresh(true);
        xRefreshView.setAutoLoadMore(true);
        //xRefreshView.setPinnedTime(1000);
        xRefreshView.stopLoadMore(false);
        xRefreshView.setMoveForHorizontal(true);
        CustomGifHeader header = new CustomGifHeader(getContext());
        xRefreshView.setCustomHeaderView(header);
        initRecycleView();
        mImageViewRebackTop.setVisibility(View.INVISIBLE);

    }

    public void initRecycleView() {
        baseRefreshAdapter = getBaseRefreshAdapter();
        if (baseRefreshAdapter == null) {
            return;
        }
        baseRefreshAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        baseRefreshAdapter.setHeaderView(getHeadView(), recyclerView);
        recyclerView.setAdapter(baseRefreshAdapter);

    }


    public abstract BaseRefreshAdapter getBaseRefreshAdapter();

    public abstract View getHeadView();

}
