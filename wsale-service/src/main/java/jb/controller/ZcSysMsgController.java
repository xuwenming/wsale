package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcSysMsg;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcSysMsgServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcSysMsg管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcSysMsgController")
public class ZcSysMsgController extends BaseController {

	@Autowired
	private ZcSysMsgServiceI zcSysMsgService;


	/**
	 * 跳转到ZcSysMsg管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcsysmsg/zcSysMsg";
	}

	/**
	 * 获取ZcSysMsg数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcSysMsg zcSysMsg, PageHelper ph) {
		return zcSysMsgService.dataGrid(zcSysMsg, ph);
	}
	/**
	 * 获取ZcSysMsg数据表格excel
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
	public void download(ZcSysMsg zcSysMsg, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcSysMsg,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcSysMsg页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcSysMsg zcSysMsg = new ZcSysMsg();
		zcSysMsg.setId(jb.absx.UUID.uuid());
		return "/zcsysmsg/zcSysMsgAdd";
	}

	/**
	 * 添加ZcSysMsg
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcSysMsg zcSysMsg) {
		Json j = new Json();		
		zcSysMsgService.add(zcSysMsg);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcSysMsg查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcSysMsg zcSysMsg = zcSysMsgService.get(id);
		request.setAttribute("zcSysMsg", zcSysMsg);
		return "/zcsysmsg/zcSysMsgView";
	}

	/**
	 * 跳转到ZcSysMsg修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcSysMsg zcSysMsg = zcSysMsgService.get(id);
		request.setAttribute("zcSysMsg", zcSysMsg);
		return "/zcsysmsg/zcSysMsgEdit";
	}

	/**
	 * 修改ZcSysMsg
	 * 
	 * @param zcSysMsg
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcSysMsg zcSysMsg) {
		Json j = new Json();		
		zcSysMsgService.edit(zcSysMsg);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcSysMsg
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcSysMsgService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
