package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcBbsCommentServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcForumBbsServiceI;
import jb.service.impl.CompletionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * ZcBbsComment管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcBbsCommentController")
public class ZcBbsCommentController extends BaseController {

	@Autowired
	private ZcBbsCommentServiceI zcBbsCommentService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	/**
	 * 跳转到ZcBbsComment管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcbbscomment/zcBbsComment";
	}

	/**
	 * 获取ZcBbsComment数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcBbsComment zcBbsComment, PageHelper ph) {
		zcBbsComment.setIsDeleted(false);
		DataGrid dataGrid =zcBbsCommentService.dataGrid(zcBbsComment, ph);
		List<ZcBbsComment> list = (List<ZcBbsComment>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcBbsComment bbsComment : list) {
				if (!F.empty(bbsComment.getBbsId())) {
					completionService.submit(new Task<ZcBbsComment, ZcForumBbs>(new CacheKey("bbs", bbsComment.getBbsId()), bbsComment) {
						@Override
						public ZcForumBbs call() throws Exception {
							ZcForumBbs c = zcForumBbsService.get(getD().getBbsId());
							ZcCategory category = zcCategoryService.get(c.getCategoryId());
							ZcCategory pc = null;
							if(!F.empty(category.getPid())) {
								pc = zcCategoryService.get(category.getPid());
							}
							c.setCategoryName((pc != null ? pc.getName() + " - " : "") + category.getName());
							return c;
						}

						protected void set(ZcBbsComment d, ZcForumBbs v) {
							if (v != null) {
								d.setBbsTitle(v.getBbsTitle());
								d.setCategoryName(v.getCategoryName());
							}
						}
					});
				}
				completionService.submit(new Task<ZcBbsComment, User>(new CacheKey("user", bbsComment.getUserId()), bbsComment) {
					@Override
					public User call() throws Exception {
						User u = userService.get(getD().getUserId());
						return u;
					}

					protected void set(ZcBbsComment d, User v) {
						if (v != null)
							d.setUserName(v.getNickname());

					}

				});

			}
			completionService.sync();
		}
		return dataGrid;
	}
	@RequestMapping("/dataGridByBbs")
	@ResponseBody
	public DataGrid dataGridByBbs(ZcBbsComment zcBbsComment, PageHelper ph) {
		return dataGrid(zcBbsComment, ph);
	}
	/**
	 * 获取ZcBbsComment数据表格excel
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
	public void download(ZcBbsComment zcBbsComment, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcBbsComment,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcBbsComment页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcBbsComment zcBbsComment = new ZcBbsComment();
		zcBbsComment.setId(jb.absx.UUID.uuid());
		return "/zcbbscomment/zcBbsCommentAdd";
	}

	/**
	 * 添加ZcBbsComment
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcBbsComment zcBbsComment) {
		Json j = new Json();		
		zcBbsCommentService.add(zcBbsComment);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcBbsComment查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcBbsComment zcBbsComment = zcBbsCommentService.get(id);
		request.setAttribute("zcBbsComment", zcBbsComment);
		return "/zcbbscomment/zcBbsCommentView";
	}

	/**
	 * 跳转到ZcBbsComment修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcBbsComment zcBbsComment = zcBbsCommentService.get(id);
		request.setAttribute("zcBbsComment", zcBbsComment);
		return "/zcbbscomment/zcBbsCommentEdit";
	}

	/**
	 * 修改ZcBbsComment
	 * 
	 * @param zcBbsComment
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcBbsComment zcBbsComment) {
		Json j = new Json();		
		zcBbsCommentService.edit(zcBbsComment);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcBbsComment
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		//zcBbsCommentService.delete(id);
		ZcBbsComment comment = new ZcBbsComment();
		comment.setId(id);
		zcBbsCommentService.delete(comment);
		ZcBbsComment c = zcBbsCommentService.get(comment.getId());
		zcForumBbsService.updateCount(c.getBbsId(), -1, "bbs_comment");
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
