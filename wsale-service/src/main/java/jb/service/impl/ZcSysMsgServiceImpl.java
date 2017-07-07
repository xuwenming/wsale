package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcSysMsgDaoI;
import jb.model.TzcSysMsg;
import jb.pageModel.ZcSysMsg;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcSysMsgServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcSysMsgServiceImpl extends BaseServiceImpl<ZcSysMsg> implements ZcSysMsgServiceI {

	@Autowired
	private ZcSysMsgDaoI zcSysMsgDao;

	@Override
	public DataGrid dataGrid(ZcSysMsg zcSysMsg, PageHelper ph) {
		List<ZcSysMsg> ol = new ArrayList<ZcSysMsg>();
		String hql = " from TzcSysMsg t ";
		DataGrid dg = dataGridQuery(hql, ph, zcSysMsg, zcSysMsgDao);
		@SuppressWarnings("unchecked")
		List<TzcSysMsg> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcSysMsg t : l) {
				ZcSysMsg o = new ZcSysMsg();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcSysMsg zcSysMsg, Map<String, Object> params) {
		String whereHql = "";	
		if (zcSysMsg != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcSysMsg.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcSysMsg.getObjectType());
			}		
			if (!F.empty(zcSysMsg.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcSysMsg.getObjectId());
			}		
			if (!F.empty(zcSysMsg.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcSysMsg.getUserId());
			}		
			if (!F.empty(zcSysMsg.getMtype())) {
				whereHql += " and t.mtype = :mtype";
				params.put("mtype", zcSysMsg.getMtype());
			}		
			if (!F.empty(zcSysMsg.getTimeUnit())) {
				whereHql += " and t.timeUnit = :timeUnit";
				params.put("timeUnit", zcSysMsg.getTimeUnit());
			}		
			if (!F.empty(zcSysMsg.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", zcSysMsg.getContent());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcSysMsg zcSysMsg) {
		zcSysMsg.setId(jb.absx.UUID.uuid());
		TzcSysMsg t = new TzcSysMsg();
		BeanUtils.copyProperties(zcSysMsg, t);
		zcSysMsgDao.save(t);
	}

	@Override
	public ZcSysMsg get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcSysMsg t = zcSysMsgDao.get("from TzcSysMsg t  where t.id = :id", params);
		ZcSysMsg o = new ZcSysMsg();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcSysMsg zcSysMsg) {
		TzcSysMsg t = zcSysMsgDao.get(TzcSysMsg.class, zcSysMsg.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcSysMsg, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcSysMsgDao.delete(zcSysMsgDao.get(TzcSysMsg.class, id));
	}

	@Override
	public List<ZcSysMsg> query(ZcSysMsg zcSysMsg) {
		List<ZcSysMsg> ol = new ArrayList<ZcSysMsg>();
		String hql = " from TzcSysMsg t ";
		@SuppressWarnings("unchecked")
		List<TzcSysMsg> l = query(hql, zcSysMsg, zcSysMsgDao);
		if (l != null && l.size() > 0) {
			for (TzcSysMsg t : l) {
				ZcSysMsg o = new ZcSysMsg();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcSysMsg get(ZcSysMsg zcSysMsg) {
		String hql = " from TzcSysMsg t ";
		@SuppressWarnings("unchecked")
		List<TzcSysMsg> l = query(hql, zcSysMsg, zcSysMsgDao);
		ZcSysMsg o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcSysMsg();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
