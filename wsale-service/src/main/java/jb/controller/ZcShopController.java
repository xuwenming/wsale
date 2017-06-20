package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcShop;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcShopServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcShop管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcShopController")
public class ZcShopController extends BaseController {

	@Autowired
	private ZcShopServiceI zcShopService;


	/**
	 * 跳转到ZcShop管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcshop/zcShop";
	}

	/**
	 * 获取ZcShop数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcShop zcShop, PageHelper ph) {
		return zcShopService.dataGrid(zcShop, ph);
	}
	/**
	 * 获取ZcShop数据表格excel
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
	public void download(ZcShop zcShop, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcShop,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcShop页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcShop zcShop = new ZcShop();
		zcShop.setId(jb.absx.UUID.uuid());
		return "/zcshop/zcShopAdd";
	}

	/**
	 * 添加ZcShop
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcShop zcShop) {
		Json j = new Json();		
		zcShopService.add(zcShop);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcShop查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcShop zcShop = zcShopService.get(id);
		request.setAttribute("zcShop", zcShop);
		return "/zcshop/zcShopView";
	}

	/**
	 * 跳转到ZcShop修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcShop zcShop = zcShopService.get(id);
		request.setAttribute("zcShop", zcShop);
		return "/zcshop/zcShopEdit";
	}

	/**
	 * 修改ZcShop
	 * 
	 * @param zcShop
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcShop zcShop) {
		Json j = new Json();		
		zcShopService.edit(zcShop);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcShop
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcShopService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
