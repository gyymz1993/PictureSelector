package itbour.onetouchshow.fragment.design;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;
import com.ys.uilibrary.rv.RecyclerTabLayout;
import com.ys.uilibrary.vp.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import itbour.onetouchshow.R;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.MyRyItemListener;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.fragment.designlist.DesignlistFragment;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.utils.L_;
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
    @BindView(R.id.id_recyview_type)
    RecyclerView idRvType;

    private List<Fragment> desingTypeFragment = new ArrayList<>();
    public Fragment currentTypeFragment;

    List<HomeDetailslBean.TypesBean> mdata;

    //标题
    List<HomeDetailslBean.TypesBean.ChildrenBean> children = null;

    private TypeAdapter typeAdapter;


    @SuppressLint("ValidFragment")
    public DesignFragment(List<HomeDetailslBean.TypesBean> desingList) {
        this.mdata = desingList;
        L_.e("size" + mdata.size());
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


    private void setView() {


        //二级小分类
        if (mdata == null || mdata.size() == 0) {
            idRvType.setVisibility(View.GONE);
            return;
        }
        children = mdata.get(0).getChildren();
        idRvType.setHasFixedSize(true);

        /**设计页面*/
        for (int i = 0; i < mdata.size(); i++) {
            DesignlistFragment designlistFragment = new DesignlistFragment(children.get(i).getSetIds());
            desingTypeFragment.add(designlistFragment);
        }

        currentTypeFragment = desingTypeFragment.get(0);
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
        //二级小分类
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        idRvType.setLayoutManager(linearLayoutManager);
        typeAdapter = new TypeAdapter(getContext(), children, R.layout.item_design_type_adapter);
        idRvType.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new MyRyItemListener<HomeDetailslBean.TypesBean.ChildrenBean>() {
            @Override
            public void onItemSelect(HomeDetailslBean.TypesBean.ChildrenBean bean) {
                if (currentTypeFragment != null) {
                    DesignlistFragment designlistFragment = (DesignlistFragment) currentTypeFragment;
                    designlistFragment.onfresh();
                }
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
                        notifyDataSetChanged();
                        onItemClickListener.onItemSelect(childrenBean);
                    }
                }
            });
        }
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
            if (children == null || children.size() == 0) {
                return;
            }
            currentTypeFragment = desingTypeFragment.get(indicatorPosition);
            children = mdata.get(indicatorPosition).getChildren();
            if (typeAdapter != null) {
                typeAdapter.notifyDataSetChanged(children);
            }
            //T_.showToastReal("点击"+indicatorPosition);
        }

        @Override
        public DesignFragment.IndicatorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_design_view_tab, parent, false);
            return new IndicatorAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(IndicatorAdapter.ViewHolder holder, int position) {
            holder.text.setText(mdata.get(position).getName());
            ViewGroup.LayoutParams layoutParams = holder.text.getLayoutParams();
            // L_.e("WIDTH="+holder.text.getMeasuredWidth());
            layoutParams.width = UIUtils.WH()[0] / 3;
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
