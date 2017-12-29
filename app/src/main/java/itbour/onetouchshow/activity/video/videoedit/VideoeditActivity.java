package itbour.onetouchshow.activity.video.videoedit;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.adapter.ABaseRefreshAdapter;
import com.andview.adapter.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itbour.onetouchshow.R;
import itbour.onetouchshow.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VideoeditActivity extends MVPBaseActivity<VideoeditContract.View, VideoeditPresenter> implements VideoeditContract.View {
    @BindView(R.id.id_rv_vb)
    RecyclerView idRvVb;

    VideoEditAdapter videoEditAdapter;

    @Override
    protected void initTitle() {
        super.initTitle();
        setTitleText("视频预览");

    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_edit;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        List<String> list = new ArrayList<>();
        videoEditAdapter = new VideoEditAdapter(getApplicationContext(), list, R.layout.item_video_edit);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class VideoEditAdapter extends ABaseRefreshAdapter<String> {

        public VideoEditAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder var1, String var2, int var3) {

        }
    }
}
