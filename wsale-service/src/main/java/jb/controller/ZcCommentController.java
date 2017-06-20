package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.ZcCommentServiceI;

import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

/**
 * ZcComment管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcCommentController")
public class ZcCommentController extends BaseController {

	@Autowired
	private ZcCommentServiceI zcCommentService;


	@Autowired
	private ZcProductServiceI zcProductService;



	/**
	 * 跳转到ZcComment管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zccomment/zcComment";
	}

	/**
	 * 获取ZcComment数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcComment zcComment, PageHelper ph) {
		DataGrid dataGrid=zcCommentService.dataGrid(zcComment, ph);
		List<ZcComment> list = (List<ZcComment>) dataGrid.getRows();

		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcComment comment : list) {
				if (!F.empty(comment.getProductId())) {
					completionService.submit(new Task<ZcComment, String>(comment) {
						@Override
						public String call() throws Exception {
							ZcProduct c = zcProductService.get(getD().getProductId());
							String pName=c.getPno();
							return pName;
						}

						protected void set(ZcComment d, String v) {
							if (v != null)
								d.setPname(v);
						}

					});
				}


			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcComment数据表格excel
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
	public void download(ZcComment zcComment, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcComment,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcComment页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcComment zcComment = new ZcComment();
		zcComment.setId(jb.absx.UUID.uuid());
		return "/zccomment/zcCommentAdd";
	}

	/**
	 * 添加ZcComment
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcComment zcComment) {
		Json j = new Json();		
		zcCommentService.add(zcComment);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcComment查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcComment zcComment = zcCommentService.get(id);
		request.setAttribute("zcComment", zcComment);
		return "/zccomment/zcCommentView";
	}

	/**
	 * 跳转到ZcComment修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcComment zcComment = zcCommentService.get(id);
		request.setAttribute("zcComment", zcComment);
		return "/zccomment/zcCommentEdit";
	}

	/**
	 * 修改ZcComment
	 * 
	 * @param zcComment
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcComment zcComment) {
		Json j = new Json();		
		zcCommentService.edit(zcComment);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcComment
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcCommentService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
