package jb.controller;

import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.service.impl.order.OrderState;
import jb.util.EnumConstants;
import jb.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 中介交易
 * Created by wenming on 2017/7/12.
 */
@Controller
@RequestMapping("/api/apiIntermediary")
public class ApiIntermediaryController extends BaseController {

	@Autowired
	private ZcIntermediaryServiceI zcIntermediaryService;

	@Autowired
	private ZcIntermediaryLogServiceI zcIntermediaryLogService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@javax.annotation.Resource(name = "order01StateImpl")
	private OrderState order01State;

	/**
	 * 跳转至我的中介交易
	 * type 1-待处理；2-已完成；3-已取消；
	 * @return
	 */
	@RequestMapping("/myIntermediary")
	public String myIntermediary(HttpServletRequest request) {
		return "/wsale/intermediary/intermediary_list";
	}

	/**
	 * 中介交易列表
	 * @return
	 */
	@RequestMapping("/intermediaryList")
	@ResponseBody
	public Json intermediaryList(PageHelper ph, ZcIntermediary zcIntermediary, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("addtime");
			ph.setOrder("desc");
			zcIntermediary.setSellUserId(s.getId());
			DataGrid dataGrid = zcIntermediaryService.dataGrid(zcIntermediary, ph);
			List<ZcIntermediary> ims = (List<ZcIntermediary>) dataGrid.getRows();
			if(CollectionUtils.isNotEmpty(ims)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				final String curUserId = s.getId();
				for(ZcIntermediary im : ims) {
					completionService.submit(new Task<ZcIntermediary, ZcForumBbs>(new CacheKey("bbs", im.getBbsId()), im) {
						@Override
						public ZcForumBbs call() throws Exception {
							ZcForumBbs bbs = zcForumBbsService.get(getD().getBbsId());
							ZcFile file = new ZcFile();
							file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
							file.setObjectId(bbs.getId());
							file.setFileType("FT01");
							ZcFile f = zcFileService.get(file);
							bbs.setIcon(f.getFileHandleUrl());
							return bbs;
						}
						protected void set(ZcIntermediary d, ZcForumBbs v) {
							if(v != null) {
								d.setBbs(v);
							}
						}
					});
					completionService.submit(new Task<ZcIntermediary, ZcIntermediaryLog>(im) {
						@Override
						public ZcIntermediaryLog call() throws Exception {
							ZcIntermediaryLog log = new ZcIntermediaryLog();
							log.setImId(getD().getId());
							return zcIntermediaryLogService.get(log);
						}
						protected void set(ZcIntermediary d, ZcIntermediaryLog v) {
							if(v != null) {
								d.setLastLog(v);
							}
						}
					});
					completionService.submit(new Task<ZcIntermediary, User>(im) {
						@Override
						public User call() throws Exception {
							User user = null;
							if(curUserId.equals(getD().getSellUserId())) {
								user = userService.getByZc(getD().getUserId());
							} else if(curUserId.equals(getD().getUserId())) {
								user = userService.getByZc(getD().getSellUserId());
							}
							return user;
						}
						protected void set(ZcIntermediary d, User v) {
							if(v != null) {
								d.setImUser(v);
							}
						}
					});

					if(s.getId().equals(im.getSellUserId())) {
						im.setIsBuyer(false);
					} else {
						im.setIsBuyer(true);
					}
				}
				completionService.sync();
			}

			j.setObj(dataGrid);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 中介交易详情
	 * @return
	 */
	@RequestMapping("/intermediaryDetail")
	public String intermediaryDetail(String id, HttpServletRequest request) {
		try{
			SessionInfo s = getSessionInfo(request);
			ZcIntermediary im = zcIntermediaryService.get(id);

			ZcForumBbs bbs = zcForumBbsService.get(im.getBbsId());
			ZcFile file = new ZcFile();
			file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
			file.setObjectId(bbs.getId());
			file.setFileType("FT01");
			ZcFile f = zcFileService.get(file);
			bbs.setIcon(f.getFileHandleUrl());
			im.setBbs(bbs);

			User user = null;
			if(s.getId().equals(im.getSellUserId())) {
				user = userService.getByZc(im.getUserId());
				im.setIsBuyer(false);
			} else if (s.getId().equals(im.getUserId())) {
				user = userService.getByZc(im.getSellUserId());
				im.setIsBuyer(true);
			}
			im.setImUser(user);

			ZcIntermediaryLog log = new ZcIntermediaryLog();
			log.setImId(id);
			List<ZcIntermediaryLog> logs = zcIntermediaryLogService.query(log);
			im.setLastLog(logs.get(0));
			im.setLogs(logs);

			request.setAttribute("intermediary", im);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wsale/intermediary/intermediary_detail";
	}

	/**
	 * 创建中介交易
	 * @return
	 */
	@RequestMapping("/addIntermediary")
	@ResponseBody
	public Json addIntermediary(ZcIntermediary zcIntermediary, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcIntermediary exist = new ZcIntermediary();
			exist.setStatus("IS01");
			exist.setUserId(s.getId());
			exist.setBbsId(zcIntermediary.getBbsId());
			if(zcIntermediaryService.get(exist) != null) {
				j.setMsg("您申请的交易正在处理中，请耐心等待或联系卖家！");
				j.fail();
				return j;
			}

			zcIntermediary.setUserId(s.getId());
			zcIntermediary.setImNo(Util.CreateIMNo());
			zcIntermediary.setStatus("IS01");

			ZcIntermediaryLog log = new ZcIntermediaryLog();
			log.setUserId(s.getId());
			log.setLogType("IL01");
			log.setIntermediary(zcIntermediary);

			zcIntermediaryLogService.addAndUpdateIM(log);

			// 给卖家发送中介交易提醒
			sendWxMessage.sendIMPayTemplateMessage(zcIntermediary, 0);

			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 取消交易
	 * @return
	 */
	@RequestMapping("/cancelIM")
	@ResponseBody
	public Json cancelIM(ZcIntermediary zcIntermediary, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			zcIntermediary.setStatus("IS03");

			ZcIntermediaryLog log = new ZcIntermediaryLog();
			log.setUserId(s.getId());
			log.setLogType("IL04");
			log.setContent("买家取消");
			log.setIntermediary(zcIntermediary);

			zcIntermediaryLogService.addAndUpdateIM(log);

			j.success();
			j.setMsg("取消成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 同意交易
	 * @return
	 */
	@RequestMapping("/agreeIM")
	@ResponseBody
	public Json agreeIM(ZcIntermediary zcIntermediary, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			zcIntermediary.setStatus("IS02");

			ZcIntermediaryLog log = new ZcIntermediaryLog();
			log.setUserId(s.getId());
			log.setLogType("IL02");
			log.setIntermediary(zcIntermediary);

			zcIntermediaryLogService.addAndUpdateIM(log);

			// 生成订单
			ZcOrder order = new ZcOrder();
			order.setProductId(zcIntermediary.getId());
			order.setIsIntermediary(true); // 中介交易订单
			order.setTotalPrice((new BigDecimal(zcIntermediary.getAmount()).divide(new BigDecimal(100))).doubleValue());
			order01State.handle(order);

			// 给买家推送同意交易通知
			sendWxMessage.sendIMResultTemplateMessage(zcIntermediary, log.getLogType());

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 拒绝交易
	 * @return
	 */
	@RequestMapping("/refuseIM")
	@ResponseBody
	public Json refuseIM(ZcIntermediary zcIntermediary, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			zcIntermediary.setStatus("IS03");

			ZcIntermediaryLog log = new ZcIntermediaryLog();
			log.setUserId(s.getId());
			log.setLogType("IL03");
			log.setContent(zcIntermediary.getContent());
			log.setIntermediary(zcIntermediary);

			zcIntermediaryLogService.addAndUpdateIM(log);

			// 给买家推送拒绝交易通知
			sendWxMessage.sendIMResultTemplateMessage(zcIntermediary, log.getLogType());

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}
