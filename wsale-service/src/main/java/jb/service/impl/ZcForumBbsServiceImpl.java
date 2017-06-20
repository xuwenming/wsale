package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcForumBbsDaoI;
import jb.model.TzcForumBbs;
import jb.pageModel.*;
import jb.service.ZcBbsLogServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcForumBbsServiceI;
import jb.service.ZcReadRecordServiceI;
import jb.util.EnumConstants;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZcForumBbsServiceImpl extends BaseServiceImpl<ZcForumBbs> implements ZcForumBbsServiceI {

	@Autowired
	private ZcForumBbsDaoI zcForumBbsDao;

	@Autowired
	private ZcReadRecordServiceI zcReadRecordService;

	@Autowired
	private ZcBbsLogServiceI zcBbsLogService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Override
	public DataGrid dataGrid(ZcForumBbs zcForumBbs, PageHelper ph) {
		List<ZcForumBbs> ol = new ArrayList<ZcForumBbs>();
		String hql = " from TzcForumBbs t ";
		DataGrid dg = dataGridQuery(hql, ph, zcForumBbs, zcForumBbsDao);
		@SuppressWarnings("unchecked")
		List<TzcForumBbs> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcForumBbs t : l) {
				ZcForumBbs o = new ZcForumBbs();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid dataGridComplex(ZcForumBbs bbs, PageHelper ph) {
		List<ZcForumBbs> ol = new ArrayList<ZcForumBbs>();
		String hql = "select distinct t from TzcForumBbs t, TzcShieldorfans sf ";
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(bbs, params);
		List<TzcForumBbs> l = zcForumBbsDao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(zcForumBbsDao.count("select count(distinct t.id) " + hql.substring(hql.indexOf("from")) + where, params));
		if (l != null && l.size() > 0) {
			for (TzcForumBbs t : l) {
				ZcForumBbs o = new ZcForumBbs();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}


	protected String whereHql(ZcForumBbs zcForumBbs, Map<String, Object> params) {
		String whereHql = "";	
		if (zcForumBbs != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcForumBbs.getCategoryId())) {
				whereHql += " and t.categoryId = :categoryId";
				params.put("categoryId", zcForumBbs.getCategoryId());
			}		
			if (!F.empty(zcForumBbs.getBbsTitle())) {
				whereHql += " and t.bbsTitle like :bbsTitle";
				params.put("bbsTitle", "%%" + zcForumBbs.getBbsTitle() + "%%");
			}		
			if (!F.empty(zcForumBbs.getBbsContent())) {
				whereHql += " and t.bbsContent = :bbsContent";
				params.put("bbsContent", zcForumBbs.getBbsContent());
			}		
			if (!F.empty(zcForumBbs.getBbsType())) {
				whereHql += " and t.bbsType = :bbsType";
				params.put("bbsType", zcForumBbs.getBbsType());
			}		
			if (!F.empty(zcForumBbs.getBbsStatus())) {
				whereHql += " and t.bbsStatus = :bbsStatus";
				params.put("bbsStatus", zcForumBbs.getBbsStatus());
			}		
			if (!F.empty(zcForumBbs.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcForumBbs.getAddUserId());
			}		
			if (!F.empty(zcForumBbs.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcForumBbs.getUpdateUserId());
			}
			if (zcForumBbs.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcForumBbs.getIsDeleted());
			}
			if (zcForumBbs.getIsTop() != null) {
				whereHql += " and t.isTop = :isTop";
				params.put("isTop", zcForumBbs.getIsTop());
			}
			if (zcForumBbs.getIsLight() != null) {
				whereHql += " and t.isLight = :isLight";
				params.put("isLight", zcForumBbs.getIsLight());
			}
			if (zcForumBbs.getIsEssence() != null) {
				whereHql += " and t.isEssence = :isEssence";
				params.put("isEssence", zcForumBbs.getIsEssence());
			}
			if (zcForumBbs.getIsHomeHot() != null) {
				whereHql += " and t.isHomeHot = :isHomeHot";
				params.put("isHomeHot", zcForumBbs.getIsHomeHot());
			}
			if(zcForumBbs.getIsHot() != null && zcForumBbs.getIsHot()) {
				whereHql += " and (t.isLight = 1 or t.isEssence = 1)";
			}
			if(!F.empty(zcForumBbs.getThemeType())) {
				if(EnumConstants.MSG_TYPE.TEXT.getCode().equals(zcForumBbs.getThemeType()))
					whereHql += " and t.bbsType != 'BT03'";
				else
					whereHql += " and t.bbsType = 'BT03'";
			}
			if (zcForumBbs.getAddtimeBegin() != null) {
				whereHql += " and t.addtime >= :addtimeBegin";
				params.put("addtimeBegin", zcForumBbs.getAddtimeBegin());
			}
			if (zcForumBbs.getAddtimeEnd() != null) {
				whereHql += " and t.addtime <= :addtimeEnd";
				params.put("addtimeEnd", zcForumBbs.getAddtimeEnd());
			}
			// 当天时间
			if(zcForumBbs.getAddtime() != null) {
				whereHql += " and to_days(t.addtime) = to_days(:addtime)";
				params.put("addtime", zcForumBbs.getAddtime());
			}
			if(zcForumBbs.getBbsComment() != null) {
				whereHql += " and t.bbsComment >= :bbsComment";
				params.put("bbsComment", zcForumBbs.getBbsComment());
			}
			if(zcForumBbs.getBbsReward() != null) {
				whereHql += " and t.bbsReward >= :bbsReward";
				params.put("bbsReward", zcForumBbs.getBbsReward());
			}
			if(zcForumBbs.getBbsShare() != null) {
				whereHql += " and t.bbsShare >= :bbsShare";
				params.put("bbsShare", zcForumBbs.getBbsShare());
			}

			// 查询关注人拍品列表条件
			if(!F.empty(zcForumBbs.getAtteId())) {
				whereHql += " and t.addUserId = sf.objectById and sf.objectType='FS' and sf.objectId = :atteId ";
				params.put("atteId", zcForumBbs.getAtteId());
			}

		}
		return whereHql;
	}

	@Override
	public void add(ZcForumBbs zcForumBbs) {
		zcForumBbs.setId(jb.absx.UUID.uuid());
		TzcForumBbs t = new TzcForumBbs();
		BeanUtils.copyProperties(zcForumBbs, t);
		zcForumBbsDao.save(t);
	}

	@Override
	public ZcForumBbs get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcForumBbs t = zcForumBbsDao.get("from TzcForumBbs t  where t.id = :id", params);
		ZcForumBbs o = new ZcForumBbs();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcForumBbs zcForumBbs) {
		TzcForumBbs t = zcForumBbsDao.get(TzcForumBbs.class, zcForumBbs.getId());
		String oldCategoryId = t.getCategoryId();
		if (t != null) {
			MyBeanUtils.copyProperties(zcForumBbs, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());

			// 插入日志
			if(!F.empty(zcForumBbs.getLogUserId())) {
				ZcBbsLog bbsLog = new ZcBbsLog();
				bbsLog.setBbsId(zcForumBbs.getId());
				bbsLog.setUserId(zcForumBbs.getLogUserId());
				if(!F.empty(zcForumBbs.getLastUpdateUserId())) {
					bbsLog.setLogType("BL001");
					bbsLog.setContent("【编辑】");
				} else if(!F.empty(zcForumBbs.getBbsStatus())) {
					bbsLog.setLogType("BL002");
					bbsLog.setContent("BS01".equals(zcForumBbs.getBbsStatus()) ? "【打开】" : "【关闭】");
				} else if(zcForumBbs.getIsOffReply() != null) {
					bbsLog.setLogType("BL003");
					bbsLog.setContent(zcForumBbs.getIsOffReply() ? "【关闭回复】" : "【打开回复】");
				} else if(zcForumBbs.getIsTop() != null) {
					bbsLog.setLogType("BL004");
					bbsLog.setContent(zcForumBbs.getIsTop() ? "【置顶】" : "【取消置顶】");
				} else if(!F.empty(zcForumBbs.getCategoryId())) {
					bbsLog.setLogType("BL005");

					ZcCategory oldCategory = zcCategoryService.get(oldCategoryId);
					ZcCategory newCategory = zcCategoryService.get(zcForumBbs.getCategoryId());
					ZcCategory pc = null;
					if(!F.empty(oldCategory.getPid())) {
						pc = zcCategoryService.get(oldCategory.getPid());
					}
					String oldCategoryName = (pc != null ? pc.getName() + " - " : "") + oldCategory.getName();
					pc = null;
					if(!F.empty(newCategory.getPid())) {
						pc = zcCategoryService.get(newCategory.getPid());
					}
					String newCategoryName = (pc != null ? pc.getName() + " - " : "") + newCategory.getName();
					bbsLog.setContent("由【" + oldCategoryName + "】版块移动到【" + newCategoryName + "】版块");
				}

				if(zcForumBbs.getIsEssence() != null) {
					if(zcForumBbs.getIsLight() == null || !zcForumBbs.getIsLight()) {
						bbsLog.setLogType("BL004");
						bbsLog.setContent(zcForumBbs.getIsEssence() ? "【加精】" : "【取消加精】");
					}
				}
				if(zcForumBbs.getIsLight() != null) {
					if(zcForumBbs.getIsEssence() == null || !zcForumBbs.getIsEssence()) {
						bbsLog.setLogType("BL004");
						bbsLog.setContent(zcForumBbs.getIsLight() ? "【加亮】" : "【取消加亮】");
					}
				}
				zcBbsLogService.add(bbsLog);
			}
		}
	}

	@Override
	public void delete(String id) {
		ZcForumBbs zcForumBbs = new ZcForumBbs();
		zcForumBbs.setId(id);
		zcForumBbs.setIsDeleted(true);
		edit(zcForumBbs);
		//zcForumBbsDao.delete(zcForumBbsDao.get(TzcForumBbs.class, id));
	}

	@Override
	public ZcForumBbs addReadAndDetail(String id, String userId) {
		ZcReadRecord read = new ZcReadRecord();
		read.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
		read.setObjectId(id);
		read.setUserId(userId);
		if(zcReadRecordService.get(read) == null) {
			zcReadRecordService.add(read);
		}
		updateCount(id, 1, "bbs_read"); // uv

		return get(id);
	}

	@Override
	public void updateCount(String id, int count, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcForumBbsDao.executeSql("update zc_forum_bbs t set t."+type+" = ifnull(t."+type+", 0) + "+count+" where t.id = :id", params);
	}

	@Override
	public void updateCountByWhere(String where, int count, String type) {
		zcForumBbsDao.executeSql("update zc_forum_bbs t set t."+type+" = ifnull(t."+type+", 0) + "+count+ where);
	}

	@Override
	public int getTextthemeCommentNums(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addUserId", userId);
		Long count = zcForumBbsDao.count("select SUM(t.bbsComment) from TzcForumBbs t where t.isDeleted = 0 and t.addUserId = :addUserId and t.bbsType in ('BT01', 'BT02')", params);
		return count == null ? 0 : count.intValue();
	}

	@Override
	public List<ZcForumBbs> query(ZcForumBbs bbs) {
		List<ZcForumBbs> ol = new ArrayList<ZcForumBbs>();
		String hql = " from TzcForumBbs t ";
		@SuppressWarnings("unchecked")
		List<TzcForumBbs> l = query(hql, bbs, zcForumBbsDao, "addtime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcForumBbs t : l) {
				ZcForumBbs o = new ZcForumBbs();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
