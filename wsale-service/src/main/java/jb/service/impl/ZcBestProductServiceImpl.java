package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.dao.ZcBestProductDaoI;
import jb.model.TzcBestProduct;
import jb.pageModel.ZcBestProduct;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcBestProductServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcBestProductServiceImpl extends BaseServiceImpl<ZcBestProduct> implements ZcBestProductServiceI {

	@Autowired
	private ZcBestProductDaoI zcBestProductDao;

	@Override
	public DataGrid dataGrid(ZcBestProduct zcBestProduct, PageHelper ph) {
		List<ZcBestProduct> ol = new ArrayList<ZcBestProduct>();
		String hql = "select t from TzcBestProduct t , TzcProduct p ";
		DataGrid dg = dataGridQuery(hql, ph, zcBestProduct, zcBestProductDao);
		@SuppressWarnings("unchecked")
		List<TzcBestProduct> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcBestProduct t : l) {
				ZcBestProduct o = new ZcBestProduct();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcBestProduct zcBestProduct, Map<String, Object> params) {
		String whereHql = "";	
		if (zcBestProduct != null) {
			whereHql += " where p.id = t.productId and p.isDeleted = 0 ";
			if(!F.empty(zcBestProduct.getCategoryId())) {
				whereHql += " and p.categoryId = :categoryId";
				params.put("categoryId", zcBestProduct.getCategoryId());
			}

			if (!F.empty(zcBestProduct.getChannel())) {
				whereHql += " and t.channel = :channel";
				params.put("channel", zcBestProduct.getChannel());
			}		
			if (!F.empty(zcBestProduct.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcBestProduct.getProductId());
			}		
			if (!F.empty(zcBestProduct.getAuditStatus())) {
				whereHql += " and t.auditStatus = :auditStatus";
				params.put("auditStatus", zcBestProduct.getAuditStatus());
			}		
			if (!F.empty(zcBestProduct.getAuditUserId())) {
				whereHql += " and t.auditUserId = :auditUserId";
				params.put("auditUserId", zcBestProduct.getAuditUserId());
			}		
			if (!F.empty(zcBestProduct.getAuditRemark())) {
				whereHql += " and t.auditRemark = :auditRemark";
				params.put("auditRemark", zcBestProduct.getAuditRemark());
			}		
			if (!F.empty(zcBestProduct.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcBestProduct.getPayStatus());
			}		
			if (!F.empty(zcBestProduct.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcBestProduct.getAddUserId());
			}
			if(zcBestProduct.getEndTime() != null) {
				whereHql += " and t.endTime > :endTime";
				params.put("endTime", zcBestProduct.getEndTime());
			}
			if(!F.empty(zcBestProduct.getProductStatus())) {
				whereHql += " and p.status = :status";
				params.put("status", zcBestProduct.getProductStatus());
			}

			if(!F.empty(zcBestProduct.getGroup())) {
				whereHql += " group by t." + zcBestProduct.getGroup();
			}
		}	
		return whereHql;
	}

	@Override
	protected DataGrid dataGridQuery(String hql, PageHelper ph, ZcBestProduct zcBestProduct, BaseDaoI dao) {
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcBestProduct, params);
		List<TzcBestProduct> l = dao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		if(F.empty(zcBestProduct.getGroup()))
			dg.setTotal(dao.count("select count(*) " + hql.substring(hql.indexOf("from")) + where, params));

		dg.setRows(l);
		return dg;
	}

	@Override
	public void add(ZcBestProduct zcBestProduct) {
		zcBestProduct.setId(jb.absx.UUID.uuid());
		TzcBestProduct t = new TzcBestProduct();
		BeanUtils.copyProperties(zcBestProduct, t);
		zcBestProductDao.save(t);
	}

	@Override
	public ZcBestProduct get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcBestProduct t = zcBestProductDao.get("from TzcBestProduct t  where t.id = :id", params);
		ZcBestProduct o = new ZcBestProduct();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcBestProduct zcBestProduct) {
		TzcBestProduct t = zcBestProductDao.get(TzcBestProduct.class, zcBestProduct.getId());
		if (t != null) {
			if("AS03".equals(zcBestProduct.getAuditStatus()) && t.getEndTime() != null) {
				zcBestProduct.setEndTime(new Date()); // 审核失败，终止结束时间
			}
			MyBeanUtils.copyProperties(zcBestProduct, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcBestProductDao.delete(zcBestProductDao.get(TzcBestProduct.class, id));
	}

	@Override
	public List<ZcBestProduct> query(ZcBestProduct zcBestProduct) {
		List<ZcBestProduct> ol = new ArrayList<ZcBestProduct>();
		String hql = " select t from TzcBestProduct t , TzcProduct p  ";
		@SuppressWarnings("unchecked")
		List<TzcBestProduct> l = query(hql, zcBestProduct, zcBestProductDao, "auditTime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcBestProduct t : l) {
				ZcBestProduct o = new ZcBestProduct();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public long getCount(ZcBestProduct zcBestProduct) {
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcBestProduct, params);
		return zcBestProductDao.count("select count(*) from TzcBestProduct t , TzcProduct p " + where, params);
	}

	@Override
	public ZcBestProduct get(ZcBestProduct zcBestProduct) {
		String hql = "select t from TzcBestProduct t , TzcProduct p ";
		@SuppressWarnings("unchecked")
		List<TzcBestProduct> l = query(hql, zcBestProduct, zcBestProductDao);
		ZcBestProduct o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcBestProduct();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
