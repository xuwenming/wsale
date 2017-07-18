package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;

import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.util.ConfigUtil;
import jb.util.Constants;
import jb.util.RSAUtil;
import jb.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

/**
 * ZcOfflineTransfer管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcOfflineTransferController")
public class ZcOfflineTransferController extends BaseController {

	@Autowired
	private ZcOfflineTransferServiceI zcOfflineTransferService;

	@Autowired
	private ZcPayOrderServiceI zcPayOrderService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcWalletDetailServiceI zcWalletDetailService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;


	/**
	 * 跳转到ZcOfflineTransfer管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcofflinetransfer/zcOfflineTransfer";
	}

	/**
	 * 获取ZcOfflineTransfer数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcOfflineTransfer zcOfflineTransfer, PageHelper ph) {
		DataGrid dataGrid = zcOfflineTransferService.dataGrid(zcOfflineTransfer, ph);
		List<ZcOfflineTransfer> list = (List<ZcOfflineTransfer>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcOfflineTransfer t : list) {
				if(!F.empty(t.getUserId()))
					completionService.submit(new Task<ZcOfflineTransfer, User>(new CacheKey("user", t.getUserId()), t) {
						@Override
						public User call() throws Exception {
							User u = userService.getByZc(getD().getUserId());
							return u;
						}

						protected void set(ZcOfflineTransfer d, User v) {
							if (v != null)
								d.setUser(v);
						}
					});
				if(!F.empty(t.getHandleUserId()))
					completionService.submit(new Task<ZcOfflineTransfer, String>(new CacheKey("user", t.getHandleUserId()), t) {
						@Override
						public String call() throws Exception {
							User u = userService.getByZc(getD().getHandleUserId());
							return u.getNickname();
						}

						protected void set(ZcOfflineTransfer d, String v) {
							if (v != null)
								d.setHandleUserName(v);
						}
					});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcOfflineTransfer数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(ZcOfflineTransfer zcOfflineTransfer, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcOfflineTransfer,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcOfflineTransfer页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcOfflineTransfer zcOfflineTransfer = new ZcOfflineTransfer();
		zcOfflineTransfer.setId(jb.absx.UUID.uuid());
		return "/zcofflinetransfer/zcOfflineTransferAdd";
	}

	/**
	 * 添加ZcOfflineTransfer
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcOfflineTransfer zcOfflineTransfer) {
		Json j = new Json();		
		zcOfflineTransferService.add(zcOfflineTransfer);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcOfflineTransfer查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcOfflineTransfer zcOfflineTransfer = zcOfflineTransferService.get(id);
		if(!F.empty(zcOfflineTransfer.getUserId()))
			zcOfflineTransfer.setUser(userService.getByZc(zcOfflineTransfer.getUserId()));
		if(!F.empty(zcOfflineTransfer.getHandleUserId()))
			zcOfflineTransfer.setHandleUserName(userService.getByZc(zcOfflineTransfer.getHandleUserId()).getNickname());

		request.setAttribute("zcOfflineTransfer", zcOfflineTransfer);
		return "/zcofflinetransfer/zcOfflineTransferView";
	}

	/**
	 * 跳转到ZcOfflineTransfer修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcOfflineTransfer zcOfflineTransfer = zcOfflineTransferService.get(id);
		if(!F.empty(zcOfflineTransfer.getUserId()))
			zcOfflineTransfer.setUser(userService.getByZc(zcOfflineTransfer.getUserId()));
		if(!F.empty(zcOfflineTransfer.getHandleUserId()))
			zcOfflineTransfer.setHandleUserName(userService.getByZc(zcOfflineTransfer.getHandleUserId()).getNickname());

		request.setAttribute("zcOfflineTransfer", zcOfflineTransfer);
		return "/zcofflinetransfer/zcOfflineTransferEdit";
	}

	/**
	 * 修改ZcOfflineTransfer
	 * 
	 * @param zcOfflineTransfer
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcOfflineTransfer zcOfflineTransfer, String oldHandleStatus, String checkPwd, HttpServletRequest request) {
		Json j = new Json();

		// 获取提现充值密码
		String privateKey = (String)request.getSession().getAttribute(RSAUtil.PRIVATE_KEY);
		if(F.empty(privateKey)) {
			j.setMsg("操作失败，请刷新或关闭当前浏览器重新打开！");
			return j;
		}
		checkPwd = RSAUtil.decryptByPravite(checkPwd, privateKey);

		User admin = userService.get(Constants.MANAGERADMIN);
		if(F.empty(checkPwd) || !checkPwd.equals(admin.getHxPassword())) {
			j.fail();
			j.setMsg("校验密码错误");
			return j;
		}

		String handleUserId = ((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId();

		// 处理成功，自动充值到余额
		if(!zcOfflineTransfer.getIsWallet() && "HS03".equals(zcOfflineTransfer.getHandleStatus())) {
			zcOfflineTransfer.setIsWallet(true);

			// 更新余额
//			zcPayOrderService.updateWallet(zcOfflineTransfer.getUserId(), zcOfflineTransfer.getTransferAmount());

			// 新增钱包收支明细
			ZcWalletDetail zcWalletDetail = new ZcWalletDetail();
			zcWalletDetail.setUserId(zcOfflineTransfer.getUserId());
			zcWalletDetail.setOrderNo(Util.CreateWalletNo());
			zcWalletDetail.setAmount(zcOfflineTransfer.getTransferAmount());
			zcWalletDetail.setWtype("WT01"); // 充值
			zcWalletDetail.setChannel("CS05"); // 转账汇款
			zcWalletDetail.setDescription("线下转账汇款");
			zcWalletDetailService.addAndUpdateWallet(zcWalletDetail);

			// 发送线下转账成功通知
			sendWxMessage.sendOfflineTransferTemplateMessage(zcOfflineTransfer.getId(), 1);

		} else if(!"HS04".equals(oldHandleStatus) && "HS04".equals(zcOfflineTransfer.getHandleStatus())) {
			// 发送线下转账失败通知
			sendWxMessage.sendOfflineTransferTemplateMessage(zcOfflineTransfer.getId(), 2);
		}

		zcOfflineTransfer.setHandleUserId(handleUserId);
		zcOfflineTransfer.setHandleTime(new Date());
		zcOfflineTransferService.edit(zcOfflineTransfer);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcOfflineTransfer
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcOfflineTransferService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
