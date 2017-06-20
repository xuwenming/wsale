package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.RoleServiceI;
import jb.service.UserServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcPositionApplyServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
import jb.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * ZcPositionApply管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcPositionApplyController")
public class ZcPositionApplyController extends BaseController {

	@Autowired
	private ZcPositionApplyServiceI zcPositionApplyService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;


	/**
	 * 跳转到ZcPositionApply管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		boolean mark = false;
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		User user = userService.getByZc(sessionInfo.getId());
		if("UT02".equals(user.getUtype())) {
			String[] roleIds = sessionInfo.getRoleIds().split(",");

			for(String roleId : roleIds) {
				if(F.empty(roleId)) continue;
				if(roleId.trim().equals(Constants.JSSHADMIN)) {
					mark = true;
					break;
				}
			}
		}
		request.setAttribute("mark", mark);
		return "/zcpositionapply/zcPositionApply";
	}

	/**
	 * 获取ZcPositionApply数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcPositionApply zcPositionApply, PageHelper ph) {
		DataGrid dataGrid = zcPositionApplyService.dataGrid(zcPositionApply, ph);

		List<ZcPositionApply> list = (List<ZcPositionApply>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcPositionApply positionApply : list) {

				// 分类名称
				completionService.submit(new Task<ZcPositionApply, ZcCategory>(new CacheKey("category", positionApply.getCategoryId()), positionApply) {
					@Override
					public ZcCategory call() throws Exception {
						ZcCategory c = zcCategoryService.get(getD().getCategoryId());
						ZcCategory pc = null;
						if(!F.empty(c.getPid())) {
							pc = zcCategoryService.get(c.getPid());
						}
						c.setName((pc != null ? pc.getName() + " - " : "") + c.getName());
						return c;
					}

					protected void set(ZcPositionApply d, ZcCategory v) {
						if (v != null)
							d.setCategoryName(v.getName());
					}

				});

				// 申请职位
				completionService.submit(new Task<ZcPositionApply, Role>(new CacheKey("role", positionApply.getRoleId()), positionApply) {
					@Override
					public Role call() throws Exception {
						Role role = roleService.get(getD().getRoleId());
						return role;
					}

					protected void set(ZcPositionApply d, Role v) {
						if (v != null)
							d.setRoleName(v.getName());
					}
				});

				// 申请人
				completionService.submit(new Task<ZcPositionApply, User>(new CacheKey("user", positionApply.getApplyUserId()), positionApply) {
					@Override
					public User call() throws Exception {
						User user = userService.get(getD().getApplyUserId(), true);
						return user;
					}

					protected void set(ZcPositionApply d, User v) {
						if (v != null)
							d.setApplyUserName(v.getNickname());
					}
				});

				if(!F.empty(positionApply.getAuditUserId()))
					// 审核人
					completionService.submit(new Task<ZcPositionApply, User>(new CacheKey("user", positionApply.getAuditUserId()), positionApply) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getAuditUserId(), true);
							return user;
						}

						protected void set(ZcPositionApply d, User v) {
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
	 * 获取ZcPositionApply数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(ZcPositionApply zcPositionApply, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcPositionApply,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcPositionApply页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcPositionApply zcPositionApply = new ZcPositionApply();
		zcPositionApply.setId(UUID.randomUUID().toString());
		return "/zcpositionapply/zcPositionApplyAdd";
	}

	/**
	 * 添加ZcPositionApply
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcPositionApply zcPositionApply) {
		Json j = new Json();		
		zcPositionApplyService.add(zcPositionApply);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcPositionApply查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcPositionApply zcPositionApply = zcPositionApplyService.get(id);
		ZcCategory c = zcCategoryService.get(zcPositionApply.getCategoryId());
		ZcCategory pc = null;
		if(!F.empty(c.getPid())) {
			pc = zcCategoryService.get(c.getPid());
		}
		zcPositionApply.setCategoryName((pc != null ? pc.getName() + " - " : "") + c.getName());
		zcPositionApply.setRoleName(roleService.get(zcPositionApply.getRoleId()).getName());
		zcPositionApply.setApplyUserName(userService.get(zcPositionApply.getApplyUserId(), true).getNickname());
		if(!F.empty(zcPositionApply.getAuditUserId()))
			zcPositionApply.setAuditUserName(userService.get(zcPositionApply.getAuditUserId(), true).getNickname());
		request.setAttribute("zcPositionApply", zcPositionApply);
		return "/zcpositionapply/zcPositionApplyView";
	}

	/**
	 * 跳转到ZcPositionApply修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcPositionApply zcPositionApply = zcPositionApplyService.get(id);
		ZcCategory c = zcCategoryService.get(zcPositionApply.getCategoryId());
		ZcCategory pc = null;
		if(!F.empty(c.getPid())) {
			pc = zcCategoryService.get(c.getPid());
		}
		zcPositionApply.setCategoryName((pc != null ? pc.getName() + " - " : "") + c.getName());
		zcPositionApply.setRoleName(roleService.get(zcPositionApply.getRoleId()).getName());
		zcPositionApply.setApplyUserName(userService.get(zcPositionApply.getApplyUserId(), true).getNickname());
		if(!F.empty(zcPositionApply.getAuditUserId()))
			zcPositionApply.setAuditUserName(userService.get(zcPositionApply.getAuditUserId(), true).getNickname());
		request.setAttribute("zcPositionApply", zcPositionApply);
		return "/zcpositionapply/zcPositionApplyEdit";
	}

	/**
	 * 修改ZcPositionApply
	 * 
	 * @param zcPositionApply
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcPositionApply zcPositionApply, HttpServletRequest request) {
		Json j = new Json();
		zcPositionApply.setAuditUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		//zcPositionApplyService.edit(zcPositionApply);
		zcPositionApplyService.editAudit(zcPositionApply);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcPositionApply
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcPositionApplyService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
