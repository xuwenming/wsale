package jb.controller;

import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.pageModel.ZcCollect;
import jb.pageModel.ZcPraise;
import jb.service.ZcCollectServiceI;
import jb.service.ZcForumBbsServiceI;
import jb.service.ZcPraiseServiceI;
import jb.service.ZcTopicServiceI;
import jb.util.EnumConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
	private ZcForumBbsServiceI zcForumBbsService;

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

}
