package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcBbsLogServiceI;
import jb.service.impl.CompletionFactory;
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

/**
 * ZcBbsLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcBbsLogController")
public class ZcBbsLogController extends BaseController {

	@Autowired
	private ZcBbsLogServiceI zcBbsLogService;

	@Autowired
	private UserServiceI userService;


	/**
	 * 跳转到ZcBbsLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcbbslog/zcBbsLog";
	}

	/**
	 * 获取ZcBbsLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcBbsLog zcBbsLog, PageHelper ph) {
		DataGrid dataGrid = zcBbsLogService.dataGrid(zcBbsLog, ph);
		List<ZcBbsLog> list = (List<ZcBbsLog>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcBbsLog log : list) {
				if(!F.empty(log.getUserId()))
					// 审核人
					completionService.submit(new Task<ZcBbsLog, User>(new CacheKey("user", log.getUserId()), log) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getUserId(), true);
							return user;
						}

						protected void set(ZcBbsLog d, User v) {
							if (v != null)
								d.setUserName(v.getNickname());
						}
					});

			}
			completionService.sync();
		}
		return dataGrid;
	}
	@RequestMapping("/dataGridByBbs")
	@ResponseBody
	public DataGrid dataGridByBbs(ZcBbsLog zcBbsLog, PageHelper ph) {
		return dataGrid(zcBbsLog, ph);
	}
	/**
	 * 获取ZcBbsLog数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(ZcBbsLog zcBbsLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcBbsLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcBbsLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcBbsLog zcBbsLog = new ZcBbsLog();
		zcBbsLog.setId(jb.absx.UUID.uuid());
		return "/zcbbslog/zcBbsLogAdd";
	}

	/**
	 * 添加ZcBbsLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcBbsLog zcBbsLog) {
		Json j = new Json();		
		zcBbsLogService.add(zcBbsLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcBbsLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcBbsLog zcBbsLog = zcBbsLogService.get(id);
		request.setAttribute("zcBbsLog", zcBbsLog);
		return "/zcbbslog/zcBbsLogView";
	}

	/**
	 * 跳转到ZcBbsLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcBbsLog zcBbsLog = zcBbsLogService.get(id);
		request.setAttribute("zcBbsLog", zcBbsLog);
		return "/zcbbslog/zcBbsLogEdit";
	}

	/**
	 * 修改ZcBbsLog
	 * 
	 * @param zcBbsLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcBbsLog zcBbsLog) {
		Json j = new Json();		
		zcBbsLogService.edit(zcBbsLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcBbsLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcBbsLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
