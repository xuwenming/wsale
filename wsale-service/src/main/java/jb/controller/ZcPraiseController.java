package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcPraise;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcPraiseServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcPraise管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcPraiseController")
public class ZcPraiseController extends BaseController {

	@Autowired
	private ZcPraiseServiceI zcPraiseService;


	/**
	 * 跳转到ZcPraise管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcpraise/zcPraise";
	}

	/**
	 * 获取ZcPraise数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcPraise zcPraise, PageHelper ph) {
		return zcPraiseService.dataGrid(zcPraise, ph);
	}
	/**
	 * 获取ZcPraise数据表格excel
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
	public void download(ZcPraise zcPraise, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcPraise,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcPraise页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcPraise zcPraise = new ZcPraise();
		zcPraise.setId(jb.absx.UUID.uuid());
		return "/zcpraise/zcPraiseAdd";
	}

	/**
	 * 添加ZcPraise
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcPraise zcPraise) {
		Json j = new Json();		
		zcPraiseService.add(zcPraise);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcPraise查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcPraise zcPraise = zcPraiseService.get(id);
		request.setAttribute("zcPraise", zcPraise);
		return "/zcpraise/zcPraiseView";
	}

	/**
	 * 跳转到ZcPraise修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcPraise zcPraise = zcPraiseService.get(id);
		request.setAttribute("zcPraise", zcPraise);
		return "/zcpraise/zcPraiseEdit";
	}

	/**
	 * 修改ZcPraise
	 * 
	 * @param zcPraise
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcPraise zcPraise) {
		Json j = new Json();		
		zcPraiseService.edit(zcPraise);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcPraise
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcPraiseService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
