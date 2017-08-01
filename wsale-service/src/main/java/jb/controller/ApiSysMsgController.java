package jb.controller;

import jb.pageModel.*;
import jb.service.ZcProductServiceI;
import jb.service.ZcSysMsgLogServiceI;
import jb.service.ZcSysMsgServiceI;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统消息
 * Created by wenming on 2017/7/12.
 */
@Controller
@RequestMapping("/api/apiSysMsg")
public class ApiSysMsgController extends BaseController {

	@Autowired
	private ZcSysMsgServiceI zcSysMsgService;

	@Autowired
	private ZcSysMsgLogServiceI zcSysMsgLogService;

	@Autowired
	private ZcProductServiceI zcProductService;

	/**
	 * 跳转至系统消息
	 * @return
	 */
	@RequestMapping("/sysMsg")
	public String sysMsg(HttpServletRequest request) {
		return "/wsale/chat/sys_msg";
	}

	/**
	 * 中介交易列表
	 * @return
	 */
	@RequestMapping("/sysMsgList")
	@ResponseBody
	public Json sysMsgList(PageHelper ph, ZcSysMsg zcSysMsg, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("newtime");
			ph.setOrder("desc");

			zcSysMsg.setUserId(s.getId());
			DataGrid dataGrid = zcSysMsgService.dataGrid(zcSysMsg, ph);
			List<ZcSysMsg> sysMsgs = (List<ZcSysMsg>) dataGrid.getRows();
			if(CollectionUtils.isNotEmpty(sysMsgs)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcSysMsg sysMsg : sysMsgs) {
					completionService.submit(new Task<ZcSysMsg, List<ZcSysMsgLog>>(new CacheKey("sysMsg", sysMsg.getId()), sysMsg) {
						@Override
						public List<ZcSysMsgLog> call() throws Exception {
							List<ZcSysMsgLog> sysMsgLogs = getSysMsgLogs(getD().getId());
							return sysMsgLogs;
						}
						protected void set(ZcSysMsg d, List<ZcSysMsgLog> v) {
							if(v != null) {
								d.setSysMsgLogs(v);
							}
						}
					});
					completionService.submit(new Task<ZcSysMsg, Map<String, Object>>(sysMsg) {
						@Override
						public Map<String, Object> call() throws Exception {
							Map<String, Object> product = new HashMap<String, Object>();
							if(EnumConstants.OBJECT_TYPE.PRODUCT.getCode().equals(getD().getObjectType())) {
								ZcProduct p = zcProductService.get(getD().getObjectId(), null);
								product.put("id", p.getId());
								product.put("icon", p.getIcon());
								product.put("content", p.getContentLine());
							}

							return product;
						}
						protected void set(ZcSysMsg d, Map<String, Object> v) {
							if(v != null) {
								d.setProduct(v);
							}
						}
					});
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

	private List<ZcSysMsgLog> getSysMsgLogs(String sysMsgId) {
		PageHelper ph = new PageHelper();
		ph.setPage(1);
		ph.setRows(20);
		ph.setSort("addtime");
		ph.setOrder("desc");
		ZcSysMsgLog sysMsgLog = new ZcSysMsgLog();
		sysMsgLog.setSysMsgId(sysMsgId);
		DataGrid dataGrid = zcSysMsgLogService.dataGrid(sysMsgLog, ph);
		return (List<ZcSysMsgLog>)dataGrid.getRows();
	}
}
