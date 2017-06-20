package jb.interceptors;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jb.absx.F;
import jb.absx.UUID;
import jb.pageModel.SessionInfo;
import jb.pageModel.User;
import jb.service.UserServiceI;
import jb.service.impl.RedisUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class TokenManage {
	
	public static final String TOKEN_FIELD = "tokenId";

	public static final String DEFAULT_TOKEN = "1D96DACB84F21890ED9F4928FA8B352B";

	@Autowired
	private UserServiceI userService;
	
	private ConcurrentHashMap<String, TokenWrap> tokenMap = new ConcurrentHashMap<String, TokenWrap>();
	
	/**
	 * 数据源回收，空闲期2小时
	 */
	private long freeTime = 1000*60*60*2;

	/**
	 * 开启redis token管理
	 */
	protected boolean enableRedis = true;

	@Resource
	private RedisUserServiceImpl redisUserService;
	
	private TokenWrap getDefaultToken(){
		SessionInfo sessionInfo = userService.login("oYKBMxBYnY7B6kYSK0mIFfsQhEO8");
		return new TokenWrap(DEFAULT_TOKEN, sessionInfo, this);
	}	
	
	public void init(){
		
		new Thread("token 回收"){
			public void run(){
				while(true){
					try {
						sleep(10*1000);
						try {
							collection();
						} catch (Exception e) {
							e.printStackTrace();
						}						
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}.start();
	}
	
	public boolean validToken(String tid){
		if(TokenManage.DEFAULT_TOKEN.equals(tid)) return true;

		boolean flag = redisUserService.getToken(tid)==null?false:true;
		if(flag) redisUserService.refresh(tid);
		return flag;
	}

	public SessionInfo getSessionInfo(String tokenId) {
		if(F.empty(tokenId)) return null;
		TokenWrap token;
		if(enableRedis){
			if(TokenManage.DEFAULT_TOKEN.equals(tokenId)){
				token = getDefaultToken();
			} else {
				token = redisUserService.getToken(tokenId);
			}
		}else{
			token = getTokenWrap(tokenId);
		}
		if(token == null) return null;

		return token.getSessionInfo();
	}
	
	public String getName(String tokenId){
		SessionInfo sessionInfo = getSessionInfo(tokenId);
		return sessionInfo == null ? null : sessionInfo.getName();
	}
	
	public String getUid(String tokenId){
		SessionInfo sessionInfo = getSessionInfo(tokenId);
		return sessionInfo == null ? null : sessionInfo.getId();
	}
	private TokenWrap getTokenWrap(String tid){
		TokenWrap token = tokenMap.get(tid);
		if(token != null){
			token.retime();
		}else{
			if(DEFAULT_TOKEN.equals(tid)){
				token = getDefaultToken();
			}			
		}
		return token;
		
	}
	public SessionInfo getSessionInfo(HttpServletRequest request){
		String tokenId = request.getParameter(TokenManage.TOKEN_FIELD);
		if(F.empty(tokenId)) tokenId = (String)request.getAttribute(TokenManage.TOKEN_FIELD);
		return getSessionInfo(tokenId);
	}
	
	
	public String buildToken(SessionInfo sessionInfo){
		String tokenId = UUID.uuid();
		buildToken(tokenId,sessionInfo);
		return tokenId;
	}
	
	public String buildToken(String tokenId, SessionInfo sessionInfo){
		TokenWrap wrap = new TokenWrap(tokenId,sessionInfo,this);
		if(enableRedis){
			redisUserService.setToken(wrap);
		}else {
			wrap.retime();
			tokenMap.putIfAbsent(tokenId, wrap);
		}
		return tokenId;
	}

	public void refreshRedisToken(String token){
		redisUserService.refresh(token);
	}

	public static TokenManage getTokenManage(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(TokenManage.class);
	}
	
	/**
	 * 回收空闲数据源
	 */
	private void collection(){
		synchronized (TokenManage.class) {
			long ntime = System.currentTimeMillis();
			Iterator<String> iter = tokenMap.keySet().iterator();
			String key = null;
			TokenWrap ds = null;
			while (iter.hasNext()) {
				key = iter.next();
				ds = tokenMap.get(key);
				if (ds != null) {
					if (ntime - ds.getCtime() > freeTime) {
						tokenMap.remove(key);
						iter.remove();
					}
				}
			}
		}
	}
}
