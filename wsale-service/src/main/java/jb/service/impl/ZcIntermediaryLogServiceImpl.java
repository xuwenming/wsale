package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcIntermediaryLogDaoI;
import jb.model.TzcIntermediaryLog;
import jb.pageModel.ZcIntermediaryLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcIntermediaryLogServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcIntermediaryLogServiceImpl extends BaseServiceImpl<ZcIntermediaryLog> implements ZcIntermediaryLogServiceI {

	@Autowired
	private ZcIntermediaryLogDaoI zcIntermediaryLogDao;

	@Override
	public DataGrid dataGrid(ZcIntermediaryLog zcIntermediaryLog, PageHelper ph) {
		List<ZcIntermediaryLog> ol = new ArrayList<ZcIntermediaryLog>();
		String hql = " from TzcIntermediaryLog t ";
		DataGrid dg = dataGridQuery(hql, ph, zcIntermediaryLog, zcIntermediaryLogDao);
		@SuppressWarnings("unchecked")
		List<TzcIntermediaryLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcIntermediaryLog t : l) {
				ZcIntermediaryLog o = new ZcIntermediaryLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcIntermediaryLog zcIntermediaryLog, Map<String, Object> params) {
		String whereHql = "";	
		if (zcIntermediaryLog != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcIntermediaryLog.getImId())) {
				whereHql += " and t.imId = :imId";
				params.put("imId", zcIntermediaryLog.getImId());
			}		
			if (!F.empty(zcIntermediaryLog.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcIntermediaryLog.getUserId());
			}		
			if (!F.empty(zcIntermediaryLog.getLogType())) {
				whereHql += " and t.logType = :logType";
				params.put("logType", zcIntermediaryLog.getLogType());
			}		
			if (!F.empty(zcIntermediaryLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", zcIntermediaryLog.getContent());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcIntermediaryLog zcIntermediaryLog) {
		zcIntermediaryLog.setId(jb.absx.UUID.uuid());
		TzcIntermediaryLog t = new TzcIntermediaryLog();
		BeanUtils.copyProperties(zcIntermediaryLog, t);
		zcIntermediaryLogDao.save(t);
	}

	@Override
	public ZcIntermediaryLog get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcIntermediaryLog t = zcIntermediaryLogDao.get("from TzcIntermediaryLog t  where t.id = :id", params);
		ZcIntermediaryLog o = new ZcIntermediaryLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcIntermediaryLog zcIntermediaryLog) {
		TzcIntermediaryLog t = zcIntermediaryLogDao.get(TzcIntermediaryLog.class, zcIntermediaryLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcIntermediaryLog, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcIntermediaryLogDao.delete(zcIntermediaryLogDao.get(TzcIntermediaryLog.class, id));
	}

	@Override
	public List<ZcIntermediaryLog> query(ZcIntermediaryLog zcIntermediaryLog) {
		List<ZcIntermediaryLog> ol = new ArrayList<ZcIntermediaryLog>();
		String hql = " from TzcIntermediaryLog t ";
		@SuppressWarnings("unchecked")
		List<TzcIntermediaryLog> l = query(hql, zcIntermediaryLog, zcIntermediaryLogDao);
		if (l != null && l.size() > 0) {
			for (TzcIntermediaryLog t : l) {
				ZcIntermediaryLog o = new ZcIntermediaryLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcIntermediaryLog get(ZcIntermediaryLog zcIntermediaryLog) {
		String hql = " from TzcIntermediaryLog t ";
		@SuppressWarnings("unchecked")
		List<TzcIntermediaryLog> l = query(hql, zcIntermediaryLog, zcIntermediaryLogDao);
		ZcIntermediaryLog o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcIntermediaryLog();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
