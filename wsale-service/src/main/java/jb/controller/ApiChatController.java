package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.util.DateUtil;
import jb.util.EnumConstants;
import jb.util.PathUtil;
import jb.util.Util;
import jb.util.easemob.HuanxinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;

/**
 * 消息中心
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiChat")
public class ApiChatController extends BaseController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcChatMsgServiceI zcChatMsgService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Autowired
	private ZcChatFriendServiceI zcChatFriendService;

	/**
	 * 跳转至消息中心-聊天历史列表
	 * @return
	 */
	@RequestMapping("/chat_list")
	public String chat_list(HttpServletRequest request) {
		request.setAttribute("own", getSessionInfo(request));
		return "/wsale/chat/chat_list";
	}

	/**
	 * 获取聊天列表
	 * @return
	 */
	@RequestMapping("/getChatList")
	@ResponseBody
	public Json getChatList(HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);

			ZcChatFriend friend = new ZcChatFriend();
			friend.setUserId(s.getId());
			friend.setIsDeleted(false);
			List<ZcChatFriend> list = zcChatFriendService.query(friend);
			if(CollectionUtils.isNotEmpty(list)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcChatFriend cf : list) {
					completionService.submit(new Task<ZcChatFriend, Integer>(cf) {
						@Override
						public Integer call() throws Exception {
							ZcChatMsg q = new ZcChatMsg();
							q.setFromUserId(getD().getFriendUserId());
							q.setToUserId(getD().getUserId());
							q.setUnread(true);
							return zcChatMsgService.count(q);
						}

						protected void set(ZcChatFriend d, Integer v) {
							d.setUnreadCount(v);
							Date lastTime = d.getLastTime();
							Calendar c = Calendar.getInstance();
							int nowDay = c.get(Calendar.DAY_OF_MONTH);
							c.setTime(lastTime);
							if(nowDay == c.get(Calendar.DAY_OF_MONTH)) {
								d.setLastTimeStr(DateUtil.format(lastTime, "HH:mm"));
							} else {
								d.setLastTimeStr(DateUtil.format(lastTime, "yyyy/MM/dd"));
							}
						}
					});

					completionService.submit(new Task<ZcChatFriend, User>(cf) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getFriendUserId());
							return user;
						}

						protected void set(ZcChatFriend d, User v) {
							if(v != null)
								d.setFriendUser(v);
						}

					});
				}
				completionService.sync();
			}
			j.setObj(list);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 删除聊天列表好友
	 * @return
	 */
	@RequestMapping("/delFriend")
	@ResponseBody
	public Json delFriend(String id, HttpServletRequest request) {
		Json j = new Json();
		try{
			zcChatFriendService.delete(id);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 跳转至消息中心-聊天页
	 * @return subscribe=true:检查首次订阅是否发送欢迎语句
	 */
	@RequestMapping("/chat")
	public String chat(String toUserId, String productId, String bbsId, boolean subscribe, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		User friendUser = userService.getByZc(toUserId);

		ZcChatFriend friend = new ZcChatFriend();
		friend.setUserId(s.getId());
		friend.setFriendUserId(toUserId);
		friend.setIsBoth(true);
		List<ZcChatFriend> exists = zcChatFriendService.query(friend);
		if(CollectionUtils.isEmpty(exists)) {
			if(!F.empty(toUserId) && !toUserId.equals(s.getId())) {
				HuanxinUtil.addFriend(s.getId(), toUserId);
			}
			if(subscribe) {
				ZcChatMsg welcome = new ZcChatMsg();
				welcome.setFromUserId(toUserId);
				welcome.setToUserId(s.getId());
				welcome.setContent("欢迎来到" + friendUser.getNickname() + "的臻藏！");
				welcome.setMtype(EnumConstants.MSG_TYPE.TEXT.getCode());
				welcome.setUnread(false);
				zcChatMsgService.add(welcome);

				friend.setLastContent(welcome.getContent());
				friend.setLastTime(welcome.getAddtime());
				friend.setIsDeleted(false);
				zcChatFriendService.add(friend);
			}
		} else {
			zcChatMsgService.updateReaded(toUserId, s.getId()); // 把toUserId给我发的消息设置为已读

			/*if(exist.getIsDeleted()) {
				friend = new ZcChatFriend();
				friend.setIsDeleted(false);
				zcChatFriendService.edit(friend);
			}*/
		}


		ZcProduct product = null;
		//
		if(!F.empty(productId)) {
			product = zcProductService.get(productId, null);
		}
		ZcForumBbs bbs = null;
		if(!F.empty(bbsId)) {
			bbs = zcForumBbsService.get(bbsId);
			ZcFile file = new ZcFile();
			file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
			file.setObjectId(bbsId);
			file.setFileType("FT01");
			ZcFile f = zcFileService.get(file);
			if(f != null) bbs.setIcon(f.getFileHandleUrl());
		}

		request.setAttribute("friend", friendUser);
		request.setAttribute("own", s);
		request.setAttribute("product", product);
		request.setAttribute("bbs", bbs);

		return "/wsale/chat/chat";
	}

	/**
	 * 获取聊天消息
	 * @return
	 */
	@RequestMapping("/getMessage")
	@ResponseBody
	public Json getMessage(ZcChatMsg msg) {
		Json j = new Json();
		try{
			msg = zcChatMsgService.get(msg);
			if(msg == null) {
				j.fail();
				return j;
			}
			msg.setUser(userService.getByZc(msg.getFromUserId()));

			if(EnumConstants.MSG_TYPE.PRODUCT.getCode().equals(msg.getMtype())) {
				ZcProduct product = zcProductService.get(msg.getContent(), null);
				msg.setProduct(product);
			}

			if(EnumConstants.MSG_TYPE.BBS.getCode().equals(msg.getMtype())) {
				ZcForumBbs bbs = zcForumBbsService.get(msg.getContent());
				if(bbs != null) {
					ZcFile file = new ZcFile();
					file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
					file.setObjectId(bbs.getId());
					file.setFileType("FT01");
					ZcFile f = zcFileService.get(file);
					if(f != null) bbs.setIcon(f.getFileHandleUrl());
				}
				msg.setBbs(bbs);
			}

			j.setObj(msg);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 新增聊天记录
	 * @return
	 */
	@RequestMapping("/addMessage")
	@ResponseBody
	public Json addMessage(ZcChatMsg msg) {
		Json j = new Json();
		try{
			zcChatMsgService.add(msg);
			msg.setUser(userService.getByZc(msg.getFromUserId()));

			// 更新好友记录
			zcChatFriendService.updateFriend(msg);
			j.setObj(msg);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 标记为已读
	 * @return
	 */
	@RequestMapping("/updateUnRead")
	@ResponseBody
	public Json updateUnRead(String id) {
		Json j = new Json();
		try{
			ZcChatMsg msg = new ZcChatMsg();
			msg.setId(id);
			msg.setUnread(false);
			zcChatMsgService.edit(msg);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 推送客服提醒消息
	 * @return
	 */
	@RequestMapping("/pushMessage")
	@ResponseBody
	public Json pushMessage(ZcChatMsg msg, HttpServletRequest request) {
		Json j = new Json();
		try{
			// 5秒后推送
			Thread.currentThread().sleep(5000);
			ZcChatMsg q = new ZcChatMsg();
			q.setFromUserId(msg.getFromUserId());
			q.setToUserId(msg.getToUserId());
			q.setUnread(true); // 查询未读
			//msg = zcChatMsgService.get(msg);
			List<ZcChatMsg> msgs = zcChatMsgService.query(q);
			// 当前仅有一条未读消息则推送
			if(CollectionUtils.isNotEmpty(msgs) && msgs.size() == 1) {
				SessionInfo s = getSessionInfo(request);
				// 推送私信消息
				StringBuffer buffer = new StringBuffer();
				buffer.append("您有一条来自『" + s.getNickname() + "』的新私信。").append("\n");
				String content = "";
				if(EnumConstants.MSG_TYPE.TEXT.getCode().equals(msg.getMtype())) {
					content = Util.replaceFace(msg.getContent(), 10);
				} else if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(msg.getMtype())){
					content = "[图片]请点击下方查看";
				} else if(EnumConstants.MSG_TYPE.AUDIO.getCode().equals(msg.getMtype())){
					content = "[语音]请点击下方查看";
				} else if(EnumConstants.MSG_TYPE.PRODUCT.getCode().equals(msg.getMtype())){
					content = "[拍品]请点击下方查看";
				} else if(EnumConstants.MSG_TYPE.BBS.getCode().equals(msg.getMtype())) {
					content = "[帖子]请点击下方查看";
				}
				buffer.append("私信内容：" + content).append("\n\n");
				//buffer.append("<a href='"+ PathUtil.getUrlPath("api/apiChat/chat?toUserId=" + s.getId()) +"'>点击查看</a>");
				buffer.append("<a href='"+ PathUtil.getUrlPath("api/apiChat/chat_list") +"'>点击查看</a>");
				sendWxMessage.sendCustomMessageByUserId(msg.getToUserId(), buffer.toString());
			}
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 聊天消息分页查
	 * @return
	 */
	@RequestMapping("/messages")
	@ResponseBody
	public Json messages(PageHelper ph, String userId, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			j.setObj(messageDataGrid(ph, s.getId(), userId));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	private DataGrid messageDataGrid(PageHelper ph, String ownUserId, String friendUserId) {
		ZcChatMsg msg = new ZcChatMsg();
		ph.setSort("addtime");
		ph.setOrder("desc");
		msg.setFromUserId(ownUserId);
		msg.setToUserId(friendUserId);
		DataGrid dataGrid = zcChatMsgService.dataGridBy(msg, ph);
		List<ZcChatMsg> list = (List<ZcChatMsg>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcChatMsg chatMsg : list) {
				completionService.submit(new Task<ZcChatMsg, User>(new CacheKey("user", chatMsg.getFromUserId()), chatMsg) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getFromUserId());
						return user;
					}

					protected void set(ZcChatMsg d, User v) {
						if (v != null)
							d.setUser(v);
					}
				});
				if(EnumConstants.MSG_TYPE.PRODUCT.getCode().equals(chatMsg.getMtype()))
					completionService.submit(new Task<ZcChatMsg, ZcProduct>(new CacheKey("product", chatMsg.getContent()), chatMsg) {
						@Override
						public ZcProduct call() throws Exception {
							ZcProduct product = zcProductService.get(getD().getContent(), null);
							return product;
						}

						protected void set(ZcChatMsg d, ZcProduct v) {
							if (v != null)
								d.setProduct(v);
						}
					});
				if(EnumConstants.MSG_TYPE.BBS.getCode().equals(chatMsg.getMtype()))
					completionService.submit(new Task<ZcChatMsg, ZcForumBbs>(new CacheKey("bbs", chatMsg.getContent()), chatMsg) {
						@Override
						public ZcForumBbs call() throws Exception {
							ZcForumBbs bbs = zcForumBbsService.get(getD().getContent());
							if(bbs != null) {
								ZcFile file = new ZcFile();
								file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
								file.setObjectId(bbs.getId());
								file.setFileType("FT01");
								ZcFile f = zcFileService.get(file);
								if(f != null) bbs.setIcon(f.getFileHandleUrl());
							}
							return bbs;
						}

						protected void set(ZcChatMsg d, ZcForumBbs v) {
							if (v != null)
								d.setBbs(v);
						}
					});
			}
			completionService.sync();
		}
		return dataGrid;
	}

}
