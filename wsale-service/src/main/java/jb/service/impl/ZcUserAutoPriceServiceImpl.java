package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcUserAutoPriceDaoI;
import jb.model.TzcUserAutoPrice;
import jb.pageModel.ZcUserAutoPrice;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcUserAutoPriceServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcUserAutoPriceServiceImpl extends BaseServiceImpl<ZcUserAutoPrice> implements ZcUserAutoPriceServiceI {

	@Autowired
	private ZcUserAutoPriceDaoI zcUserAutoPriceDao;

	@Override
	public DataGrid dataGrid(ZcUserAutoPrice zcUserAutoPrice, PageHelper ph) {
		List<ZcUserAutoPrice> ol = new ArrayList<ZcUserAutoPrice>();
		String hql = " from TzcUserAutoPrice t ";
		DataGrid dg = dataGridQuery(hql, ph, zcUserAutoPrice, zcUserAutoPriceDao);
		@SuppressWarnings("unchecked")
		List<TzcUserAutoPrice> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcUserAutoPrice t : l) {
				ZcUserAutoPrice o = new ZcUserAutoPrice();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcUserAutoPrice zcUserAutoPrice, Map<String, Object> params) {
		String whereHql = "";	
		if (zcUserAutoPrice != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcUserAutoPrice.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcUserAutoPrice.getUserId());
			}		
			if (!F.empty(zcUserAutoPrice.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcUserAutoPrice.getProductId());
			}
			if (zcUserAutoPrice.getMaxPrice() != null) {
				whereHql += " and t.maxPrice > :maxPrice";
				params.put("maxPrice", zcUserAutoPrice.getMaxPrice());
			}
			if (!F.empty(zcUserAutoPrice.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcUserAutoPrice.getAddUserId());
			}		
			if (!F.empty(zcUserAutoPrice.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcUserAutoPrice.getUpdateUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcUserAutoPrice zcUserAutoPrice) {
		zcUserAutoPrice.setId(jb.absx.UUID.uuid());
		TzcUserAutoPrice t = new TzcUserAutoPrice();
		BeanUtils.copyProperties(zcUserAutoPrice, t);
		zcUserAutoPriceDao.save(t);
	}

	@Override
	public ZcUserAutoPrice get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcUserAutoPrice t = zcUserAutoPriceDao.get("from TzcUserAutoPrice t  where t.id = :id", params);
		ZcUserAutoPrice o = new ZcUserAutoPrice();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcUserAutoPrice zcUserAutoPrice) {
		TzcUserAutoPrice t = zcUserAutoPriceDao.get(TzcUserAutoPrice.class, zcUserAutoPrice.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcUserAutoPrice, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcUserAutoPriceDao.delete(zcUserAutoPriceDao.get(TzcUserAutoPrice.class, id));
	}

	@Override
	public List<ZcUserAutoPrice> query(ZcUserAutoPrice zcUserAutoPrice) {
		List<ZcUserAutoPrice> ol = new ArrayList<ZcUserAutoPrice>();
		String hql = " from TzcUserAutoPrice t ";
		@SuppressWarnings("unchecked")
		List<TzcUserAutoPrice> l = query(hql, zcUserAutoPrice, zcUserAutoPriceDao);
		if (l != null && l.size() > 0) {
			for (TzcUserAutoPrice t : l) {
				ZcUserAutoPrice o = new ZcUserAutoPrice();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcUserAutoPrice get(ZcUserAutoPrice zcUserAutoPrice) {
		String hql = " from TzcUserAutoPrice t ";
		@SuppressWarnings("unchecked")
		List<TzcUserAutoPrice> l = query(hql, zcUserAutoPrice, zcUserAutoPriceDao);
		ZcUserAutoPrice o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcUserAutoPrice();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
