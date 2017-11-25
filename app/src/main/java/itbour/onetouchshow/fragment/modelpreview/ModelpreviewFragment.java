package itbour.onetouchshow.fragment.modelpreview;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel.VerticalmodelActivity;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.bean.ModelPreviewInfoBean;
import itbour.onetouchshow.mvp.MVPBaseFragment;
import itbour.onetouchshow.utils.ImageLoader;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 * <p>
 * 本类用于设计/印品 模板预览的切换页面
 */

@SuppressLint("ValidFragment")
public class ModelpreviewFragment extends MVPBaseFragment<ModelpreviewContract.View, ModelpreviewPresenter> implements ModelpreviewContract.View {

    public Boolean isCollect;
    @BindView(R.id.rv_fragment_vertical_model_preview)
    RecyclerView recyclerView;
    Unbinder unbinder;
    public DesignListBean.ListBean listBean;
    private VerticalmodelActivity context;

    @SuppressLint("ValidFragment")
    public ModelpreviewFragment(VerticalmodelActivity verticalmodelActivity, DesignListBean.ListBean listBean) {
        this.context = verticalmodelActivity;
        this.listBean = listBean;

    }

    @Override
    public void loadSucceed(String result) {
        L_.i("result===" + result);

        ModelPreviewInfoBean modelPreviewInfoBean = new Gson().fromJson(result, ModelPreviewInfoBean.class);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        Adapter adapter = new Adapter(modelPreviewInfoBean);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vertical_model_preview;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.loadThisPageInfo(listBean);

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        if (listBean != null) {
            context.nowFragmentVisable(listBean);

        }
    }


    /**
     * recyclerView的adapter
     */
    public class Adapter extends RecyclerView.Adapter {

        ModelPreviewInfoBean data;

        public Adapter(ModelPreviewInfoBean data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_model_preview, parent, false);
            ViewHolder itemViewHolder = new ViewHolder(view);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            ImageView imageView = holder.imageView;
            ModelPreviewInfoBean.ImageBean image = data.getImage();


//            int placeHolder = CommonUtils.jugleStubImageType(scale);
//            ViewGroup.LayoutParams layoutParams = ((ItemViewHolder) holder).rlVerticalItem.getLayoutParams();
//            layoutParams.height = (int) (MyApplication.screenWith *1.0f/ w * h);
//            layoutParams.width = (int) MyApplication.screenWith;
//            ((ItemViewHolder) holder).rlVerticalItem.setLayoutParams(layoutParams);


            int screenWith = UIUtils.WHD()[0] - UIUtils.dp2px(12);
            int screenHeight = UIUtils.WHD()[1]
                    - UIUtils.dp2px(80 + 55) - UIUtils.getStatusBarHeight(getActivity());
            L_.i("screenHeight===" + screenHeight);
            //底部加状态栏
            int imageHeight = image.getH();
            int imageWidth = image.getW();

            float scale = imageHeight * 1.0f / imageWidth;


            imageWidth = screenWith;
            imageHeight = (int) (screenWith * scale);
            if (imageHeight > screenHeight) {
                imageHeight = screenHeight;
                imageWidth = (int) (imageHeight / scale);
            }

            List<String> thumbs = image.getThumbs();
            String thumb = thumbs.get(position);
            String shrinkImageUrl = ImageLoader.getShrinkImageUrl(thumb, imageWidth,imageHeight);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            lp.width = imageWidth;
            lp.height = imageHeight;
            imageView.setLayoutParams(lp);

            //如果只有一个  居中显示
            if (getItemCount() == 1) {
                RelativeLayout.LayoutParams lpRl = (RelativeLayout.LayoutParams) holder.rl.getLayoutParams();
                lpRl.height = screenHeight;
                holder.rl.setLayoutParams(lpRl);
            }


            Glide.with(context).load(shrinkImageUrl).into(imageView);
        }

        @Override
        public int getItemCount() {
            return data.getImage().getThumbs().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_adapter_model_preview)
            ImageView imageView;
            @BindView(R.id.rl_adapter_model_preview)
            RelativeLayout rl;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}
