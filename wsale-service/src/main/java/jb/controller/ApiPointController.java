package jb.controller;

import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
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
 * 点赞/收藏
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiPoint")
public class ApiPointController extends BaseController {

	@Autowired
	private ZcPraiseServiceI zcPraiseService;

	@Autowired
	private ZcCollectServiceI zcCollectService;

	@Autowired
	private ZcTopicServiceI zcTopicService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ZcFileServiceI zcFileService;

	/**
	 * 点赞接口
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addPraise")
	public Json addPraise(String objectId, String objectType, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcPraise praise = new ZcPraise();
			praise.setObjectId(objectId);
			praise.setObjectType(objectType);
			praise.setUserId(s.getId());
			ZcPraise exist = zcPraiseService.get(praise);
			if(exist == null) {
				zcPraiseService.add(praise);
				if(EnumConstants.OBJECT_TYPE.TOPIC.getCode().equals(objectType)) {
					zcTopicService.updateCount(objectId, 1, "topic_praise");
				}
			}

			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 取消赞接口
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancelPraise")
	public Json cancelPraise(String objectId, String objectType, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcPraise praise = new ZcPraise();
			praise.setObjectId(objectId);
			praise.setObjectType(objectType);
			praise.setUserId(s.getId());
			ZcPraise exist = zcPraiseService.get(praise);
			if(exist != null)
				zcPraiseService.delete(exist.getId());
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 收藏接口
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addCollect")
	public Json addCollect(String objectId, String objectType, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcCollect collect = new ZcCollect();
			collect.setObjectId(objectId);
			collect.setObjectType(objectType);
			collect.setUserId(s.getId());
			ZcCollect exist = zcCollectService.get(collect);
			if(exist == null) {
				zcCollectService.add(collect);
				if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(objectType)) {
					zcForumBbsService.updateCount(objectId, 1, "bbs_collect");
				}
			}

			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 取消收藏接口
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancelCollect")
	public Json cancelCollect(String objectId, String objectType, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcCollect collect = new ZcCollect();
			collect.setObjectId(objectId);
			collect.setObjectType(objectType);
			collect.setUserId(s.getId());
			ZcCollect exist = zcCollectService.get(collect);
			if(exist != null) {
				zcCollectService.delete(exist.getId());
				if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(objectType)) {
					zcForumBbsService.updateCount(objectId, -1, "bbs_collect");
				}
			}
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 收藏列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/collects")
	public Json collects(String objectType, PageHelper ph, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcCollect zcCollect = new ZcCollect();
			zcCollect.setUserId(s.getId());
			zcCollect.setObjectType(objectType);
			ph.setSort("addtime");
			ph.setOrder("desc");
			DataGrid dataGrid = zcCollectService.dataGrid(zcCollect, ph);
			List<ZcCollect> collects = (List<ZcCollect>)dataGrid.getRows();
			if(CollectionUtils.isNotEmpty(collects)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcCollect collect : collects) {
					if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(objectType)) {
						completionService.submit(new Task<ZcCollect, ZcForumBbs>(collect) {
							@Override
							public ZcForumBbs call() throws Exception {
								ZcForumBbs bbs = zcForumBbsService.get(getD().getObjectId());
								User user = userService.getByZc(bbs.getAddUserId());
								bbs.setAddUserName(user.getNickname());
								ZcFile file = new ZcFile();
								file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
								file.setObjectId(bbs.getId());
								file.setFileType("FT01");
								ZcFile f = zcFileService.get(file);
								bbs.setIcon(f.getFileHandleUrl());
								return bbs;
							}

							protected void set(ZcCollect d, ZcForumBbs v) {
								if (v != null)
									d.setObject(v);
							}

						});
					}
				}
				completionService.sync();
			}
			j.setObj(dataGrid);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}
;