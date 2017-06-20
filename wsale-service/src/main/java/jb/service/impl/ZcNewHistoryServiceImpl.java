package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcNewHistoryDaoI;
import jb.model.TzcNewHistory;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcNewHistory;
import jb.service.ZcNewHistoryServiceI;
import jb.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ZcNewHistoryServiceImpl extends BaseServiceImpl<ZcNewHistory> implements ZcNewHistoryServiceI {

	@Autowired
	private ZcNewHistoryDaoI zcNewHistoryDao;

	@Override
	public DataGrid dataGrid(ZcNewHistory zcNewHistory, PageHelper ph) {
		List<ZcNewHistory> ol = new ArrayList<ZcNewHistory>();
		String hql = " from TzcNewHistory t ";
		DataGrid dg = dataGridQuery(hql, ph, zcNewHistory, zcNewHistoryDao);
		@SuppressWarnings("unchecked")
		List<TzcNewHistory> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcNewHistory t : l) {
				ZcNewHistory o = new ZcNewHistory();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcNewHistory zcNewHistory, Map<String, Object> params) {
		String whereHql = "";	
		if (zcNewHistory != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcNewHistory.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", zcNewHistory.getId());
			}		
			if (!F.empty(zcNewHistory.getOpenid())) {
				whereHql += " and t.openid = :openid";
				params.put("openid", zcNewHistory.getOpenid());
			}		
			if (!F.empty(zcNewHistory.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcNewHistory.getUserId());
			}		
			if (!F.empty(zcNewHistory.getProductIds())) {
				whereHql += " and t.productIds = :productIds";
				params.put("productIds", zcNewHistory.getProductIds());
			}
			if(zcNewHistory.getSameDay() != null && zcNewHistory.getSameDay()) {
				whereHql += " and to_days(t.addtime) = to_days(now())";
			}
			if (zcNewHistory.getIsRead() != null) {
				whereHql += " and t.isRead = :isRead";
				params.put("isRead", zcNewHistory.getIsRead());
			}
		}
		return whereHql;
	}

	@Override
	public void add(ZcNewHistory zcNewHistory) {
		zcNewHistory.setId(jb.absx.UUID.uuid());
		if(zcNewHistory.getAddtime() == null)
			zcNewHistory.setAddtime(new Date());
		TzcNewHistory t = new TzcNewHistory();
		BeanUtils.copyProperties(zcNewHistory, t);
		zcNewHistoryDao.save(t);
	}

	@Override
	public ZcNewHistory get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcNewHistory t = zcNewHistoryDao.get("from TzcNewHistory t  where t.id = :id", params);
		ZcNewHistory o = new ZcNewHistory();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcNewHistory zcNewHistory) {
		TzcNewHistory t = zcNewHistoryDao.get(TzcNewHistory.class, zcNewHistory.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcNewHistory, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void updateReaded(String openid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openid);
		zcNewHistoryDao.executeSql("update zc_new_history t set t.is_read = 1, t.updatetime = now() where t.is_read = 0 and t.openid = :openid", params);
	}

	@Override
	public int count(ZcNewHistory zcNewHistory) {
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcNewHistory, params);
		Long count = zcNewHistoryDao.count("select count(*) from TzcNewHistory t " + where, params);
		return count == null ? 0 : count.intValue();
	}

	@Override
	public void delete(String id) {
		zcNewHistoryDao.delete(zcNewHistoryDao.get(TzcNewHistory.class, id));
	}

	@Override
	public List<ZcNewHistory> query(ZcNewHistory zcNewHistory) {
		List<ZcNewHistory> ol = new ArrayList<ZcNewHistory>();
		String hql = " from TzcNewHistory t ";
		@SuppressWarnings("unchecked")
		List<TzcNewHistory> l = query(hql, zcNewHistory, zcNewHistoryDao);
		if (l != null && l.size() > 0) {
			for (TzcNewHistory t : l) {
				ZcNewHistory o = new ZcNewHistory();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcNewHistory get(ZcNewHistory zcNewHistory) {
		String hql = " from TzcNewHistory t ";
		@SuppressWarnings("unchecked")
		List<TzcNewHistory> l = query(hql, zcNewHistory, zcNewHistoryDao);
		ZcNewHistory o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcNewHistory();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
