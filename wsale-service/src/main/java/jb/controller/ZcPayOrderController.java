package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcPayOrder;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcPayOrderServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcPayOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcPayOrderController")
public class ZcPayOrderController extends BaseController {

	@Autowired
	private ZcPayOrderServiceI zcPayOrderService;


	/**
	 * 跳转到ZcPayOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcpayorder/zcPayOrder";
	}

	/**
	 * 获取ZcPayOrder数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcPayOrder zcPayOrder, PageHelper ph) {
		return zcPayOrderService.dataGrid(zcPayOrder, ph);
	}
	/**
	 * 获取ZcPayOrder数据表格excel
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
	public void download(ZcPayOrder zcPayOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcPayOrder,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcPayOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcPayOrder zcPayOrder = new ZcPayOrder();
		zcPayOrder.setId(jb.absx.UUID.uuid());
		return "/zcpayorder/zcPayOrderAdd";
	}

	/**
	 * 添加ZcPayOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcPayOrder zcPayOrder) {
		Json j = new Json();		
		zcPayOrderService.add(zcPayOrder);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcPayOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcPayOrder zcPayOrder = zcPayOrderService.get(id);
		request.setAttribute("zcPayOrder", zcPayOrder);
		return "/zcpayorder/zcPayOrderView";
	}

	/**
	 * 跳转到ZcPayOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcPayOrder zcPayOrder = zcPayOrderService.get(id);
		request.setAttribute("zcPayOrder", zcPayOrder);
		return "/zcpayorder/zcPayOrderEdit";
	}

	/**
	 * 修改ZcPayOrder
	 * 
	 * @param zcPayOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcPayOrder zcPayOrder) {
		Json j = new Json();		
		zcPayOrderService.edit(zcPayOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcPayOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcPayOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
