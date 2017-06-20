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
import jb.service.ZcBannerServiceI;

import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

/**
 * ZcBanner管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcBannerController")
public class ZcBannerController extends BaseController {

	@Autowired
	private ZcBannerServiceI zcBannerService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 跳转到ZcBanner管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcbanner/zcBanner";
	}

	/**
	 * 获取ZcBanner数据表格
	 * 
	 * @param zcBanner
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcBanner zcBanner, PageHelper ph) {
		DataGrid dataGrid = zcBannerService.dataGrid(zcBanner, ph);
		List<ZcBanner> list = (List<ZcBanner>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcBanner banner : list) {
				completionService.submit(new Task<ZcBanner, User>(new CacheKey("user", banner.getAddUserId()), banner) {
					@Override
					public User call() throws Exception {
						User user = userService.get(getD().getAddUserId());
						return user;
					}

					protected void set(ZcBanner d, User v) {
						if (v != null)
							d.setAddUserName(v.getNickname());
					}

				});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcBanner数据表格excel
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
	public void download(ZcBanner zcBanner, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcBanner,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcBanner页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcBanner zcBanner = new ZcBanner();
		zcBanner.setId(jb.absx.UUID.uuid());
		return "/zcbanner/zcBannerAdd";
	}

	/**
	 * 添加ZcBanner
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcBanner zcBanner, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		zcBanner.setUrl(uploadFile(request, "banner", iconFile));
		zcBanner.setAddUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcBannerService.add(zcBanner);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcBanner查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcBanner zcBanner = zcBannerService.get(id);
		if(!F.empty(zcBanner.getAddUserId()))
			zcBanner.setAddUserName(userService.get(zcBanner.getAddUserId()).getNickname());
		request.setAttribute("zcBanner", zcBanner);
		return "/zcbanner/zcBannerView";
	}

	/**
	 * 跳转到ZcBanner修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcBanner zcBanner = zcBannerService.get(id);
		request.setAttribute("zcBanner", zcBanner);
		return "/zcbanner/zcBannerEdit";
	}

	/**
	 * 修改ZcBanner
	 * 
	 * @param zcBanner
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcBanner zcBanner, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		zcBanner.setUrl(uploadFile(request, "banner", iconFile));
		zcBanner.setUpdateUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcBanner.setUpdatetime(new Date());
		zcBannerService.edit(zcBanner);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcBanner
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcBannerService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
