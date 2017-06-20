package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcShieldorfansDaoI;
import jb.model.TzcShieldorfans;
import jb.model.TzcShop;
import jb.pageModel.ZcShieldorfans;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcShop;
import jb.service.ZcShieldorfansServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcShieldorfansServiceImpl extends BaseServiceImpl<ZcShieldorfans> implements ZcShieldorfansServiceI {

	@Autowired
	private ZcShieldorfansDaoI zcShieldorfansDao;

	@Override
	public DataGrid dataGrid(ZcShieldorfans zcShieldorfans, PageHelper ph) {
		List<ZcShieldorfans> ol = new ArrayList<ZcShieldorfans>();
		String hql = " from TzcShieldorfans t ";
		DataGrid dg = dataGridQuery(hql, ph, zcShieldorfans, zcShieldorfansDao);
		@SuppressWarnings("unchecked")
		List<TzcShieldorfans> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcShieldorfans t : l) {
				ZcShieldorfans o = new ZcShieldorfans();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcShieldorfans zcShieldorfans, Map<String, Object> params) {
		String whereHql = "";	
		if (zcShieldorfans != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcShieldorfans.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcShieldorfans.getObjectType());
			}		
			if (!F.empty(zcShieldorfans.getObjectById())) {
				whereHql += " and t.objectById = :objectById";
				params.put("objectById", zcShieldorfans.getObjectById());
			}		
			if (!F.empty(zcShieldorfans.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcShieldorfans.getObjectId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcShieldorfans zcShieldorfans) {
		TzcShieldorfans t = new TzcShieldorfans();
		BeanUtils.copyProperties(zcShieldorfans, t);
		t.setId(jb.absx.UUID.uuid());
		//t.setCreatedatetime(new Date());
		zcShieldorfansDao.save(t);
	}

	@Override
	public ZcShieldorfans get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcShieldorfans t = zcShieldorfansDao.get("from TzcShieldorfans t  where t.id = :id", params);
		ZcShieldorfans o = new ZcShieldorfans();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcShieldorfans zcShieldorfans) {
		TzcShieldorfans t = zcShieldorfansDao.get(TzcShieldorfans.class, zcShieldorfans.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcShieldorfans, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		zcShieldorfansDao.delete(zcShieldorfansDao.get(TzcShieldorfans.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public ZcShieldorfans get(ZcShieldorfans shieldorfans) {
		String hql = " from TzcShieldorfans t ";
		List<TzcShieldorfans> l = query(hql, shieldorfans, zcShieldorfansDao);
		ZcShieldorfans o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcShieldorfans();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public List<ZcShieldorfans> query(ZcShieldorfans shieldorfans) {
		List<ZcShieldorfans> ol = new ArrayList<ZcShieldorfans>();
		String hql = " from TzcShieldorfans t ";
		@SuppressWarnings("unchecked")
		List<TzcShieldorfans> l = query(hql, shieldorfans, zcShieldorfansDao);
		if (l != null && l.size() > 0) {
			for (TzcShieldorfans t : l) {
				ZcShieldorfans o = new ZcShieldorfans();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
