package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcIntermediary;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcIntermediaryServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcIntermediary管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcIntermediaryController")
public class ZcIntermediaryController extends BaseController {

	@Autowired
	private ZcIntermediaryServiceI zcIntermediaryService;


	/**
	 * 跳转到ZcIntermediary管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcintermediary/zcIntermediary";
	}

	/**
	 * 获取ZcIntermediary数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcIntermediary zcIntermediary, PageHelper ph) {
		return zcIntermediaryService.dataGrid(zcIntermediary, ph);
	}
	/**
	 * 获取ZcIntermediary数据表格excel
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
	public void download(ZcIntermediary zcIntermediary, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcIntermediary,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcIntermediary页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcIntermediary zcIntermediary = new ZcIntermediary();
		zcIntermediary.setId(jb.absx.UUID.uuid());
		return "/zcintermediary/zcIntermediaryAdd";
	}

	/**
	 * 添加ZcIntermediary
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcIntermediary zcIntermediary) {
		Json j = new Json();		
		zcIntermediaryService.add(zcIntermediary);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcIntermediary查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcIntermediary zcIntermediary = zcIntermediaryService.get(id);
		request.setAttribute("zcIntermediary", zcIntermediary);
		return "/zcintermediary/zcIntermediaryView";
	}

	/**
	 * 跳转到ZcIntermediary修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcIntermediary zcIntermediary = zcIntermediaryService.get(id);
		request.setAttribute("zcIntermediary", zcIntermediary);
		return "/zcintermediary/zcIntermediaryEdit";
	}

	/**
	 * 修改ZcIntermediary
	 * 
	 * @param zcIntermediary
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcIntermediary zcIntermediary) {
		Json j = new Json();		
		zcIntermediaryService.edit(zcIntermediary);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcIntermediary
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcIntermediaryService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
