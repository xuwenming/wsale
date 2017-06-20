package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcProductMarginDaoI;
import jb.model.TzcProductMargin;
import jb.pageModel.ZcProductMargin;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcProductMarginServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcProductMarginServiceImpl extends BaseServiceImpl<ZcProductMargin> implements ZcProductMarginServiceI {

	@Autowired
	private ZcProductMarginDaoI zcProductMarginDao;

	@Override
	public DataGrid dataGrid(ZcProductMargin zcProductMargin, PageHelper ph) {
		List<ZcProductMargin> ol = new ArrayList<ZcProductMargin>();
		String hql = " from TzcProductMargin t ";
		DataGrid dg = dataGridQuery(hql, ph, zcProductMargin, zcProductMarginDao);
		@SuppressWarnings("unchecked")
		List<TzcProductMargin> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcProductMargin t : l) {
				ZcProductMargin o = new ZcProductMargin();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcProductMargin zcProductMargin, Map<String, Object> params) {
		String whereHql = "";	
		if (zcProductMargin != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcProductMargin.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcProductMargin.getProductId());
			}		
			if (!F.empty(zcProductMargin.getBuyUserId())) {
				whereHql += " and t.buyUserId = :buyUserId";
				params.put("buyUserId", zcProductMargin.getBuyUserId());
			}		
			if (!F.empty(zcProductMargin.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcProductMargin.getPayStatus());
			}		
			if (!F.empty(zcProductMargin.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcProductMargin.getAddUserId());
			}		
			if (!F.empty(zcProductMargin.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcProductMargin.getUpdateUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcProductMargin zcProductMargin) {
		zcProductMargin.setId(jb.absx.UUID.uuid());
		TzcProductMargin t = new TzcProductMargin();
		BeanUtils.copyProperties(zcProductMargin, t);
		zcProductMarginDao.save(t);
	}

	@Override
	public ZcProductMargin get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcProductMargin t = zcProductMarginDao.get("from TzcProductMargin t  where t.id = :id", params);
		ZcProductMargin o = new ZcProductMargin();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcProductMargin zcProductMargin) {
		TzcProductMargin t = zcProductMarginDao.get(TzcProductMargin.class, zcProductMargin.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcProductMargin, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcProductMarginDao.delete(zcProductMarginDao.get(TzcProductMargin.class, id));
	}

	@Override
	public List<ZcProductMargin> query(ZcProductMargin zcProductMargin) {
		List<ZcProductMargin> ol = new ArrayList<ZcProductMargin>();
		String hql = " from TzcProductMargin t ";
		@SuppressWarnings("unchecked")
		List<TzcProductMargin> l = query(hql, zcProductMargin, zcProductMarginDao);
		if (l != null && l.size() > 0) {
			for (TzcProductMargin t : l) {
				ZcProductMargin o = new ZcProductMargin();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcProductMargin get(ZcProductMargin zcProductMargin) {
		String hql = " from TzcProductMargin t ";
		@SuppressWarnings("unchecked")
		List<TzcProductMargin> l = query(hql, zcProductMargin, zcProductMarginDao);
		ZcProductMargin o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcProductMargin();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
