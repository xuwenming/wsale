package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcUserAutoPriceServiceI;
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
import java.util.List;

/**
 * ZcUserAutoPrice管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcUserAutoPriceController")
public class ZcUserAutoPriceController extends BaseController {

	@Autowired
	private ZcUserAutoPriceServiceI zcUserAutoPriceService;

	@Autowired
	private UserServiceI userService;


	/**
	 * 跳转到ZcUserAutoPrice管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcuserautoprice/zcUserAutoPrice";
	}

	/**
	 * 获取ZcUserAutoPrice数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcUserAutoPrice zcUserAutoPrice, PageHelper ph) {
		DataGrid dataGrid = zcUserAutoPriceService.dataGrid(zcUserAutoPrice, ph);
		List<ZcUserAutoPrice> list = (List<ZcUserAutoPrice>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcUserAutoPrice auto : list) {
				completionService.submit(new Task<ZcUserAutoPrice, User>(new CacheKey("user", auto.getUserId()), auto) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getUserId());
						return user;
					}

					protected void set(ZcUserAutoPrice d, User v) {
						if (v != null)
							d.setUserName(v.getNickname());
					}

				});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	@RequestMapping("/dataGridByProduct")
	@ResponseBody
	public DataGrid dataGridByProduct(ZcUserAutoPrice zcUserAutoPrice, PageHelper ph) {
		return dataGrid(zcUserAutoPrice, ph);
	}
	/**
	 * 获取ZcUserAutoPrice数据表格excel
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
	public void download(ZcUserAutoPrice zcUserAutoPrice, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcUserAutoPrice,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcUserAutoPrice页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcUserAutoPrice zcUserAutoPrice = new ZcUserAutoPrice();
		zcUserAutoPrice.setId(jb.absx.UUID.uuid());
		return "/zcuserautoprice/zcUserAutoPriceAdd";
	}

	/**
	 * 添加ZcUserAutoPrice
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcUserAutoPrice zcUserAutoPrice) {
		Json j = new Json();		
		zcUserAutoPriceService.add(zcUserAutoPrice);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcUserAutoPrice查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcUserAutoPrice zcUserAutoPrice = zcUserAutoPriceService.get(id);
		request.setAttribute("zcUserAutoPrice", zcUserAutoPrice);
		return "/zcuserautoprice/zcUserAutoPriceView";
	}

	/**
	 * 跳转到ZcUserAutoPrice修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcUserAutoPrice zcUserAutoPrice = zcUserAutoPriceService.get(id);
		request.setAttribute("zcUserAutoPrice", zcUserAutoPrice);
		return "/zcuserautoprice/zcUserAutoPriceEdit";
	}

	/**
	 * 修改ZcUserAutoPrice
	 * 
	 * @param zcUserAutoPrice
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcUserAutoPrice zcUserAutoPrice) {
		Json j = new Json();		
		zcUserAutoPriceService.edit(zcUserAutoPrice);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcUserAutoPrice
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcUserAutoPriceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
