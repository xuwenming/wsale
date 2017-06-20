package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcPositionApplyServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiPositionApply")
public class ApiPositionApplyController extends BaseController {


	@Autowired
	private ZcPositionApplyServiceI zcPositionApplyService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private UserServiceI userService;

	@RequestMapping("/applyOne")
	public String applyOne(String categoryId, HttpServletRequest request) {

		ZcCategory category = zcCategoryService.get(categoryId);
		ZcCategory pc = null;
		if(!F.empty(category.getPid())) {
			pc = zcCategoryService.get(category.getPid());
		}
		category.setName((pc != null ? pc.getName() + " - " : "") + category.getName());
		request.setAttribute("category", category);

		// 获取分类
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

		return "/wsale/positionApply/apply_one";
	}

	@RequestMapping("/applyTwo")
	public String applyTwo(ZcPositionApply positionApply, HttpServletRequest request) {
		positionApply.setApplyUserName(getSessionInfo(request).getNickname());
		request.setAttribute("positionApply", positionApply);
		return "/wsale/positionApply/apply_two";
	}

	@RequestMapping("/applySuccess")
	public String applySuccess(String categoryId, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		return "/wsale/positionApply/apply_success";
	}

	/**
	 * 职位申请
	 * http://localhost:8080/api/apiPositionApply/applyPosition?tokenId=1D96DACB84F21890ED9F4928FA8B352B&categoryId=988F9AB6C2BF4428BA3ED58F0F799385&roleId=bz&applyContent=我要申请版主&recommend=小五&companyName=莫辩&specialty=我是专长&advice=我是建议&activityForum=我是经常参与的版块&onlineDuration=10
	 * @return
	 */
	@RequestMapping("/applyPosition")
	@ResponseBody
	public Json applyPosition(ZcPositionApply pa, HttpServletRequest request) {
		Json j = new Json();
		try{
			pa.setApplyUserId(getSessionInfo(request).getId());
			ZcPositionApply checkPa = zcPositionApplyService.get(pa);
			if(checkPa != null) {
				pa = checkPa;
			} else {
				pa.setAuditStatus("AS01"); // 待审核
				pa.setPayStatus("PS01"); // 待支付
				zcPositionApplyService.add(pa);
			}

			j.setObj(pa.getId());
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 申请条件检查
	 * @return
	 */
	@RequestMapping("/checkApply")
	@ResponseBody
	public Json checkApply(String roleId, HttpServletRequest request) {
		Json j = new Json();
		try{
			Map<String, Object> obj = new HashMap<String, Object>();
			SessionInfo s = getSessionInfo(request);
			ZcPositionApply pa = new ZcPositionApply();
			pa.setApplyUserId(s.getId());
			pa.setRoleId(roleId);
			pa = zcPositionApplyService.get(pa);
			if(pa != null) {
				boolean f = true;
				// 审核通过需要检查角色期限
				if("AS02".equals(pa.getAuditStatus())) {
					if("zsjs".equals(roleId) || "zscj".equals(roleId)) {
						if(!userService.checkRoleIsValid(s.getId(), roleId)) {
							f = false;
						}
					}
				}

				if(f) {
					obj.put("applyed", true); // 正在申请或已申请过
					obj.put("apply", pa);
				} else {
					obj.put("applyed", false); // 未申请过
				}
			} else {
				obj.put("applyed", false); // 未申请过
			}
			User u = userService.get(s.getId(), true);
			obj.put("isAuth", u.getIsAuth()); // 是否实名

			j.setObj(obj);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}
