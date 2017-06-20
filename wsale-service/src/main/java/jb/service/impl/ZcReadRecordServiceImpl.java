package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcReadRecordDaoI;
import jb.model.TzcReadRecord;
import jb.pageModel.ZcReadRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcReadRecordServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcReadRecordServiceImpl extends BaseServiceImpl<ZcReadRecord> implements ZcReadRecordServiceI {

	@Autowired
	private ZcReadRecordDaoI zcReadRecordDao;

	@Override
	public DataGrid dataGrid(ZcReadRecord zcReadRecord, PageHelper ph) {
		List<ZcReadRecord> ol = new ArrayList<ZcReadRecord>();
		String hql = " from TzcReadRecord t ";
		DataGrid dg = dataGridQuery(hql, ph, zcReadRecord, zcReadRecordDao);
		@SuppressWarnings("unchecked")
		List<TzcReadRecord> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcReadRecord t : l) {
				ZcReadRecord o = new ZcReadRecord();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcReadRecord zcReadRecord, Map<String, Object> params) {
		String whereHql = "";	
		if (zcReadRecord != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcReadRecord.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcReadRecord.getObjectType());
			}		
			if (!F.empty(zcReadRecord.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcReadRecord.getObjectId());
			}		
			if (!F.empty(zcReadRecord.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcReadRecord.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcReadRecord zcReadRecord) {
		zcReadRecord.setId(jb.absx.UUID.uuid());
		TzcReadRecord t = new TzcReadRecord();
		BeanUtils.copyProperties(zcReadRecord, t);
		zcReadRecordDao.save(t);
	}

	@Override
	public ZcReadRecord get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcReadRecord t = zcReadRecordDao.get("from TzcReadRecord t  where t.id = :id", params);
		ZcReadRecord o = new ZcReadRecord();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcReadRecord zcReadRecord) {
		TzcReadRecord t = zcReadRecordDao.get(TzcReadRecord.class, zcReadRecord.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcReadRecord, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcReadRecordDao.delete(zcReadRecordDao.get(TzcReadRecord.class, id));
	}

	@Override
	public List<ZcReadRecord> query(ZcReadRecord zcReadRecord) {
		List<ZcReadRecord> ol = new ArrayList<ZcReadRecord>();
		String hql = " from TzcReadRecord t ";
		@SuppressWarnings("unchecked")
		List<TzcReadRecord> l = query(hql, zcReadRecord, zcReadRecordDao);
		if (l != null && l.size() > 0) {
			for (TzcReadRecord t : l) {
				ZcReadRecord o = new ZcReadRecord();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcReadRecord get(ZcReadRecord zcReadRecord) {
		String hql = " from TzcReadRecord t ";
		@SuppressWarnings("unchecked")
		List<TzcReadRecord> l = query(hql, zcReadRecord, zcReadRecordDao);
		ZcReadRecord o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcReadRecord();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
