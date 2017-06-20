package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcProductLikeServiceI;
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
 * ZcProductLike管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcProductLikeController")
public class ZcProductLikeController extends BaseController {

	@Autowired
	private ZcProductLikeServiceI zcProductLikeService;

	@Autowired
	private UserServiceI userService;
	/**
	 * 跳转到ZcProductLike管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcproductlike/zcProductLike";
	}

	/**
	 * 获取ZcProductLike数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcProductLike zcProductLike, PageHelper ph) {
		DataGrid dataGrid=zcProductLikeService.dataGrid(zcProductLike, ph);
		List<ZcProductLike> list = (List<ZcProductLike>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcProductLike like : list) {
				completionService.submit(new Task<ZcProductLike, User>(new CacheKey("user", like.getUserId()), like) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getUserId());
						return user;
					}

					protected void set(ZcProductLike d, User v) {
						if (v != null)
							d.setUserName(v.getNickname());
					}

				});

			}
			completionService.sync();
		}
		return dataGrid;
	}
	@RequestMapping("/dataGridByProduct")
	@ResponseBody
	public DataGrid dataGridByProduct(ZcProductLike zcProductLike, PageHelper ph) {
		return dataGrid(zcProductLike, ph);
	}
	/**
	 * 获取ZcProductLike数据表格excel
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
	public void download(ZcProductLike zcProductLike, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcProductLike,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcProductLike页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcProductLike zcProductLike = new ZcProductLike();
		zcProductLike.setId(jb.absx.UUID.uuid());
		return "/zcproductlike/zcProductLikeAdd";
	}

	/**
	 * 添加ZcProductLike
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcProductLike zcProductLike) {
		Json j = new Json();		
		zcProductLikeService.add(zcProductLike);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcProductLike查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcProductLike zcProductLike = zcProductLikeService.get(id);
		request.setAttribute("zcProductLike", zcProductLike);
		return "/zcproductlike/zcProductLikeView";
	}

	/**
	 * 跳转到ZcProductLike修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcProductLike zcProductLike = zcProductLikeService.get(id);
		request.setAttribute("zcProductLike", zcProductLike);
		return "/zcproductlike/zcProductLikeEdit";
	}

	/**
	 * 修改ZcProductLike
	 * 
	 * @param zcProductLike
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcProductLike zcProductLike) {
		Json j = new Json();		
		zcProductLikeService.edit(zcProductLike);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcProductLike
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcProductLikeService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
