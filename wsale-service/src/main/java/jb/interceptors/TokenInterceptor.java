package jb.interceptors;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.SessionInfo;
import jb.service.UserServiceI;
import jb.util.wx.SignUtil;
import jb.util.wx.WeixinUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 权限拦截器
 *
 * @author John
 *
 */
public class TokenInterceptor implements HandlerInterceptor {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TokenInterceptor.class);

	private static final String CODE = "code";
	private static final String STATE = "state";

	@Autowired
	private UserServiceI userService;

	private List<String> excludeUrls;// 不需要拦截的资源

	private List<String> tokenUrls;  //tocken认证资源

	private List<String> wxOAuthUrls; // 需要微信网页授权的url
	private List<String> wxJSSDKUrls; // 需要微信JS-SDK权限签名的url

	private TokenManage tokenManage;

	public List<String> getTokenUrls() {
		return tokenUrls;
	}

	public void setTokenUrls(List<String> tokenUrls) {
		this.tokenUrls = tokenUrls;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		request.setAttribute("staticVersion", Application.version);
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
//		String redirect_uri = request.getScheme() + "://" + request.getServerName(); // 服务器地址
//		if(request.getServerPort() != 80)
//			redirect_uri += ":" + request.getServerPort();           // 端口号
		String redirect_uri = Application.getString("SV101");
		redirect_uri += request.getContextPath()      // 项目名称
				+ request.getServletPath();      // 请求页面或其他地址
		String sign_url = redirect_uri;
		if(request.getQueryString() != null)
			sign_url += "?" + (request.getQueryString()); //参数

		Map<String, String[]> paramMap = request.getParameterMap();
		if(!paramMap.isEmpty()) {
			String paramStr = "";
			for(String key : paramMap.keySet()) {
				if(CODE.equals(key) || STATE.equals(key) || TokenManage.TOKEN_FIELD.equals(key)) continue;
				paramStr += "".equals(paramStr) ? "?" : "&";
				paramStr += key + "=" + paramMap.get(key)[0];
			}
			redirect_uri += paramStr;
		}

//		System.out.println("~~~~~~~~~~~~~redirect_uri:" + redirect_uri);

		if (url.indexOf("/baseController/") > -1 || url.indexOf("/imageTransfer/") > -1 || excludeUrls.contains(url)) {// 如果要访问的资源是不需要验证的
			return true;
		}

		String code = request.getParameter(CODE);
		String tokenId = request.getParameter(TokenManage.TOKEN_FIELD);
		String appId = Application.getString(WeixinUtil.APPID);
		if(F.empty(code) || tokenManage.validToken(code)) {
			// TODO 非微信授权或获取code失败
			if(F.empty(tokenId) || !tokenManage.validToken(tokenId)) {
				String userAgent = request.getHeader("user-agent");
				if(!F.empty(userAgent) && userAgent.indexOf("MicroMessenger") == -1)
					request.getRequestDispatcher("/api/apiCommon/error").forward(request, response);
				else
					response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+ URLEncoder.encode(redirect_uri, "UTF-8") +"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
				return false;
			}
		} else {
			if(F.empty(tokenId) || !tokenManage.validToken(tokenId)) {
				String state = request.getParameter("state");
				boolean snsapi_userinfo = false;
				if(!F.empty(state) && "userinfo".equals(state)) {
					snsapi_userinfo = true;
				}
				SessionInfo sessionInfo = userService.loginByWx(code, snsapi_userinfo);
				if(sessionInfo != null) {
					request.setAttribute(TokenManage.TOKEN_FIELD, tokenManage.buildToken(sessionInfo));
					tokenManage.buildToken(code, new SessionInfo()); // 防止重复登录验证
				} else {
					if(!tokenManage.validToken(code)){
						response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+URLEncoder.encode(redirect_uri, "UTF-8")+"&response_type=code&scope=snsapi_userinfo&state=userinfo&connect_redirect=1#wechat_redirect");
						return false;
					}

				}
			}
		}
		try {
			// 微信JS-SDK权限签名
			Map<String, String> signMap = SignUtil.sign(sign_url.split("#")[0]);
			request.setAttribute("timestamp", signMap.get("timestamp"));
			request.setAttribute("nonceStr", signMap.get("nonceStr"));
			request.setAttribute("signature", signMap.get("signature"));
			request.setAttribute("appId", appId);
		} catch (Exception e) {
			logger.error("微信JS-SDK权限签名异常", e);
		}

		return true;
	}

	public void setTokenManage(TokenManage tokenManage) {
		this.tokenManage = tokenManage;
	}

	public List<String> getWxOAuthUrls() {
		return wxOAuthUrls;
	}

	public void setWxOAuthUrls(List<String> wxOAuthUrls) {
		this.wxOAuthUrls = wxOAuthUrls;
	}

	public List<String> getWxJSSDKUrls() {
		return wxJSSDKUrls;
	}

	public void setWxJSSDKUrls(List<String> wxJSSDKUrls) {
		this.wxJSSDKUrls = wxJSSDKUrls;
	}
}
