package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.DonationOrderDaoI;
import jb.model.TdonationOrder;
import jb.pageModel.DonationOrder;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.DonationOrderServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class DonationOrderServiceImpl extends BaseServiceImpl<DonationOrder> implements DonationOrderServiceI {

	@Autowired
	private DonationOrderDaoI donationOrderDao;

	@Override
	public DataGrid dataGrid(DonationOrder donationOrder, PageHelper ph) {
		List<DonationOrder> ol = new ArrayList<DonationOrder>();
		String hql = " from TdonationOrder t ";
		DataGrid dg = dataGridQuery(hql, ph, donationOrder, donationOrderDao);
		@SuppressWarnings("unchecked")
		List<TdonationOrder> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdonationOrder t : l) {
				DonationOrder o = new DonationOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}


	protected String whereHql(DonationOrder donationOrder, Map<String, Object> params) {
		String whereHql = "";	
		if (donationOrder != null) {
			whereHql += " where 1=1 ";

			if (!F.empty(donationOrder.getNickname())) {
				whereHql += " and t.nickname like :nickname";
				params.put("nickname", "%%" + donationOrder.getNickname() + "%%");
			}
			if (!F.empty(donationOrder.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", donationOrder.getPayStatus());
			}		
			if (!F.empty(donationOrder.getOrderNo())) {
				whereHql += " and t.orderNo like :orderNo";
				params.put("orderNo", "%%" + donationOrder.getOrderNo() + "%%");
			}
			if (donationOrder.getSex() != null) {
				whereHql += " and t.sex = :sex";
				params.put("sex", donationOrder.getSex());
			}
			if (donationOrder.getPaytimeBegin() != null) {
				whereHql += " and t.paytime >= :paytimeBegin";
				params.put("paytimeBegin", donationOrder.getPaytimeBegin());
			}
			if (donationOrder.getPaytimeEnd() != null) {
				whereHql += " and t.paytime <= :paytimeEnd";
				params.put("paytimeEnd", donationOrder.getPaytimeEnd());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(DonationOrder donationOrder) {
		TdonationOrder t = new TdonationOrder();
		BeanUtils.copyProperties(donationOrder, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		donationOrderDao.save(t);
	}

	@Override
	public DonationOrder get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdonationOrder t = donationOrderDao.get("from TdonationOrder t  where t.id = :id", params);
		DonationOrder o = new DonationOrder();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DonationOrder donationOrder) {
		TdonationOrder t = donationOrderDao.get(TdonationOrder.class, donationOrder.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(donationOrder, t, new String[] { "id" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void editByOrderNo(DonationOrder order) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", order.getOrderNo());
		TdonationOrder t = donationOrderDao.get("from TdonationOrder t where t.orderNo = :orderNo", params);
		if (t != null) {
			if("PS01".equals(t.getPayStatus())) {
				order.setPaytime(new Date());
				MyBeanUtils.copyProperties(order, t, new String[] {"id"},true);
			}
		}
	}

	@Override
	public void delete(String id) {
		donationOrderDao.delete(donationOrderDao.get(TdonationOrder.class, id));
	}

}
