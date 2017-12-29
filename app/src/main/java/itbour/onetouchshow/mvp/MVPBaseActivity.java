package itbour.onetouchshow.mvp;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;

import java.lang.reflect.ParameterizedType;

import itbour.onetouchshow.R;
import itbour.onetouchshow.base.ABaseActivity;
import itbour.onetouchshow.helper.SystemBarHelper;
import itbour.onetouchshow.utils.UIUtils;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public abstract class MVPBaseActivity<V extends BaseView,T extends BasePresenterImpl<V>> extends ABaseActivity implements BaseView{

    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        mPresenter = getInstance(this,1);
        if (mPresenter!=null){
            mPresenter.attachView((V) this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
        mPresenter=null;
    }

    public  <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
          //  e.printStackTrace();
        } catch (IllegalAccessException e) {
          //  e.printStackTrace();
        } catch (ClassCastException e) {
           // e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.System.canWrite(this);
        }
        setTopLeftButton(R.mipmap.return_icon).
                setTitleTextColor(UIUtils.getColor(R.color.white)).
                setBackgroundColor(UIUtils.getColor(R.color.black));
        getToolBarView().getLeftimageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setImmersionBarBlack();
    }

    protected void setImmersionBarBlack(){
        //import com.githang.statusbar.StatusBarCompat;
        StatusBarCompat.setStatusBarColor(this, UIUtils.getColor(R.color.apptheme));
       // SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.apptheme),0);
    }


    @Override
    protected void initView() {

    }

    @Override
    public int getFragmentContentId() {
        return 0;
    }

    protected void loginResultOpen(Class<?> pClass) {
//        LogingStatusListener.getInstance().checkLogin(new LogingStatusListener.LoginForCallBack() {
//            @Override
//            public void callBack() {
//                openActivity(pClass);
//            }
//        });
    }

}
