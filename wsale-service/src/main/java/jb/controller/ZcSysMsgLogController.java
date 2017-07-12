package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcSysMsgLog;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcSysMsgLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcSysMsgLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcSysMsgLogController")
public class ZcSysMsgLogController extends BaseController {

	@Autowired
	private ZcSysMsgLogServiceI zcSysMsgLogService;


	/**
	 * 跳转到ZcSysMsgLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcsysmsglog/zcSysMsgLog";
	}

	/**
	 * 获取ZcSysMsgLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcSysMsgLog zcSysMsgLog, PageHelper ph) {
		return zcSysMsgLogService.dataGrid(zcSysMsgLog, ph);
	}
	/**
	 * 获取ZcSysMsgLog数据表格excel
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
	public void download(ZcSysMsgLog zcSysMsgLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcSysMsgLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcSysMsgLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcSysMsgLog zcSysMsgLog = new ZcSysMsgLog();
		zcSysMsgLog.setId(jb.absx.UUID.uuid());
		return "/zcsysmsglog/zcSysMsgLogAdd";
	}

	/**
	 * 添加ZcSysMsgLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcSysMsgLog zcSysMsgLog) {
		Json j = new Json();		
		zcSysMsgLogService.add(zcSysMsgLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcSysMsgLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcSysMsgLog zcSysMsgLog = zcSysMsgLogService.get(id);
		request.setAttribute("zcSysMsgLog", zcSysMsgLog);
		return "/zcsysmsglog/zcSysMsgLogView";
	}

	/**
	 * 跳转到ZcSysMsgLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcSysMsgLog zcSysMsgLog = zcSysMsgLogService.get(id);
		request.setAttribute("zcSysMsgLog", zcSysMsgLog);
		return "/zcsysmsglog/zcSysMsgLogEdit";
	}

	/**
	 * 修改ZcSysMsgLog
	 * 
	 * @param zcSysMsgLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcSysMsgLog zcSysMsgLog) {
		Json j = new Json();		
		zcSysMsgLogService.edit(zcSysMsgLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcSysMsgLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcSysMsgLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
