package itbour.onetouchshow.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.activity.video.clip.ClipActivity;
import itbour.onetouchshow.bean.canvas.CompoundPathsBean;
import itbour.onetouchshow.bean.canvas.PathsBean;
import itbour.onetouchshow.bean.inching.noppt.InchingDocStringNoPPTV10Bean;

/**
 * Created by zjl_d on 2017/11/20.
 */

public class CommonUtils {
    /**
     * Gson解析Json数组
     *
     */
    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        if (jsonObjects != null && jsonObjects.size() > 0) {
            ArrayList<T> arrayList = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
            return arrayList;
        }
        return null;

    }


    public static boolean jugleIsSupportAddTextAndImg(InchingDocStringNoPPTV10Bean data) {
        if (data.getCoreStructure().getVersion() < 3) {
            return false;
        }
        return true;
    }

    /**
     * 用于文字轮廓路径 传入的是微调库经过缩放后的文字轮廓路劲
     * 传入一个复合路劲 返回一个paths
     *
     * @return
     */
    public static Path getTextOrShapeBesselPaths(List<PathsBean> paths) {
        Path path = new Path();

        int minScale = 1;

        int offsetX = 0;
        int offsetY = 0;
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                List<List<Float>> points = paths.get(i).getPoints();
                List<List<Float>> bezier3 = new ArrayList<>();
                List<List<Float>> bezier2 = new ArrayList<>();
                for (int j = 0; j < points.size(); j++) {
                    List<Float> doubles = points.get(j);
                    float type = doubles.get(2);
                    if (type == 0) {

                    } else if (type == 1) {
                        float x = (float) ((float) (doubles.get(0) * minScale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * minScale) + offsetY);

                        path.lineTo(x, y);  //画线
                    } else if (type == 2) {
                        bezier2.add(points.get(j));
                        bezier2.add(points.get(j + 1)); //如果是二阶贝塞尔曲线，一次取两个点
                        float x1 = (float) ((float) (bezier2.get(0).get(0) * minScale) + offsetX);
                        float y1 = (float) ((float) (bezier2.get(0).get(1) * minScale) + offsetY);
                        float x2 = (float) ((float) (bezier2.get(1).get(0) * minScale) + offsetX);
                        float y2 = (float) ((float) (bezier2.get(1).get(1) * minScale) + offsetY);

                        path.quadTo(x1, y1, x2, y2);
                        bezier2.clear(); //画完清空数据
                        j++;
                    } else if (type == 3) {
                        bezier3.add(points.get(j));
                        bezier3.add(points.get(j + 1));
                        bezier3.add(points.get(j + 2));//如果是三阶贝塞尔曲线，一次取三个点
                        float x1 = (float) ((float) (bezier3.get(0).get(0) * minScale) + offsetX);
                        float y1 = (float) ((float) (bezier3.get(0).get(1) * minScale) + offsetY);
                        float x2 = (float) ((float) (bezier3.get(1).get(0) * minScale) + offsetX);
                        float y2 = (float) ((float) (bezier3.get(1).get(1) * minScale) + offsetY);
                        float x3 = (float) ((float) (bezier3.get(2).get(0) * minScale) + offsetX);
                        float y3 = (float) ((float) (bezier3.get(2).get(1) * minScale) + offsetY);

                        path.cubicTo(x1, y1, x2, y2, x3, y3);
                        bezier3.clear();
                        j++;
                        j++;
                    } else if (type == 8) {
                        float x = (float) ((float) (doubles.get(0) * minScale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * minScale) + offsetY);
                        path.moveTo(x, y);
                    } else if (type == 9) {
                        path.close();

                    }
                    if (j == points.size() - 1) {
                        path.close();

                    }

                }
            }
        }
        return path;

    }

    //给edittext设置最大字数且过滤emjio
    public static void setEditTextFilters(EditText editText, int max) {
        editText.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(max), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher matcher = ShieldEmoji.isEmoji().matcher(source);
                if (matcher.find()) {
                    return "";
                }
                return null;
            }
        }});
    }

    public static Intent createToPhotoIntent(Intent data, Activity activity) {

        Uri uri = data.getData();
        //这里拿到的是从相册取到的图片路径
        String path = GetPath.getPath(uri, activity);
        String end = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase();
               /* 依扩展名的类型决定MimeType 这里只用了pdf word 图片更类型可查http://www.cnblogs.com/hibr		aincol/archive/2010/09/16/1828502.html*/
        if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            Intent intent = new Intent(activity, ClipActivity.class);
            //图片本地路径
            intent.putExtra(AppConst.LOCAL_PHOTO_PATH, path);
            return intent;
        }

        return null;

    }


    /**
     * 判断两个浮点数是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean floatEqual(float a, float b) {
        if ((a - b > -0.000001f) && (a - b) < 0.000001f) {

            return true;
        } else {

            return false;
        }
    }

    /**
     * 传入符合路径
     * 用于裁剪界面绘制蒙板控件
     *
     * @param paths
     * @param vW    view的宽高
     * @param vH
     * @param mW    mask的宽高
     * @param mH
     * @return
     */
    public static Path getTextOrShapeBesselPaths(List<PathsBean> paths, int vW, int vH, float mW, float mH) {
        Path path = new Path();


        double scaleHeight = vH * 1.0 / (int) mH;
        double scaleWidth = vW * 1.0 / (int) mW;
        double scale = (scaleHeight > scaleWidth) ? scaleWidth : scaleHeight;//缩放比例
        //偏移量 *0.5是为了让蒙板居中  这里的x 会有非常小的偏移量
        double offsetX = (vW - mW * scale) * 0.5;
        double offsetY = (vH - mH * scale) * 0.5;

        L_.i("PathImgbaseMatrix===offsetX===" + offsetX + "offsetY===" + offsetY + "vW===" + vW + "vH===" + vH + "mW===" + mW + "mH===" + mH + "scale===" + scale);

        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                List<List<Float>> points = paths.get(i).getPoints();
                List<List<Float>> bezier3 = new ArrayList<>();
                List<List<Float>> bezier2 = new ArrayList<>();
                for (int j = 0; j < points.size(); j++) {
                    List<Float> doubles = points.get(j);
                    float type = doubles.get(2);
                    if (type == 0) {

                    } else if (type == 1) {
                        float x = (float) ((float) (doubles.get(0) * scale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * scale) + offsetY);


                        path.lineTo(x, y);  //画线
                    } else if (type == 2) {
                        bezier2.add(points.get(j));
                        bezier2.add(points.get(j + 1)); //如果是二阶贝塞尔曲线，一次取两个点
                        float x1 = (float) ((float) (bezier2.get(0).get(0) * scale) + offsetX);
                        float y1 = (float) ((float) (bezier2.get(0).get(1) * scale) + offsetY);
                        float x2 = (float) ((float) (bezier2.get(1).get(0) * scale) + offsetX);
                        float y2 = (float) ((float) (bezier2.get(1).get(1) * scale) + offsetY);

                        path.quadTo(x1, y1, x2, y2);
                        bezier2.clear(); //画完清空数据
                        j++;
                    } else if (type == 3) {
                        bezier3.add(points.get(j));
                        bezier3.add(points.get(j + 1));
                        bezier3.add(points.get(j + 2));//如果是三阶贝塞尔曲线，一次取三个点
                        float x1 = (float) ((float) (bezier3.get(0).get(0) * scale) + offsetX);
                        float y1 = (float) ((float) (bezier3.get(0).get(1) * scale) + offsetY);
                        float x2 = (float) ((float) (bezier3.get(1).get(0) * scale) + offsetX);
                        float y2 = (float) ((float) (bezier3.get(1).get(1) * scale) + offsetY);
                        float x3 = (float) ((float) (bezier3.get(2).get(0) * scale) + offsetX);
                        float y3 = (float) ((float) (bezier3.get(2).get(1) * scale) + offsetY);

                        path.cubicTo(x1, y1, x2, y2, x3, y3);
                        bezier3.clear();
                        j++;
                        j++;
                    } else if (type == 8) {
                        float x = (float) ((float) (doubles.get(0) * scale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * scale) + offsetY);
                        ;
                        path.moveTo(x, y);
                    } else if (type == 9) {
                        path.close();
                    }
                    if (j == points.size() - 1) {
                        path.close();
                    }

                }
            }
        }
        return path;

    }




    public static Path getOnControlMoveScalePath(CompoundPathsBean maskBean,float wScale, float hScale, float offsetX, float offsetY) {
        Path path = new Path();
        List<PathsBean> paths = maskBean.getPaths();

        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                List<List<Float>> points = paths.get(i).getPoints();
                List<List<Float>> bezier3 = new ArrayList<>();
                List<List<Float>> bezier2 = new ArrayList<>();
                for (int j = 0; j < points.size(); j++) {
                    List<Float> doubles = points.get(j);
                    float type = doubles.get(2);
                    if (type == 0) {

                    } else if (type == 1) {
                        float x = (float) ((float) (doubles.get(0) * wScale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * hScale) + offsetY);

                        path.lineTo(x, y);  //画线
                    } else if (type == 2) {
                        bezier2.add(points.get(j));
                        bezier2.add(points.get(j + 1)); //如果是二阶贝塞尔曲线，一次取两个点
                        float x1 = (float) ((float) (bezier2.get(0).get(0) * wScale) + offsetX);
                        float y1 = (float) ((float) (bezier2.get(0).get(1) * hScale) + offsetY);
                        float x2 = (float) ((float) (bezier2.get(1).get(0) * wScale) + offsetX);
                        float y2 = (float) ((float) (bezier2.get(1).get(1) * hScale) + offsetY);
                        path.quadTo(x1, y1, x2, y2);
                        bezier2.clear(); //画完清空数据
                        j++;
                    } else if (type == 3) {
                        bezier3.add(points.get(j));
                        bezier3.add(points.get(j + 1));
                        bezier3.add(points.get(j + 2));//如果是三阶贝塞尔曲线，一次取三个点
                        float x1 = (float) ((float) (bezier3.get(0).get(0) * wScale) + offsetX);
                        float y1 = (float) ((float) (bezier3.get(0).get(1) * hScale) + offsetY);
                        float x2 = (float) ((float) (bezier3.get(1).get(0) * wScale) + offsetX);
                        float y2 = (float) ((float) (bezier3.get(1).get(1) * hScale) + offsetY);
                        float x3 = (float) ((float) (bezier3.get(2).get(0) * wScale) + offsetX);
                        float y3 = (float) ((float) (bezier3.get(2).get(1) * hScale) + offsetY);
                        path.cubicTo(x1, y1, x2, y2, x3, y3);
                        bezier3.clear();
                        j++;
                        j++;
                    } else if (type == 8) {
                        float x = (float) ((float) (doubles.get(0) * wScale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * hScale) + offsetY);
                        path.moveTo(x, y);
                    } else if (type == 9) {
                        path.close();

                    }
                    if (j == points.size() - 1) {
                        path.close();

                    }

                }
            }
        }

        return path;


    }




    public static Path getBoundViewPaths(List<PathsBean> paths, int vW, int vH, float mW, float mH) {
        Path path = new Path();


        double scaleHeight = vH * 1.0 / (int) mH;
        double scaleWidth = vW * 1.0 / (int) mW;
        double scale = (scaleHeight > scaleWidth) ? scaleWidth : scaleHeight;//缩放比例
        //偏移量 *0.5是为了让蒙板居中  这里的x 会有非常小的偏移量
        double offsetX = (vW - mW * scale) * 0.5 * 0;
        double offsetY = (vH - mH * scale) * 0.5 * 0;

        L_.i("PathImgbaseMatrix===offsetX===" + offsetX + "offsetY===" + offsetY + "vW===" + vW + "vH===" + vH + "mW===" + mW + "mH===" + mH + "scale===" + scale);

        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                List<List<Float>> points = paths.get(i).getPoints();
                List<List<Float>> bezier3 = new ArrayList<>();
                List<List<Float>> bezier2 = new ArrayList<>();
                for (int j = 0; j < points.size(); j++) {
                    List<Float> doubles = points.get(j);
                    float type = doubles.get(2);
                    if (type == 0) {

                    } else if (type == 1) {
                        float x = (float) ((float) (doubles.get(0) * scale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * scale) + offsetY);


                        path.lineTo(x, y);  //画线
                    } else if (type == 2) {
                        bezier2.add(points.get(j));
                        bezier2.add(points.get(j + 1)); //如果是二阶贝塞尔曲线，一次取两个点
                        float x1 = (float) ((float) (bezier2.get(0).get(0) * scale) + offsetX);
                        float y1 = (float) ((float) (bezier2.get(0).get(1) * scale) + offsetY);
                        float x2 = (float) ((float) (bezier2.get(1).get(0) * scale) + offsetX);
                        float y2 = (float) ((float) (bezier2.get(1).get(1) * scale) + offsetY);

                        path.quadTo(x1, y1, x2, y2);
                        bezier2.clear(); //画完清空数据
                        j++;
                    } else if (type == 3) {
                        bezier3.add(points.get(j));
                        bezier3.add(points.get(j + 1));
                        bezier3.add(points.get(j + 2));//如果是三阶贝塞尔曲线，一次取三个点
                        float x1 = (float) ((float) (bezier3.get(0).get(0) * scale) + offsetX);
                        float y1 = (float) ((float) (bezier3.get(0).get(1) * scale) + offsetY);
                        float x2 = (float) ((float) (bezier3.get(1).get(0) * scale) + offsetX);
                        float y2 = (float) ((float) (bezier3.get(1).get(1) * scale) + offsetY);
                        float x3 = (float) ((float) (bezier3.get(2).get(0) * scale) + offsetX);
                        float y3 = (float) ((float) (bezier3.get(2).get(1) * scale) + offsetY);

                        path.cubicTo(x1, y1, x2, y2, x3, y3);
                        bezier3.clear();
                        j++;
                        j++;
                    } else if (type == 8) {
                        float x = (float) ((float) (doubles.get(0) * scale) + offsetX);
                        float y = (float) ((float) (doubles.get(1) * scale) + offsetY);
                        ;
                        path.moveTo(x, y);
                    } else if (type == 9) {
                        path.close();
                    }
                    if (j == points.size() - 1) {
                        path.close();
                    }

                }
            }
        }
        return path;

    }

    /**
     * 平移动画
     * @param view
     * @param startY
     * @param endY
     */
    public static void transAnimation(View view, int startY, int endY) {
        TranslateAnimation scaleAnimationTrans = new TranslateAnimation(0, 0, startY, endY);
        scaleAnimationTrans.setDuration(200);
        scaleAnimationTrans.setFillAfter(true);
        view.startAnimation(scaleAnimationTrans);
    }

    public static void flashAnimontion(View pathsView) {

        Animation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        pathsView.startAnimation(alphaAnimation);
    }
}
