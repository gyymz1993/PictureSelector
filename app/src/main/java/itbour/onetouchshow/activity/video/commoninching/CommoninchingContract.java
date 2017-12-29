package itbour.onetouchshow.activity.video.commoninching;

import android.content.Intent;

import itbour.onetouchshow.bean.canvas.PosBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineAllTypeV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineImgBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineTableBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineTextOrShapeBean;
import itbour.onetouchshow.custom.inching.InchingOperaRelativeLayout;
import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CommoninchingContract {
   public interface View extends BaseView {

        void toProductPreview(int docId, int opType);

        void showToast(String string);


        void setInchingBaseAreaParams(int controller_width, int controller_height, int rootWith, int rootHeight, int bw, int bh);


        void setAddTextImgVisableStates(boolean showAddTextImg);

        void createStubImgView(InchingOutLineImgBean data);

        void addMaskAndGridView();

        void createOperaImage(InchingOutLineImgBean data);

        void createTextOrShape(InchingOutLineTextOrShapeBean data);

        void createTable(InchingOutLineTableBean data);

        void jugleIsNeedShowPageSwitch(int pageCount);

        void clearNowPageChild();

        void setNowPageIndex(int nowPageIndex);

        InchingOperaRelativeLayout getNowSelectView();

        void hideControlView();

        void showPopWindow(InchingOutLineAllTypeV10Bean data, int x, int y);

        void hidePopWindow();

        void upDateTextOrShapeView(InchingOutLineTextOrShapeBean data);

        void deleteView();

        void upDateTableView(InchingOutLineTableBean data);

        void changeImgBack(Intent data);

        void changeImg(Intent data);


        void addInchingTextView();

        void addInchingImgView(Intent data);

        void createEnabledEreaData(PosBean videoFrame);
        //当键盘弹起禁用当前界面中除了文字操作面板中所有控件的事件

        /*修改视频背景成功*/
        void toUpVideoBgSuccess(String result);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
