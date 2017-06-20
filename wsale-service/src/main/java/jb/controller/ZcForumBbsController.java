package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcFileServiceI;
import jb.service.ZcForumBbsServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.ConfigUtil;
import jb.util.EnumConstants;
import jb.util.ImageUtils;
import jb.util.MP3Util;
import jb.util.wx.DownloadMediaUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * ZcForumBbs管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcForumBbsController")
public class ZcForumBbsController extends BaseController {

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private UserServiceI userService;


	/**
	 * 跳转到ZcForumBbs管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcforumbbs/zcForumBbs";
	}

	/**
	 * 获取ZcForumBbs数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcForumBbs zcForumBbs, PageHelper ph, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		User user = userService.getByZc(sessionInfo.getId());
		if("UT02".equals(user.getUtype())) {
			zcForumBbs.setAddUserId(user.getId());
		}
		zcForumBbs.setIsDeleted(false);
		DataGrid dataGrid = zcForumBbsService.dataGrid(zcForumBbs, ph);
		List<ZcForumBbs> list = (List<ZcForumBbs>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcForumBbs bbs : list) {
				if(!F.empty(bbs.getCategoryId()))
					completionService.submit(new Task<ZcForumBbs, String>(new CacheKey("category", bbs.getCategoryId()), bbs) {
						@Override
						public String call() throws Exception {
							ZcCategory c = zcCategoryService.get(getD().getCategoryId());
							ZcCategory pc = null;
							if(!F.empty(c.getPid())) {
								pc = zcCategoryService.get(c.getPid());
							}
							return (pc != null ? pc.getName() + " - " : "") + c.getName();
						}

						protected void set(ZcForumBbs d, String v) {
							if (v != null)
								d.setCategoryName(v);
						}
					});

				if(!F.empty(bbs.getAddUserId()))
					completionService.submit(new Task<ZcForumBbs, String>(new CacheKey("user", bbs.getAddUserId()), bbs) {
						@Override
						public String call() throws Exception {
							User u = userService.getByZc(getD().getAddUserId());
							return u.getNickname();
						}

						protected void set(ZcForumBbs d, String v) {
							if (v != null)
								d.setAddUserName(v);
						}
					});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcForumBbs数据表格excel
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
	public void download(ZcForumBbs zcForumBbs, PageHelper ph,String downloadFields,HttpServletResponse response, HttpSession session) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcForumBbs,ph,session);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcForumBbs页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcForumBbs zcForumBbs = new ZcForumBbs();
		zcForumBbs.setId(UUID.randomUUID().toString());
		return "/zcforumbbs/zcForumBbsAdd";
	}

	/**
	 * 添加ZcForumBbs
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcForumBbs zcForumBbs, String audioFileName, String imageFileNames, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		User user = userService.getByZc(sessionInfo.getId());
		if("UT02".equals(user.getUtype())) {
			ZcForumBbs q = new ZcForumBbs();
			q.setAddUserId(user.getId());
			q.setIsDeleted(false);
			q.setAddtime(new Date());
			int publishBbsNum = zcForumBbsService.query(q).size();
			int byNum = 10, nonByNum = 30;
			try {
				byNum = Integer.valueOf(Application.getString("SV200"));
				nonByNum = Integer.valueOf(Application.getString("SV201"));
			} catch (Exception e) {}
			if(("by".equals(user.getPositionId()) && publishBbsNum >= byNum)
					|| publishBbsNum >= nonByNum) {
				j.fail();
				j.setMsg("您今日发帖已全部用完，请明日再来哟！");
				return j;
			}
		}
		zcForumBbs.setAddUserId(sessionInfo.getId());
		zcForumBbs.setUpdatetime(new Date());
		zcForumBbsService.add(zcForumBbs);
		final CompletionService completionService = CompletionFactory.initCompletion();
		if(!F.empty(audioFileName)) {
			int duration = MP3Util.getDuration(audioFileName, request);
			ZcFile zcFile = new ZcFile();
			zcFile.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode()); // 对象类型：帖子
			zcFile.setObjectId(zcForumBbs.getId());
			zcFile.setFileType("FT02"); // 文件类型：语音
			zcFile.setFileOriginalUrl(audioFileName);
			zcFile.setFileHandleUrl(audioFileName);
			zcFile.setDuration(duration);
			zcFile.setSeq(1);
			completionService.submit(new Task<ZcFile, Object>(zcFile){
				@Override
				public Boolean call() throws Exception {
					zcFileService.add(getD());
					return true;
				}
			});
		}
		if(!F.empty(imageFileNames)) {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String[] fileNames = imageFileNames.split(";");
			int index = 0;
			for(String fileName : fileNames) {
				if(F.empty(fileName)) continue;
				ZcFile zcFile = new ZcFile();
				zcFile.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode()); // 对象类型：帖子
				zcFile.setObjectId(zcForumBbs.getId());
				zcFile.setFileType("FT01"); // 文件类型：图片
				zcFile.setFileOriginalUrl(fileName);
				zcFile.setFileHandleUrl(ImageUtils.pressImage(fileName, realPath));
				zcFile.setSeq(index ++ );
				completionService.submit(new Task<ZcFile, Object>(zcFile){
					@Override
					public Boolean call() throws Exception {
						zcFileService.add(getD());
						return true;
					}
				});
			}
		}
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcForumBbs查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcForumBbs zcForumBbs = zcForumBbsService.get(id);
		ZcFile file = new ZcFile();
		file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
		file.setObjectId(zcForumBbs.getId());
		file.setFileType("FT01");
		zcForumBbs.setFiles(zcFileService.queryFiles(file));
		if("BT03".equals(zcForumBbs.getBbsType())) {
			file.setFileType("FT02");
			zcForumBbs.setVoiceFiles(zcFileService.queryFiles(file));
		}
		User user = userService.getByZc(zcForumBbs.getAddUserId());
		zcForumBbs.setAddUserName(user.getNickname());
		request.setAttribute("zcForumBbs", zcForumBbs);

		ZcCategory category = zcCategoryService.get(zcForumBbs.getCategoryId());
		ZcCategory pc = null;
		if(!F.empty(category.getPid())) {
			pc = zcCategoryService.get(category.getPid());
		}
		request.setAttribute("categoryName", (pc != null ? pc.getName() + " - " : "") + category.getName());
		return "/zcforumbbs/zcForumBbsView";
	}

	/**
	 * 跳转到ZcForumBbs修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcForumBbs zcForumBbs = zcForumBbsService.get(id);
		ZcFile file = new ZcFile();
		file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
		file.setObjectId(zcForumBbs.getId());
		file.setFileType("FT01");
		zcForumBbs.setFiles(zcFileService.queryFiles(file));
		if("BT03".equals(zcForumBbs.getBbsType())) {
			file.setFileType("FT02");
			zcForumBbs.setVoiceFiles(zcFileService.queryFiles(file));
		}
		request.setAttribute("zcForumBbs", zcForumBbs);
		return "/zcforumbbs/zcForumBbsEdit";
	}

	/**
	 * 修改ZcForumBbs
	 * 
	 * @param zcForumBbs
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcForumBbs zcForumBbs) {
		Json j = new Json();
		//zcForumBbs.setUpdatetime(new Date());
		zcForumBbsService.edit(zcForumBbs);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 跳转到ZcForumBbs修改页面
	 *
	 * @return
	 */
	@RequestMapping("/editNumPage")
	public String editNumPage(HttpServletRequest request, String id) {
		ZcForumBbs zcForumBbs = zcForumBbsService.get(id);
		request.setAttribute("zcForumBbs", zcForumBbs);
		return "/zcforumbbs/zcForumBbsEditNum";
	}

	/**
	 * 修改ZcForumBbs
	 *
	 * @param zcForumBbs
	 * @return
	 */
	@RequestMapping("/editNum")
	@ResponseBody
	public Json editNum(ZcForumBbs zcForumBbs) {
		Json j = new Json();
		zcForumBbsService.edit(zcForumBbs);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 删除ZcForumBbs
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcForumBbsService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 上传文件
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public Json upload(@RequestParam(required = false) MultipartFile audioFile, @RequestParam(required = false) MultipartFile imageFile, HttpServletRequest request) {
		Json j = new Json();
		try {
			String filePath = null;
			if(audioFile != null) {
				filePath = uploadLocalFile(request, "BBS/Voice", audioFile, "BBS");
			}
			if(imageFile != null) {
				Calendar calendar = Calendar.getInstance();
				String dirName = "BBS/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
				filePath = uploadFile(request, dirName, imageFile, "BBS");
			}
			j.setObj(filePath);
			j.success();
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/queryBbs")
	@ResponseBody
	public List<ZcForumBbs> queryBbs(String q) {
		List<ZcForumBbs> list = new ArrayList<ZcForumBbs>();
		ZcForumBbs zcForumBbs = new ZcForumBbs();
		if(F.empty(q)) {
			return list;
		} else {
			zcForumBbs.setBbsTitle(q);
		}
		list = zcForumBbsService.query(zcForumBbs);
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcForumBbs bbs : list) {
				if(!F.empty(bbs.getCategoryId()))
					completionService.submit(new Task<ZcForumBbs, String>(new CacheKey("category", bbs.getCategoryId()), bbs) {
						@Override
						public String call() throws Exception {
							ZcCategory c = zcCategoryService.get(getD().getCategoryId());
							return c.getName();
						}

						protected void set(ZcForumBbs d, String v) {
							if (v != null)
								d.setCategoryName(v);
						}
					});

				if(!F.empty(bbs.getAddUserId()))
					completionService.submit(new Task<ZcForumBbs, String>(new CacheKey("user", bbs.getAddUserId()), bbs) {
						@Override
						public String call() throws Exception {
							User u = userService.getByZc(getD().getAddUserId());
							return u.getNickname();
						}

						protected void set(ZcForumBbs d, String v) {
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
