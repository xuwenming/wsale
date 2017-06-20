package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.ZcProtectionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * ZcProtection管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcProtectionController")
public class ZcProtectionController extends BaseController {

	@Autowired
	private ZcProtectionServiceI zcProtectionService;


	/**
	 * 跳转到ZcProtection管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcprotection/zcProtection";
	}

	/**
	 * 获取ZcProtection数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcProtection zcProtection, PageHelper ph) {
		return zcProtectionService.dataGrid(zcProtection, ph);
	}
	@RequestMapping("/dataGridByUser")
	@ResponseBody
	public DataGrid dataGridByUser(ZcProtection zcProtection, PageHelper ph) {
		return dataGrid(zcProtection, ph);
	}
	/**
	 * 获取ZcProtection数据表格excel
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
	public void download(ZcProtection zcProtection, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcProtection,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcProtection页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcProtection zcProtection = new ZcProtection();
		zcProtection.setId(jb.absx.UUID.uuid());
		return "/zcprotection/zcProtectionAdd";
	}

	/**
	 * 添加ZcProtection
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcProtection zcProtection) {
		Json j = new Json();		
		zcProtectionService.add(zcProtection);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcProtection查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcProtection zcProtection = zcProtectionService.get(id);
		request.setAttribute("zcProtection", zcProtection);
		return "/zcprotection/zcProtectionView";
	}

	/**
	 * 跳转到ZcProtection修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcProtection zcProtection = zcProtectionService.get(id);
		request.setAttribute("zcProtection", zcProtection);
		return "/zcprotection/zcProtectionEdit";
	}

	/**
	 * 修改ZcProtection
	 * 
	 * @param zcProtection
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcProtection zcProtection) {
		Json j = new Json();		
		zcProtectionService.edit(zcProtection);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcProtection
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcProtectionService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
