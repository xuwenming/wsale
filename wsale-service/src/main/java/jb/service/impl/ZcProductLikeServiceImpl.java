package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcProductLikeDaoI;
import jb.model.TzcProductLike;
import jb.pageModel.ZcProductLike;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcProductLikeServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcProductLikeServiceImpl extends BaseServiceImpl<ZcProductLike> implements ZcProductLikeServiceI {

	@Autowired
	private ZcProductLikeDaoI zcProductLikeDao;

	@Override
	public DataGrid dataGrid(ZcProductLike zcProductLike, PageHelper ph) {
		List<ZcProductLike> ol = new ArrayList<ZcProductLike>();
		String hql = " from TzcProductLike t ";
		DataGrid dg = dataGridQuery(hql, ph, zcProductLike, zcProductLikeDao);
		@SuppressWarnings("unchecked")
		List<TzcProductLike> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcProductLike t : l) {
				ZcProductLike o = new ZcProductLike();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcProductLike zcProductLike, Map<String, Object> params) {
		String whereHql = "";	
		if (zcProductLike != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcProductLike.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcProductLike.getProductId());
			}		
			if (!F.empty(zcProductLike.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcProductLike.getUserId());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(ZcProductLike zcProductLike) {
		zcProductLike.setId(jb.absx.UUID.uuid());
		TzcProductLike t = new TzcProductLike();
		BeanUtils.copyProperties(zcProductLike, t);
		zcProductLikeDao.save(t);
	}

	@Override
	public ZcProductLike get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcProductLike t = zcProductLikeDao.get("from TzcProductLike t  where t.id = :id", params);
		ZcProductLike o = new ZcProductLike();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcProductLike zcProductLike) {
		TzcProductLike t = zcProductLikeDao.get(TzcProductLike.class, zcProductLike.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcProductLike, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcProductLikeDao.delete(zcProductLikeDao.get(TzcProductLike.class, id));
	}

	@Override
	public List<ZcProductLike> query(ZcProductLike zcProductLike) {
		List<ZcProductLike> ol = new ArrayList<ZcProductLike>();
		String hql = " from TzcProductLike t ";
		@SuppressWarnings("unchecked")
		List<TzcProductLike> l = query(hql, zcProductLike, zcProductLikeDao, "addtime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcProductLike t : l) {
				ZcProductLike o = new ZcProductLike();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcProductLike get(ZcProductLike zcProductLike) {
		String hql = " from TzcProductLike t ";
		@SuppressWarnings("unchecked")
		List<TzcProductLike> l = query(hql, zcProductLike, zcProductLikeDao);
		ZcProductLike o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcProductLike();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
