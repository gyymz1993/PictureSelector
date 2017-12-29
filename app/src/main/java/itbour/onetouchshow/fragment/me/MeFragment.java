package itbour.onetouchshow.fragment.me;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.adapter.ABaseRefreshAdapter;
import com.andview.adapter.BaseRecyclerHolder;
import com.andview.listener.OnItemClickListener;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.IntentCode;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.SettingActivity;
import itbour.onetouchshow.activity.login.LoginActivity;
import itbour.onetouchshow.activity.main.MainActivity;
import itbour.onetouchshow.activity.myorder.MyCollectActivity;
import itbour.onetouchshow.activity.myworks.MyworksActivity;
import itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct.VerticalproductActivity;
import itbour.onetouchshow.activity.userinfo.UserinfoActivity;
import itbour.onetouchshow.activity.video.videopreview.VideoPreviewActivity;
import itbour.onetouchshow.activity.video.videopreview.VideoProudctPreviewActivity;
import itbour.onetouchshow.activity.web.MyOrderListActivity;
import itbour.onetouchshow.bean.DocsBean;
import itbour.onetouchshow.bean.UserDetailInfoBean;
import itbour.onetouchshow.bean.UserWork;
import itbour.onetouchshow.custom.RoundImageView;
import itbour.onetouchshow.evenbus.UpdateUserAction;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.platform.DataPlatform;
import itbour.onetouchshow.platform.WorkPlatform;
import itbour.onetouchshow.rvmanager.FullyLinearLayoutManager;
import itbour.onetouchshow.singlecase.InchingDataSingleCase;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.UIUtils;

import static itbour.onetouchshow.evenbus.UpdateUserAction.TYPE_CREATEWORK;
import static itbour.onetouchshow.evenbus.UpdateUserAction.UPDATA_USER_NAME;
import static itbour.onetouchshow.evenbus.UpdateUserAction.UPDAT_USER_AV;
import static itbour.onetouchshow.evenbus.UpdateUserAction.UPDAT_USER_SGIN;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View {

    @BindView(R.id.id_rv_me)
    RecyclerView idRvMe;
    @BindView(R.id.id_rv_user_work)
    RecyclerView rvUserWork;
    @BindView(R.id.id_more_work)
    TextView moreWork;
    @BindView(R.id.id_ig_user_av)
    RoundImageView idIgUserAv;
    @BindView(R.id.id_user_name)
    TextView idUserName;
    @BindView(R.id.id_user_signa)
    TextView idUserSigna;
    @BindView(R.id.id_ig_more)
    ImageView idIgMore;
    @BindView(R.id.id_tv_no_data)
    TextView tvNoWork;
    @BindView(R.id.id_update_ry)
    RelativeLayout updatRy;


    private String[] titles = {"我的订单", "我的收藏", "联系客服", "设置"};
//    R.mipmap.me_item_kf
    private int[] images = {R.mipmap.me_itme_myorder, R.mipmap.me_item_collect, R.mipmap.me_item_pf, R.mipmap.me_item_seting};
    /*我的作品数据*/
    List<DocsBean> docs = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private PcenterWorkAdapter pcenterWorkAdapter;
    /*用户信息数据*/
    UserDetailInfoBean userDetailInfoBean;

    @Override
    public void loadSucceed(String result) {
        UserWork userWork = new Gson().fromJson(result, UserWork.class);
        docs = userWork.getDocs();
        if (docs == null || userWork.getDocs().size() == 0) {
            tvNoWork.setVisibility(View.VISIBLE);
            rvUserWork.setVisibility(View.GONE);
        } else {
            tvNoWork.setVisibility(View.GONE);
            rvUserWork.setVisibility(View.VISIBLE);
        }
        pcenterWorkAdapter.notifyDataSetChanged(docs);
    }


    @Override
    public void loadFaild(String error) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(UpdateUserAction updateUserAction) {
        int subType = updateUserAction.getSubType();
        switch (subType) {
            case UPDAT_USER_SGIN:
                idUserSigna.setText(updateUserAction.getContent());
                userDetailInfoBean.getUserInfo().setSign(updateUserAction.getContent());
                break;
            case UPDATA_USER_NAME:
                idUserName.setText(updateUserAction.getContent());
                userDetailInfoBean.getUserInfo().setNickName(updateUserAction.getContent());
                break;
            case UPDAT_USER_AV:
                ImageLoader.getInstance().showImage(updateUserAction.getContent(), idIgUserAv.getImageView());
                userDetailInfoBean.getUserInfo().setAvatarUrl(updateUserAction.getContent());
                break;
            case TYPE_CREATEWORK:
                //T_.showToastReal("刷新作品");
                // mPresenter.loadPersonalCenterInfo();
                break;
            default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.isLogin()) {
            mPresenter.loadPersonalCenterInfo();
            mPresenter.loadUserDetailInfo();
        } else {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.viewPager.setCurrentItem(0, false);
        }

    }

    @Override
    protected void onVisible() {
        super.onVisible();
        // T_.showToastReal("显示刷新");
        // mPresenter.loadPersonalCenterInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        //  EventBus.getDefault().register(this);
        //L_.e("注册 EVENBus");

        if (App.isLogin()) {
            mPresenter.loadPersonalCenterInfo();

            mPresenter.loadUserDetailInfo();
        }

    }

    @Override
    protected void initView() {
        super.initView();
        idRvMe.setHasFixedSize(true);
        idRvMe.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        idRvMe.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int mSpace = UIUtils.dip2px(10);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childCount = parent.getChildCount();
                int pos = parent.getChildAdapterPosition(view);
                outRect.left = 0;
                outRect.top = 0;
                outRect.bottom = 0;
                if (pos == 2) {
                    outRect.top = mSpace;
                } else {
                    if (pos != (childCount - 1)) {
                        outRect.right = mSpace;
                    } else {
                        outRect.right = 0;
                    }
                }

            }

        });
        titleList.addAll(Arrays.asList(titles));
        PcenterAdapter adapter = new PcenterAdapter(getContext(), titleList, R.layout.item_me_frag);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecyclerHolder baseRecyclerHolder, int position, Object item) {
                // {"我的订单", "我的收藏", "联系客服", "去评分", "设置"};
                if (App.isLogin()) {
                    if (titleList.get(position).equals("我的订单")) {
                        openActivity(MyOrderListActivity.class);
                    } else if (titleList.get(position).equals("我的收藏")) {
                        openActivity(MyCollectActivity.class);
                    } else if (titleList.get(position).equals("联系客服")) {
                        if (!TextUtils.isEmpty(InchingDataSingleCase.INSTANCE.getAppControl().getTel())) {
                            goToCellPhoneActivity(InchingDataSingleCase.INSTANCE.getAppControl().getTel());
                        } else {
                            goToCellPhoneActivity("");
                        }
                    } else if (titleList.get(position).equals("设置")) {
                        openActivity(SettingActivity.class);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }

            }
        });
        idRvMe.setAdapter(adapter);

        rvUserWork.setHasFixedSize(true);
        rvUserWork.setLayoutManager(new GridLayoutManager(getContext(), 4));
        pcenterWorkAdapter = new PcenterWorkAdapter(getContext(), null, R.layout.item_user_work);
        pcenterWorkAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecyclerHolder baseRecyclerHolder, int position, Object item) {
                DocsBean docsBean = docs.get(position);
                int opType = docsBean.getOpType();
                switch (opType) {
                    case DataPlatform.ORTYPE_VERTICAL:
                        Intent intent = new Intent(getActivity(), VerticalproductActivity.class);
                        intent.putExtra(AppConst.DOCID, docsBean.getId());
                        intent.putExtra(AppConst.OPTYPE, opType);
                        intent.putExtra(WorkPlatform.TYPE, IntentCode.getProductBundle(WorkPlatform.WOEK));
                        startActivity(intent);
                        break;
                    case DataPlatform.ORTYPE_VIDIO:
//                        Bundle bundle = new Bundle();
//                        bundle.putString(AppConst.DOCID, docsBean.getId() + "");
//                        bundle.putString(WorkPlatform.TYPE, WorkPlatform.getWorkPlatform(WorkPlatform.WOEK));
                        //Bundle bundle = IntentCode.getBundle(docsBean.getId(),WorkPlatform.WOEK);
                        // openActivity(VideoplayActivity.class, bundle);
//                        Bundle bundle = new Bundle();
//                        bundle.putString(AppConst.DOCID, docsBean.getId() + "");
//                        openActivity(VideoProudctPreviewActivity.class, bundle);

                        Bundle bundle = new Bundle();
                        bundle.putString(AppConst.DOCID, docsBean.getId() + "");
                        // String docId = extras.getString(AppConst.DOCID);
                        bundle.putString(WorkPlatform.TYPE, WorkPlatform.W0RK);
                        openActivity(VideoPreviewActivity.class, bundle);
                        break;
                    case DataPlatform.ORTYPE_PPT:
                        break;
                    default:
                        break;
                }

            }
        });
        rvUserWork.setAdapter(pcenterWorkAdapter);
    }


    @Override
    public void loaduserDetailSuccess(String result) {
        //L_.e(result);
        userDetailInfoBean = new Gson().fromJson(result, UserDetailInfoBean.class);
        if (userDetailInfoBean == null) {
            return;
        }
        idUserName.setText(userDetailInfoBean.getUserInfo().getNickName());
        idUserSigna.setText(userDetailInfoBean.getUserInfo().getSign());
        ImageLoader.getInstance().showImage(userDetailInfoBean.getUserInfo().getAvatarUrl(), idIgUserAv.getImageView());
    }

    @Override
    public void loaduserDetailFail(String result) {

    }


    @OnClick({R.id.id_ig_more, R.id.id_more_work, R.id.id_update_ry})
    public void onViewClicked(View view) {
        if (App.isLogin()) {
            switch (view.getId()) {
                case R.id.id_ig_more:
                case R.id.id_update_ry:
                    Bundle bundle = new Bundle();
                    bundle.putString("userName", userDetailInfoBean.getUserInfo().getNickName());
                    bundle.putString("userSign", userDetailInfoBean.getUserInfo().getSign());
                    bundle.putString("AvatarUrl", userDetailInfoBean.getUserInfo().getAvatarUrl());
                    // bundle.putParcelable("userDetailInfoBean",userDetailInfoBean);
                    openActivity(UserinfoActivity.class, bundle);
                    break;
                case R.id.id_more_work:
                    openActivity(MyworksActivity.class);
                    break;
                default:
            }
        } else {
            openActivity(LoginActivity.class);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class PcenterWorkAdapter extends ABaseRefreshAdapter<DocsBean> {

        public PcenterWorkAdapter(Context context, List<DocsBean> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder baseRecyclerHolder, DocsBean docsBean, int postion) {
            RoundImageView iv_photo = baseRecyclerHolder.getView(R.id.id_ig_user_work);
            ViewGroup.LayoutParams layoutParams = iv_photo.getLayoutParams();
            //layoutParams.width = UIUtils.WH()[0] - UIUtils.px2dip(30) / 5;
            layoutParams.width = UIUtils.WH()[0] / 4 - UIUtils.dp2px(10);
            iv_photo.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().showImage(docsBean.getThumb(), iv_photo.getImageView());
        }
    }

    public class PcenterAdapter extends ABaseRefreshAdapter<String> {



        public PcenterAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder baseRecyclerHolder, String s, int i) {
            TextView tv_name = baseRecyclerHolder.getView(R.id.tv_name);
            ImageView iv_photo = baseRecyclerHolder.getView(R.id.iv_photo);
            iv_photo.setImageResource(images[i]);
            tv_name.setText(s);
        }
    }

}
