package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcCollect;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcCollectServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcCollect管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcCollectController")
public class ZcCollectController extends BaseController {

	@Autowired
	private ZcCollectServiceI zcCollectService;


	/**
	 * 跳转到ZcCollect管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zccollect/zcCollect";
	}

	/**
	 * 获取ZcCollect数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcCollect zcCollect, PageHelper ph) {
		return zcCollectService.dataGrid(zcCollect, ph);
	}
	/**
	 * 获取ZcCollect数据表格excel
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
	public void download(ZcCollect zcCollect, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcCollect,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcCollect页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcCollect zcCollect = new ZcCollect();
		zcCollect.setId(jb.absx.UUID.uuid());
		return "/zccollect/zcCollectAdd";
	}

	/**
	 * 添加ZcCollect
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcCollect zcCollect) {
		Json j = new Json();		
		zcCollectService.add(zcCollect);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcCollect查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcCollect zcCollect = zcCollectService.get(id);
		request.setAttribute("zcCollect", zcCollect);
		return "/zccollect/zcCollectView";
	}

	/**
	 * 跳转到ZcCollect修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcCollect zcCollect = zcCollectService.get(id);
		request.setAttribute("zcCollect", zcCollect);
		return "/zccollect/zcCollectEdit";
	}

	/**
	 * 修改ZcCollect
	 * 
	 * @param zcCollect
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcCollect zcCollect) {
		Json j = new Json();		
		zcCollectService.edit(zcCollect);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcCollect
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcCollectService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
