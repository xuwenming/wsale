package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.ZcFileServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * ZcFile管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcFileController")
public class ZcFileController extends BaseController {

	@Autowired
	private ZcFileServiceI zcFileService;


	/**
	 * 跳转到ZcFile管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcfile/zcFile";
	}

	/**
	 * 获取ZcFile数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcFile zcFile, PageHelper ph) {
		return zcFileService.dataGrid(zcFile, ph);
	}
	/**
	 * 获取ZcFile数据表格excel
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
	public void download(ZcFile zcFile, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcFile,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcFile页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcFile zcFile = new ZcFile();
		zcFile.setId(UUID.randomUUID().toString());
		return "/zcfile/zcFileAdd";
	}

	/**
	 * 添加ZcFile
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcFile zcFile) {
		Json j = new Json();		
		zcFileService.add(zcFile);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcFile查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcFile zcFile = zcFileService.get(id);
		request.setAttribute("zcFile", zcFile);
		return "/zcfile/zcFileView";
	}

	/**
	 * 跳转到ZcFile修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcFile zcFile = zcFileService.get(id);
		request.setAttribute("zcFile", zcFile);
		return "/zcfile/zcFileEdit";
	}

	/**
	 * 修改ZcFile
	 * 
	 * @param zcFile
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcFile zcFile) {
		Json j = new Json();		
		zcFileService.edit(zcFile);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcFile
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcFileService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
