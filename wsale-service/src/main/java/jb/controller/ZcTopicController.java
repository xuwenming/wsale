package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcTopic;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcTopicServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcTopic管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcTopicController")
public class ZcTopicController extends BaseController {

	@Autowired
	private ZcTopicServiceI zcTopicService;


	/**
	 * 跳转到ZcTopic管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zctopic/zcTopic";
	}

	/**
	 * 获取ZcTopic数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcTopic zcTopic, PageHelper ph) {
		return zcTopicService.dataGrid(zcTopic, ph);
	}
	/**
	 * 获取ZcTopic数据表格excel
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
	public void download(ZcTopic zcTopic, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcTopic,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcTopic页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcTopic zcTopic = new ZcTopic();
		zcTopic.setId(jb.absx.UUID.uuid());
		return "/zctopic/zcTopicAdd";
	}

	/**
	 * 添加ZcTopic
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcTopic zcTopic) {
		Json j = new Json();		
		zcTopicService.add(zcTopic);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcTopic查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcTopic zcTopic = zcTopicService.get(id);
		request.setAttribute("zcTopic", zcTopic);
		return "/zctopic/zcTopicView";
	}

	/**
	 * 跳转到ZcTopic修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcTopic zcTopic = zcTopicService.get(id);
		request.setAttribute("zcTopic", zcTopic);
		return "/zctopic/zcTopicEdit";
	}

	/**
	 * 修改ZcTopic
	 * 
	 * @param zcTopic
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcTopic zcTopic) {
		Json j = new Json();		
		zcTopicService.edit(zcTopic);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcTopic
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcTopicService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
