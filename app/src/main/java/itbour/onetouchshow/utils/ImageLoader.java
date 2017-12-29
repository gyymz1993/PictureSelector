package itbour.onetouchshow.utils;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import itbour.onetouchshow.R;
import itbour.onetouchshow.utils.transform.GlideCircleTransform;

/**
 * @author ymz
 */
public class ImageLoader {


    private static final ImageLoader ourInstance = new ImageLoader();
    //private static final String SHRINKURL = "?x-oss-process=image/resize,h_";
    private static final String SHRINKURL = "?x-oss-process=image/resize,h_";
    //http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/resize,m_lfit,h_100,w_100

    public static ImageLoader getInstance() {
        return ourInstance;
    }


    public static String getShrinkImageUrl(String orangleUrl, int w, int h) {
//        return orangleUrl+SHRINKURL+(int)(h/2);
        // L_.e("显示图片" + orangleUrl + "?x-oss-process=image/resize,m_lfit,h_" + h + ",w_" + w);
        return orangleUrl + "?x-oss-process=image/resize,m_lfit,h_" + h + ",w_" + w;
    }


    public static String getShrinkImageUrl(String orangleUrl, int h) {
        return orangleUrl + SHRINKURL + (int) (h);

    }

    public void showRealSizeImage(String url, ImageView imageView) {
        Glide.with(UIUtils.getContext()).setDefaultRequestOptions(new RequestOptions()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).skipMemoryCache(false).
                        diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(new ColorDrawable(UIUtils.getColor(R.color.apptheme))))
                .load(url).into(imageView);
    }

    public void showImage(String url, ImageView imageView) {
//        Glide.with(UIUtils.getContext()).setDefaultRequestOptions(new RequestOptions()
//        .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
//        .centerCrop()).load(url).into(imageView);
        Glide.with(UIUtils.getContext()).applyDefaultRequestOptions(new RequestOptions()
                .placeholder(R.drawable.place_icon).error(R.drawable.place_icon)).load(url).into(imageView);
//        Glide.with(UIUtils.getContext())
//                .load(url)
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.empty_drawable))
//                .into(imageView);
    }


    public void showImageNoplace(String url, ImageView imageView) {
//        Glide.with(UIUtils.getContext()).setDefaultRequestOptions(new RequestOptions()
//        .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
//        .centerCrop()).load(url).into(imageView);
        Glide.with(UIUtils.getContext()).applyDefaultRequestOptions(new RequestOptions()).load(url).into(imageView);
//        Glide.with(UIUtils.getContext())
//                .load(url)
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.empty_drawable))
//                .into(imageView);
    }


    public void load(int resId, ImageView iv) {
        Glide.with(UIUtils.getContext()).setDefaultRequestOptions(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)).load(resId).into(iv);
    }


    /**
     * 加载第三秒的帧数作为封面
     */
    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
        Glide.with(UIUtils.getContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.ic_launcher)
                                .placeholder(R.mipmap.ic_launcher))
                .load(url)
                .into(imageView);
    }


    /**
     * 加载第三秒的帧数作为封面
     */
    public void showRundImage(String url, ImageView imageView) {
        Glide.with(UIUtils.getContext()).applyDefaultRequestOptions(new RequestOptions()
                .transform(new GlideCircleTransform())).load(url).into(imageView);
    }


}
