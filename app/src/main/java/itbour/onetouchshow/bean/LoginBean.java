package itbour.onetouchshow.bean;

/**
 * Created by onetouch on 2017/11/22.
 */

public class LoginBean {

    /**
     * mobile : {"zone":"+86","mobile":"17607168851"}
     * wechat : {}
     * QQ : {}
     * sinaWeibo : {}
     * userInfo : {"sign":"","userId":299211,"nickName":"yjx_38065","avatarUrl":"http://itbour-back.oss-cn-hangzhou.aliyuncs.com/image/U13396/2017/11/18/162054187_EuiaH41t72UKVDI2ojKE","evaluation":"0"}
     */

    private MobileBean mobile;
    private WechatBean wechat;
    private QQBean QQ;
    private SinaWeiboBean sinaWeibo;
    private UserInfoBean userInfo;

    public MobileBean getMobile() {
        return mobile;
    }

    public void setMobile(MobileBean mobile) {
        this.mobile = mobile;
    }

    public WechatBean getWechat() {
        return wechat;
    }

    public void setWechat(WechatBean wechat) {
        this.wechat = wechat;
    }

    public QQBean getQQ() {
        return QQ;
    }

    public void setQQ(QQBean QQ) {
        this.QQ = QQ;
    }

    public SinaWeiboBean getSinaWeibo() {
        return sinaWeibo;
    }

    public void setSinaWeibo(SinaWeiboBean sinaWeibo) {
        this.sinaWeibo = sinaWeibo;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class MobileBean {
        /**
         * zone : +86
         * mobile : 17607168851
         */

        private String zone;
        private String mobile;

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class WechatBean {
    }

    public static class QQBean {
    }

    public static class SinaWeiboBean {
    }

    public static class UserInfoBean {
        /**
         * sign :
         * userId : 299211
         * nickName : yjx_38065
         * avatarUrl : http://itbour-back.oss-cn-hangzhou.aliyuncs.com/image/U13396/2017/11/18/162054187_EuiaH41t72UKVDI2ojKE
         * evaluation : 0
         */

        private String sign;
        private int userId;
        private String nickName;
        private String avatarUrl;
        private String evaluation;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }
    }
}
