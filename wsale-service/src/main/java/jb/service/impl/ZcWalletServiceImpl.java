package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcWalletDaoI;
import jb.model.TzcWallet;
import jb.pageModel.ZcWallet;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcWalletServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcWalletServiceImpl extends BaseServiceImpl<ZcWallet> implements ZcWalletServiceI {

	@Autowired
	private ZcWalletDaoI zcWalletDao;

	@Override
	public DataGrid dataGrid(ZcWallet zcWallet, PageHelper ph) {
		List<ZcWallet> ol = new ArrayList<ZcWallet>();
		String hql = " from TzcWallet t ";
		DataGrid dg = dataGridQuery(hql, ph, zcWallet, zcWalletDao);
		@SuppressWarnings("unchecked")
		List<TzcWallet> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcWallet t : l) {
				ZcWallet o = new ZcWallet();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcWallet zcWallet, Map<String, Object> params) {
		String whereHql = "";	
		if (zcWallet != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcWallet.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcWallet.getUserId());
			}		
			if (!F.empty(zcWallet.getPayPassword())) {
				whereHql += " and t.payPassword = :payPassword";
				params.put("payPassword", zcWallet.getPayPassword());
			}		
			if (!F.empty(zcWallet.getRealName())) {
				whereHql += " and t.realName = :realName";
				params.put("realName", zcWallet.getRealName());
			}		
			if (!F.empty(zcWallet.getIdNo())) {
				whereHql += " and t.idNo = :idNo";
				params.put("idNo", zcWallet.getIdNo());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcWallet zcWallet) {
		zcWallet.setId(jb.absx.UUID.uuid());
		TzcWallet t = new TzcWallet();
		BeanUtils.copyProperties(zcWallet, t);
		zcWalletDao.save(t);
	}

	@Override
	public ZcWallet get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcWallet t = zcWalletDao.get("from TzcWallet t  where t.id = :id", params);
		ZcWallet o = new ZcWallet();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcWallet zcWallet) {
		TzcWallet t = zcWalletDao.get(TzcWallet.class, zcWallet.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcWallet, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcWalletDao.delete(zcWalletDao.get(TzcWallet.class, id));
	}

	@Override
	public List<ZcWallet> query(ZcWallet zcWallet) {
		List<ZcWallet> ol = new ArrayList<ZcWallet>();
		String hql = " from TzcWallet t ";
		@SuppressWarnings("unchecked")
		List<TzcWallet> l = query(hql, zcWallet, zcWalletDao);
		if (l != null && l.size() > 0) {
			for (TzcWallet t : l) {
				ZcWallet o = new ZcWallet();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcWallet get(ZcWallet zcWallet) {
		String hql = " from TzcWallet t ";
		@SuppressWarnings("unchecked")
		List<TzcWallet> l = query(hql, zcWallet, zcWalletDao);
		ZcWallet o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcWallet();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public void updateProtection(String userId, double protection) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("protection", protection);
		zcWalletDao.executeSql("update zc_wallet t set t.protection = t.protection + :protection where t.user_id = :userId", params);
	}

	public void updateAmount(String userId, double amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("amount", amount);
		zcWalletDao.executeSql("update zc_wallet t set t.amount = ROUND(t.amount + :amount, 2) where t.user_id = :userId", params);
	}

}
