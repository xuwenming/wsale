package jb.controller;

import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.pageModel.ZcPraise;
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
	private ZcTopicServiceI zcTopicService;

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

}
