package jb.listener;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jb.absx.F;
import jb.pageModel.BaseData;
import jb.service.BasedataServiceI;

import jb.util.easemob.HxAccessTokenInstance;
import jb.util.wx.AccessTokenInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 系统全局容器
 * @author John
 *
 */
public class Application implements ServletContextListener {

	public final static ExecutorService executors = Executors.newFixedThreadPool(3);
	private static ServletContext context;
	private static String PREFIX = "SV.";
	private static String sWordReg;
	public static String version = "0";
	@Override
	public void contextInitialized(ServletContextEvent event) {	
	 	context = event.getServletContext();
	 	initAppVariable();
		version = new Date().getTime() + "";

		// 启动刷新微信access_token
		AccessTokenInstance.getInstance();

		// 启动刷新环信access_token
		HxAccessTokenInstance.getInstance();
	}

	private static void initAppVariable(){
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context); 
		BasedataServiceI service = app.getBean(BasedataServiceI.class);
		Map<String,BaseData> map = service.getAppVariable();
		for(String key : map.keySet()){
			context.setAttribute(PREFIX+key, map.get(key));
		}
		sWordReg = null;
	}
	
	/**
	 * 刷新全局变量值
	 */
	public static void refresh(){
		initAppVariable();
	}
	
	/**
	 * 获取全局变量值
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
		String val = null;
		if(bd != null){
			val = bd.getName();
		}
		return val;
	}
	
	/**
	 * 获取全局变量值
	 * @param key
	 * @return
	 */
	public static BaseData get(String key){
		BaseData bd = (BaseData)context.getAttribute(PREFIX+key);		
		return bd;
	}
	public static BasedataServiceI getBasedataService(){
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context); 
		BasedataServiceI service = app.getBean(BasedataServiceI.class);
		return service;
	}
	/**
	 * 获取敏感词的正则
	 * @return
	 */
	public static String getSWordReg() {
		if(!F.empty(sWordReg)) return sWordReg;
		BaseData baseData = new BaseData();
		baseData.setBasetypeCode("SW");
		List<BaseData> baseDataList = getBasedataService().getBaseDatas(baseData);
		if(sWordReg == null) sWordReg = "";
		for(BaseData bd : baseDataList) {
			if(!"".equals(sWordReg)) sWordReg += "|";
			sWordReg += bd.getName();
		}
		return sWordReg;
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	public static <T> T getBean(Class<T> requiredType){
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context);
		return app.getBean(requiredType);
	}

}
