package jb.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcWalletDaoI;
import jb.dao.ZcWalletDetailDaoI;
import jb.model.TzcWalletDetail;
import jb.pageModel.ZcWallet;
import jb.pageModel.ZcWalletDetail;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcWalletDetailServiceI;

import jb.service.ZcWalletServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcWalletDetailServiceImpl extends BaseServiceImpl<ZcWalletDetail> implements ZcWalletDetailServiceI {

	@Autowired
	private ZcWalletDetailDaoI zcWalletDetailDao;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Override
	public DataGrid dataGrid(ZcWalletDetail zcWalletDetail, PageHelper ph) {
		List<ZcWalletDetail> ol = new ArrayList<ZcWalletDetail>();
		String hql = " from TzcWalletDetail t ";
		DataGrid dg = dataGridQuery(hql, ph, zcWalletDetail, zcWalletDetailDao);
		@SuppressWarnings("unchecked")
		List<TzcWalletDetail> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcWalletDetail t : l) {
				ZcWalletDetail o = new ZcWalletDetail();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcWalletDetail zcWalletDetail, Map<String, Object> params) {
		String whereHql = "";	
		if (zcWalletDetail != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcWalletDetail.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcWalletDetail.getUserId());
			}		
			if (!F.empty(zcWalletDetail.getOrderNo())) {
				whereHql += " and t.orderNo like :orderNo";
				params.put("orderNo", "%%" + zcWalletDetail.getOrderNo() + "%%");
			}
			if (!F.empty(zcWalletDetail.getWtype())) {
				whereHql += " and t.wtype = :wtype";
				params.put("wtype", zcWalletDetail.getWtype());
			}		
			if (!F.empty(zcWalletDetail.getDescription())) {
				whereHql += " and t.description like :description";
				params.put("description", "%%" + zcWalletDetail.getDescription() + "%%");
			}		
			if (!F.empty(zcWalletDetail.getChannel())) {
				whereHql += " and t.channel = :channel";
				params.put("channel", zcWalletDetail.getChannel());
			}
			if (!F.empty(zcWalletDetail.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", zcWalletDetail.getHandleStatus());
			}
			if (zcWalletDetail.getAddtimeBegin() != null) {
				whereHql += " and t.addtime >= :addtimeBegin";
				params.put("addtimeBegin", zcWalletDetail.getAddtimeBegin());
			}
			if (zcWalletDetail.getAddtimeEnd() != null) {
				whereHql += " and t.addtime <= :addtimeEnd";
				params.put("addtimeEnd", zcWalletDetail.getAddtimeEnd());
			}
			if(zcWalletDetail.getAddtime() != null) {
				whereHql += " and to_days(t.addtime) = to_days(now())";
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcWalletDetail zcWalletDetail) {
		zcWalletDetail.setId(jb.absx.UUID.uuid());
		TzcWalletDetail t = new TzcWalletDetail();
		BeanUtils.copyProperties(zcWalletDetail, t);
		zcWalletDetailDao.save(t);
	}

	@Override
	public void addAndUpdateWallet(ZcWalletDetail zcWalletDetail) {
		ZcWallet q = new ZcWallet();
		q.setUserId(zcWalletDetail.getUserId());
		ZcWallet wallet = zcWalletService.get(q);

		double amount = zcWalletDetail.getAmount();
		if(!zcWalletDetail.getIsIncome()) amount = -amount;

		if(wallet == null) zcWalletDetail.setWalletAmount(amount);
		else zcWalletDetail.setWalletAmount(new BigDecimal(wallet.getAmount()).add(new BigDecimal(amount)).doubleValue());
		add(zcWalletDetail);

		if(wallet == null) {
			wallet = q;
			wallet.setAmount(amount);
			wallet.setFrozenAmount(0.0);
			wallet.setUserId(zcWalletDetail.getUserId());
			zcWalletService.add(wallet);
		} else {
			zcWalletService.updateAmount(wallet.getUserId(), amount);
		}
	}

	@Override
	public ZcWalletDetail get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcWalletDetail t = zcWalletDetailDao.get("from TzcWalletDetail t  where t.id = :id", params);
		ZcWalletDetail o = new ZcWalletDetail();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcWalletDetail zcWalletDetail) {
		TzcWalletDetail t = zcWalletDetailDao.get(TzcWalletDetail.class, zcWalletDetail.getId());
		if (t != null) {
			zcWalletDetail.setUserId(t.getUserId());
			zcWalletDetail.setOrderNo(t.getOrderNo());
			zcWalletDetail.setAmount(t.getAmount());
			zcWalletDetail.setChannel(t.getChannel());
			MyBeanUtils.copyProperties(zcWalletDetail, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcWalletDetailDao.delete(zcWalletDetailDao.get(TzcWalletDetail.class, id));
	}

	@Override
	public List<ZcWalletDetail> query(ZcWalletDetail zcWalletDetail) {
		List<ZcWalletDetail> ol = new ArrayList<ZcWalletDetail>();
		String hql = " from TzcWalletDetail t ";
		@SuppressWarnings("unchecked")
		List<TzcWalletDetail> l = query(hql, zcWalletDetail, zcWalletDetailDao);
		if (l != null && l.size() > 0) {
			for (TzcWalletDetail t : l) {
				ZcWalletDetail o = new ZcWalletDetail();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcWalletDetail get(ZcWalletDetail zcWalletDetail) {
		String hql = " from TzcWalletDetail t ";
		@SuppressWarnings("unchecked")
		List<TzcWalletDetail> l = query(hql, zcWalletDetail, zcWalletDetailDao, "addtime", "desc");
		ZcWalletDetail o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcWalletDetail();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
