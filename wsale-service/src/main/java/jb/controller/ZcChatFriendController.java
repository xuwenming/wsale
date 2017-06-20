package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcChatFriend;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcChatFriendServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcChatFriend管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcChatFriendController")
public class ZcChatFriendController extends BaseController {

	@Autowired
	private ZcChatFriendServiceI zcChatFriendService;


	/**
	 * 跳转到ZcChatFriend管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcchatfriend/zcChatFriend";
	}

	/**
	 * 获取ZcChatFriend数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcChatFriend zcChatFriend, PageHelper ph) {
		return zcChatFriendService.dataGrid(zcChatFriend, ph);
	}
	/**
	 * 获取ZcChatFriend数据表格excel
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
	public void download(ZcChatFriend zcChatFriend, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcChatFriend,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcChatFriend页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcChatFriend zcChatFriend = new ZcChatFriend();
		zcChatFriend.setId(jb.absx.UUID.uuid());
		return "/zcchatfriend/zcChatFriendAdd";
	}

	/**
	 * 添加ZcChatFriend
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcChatFriend zcChatFriend) {
		Json j = new Json();		
		zcChatFriendService.add(zcChatFriend);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcChatFriend查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcChatFriend zcChatFriend = zcChatFriendService.get(id);
		request.setAttribute("zcChatFriend", zcChatFriend);
		return "/zcchatfriend/zcChatFriendView";
	}

	/**
	 * 跳转到ZcChatFriend修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcChatFriend zcChatFriend = zcChatFriendService.get(id);
		request.setAttribute("zcChatFriend", zcChatFriend);
		return "/zcchatfriend/zcChatFriendEdit";
	}

	/**
	 * 修改ZcChatFriend
	 * 
	 * @param zcChatFriend
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcChatFriend zcChatFriend) {
		Json j = new Json();		
		zcChatFriendService.edit(zcChatFriend);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcChatFriend
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcChatFriendService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
