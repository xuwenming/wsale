package jb.util.easemob;

import jb.listener.Application;
import jb.util.redis.Key;
import jb.util.redis.Namespace;
import jb.util.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by wenming on 2016/7/9.
 */
public class HxAccessTokenInstance {

    private static Logger log = LoggerFactory.getLogger(HxAccessTokenInstance.class);

    public static AccessToken accessToken = null;

    private static HxAccessTokenInstance instance;

    public static HxAccessTokenInstance getInstance() {
        // return instance;
        if (instance == null) {
            synchronized (HxAccessTokenInstance.class) {
                instance = new HxAccessTokenInstance();
            }
        }
        return instance;
    }

    private HxAccessTokenInstance() {
        if (instance != null) {
            throw new IllegalStateException("A server is already running");
        }
        instance = this;
        start();
    }

    private void start() {
        Application.executors.execute(new Runnable(){
            public void run() {
                while (true) {
                    try {
                        AccessToken accessToken = HuanxinUtil.getAccessToken();
                        if (null != accessToken) {
                            log.info("获取环信access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
                            RedisUtil redisUtil = Application.getBean(RedisUtil.class);
                            redisUtil.set(Key.build(Namespace.HX_CONFIG, "hx_access_token"), accessToken.getToken(), accessToken.getExpiresIn(), TimeUnit.SECONDS);
                            // 休眠5180000秒
                            Thread.sleep(((long)accessToken.getExpiresIn() - 4000) * 1000);
                        } else {
                            // 如果access_token为null，60秒后再获取
                            Thread.sleep(60 * 1000);
                        }

                    } catch (InterruptedException e) {
                        log.error("刷环信token线程中断了", e);
                        break;
                    }
                }
            }
        });
    }
}
