package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcShopDaoI;
import jb.model.TzcShop;
import jb.pageModel.ZcShop;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcShopServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcShopServiceImpl extends BaseServiceImpl<ZcShop> implements ZcShopServiceI {

	@Autowired
	private ZcShopDaoI zcShopDao;

	@Override
	public DataGrid dataGrid(ZcShop zcShop, PageHelper ph) {
		List<ZcShop> ol = new ArrayList<ZcShop>();
		String hql = " from TzcShop t ";
		DataGrid dg = dataGridQuery(hql, ph, zcShop, zcShopDao);
		@SuppressWarnings("unchecked")
		List<TzcShop> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcShop t : l) {
				ZcShop o = new ZcShop();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcShop zcShop, Map<String, Object> params) {
		String whereHql = "";	
		if (zcShop != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcShop.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcShop.getUserId());
			}		
			if (!F.empty(zcShop.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", zcShop.getName());
			}		
			if (!F.empty(zcShop.getLogoUrl())) {
				whereHql += " and t.logoUrl = :logoUrl";
				params.put("logoUrl", zcShop.getLogoUrl());
			}		
			if (!F.empty(zcShop.getNotice())) {
				whereHql += " and t.notice = :notice";
				params.put("notice", zcShop.getNotice());
			}		
			if (!F.empty(zcShop.getIntroduction())) {
				whereHql += " and t.introduction = :introduction";
				params.put("introduction", zcShop.getIntroduction());
			}		
			if (!F.empty(zcShop.getShopUrl())) {
				whereHql += " and t.shopUrl = :shopUrl";
				params.put("shopUrl", zcShop.getShopUrl());
			}		
			if (!F.empty(zcShop.getShopQrcodeUrl())) {
				whereHql += " and t.shopQrcodeUrl = :shopQrcodeUrl";
				params.put("shopQrcodeUrl", zcShop.getShopQrcodeUrl());
			}		
			if (!F.empty(zcShop.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcShop.getAddUserId());
			}		
			if (!F.empty(zcShop.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcShop.getUpdateUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcShop zcShop) {
		zcShop.setId(jb.absx.UUID.uuid());
		TzcShop t = new TzcShop();
		BeanUtils.copyProperties(zcShop, t);
		zcShopDao.save(t);
	}

	@Override
	public ZcShop get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcShop t = zcShopDao.get("from TzcShop t  where t.id = :id", params);
		ZcShop o = new ZcShop();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcShop zcShop) {
		TzcShop t = zcShopDao.get(TzcShop.class, zcShop.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcShop, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcShopDao.delete(zcShopDao.get(TzcShop.class, id));
	}

	@Override
	public List<ZcShop> query(ZcShop zcShop) {
		List<ZcShop> ol = new ArrayList<ZcShop>();
		String hql = " from TzcShop t ";
		@SuppressWarnings("unchecked")
		List<TzcShop> l = query(hql, zcShop, zcShopDao);
		if (l != null && l.size() > 0) {
			for (TzcShop t : l) {
				ZcShop o = new ZcShop();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcShop get(ZcShop zcShop) {
		String hql = " from TzcShop t ";
		@SuppressWarnings("unchecked")
		List<TzcShop> l = query(hql, zcShop, zcShopDao);
		ZcShop o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcShop();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
