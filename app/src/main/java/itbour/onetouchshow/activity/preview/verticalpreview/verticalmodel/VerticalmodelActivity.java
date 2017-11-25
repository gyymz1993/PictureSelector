package itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel;


import android.content.Intent;
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
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.video.commoninching.CommoninchingActivity;
import itbour.onetouchshow.base.IntentCode;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.fragment.modelpreview.ModelpreviewFragment;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.CommonUtils;
import itbour.onetouchshow.utils.L_;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
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
        //after获取当前的title
        setTitleText("模板标题");
        //在after中判断当前是否收藏

        getToolBarView().setleftImageResource(getResources().getDrawable(R.drawable.back))
                .setRightImageResource(getResources().getDrawable(R.drawable.iv_un_collect)).setRightIocnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelpreviewFragment currentFragment = pagerAdapter.getCurrentFragment();
                Boolean isCollect = currentFragment.isCollect;
                int id = currentFragment.listBean.getId();
                mPresenter.collectOrCancalCollectThisModel(isCollect, id);
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
        listBeans = CommonUtils.jsonToArrayList(pageContentList, DesignListBean.ListBean.class);
        L_.i("listBeans===" + listBeans);
        //拿到数据后构建可以滑动的viewpager adapter
        initFragment();

    }

    private List<Fragment> scrollFragment = new ArrayList<>();

    private void initFragment() {
        for (int i = 0; i < listBeans.size(); i++) {
            DesignListBean.ListBean listBean = listBeans.get(i);
            scrollFragment.add(new ModelpreviewFragment(VerticalmodelActivity.this, listBean));
        }
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewpager.setNoScroll(false);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(1);


    }

    @Override
    public void isCollect() {
        getToolBarView().setRightImageResource(getResources().getDrawable(R.drawable.iv_on_collect));
        if (pagerAdapter != null) {
            pagerAdapter.mCurrentFragment.isCollect = true;
        }
    }

    @Override
    public void unCollect() {
        getToolBarView().setRightImageResource(getResources().getDrawable(R.drawable.iv_un_collect));
        pagerAdapter.mCurrentFragment.isCollect = false;

    }

    @Override
    public void nowFragmentVisable(DesignListBean.ListBean data) {
        mPresenter.loadThisIsCollect(data);
        setTitleText(data.getName());
    }

    @Override
    public void collectSuccess(String text) {
        toastCenter(text);
    }

    @Override
    public void collectFaild() {
        toastCenter("收藏失败");
    }


    @OnClick({R.id.rl_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //拿到当前的选中 fragment是否显示然后去执行收藏/取消收藏标记
            case R.id.rl_next:
                //跳转到公共微调界面
                Intent intent = new Intent(this, CommoninchingActivity.class);
                DesignListBean.ListBean listBean = pagerAdapter.getCurrentFragment().listBean;
                int id = listBean.getId();

                intent.putExtra(IntentCode.TAG,IntentCode.INTENT_VERTICAL_MODEL);
                intent.putExtra(AppConst.ID,id);
                startActivity(intent);

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
}
