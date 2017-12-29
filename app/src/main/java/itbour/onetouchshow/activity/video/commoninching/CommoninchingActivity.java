package itbour.onetouchshow.activity.video.commoninching;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct.VerticalproductActivity;
import itbour.onetouchshow.activity.video.tableedit.TableeditActivity;
import itbour.onetouchshow.base.IntentCode;
import itbour.onetouchshow.base.SPCTag;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.bean.canvas.CompoundPathsBean;
import itbour.onetouchshow.bean.canvas.PosBean;
import itbour.onetouchshow.bean.inching.noppt.InchingLinkObjBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineAllTypeV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineImgBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineTableBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineTextOrShapeBean;
import itbour.onetouchshow.custom.DialogUtils;
import itbour.onetouchshow.custom.InchingDottedView;
import itbour.onetouchshow.custom.inching.InchingBaseAreaRelativeLayout;
import itbour.onetouchshow.custom.inching.InchingOperaPW;
import itbour.onetouchshow.custom.inching.InchingOperaRelativeLayout;
import itbour.onetouchshow.custom.inching.InchingOperaViewImg;
import itbour.onetouchshow.custom.inching.InchingOpreaImageView;
import itbour.onetouchshow.custom.inching.InchingTableOperaPanel;
import itbour.onetouchshow.custom.inching.InchingTextOperaPanel;
import itbour.onetouchshow.custom.inching.NewInchingFrontBoderView;
import itbour.onetouchshow.gsonanalysis.InchingOutLineAnalysisRules;
import itbour.onetouchshow.listener.PermissionListener;
import itbour.onetouchshow.listener.SoftKeyBoardListener;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.platform.WorkPlatform;
import itbour.onetouchshow.singlecase.InchingDataSingleCase;
import itbour.onetouchshow.utils.BroadcastAction;
import itbour.onetouchshow.utils.CommonUtils;
import itbour.onetouchshow.utils.FineTuneRender;
import itbour.onetouchshow.utils.FinshBroadcast;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * 用于竖屏的微调
 */

public class CommoninchingActivity extends MVPBaseActivity<CommoninchingContract.View, CommoninchingPresenter>
        implements CommoninchingContract.View, InchingTextOperaPanel.OnPanelButtonClick, InchingTableOperaPanel.OnPanelButtonClick {

    @BindView(R.id.text_opera_panel)
    InchingTextOperaPanel textOperaPanel;
    @BindView(R.id.rl_inching_root)
    RelativeLayout rlInchingRoot;
    @BindView(R.id.rl_next)
    RelativeLayout rlNext;
    @BindView(R.id.rl_stub)
    RelativeLayout rlStub;
    @BindView(R.id.rl_inching_base_area)
    InchingBaseAreaRelativeLayout rlInchingBaseArea;

    @BindView(R.id.rl_remove_broad)
    RelativeLayout rlRemoveBroad;
    @BindView(R.id.bt_previous_pagae)
    TextView btPreviousPagae;
    @BindView(R.id.bt_next_pagae)
    TextView btNextPagae;
    @BindView(R.id.ll_switch_page)
    LinearLayout llSwitchPage;
    @BindView(R.id.rl_add_text)
    RelativeLayout rlAddText;
    @BindView(R.id.rl_add_image)
    RelativeLayout rlAddImage;
    @BindView(R.id.table_opera_panel)
    InchingTableOperaPanel tableOperaPanel;

    private InchingOperaPW pw;

    private int inchType;
    /*是否是存草稿*/
    private boolean isSave = false;

    InchingOperaRelativeLayout nowSelectView;
    int nowPageIndex = 0;
    int controlW = 27;
    int controlH = 27;

    @Override
    protected void initTitle() {
        super.initTitle();

        getToolBarView().setLetfIocnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugleQuit();
            }
        });
        requestPermissions();
        registerExitReceiver();

    }

    private void initTitleBar(String title, String rightText) {
        setTitleText(title);
        getToolBarView().setleftImageResource(getResources().getDrawable(R.drawable.back))
                .setRightText(rightText).setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveCaoGaoClick();
            }
        });

    }

    public void onSaveCaoGaoClick() {
        isSave = true;
        //T_.showToastReal("存草稿");
        //存草稿
        //区分作品或者模板

        switch (inchType) {
            case IntentCode.SUB_TYPE_MODULE:
                //模板
                showProgressDialogWithText("正在存草稿");
                mPresenter.createProduct();
                break;
            case IntentCode.SUB_TYPE_PRODUCE:
                //作品
                showProgressDialogWithText("正在存草稿");
                mPresenter.updataProduct();
                break;
        }
    }

    @Override
    public void loadSucceed(String result) {
        dismissProgressDialog();
    }

    @Override
    public void loadFaild(String error) {
        dismissProgressDialog();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_inching;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        rlStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L_.i("rlStub");
                textOperaPanel.hidePanelAndKeyBroad();
                tableOperaPanel.setVisibility(View.GONE);
                rlStub.setVisibility(View.GONE);
                hideControlView();

            }
        });
        initKeyBroadListener();


        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(IntentCode.TAG, 0);
        int id = intent.getIntExtra(AppConst.ID, 0);
        inchType = intent.getIntExtra(IntentCode.INCHING_TYPE, 0);
        //  boolean isCopyNew = intent.getBooleanExtra("isCopyNew", false);
//        int id = 828;

        mPresenter.setId(id);
        L_.e(id + "");
        showProgressDialogWithText("数据加载中...");
        switch (intExtra) {
            case IntentCode.INTENT_VERTICAL_MODEL:
                initTitleBar("模版编辑", "存草稿");
                mPresenter.onVerticalModelEnter(id, 1);
                break;
            case IntentCode.INTENT_VERTICAL_VIDEP:
                mPresenter.onVerticalVideoEnter(id);
                initTitleBar("修改背景", "");
                getToolBarView().getLeftimageView().setVisibility(View.GONE);
                getToolBarView().getRightImageView().setImageResource(R.mipmap.cancle_icon);
                getToolBarView().getRightImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                getToolBarView().getRightImageView().setVisibility(View.VISIBLE);
                break;
            case IntentCode.INTENT_COPY_PRO_NEW:
                //  数据拷贝
                mPresenter.getCopyDocStringAndNew(id);
                initTitleBar("修改作品", "存草稿");
                break;
            case IntentCode.INTENT_VERTICAL_PRODUCT:  //没有去水印
                // mPresenter.updataProduct();
                mPresenter.onVerticalModelEnter(id, 0);
                initTitleBar("修改作品", "存草稿");
                break;
            case IntentCode.INTENT_VEDIO_MODEL:
                break;
            case IntentCode.INTENT_VEDIO_PRODUCT:
                break;
            default:
                break;
        }
        pw = initPopWindow();

    }


    public InchingOperaPW initPopWindow() {
        InchingOperaPW pw = new InchingOperaPW(this);

        return pw;
    }


    private void initKeyBroadListener() {
        SoftKeyBoardListener.setListener(CommoninchingActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

                L_.i("键盘显示 高度" + height);
                int saveHeight = SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0);
                if (saveHeight == 0 || saveHeight < 300) {
                    SpUtils.putInt(SPCTag.KEYBROAD_HEIGHT, height);
                }
                if (textOperaPanel.getWenBenHeight() != height) {
                    textOperaPanel.setWenBenParams(SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0));
                }

                rlStub.setVisibility(View.VISIBLE);
                rlStub.bringToFront();
                textOperaPanel.bringToFront();
            }

            @Override
            public void keyBoardHide(int height) {
                L_.i("键盘隐藏 高度" + height);

                boolean isShow = textOperaPanel.editBarIsShow();

                if (isShow && textOperaPanel.needDismiss()) {
                    L_.i("switch1needDismiss");
                    textOperaPanel.hidePanelAndKeyBroad();
                }


                //从文字输入切换到其它 然后再执行后续操作
                if (textOperaPanel != null) {
                    textOperaPanel.fromWenBenOnKeyBroadDismiss();
                }


            }
        });
    }


    @OnClick({

            R.id.rl_remove_broad, R.id.rl_next,
            R.id.bt_previous_pagae, R.id.bt_next_pagae,
            R.id.rl_add_text, R.id.rl_add_image

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_add_text:
                L_.i("rl_add_text");
                addInchingTextView();
                break;
            case R.id.rl_add_image:
                L_.i("rl_add_image");
                int count = 0;
                //当前页可操作控件总数为20
                //获取传入的加图最大限制
                int ftMaxImageCnt = InchingDataSingleCase.INSTANCE.getLengthControl().getFTMaxImageCnt();
                if (rlInchingBaseArea != null) {
                    for (int i = 0; i < rlInchingBaseArea.getChildCount(); i++) {
                        View childAt = rlInchingBaseArea.getChildAt(i);
                        if (childAt != null && childAt instanceof InchingOperaRelativeLayout) {
                            InchingOperaRelativeLayout rl = (InchingOperaRelativeLayout) childAt;
                            if (rl.getIsEnable() == 1) {
                                if (rl.getData() instanceof InchingOutLineImgBean) {

                                    count++;
                                }
                            }
                        }
                    }
                }
                L_.i("addInchingTextView cout===" + count);
                if (count > ftMaxImageCnt) {
                    DialogUtils.getInstance((new DialogUtils.Builder().setContext("图片数量已达到上限").setCancletext("确定").setConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.getInstance().dismiss();
                        }
                    }).setPlatform(DialogUtils.DialogPlatform.ONE_BTN))).builder(this);
                    return;
                }
                //去相册选图
                //吊起取相册的逻辑
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, AppConst.TO_ADD_IMAGE);
                break;

            case R.id.rl_remove_broad:
                L_.i("rl_remove_broad");
                hideControlView();

                break;
            case R.id.bt_previous_pagae:
                L_.i("bt_previous_pagae");
                llSwitchPage.setBackground(getResources().getDrawable(R.drawable.iv_inching_button_first_page));
                btPreviousPagae.setTextColor(Color.WHITE);
                btNextPagae.setTextColor(Color.BLACK);

                mPresenter.previousPage();

                break;
            case R.id.bt_next_pagae:
                L_.i("bt_next_pagae");
                llSwitchPage.setBackground(getResources().getDrawable(R.drawable.iv_inching_button_reverse_page));
                btPreviousPagae.setTextColor(Color.BLACK);
                btNextPagae.setTextColor(Color.WHITE);

                mPresenter.nextPage();

                break;

            case R.id.rl_next:
                showProgressDialogWithText("数据加载中...");
                //T_.showToastReal("制作完成");
                //模拟生成接口
                switch (inchType) {
                    case IntentCode.SUB_TYPE_MODULE:
                        mPresenter.createProduct();
                        break;
                    case IntentCode.SUB_TYPE_PRODUCE:
                        //根据opType判断是视频还是设计
                        int value = mPresenter.getIsVideo().intValue();
                        if (value == 1) {
                            //视频背景
                            mPresenter.updataVideoBgProduct();
                        } else {
                            mPresenter.updataProduct();
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onEnsure() {
        L_.i("onEnsure");
        hideControlView();
    }


    @Override
    public void onEditTextChange(String string) {
        L_.i(string);
        //这里的Id是微调库里的id
        int id = getNowSelectView().getFineTuneRenderId();

        FineTuneRender.changeTextContentForJava(nowPageIndex, id, string);
        String texts = FineTuneRender.getPageTextPropertyForJava(nowPageIndex, id, 1);
        mPresenter.updateInchingView(id, texts, AppConst.INCHING_TEXTVIEW_TYPE);
    }

    /**
     * 当换颜色点击
     *
     * @param r
     * @param g
     * @param b
     * @param type
     */
    @Override
    public void onColorClick(int r, int g, int b, String type) {

        int id = getNowSelectView().getFineTuneRenderId();
        String dataJson = FineTuneRender.changeTextColorForJava(nowPageIndex, id, r, g, b, 0);
        InchingOutLineAllTypeV10Bean allTypeData = new GsonBuilder().registerTypeAdapter
                (InchingOutLineAllTypeV10Bean.class, new InchingOutLineAnalysisRules()).create()
                .fromJson(dataJson, InchingOutLineAllTypeV10Bean.class);
        if (allTypeData instanceof InchingOutLineTextOrShapeBean) {
            InchingOutLineTextOrShapeBean data = (InchingOutLineTextOrShapeBean) allTypeData;
            upDateTextOrShapeView(data);
        }

    }

    /**
     * 当字体点击
     *
     * @param position
     */
    @Override
    public void onTypeClick(int position) {
        List<HomeDetailslBean.FontListBean> fontList = InchingDataSingleCase.INSTANCE.getFontList();
        HomeDetailslBean.FontListBean fontListBean = fontList.get(position);
        String font = fontListBean.getFont();
        textOperaPanel.setNowSelectType(font);
        textOperaPanel.getInchingFontListAdapter().notifyDataSetChanged();

        int fineTuneRenderId = getNowSelectView().getFineTuneRenderId();

        FineTuneRender.changeFontFamilyForJava(nowPageIndex, fineTuneRenderId, font);
        String pageTextPropertyForJava = FineTuneRender.getPageTextPropertyForJava(nowPageIndex, fineTuneRenderId, 1);
        mPresenter.updateInchingView(fineTuneRenderId, pageTextPropertyForJava, AppConst.INCHING_TEXTVIEW_TYPE);

    }

    @Override
    public void onSeekBarChange(int fs, int ls, int cs) {
        L_.i("onSeekBarChange===fs" + fs + "ls===" + ls + "cs===" + cs);
        int fineTuneRenderId = getNowSelectView().getFineTuneRenderId();

        String json = FineTuneRender.changeTextInfoForJava(nowPageIndex, fineTuneRenderId, fs / 100.0f, ls / 100.0f, cs / 100.0f);

        InchingOutLineTextOrShapeBean data = new Gson().fromJson(json, InchingOutLineTextOrShapeBean.class);
        L_.i("onSeekBarChangeAfter===fs" + data.getFs() + "ls===" + data.getLs() + "cs===" + data.getCs());

        upDateTextOrShapeView(data);
    }

    @Override
    public void onPanelHide() {

        rlStub.setVisibility(View.GONE);
        rlRemoveBroad.clearAnimation();
    }


    /**
     * 监听按键当手势的时候隐藏键盘
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        L_.i("event===" + event);
        if (event.getAction() == KeyEvent.ACTION_UP &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (textOperaPanel.getVisibility() == View.VISIBLE) {
                textOperaPanel.hidePanelAndKeyBroad();
                return true;
            } else {
                jugleQuit();
                return true;
            }

        }
        return super.dispatchKeyEvent(event);
    }

    //当退出时 改变后调用存草稿的标记
    private boolean saveTag = false;

    //判断数据是否改变
    private void jugleQuit() {
        long changeForJava = FineTuneRender.isChangeForJava();
        if (changeForJava == 1) {
            DialogUtils.getInstance((new DialogUtils.Builder().setContext("是否保存本次编辑")
                    .setCancletext("取消").setConfirmText("确认")
                    .setConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.getInstance().dismiss();
                            saveTag = true;
                            onSaveCaoGaoClick();
                        }
                    })
                    .setCancleListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.getInstance().dismiss();
                            finish();
                        }
                    }).setPlatform(DialogUtils.DialogPlatform.TWO_BTN))).builder(this);
        } else if (changeForJava == 0) {
            finish();
        }

    }


    //暂停时隐藏键盘
    @Override
    protected void onPause() {
        super.onPause();
        textOperaPanel.hidePanelAndKeyBroad();
    }

    /**
     * 跳转到竖屏的作品预览
     *
     * @param docId
     * @param opType
     */
    @Override
    public void toProductPreview(int docId, int opType) {
        dismissProgressDialog();
        if (!isSave) {
            Intent intent = new Intent(this, VerticalproductActivity.class);
            intent.putExtra(AppConst.DOCID, docId);
            intent.putExtra(AppConst.OPTYPE, opType);
            intent.putExtra(WorkPlatform.TYPE, itbour.onetouchshow.IntentCode.getProductBundle(WorkPlatform.MODULE));
            startActivity(intent);
        } else {
            T_.showToastReal("存草稿成功");

            if (saveTag) {//退出时的存草稿
                finish();
            } else {//非退出时的存草稿
                FineTuneRender.resetChangeStatusForJava();
            }
        }
        isSave = false;

    }

    @Override
    public void showToast(String string) {
        T_.showToastReal(string);
    }


    @Override
    public void setInchingBaseAreaParams(int cw, int ch, int rootWith, int rootHeight, int bw, int bh) {
        controlW = cw;
        controlH = ch;

        //顶部预留高 用于文字在顶部时的弹窗
        int topZone = UIUtils.dp2px(30);
        //底部预留高 用于可以划上来
        int bootomZone = UIUtils.dp2px(130);
        //设置基础绘图区域的属性
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rlInchingBaseArea.getLayoutParams();

        //宽度 流出半个控制图标的宽高
        lp.leftMargin = cw / 2;
        //设置该控件的margin
        lp.topMargin = topZone;
        lp.width = rootWith + cw / 2;
        //上边界 title加上弹框预留区域的起点
        int startY = UIUtils.getStatusBarHeight(CommoninchingActivity.this) + UIUtils.dp2px(50) + topZone;
        //底边界 制作完成加上页面切换按钮的终点
        int endY = UIUtils.WHD()[1] - bootomZone;
        L_.i("startY===" + startY + "endY===" + endY);
        //高度加上顶部没有越过底边界
        if (startY + rootHeight > endY) {
            lp.height = rootHeight + bootomZone;
        } else {
            lp.height = rootHeight + ch / 2;
            //如果居中不超过下边界 则居中
            //如果中线+一半高没有越过底边界
            int midY = (endY - (startY - topZone)) / 2;
            //看如果把该控件至于中线是否超过下边界
            int viewExceptEndY = midY + rootHeight / 2;
            if (viewExceptEndY < endY) {
                int marginTop = midY - rootHeight / 2;
                L_.i("newmarginTop===" + marginTop);
                lp.topMargin = marginTop;
            }
        }


        rlInchingBaseArea.setParams(bw, bh, cw, ch, rootWith, rootHeight, lp.topMargin, lp.leftMargin);

        L_.i("layoutParamsV53.width===" + lp.width + "layoutParamsV53.height===" + lp.height);
        (rlInchingBaseArea).setLayoutParams(lp);


    }

    @Override
    public void setAddTextImgVisableStates(boolean showAddTextImg) {
        if (rlAddImage != null) {
            if (showAddTextImg) {
                rlAddImage.setVisibility(View.VISIBLE);
            } else {
                rlAddImage.setVisibility(View.GONE);
            }
        }
        if (rlAddText != null) {
            if (showAddTextImg) {
                rlAddText.setVisibility(View.VISIBLE);
            } else {
                rlAddText.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void createStubImgView(InchingOutLineImgBean data) {


        ImageView imageView = new ImageView(this);
        int w = (int) Math.ceil(data.getPos().getW());
        int h = (int) Math.ceil(data.getPos().getH());
        int x = (int) Math.ceil(data.getPos().getX());
        int y = (int) Math.ceil(data.getPos().getY());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
        params.leftMargin = x;
        params.topMargin = y;
        rlInchingBaseArea.addView(imageView, params);


        String source = data.getSource();
        Glide.with(CommoninchingActivity.this).load(source).into(imageView);
    }

    /**
     * 创建蒙板和网格线 在加载完其它控件后添加
     */
    @Override
    public void addMaskAndGridView() {
        rlInchingBaseArea.setMaskAndGrid();
    }


    /**
     * 要怎么做边界控制？
     * <p>
     * 之前的做法
     * 基础绘图区域加上了半个控制图标的宽高
     * 设定一个容器 容器的宽高加上了控制图标的宽高
     * 可编辑标记的虚线和拖动的控制控件 和展示控件在里面
     * <p>
     * 问题
     * 1：可编辑标记虚线应该怎么放
     * 2：控制控件应该怎么放
     * 3：视频的不可编辑区域应该怎么处理
     * <p>
     * 1:可编辑标记虚线作为bitmap的一部分绘制上去
     * 2:控制控件 放在基础绘图去的外层
     * 3：不可编辑的区域在基础绘图区判断
     * 4:图片在控制点区域内拖动时 蒙板实时改变 应该怎么处理
     *
     * @param data
     */
    @Override
    public void createOperaImage(InchingOutLineImgBean data) {
        int w = (int) Math.ceil(data.getPos().getW());
        int h = (int) Math.ceil(data.getPos().getH());
        int x = (int) data.getPos().getX();
        int y = (int) data.getPos().getY();
        int editable = data.getEditable();
        String source = data.getSource();


        InchingOperaRelativeLayout operaViewRL = getInchingRL(x, y, w, h);
        operaViewRL.setData(data);


        Glide.with(this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                InchingOpreaImageView img = new InchingOpreaImageView(CommoninchingActivity.this);
                img.setId(R.id.inching_can_opear_img);

                RelativeLayout.LayoutParams inchingRLLP = getInchingRLLP(w, h);
                operaViewRL.addView(img, inchingRLLP);
                img.setImgBitmap(resource);
                img.setData(data);

                if (editable == 1) {
                    operaViewRL.setThisDottedLineIsShow(1);

                    operaViewRL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            operaViewRL.setThisDottedLineIsShow(0);
                            InchingOperaRelativeLayout nowSelectView = getNowSelectView();
                            if (nowSelectView != null && nowSelectView != operaViewRL) {
                                nowSelectView.setThisDottedLineIsShow(1);
                            }
                            setNowSelectView(operaViewRL);
                            //当再次点击时要取现在的数据
                            InchingOutLineImgBean nowData = (InchingOutLineImgBean) operaViewRL.getData();

                            int[] locationMove = new int[2];
                            operaViewRL.getLocationOnScreen(locationMove);//获取在整个屏幕内的绝对坐标
                            int viewInWindowsX = locationMove[0];
                            int viewInWindowsY = locationMove[1];
                            showPopWindow(nowData, viewInWindowsX, viewInWindowsY);


                        }
                    });
                }


            }
        });


    }

    private void setNowSelectView(InchingOperaRelativeLayout operaViewRL) {

//        hideControlView();
        InchingOperaRelativeLayout nowSelectView = getNowSelectView();
        if (nowSelectView != null) {
            nowSelectView.hideControlView();
        }
        this.nowSelectView = operaViewRL;
        operaViewRL.setControlViewShow();
    }

    /**
     * 获取 包含微调控件的baseVessel
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    @NonNull
    public InchingOperaRelativeLayout getInchingRL(int x, int y, int w, int h) {

        RelativeLayout.LayoutParams rlLp = getInchingRLLP(x, y, w, h);

        RelativeLayout.LayoutParams dottedLp = getInchingRLLP(w, h);
        InchingOperaRelativeLayout operaViewRL = new InchingOperaRelativeLayout(CommoninchingActivity.this);
        operaViewRL.setClipChildren(false);
        //加入边框线控件
        InchingDottedView inchingDottedView = new InchingDottedView(CommoninchingActivity.this);
        inchingDottedView.setId(R.id.inching_dotted_line_view);
        inchingDottedView.postInvalidate();

        operaViewRL.addView(inchingDottedView, dottedLp);

        operaViewRL.setThisDottedLineIsShow(0);

        //加入控制边框控件
        NewInchingFrontBoderView controlBroad = new NewInchingFrontBoderView(CommoninchingActivity.this);
        controlBroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w = 0;
                int h = 0;

                //当再次点击时要取现在的数据
                InchingOutLineAllTypeV10Bean data = operaViewRL.getData();
//                if (data instanceof InchingOutLineImgBean) {
//                    InchingOutLineImgBean nowData = (InchingOutLineImgBean) data;
//                    w = (int) Math.ceil(nowData.getPos().getW());
//                    h = (int) Math.ceil(nowData.getPos().getH());
//                } else if (data instanceof InchingOutLineTextOrShapeBean) {
//                    InchingOutLineTextOrShapeBean nowData = (InchingOutLineTextOrShapeBean) data;
//                    w = (int) Math.ceil(nowData.getPos().getW());
//                    h = (int) Math.ceil(nowData.getPos().getH());
//                } else if (data instanceof InchingOutLineTableBean) {
//                    InchingOutLineTableBean nowData = (InchingOutLineTableBean) data;
//                    w = (int) Math.ceil(nowData.getPos().getW());
//                    h = (int) Math.ceil(nowData.getPos().getH());
//                }
//
//                controlBroad.refreshFontBoderView(0, 0, w, h);
                int[] locationMove = new int[2];
                operaViewRL.getLocationOnScreen(locationMove);//获取在整个屏幕内的绝对坐标
                int viewInWindowsX = locationMove[0];
                int viewInWindowsY = locationMove[1];
                showPopWindow(data, viewInWindowsX, viewInWindowsY);
            }
        });


        controlBroad.setNowPageIndex(nowPageIndex);

        controlBroad.setId(R.id.inching_control);
        controlBroad.setNowSelectView(operaViewRL);
        controlBroad.setBaseArea(rlInchingBaseArea);
        RelativeLayout.LayoutParams broadLp = getInchingRLLP(w + controlW, h + controlH);
        operaViewRL.addView(controlBroad, broadLp);

        operaViewRL.hideControlView();
        if (rlInchingBaseArea != null) {
            rlInchingBaseArea.addView(operaViewRL, rlLp);

        }
        return operaViewRL;
    }

    /**
     * 获取下级控件 例如可操作图片 文字 表格 加入baseVessel的参数
     *
     * @param w
     * @param h
     * @return
     */
    public RelativeLayout.LayoutParams getInchingRLLP(int w, int h) {
        RelativeLayout.LayoutParams rlLp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlLp.width = w;
        rlLp.height = h;

        return rlLp;
    }


    /**
     * 获取下级控件 例如可操作图片 文字 表格 加入baseVessel的参数
     *
     * @param w
     * @param h
     * @return
     */
    public RelativeLayout.LayoutParams getInchingRLLP(int x, int y, int w, int h) {
        RelativeLayout.LayoutParams rlLp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlLp.width = w + controlW / 2;
        rlLp.height = h + controlH / 2;
        rlLp.leftMargin = x;
        rlLp.topMargin = y;

        return rlLp;
    }

    @Override
    public void createTextOrShape(InchingOutLineTextOrShapeBean data) {
        int w = (int) Math.ceil(data.getPos().getW());
        int h = (int) Math.ceil(data.getPos().getH());
        int x = (int) data.getPos().getX();
        int y = (int) data.getPos().getY();
        int editable = data.getEditable();


        InchingOperaRelativeLayout inchingRL = getInchingRL(x, y, w, h);
        RelativeLayout.LayoutParams inchingRLLP = getInchingRLLP(w, h);

        inchingRL.setData(data);

        InchingOperaViewImg textOrShapView = new InchingOperaViewImg(this);
        textOrShapView.setId(R.id.inching_can_opar_text);
        Bitmap bitmap = inchingRL.getTextOrShapBitMap(data);
        textOrShapView.setImageBitmap(bitmap);

        inchingRL.addView(textOrShapView, inchingRLLP);

        if (editable == 1) {

            inchingRL.setThisDottedLineIsShow(1);

            inchingRL.setData(data);
            inchingRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inchingRL.setThisDottedLineIsShow(0);
                    InchingOperaRelativeLayout nowSelectView = getNowSelectView();
                    if (nowSelectView != null &&nowSelectView != inchingRL) {
                            L_.i("nowSelectView != inchingRL");
                            nowSelectView.setThisDottedLineIsShow(1);
                    }

                    setNowSelectView(inchingRL);

                    //当再次点击时要取现在的数据
                    InchingOutLineTextOrShapeBean nowData = (InchingOutLineTextOrShapeBean) inchingRL.getData();
                    int w = (int) Math.ceil(nowData.getPos().getW());
                    int h = (int) Math.ceil(nowData.getPos().getH());
                    int x = (int) nowData.getPos().getX();
                    int y = (int) nowData.getPos().getY();
                    // TODO: 2017/12/20  
//                    getNowSelectView().refreshFontBoderView(x, y, w, h);

                    if (textOperaPanel != null) {
                        textOperaPanel.setNowSelectType(nowData.getFn());

                    }

                    int[] locationMove = new int[2];
                    inchingRL.getLocationOnScreen(locationMove);//获取在整个屏幕内的绝对坐标
                    int viewInWindowsX = locationMove[0];
                    int viewInWindowsY = locationMove[1];
                    showPopWindow(nowData, viewInWindowsX, viewInWindowsY);


                }
            });


        }

    }

    @Override
    public void createTable(InchingOutLineTableBean tableBean) {
        PosBean tablePos = tableBean.getPos();
        int w = (int) Math.ceil(tablePos.getW());
        int h = (int) Math.ceil(tablePos.getH());
        int x = (int) tablePos.getX();
        int y = (int) tablePos.getY();

        InchingOperaRelativeLayout inchingRL = getInchingRL(x, y, w, h);
        RelativeLayout.LayoutParams inchingRLLP = getInchingRLLP(w, h);

        InchingOperaViewImg tableView = new InchingOperaViewImg(this);
        tableView.setId(R.id.inching_can_opar_table);
        Bitmap bitmap = inchingRL.getTableBitMap(tableBean);
        tableView.setImageBitmap(bitmap);
        inchingRL.setData(tableBean);
        inchingRL.addView(tableView, inchingRLLP);

        int editable = tableBean.getEditable();
        if (editable == 1) {
            inchingRL.setThisDottedLineIsShow(1);
            inchingRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inchingRL.setThisDottedLineIsShow(0);
                    InchingOperaRelativeLayout nowSelectView = getNowSelectView();
                    if (nowSelectView != null && nowSelectView != inchingRL) {
                        nowSelectView.setThisDottedLineIsShow(1);
                    }
                    //设定当前选中的控件
                    setNowSelectView(inchingRL);
                    //当再次点击时要取现在的数据 使控制控件和当前view宽高相符
                    InchingOutLineTableBean nowData = (InchingOutLineTableBean) inchingRL.getData();
                    int w = (int) Math.ceil(nowData.getPos().getW());
                    int h = (int) Math.ceil(nowData.getPos().getH());
                    int x = (int) nowData.getPos().getX();
                    int y = (int) nowData.getPos().getY();
                    // TODO: 2017/12/20  
//                    getNowSelectView().refreshFontBoderView(x, y, w, h);

                    int[] locationMove = new int[2];
                    tableView.getLocationOnScreen(locationMove);//获取在整个屏幕内的绝对坐标
                    int viewInWindowsX = locationMove[0];
                    int viewInWindowsY = locationMove[1];
                    showPopWindow(nowData, viewInWindowsX, viewInWindowsY);

                }
            });
        }

    }

    @Override
    public void jugleIsNeedShowPageSwitch(int pageCount) {
        if (llSwitchPage != null) {
            if (pageCount < 2) {
                llSwitchPage.setVisibility(View.GONE);
            } else {
                llSwitchPage.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void clearNowPageChild() {
        if (rlInchingBaseArea != null) {
            rlInchingBaseArea.removeAllViews();
        }

        if (rlRemoveBroad != null) {
            rlRemoveBroad.removeView(rlRemoveBroad.findViewById(R.id.inching_control));
        }
    }


    /**
     * 切换页时会调用
     *
     * @param nowPageIndex
     */
    @Override
    public void setNowPageIndex(int nowPageIndex) {
        this.nowPageIndex = nowPageIndex;
//        NewInchingFrontBoderView boderView = new NewInchingFrontBoderView(this);
//        boderView.setId(R.id.inching_control);
//        if (rlInchingBaseArea != null) {
//            rlRemoveBroad.addView(boderView);
//            boderView.setBaseArea(rlInchingBaseArea);
//            boderView.setNowPageIndex(nowPageIndex);
//
//            //用于点击过一次 取消了pw 再次点击事件会被控制控件拦截的情况
//            boderView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    InchingOperaRelativeLayout nowSelectView = boderView.getNowSelectView();
//                    if (nowSelectView != null) {
//                        int[] xy = new int[2];
//                        nowSelectView.getLocationOnScreen(xy);
//
//                        InchingOutLineAllTypeV10Bean data = nowSelectView.getData();
//                        showPopWindow(data, xy[0], xy[1]);
//                    }
//                }
//            });
//            hideControlView();
//
//        }
    }


    @Override
    public InchingOperaRelativeLayout getNowSelectView() {

        return nowSelectView;
    }

    @Override
    public void hideControlView() {
        InchingOperaRelativeLayout nowSelectView = getNowSelectView();
        if (nowSelectView != null) {
            nowSelectView.setThisDottedLineIsShow(1);
            nowSelectView.hideControlView();
        }


    }

    /**
     * 显示操作的popwindow
     */
    @Override
    public void showPopWindow(InchingOutLineAllTypeV10Bean allTypeData, int x, int y) {
        if (pw == null) {
            return;
        }
        if (pw.isShowing()) {
            return;
        }

        if (allTypeData instanceof InchingOutLineTextOrShapeBean) {
            InchingOutLineTextOrShapeBean data = (InchingOutLineTextOrShapeBean) allTypeData;
            int id = data.getId();


            String objectsForAppForJava = FineTuneRender.getObjectsForAppForJava(nowPageIndex, id);
            ArrayList<InchingOutLineTextOrShapeBean> list = CommonUtils.jsonToArrayList(objectsForAppForJava, InchingOutLineTextOrShapeBean.class);
            InchingOutLineTextOrShapeBean inchingOutLineTextOrShapeBean = list.get(0);
            //这个控件只是操作文字的
            textOperaPanel.setDaGangData(inchingOutLineTextOrShapeBean);


            pw.showTextType();
            pw.setFontStyleListener(new InchingOperaPW.OnPwTabOnClick() {
                @Override
                public void onTabClick(String type) {
                    pw.dismiss();
                    if (type.equals(AppConst.PW_TV_DEL)) {
                        L_.i("PW_TV_DEL");


                        String linkObjStatusForJava = FineTuneRender.getLinkObjStatusForJava(nowPageIndex, id);
                        InchingLinkObjBean linkObjBean = new Gson().fromJson(linkObjStatusForJava, InchingLinkObjBean.class);
                        if (linkObjBean != null) {
                            List<Integer> idList = linkObjBean.getId();

                            L_.i(linkObjStatusForJava);
                            for (int i = 0; i < rlInchingBaseArea.getChildCount(); i++) {

                                View childAt = rlInchingBaseArea.getChildAt(i);
                                if (childAt != null && childAt instanceof InchingOperaRelativeLayout) {
                                    InchingOperaRelativeLayout rl = (InchingOperaRelativeLayout) rlInchingBaseArea.getChildAt(i);

                                    for (int j = 0; j < idList.size(); j++) {
                                        Integer integer = idList.get(j);
                                        L_.i("integer===" + integer + "rl.getFineTuneRenderId()===" + rl.getFineTuneRenderId());
                                        if (rl.getFineTuneRenderId() == integer.intValue()) {
                                            long isSuccess = FineTuneRender.deleteObjectForJava(nowPageIndex, integer.intValue());
                                            if (isSuccess == 0) {

                                                if (rl != null && rlInchingBaseArea != null) {
                                                    rlInchingBaseArea.removeView(rl);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }


                        mPresenter.deleteViewData(nowPageIndex, id);

                    } else if (type.equals(AppConst.PW_TV_EDIT)) {
                        isNeedTransInChangeColor();
                        L_.i("PW_TV_EDIT");
                        textOperaPanel.setThisShow();
                        textOperaPanel.wenbenTabClick();


                    }
                }
            });

        } else if (allTypeData instanceof InchingOutLineTableBean) {
            InchingOutLineTableBean data = (InchingOutLineTableBean) allTypeData;
            int id = data.getId();


            ArrayList<ArrayList<String>> tableSourceData = data.getTableObjectDataSource();

            InchingDataSingleCase.INSTANCE.setNowTableData(tableSourceData);

            pw.showTabType();
            pw.setFontStyleListener(new InchingOperaPW.OnPwTabOnClick() {
                @Override
                public void onTabClick(String type) {
                    pw.dismiss();
                    if (type.equals(AppConst.PW_TAB_EDIT)) {
                        //跳转到表格的编辑界面
                        Intent intent = new Intent(CommoninchingActivity.this, TableeditActivity.class);
                        intent.putExtra("pageIndex", nowPageIndex);
                        intent.putExtra("objId", id);
                        startActivity(intent);
                    } else if (type.equals(AppConst.PW_TAB_DEL)) {
                        mPresenter.deleteViewData(nowPageIndex, id);
                    } else if (type.equals(AppConst.PW_TAB_COLOR)) {

                        tableOperaPanel.setVisibility(View.VISIBLE);
                        rlStub.setVisibility(View.VISIBLE);
                        rlStub.bringToFront();
                        tableOperaPanel.bringToFront();
                        isNeedTransTabColor();
                    }

                }
            });
        } else if (allTypeData instanceof InchingOutLineImgBean) {
            pw.showImgType();
            InchingOutLineImgBean data = (InchingOutLineImgBean) allTypeData;
            int id = data.getId();
            pw.setFontStyleListener(new InchingOperaPW.OnPwTabOnClick() {
                @Override
                public void onTabClick(String type) {
                    pw.dismiss();

                    if (type.equals(AppConst.PW_IMG_EDIT)) {
                        //去相册选图
                        //吊起取相册的逻辑
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, AppConst.TOPHOTO);

                    } else if (type.equals(AppConst.PW_IMG_DEL)) {
                        mPresenter.deleteViewData(nowPageIndex, id);
                    }
                }
            });
        }

        //如果y 显示显示的上边界处于标题栏之上让这个东西显示在该控件下面
        int topY = UIUtils.getStatusBarHeight(CommoninchingActivity.this) + UIUtils.dp2px(50);
        int showY = y - UIUtils.dp2px(30);

        int height = getNowSelectView().getHeight();
        if (showY < topY) {
            showY = y + height;
        }

        //显示popwindow y-当前popwindow的高度
        pw.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, x, showY);
        L_.i("showPopWindow===x" + x + "y===" + y);
    }

    private void isNeedTransTabColor() {
        int[] pos = {0, 0};
        getNowSelectView().getLocationOnScreen(pos);
        int height = getNowSelectView().getHeight();
        L_.i(" textInNowSelectView.getLocationOnScreen(pos);===" + pos[1] + "height===" + height);
        //如果在屏幕中的位置加高度在弹出的dialog之下 那么用动画把控件移动到可视区域
        int i1 = pos[1] + height;
        int changePanleHeiht = ((UIUtils.WHD()[0] - UIUtils.dp2px(144)) / 6 + UIUtils.dp2px(2) + UIUtils.dp2px(20)) * 4;
        //表格和文本的弹出框不是一个
        int i2 = UIUtils.WHD()[1] - changePanleHeiht - UIUtils.dp2px(40);
        L_.i("i1===" + i1 + "i2===" + i2);
        if (i1 > i2) {
            L_.i("i1-i2===" + (i1 - i2));
//            int trans = -(i1 - i2 + height);
            int trans = -(i1 - i2);
            CommonUtils.transAnimation(rlRemoveBroad, 0, trans);
        }

    }

    @Override
    public void deleteView() {
        getNowSelectView().setVisibility(View.GONE);
        InchingOperaRelativeLayout nowSelectView = getNowSelectView();
        if (nowSelectView != null && rlInchingBaseArea != null) {
            rlInchingBaseArea.removeView(nowSelectView);

        }
    }

    @Override
    public void upDateTableView(InchingOutLineTableBean data) {
        InchingOperaRelativeLayout nowSelectView = getNowSelectView();
        // TODO: 2017/12/6  
        nowSelectView.chagneValueUpdate(data);
    }

    @Override
    public void hidePopWindow() {

    }

    /**
     * 修改文字后更新文字
     *
     * @param data
     */
    @Override
    public void upDateTextOrShapeView(InchingOutLineTextOrShapeBean data) {
        textOperaPanel.setDaGangData(data);
        InchingOperaRelativeLayout nowSelectView = getNowSelectView();
        nowSelectView.chagneValueUpdate(data);

    }

    /**
     * 此处用于更新表格
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(String msg) {
        if (msg.equals(AppConst.REFRESH_TABLE)) {
            L_.i("REFRESH_TABLE");
            String texts = FineTuneRender.getPageTextPropertyForJava(nowPageIndex,
                    getNowSelectView().getFineTuneRenderId(), 1);

            //在上个页面更新了

            mPresenter.updateInchingView(getNowSelectView().getFineTuneRenderId(), texts, AppConst.INCHING_TABLEVIEW_TYPE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
        EventBus.getDefault().unregister(this);

    }


    /**
     * 从不同的界面返回 执行不同的操作
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == AppConst.TOPHOTO) {
                changeImg(data);
            } else if (requestCode == AppConst.CHANGE_IMG_RESULT) {
                changeImgBack(data);
            } else if (requestCode == AppConst.TO_ADD_IMAGE) {
                addImage(data);
            } else if (requestCode == AppConst.TO_ADD_IMAGE_BACK) {
                addInchingImgView(data);
            }
        }
    }

    private void addImage(Intent data) {
        Intent intent = CommonUtils.createToPhotoIntent(data, CommoninchingActivity.this);
        if (intent == null) {
            T_.showCustomToast("选择的文件不是图片", true);

            return;
        }
        //拿大纲中的通过id mask
        intent.putExtra(AppConst.NODE_IS_PRINT, true);
        startActivityForResult(intent, AppConst.TO_ADD_IMAGE_BACK);
    }

    @Override
    public void changeImgBack(Intent data) {
        String path = data.getStringExtra(AppConst.LOCAL_PHOTO_PATH);
        String pathName = data.getStringExtra(AppConst.LOCAL_PHOTO_PATH_NAME);
        int bitW = data.getIntExtra(AppConst.PHOTO_BITMAP_W, -1);
        int bitH = data.getIntExtra(AppConst.PHOTO_BITMAP_H, -1);


        InchingOperaRelativeLayout nowSelectView = getNowSelectView();
        int fId = getNowSelectView().getFineTuneRenderId();

        String imgData = FineTuneRender.changeImageContentForJava(nowPageIndex, fId, pathName, bitW, bitH);

        InchingOutLineImgBean list = new Gson().fromJson(imgData, InchingOutLineImgBean.class);

        InchingOutLineAllTypeV10Bean inchingOutLineAllTypeV10Bean = list;
        nowSelectView.chagneValueUpdate(inchingOutLineAllTypeV10Bean);
        L_.i("PHOTOREturn===" + path + "===pathName" + pathName + "bitW===" + bitW + "bitH===" + bitH);
    }

    /**
     * 从相册中选择更换图片
     *
     * @param data
     */
    @Override
    public void changeImg(Intent data) {
        Intent intent = CommonUtils.createToPhotoIntent(data, CommoninchingActivity.this);
        if (intent == null) {
            T_.showCustomToast("选择的文件不是图片", true);
            return;
        }
        //拿大纲中的通过id mask

        int id = getNowSelectView().getFineTuneRenderId();

        String objectsForAppForJava = FineTuneRender.getObjectsForAppForJava(nowPageIndex, id);
        ArrayList<InchingOutLineImgBean> list = CommonUtils.jsonToArrayList(objectsForAppForJava, InchingOutLineImgBean.class);

        CompoundPathsBean mask = list.get(0).getMask().get(0);
        //为了传入宽高
        Gson gson = new Gson();
        String maskJson = gson.toJson(mask);


        intent.putExtra(AppConst.NODE_IS_PRINT, true);

        //向ClipAct传递mask
        intent.putExtra(AppConst.TOPHOTO_MASK, maskJson);
        startActivityForResult(intent, AppConst.CHANGE_IMG_RESULT);
    }

    @Override
    public void addInchingTextView() {
        int count = 0;
        int ftMaxTextCnt = InchingDataSingleCase.INSTANCE.getLengthControl().getFTMaxTextCnt();

        //当前页可操作控件总数为20
        if (rlInchingBaseArea != null) {
            for (int i = 0; i < rlInchingBaseArea.getChildCount(); i++) {
                View childAt = rlInchingBaseArea.getChildAt(i);
                if (childAt != null && childAt instanceof InchingOperaRelativeLayout) {
                    InchingOperaRelativeLayout rl = (InchingOperaRelativeLayout) childAt;
                    if (rl.getIsEnable() == 1) {
                        if (rl.getData() instanceof InchingOutLineTextOrShapeBean) {
                            count++;
                        }
                    }
                }
            }
        }
        L_.i("addInchingTextView cout===" + count);
        if (count > ftMaxTextCnt) {
            DialogUtils.getInstance((new DialogUtils.Builder().setContext("文本数量已达到上限").setCancletext("确定").setConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.getInstance().dismiss();
                }
            }).setPlatform(DialogUtils.DialogPlatform.ONE_BTN))).builder(this);
            return;
        }


        long id = FineTuneRender.addTextForJava(nowPageIndex, "");
        String json = FineTuneRender.getObjectsForAppForJava(nowPageIndex, id);
        ArrayList<InchingOutLineTextOrShapeBean> list = CommonUtils.jsonToArrayList(json, InchingOutLineTextOrShapeBean.class);

        createTextOrShape(list.get(0));
    }

    @Override
    public void addInchingImgView(Intent data) {


        String path = data.getStringExtra(AppConst.LOCAL_PHOTO_PATH);
        String pathName = data.getStringExtra(AppConst.LOCAL_PHOTO_PATH_NAME);
        int bitW = data.getIntExtra(AppConst.PHOTO_BITMAP_W, -1);
        int bitH = data.getIntExtra(AppConst.PHOTO_BITMAP_H, -1);

        long id = FineTuneRender.addImageForJava(nowPageIndex, pathName, bitW, bitH);

        String json = FineTuneRender.getObjectsForAppForJava(nowPageIndex, id);
        ArrayList<InchingOutLineImgBean> list = CommonUtils.jsonToArrayList(json, InchingOutLineImgBean.class);
        createOperaImage(list.get(0));

    }

    @Override
    public void createEnabledEreaData(PosBean videoFrame) {

    }

    @Override
    public void toUpVideoBgSuccess(String result) {
        dismissProgressDialog();
        EventBus.getDefault().post(1200);
        finish();
    }

    /**
     * 动态请求权限
     */
    @TargetApi(23)
    private void requestPermissions() {
        requestPermissions(new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO
                , Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_SETTINGS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
        }, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
            }
        });
    }

    private void isNeedTransInChangeColor() {
        int[] pos = {0, 0};
        getNowSelectView().getLocationOnScreen(pos);
        int height = getNowSelectView().getHeight();
        L_.i(" textInNowSelectView.getLocationOnScreen(pos);===" + pos[1] + "height===" + height);
        //如果在屏幕中的位置加高度在弹出的dialog之下 那么用动画把控件移动到可视区域
        int i1 = pos[1] + height;
        int saveHeight = SpUtils.getInt(SPCTag.KEYBROAD_HEIGHT, 0);

        int i2 = UIUtils.WHD()[1] - saveHeight - UIUtils.dp2px(103);
        L_.i("i1===" + i1 + "i2===" + i2);
        if (i1 > i2) {
            L_.i("i1-i2===" + (i1 - i2));
//            int trans = -(i1 - i2 + height);
            int trans = -(i1 - i2);
            CommonUtils.transAnimation(rlRemoveBroad, 0, trans);
        }
    }

    @Override
    public void onTableColorChange(int r, int g, int b, String type) {
        L_.i("onTableColorChangeR===" + r + "g===" + g + "b===" + b);

        int objId = getNowSelectView().getFineTuneRenderId();

        String dataJson = FineTuneRender.changeTableTextColorForJava(nowPageIndex, objId, r, g, b, 0.0f);

        InchingOutLineAllTypeV10Bean allTypeData = new GsonBuilder().registerTypeAdapter
                (InchingOutLineAllTypeV10Bean.class, new InchingOutLineAnalysisRules()).create()
                .fromJson(dataJson, InchingOutLineAllTypeV10Bean.class);
        if (allTypeData instanceof InchingOutLineTableBean) {
            InchingOutLineTableBean data = (InchingOutLineTableBean) allTypeData;
            upDateTableView(data);
        }
    }

    @Override
    public void onTableColorEnsure() {
        L_.i("onTableColorEnsure");
    }

    @Override
    public void onTableColorCancel() {
        L_.i("onTableColorCancel");
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


}
