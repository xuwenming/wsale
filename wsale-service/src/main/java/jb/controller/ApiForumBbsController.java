package jb.controller;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.ForumBbsCommon;
import jb.service.impl.SendWxMessageImpl;
import jb.util.*;
import jb.util.wx.DownloadMediaUtil;
import jb.util.wx.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/bbsController")
public class ApiForumBbsController extends BaseController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Autowired
	private ZcBbsCommentServiceI zcBbsCommentService;

	@Autowired
	private ZcShareRecordServiceI zcShareRecordService;

	@Autowired
	private ZcRewardServiceI zcRewardService;

	@Autowired
	private ForumBbsCommon forumBbsCommon;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Autowired
	private ZcCollectServiceI zcCollectService;

	@Autowired
	private ZcIntermediaryServiceI zcIntermediaryService;

	/**
	 * 跳转至帖子发布页
	 * @return
	 */
	@RequestMapping("/toPublish")
	public String toPublish(String categoryId, String bbsType, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("bbsType", bbsType);
		return "/wsale/bbs/publishBbs";
	}

	/**
	 * 跳转至名家讲堂帖子发布页
	 * http://localhost:8080/api/bbsController/toPublishForumBbs?tokenId=1D96DACB84F21890ED9F4928FA8B352B&categoryId=988F9AB6C2BF4428BA3ED58F0F799385
	 * @return
	 */
	@RequestMapping("/toPublishForumBbs")
	public String toPublishForumBbs(String categoryId, String bbsType, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("bbsType", bbsType);
		return "/wsale/bbs/publishForumBbs";
	}

	/**
	 * 跳转至热门发帖页
	 * http://localhost:8080/api/bbsController/toPublishHot?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/toPublishHot")
	public String toPublishHot(String serverIds, String localIds, HttpServletRequest request) {
		List<Map<String, String>> images = new ArrayList<Map<String, String>>();
		if(!F.empty(serverIds) && !F.empty(localIds)) {
			String[] serverIdArr = serverIds.split(",");
			String[] localIdArr = localIds.split(",");
			for(int i=0; i<serverIdArr.length; i++) {
				Map<String, String> image = new HashMap<String, String>();
				image.put("serverId", serverIdArr[i]);
				image.put("localId", localIdArr[i]);
				images.add(image);
			}
		}
		request.setAttribute("images", images);

		// 获取分类
		ZcCategory c = new ZcCategory();
		c.setPid("0");
		c.setIsDeleted(false);
		List<ZcCategory> categorys = zcCategoryService.query(c);
		if(CollectionUtils.isNotEmpty(categorys)) {
			c.setPid(categorys.get(0).getId());
			List<ZcCategory> childCategorys = zcCategoryService.query(c);
			request.setAttribute("childCategorys", childCategorys);
		}
		request.setAttribute("categorys", categorys);
		return "/wsale/bbs/publishHotBbs";
	}

	/**
	 * 发布帖子
	 * http://localhost:8080/api/bbsController/publishBbs?tokenId=1D96DACB84F21890ED9F4928FA8B352B&categoryId=988F9AB6C2BF4428BA3ED58F0F799385&bbsTitle=新收的和田玉&bbsContent=新收的和田玉&mediaIds=image1,image2,image3
	 * @return
	 */
	@RequestMapping("/publishBbs")
	@ResponseBody
	public Json publishBbs(ZcForumBbs bbs, String voicePaths, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);

			ZcForumBbs q = new ZcForumBbs();
			q.setAddUserId(s.getId());
			q.setIsDeleted(false);
			q.setAddtime(new Date());
			int publishBbsNum = zcForumBbsService.query(q).size();
			int byNum = 10, nonByNum = 30;
			try {
				byNum = Integer.valueOf(Application.getString("SV200"));
				nonByNum = Integer.valueOf(Application.getString("SV201"));
			} catch (Exception e) {}
			if(("by".equals(s.getPositionId()) && publishBbsNum >= byNum)
					|| publishBbsNum >= nonByNum) {
				j.fail();
				j.setMsg("您今日发帖已全部用完，请明日再来哟！");
				return j;
			}

			bbs.setAddUserId(s.getId());
			bbs.setUpdatetime(new Date());
			zcForumBbsService.add(bbs);
			if(!F.empty(bbs.getMediaIds())) {
				String[] mediaIds = bbs.getMediaIds().split(",");
				final CompletionService completionService = CompletionFactory.initCompletion();
				final String realPath = request.getSession().getServletContext().getRealPath("/");
				int index = 0;
				for(final String mediaId : mediaIds) {
					ZcFile zcFile = new ZcFile();
					zcFile.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode()); // 对象类型：帖子
					zcFile.setObjectId(bbs.getId());
					zcFile.setFileType("FT01"); // 文件类型：图片
					zcFile.setSeq(++index);
					completionService.submit(new Task<ZcFile, Object>(zcFile){
						@Override
						public Boolean call() throws Exception {
							String filePath = DownloadMediaUtil.downloadMedia(realPath, mediaId, "BBS");
							getD().setFileOriginalUrl(filePath);
							getD().setFileHandleUrl(ImageUtils.pressImage(filePath, realPath));
							zcFileService.add(getD());
							return true;
						}
					});
				}
				completionService.sync();
			}
			if(!F.empty(voicePaths)) {
				String[] voicePathArr = voicePaths.split(",");
				Arrays.sort(voicePathArr); // 排序
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(int i=0; i<voicePathArr.length; i++) {
					String[] voices = voicePathArr[i].split("[|][|]");
					ZcFile zcFile = new ZcFile();
					zcFile.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode()); // 对象类型：帖子
					zcFile.setObjectId(bbs.getId());
					zcFile.setFileType("FT02"); // 文件类型：语音
					zcFile.setFileOriginalUrl(voices[1]);
					zcFile.setFileHandleUrl(voices[1]);
					zcFile.setDuration(Integer.valueOf(voices[2]));
					zcFile.setSeq(i);
					completionService.submit(new Task<ZcFile, Object>(zcFile){
						@Override
						public Boolean call() throws Exception {
							zcFileService.add(getD());
							return true;
						}
					});
				}
				completionService.sync();
			}
			j.setObj(bbs.getId());
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}

	/**
	 * 上传语音
	 * @return
	 */
	@RequestMapping("/uploadVoice")
	@ResponseBody
	public Json uploadVoice(String mediaId, HttpServletRequest request) {
		Json j = new Json();
		try{
			long timestamp = System.currentTimeMillis(); // 时间戳，区分先后
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String path = MP3Util.convert(DownloadMediaUtil.downloadMedia(realPath, mediaId, "BBS/Voice"), request);
			int duration = MP3Util.getDuration(path, request);
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("path", path);
			obj.put("duration", duration);
			obj.put("timestamp", timestamp);
			j.setObj(obj);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取帖子列表
	 * http://localhost:8080/api/bbsController/bbsList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10&categoryId=988F9AB6C2BF4428BA3ED58F0F799385&bbsType=BT01
	 * @return
	 */
	@RequestMapping("/bbsList")
	@ResponseBody
	public Json bbsList(PageHelper ph, ZcForumBbs bbs, HttpServletRequest request) {
		Json j = new Json();
		try{
			if(F.empty(ph.getSort()))
				ph.setSort("updatetime desc, t.addtime");
			if(F.empty(ph.getOrder()))
				ph.setOrder("desc");
			bbs.setIsDeleted(false);
			j.setObj(forumBbsCommon.dataGrid(ph, bbs));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/attedBbsList")
	@ResponseBody
	public Json attedBbsList(PageHelper ph, ZcForumBbs bbs, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(F.empty(ph.getSort()))
				ph.setSort("updatetime desc, t.addtime");
			if(F.empty(ph.getOrder()))
				ph.setOrder("desc");
			bbs.setIsDeleted(false);
			bbs.setBbsStatus("BS01");
			bbs.setAtteId(s.getId());

			j.setObj(forumBbsCommon.dataGrid(ph, bbs));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 置顶帖子-更多页
	 *
	 * @return
	 */
	@RequestMapping("/topBbs")
	public String topBbs(String categoryId, String bbsType, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("bbsType", bbsType);
		return "/wsale/bbs/topBbs";
	}

	/**
	 * 热门帖子
	 *
	 * @return
	 */
	@RequestMapping("/hotBbs")
	public String hotBbs(String bbsType, boolean isHomeHot, HttpServletRequest request) {
		request.setAttribute("bbsType", bbsType);
		request.setAttribute("isHomeHot", isHomeHot);
		return "/wsale/bbs/hotBbs";
	}

	/**
	 * 获取帖子详情
	 * http://localhost:8080/api/bbsController/bbsDetail?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=CA2387B0F76041E89626FB1348F8E431
	 * @return
	 */
	@RequestMapping("/bbsDetail")
	public String bbsDetail(ZcForumBbs bbs, boolean backCustom, boolean fromShare, HttpServletRequest request) {
		try{
			SessionInfo s = getSessionInfo(request);
			bbs = zcForumBbsService.addReadAndDetail(bbs.getId(), s.getId());
			ZcFile file = new ZcFile();
			file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
			file.setObjectId(bbs.getId());
			file.setFileType("FT01");
			bbs.setFiles(zcFileService.queryFiles(file));

			// 最后编辑人
			if(!F.empty(bbs.getLastUpdateUserId())) {
				bbs.setLastUpdateUserName(userService.getByZc(bbs.getLastUpdateUserId()).getNickname());
			}
			boolean isShare = false;
			if("BT03".equals(bbs.getBbsType())) {
				file.setFileType("FT02");
				bbs.setVoiceFiles(zcFileService.queryFiles(file));
				ZcFile audio = zcFileService.get(file);
				if(audio != null) {
					bbs.setAudioUrl(audio.getFileHandleUrl());
				}

				if(!bbs.getAddUserId().equals(s.getId())) {
					ZcShareRecord sq = new ZcShareRecord();
					sq.setBbsId(bbs.getId());
					sq.setUserId(s.getId());
					if(CollectionUtils.isNotEmpty(zcShareRecordService.query(sq))) {
						isShare = true;
					}
				}
			}
			request.setAttribute("isShare", isShare);

			// 是否收藏
			boolean isCollect = false;
			ZcCollect collect = new ZcCollect();
			collect.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
			collect.setObjectId(bbs.getId());
			collect.setUserId(s.getId());
			collect = zcCollectService.get(collect);
			if(collect != null) isCollect = true;
			request.setAttribute("isCollect", isCollect);

			User user = userService.get(bbs.getAddUserId(), s.getId());

			ZcReward reward = new ZcReward();
			reward.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
			reward.setObjectId(bbs.getId());
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

			ZcCategory category = zcCategoryService.get(bbs.getCategoryId());
			ZcCategory pc = null;
			if(!F.empty(category.getPid())) {
				pc = zcCategoryService.get(category.getPid());
			}
			request.setAttribute("title", (pc != null ? pc.getName() + "/" : "") + category.getName());

			// 帖子移动权限获取分类
			if(s.getResourceList().contains("auth_tzyd")) {
				ZcCategory c = new ZcCategory();
				c.setPid("0");
				c.setIsDeleted(false);
				List<ZcCategory> categorys = zcCategoryService.query(c);
				if(CollectionUtils.isNotEmpty(categorys)) {
					c.setPid(categorys.get(0).getId());
					List<ZcCategory> childCategorys = zcCategoryService.query(c);
					request.setAttribute("childCategorys", childCategorys);
				}
				request.setAttribute("categorys", categorys);
			}

			// 查询交易中或交易完成的中介交易
			boolean imable = true;
			ZcIntermediary im = new ZcIntermediary();
			im.setBbsId(bbs.getId());
			im.setStatus("IS02,IS04");
			if(zcIntermediaryService.query(im) != null) {
				imable = false;
			}

			request.setAttribute("rewards", rewards);
			request.setAttribute("bbs", bbs);
			request.setAttribute("user", user);
			request.setAttribute("backCustom", backCustom);
			request.setAttribute("sessionInfo", s);
			request.setAttribute("fromShare", fromShare);
			request.setAttribute("imable", imable);

			// 判断用户是否关注公众号
			request.setAttribute("subscribe", WeixinUtil.getSubscribe(s.getName()));

		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wsale/bbs/bbs_detail";
	}

	private DataGrid commentDataGrid(PageHelper ph, ZcBbsComment comment, final String userId) {
		ph.setSort("addtime");
		ph.setOrder("asc");
		comment.setIsDeleted(false);
		DataGrid dataGrid = zcBbsCommentService.dataGrid(comment, ph);
		List<ZcBbsComment> list = (List<ZcBbsComment>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcBbsComment bbsComment : list) {
				completionService.submit(new Task<ZcBbsComment, User>(new CacheKey("user", bbsComment.getUserId()), bbsComment) {
					@Override
					public User call() throws Exception {
						User user = userService.get(getD().getUserId(), userId);
						return user;
					}

					protected void set(ZcBbsComment d, User v) {
						if (v != null)
							d.setUser(v);
					}

				});

				if(!F.empty(bbsComment.getPid()))
					completionService.submit(new Task<ZcBbsComment, ZcBbsComment>(new CacheKey("comment", bbsComment.getPid()), bbsComment) {
						@Override
						public ZcBbsComment call() throws Exception {
							ZcBbsComment c = zcBbsCommentService.get(getD().getPid());
							c.setUser(userService.getByZc(c.getUserId()));
							return c;
						}

						protected void set(ZcBbsComment d, ZcBbsComment v) {
							if(v != null)
								d.setParentComment(v);
						}

					});
			}
			completionService.sync();
		}
		return dataGrid;
	}

	/**
	 * 帖子评论分页查
	 * http://localhost:8080/api/bbsController/bbsComments?tokenId=1D96DACB84F21890ED9F4928FA8B352B&bbsId=CA2387B0F76041E89626FB1348F8E431&page=1&rows=10
	 * @return
	 */
	@RequestMapping("/bbsComments")
	@ResponseBody
	public Json bbsComments(PageHelper ph, ZcBbsComment comment, HttpServletRequest request) {
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
	 * 添加论坛评论
	 * http://localhost:8080/api/bbsController/addBbsComment?tokenId=1D96DACB84F21890ED9F4928FA8B352B&bbsId=CA2387B0F76041E89626FB1348F8E431&comment=我是评论内容
	 * @return
	 */
	@RequestMapping("/addBbsComment")
	@ResponseBody
	public Json addBbsComment(ZcBbsComment comment, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			comment.setUserId(s.getId());
			comment.setIsDeleted(false);
			zcBbsCommentService.add(comment);

			// 推送帖子回复消息
			ZcForumBbs bbs = zcForumBbsService.get(comment.getBbsId());
			StringBuffer buffer = new StringBuffer();
			if(!bbs.getAddUserId().equals(s.getId())) {
				User user = userService.getByZc(bbs.getAddUserId());
				if("UT02".equals(user.getUtype())) {
					String username = "BT02".equals(bbs.getBbsType()) ? "鉴定员" : s.getNickname();
					buffer.append("『" + username + "』回复了您的主题帖\"" + bbs.getBbsTitle() + "\"").append("\n");
					String content = "";
					if(EnumConstants.MSG_TYPE.TEXT.getCode().equals(comment.getCtype())) {
						content = Util.replaceFace(comment.getComment(), 10);
					} else {
						content = "[图片]请点击下方查看";
					}
					buffer.append("回复内容：" + content).append("\n\n");
					buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + bbs.getId()) +"'>点击查看</a>");
					sendWxMessage.sendCustomMessage(user.getName(), buffer.toString());
				}
			}

			comment.setUser(userService.get(s.getId(), null));

			if(!F.empty(comment.getPid())) {
				ZcBbsComment p = zcBbsCommentService.get(comment.getPid());
				User user = userService.getByZc(p.getUserId());
				p.setUser(user);
				comment.setParentComment(p);

				// 推送回复@消息
				// 非@发帖人并且评论人不是@自己
				if(!p.getUserId().equals(bbs.getAddUserId()) && !p.getUserId().equals(s.getId()) && "UT02".equals(user.getUtype())) {
					buffer = new StringBuffer();
					buffer.append("『" + s.getNickname() + "』在主题帖\"" + bbs.getBbsTitle() + "\"中@了您。").append("\n\n");
					buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + comment.getBbsId()) +"'>点击查看</a>");
					sendWxMessage.sendCustomMessage(user.getName(), buffer.toString());
				}
			}



			// 更新帖子最后评论时间和回复数
			CompletionService completionService = CompletionFactory.initCompletion();
			completionService.submit(new Task<ZcBbsComment, Object>(comment) {
				@Override
				public Boolean call() throws Exception {
					zcForumBbsService.updateCount(getD().getBbsId(), 1, "bbs_comment");

					Calendar c = Calendar.getInstance();
					ZcForumBbs bbs = new ZcForumBbs();
					bbs.setId(getD().getBbsId());
					bbs.setLastCommentTime(c.getTime());
					bbs.setUpdatetime(c.getTime());
					zcForumBbsService.edit(bbs);
					return true;
				}
			});

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
	 * 删除评论
	 * http://localhost:8080/api/bbsController/delBbsComment?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=E911084885C64FBF8F08342E079EBE38
	 * @return
	 */
	@RequestMapping("/delBbsComment")
	@ResponseBody
	public Json delBbsComment(ZcBbsComment comment, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			ZcBbsComment c = zcBbsCommentService.get(comment.getId());
//			if(s.getId().equals(c.getUserId())) {
				zcBbsCommentService.delete(comment);
				zcForumBbsService.updateCount(c.getBbsId(), -1, "bbs_comment");
				j.success();
				j.setMsg("删除成功！");
//			} else {
//				j.fail();
//				j.setMsg("无权删除他人评论！");
//			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 转发帖子
	 * @return
	 */
	@RequestMapping("/addShare")
	@ResponseBody
	public Json addShare(ZcShareRecord share, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			share.setUserId(s.getId());
			zcShareRecordService.add(share);
			zcForumBbsService.updateCount(share.getBbsId(), 1, "bbs_share");

			j.success();
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.fail();
		}
		return j;
	}

	/**
	 * 帖子编辑
	 *
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id, HttpServletRequest request) {
		ZcForumBbs bbs = zcForumBbsService.get(id);
		ZcFile file = new ZcFile();
		file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
		file.setObjectId(bbs.getId());
		file.setFileType("FT01");
		bbs.setFiles(zcFileService.queryFiles(file));

		request.setAttribute("bbs", bbs);
		return "/wsale/bbs/bbs_edit";
	}

	/**
	 * 编辑帖子(james)TODO Created by james on 2016/8/26 0026.
	 * http://localhost:8080/api/bbsController/editBbs?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=CA2387B0F76041E89626FB1348F8E431&bbsTitle=新收的&mediaIds=image1,image2,image3
	 * @return
	 */
	@RequestMapping("/editBbs")
	@ResponseBody
	public Json editBbs(ZcForumBbs bbs, String delFileIds, boolean isEdit, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(s.getId())) {

				// 推送加精消息
				if(bbs.getIsEssence() != null) {
					if(bbs.getIsEssence()) {
						bbs.setIsLight(false);
						sendWxMessage.sendCustomMessage(bbs.getId(), "bbs_e", s.getNickname());
					}
					bbs.setUpdateUserId(s.getId());
					bbs.setUpdatetime(new Date());
				}
				// 推送加亮消息
				if(bbs.getIsLight() != null) {
					if(bbs.getIsLight()) {
						bbs.setIsEssence(false);
						sendWxMessage.sendCustomMessage(bbs.getId(), "bbs_l", s.getNickname());
					}
					bbs.setUpdateUserId(s.getId());
					bbs.setUpdatetime(new Date());
				}
				// 推送置顶消息
				if(bbs.getIsTop() != null) {
					if(bbs.getIsTop())
						sendWxMessage.sendCustomMessage(bbs.getId(), "bbs_t", s.getNickname());

					bbs.setUpdateUserId(s.getId());
					bbs.setUpdatetime(new Date());
				}
				// 推送关闭消息
				if(!F.empty(bbs.getBbsStatus()) && "BS02".equals(bbs.getBbsStatus())) {
					sendWxMessage.sendCustomMessage(bbs.getId(), "bbs_o", s.getNickname());
				}
				// 推送移动消息
				if(!F.empty(bbs.getCategoryId())) {
					sendWxMessage.sendCustomMessage(bbs.getId(), "bbs_m", bbs.getCategoryId());
				}
				// 推送删除消息
				if(bbs.getIsDeleted() != null && bbs.getIsDeleted()) {
					sendWxMessage.sendCustomMessage(bbs.getId(), "bbs_d", s.getNickname());
				}

				if(isEdit) {
					bbs.setLastUpdateUserId(s.getId());
					bbs.setLastUpdateTime(new Date());
				}

				bbs.setLogUserId(s.getId());
				zcForumBbsService.edit(bbs);

				if(!F.empty(bbs.getMediaIds())) {
					String[] mediaIds = bbs.getMediaIds().split(",");
					final CompletionService completionService = CompletionFactory.initCompletion();
					final String realPath = request.getSession().getServletContext().getRealPath("/");

					ZcFile zcFile = new ZcFile();
					zcFile.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode()); // 对象类型：帖子
					zcFile.setObjectId(bbs.getId());
					zcFile.setFileType("FT01"); // 文件类型：图片
					int index = zcFileService.getMaxSeq(zcFile);
					for(final String mediaId : mediaIds) {
						zcFile = new ZcFile();
						zcFile.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode()); // 对象类型：帖子
						zcFile.setObjectId(bbs.getId());
						zcFile.setFileType("FT01"); // 文件类型：图片
						zcFile.setSeq(++index);
						completionService.submit(new Task<ZcFile, Object>(zcFile){
							@Override
							public Boolean call() throws Exception {
								String filePath = DownloadMediaUtil.downloadMedia(realPath, mediaId, "BBS");
								getD().setFileOriginalUrl(filePath);
								getD().setFileHandleUrl(ImageUtils.pressImage(filePath, realPath));
								zcFileService.add(getD());
								return true;
							}
						});
					}
					completionService.sync();
				}
				if(!F.empty(delFileIds)) {
					final CompletionService completionService = CompletionFactory.initCompletion();
					String[] fileIds = delFileIds.split(",");
					for(String fileId : fileIds) {
						if(F.empty(fileId)) continue;
						completionService.submit(new Task<String, Object>(fileId){
							@Override
							public Boolean call() throws Exception {
								zcFileService.delete(getD());
								return true;
							}
						});
					}
					completionService.sync();
				}
				j.success();
				j.setMsg("操作成功");
			}
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 转发打赏-废弃
	 * @return
	 */
	@RequestMapping("/reward")
	@ResponseBody
	public Json reward(ZcBbsReward reward, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
//			reward.setUserId(s.getId());
//			reward.setPayStatus("PS01");
//			zcBbsRewardService.add(reward);
//			//zcForumBbsService.updateCount(reward.getBbsId(), 1, "bbs_reward");
//			j.setObj(reward.getId());
			j.success();
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.fail();
		}
		return j;
	}
}
