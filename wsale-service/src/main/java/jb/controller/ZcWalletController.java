package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcWallet;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcWalletServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcWallet管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcWalletController")
public class ZcWalletController extends BaseController {

	@Autowired
	private ZcWalletServiceI zcWalletService;


	/**
	 * 跳转到ZcWallet管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcwallet/zcWallet";
	}

	/**
	 * 获取ZcWallet数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcWallet zcWallet, PageHelper ph) {
		return zcWalletService.dataGrid(zcWallet, ph);
	}
	/**
	 * 获取ZcWallet数据表格excel
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
	public void download(ZcWallet zcWallet, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcWallet,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcWallet页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcWallet zcWallet = new ZcWallet();
		zcWallet.setId(jb.absx.UUID.uuid());
		return "/zcwallet/zcWalletAdd";
	}

	/**
	 * 添加ZcWallet
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcWallet zcWallet) {
		Json j = new Json();		
		zcWalletService.add(zcWallet);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcWallet查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcWallet zcWallet = zcWalletService.get(id);
		request.setAttribute("zcWallet", zcWallet);
		return "/zcwallet/zcWalletView";
	}

	/**
	 * 跳转到ZcWallet修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcWallet zcWallet = zcWalletService.get(id);
		request.setAttribute("zcWallet", zcWallet);
		return "/zcwallet/zcWalletEdit";
	}

	/**
	 * 修改ZcWallet
	 * 
	 * @param zcWallet
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcWallet zcWallet) {
		Json j = new Json();		
		zcWalletService.edit(zcWallet);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcWallet
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcWalletService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
