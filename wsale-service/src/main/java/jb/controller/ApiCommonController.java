package jb.controller;

import com.alibaba.fastjson.JSONObject;
import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.pageModel.ZcLastViewLog;
import jb.service.ZcLastViewLogServiceI;
import jb.service.impl.RedisUserServiceImpl;
import jb.util.Constants;
import jb.util.EnumConstants;
import jb.util.MP3Util;
import jb.util.RSAUtil;
import jb.util.wx.DownloadMediaUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.bean.Text;
import jb.util.wx.bean.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共模块接口
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiCommon")
public class ApiCommonController extends BaseController {

	@Resource
	private RedisUserServiceImpl redisUserService;

	@Autowired
	private ZcLastViewLogServiceI zcLastViewLogService;
	
	/**
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/error")
	public Json error() {
		Json j = new Json();
		j.setObj("token_expire");
		j.setSuccess(false);
		j.setMsg("token过期，请重新登录！");
		return j;
	}

	/**
	 * 上传图片/语音(帖子评论tag:COMMENT、聊天tag:CHAT等)
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Json upload(String mediaId, String type, String tag, HttpServletRequest request) {
		Json j = new Json();
		try{
			Map<String, Object> obj = new HashMap<String, Object>();
			String path = null;
			String realPath = request.getSession().getServletContext().getRealPath("/");
			if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(type)) {
				path = DownloadMediaUtil.downloadMedia(realPath, mediaId, tag);
			} else if(EnumConstants.MSG_TYPE.AUDIO.getCode().equals(type)) {
				path = MP3Util.convert(DownloadMediaUtil.downloadMedia(realPath, mediaId, tag + "/Voice", EnumConstants.MSG_TYPE.AUDIO.getCode()), request);
				int duration = MP3Util.getDuration(path.substring(path.indexOf(Constants.UPLOADFILE)), request);
				obj.put("duration", duration);
			}
			obj.put("path", path);
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
	 * 发送链接
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendLink")
	public Json sendLink(String openid, String link) {
		Json j = new Json();
		TextMessage tm = new TextMessage();
		Text text = new Text();
		StringBuffer buffer = new StringBuffer();
		buffer.append("分享链接：").append("\n");
//		buffer.append("<a target='_blank' href='" + link + "'>" + link.split("//")[1] + "</a>");
		buffer.append(link);
		text.setContent(buffer.toString());
		tm.setTouser(openid);
		tm.setMsgtype("text");
		tm.setText(text);
		WeixinUtil.sendCustomMessage(JSONObject.toJSONString(tm));
		j.success();
		return j;
	}

	/**
	 * 获取RSA公钥接口
	 */
	@RequestMapping("/getPublicKey")
	@ResponseBody
	public Json getPublicKey(HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			Map<String,String> keyMap = RSAUtil.generateKeyPair();
			String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
			redisUserService.setRSAPrivateKey(s.getId(), keyMap.get(RSAUtil.PRIVATE_KEY));
			j.setSuccess(true);
			j.setMsg("获取RSA公钥接口成功！");
			j.setObj(publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return j;
	}

	/**
	 * 添加未关注前最后访问记录
	 */
	@RequestMapping("/addSubscribeLog")
	@ResponseBody
	public Json addSubscribeLog(String objectType, String objectId, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			if(!"cs".equals(s.getId())) {
				ZcLastViewLog log = new ZcLastViewLog();
				log.setBusinessType("SUBSCRIBE");
				log.setUserId(s.getId());
				ZcLastViewLog exist = zcLastViewLogService.get(log);

				log.setObjectType(objectType);
				log.setObjectId(objectId);
				log.setLastViewTime(new Date());
				if(exist == null) {
					zcLastViewLogService.add(log);
				} else {
					log.setId(exist.getId());
					zcLastViewLogService.edit(log);
				}
			}

			j.setSuccess(true);
			j.setMsg("获取RSA公钥接口成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return j;
	}

}
