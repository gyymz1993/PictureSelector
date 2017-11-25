package itbour.onetouchshow.bean;

/**
 * Created by onetouch on 2017/11/23.
 */

public class PayInfoBean {


    /**
     * orderFee : 1
     * paymentPlatform : 10
     * wechat : {"sign":"782ED9B7CDA551EAA1572062D9B4867B","nonceStr":"S7lhkxdyXTR5tU9wWSr1gwMnlBIhucIm","timeStamp":"1511427196","prepayId":"wx2017112316571073a0e07a8a0136840720"}
     * orderId : 24
     * orderNo : 6RL1501XQ3517
     */

    private int orderFee;
    private int paymentPlatform;
    private WechatBean wechat;
    private int orderId;
    private String orderNo;

    public int getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(int orderFee) {
        this.orderFee = orderFee;
    }

    public int getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(int paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public WechatBean getWechat() {
        return wechat;
    }

    public void setWechat(WechatBean wechat) {
        this.wechat = wechat;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public static class WechatBean {
        /**
         * sign : 782ED9B7CDA551EAA1572062D9B4867B
         * nonceStr : S7lhkxdyXTR5tU9wWSr1gwMnlBIhucIm
         * timeStamp : 1511427196
         * prepayId : wx2017112316571073a0e07a8a0136840720
         */

        private String sign;
        private String nonceStr;
        private String timeStamp;
        private String prepayId;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }
    }
}
