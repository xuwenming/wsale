package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcNewHistory;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcNewHistoryServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcNewHistory管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcNewHistoryController")
public class ZcNewHistoryController extends BaseController {

	@Autowired
	private ZcNewHistoryServiceI zcNewHistoryService;


	/**
	 * 跳转到ZcNewHistory管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcnewhistory/zcNewHistory";
	}

	/**
	 * 获取ZcNewHistory数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcNewHistory zcNewHistory, PageHelper ph) {
		return zcNewHistoryService.dataGrid(zcNewHistory, ph);
	}
	/**
	 * 获取ZcNewHistory数据表格excel
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
	public void download(ZcNewHistory zcNewHistory, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcNewHistory,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcNewHistory页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcNewHistory zcNewHistory = new ZcNewHistory();
		zcNewHistory.setId(jb.absx.UUID.uuid());
		return "/zcnewhistory/zcNewHistoryAdd";
	}

	/**
	 * 添加ZcNewHistory
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcNewHistory zcNewHistory) {
		Json j = new Json();		
		zcNewHistoryService.add(zcNewHistory);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcNewHistory查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcNewHistory zcNewHistory = zcNewHistoryService.get(id);
		request.setAttribute("zcNewHistory", zcNewHistory);
		return "/zcnewhistory/zcNewHistoryView";
	}

	/**
	 * 跳转到ZcNewHistory修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcNewHistory zcNewHistory = zcNewHistoryService.get(id);
		request.setAttribute("zcNewHistory", zcNewHistory);
		return "/zcnewhistory/zcNewHistoryEdit";
	}

	/**
	 * 修改ZcNewHistory
	 * 
	 * @param zcNewHistory
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcNewHistory zcNewHistory) {
		Json j = new Json();		
		zcNewHistoryService.edit(zcNewHistory);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcNewHistory
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcNewHistoryService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
