package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcRewardServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

	@Autowired
	private UserServiceI userService;

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
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcReward zcReward, PageHelper ph, HttpSession session) {
		if(F.empty(zcReward.getByUserId())) {
			// 前端人员只查询自己相关的数据
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			User user = userService.getByZc(sessionInfo.getId());
			if("UT02".equals(user.getUtype())) {
				zcReward.setAuth(true);
				zcReward.setByUserId(user.getId());
			}
		}
		// 已支付
		zcReward.setPayStatus("PS02");
		return zcRewardService.dataGridComplex(zcReward, ph);
	}

	@RequestMapping("/dataGridByTopic")
	@ResponseBody
	public DataGrid dataGridByTopic(ZcReward zcReward, PageHelper ph) {
		return dataGridBy(zcReward, ph);
	}
	@RequestMapping("/dataGridByBbs")
	@ResponseBody
	public DataGrid dataGridByBbs(ZcReward zcReward, PageHelper ph) {
		return dataGridBy(zcReward, ph);
	}

	private DataGrid dataGridBy(ZcReward zcReward, PageHelper ph) {
		DataGrid dataGrid = zcRewardService.dataGrid(zcReward, ph);
		List<ZcReward> list = (List<ZcReward>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcReward reward : list) {
				completionService.submit(new Task<ZcReward, User>(new CacheKey("user", reward.getUserId()), reward) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getUserId());
						return user;
					}

					protected void set(ZcReward d, User v) {
						if (v != null)
							d.setUserName(v.getNickname());
					}
				});
			}
			completionService.sync();
		}
		return dataGrid;
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
	public void download(ZcReward zcReward, PageHelper ph,String downloadFields,HttpServletResponse response, HttpSession session) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcReward,ph, session);
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
