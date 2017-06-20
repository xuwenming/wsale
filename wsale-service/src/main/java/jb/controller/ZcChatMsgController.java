package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcChatMsgServiceI;
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
 * ZcChatMsg管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcChatMsgController")
public class ZcChatMsgController extends BaseController {

	@Autowired
	private ZcChatMsgServiceI zcChatMsgService;

	@Autowired
	private UserServiceI userService;


	/**
	 * 跳转到ZcChatMsg管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcchatmsg/zcChatMsg";
	}

	/**
	 * 获取ZcChatMsg数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcChatMsg zcChatMsg, PageHelper ph) {
		DataGrid dataGrid = zcChatMsgService.dataGrid(zcChatMsg, ph);
		List<ZcChatMsg> list = (List<ZcChatMsg>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcChatMsg msg : list) {
				// 发送人
				if (!F.empty(msg.getFromUserId())) {
					completionService.submit(new Task<ZcChatMsg, User>(new CacheKey("user", msg.getFromUserId()), msg) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getFromUserId());
							return user;
						}

						protected void set(ZcChatMsg d, User v) {
							if (v != null)
								d.setFromUserName(v.getNickname());

						}

					});
				}

				// 接收人
				if (!F.empty(msg.getToUserId())) {
					completionService.submit(new Task<ZcChatMsg, User>(new CacheKey("user", msg.getToUserId()), msg) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getToUserId());
							return user;
						}

						protected void set(ZcChatMsg d, User v) {
							if (v != null)
								d.setToUserName(v.getNickname());
						}

					});
				}

			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcChatMsg数据表格excel
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
	public void download(ZcChatMsg zcChatMsg, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcChatMsg,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcChatMsg页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcChatMsg zcChatMsg = new ZcChatMsg();
		zcChatMsg.setId(jb.absx.UUID.uuid());
		return "/zcchatmsg/zcChatMsgAdd";
	}

	/**
	 * 添加ZcChatMsg
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcChatMsg zcChatMsg) {
		Json j = new Json();		
		zcChatMsgService.add(zcChatMsg);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcChatMsg查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcChatMsg zcChatMsg = zcChatMsgService.get(id);
		request.setAttribute("zcChatMsg", zcChatMsg);
		return "/zcchatmsg/zcChatMsgView";
	}

	/**
	 * 跳转到ZcChatMsg修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcChatMsg zcChatMsg = zcChatMsgService.get(id);
		request.setAttribute("zcChatMsg", zcChatMsg);
		return "/zcchatmsg/zcChatMsgEdit";
	}

	/**
	 * 修改ZcChatMsg
	 * 
	 * @param zcChatMsg
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcChatMsg zcChatMsg) {
		Json j = new Json();		
		zcChatMsgService.edit(zcChatMsg);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcChatMsg
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcChatMsgService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
