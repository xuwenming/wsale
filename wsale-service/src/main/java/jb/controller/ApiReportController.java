package jb.controller;

import jb.interceptors.TokenManage;
import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.pageModel.User;
import jb.pageModel.ZcReport;
import jb.service.UserServiceI;
import jb.service.ZcReportServiceI;
import jb.service.impl.CompletionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Vector;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/reportController")
public class ApiReportController extends BaseController {

	@Autowired
	private ZcReportServiceI zcReportService;

	/**
	 * 举报 objectType(BBS:帖子；COMMENT:评论；PRODUCT:拍品)
	 * http://localhost:8080/api/reportController/report?tokenId=1D96DACB84F21890ED9F4928FA8B352B&objectType=BBS&objectId=CA2387B0F76041E89626FB1348F8E431&reportReason=我是来举报帖子的
	 * @return
	 */
	@RequestMapping("/report")
	@ResponseBody
	public Json report(ZcReport report, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcReport r = new ZcReport();
			r.setUserId(s.getId());
			r.setObjectType(report.getObjectType());
			r.setObjectId(report.getObjectId());
			if(zcReportService.query(r).size() > 0) {
				j.fail();
				j.setMsg("您已举报过，请耐心等待！");
				return j;
			}
			report.setUserId(s.getId());
			zcReportService.add(report);

			j.setMsg("举报成功");
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}

}
