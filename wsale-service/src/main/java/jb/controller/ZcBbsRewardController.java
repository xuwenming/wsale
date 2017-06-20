package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcBbsRewardServiceI;
import jb.service.ZcForumBbsServiceI;
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
 * ZcBbsReward管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcBbsRewardController")
public class ZcBbsRewardController extends BaseController {

	@Autowired
	private ZcBbsRewardServiceI zcBbsRewardService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 跳转到ZcBbsReward管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcbbsreward/zcBbsReward";
	}

	/**
	 * 获取ZcBbsReward数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcBbsReward zcBbsReward, PageHelper ph, HttpSession session) {
		if(F.empty(zcBbsReward.getByUserId())) {
			// 前端人员只查询自己相关的数据
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			User user = userService.getByZc(sessionInfo.getId());
			if("UT02".equals(user.getUtype())) {
				zcBbsReward.setAuth(true);
				zcBbsReward.setByUserId(user.getId());
			}
		}

		// 已支付
		zcBbsReward.setPayStatus("PS02");
		DataGrid dataGrid = zcBbsRewardService.dataGrid(zcBbsReward, ph);
		List<ZcBbsReward> list = (List<ZcBbsReward>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcBbsReward bbsReward : list) {
				// 打赏帖子标题和被打赏人
				if (!F.empty(bbsReward.getBbsId())) {
					completionService.submit(new Task<ZcBbsReward, ZcForumBbs>(new CacheKey("bbs", bbsReward.getBbsId()), bbsReward) {
						@Override
						public ZcForumBbs call() throws Exception {
							ZcForumBbs bbs = zcForumBbsService.get(getD().getBbsId());
							bbs.setAddUserName(userService.get(bbs.getAddUserId()).getNickname());
							return bbs;
						}

						protected void set(ZcBbsReward d, ZcForumBbs v) {
							if (v != null) {
								d.setBbsTitle(v.getBbsTitle());
								d.setByUserName(v.getAddUserName());
							}

						}

					});
				}

				// 打赏人
				completionService.submit(new Task<ZcBbsReward, User>(new CacheKey("user", bbsReward.getUserId()), bbsReward) {
					@Override
					public User call() throws Exception {
						User u = userService.get(getD().getUserId());
						return u;
					}

					protected void set(ZcBbsReward d, User v) {
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
	public DataGrid dataGridByBbs(ZcBbsReward zcBbsReward, PageHelper ph, HttpSession session) {
		return dataGrid(zcBbsReward, ph, session);
	}
	/**
	 * 获取ZcBbsReward数据表格excel
	 * 
	 * @param
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(ZcBbsReward zcBbsReward, PageHelper ph,String downloadFields,HttpServletResponse response, HttpSession session) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcBbsReward,ph,session);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcBbsReward页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcBbsReward zcBbsReward = new ZcBbsReward();
		zcBbsReward.setId(jb.absx.UUID.uuid());
		return "/zcbbsreward/zcBbsRewardAdd";
	}

	/**
	 * 添加ZcBbsReward
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcBbsReward zcBbsReward) {
		Json j = new Json();		
		zcBbsRewardService.add(zcBbsReward);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcBbsReward查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcBbsReward zcBbsReward = zcBbsRewardService.get(id);
		request.setAttribute("zcBbsReward", zcBbsReward);
		return "/zcbbsreward/zcBbsRewardView";
	}

	/**
	 * 跳转到ZcBbsReward修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcBbsReward zcBbsReward = zcBbsRewardService.get(id);
		request.setAttribute("zcBbsReward", zcBbsReward);
		return "/zcbbsreward/zcBbsRewardEdit";
	}

	/**
	 * 修改ZcBbsReward
	 * 
	 * @param zcBbsReward
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcBbsReward zcBbsReward) {
		Json j = new Json();		
		zcBbsRewardService.edit(zcBbsReward);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcBbsReward
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcBbsRewardService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
