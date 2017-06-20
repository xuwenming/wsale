package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.DonationOrderServiceI;
import jb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * DonationOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/donationOrderController")
public class DonationOrderController extends BaseController {

	@Autowired
	private DonationOrderServiceI donationOrderService;


	/**
	 * 跳转到DonationOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/donationorder/donationOrder";
	}

	/**
	 * 获取DonationOrder数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DonationOrder donationOrder, PageHelper ph) {
		return donationOrderService.dataGrid(donationOrder, ph);
	}
	/**
	 * 获取DonationOrder数据表格excel
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
	public void download(DonationOrder donationOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		ph.setSort("addtime");
		ph.setOrder("desc");
		DataGrid dg = dataGrid(donationOrder, ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		String fileName = "捐款订单-" + DateUtil.format(new Date(), "yyyyMMdd");
		downloadTable(colums, dg, new String(URLDecoder.decode(fileName, "UTF-8").getBytes(), "ISO8859-1"), response);
	}
	/**
	 * 跳转到添加DonationOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DonationOrder donationOrder = new DonationOrder();
		donationOrder.setId(UUID.randomUUID().toString());
		return "/donationorder/donationOrderAdd";
	}

	/**
	 * 添加DonationOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DonationOrder donationOrder) {
		Json j = new Json();		
		donationOrderService.add(donationOrder);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DonationOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		DonationOrder donationOrder = donationOrderService.get(id);
		request.setAttribute("donationOrder", donationOrder);
		return "/donationorder/donationOrderView";
	}

	/**
	 * 跳转到DonationOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		DonationOrder donationOrder = donationOrderService.get(id);
		request.setAttribute("donationOrder", donationOrder);
		return "/donationorder/donationOrderEdit";
	}

	/**
	 * 修改DonationOrder
	 * 
	 * @param donationOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DonationOrder donationOrder) {
		Json j = new Json();		
		donationOrderService.edit(donationOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DonationOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		donationOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
