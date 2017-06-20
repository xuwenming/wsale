package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcAuthenticationServiceI;

import jb.service.ZcShopServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

/**
 * ZcAuthentication管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcAuthenticationController")
public class ZcAuthenticationController extends BaseController {

	@Autowired
	private ZcAuthenticationServiceI zcAuthenticationService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcShopServiceI zcShopService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;


	/**
	 * 跳转到ZcAuthentication管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcauthentication/zcAuthentication";
	}

	/**
	 * 获取ZcAuthentication数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcAuthentication zcAuthentication, PageHelper ph) {
		DataGrid dataGrid = zcAuthenticationService.dataGrid(zcAuthentication, ph);

		List<ZcAuthentication> list = (List<ZcAuthentication>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcAuthentication auth : list) {
				// 认证用户
				completionService.submit(new Task<ZcAuthentication, User>(new CacheKey("user", auth.getAddUserId()), auth) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getAddUserId());
						return user;
					}

					protected void set(ZcAuthentication d, User v) {
						if (v != null)
							d.setAddUserName(v.getNickname());
					}
				});

				if(!F.empty(auth.getAuditUserId()))
					// 审核人
					completionService.submit(new Task<ZcAuthentication, User>(new CacheKey("user", auth.getAuditUserId()), auth) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getAuditUserId(), true);
							return user;
						}

						protected void set(ZcAuthentication d, User v) {
							if (v != null)
								d.setAuditUserName(v.getNickname());
						}
					});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcAuthentication数据表格excel
	 * 
	 * @param
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(ZcAuthentication zcAuthentication, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcAuthentication,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcAuthentication页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcAuthentication zcAuthentication = new ZcAuthentication();
		zcAuthentication.setId(jb.absx.UUID.uuid());
		return "/zcauthentication/zcAuthenticationAdd";
	}

	/**
	 * 添加ZcAuthentication
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcAuthentication zcAuthentication) {
		Json j = new Json();		
		zcAuthenticationService.add(zcAuthentication);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcAuthentication查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcAuthentication zcAuthentication = zcAuthenticationService.get(id);
		User user = userService.getByZc(zcAuthentication.getAddUserId());
		zcAuthentication.setAddUserName(user.getNickname());
		if(!F.empty(zcAuthentication.getAuditUserId()))
			zcAuthentication.setAuditUserName(userService.getByZc(zcAuthentication.getAuditUserId()).getNickname());
		request.setAttribute("zcAuthentication", zcAuthentication);

		ZcShop q = new ZcShop();
		q.setUserId(zcAuthentication.getAddUserId());
		ZcShop shop = zcShopService.get(q);
		request.setAttribute("shop", shop);
		if(shop != null && F.empty(shop.getLogoUrl()))
			shop.setLogoUrl(user.getHeadImage());

		return "/zcauthentication/zcAuthenticationView";
	}

	/**
	 * 跳转到ZcAuthentication修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcAuthentication zcAuthentication = zcAuthenticationService.get(id);
		User user = userService.getByZc(zcAuthentication.getAddUserId());
		zcAuthentication.setAddUserName(user.getNickname());
		if(!F.empty(zcAuthentication.getAuditUserId()))
			zcAuthentication.setAuditUserName(userService.getByZc(zcAuthentication.getAuditUserId()).getNickname());
		request.setAttribute("zcAuthentication", zcAuthentication);

		ZcShop q = new ZcShop();
		q.setUserId(zcAuthentication.getAddUserId());
		ZcShop shop = zcShopService.get(q);
		if(shop != null && F.empty(shop.getLogoUrl()))
			shop.setLogoUrl(user.getHeadImage());
		request.setAttribute("shop", shop);

		return "/zcauthentication/zcAuthenticationEdit";
	}

	/**
	 * 修改ZcAuthentication
	 * 
	 * @param zcAuthentication
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcAuthentication zcAuthentication, HttpServletRequest request) {
		Json j = new Json();
		try {
			zcAuthentication.setAuditUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
			zcAuthentication.setAuditTime(new Date());
			zcAuthenticationService.edit(zcAuthentication);

			// 更新用户表实名状态
			User user = new User();
			user.setId(zcAuthentication.getAddUserId());
			if("AS02".equals(zcAuthentication.getAuditStatus())) {
				user.setIsAuth(true);

				// 推送认证成功通知
				sendWxMessage.sendTemplateMessage(zcAuthentication.getId(), "AUTH");
			} else if("AS03".equals(zcAuthentication.getAuditStatus())) {
				user.setIsAuth(false);
				// 推送认证失败通知
				sendWxMessage.sendAuthFailTemplateMessage(zcAuthentication.getId());
			}
			userService.editAuth(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.fail();
		}

		return j;
	}

	/**
	 * 删除ZcAuthentication
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcAuthenticationService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
