package jb.util.easemob;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import jb.listener.Application;
import jb.util.redis.Key;
import jb.util.redis.Namespace;
import jb.util.redis.RedisUtil;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HuanxinUtil {
	
	private static final Logger log = Logger.getLogger(HuanxinUtil.class);
	
	public static final String APPKEY = "HX01";
	public static final String CLIENT_ID = "HX02";
	public static final String CLIENT_SECRET = "HX03";

	private static RedisUtil redisUtil = Application.getBean(RedisUtil.class);

//	public static final String APPKEY = "xuwenming-1987#wsale";
//	public static final String CLIENT_ID = "YXA6ZV2AsG-MEea11rNxnRarPQ";
//	public static final String CLIENT_SECRET = "YXA6nCzfxS2QoeA9y2PHYekCyde_3_o";
	
	/**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            System.out.println("WARNING: Hostname is not matched for cert.");
            return true;
        }
    };
    
	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param requestUrl 请求地址
	 * @return
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod(requestMethod.toUpperCase());
			
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
		}
		return buffer.toString();
	}
	
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return 返回环信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr, boolean oauth) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod.toUpperCase());
			if(oauth) {
				String token = (String)redisUtil.get(Key.build(Namespace.HX_CONFIG, "hx_access_token"));
				httpUrlConn.setRequestProperty("Content-Type", "application/json");
				httpUrlConn.setRequestProperty("Authorization", "Bearer " + token);
			}

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return buffer.toString();
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = null;
		String client_id = Application.getString(CLIENT_ID);
		String client_secret = Application.getString(CLIENT_SECRET);
//		String client_id = CLIENT_ID;
//		String client_secret = CLIENT_SECRET;
		String params = "{\"grant_type\": \"client_credentials\",\"client_id\": \""+client_id+"\",\"client_secret\": \""+client_secret+"\"}";
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest(getUrl("token"), "POST", params, false));
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败", e);
			}
		}
		return accessToken;
	}
	
	/**
	 * 环信注册用户
	 * @return
	 */
	public static String createUser(String username, String password) {
		
		String params = "{\"username\": \""+username+"\",\"password\": \""+password+"\"}";
		String response = httpsRequest(getUrl("users"), "POST", params, true);
		log.info(response);
		return response;
	}

	/**
	 * 环信批量注册用户
	 * usesJsonString：格式：[{"username":"u1", "password":"p1"},{"username":"u2", "password":"p2"}]
	 * @return
	 */
	public static String createUsers(String usesJsonString) {
		String response = httpsRequest(getUrl("users"), "POST", usesJsonString, true);
		log.info(response);
		return response;
	}
	
	/**
	 * 环信重置用户密码
	 * @return
	 */
	public static String resetPass(String username, String password) {
		
		String params = "{\"newpassword\": \""+password+"\"}";
		String response = httpsRequest(getUrl("users/"+username+"/password"), "PUT", params, true);
		log.info(response);
		return response;
	}
	
	/**
	 * 环信添加好友
	 * @param owner_username：要添加好友的用户名
	 * @param friend_username：被添加的用户名
	 * @return
	 */
	public static String addFriend(String owner_username, String friend_username) {
		String response = httpsRequest(getUrl("users/" + owner_username + "/contacts/users/" + friend_username), "POST", null, true);
		log.info(response);
		return response;
	}

	/**
	 * 环信删除好友
	 * @param owner_username：要添加好友的用户名
	 * @param friend_username：被添加的用户名
	 * @return
	 */
	public static String delFriend(String owner_username, String friend_username) {
		String response = httpsRequest(getUrl("users/" + owner_username + "/contacts/users/" + friend_username), "DELETE", null, true);
		log.info(response);
		return response;
	}

	/**
	 * 环信获取好友
	 * @param owner_username：要添加好友的用户名
	 * @return
	 */
	public static String getFriends(String owner_username) {
		String response = httpsRequest(getUrl("users/" + owner_username + "/contacts/users"), "GET", null, true);
		log.info(response);
		return response;
	}
	
	private static String getUrl(String action) {
		String[] keys = Application.getString(APPKEY).split("#");
//		String[] keys = APPKEY.split("#");
		return "https://a1.easemob.com/"+keys[0]+"/"+keys[1]+"/"+action;
	}

	public static void main(String[] args) {
		HxAccessTokenInstance.accessToken = getAccessToken();
		getFriends("cs");
		//delFriend("cs", "da810fe5750049698c09dd2761267a39");
	}
}
