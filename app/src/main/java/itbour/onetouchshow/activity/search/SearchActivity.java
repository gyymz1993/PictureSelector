package itbour.onetouchshow.activity.search;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.R;
import itbour.onetouchshow.custom.ClearEditTextView;
import itbour.onetouchshow.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SearchActivity extends MVPBaseActivity<SearchContract.View, SearchPresenter> implements SearchContract.View {

    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.iv_homepage_search)
    ImageView ivHomepageSearch;
    @BindView(R.id.et_hot_search)
    ClearEditTextView etHotSearch;
    @BindView(R.id.tv_search_cancle)
    TextView tvSearchCancle;

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setImmersionBarBlack();
    }


    @OnClick({R.id.iv_search_back, R.id.iv_homepage_search/*, R.id.et_hot_search*/, R.id.tv_search_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                finish();
                break;
            case R.id.iv_homepage_search:
                break;
         /*   case R.id.et_hot_search:
                L_.i("et_hot_search");
                break;*/
            case R.id.tv_search_cancle:
                break;
            default:
                break;
        }
    }
}
