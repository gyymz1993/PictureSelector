package itbour.onetouchshow.activity.myworks;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andview.adapter.BaseRecyclerHolder;
import com.ys.uilibrary.swip.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

import itbour.onetouchshow.R;
import itbour.onetouchshow.base.BaseRefreshActivity;
import itbour.onetouchshow.base.BaseRefreshAdapter;


/**
 * MVPPlugin
 * 我的作品
 */

public class MyworksActivity extends BaseRefreshActivity<MyworksContract.View, MyworksPresenter> implements MyworksContract.View {

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }


    @Override
    protected void initTitle() {
        super.initTitle();
        setTitleText("我的作品");
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public BaseRefreshAdapter getBaseRefreshAdapter() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("ooo" + i);
        }
        return new WorkAdapter(getApplicationContext(), data, R.layout.item_work_adapter);
    }

    @Override
    public View getHeadView() {
        return null;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    public class WorkAdapter extends BaseRefreshAdapter<String> {

        public WorkAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, String var2, int var3) {
            SwipeMenuLayout swipeMenuLayout = var1.getView(R.id.id_root_swp);
            Button btnDelete = var1.getView(R.id.btnDelete);
            btnDelete.setOnClickListener(v -> {
                swipeMenuLayout.quickClose();
            });
        }
    }
}
