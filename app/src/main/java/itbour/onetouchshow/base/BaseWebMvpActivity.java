package itbour.onetouchshow.base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import butterknife.BindView;
import itbour.onetouchshow.App;
import itbour.onetouchshow.R;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.mvp.BaseView;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

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


    protected AgentWeb.CommonAgentBuilder getWebBuilder() {
        long p = System.currentTimeMillis();
        return AgentWeb.with(this)
                //new LinearLayout.LayoutParams(-1, -1)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                 //.defaultProgressBarColor()
                .setIndicatorColor(UIUtils.getColor(R.color.apptheme))
                .setReceivedTitleCallback(mCallback).
                        setWebChromeClient(mWebChromeClient).
                        setWebViewClient(mWebViewClient).
                        setSecutityType(AgentWeb.SecurityType.strict);
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
        if (App.testWhendebug()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWebView().setWebContentsDebuggingEnabled(true);
            }
        }
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

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            T_.showCustomToast("暂无网络");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            T_.showCustomToast("暂无网络");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            T_.showCustomToast("暂无网络");
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
        if (mAgentWeb == null) {
            return super.onKeyDown(keyCode, event);
        }
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
