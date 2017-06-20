package jb.controller;

import jb.absx.F;
import jb.pageModel.BaseData;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.BasedataServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiBaseDataController")
public class ApiBaseDataController extends BaseController {
	
	@Autowired
	private BasedataServiceI basedataService;
	
	/**
	 * 获取基础数据
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/basedata")
	@ResponseBody
	public Json basedata(PageHelper ph,String dataType,String pid) {
		Json j = new Json();
		try{
			System.out.println(111);
			BaseData baseData = new BaseData();
			baseData.setBasetypeCode(dataType);
			baseData.setPid(F.empty(pid) ? "-1" : pid);
			j.setObj(basedataService.getBaseDatas(baseData));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}	
	
}
