package jb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

public abstract class BaseServiceImpl<T> {
	protected String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}
	
	protected abstract String whereHql(T t, Map<String, Object> params);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected DataGrid dataGridQuery(String hql,PageHelper ph,T t,BaseDaoI dao){
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(t, params);
		List<T> l = dao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(dao.count("select count(*) " + hql + where, params));
		dg.setRows(l);
		return dg;
	}

	protected List query(String hql,T t,BaseDaoI dao){
		return query(hql, t, dao, null, null);
	}

	protected List query(String hql,T t,BaseDaoI dao, String sort, String order){
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(t, params);
		String orderString = "";
		if(sort != null) {
			orderString = " order by t." + sort + " " + (F.empty(order) ? "desc" : order);
		}
		List<T> l = dao.find(hql  + where + orderString, params);
		return l;
	}
}
