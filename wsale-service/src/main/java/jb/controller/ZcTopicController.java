package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcTopicServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
import jb.util.ImageUtils;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * ZcTopic管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcTopicController")
public class ZcTopicController extends BaseController {

	@Autowired
	private ZcTopicServiceI zcTopicService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	/**
	 * 跳转到ZcTopic管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zctopic/zcTopic";
	}

	/**
	 * 获取ZcTopic数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcTopic zcTopic, PageHelper ph) {
		DataGrid dataGrid = zcTopicService.dataGrid(zcTopic, ph);

		List<ZcTopic> list = (List<ZcTopic>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcTopic topic : list) {
				completionService.submit(new Task<ZcTopic, User>(new CacheKey("user", topic.getAddUserId()), topic) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getAddUserId());
						return user;
					}

					protected void set(ZcTopic d, User v) {
						if (v != null)
							d.setAddUserName(v.getNickname());
					}
				});

				if(!F.empty(topic.getCategoryId()))
					completionService.submit(new Task<ZcTopic, String>(new CacheKey("category", topic.getCategoryId()), topic) {
						@Override
						public String call() throws Exception {
							ZcCategory c = zcCategoryService.get(getD().getCategoryId());
							ZcCategory pc = null;
							if(!F.empty(c.getPid())) {
								pc = zcCategoryService.get(c.getPid());
							}
							return (pc != null ? pc.getName() + " - " : "") + c.getName();
						}

						protected void set(ZcTopic d, String v) {
							if (v != null)
								d.setCategoryName(v);
						}
					});
			}
			completionService.sync();
		}

		return dataGrid;
	}
	/**
	 * 获取ZcTopic数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(ZcTopic zcTopic, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcTopic,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcTopic页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcTopic zcTopic = new ZcTopic();
		zcTopic.setId(jb.absx.UUID.uuid());
		return "/zctopic/zcTopicAdd";
	}

	/**
	 * 添加ZcTopic
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcTopic zcTopic, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String iconPath = uploadFile(request, "topic", iconFile);
		zcTopic.setIcon(ImageUtils.pressImage(iconPath, realPath));
		zcTopic.setContent(ImageUtils.replaceHtmlTag(zcTopic.getContent(), "img", "src", "src=\"", "\"", realPath));
		zcTopic.setAddUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());

		zcTopicService.add(zcTopic);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcTopic查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcTopic zcTopic = zcTopicService.get(id);
		User user = userService.getByZc(zcTopic.getAddUserId());
		zcTopic.setAddUserName(user.getNickname());
		if(!F.empty(zcTopic.getCategoryId())) {
			ZcCategory category = zcCategoryService.get(zcTopic.getCategoryId());
			ZcCategory pc = null;
			if(!F.empty(category.getPid())) {
				pc = zcCategoryService.get(category.getPid());
			}
			zcTopic.setCategoryName((pc != null ? pc.getName() + " - " : "") + category.getName());
		}
		request.setAttribute("zcTopic", zcTopic);

		return "/zctopic/zcTopicView";
	}

	/**
	 * 跳转到ZcTopic修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcTopic zcTopic = zcTopicService.get(id);
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		User user = userService.getByZc(sessionInfo.getId());
		request.setAttribute("zcTopic", zcTopic);
		request.setAttribute("utype", user.getUtype());
		return "/zctopic/zcTopicEdit";
	}

	/**
	 * 修改ZcTopic
	 * 
	 * @param zcTopic
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcTopic zcTopic, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String iconPath = uploadFile(request, "topic", iconFile);
		zcTopic.setIcon(ImageUtils.pressImage(iconPath, realPath));
		zcTopic.setContent(ImageUtils.replaceHtmlTag(zcTopic.getContent(), "img", "src", "src=\"", "\"", realPath));
		zcTopic.setUpdateUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcTopic.setUpdatetime(new Date());
		zcTopicService.edit(zcTopic);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcTopic
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcTopicService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
