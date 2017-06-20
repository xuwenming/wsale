package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcLastViewLog;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcLastViewLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcLastViewLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcLastViewLogController")
public class ZcLastViewLogController extends BaseController {

	@Autowired
	private ZcLastViewLogServiceI zcLastViewLogService;


	/**
	 * 跳转到ZcLastViewLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zclastviewlog/zcLastViewLog";
	}

	/**
	 * 获取ZcLastViewLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcLastViewLog zcLastViewLog, PageHelper ph) {
		return zcLastViewLogService.dataGrid(zcLastViewLog, ph);
	}
	/**
	 * 获取ZcLastViewLog数据表格excel
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
	public void download(ZcLastViewLog zcLastViewLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcLastViewLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcLastViewLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcLastViewLog zcLastViewLog = new ZcLastViewLog();
		zcLastViewLog.setId(jb.absx.UUID.uuid());
		return "/zclastviewlog/zcLastViewLogAdd";
	}

	/**
	 * 添加ZcLastViewLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcLastViewLog zcLastViewLog) {
		Json j = new Json();		
		zcLastViewLogService.add(zcLastViewLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcLastViewLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcLastViewLog zcLastViewLog = zcLastViewLogService.get(id);
		request.setAttribute("zcLastViewLog", zcLastViewLog);
		return "/zclastviewlog/zcLastViewLogView";
	}

	/**
	 * 跳转到ZcLastViewLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcLastViewLog zcLastViewLog = zcLastViewLogService.get(id);
		request.setAttribute("zcLastViewLog", zcLastViewLog);
		return "/zclastviewlog/zcLastViewLogEdit";
	}

	/**
	 * 修改ZcLastViewLog
	 * 
	 * @param zcLastViewLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcLastViewLog zcLastViewLog) {
		Json j = new Json();		
		zcLastViewLogService.edit(zcLastViewLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcLastViewLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcLastViewLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
