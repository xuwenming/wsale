package jb.controller;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.SessionInfo;
import jb.pageModel.ZcBanner;
import jb.service.ZcBannerServiceI;
import jb.util.wx.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 首页
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiHomeController")
public class ApiHomeController extends BaseController {

	@Autowired
	private ZcBannerServiceI zcBannerService;

	/**
	 * 跳转首页
	 * http://localhost:8080/api/apiHomeController/home?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request) {
		//Json j = new Json();
		try{
			ZcBanner banner = new ZcBanner();
			banner.setStatus("ST01");
			List<ZcBanner> banners = zcBannerService.query(banner);
			if(CollectionUtils.isNotEmpty(banners)) {
				for(ZcBanner b : banners) {
					if(F.empty(b.getDetailUrl()) && !F.empty(b.getContent()))
						b.setDetailUrl(Application.getString("SV100") + "api/apiHomeController/html?id=" + b.getId());
				}
			}
			request.setAttribute("banners", banners);
			//j.setObj(banners);
			//j.success();
		}catch(Exception e){
			//j.fail();
			e.printStackTrace();
		}
		return "/wsale/home";
	}

	/**
	 * 跳转我的关注-拍品
	 * @return
	 */
	@RequestMapping("/myAttedProduct")
	public String myAttedProduct(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		// 判断用户是否关注公众号
		request.setAttribute("subscribe", "cs".equals(s.getId()) ? true : WeixinUtil.getSubscribe(s.getName()));
		request.setAttribute("sessionInfo", s);
		return "/wsale/my/my_atted_product";
	}

	/**
	 * 跳转我的关注-主题
	 * @return
	 */
	@RequestMapping("/myAttedBbs")
	public String myAttedBbs(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("sessionInfo", s);
		return "/wsale/my/my_atted_bbs";
	}

	/**
	 * 生成html
	 * @return
	 */
	@RequestMapping("/html")
	public void html(String id, HttpServletResponse response) {
		PrintWriter out = null;
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			String content = zcBannerService.get(id).getContent();
			out = response.getWriter();
			out.write("<html><head>");
			out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">");
			out.write("<style type=\"text/css\">");
			out.write("body {font-family:\"微软雅黑\";font-size:12px; background-color:#f8f7f5;}");
			out.write("ul,ol,li{padding:0; margin:0;}");
			out.write("img{border:0; line-height:0; width: 100%;}");
			out.write("ol,ul {list-style:none;}");
			out.write("a { color: #000; text-decoration: none; outline: none;}");
			out.write("a img { border: none; }");
			out.write("</style></head><body>");
			out.write(content);
			out.write("</body></html>");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}
	}

}
