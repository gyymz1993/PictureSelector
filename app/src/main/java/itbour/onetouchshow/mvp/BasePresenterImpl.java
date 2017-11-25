package itbour.onetouchshow.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    protected V mvpView;
    protected Reference<V> mViewRef;

    @Override
    public void attachView(V view) {
        this.mvpView = view;
        mViewRef = new WeakReference<>(mvpView);
    }

    @Override
    public void detachView() {
        this.mvpView = null;
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
