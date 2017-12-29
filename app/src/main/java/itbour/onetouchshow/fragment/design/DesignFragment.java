package itbour.onetouchshow.fragment.design;


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
import butterknife.OnClick;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.search.SearchActivity;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.fragment.designlist.DesignlistFragment;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * <p>
 * 设计
 *
 * @author ymz
 */

@SuppressLint("ValidFragment")
public class DesignFragment extends MVPBaseFragment<DesignContract.View, DesignPresenter> implements DesignContract.View {


    //private String[] titles = new String[]{"首页", "推荐", "全部"};
    @BindView(R.id.id_vp_home)
    NoScrollViewPager mVpHome;
    @BindView(R.id.id_recyview)
    RecyclerTabLayout idRecyview;

    @BindView(R.id.rl_circular_shape)
    RelativeLayout rlCircularShape;

    private List<Fragment> desingTypeFragment = new ArrayList<>();
    public Fragment currentTypeFragment;
    List<HomeDetailslBean.TypesBean> mdata;


    @SuppressLint("ValidFragment")
    public DesignFragment(List<HomeDetailslBean.TypesBean> desingList) {
        this.mdata = desingList;
    }

    /**
     * 跳转视频界面后会暂停此页面  退出视频后系统恢复数据需要加入默认构造函数
     **/
    public DesignFragment() {
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_design;
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


    private void setView() {
        if (mdata == null || mdata.size() == 0) {
            return;
        }
        /**设计页面*/
        for (int i = 0; i < mdata.size(); i++) {
            DesignlistFragment designlistFragment = new DesignlistFragment(mdata.get(i).getChildren(), mdata.get(i).getChildren().get(0).getColumn(), mdata.get(i).getChildren().get(0).getSetIds());
            desingTypeFragment.add(designlistFragment);
        }
        currentTypeFragment = desingTypeFragment.get(0);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        mVpHome.setNoScroll(false);
        mVpHome.setAdapter(pagerAdapter);
        mVpHome.setCurrentItem(0);
        mVpHome.setOffscreenPageLimit(desingTypeFragment.size());

        idRecyview.setUpWithAdapter(new IndicatorAdapter(mVpHome));
        idRecyview.setPositionThreshold(0.5f);
        idRecyview.setIndicatorColor(UIUtils.getColor(R.color.tab_selected));
        idRecyview.setIndicatorHeight(UIUtils.dip2px(2));
        idRecyview.setRecycleViewScollto(true);

    }

    /**
     * 三个Tab
     */
    public class IndicatorAdapter extends RecyclerTabLayout.Adapter<IndicatorAdapter.ViewHolder> {
        private DesignFragment.PagerAdapter mAdapater;

        public IndicatorAdapter(ViewPager viewPager) {
            super(viewPager);
            mAdapater = (DesignFragment.PagerAdapter) mViewPager.getAdapter();
        }

        @Override
        public void setCurrentIndicatorPosition(int indicatorPosition) {
            super.setCurrentIndicatorPosition(indicatorPosition);
            currentTypeFragment = desingTypeFragment.get(indicatorPosition);
        }

        @Override
        public DesignFragment.IndicatorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_design_view_tab, parent, false);
            return new IndicatorAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(IndicatorAdapter.ViewHolder holder, int position) {
            holder.text.setText(mdata.get(position).getName());
            ViewGroup.LayoutParams layoutParams = holder.ry.getLayoutParams();
            // L_.e("WIDTH="+holder.text.getMeasuredWidth());
            if (mdata.size() > 0 && mdata.size() <= 5) {
                layoutParams.width = UIUtils.WH()[0] / mdata.size();
            }
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

            RelativeLayout ry;

            ViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.text);
                ry = itemView.findViewById(R.id.id_ry_root);
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
            Fragment fragment = desingTypeFragment.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return desingTypeFragment.size();
        }

        @Override
        public String getPageTitle(int position) {
            return mdata.get(position).getName();
        }


    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setView();
    }


}
