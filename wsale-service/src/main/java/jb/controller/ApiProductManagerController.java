package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.ProductCommon;
import jb.util.*;
import jb.util.wx.DownloadMediaUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiProductMangerController")
public class ApiProductManagerController extends BaseController {



	@Autowired
	private ProductCommon productCommon;
	/**
	 * 拍品列表
	 * http://localhost:8080/api/apiProductMangerController/productList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10&status=PT05
	 * @return
	 */
	@RequestMapping("/productList")
	@ResponseBody
	public Json productList(PageHelper ph,ZcProduct zc, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("addtime");
			ph.setOrder("desc");
			ZcProduct zcProduct =new ZcProduct();
			zcProduct.setAddUserId(s.getId());
			zcProduct.setStatus(zc.getStatus());
			zcProduct.setIsDeleted(false);
			j.setObj(productCommon.dataGrid(ph, zcProduct, s.getId()));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}
