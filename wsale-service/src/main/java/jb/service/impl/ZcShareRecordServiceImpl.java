package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcShareRecordDaoI;
import jb.model.TzcShareRecord;
import jb.pageModel.ZcShareRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcShareRecordServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcShareRecordServiceImpl extends BaseServiceImpl<ZcShareRecord> implements ZcShareRecordServiceI {

	@Autowired
	private ZcShareRecordDaoI zcShareRecordDao;

	@Override
	public DataGrid dataGrid(ZcShareRecord zcShareRecord, PageHelper ph) {
		List<ZcShareRecord> ol = new ArrayList<ZcShareRecord>();
		String hql = " from TzcShareRecord t ";
		DataGrid dg = dataGridQuery(hql, ph, zcShareRecord, zcShareRecordDao);
		@SuppressWarnings("unchecked")
		List<TzcShareRecord> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcShareRecord t : l) {
				ZcShareRecord o = new ZcShareRecord();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcShareRecord zcShareRecord, Map<String, Object> params) {
		String whereHql = "";	
		if (zcShareRecord != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcShareRecord.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcShareRecord.getBbsId());
			}		
			if (!F.empty(zcShareRecord.getShareChannel())) {
				whereHql += " and t.shareChannel = :shareChannel";
				params.put("shareChannel", zcShareRecord.getShareChannel());
			}		
			if (!F.empty(zcShareRecord.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcShareRecord.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcShareRecord zcShareRecord) {
		zcShareRecord.setId(jb.absx.UUID.uuid());
		TzcShareRecord t = new TzcShareRecord();
		BeanUtils.copyProperties(zcShareRecord, t);
		zcShareRecordDao.save(t);
	}

	@Override
	public ZcShareRecord get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcShareRecord t = zcShareRecordDao.get("from TzcShareRecord t  where t.id = :id", params);
		ZcShareRecord o = new ZcShareRecord();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcShareRecord zcShareRecord) {
		TzcShareRecord t = zcShareRecordDao.get(TzcShareRecord.class, zcShareRecord.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcShareRecord, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcShareRecordDao.delete(zcShareRecordDao.get(TzcShareRecord.class, id));
	}

	@Override
	public List<ZcShareRecord> query(ZcShareRecord zcShareRecord) {
		List<ZcShareRecord> ol = new ArrayList<ZcShareRecord>();
		String hql = " from TzcShareRecord t ";
		@SuppressWarnings("unchecked")
		List<TzcShareRecord> l = query(hql, zcShareRecord, zcShareRecordDao);
		if (l != null && l.size() > 0) {
			for (TzcShareRecord t : l) {
				ZcShareRecord o = new ZcShareRecord();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcShareRecord get(ZcShareRecord zcShareRecord) {
		String hql = " from TzcShareRecord t ";
		@SuppressWarnings("unchecked")
		List<TzcShareRecord> l = query(hql, zcShareRecord, zcShareRecordDao);
		ZcShareRecord o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcShareRecord();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
