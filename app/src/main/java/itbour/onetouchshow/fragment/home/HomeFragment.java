package itbour.onetouchshow.fragment.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.uilibrary.rv.RecyclerTabLayout;
import com.ys.uilibrary.vp.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.search.SearchActivity;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.fragment.main.MainFragment;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * @author ymz
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter> implements HomeContract.View {


    @BindView(R.id.tv_homepage_hotsearch)
    TextView tvHomepageHotsearch;
    @BindView(R.id.rl_circular_shape)
    RelativeLayout rlCircularShape;
    private String[] titles = new String[]{"首页", "推荐", "全部", "会议", "一键生成", "宣传", "一键生成"};
    @BindView(R.id.id_vp_home)
    NoScrollViewPager mVpHome;
    @BindView(R.id.id_recyview)
    RecyclerTabLayout idRecyview;
    private List<Fragment> fragments = new ArrayList<>();
    List<HomeDetailslBean.TypesBean> mdata;

    //标题
    List<HomeDetailslBean.TypesBean.ChildrenBean> children = null;

    @SuppressLint("ValidFragment")
    public HomeFragment(List<HomeDetailslBean.TypesBean> playLargeList) {
        this.mdata = playLargeList;
        L_.e("size" + mdata.size());
    }

    public HomeFragment() {
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    private void setView() {
        if (mdata == null || mdata.size() == 0) {
            return;
        }
        for (HomeDetailslBean.TypesBean typesBean : mdata) {
            children = typesBean.getChildren();
            if (children == null || children.size() == 0) {
                T_.showToastReal("初始化数据失败");
                return;
            }
            for (int i = 0; i < children.size(); i++) {
                L_.e("children.get(i).toString()" + children.get(i).toString());
            }
        }

        if (children == null || children.size() == 0) {

            return;
        }

        /**首页*/
        for (int i = 0; i < children.size(); i++) {
            fragments.add(new MainFragment(children.get(i).getSetIds()));
        }
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        mVpHome.setNoScroll(false);
        mVpHome.setAdapter(pagerAdapter);
        mVpHome.setCurrentItem(0);
        mVpHome.setOffscreenPageLimit(0);

        idRecyview.setUpWithAdapter(new IndicatorAdapter(mVpHome));
        idRecyview.setPositionThreshold(0.5f);
        idRecyview.setIndicatorColor(UIUtils.getColor(R.color.tab_selected));
        idRecyview.setIndicatorHeight(UIUtils.dip2px(2));
        idRecyview.setRecycleViewScollto(true);

    }


    @OnClick(R.id.rl_circular_shape)
    public void onViewClicked() {
    }

    @OnClick({R.id.rl_circular_shape})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rl_circular_shape:
                //跳转到搜索界面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }


    public class IndicatorAdapter extends RecyclerTabLayout.Adapter<IndicatorAdapter.ViewHolder> {
        private PagerAdapter mAdapater;

        public IndicatorAdapter(ViewPager viewPager) {
            super(viewPager);
            mAdapater = (PagerAdapter) mViewPager.getAdapter();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_view_tab, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(children.get(position).getName());
            if (position == getCurrentIndicatorPosition()) {
                holder.text.setTextColor(UIUtils.getColor(R.color.tab_selected));
            } else {
                holder.text.setTextColor(0xffffffff);
            }
        }


        @Override
        public int getItemCount() {
            return mAdapater.getCount();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView text;

            ViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getViewPager().setCurrentItem(getAdapterPosition());
                    }
                });
            }
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public String getPageTitle(int position) {
            return children.get(position).getName();
        }


    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setView();
    }
}
