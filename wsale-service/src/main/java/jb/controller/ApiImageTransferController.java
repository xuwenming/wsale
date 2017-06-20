package jb.controller;

import jb.absx.F;
import jb.model.Tuser;
import jb.pageModel.*;
import jb.service.*;
import jb.util.Constants;
import jb.util.EnumConstants;
import jb.util.Util;
import jb.util.oss.OSSUtil;
import jb.util.wx.DownloadMediaUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/imageTransfer")
public class ApiImageTransferController extends BaseController {
	@Autowired
	private BasedataServiceI basedataService;
	@Autowired
	private ZcAuthenticationServiceI zcAuthenticationService;
	@Autowired
	private ZcBannerServiceI zcBannerService;
	@Autowired
	private ZcBbsCommentServiceI zcBbsCommentService;
	@Autowired
	private ZcCategoryServiceI zcCategoryService;
	@Autowired
	private ZcChatMsgServiceI zcChatMsgService;
	@Autowired
	private ZcFileServiceI zcFileService;
	@Autowired
	private ZcShopServiceI zcShopService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private ZcChatFriendServiceI zcChatFriendService;

	@RequestMapping("/basedata")
	@ResponseBody
	public Json basedata(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			List<BaseData> bds = basedataService.getBaseDatas(new BaseData());
			for(BaseData bd : bds) {
				if(!F.empty(bd.getIcon()) && bd.getIcon().startsWith(Constants.UPLOADFILE)) {
					File file = new File(realpath + bd.getIcon());
					bd.setIcon(OSSUtil.putFile(OSSUtil.bucketName, file, bd.getIcon()));
					basedataService.edit(bd);
				}
			}
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/authentication")
	@ResponseBody
	public Json authentication(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("addtime");
				ph.setOrder("asc");
				List<ZcAuthentication> list = (List<ZcAuthentication>)zcAuthenticationService.dataGrid(new ZcAuthentication(), ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(ZcAuthentication o : list) {
						if(!F.empty(o.getIdFrontByhand()) && o.getIdFrontByhand().startsWith(Constants.UPLOADFILE))
							o.setIdFrontByhand(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getIdFrontByhand()), o.getIdFrontByhand()));
						if(!F.empty(o.getIdFront()) && o.getIdFront().startsWith(Constants.UPLOADFILE))
							o.setIdFront(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getIdFront()), o.getIdFront()));
						if(!F.empty(o.getIdBack()) && o.getIdBack().startsWith(Constants.UPLOADFILE))
							o.setIdBack(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getIdBack()), o.getIdBack()));
						if(!F.empty(o.getBussinessLicense()) && o.getBussinessLicense().startsWith(Constants.UPLOADFILE))
							o.setBussinessLicense(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getBussinessLicense()), o.getBussinessLicense()));
						if(!F.empty(o.getLegalPersonId()) && o.getLegalPersonId().startsWith(Constants.UPLOADFILE))
							o.setLegalPersonId(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getLegalPersonId()), o.getLegalPersonId()));
						if(!F.empty(o.getLegalPersonIdFront()) && o.getLegalPersonIdFront().startsWith(Constants.UPLOADFILE))
							o.setLegalPersonIdFront(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getLegalPersonIdFront()), o.getLegalPersonIdFront()));
						if(!F.empty(o.getLegalPersonIdBack()) && o.getLegalPersonIdBack().startsWith(Constants.UPLOADFILE))
							o.setLegalPersonIdBack(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getLegalPersonIdBack()), o.getLegalPersonIdBack()));

						zcAuthenticationService.edit(o);
					}
				}
				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/banner")
	@ResponseBody
	public Json banner(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			List<ZcBanner> list = zcBannerService.query(new ZcBanner());
			for(ZcBanner o : list) {
				if(checkIcon(o.getUrl())) {
					File file = new File(realpath + o.getUrl());
					o.setUrl(OSSUtil.putFile(OSSUtil.bucketName, file, o.getUrl()));
					zcBannerService.edit(o);
				}
			}
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/bbsComment")
	@ResponseBody
	public Json bbsComment(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("addtime");
				ph.setOrder("asc");
				ZcBbsComment q = new ZcBbsComment();
				q.setCtype(EnumConstants.MSG_TYPE.IMAGE.getCode());
				List<ZcBbsComment> list = (List<ZcBbsComment>)zcBbsCommentService.dataGrid(q, ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(ZcBbsComment o : list) {
						if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(o.getCtype()) && checkIcon(o.getComment())) {
							File file = new File(realpath + o.getComment());
							o.setComment(OSSUtil.putFile(OSSUtil.bucketName, file, o.getComment()));
							zcBbsCommentService.edit(o);
						}
					}
				}
				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/category")
	@ResponseBody
	public Json category(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			List<ZcCategory> list = zcCategoryService.query(new ZcCategory());
			for(ZcCategory o : list) {
				if(checkIcon(o.getIcon())) {
					File file = new File(realpath + o.getIcon());
					o.setIcon(OSSUtil.putFile(OSSUtil.bucketName, file, o.getIcon()));
					zcCategoryService.edit(o);
				}
			}
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/chatMsg")
	@ResponseBody
	public Json chatMsg(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("addtime");
				ph.setOrder("asc");
				List<ZcChatMsg> list = (List<ZcChatMsg>)zcChatMsgService.dataGrid(new ZcChatMsg(), ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(ZcChatMsg o : list) {
						if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(o.getMtype()) || EnumConstants.MSG_TYPE.AUDIO.getCode().equals(o.getMtype())) {
							if(checkIcon(o.getContent())) {
								File file = new File(realpath + o.getContent());
								o.setContent(OSSUtil.putFile(OSSUtil.bucketName, file, o.getContent()));
								zcChatMsgService.edit(o);
							}

						}
					}
				}
				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/file")
	@ResponseBody
	public Json file(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("addtime");
				ph.setOrder("asc");
				List<ZcFile> list = (List<ZcFile>)zcFileService.dataGrid(new ZcFile(), ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(ZcFile o : list) {
						if(checkIcon(o.getFileHandleUrl())) {
							File file = new File(realpath + o.getFileHandleUrl());
							o.setFileHandleUrl(OSSUtil.putFile(OSSUtil.bucketName, file, o.getFileHandleUrl()));
						}
						if(checkIcon(o.getFileOriginalUrl())) {
							File file = new File(realpath + o.getFileOriginalUrl());
							o.setFileOriginalUrl(OSSUtil.putFile(OSSUtil.bucketName, file, o.getFileOriginalUrl()));
						}
						zcFileService.edit(o);
					}
				}
				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/shop")
	@ResponseBody
	public Json shop(HttpServletRequest request) {
		Json j = new Json();
		try{
			String realpath = request.getSession().getServletContext().getRealPath("/");
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("addtime");
				ph.setOrder("asc");
				List<ZcShop> list = (List<ZcShop>)zcShopService.dataGrid(new ZcShop(), ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(ZcShop o : list) {
						if(checkIcon(o.getLogoUrl())) {
							o.setLogoUrl(OSSUtil.putFile(OSSUtil.bucketName, new File(realpath + o.getLogoUrl()), o.getLogoUrl()));
							zcShopService.edit(o);
						}
					}
				}
				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/chatFriend")
	@ResponseBody
	public Json chatFriend(HttpServletRequest request) {
		Json j = new Json();
		try{
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("createdatetime");
				ph.setOrder("asc");

				User user = new User();
				user.setUtype("UT02");
				List<User> list = (List<User>) userService.dataGrid(user, ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(User o : list) {
						DataGrid dataGrid = zcChatMsgService.dataGridComplex(o.getId(), null);
						List<Map> l = (List<Map>) dataGrid.getRows();
						if(CollectionUtils.isNotEmpty(list)) {
							for(Map m : l) {
								Integer isDeleted = (Integer)m.get("isDeleted");
								int isFrom = ((BigInteger)m.get("isFrom")).intValue();
								ZcChatFriend friend = new ZcChatFriend();
								friend.setUserId(o.getId());
								friend.setFriendUserId((String)m.get("friendUserId"));
								if(isDeleted == 3 || (isFrom == 1 && isDeleted == 1) || (isFrom == 0 && isDeleted == 2)) {
									friend.setIsDeleted(true);
								} else {
									friend.setIsDeleted(false);
								}

								String content = (String) m.get("content");
								String mtype = (String) m.get("mtype");
								if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(mtype)){
									content = "[图片]";
								} else if(EnumConstants.MSG_TYPE.AUDIO.getCode().equals(mtype)){
									content = "[语音]";
								} else if(EnumConstants.MSG_TYPE.PRODUCT.getCode().equals(mtype)){
									content = "[拍品]";
								} else if(EnumConstants.MSG_TYPE.BBS.getCode().equals(mtype)){
									content = "[帖子]";
								}
								friend.setLastContent(content);
								friend.setLastTime((Date)m.get("addtime"));

								zcChatFriendService.add(friend);
							}
						}
					}
				}

				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/refreshHeadImage")
	@ResponseBody
	public Json refreshHeadImage(HttpServletRequest request) {
		Json j = new Json();
		try{
			int page = 1, pageSize = 50;
			while(true){
				int count = 0;
				PageHelper ph = new PageHelper();
				ph.setPage(page);
				ph.setRows(pageSize);
				ph.setSort("createdatetime");
				ph.setOrder("asc");

				User user = new User();
				user.setUtype("UT02");
				List<User> list = (List<User>) userService.dataGrid(user, ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					count = list.size();
					for(User o : list) {
						if(!F.empty(o.getHeadImage()) && o.getHeadImage().indexOf("wx.qlogo.cn") > 0) {
							o.setHeadImage(DownloadMediaUtil.downloadHeadImage(o.getHeadImage(), o.getName()));
							userService.updateHeadImage(o);
						}
					}
				}

				page++;

				if(count < pageSize) break;
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			j.setMsg("操作失败");
			e.printStackTrace();
		}
		return j;
	}

	private boolean checkIcon(String str) {
		if(!F.empty(str) && str.startsWith(Constants.UPLOADFILE)) {
			return true;
		}
		return false;
	}
}
