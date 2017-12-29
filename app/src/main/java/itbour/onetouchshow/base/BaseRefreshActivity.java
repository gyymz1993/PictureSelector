package itbour.onetouchshow.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;

import butterknife.BindView;
import itbour.onetouchshow.R;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.mvp.BaseView;
import itbour.onetouchshow.mvp.MVPBaseActivity;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/7/21 15:14
 */
public abstract class BaseRefreshActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseActivity<V, T> implements BaseView {

    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.xrefreshview)
    public XRefreshView xRefreshView;
    public int mPageIndex = 0;
    public static final int ON_REFRESH = 1;
    public static final int ON_LOAD = 2;
    public static final int NO_MORE = 3;
    public int pullStatus;
    public BaseRefreshAdapter baseRefreshAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_refresh_fragment;
    }

    @Override
    protected void initView() {
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setAutoLoadMore(true);
        //xRefreshView.setPinnedTime(1000);
        xRefreshView.stopLoadMore(false);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setMoveForHorizontal(true);
        CustomGifHeader header = new CustomGifHeader(getApplicationContext());
        xRefreshView.setCustomHeaderView(header);
        initRecycleView();

    }

    public void initRecycleView() {
        baseRefreshAdapter = getBaseRefreshAdapter();
        if (baseRefreshAdapter == null) {
            return;
        }
        baseRefreshAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
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
