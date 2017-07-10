package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.TopicCommon;
import jb.util.EnumConstants;
import jb.util.oss.OSSUtil;
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

	@Autowired
	private ZcTopicCommentServiceI zcTopicCommentService;

	@Autowired
	private ZcPraiseServiceI zcPraiseService;

	/**
	 * 跳转专题列表
	 * @return
	 */
	@RequestMapping("/topic")
	public String topic(String addUserId, boolean isHomeHot, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("sessionInfo", s);
		request.setAttribute("addUserId", addUserId);
		request.setAttribute("isHomeHot", isHomeHot);
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
		//topic.setContent(topic.getContent().replaceAll("src=\"" + OSSUtil.cdnUrl, "data-original=\"" + OSSUtil.cdnUrl));
		topic.setUser(userService.get(topic.getAddUserId(), s.getId()));

		// 是否点赞
		ZcPraise praise = new ZcPraise();
		praise.setObjectId(id);
		praise.setObjectType(EnumConstants.OBJECT_TYPE.TOPIC.getCode());
		praise.setUserId(s.getId());
		praise = zcPraiseService.get(praise);
		topic.setPraise(praise == null ? false : true);

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
		request.setAttribute("sessionInfo", s);
		return "/wsale/topic/topic_detail";
	}

	private DataGrid commentDataGrid(PageHelper ph, ZcTopicComment comment, final String userId) {
		ph.setSort("addtime");
		ph.setOrder("desc");
		comment.setIsDeleted(false);
		DataGrid dataGrid = zcTopicCommentService.dataGrid(comment, ph);
		List<ZcTopicComment> list = (List<ZcTopicComment>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcTopicComment c : list) {
				completionService.submit(new Task<ZcTopicComment, User>(new CacheKey("user", c.getUserId()), c) {
					@Override
					public User call() throws Exception {
						User user = userService.get(getD().getUserId(), userId);
						return user;
					}

					protected void set(ZcTopicComment d, User v) {
						if (v != null)
							d.setUser(v);
					}
				});
				completionService.submit(new Task<ZcTopicComment, ZcTopicComment>(c) {
					@Override
					public ZcTopicComment call() throws Exception {
						ZcTopicComment reply = new ZcTopicComment();
						reply.setPid(getD().getId());
						return zcTopicCommentService.get(reply);
					}

					protected void set(ZcTopicComment d, ZcTopicComment v) {
						if (v != null)
							d.setReplyComment(v);
					}
				});
			}
			completionService.sync();
		}
		return dataGrid;
	}

	/**
	 * 帖子评论分页查
	 * @return
	 */
	@RequestMapping("/topicComments")
	@ResponseBody
	public Json topicComments(PageHelper ph, ZcTopicComment comment, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			j.setObj(commentDataGrid(ph, comment, s.getId()));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 添加留言
	 * @return
	 */
	@RequestMapping("/addComment")
	@ResponseBody
	public Json addComment(ZcTopicComment comment, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			comment.setUserId(s.getId());
			comment.setIsDeleted(false);
			zcTopicCommentService.add(comment);

			comment.setUser(userService.get(s.getId(), null));

			zcTopicService.updateCount(comment.getTopicId(), 1, "topic_comment");

			j.setObj(comment);
			j.success();
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.fail();
		}
		return j;
	}

	/**
	 * 删除留言
	 * @return
	 */
	@RequestMapping("/delComment")
	@ResponseBody
	public Json delComment(ZcTopicComment comment, HttpServletRequest request) {
		Json j = new Json();
		try {
			ZcTopicComment c = zcTopicCommentService.get(comment.getId());
			zcTopicCommentService.delete(comment.getId());
			zcTopicService.updateCount(c.getTopicId(), -1, "topic_comment");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}


}
