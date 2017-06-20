package jb.util.redis;

import jb.listener.Application;
import jb.util.easemob.HuanxinUtil;
import jb.util.wx.WeixinUtil;

/**
 * Created by john on 15/12/30.
 */
public interface Namespace {
    String USER_LOGIN_SERVER_HOST = "user_login_server_host";
    String USER_LOGIN_TOKEN = "user_login_token";
    String USER_LOGIN_VALIDATE_CODE = "user_login_validate_code";
    String USER_APPLE_TOKEN = "user_apple_token"; //apns  token;
    String WX_CONFIG = Application.getString(WeixinUtil.APPID) + ":wx_config";
    String HX_CONFIG = Application.getString(HuanxinUtil.APPKEY) + ":hx_config";
    String USER_RSA_PRIVATE_KEY = "user_rsa_private_key";

}
