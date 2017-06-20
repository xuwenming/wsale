package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcProductMarginServiceI;
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
 * ZcProductMargin管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcProductMarginController")
public class ZcProductMarginController extends BaseController {

	@Autowired
	private ZcProductMarginServiceI zcProductMarginService;

	@Autowired
	private UserServiceI userService;


	/**
	 * 跳转到ZcProductMargin管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcproductmargin/zcProductMargin";
	}

	/**
	 * 获取ZcProductMargin数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcProductMargin zcProductMargin, PageHelper ph) {
		DataGrid dataGrid = zcProductMarginService.dataGrid(zcProductMargin, ph);
		List<ZcProductMargin> list = (List<ZcProductMargin>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcProductMargin margin : list) {
				completionService.submit(new Task<ZcProductMargin, User>(new CacheKey("user", margin.getBuyUserId()), margin) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getBuyUserId());
						return user;
					}

					protected void set(ZcProductMargin d, User v) {
						if (v != null)
							d.setBuyUserName(v.getNickname());
					}

				});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	@RequestMapping("/dataGridByProduct")
	@ResponseBody
	public DataGrid dataGridByProduct(ZcProductMargin zcProductMargin, PageHelper ph) {
		return dataGrid(zcProductMargin, ph);
	}
	/**
	 * 获取ZcProductMargin数据表格excel
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
	public void download(ZcProductMargin zcProductMargin, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcProductMargin,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcProductMargin页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcProductMargin zcProductMargin = new ZcProductMargin();
		zcProductMargin.setId(jb.absx.UUID.uuid());
		return "/zcproductmargin/zcProductMarginAdd";
	}

	/**
	 * 添加ZcProductMargin
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcProductMargin zcProductMargin) {
		Json j = new Json();		
		zcProductMarginService.add(zcProductMargin);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcProductMargin查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcProductMargin zcProductMargin = zcProductMarginService.get(id);
		request.setAttribute("zcProductMargin", zcProductMargin);
		return "/zcproductmargin/zcProductMarginView";
	}

	/**
	 * 跳转到ZcProductMargin修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcProductMargin zcProductMargin = zcProductMarginService.get(id);
		request.setAttribute("zcProductMargin", zcProductMargin);
		return "/zcproductmargin/zcProductMarginEdit";
	}

	/**
	 * 修改ZcProductMargin
	 * 
	 * @param zcProductMargin
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcProductMargin zcProductMargin) {
		Json j = new Json();		
		zcProductMarginService.edit(zcProductMargin);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcProductMargin
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcProductMarginService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
