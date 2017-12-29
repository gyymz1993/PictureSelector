package itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ys.uilibrary.vp.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.login.LoginActivity;
import itbour.onetouchshow.activity.video.commoninching.CommoninchingActivity;
import itbour.onetouchshow.base.IntentCode;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.fragment.modelpreview.ModelpreviewFragment;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.BroadcastAction;
import itbour.onetouchshow.utils.CommonUtils;
import itbour.onetouchshow.utils.FinshBroadcast;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * 模板制作
 */

public class VerticalmodelActivity extends MVPBaseActivity<VerticalmodelContract.View, VerticalmodelPresenter> implements VerticalmodelContract.View {

    @BindView(R.id.model_viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.rl_next)
    RelativeLayout rlNext;

    private ArrayList<DesignListBean.ListBean> listBeans;
    private PagerAdapter pagerAdapter;

    @Override
    protected void initTitle() {
        super.initTitle();
        registerExitReceiver();
        //after获取当前的title
        setTitleText("模板标题");
        //在after中判断当前是否收藏

        getToolBarView().setleftImageResource(getResources().getDrawable(R.drawable.back))
                .setRightImageResource(getResources().getDrawable(R.mipmap.video_collect_normal)).setRightIocnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelpreviewFragment currentFragment = pagerAdapter.getCurrentFragment();
                Boolean isCollect = currentFragment.isCollect;
                int id = currentFragment.listBean.getId();
                if (App.isLogin()){
                    mPresenter.collectOrCancalCollectThisModel(isCollect, id);
                }else {
                    openActivity(LoginActivity.class);
                }
            }
        }).setRightImageVisible();
    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activty_verticalmodel;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setImmersionBarBlack();

        String pageContentList = getIntent().getStringExtra("pageContentList");
        int postion = getIntent().getIntExtra("postion", 0);
        listBeans = CommonUtils.jsonToArrayList(pageContentList, DesignListBean.ListBean.class);
        L_.i("listBeans===" + listBeans);
        //拿到数据后构建可以滑动的viewpager adapter
        initFragment(postion);

    }

    private List<Fragment> scrollFragment = new ArrayList<>();

    private void initFragment(int postion) {
        for (int i = 0; i < listBeans.size(); i++) {
            DesignListBean.ListBean listBean = listBeans.get(i);
            scrollFragment.add(new ModelpreviewFragment(VerticalmodelActivity.this, listBean));
        }
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewpager.setNoScroll(false);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(postion);
        viewpager.setOffscreenPageLimit(1);


    }

    @Override
    public void isCollect() {
        getToolBarView().setRightImageResource(getResources().getDrawable(R.mipmap.video_collect_checked));
        if (pagerAdapter != null) {
            pagerAdapter.mCurrentFragment.isCollect = true;
        }
    }

    @Override
    public void unCollect() {
        getToolBarView().setRightImageResource(getResources().getDrawable(R.mipmap.video_collect_normal));
        pagerAdapter.mCurrentFragment.isCollect = false;

    }

    @Override
    public void nowFragmentVisable(DesignListBean.ListBean data) {
        if (App.isLogin()) {
            mPresenter.loadThisIsCollect(data);
        }
        setTitleText(data.getName());
    }

    @Override
    public void collectSuccess(String text) {
        // dismissProgressDialog();
        T_.showCustomToast(text,true);
    }

    @Override
    public void collectFaild() {
        // dismissProgressDialog();
        T_.showCustomToast("收藏失败",true);
    }


    @OnClick({R.id.rl_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //拿到当前的选中 fragment是否显示然后去执行收藏/取消收藏标记
            case R.id.rl_next:
                //跳转到公共微调界面
                if (App.isLogin()) {
                    Intent intent = new Intent(VerticalmodelActivity.this, CommoninchingActivity.class);
                    DesignListBean.ListBean listBean = pagerAdapter.getCurrentFragment().listBean;
                    int id = listBean.getId();
                    intent.putExtra(IntentCode.INCHING_TYPE, IntentCode.SUB_TYPE_MODULE);
                    intent.putExtra(IntentCode.TAG, IntentCode.INTENT_VERTICAL_MODEL);
                    intent.putExtra(AppConst.ID, id);
                    startActivity(intent);
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {
        private ModelpreviewFragment mCurrentFragment;

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = scrollFragment.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return scrollFragment.size();
        }

        @Override
        public String getPageTitle(int position) {
            return listBeans.get(position).getName();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (ModelpreviewFragment) object;
            super.setPrimaryItem(container, position, object);
        }


        public ModelpreviewFragment getCurrentFragment() {
            return mCurrentFragment;
        }
    }


    //这是用于退出三段式逻辑的广播
    private FinshBroadcast quitThreeLogicBroadcast = new FinshBroadcast();
    //onCreate调用
    private void registerExitReceiver() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(BroadcastAction.EXIT_THREE_LOGIC);
        registerReceiver(quitThreeLogicBroadcast, exitFilter);
    }
    //onDestory反注册
    private void unRegisterExitReceiver() {

        unregisterReceiver(quitThreeLogicBroadcast);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }
}
