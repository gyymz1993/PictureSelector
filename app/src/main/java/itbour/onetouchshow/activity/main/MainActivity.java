package itbour.onetouchshow.activity.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.Gson;
import com.umeng.analytics.game.UMGameAgent;
import com.ys.uilibrary.tab.BottomTabView;
import com.ys.uilibrary.vp.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.ExceptionCrashHander;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.login.LoginActivity;
import itbour.onetouchshow.base.BaseApplication;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.fragment.design.DesignFragment;
import itbour.onetouchshow.fragment.home.HomeFragment;
import itbour.onetouchshow.fragment.me.MeFragment;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.observable.NetWorkObservable;
import itbour.onetouchshow.singlecase.InchingDataSingleCase;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.T_;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View, NetWorkObservable.NetWorkObserver {

    @BindView(R.id.viewPager)
    public NoScrollViewPager viewPager;
    @BindView(R.id.bottomTabView)
    public BottomTabView bottomTabView;

    FragmentPagerAdapter adapter;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
    private String[] titles = new String[]{"玩大片", "搞设计", "我的"};
    /**
     * 玩大片页面数据
     */
    List<HomeDetailslBean.TypesBean> playLargeList;
    /**
     * 设计页面数据
     */
    List<HomeDetailslBean.TypesBean> designList;
    Fragment homeFrg, design, groupFrg, meFrg;
    private int currentPageIndex = -1;

    @Override
    public void loadSucceed(String result) {
        HomeDetailslBean homeDetailslBean = new Gson().fromJson(result, HomeDetailslBean.class);
        List<List<Integer>> colorList = homeDetailslBean.getColorList();
        List<HomeDetailslBean.FontListBean> fontList = homeDetailslBean.getFontList();
        HomeDetailslBean.TextConfigBean textConfig = homeDetailslBean.getTextConfig();
        HomeDetailslBean.AppControl appControl = homeDetailslBean.getAppControl();
        HomeDetailslBean.LengthControl lengthControl = homeDetailslBean.getLengthControl();

        InchingDataSingleCase.INSTANCE.setFontColorList(colorList);
        InchingDataSingleCase.INSTANCE.setFontTypeList(fontList);
        InchingDataSingleCase.INSTANCE.setTextConfig(textConfig);
        InchingDataSingleCase.INSTANCE.setAppControl(appControl);
        InchingDataSingleCase.INSTANCE.setLengthControl(lengthControl);


        List<HomeDetailslBean.TypesBean> types = homeDetailslBean.getTypes();
        playLargeList = new ArrayList<>();
        designList = new ArrayList<>();
        if (types == null || types.size() == 0) {
            T_.showToastReal("初始化数据失败");
            return;
        }
        AppConst.mAllType.clear();
        AppConst.mSearchIds.clear();
        for (HomeDetailslBean.TypesBean typesBean : types) {
            //存储所有分类ID
            AppConst.mSearchIds.add(typesBean.getSt_id());
            AppConst.mAllType.put(typesBean.getSt_id(), typesBean.getName());
            //如果等于玩大片取值 否则就设计
            if (typesBean.getName().equals(AppConst.PLAY_LARGE)) {
                playLargeList.add(typesBean);
            } else {
                designList.add(typesBean);
            }
        }
        initParams();
        //L_.e(types.size() + "---", types.toString());
    }

    @Override
    public void loadFaild(String error) {
        T_.showToastReal(error);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        SpUtils.getInstance().saveBoolean("isFirst", false);
        mPresenter.getHomeDetailsData();
        BaseApplication.instance().registerNetWorkObserver(this);
        setImmersionBarBlack();


    }

    private void initButtomTab() {
        bottomTabView.setTabItemViews(getTabViews());
        if (getOnTabItemSelectListener() != null) {
            bottomTabView.setOnTabItemSelectListener(getOnTabItemSelectListener());
        }
        bottomTabView.setOnSecondSelectListener(new BottomTabView.OnSecondSelectListener() {
            @Override
            public void onSecondSelect(int position) {

            }
        });
    }


    protected List<Fragment> getFragments() {
        fragments = new ArrayList<>();
        fragments.add(homeFrg);
        fragments.add(design);
        //fragments.add(groupFrg);
        fragments.add(meFrg);
        return fragments;
    }


    @Override
    public void initView() {
        initButtomTab();
    }

    protected List<BottomTabView.TabItemView> getTabViews() {
        BottomTabView.TabItemView tabItemView1 = new BottomTabView.TabItemView(this, titles[0], R.color.tab_normal, R.color.tab_selected, R.drawable.tab_paly_video_normal, R.drawable.tab_paly_video_selector);
        tabItemViews.add(tabItemView1);
        BottomTabView.TabItemView tabItemView2 = new BottomTabView.TabItemView(this, titles[1], R.color.tab_normal, R.color.tab_selected, R.drawable.tab_paly_desgin_normal, R.drawable.tab_paly_desgin_seletor);
        tabItemViews.add(tabItemView2);

        //BottomTabView.TabItemView tabItemView3 = new BottomTabView.TabItemView(this, titles[2], R.color.tab_normal, R.color.tab_selected, R.drawable.tab_find_normal, R.drawable.tab_find_selector);
        //tabItemViews.add(tabItemView3);
        BottomTabView.TabItemView tabItemView4 = new BottomTabView.TabItemView(this, titles[2], R.color.tab_normal, R.color.tab_selected, R.drawable.tab_me_normal, R.drawable.tab_me_seletor);
        tabItemViews.add(tabItemView4);
        return tabItemViews;
    }

    public void initParams() {
        homeFrg = new HomeFragment(playLargeList);
        design = new DesignFragment(designList);
        //groupFrg = new HomeFragment(playLargeList);
        meFrg = new MeFragment();
        //设置ViewPager的缓存界面数,默认缓存为2
        viewPager.setOffscreenPageLimit(2);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }

            @Override
            public int getCount() {
                return getFragments().size();
            }
        };

        viewPager.setAdapter(adapter);
        if (getOnPageChangeListener() != null) {
            viewPager.addOnPageChangeListener(getOnPageChangeListener());
        }
    }


    protected ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomTabView.updatePosition(position);
                getToolBarView().setTitleText(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConst.CURRENT_PAGE != -1 && App.isLogin()) {
            bottomTabView.updatePosition(2);
            viewPager.setCurrentItem(2, false);
            AppConst.CURRENT_PAGE = -1;
        }
        if (ExceptionCrashHander.isException) {
            mPresenter.getHomeDetailsData();
        }
        // 集成基本统计分析,初始化 Session
        UMGameAgent.onResume(getApplication());

    }


    @Override
    public void onPause() {
        super.onPause();
        // //集成基本统计分析, 结束 Session
        UMGameAgent.onPause(getApplication());
    }


    public View getCenterView() {
        return null;
    }

    public BottomTabView.OnTabItemSelectListener getOnTabItemSelectListener() {
        return new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                if (position == 2) {
                    if (App.isLogin()) {
                        viewPager.setCurrentItem(position, true);
                    } else {
                        currentPageIndex = viewPager.getCurrentItem();
                        bottomTabView.updatePosition(currentPageIndex);
                        AppConst.CURRENT_PAGE = position;
                        openActivity(LoginActivity.class);
                    }
                } else {
                    viewPager.setCurrentItem(position, true);
                }
            }
        };
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    T_.showToastReal("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.instance().unregisterNetWorkObserver(this);
    }

    @Override
    public void onNetWorkStatusChange(boolean connected) {
    }

    @Override
    public void onNewWorkEnvironment(NetWorkObservable.NetStateChangeEvent.NetState netState) {

    }
}
