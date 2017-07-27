package jb.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcCategoryDaoI;
import jb.model.TzcCategory;
import jb.model.TzcReport;
import jb.pageModel.ZcCategory;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcReport;
import jb.service.ZcCategoryServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcCategoryServiceImpl extends BaseServiceImpl<ZcCategory> implements ZcCategoryServiceI {

	@Autowired
	private ZcCategoryDaoI zcCategoryDao;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Override
	public DataGrid dataGrid(ZcCategory zcCategory, PageHelper ph) {
		List<ZcCategory> ol = new ArrayList<ZcCategory>();
		String hql = " from TzcCategory t ";
		DataGrid dg = dataGridQuery(hql, ph, zcCategory, zcCategoryDao);
		@SuppressWarnings("unchecked")
		List<TzcCategory> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcCategory t : l) {
				ZcCategory o = new ZcCategory();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcCategory zcCategory, Map<String, Object> params) {
		String whereHql = "";	
		if (zcCategory != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcCategory.getName())) {
				whereHql += " and t.name like :name";
				params.put("name", "%%" + zcCategory.getName() + "%%");
			}		
			if (!F.empty(zcCategory.getIcon())) {
				whereHql += " and t.icon = :icon";
				params.put("icon", zcCategory.getIcon());
			}		
			if (!F.empty(zcCategory.getPid())) {
				whereHql += " and t.pid = :pid";
				params.put("pid", zcCategory.getPid());
			}		
			if (!F.empty(zcCategory.getForumIntroduce())) {
				whereHql += " and t.forumIntroduce = :forumIntroduce";
				params.put("forumIntroduce", zcCategory.getForumIntroduce());
			}		
			if (!F.empty(zcCategory.getChiefModeratorId())) {
				whereHql += " and t.chiefModeratorId = :chiefModeratorId";
				params.put("chiefModeratorId", zcCategory.getChiefModeratorId());
			}		
			if (!F.empty(zcCategory.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcCategory.getAddUserId());
			}		
			if (!F.empty(zcCategory.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcCategory.getUpdateUserId());
			}
			if (zcCategory.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcCategory.getIsDeleted());
			}

			if(zcCategory.getHotSeq() != null) {
				if(zcCategory.getHotSeq() == 0) {
					whereHql += " and t.hotSeq = 0";
				} else {
					whereHql += " and t.hotSeq >= :hotSeq and t.pid != 0";
					params.put("hotSeq", zcCategory.getHotSeq());
				}
			}
		}
		return whereHql;
	}

	@Override
	public void add(ZcCategory zcCategory) {
		zcCategory.setId(jb.absx.UUID.uuid());
		TzcCategory t = new TzcCategory();
		BeanUtils.copyProperties(zcCategory, t);

		if(!F.empty(zcCategory.getChiefModeratorId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", zcCategory.getChiefModeratorId());
			params.put("roleId", "sxbz"); // 首席版主
			params.put("categoryId", zcCategory.getId());
			BigInteger count = zcCategoryDao.countBySql("select count(*) from tuser_trole t where t.TUSER_ID = :userId and t.TROLE_ID = :roleId and t.category_id = :categoryId", params);
			if(count == null || count.intValue() == 0)  {
				zcCategoryDao.executeSql("insert into tuser_trole (TUSER_ID, TROLE_ID, category_id) values (:userId, :roleId, :categoryId)", params);
			}

			// 推送首席版主通知
			sendWxMessage.sendCustomMessageByUserId(zcCategory.getChiefModeratorId(), "恭喜您晋升为集东集西的首席版主，成为我们管理团队的一员。我们非常期待与你一起愉快的合作，同时欢迎您为网站建设谏言献策。");
		}

		zcCategoryDao.save(t);
	}

	@Override
	public ZcCategory get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcCategory t = zcCategoryDao.get("from TzcCategory t  where t.id = :id", params);
		ZcCategory o = new ZcCategory();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcCategory zcCategory) {
		TzcCategory t = zcCategoryDao.get(TzcCategory.class, zcCategory.getId());
		if (t != null) {
			String oldChiefModeratorId = t.getChiefModeratorId();
			MyBeanUtils.copyProperties(zcCategory, t, new String[] { "id" , "createdatetime" },true);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", zcCategory.getChiefModeratorId());
			params.put("roleId", "sxbz"); // 首席版主
			params.put("categoryId", zcCategory.getId());
			if(!F.empty(zcCategory.getChiefModeratorId())) {
				BigInteger count = zcCategoryDao.countBySql("select count(*) from tuser_trole t where t.TUSER_ID = :userId and t.TROLE_ID = :roleId and t.category_id = :categoryId", params);
				if(count == null || count.intValue() == 0)  {
					zcCategoryDao.executeSql("insert into tuser_trole (TUSER_ID, TROLE_ID, category_id) values (:userId, :roleId, :categoryId)", params);
				}
				if(!F.empty(oldChiefModeratorId) && !oldChiefModeratorId.equals(zcCategory.getChiefModeratorId())) {
					params.put("userId", oldChiefModeratorId);
					zcCategoryDao.executeSql("delete from tuser_trole where TUSER_ID = :userId and TROLE_ID = :roleId and category_id = :categoryId", params);

					// 推送首席版主通知
					sendWxMessage.sendCustomMessageByUserId(zcCategory.getChiefModeratorId(), "恭喜您晋升为集东集西的首席版主，成为我们管理团队的一员。我们非常期待与你一起愉快的合作，同时欢迎您为网站建设谏言献策。");
				}
			} else {
				if(!F.empty(oldChiefModeratorId)) {
					params.put("userId", oldChiefModeratorId);
					zcCategoryDao.executeSql("delete from tuser_trole where TUSER_ID = :userId and TROLE_ID = :roleId and category_id = :categoryId", params);
				}
			}
		}
	}

	@Override
	public void delete(ZcCategory zcCategory) {
		//zcCategoryDao.delete(zcCategoryDao.get(TzcCategory.class, id));
		zcCategory.setIsDeleted(true);
		TzcCategory t = zcCategoryDao.get(TzcCategory.class, zcCategory.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcCategory, t, new String[] { "id" , "addtime" },true);
			//t.setModifydatetime(new Date());
			zcCategoryDao.executeSql("update zc_category set isDeleted = 1 where pid = '" + zcCategory.getId() + "'");

			// 删除对应的首席版主角色
			zcCategoryDao.executeSql("delete from tuser_trole where TROLE_ID = 'sxbz' and category_id = '"+zcCategory.getId()+"'");
			zcCategoryDao.executeSql("delete from tuser_trole where TROLE_ID = 'sxbz' and category_id in (select id from zc_category c where c.pid = '"+zcCategory.getId()+"')");
		}

	}

	@Override
	public List<ZcCategory> query(ZcCategory r) {
		List<ZcCategory> ol = new ArrayList<ZcCategory>();
		String hql = " from TzcCategory t ";
		List<TzcCategory> l = query(hql, r, zcCategoryDao, "seq", "asc");
		if (l != null && l.size() > 0) {
			for (TzcCategory t : l) {
				ZcCategory o = new ZcCategory();
				BeanUtils.copyProperties(t, o);
				if(F.empty(o.getPid()) || "0".equals(o.getPid())) {
					o.setState("closed");
				}
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public List<ZcCategory> queryHot(ZcCategory r) {
		List<ZcCategory> ol = new ArrayList<ZcCategory>();
		String hql = " from TzcCategory t ";
		List<TzcCategory> l = query(hql, r, zcCategoryDao, "hotSeq desc, t.seq", "asc");
		if (l != null && l.size() > 0) {
			for (TzcCategory t : l) {
				ZcCategory o = new ZcCategory();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}
}
