package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.*;
import jb.service.*;
import jb.util.EnumConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiCategoryController")
public class ApiCategoryController extends BaseController {

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcPositionApplyServiceI zcPositionApplyService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcBestProductServiceI zcBestProductService;

	/**
	 * 获取分类数据
	 * http://localhost:8080/api/apiCategoryController/category?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/category")
	public String category(HttpServletRequest request) {
		Json j = new Json();
		ZcCategory c = new ZcCategory();
		c.setPid("0");
		c.setIsDeleted(false);
		List<ZcCategory> categorys = zcCategoryService.query(c);
		if(CollectionUtils.isNotEmpty(categorys)) {
			c.setPid(categorys.get(0).getId());
			List<ZcCategory> childCategorys = zcCategoryService.query(c);
			request.setAttribute("childCategorys", childCategorys);
		}
		request.setAttribute("categorys", categorys);

		return "/wsale/category/category";
	}

	/**
	 * 获取分类数据
	 * http://localhost:8080/api/apiCategoryController/categorys?tokenId=1D96DACB84F21890ED9F4928FA8B352B&pid=0
	 * @return
	 */
	@RequestMapping("/categorys")
	@ResponseBody
	public Json categorys(ZcCategory c, HttpServletRequest request) {
		Json j = new Json();
		try{
			c.setIsDeleted(false);
			List<ZcCategory> categorys = zcCategoryService.query(c);
			j.setObj(categorys);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取分类信息
	 * http://localhost:8080/api/apiCategoryController/forum?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=988F9AB6C2BF4428BA3ED58F0F799385
	 * @return
	 */
	@RequestMapping("/forum")
	public String forum(String id, HttpServletRequest request) {

		ZcCategory category = zcCategoryService.get(id);
		if(!F.empty(category.getChiefModeratorId()))
			category.setChiefModeratorName(userService.get(category.getChiefModeratorId(), true).getNickname());

		// 获取审核通过的版主集合
		List<User> moderators = zcPositionApplyService.getAllModerators(id);
		category.setModerators(moderators);

		ZcCategory pc = null;
		if(!F.empty(category.getPid())) {
			pc = zcCategoryService.get(category.getPid());
		}
		request.setAttribute("title", (pc != null ? pc.getName() + "/" : "") + category.getName());

		request.setAttribute("category", category);
		request.setAttribute("sessionInfo", getSessionInfo(request));

		// 查询默认拍品数量
		ZcProduct product = new ZcProduct();
		product.setCategoryId(id);
		product.setIsDeleted(false);
		product.setStatus("PT03");
		long pCount = zcProductService.getCount(product);

		// 查询分类精选拍品数量
		ZcBestProduct best = new ZcBestProduct();
		best.setAuditStatus("AS02");
		best.setEndTime(new Date());
		best.setProductStatus("PT03");
		best.setCategoryId(id);
		best.setChannel(EnumConstants.BEST_CHANNEL.CATEGORY.getCode());
		long bpCount = zcBestProductService.getCount(best);

		request.setAttribute("pCount", pCount);
		request.setAttribute("bpCount", bpCount);

		return "/wsale/category/forum";
	}
	/**
	 * 板块介绍编辑接口(james)TODO Created by james on 2016/8/26 0026.
	 * http://localhost:8080/api/apiCategoryController/editIntroduce?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=05498F785EB7489AA752B670D39E61A0&forum_introduce=asdasda
	 * @return
	 */

	@RequestMapping("/editIntroduce")
	@ResponseBody
	public Json editIntroduce(String id, String forumIntroduce, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(s.getId())) {
				ZcCategory c = new ZcCategory();
				c.setId(id);
				c.setForumIntroduce(forumIntroduce);
				c.setUpdateUserId(s.getId());
				c.setUpdatetime(new Date());
				zcCategoryService.edit(c);
				j.setSuccess(true);
				j.setMsg("操作成功");
			}


		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}
