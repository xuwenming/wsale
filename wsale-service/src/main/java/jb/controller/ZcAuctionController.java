package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcAuctionServiceI;
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
 * ZcAuction管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcAuctionController")
public class ZcAuctionController extends BaseController {

	@Autowired
	private ZcAuctionServiceI zcAuctionService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 跳转到ZcAuction管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcauction/zcAuction";
	}

	/**
	 * 获取ZcAuction数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcAuction zcAuction, PageHelper ph) {
		DataGrid dataGrid = zcAuctionService.dataGrid(zcAuction, ph);
		List<ZcAuction> list = (List<ZcAuction>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcAuction auction : list) {
				completionService.submit(new Task<ZcAuction, User>(new CacheKey("user", auction.getBuyerId()), auction) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getBuyerId());
						return user;
					}

					protected void set(ZcAuction d, User v) {
						if (v != null)
							d.setBuyerName(v.getNickname());
					}

				});
			}
			completionService.sync();
		}
		return dataGrid;
	}

	@RequestMapping("/dataGridByProduct")
	@ResponseBody
	public DataGrid dataGridByProduct(ZcAuction zcAuction, PageHelper ph) {
		return dataGrid(zcAuction, ph);
	}
	/**
	 * 获取ZcAuction数据表格excel
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
	public void download(ZcAuction zcAuction, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcAuction,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcAuction页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcAuction zcAuction = new ZcAuction();
		zcAuction.setId(jb.absx.UUID.uuid());
		return "/zcauction/zcAuctionAdd";
	}

	/**
	 * 添加ZcAuction
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcAuction zcAuction) {
		Json j = new Json();		
		zcAuctionService.add(zcAuction);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcAuction查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcAuction zcAuction = zcAuctionService.get(id);
		request.setAttribute("zcAuction", zcAuction);
		return "/zcauction/zcAuctionView";
	}

	/**
	 * 跳转到ZcAuction修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcAuction zcAuction = zcAuctionService.get(id);
		request.setAttribute("zcAuction", zcAuction);
		return "/zcauction/zcAuctionEdit";
	}

	/**
	 * 修改ZcAuction
	 * 
	 * @param zcAuction
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcAuction zcAuction) {
		Json j = new Json();		
		zcAuctionService.edit(zcAuction);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcAuction
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcAuctionService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
