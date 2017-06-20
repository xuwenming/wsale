package jb.service.impl;

import com.alibaba.fastjson.JSONObject;
import jb.absx.F;
import jb.interceptors.TokenWrap;
import jb.util.redis.Key;
import jb.util.redis.Namespace;
import jb.util.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by john on 16/1/10.
 */
@Service(value = "redisUserService")
public class RedisUserServiceImpl {
    private long timeout = 60 * 30;

    @Resource
    private RedisUtil redisUtil;

    public boolean setToken(TokenWrap tokenWrap) {
        redisUtil.set(buildKey(tokenWrap.getTokenId()), JSONObject.toJSONString(tokenWrap), timeout, TimeUnit.SECONDS);
        return true;
    }

    public TokenWrap getToken(String token){
        String json = (String)redisUtil.get(buildKey(token));
        if(F.empty(json))return null;
        return JSONObject.parseObject(json, TokenWrap.class);
    }

    /**
     * 刷新token
     * @param token
     */
    public void refresh(String token){
        redisUtil.expire(buildKey(token), timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置用户登录所在的服务器
     * @param userId
     * @param serverHost
     * @return
     */
    public boolean setUserConnect(String userId,String serverHost){
        redisUtil.set(Key.build(Namespace.USER_LOGIN_SERVER_HOST, userId),serverHost);
        return true;
    }

    /**
     * 获取用户所在服务器
     * @param userId
     * @return
     */
    public String getUserConnect(String userId){
        return (String)redisUtil.getString(Key.build(Namespace.USER_LOGIN_SERVER_HOST,userId));
    }

    /**
     * 设置手机验证码
     * @param phone
     * @param code
     */
    public void setValidateCode(String phone,String code){
        redisUtil.set(Key.build(Namespace.USER_LOGIN_VALIDATE_CODE,phone),code, 60*10, TimeUnit.SECONDS);
    }

    /**
     * 获取手机验证码
     * @param phone
     */
    public String getValidateCode(String phone){
        return (String)redisUtil.getString(Key.build(Namespace.USER_LOGIN_VALIDATE_CODE,phone));
    }

    /**
     * 手动删除手机验证码
     * @param phone
     */
    public void deleteValidateCode(String phone){
        redisUtil.delete(Key.build(Namespace.USER_LOGIN_VALIDATE_CODE,phone));
    }

    /**
     * 设置RSA私钥
     */
    public void setRSAPrivateKey(String userId, String key){
        redisUtil.set(Key.build(Namespace.USER_RSA_PRIVATE_KEY, userId), key);
    }

    /**
     * 获取RSA私钥
     */
    public String getRSAPrivateKey(String userId){
        String key = Key.build(Namespace.USER_RSA_PRIVATE_KEY, userId);
        String result = (String)redisUtil.getString(key);
        redisUtil.delete(key);
        return result;
    }

    private String buildKey(String token) {
        return Key.build(Namespace.USER_LOGIN_TOKEN, token);
    }

}
