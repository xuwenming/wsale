package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcBbsLogDaoI;
import jb.model.TzcBbsLog;
import jb.pageModel.ZcBbsLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcBbsLogServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcBbsLogServiceImpl extends BaseServiceImpl<ZcBbsLog> implements ZcBbsLogServiceI {

	@Autowired
	private ZcBbsLogDaoI zcBbsLogDao;

	@Override
	public DataGrid dataGrid(ZcBbsLog zcBbsLog, PageHelper ph) {
		List<ZcBbsLog> ol = new ArrayList<ZcBbsLog>();
		String hql = " from TzcBbsLog t ";
		DataGrid dg = dataGridQuery(hql, ph, zcBbsLog, zcBbsLogDao);
		@SuppressWarnings("unchecked")
		List<TzcBbsLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcBbsLog t : l) {
				ZcBbsLog o = new ZcBbsLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcBbsLog zcBbsLog, Map<String, Object> params) {
		String whereHql = "";	
		if (zcBbsLog != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcBbsLog.getLogType())) {
				whereHql += " and t.logType = :logType";
				params.put("logType", zcBbsLog.getLogType());
			}		
			if (!F.empty(zcBbsLog.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcBbsLog.getBbsId());
			}		
			if (!F.empty(zcBbsLog.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcBbsLog.getUserId());
			}		
			if (!F.empty(zcBbsLog.getContent())) {
				whereHql += " and t.content like :content";
				params.put("content", "%%" + zcBbsLog.getContent() + "%%");
			}		
			if (!F.empty(zcBbsLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", zcBbsLog.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcBbsLog zcBbsLog) {
		zcBbsLog.setId(jb.absx.UUID.uuid());
		TzcBbsLog t = new TzcBbsLog();
		BeanUtils.copyProperties(zcBbsLog, t);
		zcBbsLogDao.save(t);
	}

	@Override
	public ZcBbsLog get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcBbsLog t = zcBbsLogDao.get("from TzcBbsLog t  where t.id = :id", params);
		ZcBbsLog o = new ZcBbsLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcBbsLog zcBbsLog) {
		TzcBbsLog t = zcBbsLogDao.get(TzcBbsLog.class, zcBbsLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcBbsLog, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcBbsLogDao.delete(zcBbsLogDao.get(TzcBbsLog.class, id));
	}

	@Override
	public List<ZcBbsLog> query(ZcBbsLog zcBbsLog) {
		List<ZcBbsLog> ol = new ArrayList<ZcBbsLog>();
		String hql = " from TzcBbsLog t ";
		@SuppressWarnings("unchecked")
		List<TzcBbsLog> l = query(hql, zcBbsLog, zcBbsLogDao);
		if (l != null && l.size() > 0) {
			for (TzcBbsLog t : l) {
				ZcBbsLog o = new ZcBbsLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcBbsLog get(ZcBbsLog zcBbsLog) {
		String hql = " from TzcBbsLog t ";
		@SuppressWarnings("unchecked")
		List<TzcBbsLog> l = query(hql, zcBbsLog, zcBbsLogDao);
		ZcBbsLog o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcBbsLog();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
