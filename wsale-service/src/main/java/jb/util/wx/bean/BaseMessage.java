package jb.util.wx.bean;

/**
 * Created by wenming on 2016/9/3.
 */
public class BaseMessage {
    private String touser;
    private String msgtype;
    private Custom customservice;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public Custom getCustomservice() {
        return customservice;
    }

    public void setCustomservice(Custom customservice) {
        this.customservice = customservice;
    }
}
