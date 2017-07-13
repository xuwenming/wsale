package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcIntermediaryLog;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcIntermediaryLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcIntermediaryLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcIntermediaryLogController")
public class ZcIntermediaryLogController extends BaseController {

	@Autowired
	private ZcIntermediaryLogServiceI zcIntermediaryLogService;


	/**
	 * 跳转到ZcIntermediaryLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcintermediarylog/zcIntermediaryLog";
	}

	/**
	 * 获取ZcIntermediaryLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcIntermediaryLog zcIntermediaryLog, PageHelper ph) {
		return zcIntermediaryLogService.dataGrid(zcIntermediaryLog, ph);
	}
	/**
	 * 获取ZcIntermediaryLog数据表格excel
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
	public void download(ZcIntermediaryLog zcIntermediaryLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcIntermediaryLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcIntermediaryLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcIntermediaryLog zcIntermediaryLog = new ZcIntermediaryLog();
		zcIntermediaryLog.setId(jb.absx.UUID.uuid());
		return "/zcintermediarylog/zcIntermediaryLogAdd";
	}

	/**
	 * 添加ZcIntermediaryLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcIntermediaryLog zcIntermediaryLog) {
		Json j = new Json();		
		zcIntermediaryLogService.add(zcIntermediaryLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcIntermediaryLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcIntermediaryLog zcIntermediaryLog = zcIntermediaryLogService.get(id);
		request.setAttribute("zcIntermediaryLog", zcIntermediaryLog);
		return "/zcintermediarylog/zcIntermediaryLogView";
	}

	/**
	 * 跳转到ZcIntermediaryLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcIntermediaryLog zcIntermediaryLog = zcIntermediaryLogService.get(id);
		request.setAttribute("zcIntermediaryLog", zcIntermediaryLog);
		return "/zcintermediarylog/zcIntermediaryLogEdit";
	}

	/**
	 * 修改ZcIntermediaryLog
	 * 
	 * @param zcIntermediaryLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcIntermediaryLog zcIntermediaryLog) {
		Json j = new Json();		
		zcIntermediaryLogService.edit(zcIntermediaryLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcIntermediaryLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcIntermediaryLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
