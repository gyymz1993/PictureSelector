package itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lsjr.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.video.commoninching.CommoninchingActivity;
import itbour.onetouchshow.activity.video.videopreview.VideoPreviewActivity;
import itbour.onetouchshow.activity.web.CreateOrderActivity;
import itbour.onetouchshow.bean.ImageBean;
import itbour.onetouchshow.bean.ProductPreviewBean;
import itbour.onetouchshow.bean.ProductPreviewInfoBean;
import itbour.onetouchshow.custom.DialogUtils;
import itbour.onetouchshow.custom.ShareDialogFragment;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.platform.WorkPlatform;
import itbour.onetouchshow.utils.BroadcastAction;
import itbour.onetouchshow.utils.FinshBroadcast;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * <p>
 * 作品预览
 */

public class VerticalproductActivity extends MVPBaseActivity<VerticalproductContract.View, VerticalproductPresenter> implements VerticalproductContract.View {
    @BindView(R.id.rv_vertical_product_preview)
    RecyclerView recyclerview;
    @BindView(R.id.ll_vertical_product_share)
    LinearLayout share;
    @BindView(R.id.rl_go_to_romove_water)
    RelativeLayout reMoveWater;
    @BindView(R.id.id_ry_share)
    LinearLayout reShare;
    @BindView(R.id.id_tv_gotoremovewater)
    TextView goToRomoveWater;
    @BindView(R.id.id_ry_down)
    LinearLayout ryDown;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    ProductPreviewBean productPreviewBean;

    String type;
    int docId;

    @Override
    protected void initTitle() {
        super.initTitle();
        registerExitReceiver();
        setTitleText("作品预览");
        getToolBarView().setleftImageResource(getResources().getDrawable(R.drawable.back)).setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T_.showToastReal("完成");
                if (type.equals(WorkPlatform.W0RK)) {
                    if (productPreviewBean.getWatermark() == 1) {
                        //没去水印直接修改
                        Intent intent = new Intent(VerticalproductActivity.this, CommoninchingActivity.class);
                        intent.putExtra(itbour.onetouchshow.base.IntentCode.INCHING_TYPE, itbour.onetouchshow.base.IntentCode.SUB_TYPE_PRODUCE);
                        intent.putExtra(itbour.onetouchshow.base.IntentCode.TAG, itbour.onetouchshow.base.IntentCode.INTENT_VERTICAL_PRODUCT);
                        intent.putExtra(AppConst.ID, productPreviewBean.getId());
                        startActivity(intent);

                    } else {
                        //去了水印  先获取拷贝数据
                        //mPresenter.getCopyDocStringAndNew(docId+"");
                        Intent intent = new Intent(VerticalproductActivity.this, CommoninchingActivity.class);
                        intent.putExtra(itbour.onetouchshow.base.IntentCode.INCHING_TYPE, itbour.onetouchshow.base.IntentCode.SUB_TYPE_MODULE);
                        intent.putExtra(itbour.onetouchshow.base.IntentCode.TAG, itbour.onetouchshow.base.IntentCode.INTENT_COPY_PRO_NEW);
                        //intent.putExtra("isCopyNew",true);
                        intent.putExtra(AppConst.ID, productPreviewBean.getId());
                        startActivity(intent);
                    }

                } else {
                    sendRemoveThreeLogic();
                }

            }
        }).setLetfIocnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugleQuit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        jugleQuit();
    }

    public void jugleQuit() {
        if (productPreviewBean == null || productPreviewBean.getWatermark() == 1) {
            finish();
        } else {
            sendRemoveThreeLogic();
        }
    }

    @Override
    public void loadSucceed(String result) {
        // L_.e("制作wanc"+result);
        reShare.setEnabled(true);
        productPreviewBean = new Gson().fromJson(result, ProductPreviewBean.class);
        int supportHQ = productPreviewBean.getSupportHQ();
        llBottom.setVisibility(View.VISIBLE);
        if (supportHQ == 1) {
            ryDown.setVisibility(View.VISIBLE);
        } else {
            ryDown.setVisibility(View.GONE);  //隐藏下载按钮
        }


        L_.e(productPreviewBean.getId() + "");
        ProductPreviewInfoBean modelPreviewInfoBean = new ProductPreviewInfoBean();
        modelPreviewInfoBean.setImage(productPreviewBean.getImage());
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layout);
        Adapter adapter = new Adapter(modelPreviewInfoBean);
        recyclerview.setAdapter(adapter);

        switch (productPreviewBean.getWatermark()) {
            case 1:
                //已付款
                switch (productPreviewBean.getWatermarkPay()) {
                    case 1:
                        //有水印 重新发起去水印
                        goToRomoveWater.setEnabled(true);
                        goToRomoveWater.setText("(去水印)已支付");
                        // mPresenter.removeWatermark(productPreviewBean.getId() + "");
                        break;
                    case 0:
                        goToRomoveWater.setEnabled(true);
                        goToRomoveWater.setText(productPreviewBean.getWatermarkFee() / 100 + getResources().getString(R.string.gotowaterprice));
                        break;
                }
                break;
            case 0:
                goToRomoveWater.setText("已去水印");
                goToRomoveWater.setEnabled(false);
                break;
        }
        dismissProgressDialog();

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verticalproduct;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        docId = intent.getIntExtra(AppConst.DOCID, 0);
        type = intent.getStringExtra(WorkPlatform.TYPE);
        if (type.equals(WorkPlatform.W0RK)) {
            getToolBarView().setRightText("修改作品");
        } else {
            getToolBarView().setRightText("完成");
        }
        // intent.putExtra(WorkPlatform.TYPE, IntentCode.getProductBundle(WorkPlatform.WOEK));
        ryDown.setVisibility(View.GONE);  //隐藏下载按钮
        L_.e(docId + "");
        EventBus.getDefault().register(this);
        showProgressDialogWithText("数据加载中...");
        mPresenter.loadProductData(docId);
        reShare.setEnabled(false);
    }


    @OnClick({R.id.ll_vertical_product_share, R.id.id_ry_share,
            R.id.rl_go_to_romove_water, R.id.id_tv_gotoremovewater,
            R.id.id_ry_down

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_ry_down:
                showProgressDialogWithText("正在下载...");
                mPresenter.getDownVideoUrl(productPreviewBean.getId() + "");
                break;

            case R.id.id_ry_share:
            case R.id.ll_vertical_product_share:
                String docNm = productPreviewBean.getDocNm();
                L_.i(docNm);
                //ShareDialogFragment.getInstance().onShow(VerticalproductActivity.this);
                ShareDialogFragment.getInstance().setBuilder(new ShareDialogFragment.Builder()
                        .setDocId(productPreviewBean.getId() + "").setContext(productPreviewBean.getDocNm()).
                                setTitle(docNm).
                                setAvUrl(productPreviewBean.getImage().getThumbs().get(0)))
                        .onShow(VerticalproductActivity.this);
                break;
            case R.id.rl_go_to_romove_water:
            case R.id.id_tv_gotoremovewater:
                if (NetUtils.isConnected(getApplication())) {
                    if (productPreviewBean.getWatermark() == 1 && productPreviewBean.getWatermarkPay() == 1) {
                        showProgressDialogWithText("正在努力去水印");
                        mPresenter.removeWatermark(productPreviewBean.getId() + "");
                    } else {
                        showRemoveWaterDialog();
                    }
                } else {
                    T_.showCustomToast("请检查网络");
                }
                break;
            default:
                break;
        }
    }


    public void showRemoveWaterDialog() {
        DialogUtils.getInstance(new DialogUtils.Builder().setTitle(" ")
                .setCancletext("取消").setCancleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtils.getInstance().dismiss();
                    }
                })
                .setConfirmText("确定").setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.getInstance().dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("docId", productPreviewBean.getId() + "");
                        openActivity(CreateOrderActivity.class, bundle);
                    }
                }).setContext("去水印后,修改作品将生成带水印新作品，" +
                        "去水印作品仍会保留").setPlatform(DialogUtils.DialogPlatform.TWO_BTN))
                .builder(VerticalproductActivity.this);
    }


    /**
     * threadMode 执行的线程方式
     * priority 执行的优先级
     * sticky 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 50, sticky = true)
    public void paySuccess(Integer code) {
        L_.e("支付成功 去水印");
        // 如果有一个地方用 EventBus 发送一个 String 对象，那么这个方法就会被执行
        if (code == 1000) {
            goToRomoveWater.setText("去水印(已支付)");
            productPreviewBean.setWatermarkPay(1);
            productPreviewBean.setWatermark(1);
            showProgressDialogWithText("正在努力去水印");
            mPresenter.removeWatermark(productPreviewBean.getId() + "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegisterExitReceiver();
    }

    @Override
    public void removeWatermarkSuccess(String result) {

    }

    @Override
    public void removeWatermarkFail(String result) {
        dismissProgressDialog();
    }

    @Override
    public void copyandNewProducesuccess(String id) {

    }

    @Override
    public void downLoadPrintSuccess(String path) {


        dismissProgressDialog();
        DialogUtils.getInstance(new DialogUtils.Builder().setTitle(" ")
                .setConfirmText("确定").setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.getInstance().dismiss();
                    }
                }).setContext("已成功下载到本地").setPlatform(DialogUtils.DialogPlatform.ONE_BTN))
                .builder(this);
    }


    /**
     * recyclerView的adapter
     */
    public class Adapter extends RecyclerView.Adapter {

        ProductPreviewInfoBean data;

        public Adapter(ProductPreviewInfoBean data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_model_preview, parent, false);
            Adapter.ViewHolder itemViewHolder = new Adapter.ViewHolder(view);
            return itemViewHolder;
        }


        //
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            Adapter.ViewHolder holder = (Adapter.ViewHolder) viewHolder;
            ImageView imageView = holder.imageView;
            ImageBean image = data.getImage();

            int screenWith = UIUtils.WHD()[0] - UIUtils.dp2px(12);
            int screenHeight = UIUtils.WHD()[1]
                    - UIUtils.dp2px(60 + 55) - UIUtils.getStatusBarHeight(VerticalproductActivity.this);
            L_.i("screenHeight===" + screenHeight);
            //底部加状态栏
            int imageHeight = image.getH();
            int imageWidth = image.getW();

            L_.i("原始宽高imageWidth===" + imageWidth + "imageHeight===" + imageHeight + "thumb===" + image.getThumbs().get(0));
            float scale = imageHeight * 1.0f / imageWidth;


            imageWidth = screenWith;
            imageHeight = (int) (screenWith * scale);
            if (imageHeight > screenHeight) {
                imageHeight = screenHeight;
                imageWidth = (int) (imageHeight / scale);
            }

            List<String> thumbs = image.getThumbs();
            String thumb = thumbs.get(position);
            String shrinkImageUrl = ImageLoader.getShrinkImageUrl(thumb, imageHeight);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            lp.width = imageWidth;
            lp.height = imageHeight;
            imageView.setLayoutParams(lp);
            L_.i("imageWidth===" + imageWidth + "imageHeight===" + imageHeight + "thumb===" + thumb);

            //如果只有一个  居中显示
            if (getItemCount() == 1) {
                RelativeLayout.LayoutParams lpRl = (RelativeLayout.LayoutParams) holder.rl.getLayoutParams();
                lpRl.height = screenHeight;
                holder.rl.setLayoutParams(lpRl);
            }


            Glide.with(VerticalproductActivity.this).load(shrinkImageUrl).thumbnail(0.1f).into(imageView);
        }

        @Override
        public int getItemCount() {
            ImageBean image = data.getImage();
            List<String> thumbs = image.getThumbs();
            L_.e(thumbs.size() + "");
            return data.getImage().getThumbs().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_adapter_model_preview)
            ImageView imageView;
            @BindView(R.id.rl_adapter_model_preview)
            RelativeLayout rl;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
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

    private void sendRemoveThreeLogic() {
        Intent intent = new Intent();
        intent.setAction(BroadcastAction.EXIT_THREE_LOGIC);
        sendBroadcast(intent);
    }
}
