package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcCollectDaoI;
import jb.model.TzcCollect;
import jb.pageModel.ZcCollect;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcCollectServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcCollectServiceImpl extends BaseServiceImpl<ZcCollect> implements ZcCollectServiceI {

	@Autowired
	private ZcCollectDaoI zcCollectDao;

	@Override
	public DataGrid dataGrid(ZcCollect zcCollect, PageHelper ph) {
		List<ZcCollect> ol = new ArrayList<ZcCollect>();
		String hql = " from TzcCollect t ";
		DataGrid dg = dataGridQuery(hql, ph, zcCollect, zcCollectDao);
		@SuppressWarnings("unchecked")
		List<TzcCollect> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcCollect t : l) {
				ZcCollect o = new ZcCollect();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcCollect zcCollect, Map<String, Object> params) {
		String whereHql = "";	
		if (zcCollect != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcCollect.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcCollect.getObjectType());
			}		
			if (!F.empty(zcCollect.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcCollect.getObjectId());
			}		
			if (!F.empty(zcCollect.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcCollect.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcCollect zcCollect) {
		zcCollect.setId(jb.absx.UUID.uuid());
		TzcCollect t = new TzcCollect();
		BeanUtils.copyProperties(zcCollect, t);
		zcCollectDao.save(t);
	}

	@Override
	public ZcCollect get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcCollect t = zcCollectDao.get("from TzcCollect t  where t.id = :id", params);
		ZcCollect o = new ZcCollect();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcCollect zcCollect) {
		TzcCollect t = zcCollectDao.get(TzcCollect.class, zcCollect.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcCollect, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcCollectDao.delete(zcCollectDao.get(TzcCollect.class, id));
	}

	@Override
	public List<ZcCollect> query(ZcCollect zcCollect) {
		List<ZcCollect> ol = new ArrayList<ZcCollect>();
		String hql = " from TzcCollect t ";
		@SuppressWarnings("unchecked")
		List<TzcCollect> l = query(hql, zcCollect, zcCollectDao);
		if (l != null && l.size() > 0) {
			for (TzcCollect t : l) {
				ZcCollect o = new ZcCollect();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcCollect get(ZcCollect zcCollect) {
		String hql = " from TzcCollect t ";
		@SuppressWarnings("unchecked")
		List<TzcCollect> l = query(hql, zcCollect, zcCollectDao);
		ZcCollect o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcCollect();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
