package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcNoticeServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
import jb.util.ImageUtils;
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
import java.util.Date;
import java.util.List;

/**
 * ZcNotice管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcNoticeController")
public class ZcNoticeController extends BaseController {

	@Autowired
	private ZcNoticeServiceI zcNoticeService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 跳转到ZcNotice管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcnotice/zcNotice";
	}

	/**
	 * 获取ZcNotice数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcNotice zcNotice, PageHelper ph) {
		DataGrid dataGrid = zcNoticeService.dataGrid(zcNotice, ph);
		List<ZcNotice> list = (List<ZcNotice>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcNotice notice : list) {
				completionService.submit(new Task<ZcNotice, User>(new CacheKey("user", notice.getAddUserId()), notice) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getAddUserId());
						return user;
					}

					protected void set(ZcNotice d, User v) {
						if (v != null)
							d.setAddUserName(v.getNickname());
					}
				});
			}
			completionService.sync();
		}

		return dataGrid;
	}
	/**
	 * 获取ZcNotice数据表格excel
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
	public void download(ZcNotice zcNotice, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcNotice,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcNotice页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcNotice zcNotice = new ZcNotice();
		zcNotice.setId(jb.absx.UUID.uuid());
		return "/zcnotice/zcNoticeAdd";
	}

	/**
	 * 添加ZcNotice
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcNotice zcNotice, HttpServletRequest request) {
		Json j = new Json();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		zcNotice.setContent(ImageUtils.replaceHtmlTag(zcNotice.getContent(), "img", "src", "src=\"", "\"", realPath));
		zcNotice.setAddUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcNoticeService.add(zcNotice);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcNotice查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcNotice zcNotice = zcNoticeService.get(id);
		if(!F.empty(zcNotice.getAddUserId())) {
			User user = userService.getByZc(zcNotice.getAddUserId());
			zcNotice.setAddUserName(user.getNickname());
		}
		if(!F.empty(zcNotice.getUpdateUserId())) {
			User user = userService.getByZc(zcNotice.getUpdateUserId());
			zcNotice.setUpdateUserName(user.getNickname());
		}
		request.setAttribute("zcNotice", zcNotice);
		return "/zcnotice/zcNoticeView";
	}

	/**
	 * 跳转到ZcNotice修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcNotice zcNotice = zcNoticeService.get(id);
		request.setAttribute("zcNotice", zcNotice);
		return "/zcnotice/zcNoticeEdit";
	}

	/**
	 * 修改ZcNotice
	 * 
	 * @param zcNotice
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcNotice zcNotice, HttpServletRequest request) {
		Json j = new Json();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		zcNotice.setContent(ImageUtils.replaceHtmlTag(zcNotice.getContent(), "img", "src", "src=\"", "\"", realPath));
		zcNotice.setUpdateUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcNotice.setUpdatetime(new Date());

		zcNoticeService.edit(zcNotice);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcNotice
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcNoticeService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
