package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcProtectionDaoI;
import jb.model.TzcProtection;
import jb.pageModel.ZcProtection;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcProtectionServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcProtectionServiceImpl extends BaseServiceImpl<ZcProtection> implements ZcProtectionServiceI {

	@Autowired
	private ZcProtectionDaoI zcProtectionDao;

	@Override
	public DataGrid dataGrid(ZcProtection zcProtection, PageHelper ph) {
		List<ZcProtection> ol = new ArrayList<ZcProtection>();
		String hql = " from TzcProtection t ";
		DataGrid dg = dataGridQuery(hql, ph, zcProtection, zcProtectionDao);
		@SuppressWarnings("unchecked")
		List<TzcProtection> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcProtection t : l) {
				ZcProtection o = new ZcProtection();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcProtection zcProtection, Map<String, Object> params) {
		String whereHql = "";	
		if (zcProtection != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcProtection.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcProtection.getUserId());
			}		
			if (!F.empty(zcProtection.getProtectionType())) {
				whereHql += " and t.protectionType = :protectionType";
				params.put("protectionType", zcProtection.getProtectionType());
			}		
			if (!F.empty(zcProtection.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", zcProtection.getReason());
			}		
			if (!F.empty(zcProtection.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcProtection.getPayStatus());
			}		
			if (!F.empty(zcProtection.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcProtection.getAddUserId());
			}		
			if (!F.empty(zcProtection.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcProtection.getUpdateUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcProtection zcProtection) {
		zcProtection.setId(jb.absx.UUID.uuid());
		TzcProtection t = new TzcProtection();
		BeanUtils.copyProperties(zcProtection, t);
		zcProtectionDao.save(t);
	}

	@Override
	public ZcProtection get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcProtection t = zcProtectionDao.get("from TzcProtection t  where t.id = :id", params);
		ZcProtection o = new ZcProtection();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcProtection zcProtection) {
		TzcProtection t = zcProtectionDao.get(TzcProtection.class, zcProtection.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcProtection, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcProtectionDao.delete(zcProtectionDao.get(TzcProtection.class, id));
	}

	@Override
	public List<ZcProtection> query(ZcProtection zcProtection) {
		List<ZcProtection> ol = new ArrayList<ZcProtection>();
		String hql = " from TzcProtection t ";
		@SuppressWarnings("unchecked")
		List<TzcProtection> l = query(hql, zcProtection, zcProtectionDao);
		if (l != null && l.size() > 0) {
			for (TzcProtection t : l) {
				ZcProtection o = new ZcProtection();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcProtection get(ZcProtection zcProtection) {
		String hql = " from TzcProtection t ";
		@SuppressWarnings("unchecked")
		List<TzcProtection> l = query(hql, zcProtection, zcProtectionDao);
		ZcProtection o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcProtection();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
