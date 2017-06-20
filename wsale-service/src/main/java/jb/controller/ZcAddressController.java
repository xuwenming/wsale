package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcAddress;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcAddressServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcAddress管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcAddressController")
public class ZcAddressController extends BaseController {

	@Autowired
	private ZcAddressServiceI zcAddressService;


	/**
	 * 跳转到ZcAddress管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcaddress/zcAddress";
	}

	/**
	 * 获取ZcAddress数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcAddress zcAddress, PageHelper ph) {
		return zcAddressService.dataGrid(zcAddress, ph);
	}
	/**
	 * 获取ZcAddress数据表格excel
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
	public void download(ZcAddress zcAddress, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcAddress,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcAddress页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcAddress zcAddress = new ZcAddress();
		zcAddress.setId(jb.absx.UUID.uuid());
		return "/zcaddress/zcAddressAdd";
	}

	/**
	 * 添加ZcAddress
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcAddress zcAddress) {
		Json j = new Json();		
		zcAddressService.add(zcAddress);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcAddress查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcAddress zcAddress = zcAddressService.get(id);
		request.setAttribute("zcAddress", zcAddress);
		return "/zcaddress/zcAddressView";
	}

	/**
	 * 跳转到ZcAddress修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcAddress zcAddress = zcAddressService.get(id);
		request.setAttribute("zcAddress", zcAddress);
		return "/zcaddress/zcAddressEdit";
	}

	/**
	 * 修改ZcAddress
	 * 
	 * @param zcAddress
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcAddress zcAddress) {
		Json j = new Json();		
		zcAddressService.edit(zcAddress);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcAddress
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcAddressService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
