package jb.controller;

import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.pageModel.ZcReward;
import jb.service.ZcRewardServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 打赏
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiReward")
public class ApiRewardController extends BaseController {

	@Autowired
	private ZcRewardServiceI zcRewardService;

	/**
	 * 打赏
	 * @return
	 */
	@RequestMapping("/reward")
	@ResponseBody
	public Json reward(ZcReward reward, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			reward.setUserId(s.getId());
			reward.setPayStatus("PS01");
			zcRewardService.add(reward);
			j.setObj(reward.getId());
			j.success();
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.fail();
		}
		return j;
	}

}
