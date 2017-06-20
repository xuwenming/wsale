package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcProductRangeDaoI;
import jb.model.TzcFile;
import jb.model.TzcProductRange;
import jb.pageModel.*;
import jb.service.ZcProductRangeServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcProductRangeServiceImpl extends BaseServiceImpl<ZcProductRange> implements ZcProductRangeServiceI {

	@Autowired
	private ZcProductRangeDaoI zcProductRangeDao;

	@Override
	public DataGrid dataGrid(ZcProductRange zcProductRange, PageHelper ph) {
		List<ZcProductRange> ol = new ArrayList<ZcProductRange>();
		String hql = " from TzcProductRange t ";
		DataGrid dg = dataGridQuery(hql, ph, zcProductRange, zcProductRangeDao);
		@SuppressWarnings("unchecked")
		List<TzcProductRange> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcProductRange t : l) {
				ZcProductRange o = new ZcProductRange();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcProductRange zcProductRange, Map<String, Object> params) {
		String whereHql = "";	
		if (zcProductRange != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcProductRange.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcProductRange.getProductId());
			}
			if (!F.empty(zcProductRange.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcProductRange.getAddUserId());
			}
		}
		return whereHql;
	}

	@Override
	public void add(ZcProductRange zcProductRange) {
		TzcProductRange t = new TzcProductRange();
		BeanUtils.copyProperties(zcProductRange, t);
		t.setId(jb.absx.UUID.uuid());
		//t.setCreatedatetime(new Date());
		zcProductRangeDao.save(t);
	}

	@Override
	public ZcProductRange get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcProductRange t = zcProductRangeDao.get("from TzcProductRange t  where t.id = :id", params);
		ZcProductRange o = new ZcProductRange();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcProductRange zcProductRange) {
		TzcProductRange t = zcProductRangeDao.get(TzcProductRange.class, zcProductRange.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcProductRange, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		zcProductRangeDao.delete(zcProductRangeDao.get(TzcProductRange.class, id));
	}

	@Override
	public void delete(ZcProductRange zcProductRange) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", zcProductRange.getProductId());
		zcProductRangeDao.executeHql("delete from TzcProductRange where productId = :productId", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZcProductRange> query(ZcProductRange zcProductRange) {
		List<ZcProductRange> ol = new ArrayList<ZcProductRange>();
		String hql = " from TzcProductRange t ";
		List<TzcProductRange> l = query(hql, zcProductRange, zcProductRangeDao, "startPrice", "asc");
		if (l != null && l.size() > 0) {
			for (TzcProductRange t : l) {
				ZcProductRange o = new ZcProductRange();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcProductRange getLastByUserId(String userId) {
		ZcProductRange q = new ZcProductRange();
		q.setAddUserId(userId);
		String hql = " from TzcProductRange t ";
		List<TzcProductRange> l = query(hql, q, zcProductRangeDao, "addtime", "desc");
		ZcProductRange o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcProductRange();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public double getRangePrice(String productId, double currentPrice) {
		ZcProductRange q = new ZcProductRange();
		q.setProductId(productId);
		List<ZcProductRange> ranges = query(q);
		if(CollectionUtils.isEmpty(ranges)) {
			q.setProductId("-1");
			ranges = query(q);
		}
		double rangePrice = 0;
		for(ZcProductRange range : ranges) {
			if(currentPrice >=range.getStartPrice() && currentPrice <= range.getEndPrice()) {
				rangePrice = range.getPrice();
				break;
			}
		}

		return rangePrice;
	}

}
