package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcLastViewLogDaoI;
import jb.model.TzcLastViewLog;
import jb.pageModel.ZcLastViewLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcLastViewLogServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcLastViewLogServiceImpl extends BaseServiceImpl<ZcLastViewLog> implements ZcLastViewLogServiceI {

	@Autowired
	private ZcLastViewLogDaoI zcLastViewLogDao;

	@Override
	public DataGrid dataGrid(ZcLastViewLog zcLastViewLog, PageHelper ph) {
		List<ZcLastViewLog> ol = new ArrayList<ZcLastViewLog>();
		String hql = " from TzcLastViewLog t ";
		DataGrid dg = dataGridQuery(hql, ph, zcLastViewLog, zcLastViewLogDao);
		@SuppressWarnings("unchecked")
		List<TzcLastViewLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcLastViewLog t : l) {
				ZcLastViewLog o = new ZcLastViewLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcLastViewLog zcLastViewLog, Map<String, Object> params) {
		String whereHql = "";	
		if (zcLastViewLog != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcLastViewLog.getBusinessType())) {
				whereHql += " and t.businessType = :businessType";
				params.put("businessType", zcLastViewLog.getBusinessType());
			}
			if (!F.empty(zcLastViewLog.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcLastViewLog.getObjectType());
			}
			if (!F.empty(zcLastViewLog.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcLastViewLog.getObjectId());
			}		
			if (!F.empty(zcLastViewLog.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcLastViewLog.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcLastViewLog zcLastViewLog) {
		zcLastViewLog.setId(jb.absx.UUID.uuid());
		TzcLastViewLog t = new TzcLastViewLog();
		BeanUtils.copyProperties(zcLastViewLog, t);
		zcLastViewLogDao.save(t);
	}

	@Override
	public ZcLastViewLog get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcLastViewLog t = zcLastViewLogDao.get("from TzcLastViewLog t  where t.id = :id", params);
		ZcLastViewLog o = new ZcLastViewLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcLastViewLog zcLastViewLog) {
		TzcLastViewLog t = zcLastViewLogDao.get(TzcLastViewLog.class, zcLastViewLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcLastViewLog, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcLastViewLogDao.delete(zcLastViewLogDao.get(TzcLastViewLog.class, id));
	}

	@Override
	public List<ZcLastViewLog> query(ZcLastViewLog zcLastViewLog) {
		List<ZcLastViewLog> ol = new ArrayList<ZcLastViewLog>();
		String hql = " from TzcLastViewLog t ";
		@SuppressWarnings("unchecked")
		List<TzcLastViewLog> l = query(hql, zcLastViewLog, zcLastViewLogDao);
		if (l != null && l.size() > 0) {
			for (TzcLastViewLog t : l) {
				ZcLastViewLog o = new ZcLastViewLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcLastViewLog get(ZcLastViewLog zcLastViewLog) {
		String hql = " from TzcLastViewLog t ";
		@SuppressWarnings("unchecked")
		List<TzcLastViewLog> l = query(hql, zcLastViewLog, zcLastViewLogDao);
		ZcLastViewLog o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcLastViewLog();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
