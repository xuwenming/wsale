package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcReadRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcReadRecordServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcReadRecord管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcReadRecordController")
public class ZcReadRecordController extends BaseController {

	@Autowired
	private ZcReadRecordServiceI zcReadRecordService;


	/**
	 * 跳转到ZcReadRecord管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcreadrecord/zcReadRecord";
	}

	/**
	 * 获取ZcReadRecord数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcReadRecord zcReadRecord, PageHelper ph) {
		return zcReadRecordService.dataGrid(zcReadRecord, ph);
	}
	/**
	 * 获取ZcReadRecord数据表格excel
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
	public void download(ZcReadRecord zcReadRecord, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcReadRecord,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcReadRecord页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcReadRecord zcReadRecord = new ZcReadRecord();
		zcReadRecord.setId(jb.absx.UUID.uuid());
		return "/zcreadrecord/zcReadRecordAdd";
	}

	/**
	 * 添加ZcReadRecord
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcReadRecord zcReadRecord) {
		Json j = new Json();		
		zcReadRecordService.add(zcReadRecord);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcReadRecord查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcReadRecord zcReadRecord = zcReadRecordService.get(id);
		request.setAttribute("zcReadRecord", zcReadRecord);
		return "/zcreadrecord/zcReadRecordView";
	}

	/**
	 * 跳转到ZcReadRecord修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcReadRecord zcReadRecord = zcReadRecordService.get(id);
		request.setAttribute("zcReadRecord", zcReadRecord);
		return "/zcreadrecord/zcReadRecordEdit";
	}

	/**
	 * 修改ZcReadRecord
	 * 
	 * @param zcReadRecord
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcReadRecord zcReadRecord) {
		Json j = new Json();		
		zcReadRecordService.edit(zcReadRecord);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcReadRecord
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcReadRecordService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
