package jb.util.wx.service;


import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.User;
import jb.pageModel.ZcFile;
import jb.pageModel.ZcLastViewLog;
import jb.service.UserServiceI;
import jb.service.ZcFileServiceI;
import jb.service.ZcLastViewLogServiceI;
import jb.util.EnumConstants;
import jb.util.wx.MessageUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.message.resp.Article;
import jb.util.wx.message.resp.CustomerMessage;
import jb.util.wx.message.resp.NewsMessage;
import jb.util.wx.message.resp.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 类名: CoreService.java 
 * 描述: 微信核心服务类
 * 日期: 2015-1-12
 * 修改记录：
 */
public class CoreService {
	
	/**
	 * 函数名: processRequest
	 * 功能: 处理微信发来请求
	 * 参数: <输入参数说明>
	 * 返回值: String
	 * 抛出异常: <函数抛出的异常说明>
	 * 备注: <其它说明>
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(cal.getTime().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			int start = 9, end = 18;
			boolean transferCustomerService = false;
			try{
				String kfOnlineTime = Application.getString(WeixinUtil.KF_ONLINE_TIME);
				kfOnlineTime = F.empty(kfOnlineTime) ? "9-18" : kfOnlineTime;
				String[] times = kfOnlineTime.split("-");
				start = Integer.valueOf(times[0]);
				end = Integer.valueOf(times[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				if(hour < start || hour >= end) {
					respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
				} else {
					//respContent = "您好，请点击下面的菜单，会有新发现哦^_^";
					transferCustomerService = true;
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				if(hour < start || hour >= end) {
					respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
				} else {
					//respContent = "您好，请点击下面的菜单，会有新发现哦^_^";
					transferCustomerService = true;
				}
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				if(hour < start || hour >= end) {
					respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
				} else {
					//respContent = "您好，请点击下面的菜单，会有新发现哦^_^";
					transferCustomerService = true;
				}
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				if(hour < start || hour >= end) {
					respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
				} else {
					//respContent = "您好，请点击下面的菜单，会有新发现哦^_^";
					transferCustomerService = true;
				}
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				if(hour < start || hour >= end) {
					respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
				} else {
					//respContent = "您好，请点击下面的菜单，会有新发现哦^_^";
					transferCustomerService = true;
				}
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
//				articleList = new ArrayList<Article>();
				//事件类型
				String eventType = requestMap.get("Event");
				//订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					ZcLastViewLogServiceI logService = Application.getBean(ZcLastViewLogServiceI.class);
					UserServiceI userService = Application.getBean(UserServiceI.class);
					ZcFileServiceI fileService = Application.getBean(ZcFileServiceI.class);
					User user = userService.getByName(fromUserName);
					if(user != null) {
						ZcLastViewLog log = new ZcLastViewLog();
						log.setBusinessType("SUBSCRIBE");
						log.setUserId(user.getId());
						ZcLastViewLog exist = logService.get(log);
						if(exist != null) {
							// 删除记录
							logService.delete(exist.getId());

							//创建图文消息
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setFuncFlag(0);

							List<Article> articleList = new ArrayList<Article>();
							Article article = new Article();
							article.setTitle("因为有缘所以舍不得离开你，请继续戳我！");
							article.setDescription("");
							ZcFile file = new ZcFile();
							file.setObjectType(exist.getObjectType());
							file.setObjectId(exist.getObjectId());
							file.setFileType("FT01");
							ZcFile f = fileService.get(file);
							if(f != null) article.setPicUrl(f.getFileHandleUrl());

							if (EnumConstants.OBJECT_TYPE.PRODUCT.getCode().equals(exist.getObjectType())) {
								article.setUrl(Application.getString("SV100") + "api/apiProductController/productDetail?id=" + exist.getObjectId());
							} else if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(exist.getObjectType())) {
								article.setUrl(Application.getString("SV100") + "api/bbsController/bbsDetail?id=" + exist.getObjectId());
							}
							articleList.add(article);
							// 设置图文消息个数
							newsMessage.setArticleCount(articleList.size());
							// 设置图文消息包含的图文集合
							newsMessage.setArticles(articleList);
							// 将图文消息对象转换成xml字符串
							respMessage = MessageUtil.newsMessageToXml(newsMessage);

							return respMessage;
						}
					}

					respContent = getWelcome();
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				} 
				// 地理位置
				else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					if(hour < start || hour >= end) {
						respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
					} else {
						//respContent = "您好，请点击下面的菜单，会有新发现哦^_^";
						transferCustomerService = true;
					}
				}else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 自定义菜单点击事件
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");
					if(eventKey.equals("ck_kf")) {
						if(hour < start || hour >= end) {
							respContent = "客服工作时间是"+start+":00 - "+end+":00 现在无法为您提供服务。";
						} else {
							transferCustomerService = true;
						}

					}
				}
			}
			if(transferCustomerService) {
				CustomerMessage customerMessage = new CustomerMessage();
				customerMessage.setToUserName(fromUserName);
				customerMessage.setFromUserName(toUserName);
				customerMessage.setCreateTime(new Date().getTime());
				customerMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_CUSTOMER);
				respMessage = MessageUtil.customerMessageToXml(customerMessage);
				return respMessage;
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	
	/**
	 * Q译通使用指南
	 * @return
	 */
	public static String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(emoji(0xe148)).append("Q译通使用指南").append("\n\n");
		buffer.append("Q译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    中 -> 英").append("\n");
		buffer.append("    英 -> 中").append("\n");
		buffer.append("    日 -> 中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}

	public static String getWelcome() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("哈喽，欢迎来到集东集西收藏品社区").append("\n\n\n\n\n");
		buffer.append("单击左下角 首页 一起集真、集古、集藏。");
		return buffer.toString();
	}
	
	/** 
     * emoji表情转换(hex -> utf-16) 
     * @param hexEmoji
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  
	
}
