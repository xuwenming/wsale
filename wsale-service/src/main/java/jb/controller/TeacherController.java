package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.Teacher;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.TeacherServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * Teacher管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/teacherController")
public class TeacherController extends BaseController {

	@Autowired
	private TeacherServiceI teacherService;


	/**
	 * 跳转到Teacher管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/teacher/teacher";
	}

	/**
	 * 获取Teacher数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(Teacher teacher, PageHelper ph) {
		return teacherService.dataGrid(teacher, ph);
	}
	/**
	 * 获取Teacher数据表格excel
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
	public void download(Teacher teacher, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(teacher,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加Teacher页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		Teacher teacher = new Teacher();
		teacher.setId(jb.absx.UUID.uuid());
		return "/teacher/teacherAdd";
	}

	/**
	 * 添加Teacher
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Teacher teacher) {
		Json j = new Json();		
		teacherService.add(teacher);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到Teacher查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		Teacher teacher = teacherService.get(id);
		request.setAttribute("teacher", teacher);
		return "/teacher/teacherView";
	}

	/**
	 * 跳转到Teacher修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		Teacher teacher = teacherService.get(id);
		request.setAttribute("teacher", teacher);
		return "/teacher/teacherEdit";
	}

	/**
	 * 修改Teacher
	 * 
	 * @param teacher
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Teacher teacher) {
		Json j = new Json();		
		teacherService.edit(teacher);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除Teacher
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		teacherService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
