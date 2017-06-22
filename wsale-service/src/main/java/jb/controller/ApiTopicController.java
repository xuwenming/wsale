package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcRewardServiceI;
import jb.service.ZcTopicServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.TopicCommon;
import jb.util.EnumConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiTopic")
public class ApiTopicController extends BaseController {

	@Autowired
	private ZcTopicServiceI zcTopicService;

	@Autowired
	private TopicCommon topicCommon;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcRewardServiceI zcRewardService;

	/**
	 * 跳转专题列表
	 * @return
	 */
	@RequestMapping("/topic")
	public String topic(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("sessionInfo", s);
		return "/wsale/topic/topic_list";
	}

	/**
	 * 获取专题列表
	 * @return
	 */
	@RequestMapping("/topicList")
	@ResponseBody
	public Json topicList(PageHelper ph, ZcTopic topic) {
		Json j = new Json();
		try{
			if(F.empty(ph.getSort()))
				ph.setSort("seq desc, t.addtime");
			if(F.empty(ph.getOrder()))
				ph.setOrder("desc");

			j.setObj(topicCommon.dataGrid(ph, topic));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 跳转专题详情
	 * @return
	 */
	@RequestMapping("/topicDetail")
	public String topicDetail(String id, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcTopic topic = zcTopicService.addReadAndDetail(id);
		topic.setUser(userService.get(topic.getAddUserId(), s.getId()));

		// 打赏记录
		ZcReward reward = new ZcReward();
		reward.setObjectType(EnumConstants.OBJECT_TYPE.TOPIC.getCode());
		reward.setObjectId(id);
		reward.setPayStatus("PS02");
		List<ZcReward> rewards = zcRewardService.query(reward);
		if(!CollectionUtils.isEmpty(rewards)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcReward r : rewards) {
				completionService.submit(new Task<ZcReward, User>(new CacheKey("user", r.getUserId()), r) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getUserId());
						return user;
					}

					protected void set(ZcReward d, User v) {
						if (v != null)
							d.setUser(v);
					}

				});
			}
			completionService.sync();
		}

		request.setAttribute("topic", topic);
		request.setAttribute("rewards", rewards);
		return "/wsale/topic/topic_detail";
	}

}
