package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcTopicComment;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcTopicCommentServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcTopicComment管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcTopicCommentController")
public class ZcTopicCommentController extends BaseController {

	@Autowired
	private ZcTopicCommentServiceI zcTopicCommentService;


	/**
	 * 跳转到ZcTopicComment管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zctopiccomment/zcTopicComment";
	}

	/**
	 * 获取ZcTopicComment数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcTopicComment zcTopicComment, PageHelper ph) {
		return zcTopicCommentService.dataGrid(zcTopicComment, ph);
	}
	/**
	 * 获取ZcTopicComment数据表格excel
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
	public void download(ZcTopicComment zcTopicComment, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcTopicComment,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcTopicComment页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcTopicComment zcTopicComment = new ZcTopicComment();
		zcTopicComment.setId(jb.absx.UUID.uuid());
		return "/zctopiccomment/zcTopicCommentAdd";
	}

	/**
	 * 添加ZcTopicComment
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcTopicComment zcTopicComment) {
		Json j = new Json();		
		zcTopicCommentService.add(zcTopicComment);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcTopicComment查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcTopicComment zcTopicComment = zcTopicCommentService.get(id);
		request.setAttribute("zcTopicComment", zcTopicComment);
		return "/zctopiccomment/zcTopicCommentView";
	}

	/**
	 * 跳转到ZcTopicComment修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcTopicComment zcTopicComment = zcTopicCommentService.get(id);
		request.setAttribute("zcTopicComment", zcTopicComment);
		return "/zctopiccomment/zcTopicCommentEdit";
	}

	/**
	 * 修改ZcTopicComment
	 * 
	 * @param zcTopicComment
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcTopicComment zcTopicComment) {
		Json j = new Json();		
		zcTopicCommentService.edit(zcTopicComment);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcTopicComment
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcTopicCommentService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
