package itbour.onetouchshow.evenbus;

import java.util.List;

import itbour.onetouchshow.bean.DesignListBean;

/**
 * Created by onetouch on 2017/11/23.
 */

public class DetailsAction {

    public DetailsAction() {
    }

    public DetailsAction(String subType, List<DesignListBean.ListBean> data) {
        this.subType = subType;
        this.data = data;
    }

    private String subType;
    private List<DesignListBean.ListBean> data;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public List<DesignListBean.ListBean> getData() {
        return data;
    }

    public void setData(List<DesignListBean.ListBean> data) {
        this.data = data;
    }
}
