package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcProductServiceI;

import jb.service.impl.CompletionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

/**
 * ZcProduct管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcProductController")
public class ZcProductController extends BaseController {

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private UserServiceI userService;
	/**
	 * 跳转到ZcProduct管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcproduct/zcProduct";
	}

	/**
	 * 获取ZcProduct数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcProduct zcProduct, PageHelper ph) {
		zcProduct.setIsDeleted(false);
		DataGrid dataGrid=zcProductService.dataGrid(zcProduct, ph);
		List<ZcProduct> list = (List<ZcProduct>)dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcProduct product : list) {
				if (!F.empty(product.getCategoryId())) {
					completionService.submit(new Task<ZcProduct, String>(new CacheKey("category", product.getCategoryId()), product) {
						@Override
						public String call() throws Exception {
							ZcCategory c = zcCategoryService.get(getD().getCategoryId());
							return c.getName();
						}

						protected void set(ZcProduct d, String v) {
							if (v != null)
								d.setCname(v);
						}
					});
				}
				if (!F.empty(product.getAddUserId())) {
					completionService.submit(new Task<ZcProduct, String>(new CacheKey("user", product.getAddUserId()), product) {
						@Override
						public String call() throws Exception {
							User user = userService.getByZc(getD().getAddUserId());
							return user.getNickname();
						}

						protected void set(ZcProduct d, String v) {
							if (v != null)
								d.setAddUserName(v);
						}
					});
				}
				if (!F.empty(product.getUserId())) {
					completionService.submit(new Task<ZcProduct, String>(new CacheKey("user", product.getUserId()), product) {
						@Override
						public String call() throws Exception {
							User user = userService.getByZc(getD().getUserId());
							return user.getNickname();
						}

						protected void set(ZcProduct d, String v) {
							if (v != null)
								d.setUserName(v);
						}
					});
				}


			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcProduct数据表格excel
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
	public void download(ZcProduct zcProduct, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcProduct,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcProduct页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcProduct zcProduct = new ZcProduct();
		zcProduct.setId(jb.absx.UUID.uuid());
		return "/zcproduct/zcProductAdd";
	}

	/**
	 * 添加ZcProduct
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcProduct zcProduct) {
		Json j = new Json();		
		zcProductService.add(zcProduct);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcProduct查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcProduct zcProduct = zcProductService.get(id, null);
		if (!F.empty(zcProduct.getCategoryId())) {
			ZcCategory category = zcCategoryService.get(zcProduct.getCategoryId());
			ZcCategory pc = null;
			if (!F.empty(category.getPid())) {
				pc = zcCategoryService.get(category.getPid());
			}
			zcProduct.setCname((pc != null ? pc.getName() + " - " : "") + category.getName());
		}
		if (!F.empty(zcProduct.getAddUserId())) {
			User user = userService.getByZc(zcProduct.getAddUserId());
			zcProduct.setAddUserName(user.getNickname());
		}
		if (!F.empty(zcProduct.getUserId())) {
			User user = userService.getByZc(zcProduct.getUserId());
			zcProduct.setUserName(user.getNickname());
		}
		request.setAttribute("zcProduct", zcProduct);
		return "/zcproduct/zcProductView";
	}

	/**
	 * 跳转到ZcProduct修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcProduct zcProduct = zcProductService.get(id);
		request.setAttribute("zcProduct", zcProduct);
		return "/zcproduct/zcProductEdit";
	}

	/**
	 * 修改ZcProduct
	 * 
	 * @param zcProduct
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcProduct zcProduct) {
		Json j = new Json();		
		zcProductService.edit(zcProduct);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcProduct
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcProductService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/queryProducts")
	@ResponseBody
	public List<ZcProduct> queryProducts(String q, boolean isPno) {
		List<ZcProduct> list = new ArrayList<ZcProduct>();
		ZcProduct zcProduct = new ZcProduct();
		if(F.empty(q)) {
			return list;
		} else {
			if(isPno) zcProduct.setPno(q);
			else zcProduct.setContent(q);
			zcProduct.setIsDeleted(false);
		}
		list = zcProductService.query(zcProduct);
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcProduct product : list) {
				if(!F.empty(product.getCategoryId()))
					completionService.submit(new Task<ZcProduct, String>(new CacheKey("category", product.getCategoryId()), product) {
						@Override
						public String call() throws Exception {
							ZcCategory c = zcCategoryService.get(getD().getCategoryId());
							return c.getName();
						}

						protected void set(ZcProduct d, String v) {
							if (v != null)
								d.setCname(v);
						}
					});

				if(!F.empty(product.getAddUserId()))
					completionService.submit(new Task<ZcProduct, String>(new CacheKey("user", product.getAddUserId()), product) {
						@Override
						public String call() throws Exception {
							User u = userService.getByZc(getD().getAddUserId());
							return u.getNickname();
						}

						protected void set(ZcProduct d, String v) {
							if (v != null)
								d.setAddUserName(v);
						}
					});
			}
			completionService.sync();
		}

		return list;
	}

}
