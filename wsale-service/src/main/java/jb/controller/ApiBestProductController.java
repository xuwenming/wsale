package jb.controller;

import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcBestProductServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * TODO Created by james on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiBestProductController")
public class ApiBestProductController extends BaseController {

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcBestProductServiceI zcBestProductService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 首页-精选拍品
	 * http://localhost:8080/api/apiBestProductController/homeBest?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/homeBest")
	public String homeBest() {
		return "/wsale/product/home_best_product";
	}

	/**
	 * 拍品列表
	 * http://localhost:8080/api/apiBestProductController/bestProductList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10
	 * @return
	 */
	@RequestMapping("/bestProductList")
	@ResponseBody
	public Json bestProductList(PageHelper ph, ZcBestProduct zcBestProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("auditTime");
			ph.setOrder("desc");
			zcBestProduct.setAuditStatus("AS02");
			zcBestProduct.setEndTime(new Date());
			zcBestProduct.setProductStatus("PT03");
			DataGrid dataGrid = zcBestProductService.dataGrid(zcBestProduct, ph);
			List<ZcBestProduct> products = (List<ZcBestProduct>) dataGrid.getRows();
			if(!CollectionUtils.isEmpty(products)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				final String userId = s.getId();
				final Date now = new Date();
				for (ZcBestProduct product : products) {
					// 获取封面
					completionService.submit(new Task<ZcBestProduct, ZcProduct>(product) {
						@Override
						public ZcProduct call() throws Exception {
							ZcProduct product = zcProductService.get(getD().getProductId(), userId);
							// 距街拍
							long deadlineLen = 0;
							if(product.getRealDeadline() != null)
								deadlineLen = product.getRealDeadline().getTime() - now.getTime();
							deadlineLen = deadlineLen <= 0 ? 0 : deadlineLen/1000;
							product.setDeadlineLen(deadlineLen);
							return product == null ? null : product;
						}

						protected void set(ZcBestProduct d, ZcProduct v) {
							if (v != null)
								d.setZcProduct(v);
						}
					});

				}
				completionService.sync();
			}
			j.setObj(dataGrid);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 首页精拍列表
	 * http://localhost:8080/api/apiBestProductController/bestProductGroupList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=5&channel=HOME
	 * @return
	 */
	@RequestMapping("/bestProductGroupList")
	@ResponseBody
	public Json bestProductGroupList(PageHelper ph, final ZcBestProduct zcBestProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("auditTime");
			ph.setOrder("desc");
			zcBestProduct.setAuditStatus("AS02");
			zcBestProduct.setEndTime(new Date());
			zcBestProduct.setGroup("addUserId");
			zcBestProduct.setProductStatus("PT03");

			DataGrid dataGrid = zcBestProductService.dataGrid(zcBestProduct, ph);
			List<ZcBestProduct> list = (List<ZcBestProduct>) dataGrid.getRows();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			if(!CollectionUtils.isEmpty(list)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				final String userId = s.getId();
				for (ZcBestProduct bp : list) {
					final String addUserId = bp.getAddUserId();
					completionService.submit(new Task<List<Map<String, Object>>, Map<String, Object>>(result) {
						@Override
						public Map<String, Object> call() throws Exception {
							Map<String, Object> map = new HashMap<String, Object>();
							User user = userService.get(addUserId, userId);
							map.put("user", user);

							return map;
						}

						protected void set(List<Map<String, Object>> d, Map<String, Object> v) {
							zcBestProduct.setAddUserId(addUserId);
							zcBestProduct.setGroup(null);
							List<ZcBestProduct> list = zcBestProductService.query(zcBestProduct);
							List<ZcProduct> products = new ArrayList<ZcProduct>();
							v.put("products", products);
							v.put("readCount", 0);

							if(!CollectionUtils.isEmpty(list)) {
								for (ZcBestProduct bp : list) {
									final String productId = bp.getProductId();
									completionService.submit(new Task<Map<String, Object>, ZcProduct>(v) {
										@Override
										public ZcProduct call() throws Exception {
											ZcProduct product = zcProductService.get(productId, null);
											return product;
										}
										protected void set(Map<String, Object> d, ZcProduct v) {
											List<ZcProduct> products = (List<ZcProduct>)d.get("products");
											int readCount = (int) d.get("readCount");
											products.add(v);
											d.put("readCount", readCount + v.getReadCount());
										}
									});
								}
							}

							d.add(v);
						}
					});

				}
				completionService.sync();
			}
			dataGrid.setRows(result);
			j.setObj(dataGrid);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 申请精拍
	 * @param zc
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyBestProduct")
	@ResponseBody
	public Json applyBestProduct(ZcBestProduct zc, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcBestProduct exist = zcBestProductService.get(zc);
			if(exist == null) {
				zc.setAddUserId(s.getId());
				zc.setAuditStatus("AS01");
				zc.setPayStatus("PS01");
				zcBestProductService.add(zc);
			} else {
				zc.setId(exist.getId());
			}
			j.setObj(zc.getId());
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}


	
}
