package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * ZcReport管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcReportController")
public class ZcReportController extends BaseController {

	@Autowired
	private ZcReportServiceI zcReportService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcTopicServiceI zcTopicService;

	/**
	 * 跳转到ZcReport管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcreport/zcReport";
	}

	/**
	 * 获取ZcReport数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcReport zcReport, PageHelper ph) {
		DataGrid dataGrid = zcReportService.dataGrid(zcReport, ph);
		List<ZcReport> list = (List<ZcReport>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcReport report : list) {
				if(!F.empty(report.getObjectId()))
					completionService.submit(new Task<ZcReport, String>(new CacheKey("object", report.getObjectId()), report) {
						@Override
						public String call() throws Exception {
							String objectName = null;
							if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(getD().getObjectType())) {
								ZcForumBbs bbs = zcForumBbsService.get(getD().getObjectId());
								if(bbs != null) objectName = bbs.getBbsTitle();
							} else if(EnumConstants.OBJECT_TYPE.PRODUCT.getCode().equals(getD().getObjectType())) {
								ZcProduct product = zcProductService.get(getD().getObjectId());
								if(product != null) objectName = product.getContent();
							} else if(EnumConstants.OBJECT_TYPE.TOPIC.getCode().equals(getD().getObjectType())) {
								ZcTopic topic = zcTopicService.get(getD().getObjectId());
								if(topic != null) objectName = topic.getTitle();
							}
							return objectName;
						}

						protected void set(ZcReport d, String v) {
							if (v != null)
								d.setObjectName(v);
						}
					});

				if(!F.empty(report.getUserId()))
					completionService.submit(new Task<ZcReport, String>(new CacheKey("user", report.getUserId()), report) {
						@Override
						public String call() throws Exception {
							User u = userService.getByZc(getD().getUserId());
							return u.getNickname();
						}

						protected void set(ZcReport d, String v) {
							if (v != null)
								d.setUserName(v);
						}
					});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcReport数据表格excel
	 * 
	 * @param
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(ZcReport zcReport, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcReport,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcReport页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcReport zcReport = new ZcReport();
		zcReport.setId(UUID.randomUUID().toString());
		return "/zcreport/zcReportAdd";
	}

	/**
	 * 添加ZcReport
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcReport zcReport) {
		Json j = new Json();		
		zcReportService.add(zcReport);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcReport查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcReport zcReport = zcReportService.get(id);
		request.setAttribute("zcReport", zcReport);
		return "/zcreport/zcReportView";
	}

	/**
	 * 跳转到ZcReport修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcReport zcReport = zcReportService.get(id);
		request.setAttribute("zcReport", zcReport);
		return "/zcreport/zcReportEdit";
	}

	/**
	 * 修改ZcReport
	 * 
	 * @param zcReport
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcReport zcReport) {
		Json j = new Json();		
		zcReportService.edit(zcReport);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcReport
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcReportService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
