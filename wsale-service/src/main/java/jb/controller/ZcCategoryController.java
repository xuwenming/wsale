package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcPositionApplyServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * ZcCategory管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcCategoryController")
public class ZcCategoryController extends BaseController {

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcPositionApplyServiceI zcPositionApplyService;


	/**
	 * 跳转到ZcCategory管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zccategory/zcCategory";
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<ZcCategory> tree() {
		ZcCategory c = new ZcCategory();
		c.setIsDeleted(false);
		return zcCategoryService.query(c);
	}

	/**
	 * 获取ZcCategory数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcCategory zcCategory, PageHelper ph) {
		zcCategory.setIsDeleted(false);
		DataGrid dataGrid = zcCategoryService.dataGrid(zcCategory, ph);
		List<ZcCategory> list = (List<ZcCategory>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcCategory category : list) {
				if(!F.empty(category.getPid()) && !"0".equals(category.getPid())) {
					completionService.submit(new Task<ZcCategory, ZcCategory>(new CacheKey("category", category.getPid()), category) {
						@Override
						public ZcCategory call() throws Exception {
							ZcCategory c = zcCategoryService.get(getD().getPid());
							return c;
						}

						protected void set(ZcCategory d, ZcCategory v) {
							if (v != null)
								d.setPname(v.getName());
						}

					});
				}

				if(!F.empty(category.getChiefModeratorId())) {
					completionService.submit(new Task<ZcCategory, User>(new CacheKey("user", category.getChiefModeratorId()), category) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getChiefModeratorId());
							return user;
						}

						protected void set(ZcCategory d, User v) {
							if (v != null)
								d.setChiefModeratorName(v.getNickname());
						}
					});
				}

				completionService.submit(new Task<ZcCategory, User>(new CacheKey("user", category.getAddUserId()), category) {
					@Override
					public User call() throws Exception {
						User user = userService.get(getD().getAddUserId());
						return user;
					}

					protected void set(ZcCategory d, User v) {
						if (v != null)
							d.setAddUserName(v.getNickname());
					}

				});

				completionService.submit(new Task<ZcCategory, String>(category) {
					@Override
					public String call() throws Exception {
						// 获取审核通过的版主集合
						List<User> moderators = zcPositionApplyService.getAllModerators(getD().getId());
						String moderatorsStr = "";
						for(User user : moderators) {
							moderatorsStr += "、" + user.getNickname();
						}
						if(!"".equals(moderatorsStr)) moderatorsStr = moderatorsStr.substring(1);
						return moderatorsStr;
					}

					protected void set(ZcCategory d, String v) {
						d.setModeratorsStr(v);
					}

				});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcCategory数据表格excel
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
	public void download(ZcCategory zcCategory, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcCategory,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcCategory页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcCategory zcCategory = new ZcCategory();
		zcCategory.setId(jb.absx.UUID.uuid());
		return "/zccategory/zcCategoryAdd";
	}

	/**
	 * 添加ZcCategory
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcCategory zcCategory, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		zcCategory.setIcon(uploadFile(request, "category", iconFile));
		zcCategory.setAddUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcCategory.setIsDeleted(false);
		if(F.empty(zcCategory.getPid())) zcCategory.setPid("0");
		zcCategoryService.add(zcCategory);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcCategory查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcCategory zcCategory = zcCategoryService.get(id);
		if(!F.empty(zcCategory.getPid()) && !"0".equals(zcCategory.getPid()))
			zcCategory.setPname(zcCategoryService.get(zcCategory.getPid()).getName());
		if(!F.empty(zcCategory.getAddUserId()))
			zcCategory.setAddUserName(userService.get(zcCategory.getAddUserId()).getName());
		if(!F.empty(zcCategory.getChiefModeratorId()))
			zcCategory.setChiefModeratorName(userService.get(zcCategory.getChiefModeratorId()).getNickname());

		request.setAttribute("zcCategory", zcCategory);
		return "/zccategory/zcCategoryView";
	}

	/**
	 * 跳转到ZcCategory修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcCategory zcCategory = zcCategoryService.get(id);
		if(!F.empty(zcCategory.getChiefModeratorId()))
			zcCategory.setChiefModeratorName(userService.get(zcCategory.getChiefModeratorId()).getNickname());
		request.setAttribute("zcCategory", zcCategory);
		return "/zccategory/zcCategoryEdit";
	}

	/**
	 * 修改ZcCategory
	 * 
	 * @param zcCategory
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcCategory zcCategory, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		zcCategory.setIcon(uploadFile(request, "category", iconFile));
		zcCategory.setUpdateUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcCategory.setUpdatetime(new Date());
		if(F.empty(zcCategory.getPid()))
			zcCategory.setPid("0");
		zcCategoryService.edit(zcCategory);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcCategory
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(ZcCategory zcCategory) {
		Json j = new Json();
		zcCategoryService.delete(zcCategory);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
