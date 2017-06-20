package jb.controller;

import jb.listener.Application;
import jb.pageModel.Json;
import jb.pageModel.Resource;
import jb.pageModel.SessionInfo;
import jb.pageModel.Tree;
import jb.service.ResourceServiceI;
import jb.service.ResourceTypeServiceI;
import jb.util.ConfigUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * 资源控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/resourceController")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceServiceI resourceService;

	@Autowired
	private ResourceTypeServiceI resourceTypeService;

	/**
	 * 获得资源树(资源类型为菜单类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		return resourceService.tree(sessionInfo);
	}

	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		return resourceService.allTree(sessionInfo);
	}

	/**
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/resource";
	}

	/**
	 * 跳转到资源添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		request.setAttribute("resourceTypeList", resourceTypeService.getResourceTypeList());
		Resource r = new Resource();
		r.setId(UUID.randomUUID().toString());
		request.setAttribute("resource", r);
		return "/admin/resourceAdd";
	}

	/**
	 * 添加资源
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Resource resource, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		resourceService.add(resource, sessionInfo);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到资源编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		request.setAttribute("resourceTypeList", resourceTypeService.getResourceTypeList());
		Resource r = resourceService.get(id);
		request.setAttribute("resource", r);
		return "/admin/resourceEdit";
	}

	/**
	 * 编辑资源
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Resource resource) {
		Json j = new Json();
		resourceService.edit(resource);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 获得资源列表
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<Resource> treeGrid(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		return resourceService.treeGrid(sessionInfo);
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		resourceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}


	/**
	 * 微信自定义菜单
	 *
	 * @return
	 */
	@RequestMapping("/createWxMenu")
	@ResponseBody
	public Json createWxMenu() {

		Json j = new Json();
		try{

			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu());
			// 判断菜单创建结果
			if (0 == result) {
				j.success();
				System.out.println("菜单创建成功！");
				j.setMsg("菜单创建成功！");
			} else {
				j.fail();
				j.setMsg("菜单创建失败，错误码：" + result);
				System.out.println("菜单创建失败，错误码：" + result);
			}


		} catch (Exception e) {
			e.printStackTrace();
			j.fail();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	private Menu getMenu() {
//		//预约管理
//		ViewButton btn1 = new ViewButton();
//		btn1.setName(Constants.MENU_CN_STR_1);
//		btn1.setType("view");
//		btn1.setUrl(WeixinUtil.getOAuthUrl(Constants.MENU_URL_STR_1));
//
//		//疾病预防
//		ViewButton btn3 = new ViewButton();
//		btn3.setName(Constants.MENU_CN_STR_3);
//		btn3.setType("view");
//		btn3.setUrl(hospitalWechatConfig.getDiseasePreventionUrl());
//
//		//免疫计划
//		ViewButton btn4 = new ViewButton();
//		btn4.setName(Constants.MENU_CN_STR_4);
//		btn4.setType("view");
//		btn4.setUrl(WeixinUtil.getOAuthUrl(Constants.MENU_URL_STR_4));
//		//帐号绑定
//		ViewButton btn6 = new ViewButton();
//		btn6.setName(Constants.MENU_CN_STR_6);
//		btn6.setType("view");
//		btn6.setUrl(WeixinUtil.getOAuthUrl(Constants.MENU_URL_STR_6));
//
//		//宝宝信息
//		ViewButton btn7 = new ViewButton();
//		btn7.setName(Constants.MENU_CN_STR_7);
//		btn7.setType("view");
//		btn7.setUrl(WeixinUtil.getOAuthUrl(Constants.MENU_URL_STR_7));
//
//		//备忘录
//		ViewButton btn8 = new ViewButton();
//		btn8.setName(Constants.MENU_CN_STR_8);
//		btn8.setType("view");
//		btn8.setUrl(WeixinUtil.getOAuthUrl(Constants.MENU_URL_STR_8));
//
//		//成长树
//		ViewButton btn9 = new ViewButton();
//		btn9.setName(Constants.MENU_CN_STR_9);
//		btn9.setType("view");
//		btn9.setUrl(WeixinUtil.getOAuthUrl(Constants.MENU_URL_STR_9));
//
//		//自助查询
//		ComplexButton mainBtn2 = new ComplexButton();
//		mainBtn2.setName(Constants.MENU_CN_STR_2);
//		mainBtn2.setSub_button(new Button[] { btn3, btn4});
//
//		//个人中心
//		ComplexButton mainBtn5 = new ComplexButton();
//		mainBtn5.setName(Constants.MENU_CN_STR_5);
//		mainBtn5.setSub_button(new Button[] { btn6, btn7, btn8, btn9});
//
//		Menu menu = new Menu();
//		menu.setButton(new Button[] { btn1, mainBtn2, mainBtn5 });

		// 首页
		ViewButton btn1 = new ViewButton();
		btn1.setType("view");
		btn1.setName("首页");
		btn1.setUrl(WeixinUtil.getOAuthUrl("api/apiHomeController/home"));

		// 发现
		ViewButton btn2 = new ViewButton();
		btn2.setType("view");
		btn2.setName("发现");
		btn2.setUrl(WeixinUtil.getOAuthUrl("api/apiFindController/find"));

		// 人工客服
		CommonButton btn3 = new CommonButton();
		btn3.setType("click");
		btn3.setName("人工客服");
		btn3.setKey("ck_kf");

		// 帮助中心
		ViewButton btn4 = new ViewButton();
		btn4.setType("view");
		btn4.setName("帮助中心");
		btn4.setUrl(Application.getString("WP400"));

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("客户服务");
		mainBtn3.setSub_button(new Button[] { btn3, btn4});
		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, mainBtn3});
		return menu;
	}
}
