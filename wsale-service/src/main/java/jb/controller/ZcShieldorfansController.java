package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcShieldorfansServiceI;
import jb.service.impl.CompletionFactory;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ZcShieldorfans管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcShieldorfansController")
public class ZcShieldorfansController extends BaseController {

	@Autowired
	private ZcShieldorfansServiceI zcShieldorfansService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 跳转到ZcShieldorfans管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcshieldorfans/zcShieldorfans";
	}

	/**
	 * 获取ZcShieldorfans数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcShieldorfans zcShieldorfans, PageHelper ph) {
		zcShieldorfans.setIsDeleted(false);
		DataGrid dataGrid = zcShieldorfansService.dataGrid(zcShieldorfans, ph);
		List<ZcShieldorfans> list = (List<ZcShieldorfans>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcShieldorfans sd : list) {
				if(!F.empty(sd.getObjectId()))
					completionService.submit(new Task<ZcShieldorfans, User>(new CacheKey("user", sd.getObjectId()), sd) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getObjectId());
							return user;
						}

						protected void set(ZcShieldorfans d, User v) {
							if (v != null)
								d.setObjectName(v.getNickname());
						}

					});
				if(!F.empty(sd.getObjectById()))
					completionService.submit(new Task<ZcShieldorfans, User>(new CacheKey("user", sd.getObjectById()), sd) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getObjectById());
							return user;
						}

						protected void set(ZcShieldorfans d, User v) {
							if (v != null)
								d.setObjectByName(v.getNickname());
						}

					});

			}
			completionService.sync();
		}
		return dataGrid;
	}

	@RequestMapping("/dataGridByUser")
	@ResponseBody
	public DataGrid dataGridByUser(ZcShieldorfans zcShieldorfans, PageHelper ph) {
		return dataGrid(zcShieldorfans, ph);
	}
	/**
	 * 获取ZcShieldorfans数据表格excel
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
	public void download(ZcShieldorfans zcShieldorfans, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcShieldorfans,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcShieldorfans页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcShieldorfans zcShieldorfans = new ZcShieldorfans();
		zcShieldorfans.setId(UUID.randomUUID().toString());
		return "/zcshieldorfans/zcShieldorfansAdd";
	}

	/**
	 * 添加ZcShieldorfans
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcShieldorfans zcShieldorfans) {
		Json j = new Json();		
		zcShieldorfansService.add(zcShieldorfans);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcShieldorfans查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcShieldorfans zcShieldorfans = zcShieldorfansService.get(id);
		request.setAttribute("zcShieldorfans", zcShieldorfans);
		return "/zcshieldorfans/zcShieldorfansView";
	}

	/**
	 * 跳转到ZcShieldorfans修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcShieldorfans zcShieldorfans = zcShieldorfansService.get(id);
		request.setAttribute("zcShieldorfans", zcShieldorfans);
		return "/zcshieldorfans/zcShieldorfansEdit";
	}

	/**
	 * 修改ZcShieldorfans
	 * 
	 * @param zcShieldorfans
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcShieldorfans zcShieldorfans) {
		Json j = new Json();		
		zcShieldorfansService.edit(zcShieldorfans);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcShieldorfans
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcShieldorfansService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
