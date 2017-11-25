package itbour.onetouchshow.fragment.me;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.adapter.ABaseRefreshAdapter;
import com.andview.adapter.BaseRecyclerHolder;
import com.andview.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.myworks.MyworksActivity;
import itbour.onetouchshow.rvmanager.FullyLinearLayoutManager;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View {

    @BindView(R.id.id_rv_me)
    RecyclerView idRvMe;

    private String[] titles = {"我的订单", "我的秒杀", "我的优惠券", "我的收藏", "我的资料"};

    private List<String> titleList=new ArrayList<>();

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        super.initView();
        idRvMe.setHasFixedSize(true);
        idRvMe.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        idRvMe.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int mSpace=UIUtils.dip2px(10);
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childCount = parent.getChildCount();
                int pos = parent.getChildAdapterPosition(view);
                outRect.left = 0;
                outRect.top = 0;
                outRect.bottom = 0;
                if (pos==2||pos==4){
                    outRect.top = mSpace;
                }else {
                    if (pos != (childCount -1)) {
                        outRect.right = mSpace;
                    } else {
                        outRect.right = 0;
                    }
                }

            }

        });
        titleList.addAll(Arrays.asList(titles));
        PcenterAdapter adapter = new PcenterAdapter(getContext(), titleList,R.layout.item_me_frag);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecyclerHolder baseRecyclerHolder, int position, Object item) {
                openActivity(MyworksActivity.class);
            }
        });
        idRvMe.setAdapter(adapter);
    }

    @OnClick(R.id.id_rv_me)
    public void onViewClicked() {
    }

    public class PcenterAdapter extends ABaseRefreshAdapter<String> {

        private int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        public PcenterAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        @Override
        protected void convert(BaseRecyclerHolder baseRecyclerHolder, String s, int i) {
            TextView tv_name = baseRecyclerHolder.getView(R.id.tv_name);
            ImageView iv_photo = baseRecyclerHolder.getView(R.id.iv_photo);
            iv_photo.setImageResource(images[i]);
            tv_name.setText(s);
        }
    }

}
