package itbour.onetouchshow.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import butterknife.BindView;
import itbour.onetouchshow.R;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.mvp.BaseView;
import itbour.onetouchshow.mvp.MVPBaseActivity;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/10/16 10:09
 */

public abstract class BaseWebMvpActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseActivity<V, T> implements BaseView {

    @BindView(R.id.container)
    LinearLayout container;

    protected AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void afterCreate(Bundle bundle) {

    }

    protected AgentWeb.CommonAgentBuilder getWebBuilder() {
        long p = System.currentTimeMillis();
        return AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1)).useDefaultIndicator()
                .defaultProgressBarColor().setReceivedTitleCallback(mCallback).setWebChromeClient(mWebChromeClient).setWebViewClient(mWebViewClient).setSecutityType(AgentWeb.SecurityType.strict);
        // .setWebLayout(new WebLayout(this))
        //preAgentWeb.go(getUrl());
        // long n = System.currentTimeMillis();
        // Log.i("Info", "init used time:" + (n - p));
    }


    protected WebView getWebView() {
        WebView mWebView = null;
        if (mAgentWeb != null) {
            mWebView = mAgentWeb.getWebCreator().get();
        }
        return mWebView;

    }

    public void initAgentWeb(String url) {
        mAgentWeb = getWebBuilder().createAgentWeb().ready().go(url);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","progress:"+newProgress);
        }
    };

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = (view, title) -> showTitle(title);

    protected abstract void showTitle(String string);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }
}
