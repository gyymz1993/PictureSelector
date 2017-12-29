package itbour.onetouchshow.activity.search;


import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.adapter.BaseRecyclerHolder;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.App;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.login.LoginActivity;
import itbour.onetouchshow.activity.searchlist.SearchListActivity;
import itbour.onetouchshow.base.BaseRefreshAdapter;
import itbour.onetouchshow.base.SPCTag;
import itbour.onetouchshow.bean.NoDataResult;
import itbour.onetouchshow.custom.ClearEditTextView;
import itbour.onetouchshow.listener.SoftKeyBoardListener;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.rvmanager.FlowLayoutManager;
import itbour.onetouchshow.singlecase.InchingDataSingleCase;
import itbour.onetouchshow.utils.BroadcastAction;
import itbour.onetouchshow.utils.FinshBroadcast;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.UIUtils;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SearchActivity extends MVPBaseActivity<SearchContract.View, SearchPresenter> implements SearchContract.View {

    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.et_hot_search)
    ClearEditTextView etHotSearch;
    @BindView(R.id.tv_search_cancle)
    TextView tvSearchCancle;
    @BindView(R.id.id_rv_his)
    RecyclerView recyclerHis;
    @BindView(R.id.id_rv_tag)
    RecyclerView recyclerTag;
    @BindView(R.id.id_include_search)
    LinearLayout searchLayout;
    @BindView(R.id.id_ly_hisLayou)
    LinearLayout lyhisLayou;
    @BindView(R.id.id_ig_delete_his)
    ImageView idDelHis;

    public String keyWork;
    HotTagAdapter hisAdapter;
    private List<String> hisList;
    private List<String> mHotData;

    @Override
    public void loadSucceed(String result) {
        Bundle bundle = new Bundle();
        bundle.putString("key", result);
        bundle.putString("keywork", keyWork);
        openActivity(SearchListActivity.class, bundle);
    }

    @Override
    public void loadFaild(String error) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("noData", true);
        bundle.putString("keywork", keyWork);
        openActivity(SearchListActivity.class, bundle);
    }

    @Override
    protected void initView() {
        super.initView();
        searchLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        //  registerExitReceiver();
        EventBus.getDefault().register(this);
        mPresenter.lodaHotTag();
        initSeachBar();
        initKeyBroadListener();
        String hisKey = SpUtils.getInstance().getString("hisKey");
        if (!TextUtils.isEmpty(hisKey)) {
            NoDataResult hisDataBean = new Gson().fromJson(hisKey, NoDataResult.class);
            hisList = hisDataBean.getList();
        }
        if (hisList == null || hisList.size() == 0) {
            hisList = new ArrayList<>();
            lyhisLayou.setVisibility(View.GONE);
        }
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        //GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerHis.setLayoutManager(flowLayoutManager);
        hisAdapter = new HotTagAdapter(getApplicationContext(), hisList, R.layout.item_design_type_adapter, "hisAdapter");
        recyclerHis.setAdapter(hisAdapter);
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
        //unRegisterExitReceiver();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UIUtils.showKeyboard(etHotSearch);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //etHotSearch.clearFocus();
    }


    /**
     * threadMode 执行的线程方式
     * priority 执行的优先级
     * sticky 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 50, sticky = true)
    public void doExit(String code) {
        //  L_.e("支付成功 去水印");
        // 如果有一个地方用 EventBus 发送一个 String 对象，那么这个方法就会被执行
        if (code.equals("exit")) {
            finish();
        }

    }


    private void initKeyBroadListener() {

        SoftKeyBoardListener.setListener(SearchActivity.this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        etHotSearch.setFocusable(true);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        etHotSearch.clearFocus();
                    }
                });
    }


    @OnClick({R.id.iv_search_back, R.id.tv_search_cancle, R.id.id_ig_delete_his})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                finish();
                break;
            case R.id.tv_search_cancle:
                if (!TextUtils.isEmpty(etHotSearch.getText().toString())) {
                    keyWork = etHotSearch.getText().toString();
                } else {
                    if (mHotData != null) {
                        keyWork = mHotData.get(0);
                    }
                }
                mPresenter.searchInfo(keyWork);
                saveHisAndNotifty();
                break;
            case R.id.id_ig_delete_his:
                SpUtils.getInstance().saveString("hisKey", "");
                lyhisLayou.setVisibility(View.GONE);
                if (hisList != null) {
                    hisList.clear();
                }
                break;
            default:
                break;
        }
    }

    private void saveHisAndNotifty() {
        if (hisList.size() > 0) {
            for (int i = 0; i < hisList.size(); i++) {
                if (hisList.get(i).equals(keyWork)) {
                    hisList.remove(i);
                }
            }
        }
        if (hisList.size() > 9) {
            hisList.remove(9);
        }
        hisList.add(0, keyWork);
        //封装转JSON
        NoDataResult<String> stringNoDataResult = new NoDataResult<>();
        stringNoDataResult.setList(hisList);
        String json = new Gson().toJson(stringNoDataResult);
        SpUtils.getInstance().saveString("hisKey", json);
        lyhisLayou.setVisibility(View.VISIBLE);
        if (hisAdapter != null) {
            hisAdapter.notifyDataSetChanged(hisList);
        }

    }


    private void initSeachBar() {
        //当点击输入法的搜索按钮 两个入口 界面的搜索按钮和输入法的搜索按钮
        etHotSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(etHotSearch.getText().toString())) {
                        keyWork = etHotSearch.getText().toString();
                    } else {
                        keyWork = mHotData.get(0);
                        etHotSearch.setText(keyWork);
                        etHotSearch.setSelection(keyWork.length());//将光标移至文字末尾
                    }
                    mPresenter.searchInfo(keyWork);
                    saveHisAndNotifty();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void loadHotTagSuccess(List<String> hotData) {
        // GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHotData = hotData;
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        recyclerTag.setLayoutManager(flowLayoutManager);
        HotTagAdapter hotTagAdapter = new HotTagAdapter(getApplicationContext(), hotData, R.layout.item_design_type_adapter, "HotTagAdapter");
        recyclerTag.setAdapter(hotTagAdapter);
    }


    private class HotTagAdapter extends BaseRefreshAdapter<String> {

        private String tag;

        public HotTagAdapter(Context context, List<String> datas, int itemLayoutId, String tag) {
            super(context, datas, itemLayoutId);
            this.tag = tag;
        }

        @Override
        public void notifyDataSetChanged(List<String> list) {
            super.notifyDataSetChanged(list);
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, String childrenBean, int position) {
            TextView textView = var1.getView(R.id.id_tv_type_name);
            textView.setText(childrenBean);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keyWork = childrenBean;
                    mPresenter.searchInfo(keyWork);
                    etHotSearch.setText(keyWork);
                    etHotSearch.setSelection(keyWork.length());//将光标移至文字末尾
                    if (tag.equals("HotTagAdapter")) {
                        saveHisAndNotifty();
                    }
                }
            });
        }
    }

    @Override
    public void loadHotTagFail() {

    }
}
