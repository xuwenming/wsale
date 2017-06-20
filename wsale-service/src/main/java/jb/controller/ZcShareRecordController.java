package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcShareRecordServiceI;
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
 * ZcShareRecord管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcShareRecordController")
public class ZcShareRecordController extends BaseController {

	@Autowired
	private ZcShareRecordServiceI zcShareRecordService;

	@Autowired
	private UserServiceI userService;


	/**
	 * 跳转到ZcShareRecord管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcsharerecord/zcShareRecord";
	}

	/**
	 * 获取ZcShareRecord数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcShareRecord zcShareRecord, PageHelper ph) {
		DataGrid dataGrid = zcShareRecordService.dataGrid(zcShareRecord, ph);
		List<ZcShareRecord> list = (List<ZcShareRecord>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcShareRecord log : list) {
				if(!F.empty(log.getUserId()))
					// 审核人
					completionService.submit(new Task<ZcShareRecord, User>(new CacheKey("user", log.getUserId()), log) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getUserId(), true);
							return user;
						}

						protected void set(ZcShareRecord d, User v) {
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
	public DataGrid dataGridByBbs(ZcShareRecord zcShareRecord, PageHelper ph) {
		return dataGrid(zcShareRecord, ph);
	}
	/**
	 * 获取ZcShareRecord数据表格excel
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
	public void download(ZcShareRecord zcShareRecord, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcShareRecord,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcShareRecord页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcShareRecord zcShareRecord = new ZcShareRecord();
		zcShareRecord.setId(jb.absx.UUID.uuid());
		return "/zcsharerecord/zcShareRecordAdd";
	}

	/**
	 * 添加ZcShareRecord
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcShareRecord zcShareRecord) {
		Json j = new Json();		
		zcShareRecordService.add(zcShareRecord);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcShareRecord查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcShareRecord zcShareRecord = zcShareRecordService.get(id);
		request.setAttribute("zcShareRecord", zcShareRecord);
		return "/zcsharerecord/zcShareRecordView";
	}

	/**
	 * 跳转到ZcShareRecord修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcShareRecord zcShareRecord = zcShareRecordService.get(id);
		request.setAttribute("zcShareRecord", zcShareRecord);
		return "/zcsharerecord/zcShareRecordEdit";
	}

	/**
	 * 修改ZcShareRecord
	 * 
	 * @param zcShareRecord
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcShareRecord zcShareRecord) {
		Json j = new Json();		
		zcShareRecordService.edit(zcShareRecord);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcShareRecord
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcShareRecordService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
