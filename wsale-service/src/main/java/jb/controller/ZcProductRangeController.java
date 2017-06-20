package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.ZcProductRangeServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * ZcProductRange管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcProductRangeController")
public class ZcProductRangeController extends BaseController {

	@Autowired
	private ZcProductRangeServiceI zcProductRangeService;


	/**
	 * 跳转到ZcProductRange管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcproductrange/zcProductRange";
	}

	/**
	 * 获取ZcProductRange数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcProductRange zcProductRange, PageHelper ph) {
		return zcProductRangeService.dataGrid(zcProductRange, ph);
	}
	@RequestMapping("/dataGridByProduct")
	@ResponseBody
	public DataGrid dataGridByProduct(ZcProductRange zcProductRange, PageHelper ph) {
		// 获取加价幅度
		if(CollectionUtils.isEmpty(zcProductRangeService.query(zcProductRange)))
			zcProductRange.setProductId("-1");
		return dataGrid(zcProductRange, ph);
	}
	/**
	 * 获取ZcProductRange数据表格excel
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
	public void download(ZcProductRange zcProductRange, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcProductRange,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcProductRange页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcProductRange zcProductRange = new ZcProductRange();
		zcProductRange.setId(UUID.randomUUID().toString());
		return "/zcproductrange/zcProductRangeAdd";
	}

	/**
	 * 添加ZcProductRange
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcProductRange zcProductRange) {
		Json j = new Json();		
		zcProductRangeService.add(zcProductRange);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcProductRange查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcProductRange zcProductRange = zcProductRangeService.get(id);
		request.setAttribute("zcProductRange", zcProductRange);
		return "/zcproductrange/zcProductRangeView";
	}

	/**
	 * 跳转到ZcProductRange修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcProductRange zcProductRange = zcProductRangeService.get(id);
		request.setAttribute("zcProductRange", zcProductRange);
		return "/zcproductrange/zcProductRangeEdit";
	}

	/**
	 * 修改ZcProductRange
	 * 
	 * @param zcProductRange
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcProductRange zcProductRange) {
		Json j = new Json();		
		zcProductRangeService.edit(zcProductRange);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcProductRange
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcProductRangeService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
