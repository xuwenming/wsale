package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcPraiseDaoI;
import jb.model.TzcPraise;
import jb.pageModel.ZcPraise;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcPraiseServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcPraiseServiceImpl extends BaseServiceImpl<ZcPraise> implements ZcPraiseServiceI {

	@Autowired
	private ZcPraiseDaoI zcPraiseDao;

	@Override
	public DataGrid dataGrid(ZcPraise zcPraise, PageHelper ph) {
		List<ZcPraise> ol = new ArrayList<ZcPraise>();
		String hql = " from TzcPraise t ";
		DataGrid dg = dataGridQuery(hql, ph, zcPraise, zcPraiseDao);
		@SuppressWarnings("unchecked")
		List<TzcPraise> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcPraise t : l) {
				ZcPraise o = new ZcPraise();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcPraise zcPraise, Map<String, Object> params) {
		String whereHql = "";	
		if (zcPraise != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcPraise.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcPraise.getObjectType());
			}		
			if (!F.empty(zcPraise.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcPraise.getObjectId());
			}		
			if (!F.empty(zcPraise.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcPraise.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcPraise zcPraise) {
		zcPraise.setId(jb.absx.UUID.uuid());
		TzcPraise t = new TzcPraise();
		BeanUtils.copyProperties(zcPraise, t);
		zcPraiseDao.save(t);
	}

	@Override
	public ZcPraise get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcPraise t = zcPraiseDao.get("from TzcPraise t  where t.id = :id", params);
		ZcPraise o = new ZcPraise();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcPraise zcPraise) {
		TzcPraise t = zcPraiseDao.get(TzcPraise.class, zcPraise.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcPraise, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcPraiseDao.delete(zcPraiseDao.get(TzcPraise.class, id));
	}

	@Override
	public List<ZcPraise> query(ZcPraise zcPraise) {
		List<ZcPraise> ol = new ArrayList<ZcPraise>();
		String hql = " from TzcPraise t ";
		@SuppressWarnings("unchecked")
		List<TzcPraise> l = query(hql, zcPraise, zcPraiseDao);
		if (l != null && l.size() > 0) {
			for (TzcPraise t : l) {
				ZcPraise o = new ZcPraise();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcPraise get(ZcPraise zcPraise) {
		String hql = " from TzcPraise t ";
		@SuppressWarnings("unchecked")
		List<TzcPraise> l = query(hql, zcPraise, zcPraiseDao);
		ZcPraise o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcPraise();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
