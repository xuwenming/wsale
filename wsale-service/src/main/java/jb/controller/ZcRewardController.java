package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.ZcReward;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.ZcRewardServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ZcReward管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcRewardController")
public class ZcRewardController extends BaseController {

	@Autowired
	private ZcRewardServiceI zcRewardService;


	/**
	 * 跳转到ZcReward管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcreward/zcReward";
	}

	/**
	 * 获取ZcReward数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcReward zcReward, PageHelper ph) {
		return zcRewardService.dataGrid(zcReward, ph);
	}
	/**
	 * 获取ZcReward数据表格excel
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
	public void download(ZcReward zcReward, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcReward,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcReward页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcReward zcReward = new ZcReward();
		zcReward.setId(jb.absx.UUID.uuid());
		return "/zcreward/zcRewardAdd";
	}

	/**
	 * 添加ZcReward
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcReward zcReward) {
		Json j = new Json();		
		zcRewardService.add(zcReward);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcReward查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcReward zcReward = zcRewardService.get(id);
		request.setAttribute("zcReward", zcReward);
		return "/zcreward/zcRewardView";
	}

	/**
	 * 跳转到ZcReward修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcReward zcReward = zcRewardService.get(id);
		request.setAttribute("zcReward", zcReward);
		return "/zcreward/zcRewardEdit";
	}

	/**
	 * 修改ZcReward
	 * 
	 * @param zcReward
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcReward zcReward) {
		Json j = new Json();		
		zcRewardService.edit(zcReward);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcReward
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcRewardService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
