package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcSysMsgLogDaoI;
import jb.model.TzcSysMsgLog;
import jb.pageModel.ZcSysMsg;
import jb.pageModel.ZcSysMsgLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcSysMsgLogServiceI;

import jb.service.ZcSysMsgServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcSysMsgLogServiceImpl extends BaseServiceImpl<ZcSysMsgLog> implements ZcSysMsgLogServiceI {

	@Autowired
	private ZcSysMsgLogDaoI zcSysMsgLogDao;

	@Autowired
	private ZcSysMsgServiceI zcSysMsgService;

	@Override
	public DataGrid dataGrid(ZcSysMsgLog zcSysMsgLog, PageHelper ph) {
		List<ZcSysMsgLog> ol = new ArrayList<ZcSysMsgLog>();
		String hql = " from TzcSysMsgLog t ";
		DataGrid dg = dataGridQuery(hql, ph, zcSysMsgLog, zcSysMsgLogDao);
		@SuppressWarnings("unchecked")
		List<TzcSysMsgLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcSysMsgLog t : l) {
				ZcSysMsgLog o = new ZcSysMsgLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcSysMsgLog zcSysMsgLog, Map<String, Object> params) {
		String whereHql = "";	
		if (zcSysMsgLog != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcSysMsgLog.getSysMsgId())) {
				whereHql += " and t.sysMsgId = :sysMsgId";
				params.put("sysMsgId", zcSysMsgLog.getSysMsgId());
			}		
			if (!F.empty(zcSysMsgLog.getTitle())) {
				whereHql += " and t.title like :title";
				params.put("title", "%%" + zcSysMsgLog.getTitle() + "%%");
			}
			if (!F.empty(zcSysMsgLog.getContent())) {
				whereHql += " and t.content like :content";
				params.put("content", "%%" + zcSysMsgLog.getContent() + "%%");
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcSysMsgLog zcSysMsgLog) {
		zcSysMsgLog.setId(jb.absx.UUID.uuid());
		TzcSysMsgLog t = new TzcSysMsgLog();
		BeanUtils.copyProperties(zcSysMsgLog, t);
		zcSysMsgLogDao.save(t);
	}

	@Override
	public void addLogAndMsg(ZcSysMsgLog sysMsgLog) {
		ZcSysMsg sysMsg = sysMsgLog.getSysMsg();
		sysMsg.setNewtime(new Date());

		ZcSysMsg exist = new ZcSysMsg();
		exist.setObjectType(sysMsg.getObjectType());
		exist.setObjectId(sysMsg.getObjectId());
		exist.setUserId(sysMsg.getUserId());
		exist = zcSysMsgService.get(exist);
		if(exist == null) {
			zcSysMsgService.add(sysMsg);
		} else {
			sysMsg.setId(exist.getId());
			zcSysMsgService.edit(sysMsg);
		}

		sysMsgLog.setSysMsgId(sysMsg.getId());
		add(sysMsgLog);
	}

	@Override
	public ZcSysMsgLog get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcSysMsgLog t = zcSysMsgLogDao.get("from TzcSysMsgLog t  where t.id = :id", params);
		ZcSysMsgLog o = new ZcSysMsgLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcSysMsgLog zcSysMsgLog) {
		TzcSysMsgLog t = zcSysMsgLogDao.get(TzcSysMsgLog.class, zcSysMsgLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcSysMsgLog, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcSysMsgLogDao.delete(zcSysMsgLogDao.get(TzcSysMsgLog.class, id));
	}

	@Override
	public List<ZcSysMsgLog> query(ZcSysMsgLog zcSysMsgLog) {
		List<ZcSysMsgLog> ol = new ArrayList<ZcSysMsgLog>();
		String hql = " from TzcSysMsgLog t ";
		@SuppressWarnings("unchecked")
		List<TzcSysMsgLog> l = query(hql, zcSysMsgLog, zcSysMsgLogDao);
		if (l != null && l.size() > 0) {
			for (TzcSysMsgLog t : l) {
				ZcSysMsgLog o = new ZcSysMsgLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcSysMsgLog get(ZcSysMsgLog zcSysMsgLog) {
		String hql = " from TzcSysMsgLog t ";
		@SuppressWarnings("unchecked")
		List<TzcSysMsgLog> l = query(hql, zcSysMsgLog, zcSysMsgLogDao);
		ZcSysMsgLog o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcSysMsgLog();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
