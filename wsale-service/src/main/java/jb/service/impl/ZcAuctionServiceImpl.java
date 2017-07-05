package jb.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcAuctionDaoI;
import jb.model.TzcAuction;
import jb.pageModel.ZcAuction;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcOrder;
import jb.service.ZcAuctionServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcAuctionServiceImpl extends BaseServiceImpl<ZcAuction> implements ZcAuctionServiceI {

	@Autowired
	private ZcAuctionDaoI zcAuctionDao;

	@Override
	public DataGrid dataGrid(ZcAuction zcAuction, PageHelper ph) {
		List<ZcAuction> ol = new ArrayList<ZcAuction>();
		String hql = " from TzcAuction t ";
		DataGrid dg = dataGridQuery(hql, ph, zcAuction, zcAuctionDao);
		@SuppressWarnings("unchecked")
		List<TzcAuction> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcAuction t : l) {
				ZcAuction o = new ZcAuction();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcAuction zcAuction, Map<String, Object> params) {
		String whereHql = "";	
		if (zcAuction != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcAuction.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcAuction.getProductId());
			}		
			if (!F.empty(zcAuction.getBuyerId())) {
				whereHql += " and t.buyerId = :buyerId";
				params.put("buyerId", zcAuction.getBuyerId());
			}		
			if (!F.empty(zcAuction.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcAuction.getStatus());
			}		
			if (!F.empty(zcAuction.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcAuction.getAddUserId());
			}		
			if (!F.empty(zcAuction.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcAuction.getUpdateUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcAuction zcAuction) {
		zcAuction.setId(jb.absx.UUID.uuid());
		TzcAuction t = new TzcAuction();
		BeanUtils.copyProperties(zcAuction, t);
		zcAuctionDao.save(t);
		zcAuction.setAddtime(t.getAddtime());
	}

	@Override
	public ZcAuction get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcAuction t = zcAuctionDao.get("from TzcAuction t  where t.id = :id", params);
		ZcAuction o = new ZcAuction();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcAuction zcAuction) {
		TzcAuction t = zcAuctionDao.get(TzcAuction.class, zcAuction.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcAuction, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcAuctionDao.delete(zcAuctionDao.get(TzcAuction.class, id));
	}

	@Override
	public List<ZcAuction> query(ZcAuction zcAuction) {
		List<ZcAuction> ol = new ArrayList<ZcAuction>();
		String hql = " from TzcAuction t ";
		@SuppressWarnings("unchecked")
		List<TzcAuction> l = query(hql, zcAuction, zcAuctionDao, "bid", "desc");
		if (l != null && l.size() > 0) {
			for (TzcAuction t : l) {
				ZcAuction o = new ZcAuction();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public DataGrid dataGridComplet(ZcAuction zcAuction, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<ZcAuction> ol = new ArrayList<ZcAuction>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerId", zcAuction.getBuyerId());
		String sql = "select distinct t.product_id productId from zc_auction t where t.buyer_id = :buyerId order by t.addtime desc";
		List<Map> l = zcAuctionDao.findBySql2Map(sql, params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Map m : l) {
				ZcAuction o = new ZcAuction();
				o.setProductId((String)m.get("productId"));
				ol.add(o);
			}
		}
		dg.setRows(ol);
		BigInteger count = zcAuctionDao.countBySql("select count(distinct t.product_id) from zc_auction t where t.buyer_id = :buyerId", params);
		dg.setTotal(count == null ? 0 : count.longValue());
		return dg;
	}

	@Override
	public DataGrid dataGridCompletetd(String id, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<ZcAuction> ol = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerId", id);
		String sql = "SELECT t.product_id productId,t.buyer_Id buyerId,t.addUserId addUserId FROM zc_auction t where (t.buyer_id=:buyerId OR t.addUserId = :buyerId) AND t.status='DS02' order by t.bid desc ";

		List<Map> l = zcAuctionDao.findBySql2Map(sql, params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Map m : l) {
				ZcAuction o = new ZcAuction();
				o.setBuyerId((String)m.get("buyerId"));
				o.setAddUserId((String)m.get("addUserId"));
				o.setProductId((String)m.get("productId"));
				ol.add(o);
			}
		}
		dg.setRows(ol);
		BigInteger count = zcAuctionDao.countBySql("select count(t.product_id) FROM zc_auction t where (t.buyer_id=:buyerId OR t.addUserId = :buyerId) AND `status`='DS02'", params);
		dg.setTotal(count == null ? 0 : count.longValue());
		return dg;
	}

	@Override
	public Map<String, Integer> getCountAuctionNum(String[] productIds) {
		return zcAuctionDao.getCountAuctionNum(productIds);
	}

	@Override
	public ZcAuction get(ZcAuction zcAuction) {
		String hql = " from TzcAuction t ";
		@SuppressWarnings("unchecked")
		List<TzcAuction> l = query(hql, zcAuction, zcAuctionDao, "bid", "desc");
		ZcAuction o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcAuction();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
