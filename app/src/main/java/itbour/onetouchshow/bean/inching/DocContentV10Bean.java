package itbour.onetouchshow.bean.inching;

import itbour.onetouchshow.AppConst;

/**
 * Created by zjl_d on 2017/11/21.
 */

public class DocContentV10Bean {

    /**
     * tmplId : 1
     * docString :
     * docId : 0
     * opType : 1
     */

    private int tmplId;
    private String docString;
    private int docId;
    private int opType;
    private InchingDocStringNoPPTV10Bean noPptCoreData;
    private InchingDoStringPPTV10Bean pptCoreData;

    public InchingDocStringNoPPTV10Bean getNoPptCoreData() {
        return noPptCoreData;
    }

    public void setNoPptCoreData(InchingDocStringNoPPTV10Bean noPptCoreData) {
        this.noPptCoreData = noPptCoreData;
    }

    public InchingDoStringPPTV10Bean getPptCoreData() {
        return pptCoreData;
    }

    public void setPptCoreData(InchingDoStringPPTV10Bean pptCoreData) {
        this.pptCoreData = pptCoreData;
    }

    public int getTmplId() {
        return tmplId;
    }

    public void setTmplId(int tmplId) {
        this.tmplId = tmplId;
    }

    public String getDocString() {
        return docString;
    }

    public void setDocString(String docString) {
        this.docString = docString;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public void  getOpTypeVertical() {
        if (opType == AppConst.VERTICAL_ORTYPE) {

        }
    }

    /**
     *
     */
    public String getOpTypeVerticalJson() {
        if (opType == AppConst.VERTICAL_ORTYPE) {
            return "";
        }
        return "";
    }
}
