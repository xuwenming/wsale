package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcOrderXiaoer;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcOrderXiaoerServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcOrderXiaoer管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcOrderXiaoerController")
public class ZcOrderXiaoerController extends BaseController {

	@Autowired
	private ZcOrderXiaoerServiceI zcOrderXiaoerService;


	/**
	 * 跳转到ZcOrderXiaoer管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcorderxiaoer/zcOrderXiaoer";
	}

	/**
	 * 获取ZcOrderXiaoer数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcOrderXiaoer zcOrderXiaoer, PageHelper ph) {
		return zcOrderXiaoerService.dataGrid(zcOrderXiaoer, ph);
	}
	/**
	 * 获取ZcOrderXiaoer数据表格excel
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
	public void download(ZcOrderXiaoer zcOrderXiaoer, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcOrderXiaoer,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcOrderXiaoer页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcOrderXiaoer zcOrderXiaoer = new ZcOrderXiaoer();
		zcOrderXiaoer.setId(jb.absx.UUID.uuid());
		return "/zcorderxiaoer/zcOrderXiaoerAdd";
	}

	/**
	 * 添加ZcOrderXiaoer
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcOrderXiaoer zcOrderXiaoer) {
		Json j = new Json();		
		zcOrderXiaoerService.add(zcOrderXiaoer);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcOrderXiaoer查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcOrderXiaoer zcOrderXiaoer = zcOrderXiaoerService.get(id);
		request.setAttribute("zcOrderXiaoer", zcOrderXiaoer);
		return "/zcorderxiaoer/zcOrderXiaoerView";
	}

	/**
	 * 跳转到ZcOrderXiaoer修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcOrderXiaoer zcOrderXiaoer = zcOrderXiaoerService.get(id);
		request.setAttribute("zcOrderXiaoer", zcOrderXiaoer);
		return "/zcorderxiaoer/zcOrderXiaoerEdit";
	}

	/**
	 * 修改ZcOrderXiaoer
	 * 
	 * @param zcOrderXiaoer
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcOrderXiaoer zcOrderXiaoer) {
		Json j = new Json();		
		zcOrderXiaoerService.edit(zcOrderXiaoer);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcOrderXiaoer
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcOrderXiaoerService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
