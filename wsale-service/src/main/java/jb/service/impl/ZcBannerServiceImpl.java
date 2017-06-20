package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcBannerDaoI;
import jb.model.TzcBanner;
import jb.pageModel.ZcBanner;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcBannerServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcBannerServiceImpl extends BaseServiceImpl<ZcBanner> implements ZcBannerServiceI {

	@Autowired
	private ZcBannerDaoI zcBannerDao;

	@Override
	public DataGrid dataGrid(ZcBanner zcBanner, PageHelper ph) {
		List<ZcBanner> ol = new ArrayList<ZcBanner>();
		String hql = " from TzcBanner t ";
		DataGrid dg = dataGridQuery(hql, ph, zcBanner, zcBannerDao);
		@SuppressWarnings("unchecked")
		List<TzcBanner> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcBanner t : l) {
				ZcBanner o = new ZcBanner();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcBanner zcBanner, Map<String, Object> params) {
		String whereHql = "";	
		if (zcBanner != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcBanner.getTitle())) {
				whereHql += " and t.title like :title";
				params.put("title", "%%" + zcBanner.getTitle() + "%%");
			}
			if (!F.empty(zcBanner.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcBanner.getStatus());
			}		
			if (!F.empty(zcBanner.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcBanner.getAddUserId());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(ZcBanner zcBanner) {
		zcBanner.setId(jb.absx.UUID.uuid());
		TzcBanner t = new TzcBanner();
		BeanUtils.copyProperties(zcBanner, t);
		zcBannerDao.save(t);
	}

	@Override
	public ZcBanner get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcBanner t = zcBannerDao.get("from TzcBanner t  where t.id = :id", params);
		ZcBanner o = new ZcBanner();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcBanner zcBanner) {
		TzcBanner t = zcBannerDao.get(TzcBanner.class, zcBanner.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcBanner, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcBannerDao.delete(zcBannerDao.get(TzcBanner.class, id));
	}

	@Override
	public List<ZcBanner> query(ZcBanner zcBanner) {
		List<ZcBanner> ol = new ArrayList<ZcBanner>();
		String hql = " from TzcBanner t ";
		@SuppressWarnings("unchecked")
		List<TzcBanner> l = query(hql, zcBanner, zcBannerDao, "sortNumber", "desc");
		if (l != null && l.size() > 0) {
			for (TzcBanner t : l) {
				ZcBanner o = new ZcBanner();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcBanner get(ZcBanner zcBanner) {
		String hql = " from TzcBanner t ";
		@SuppressWarnings("unchecked")
		List<TzcBanner> l = query(hql, zcBanner, zcBannerDao);
		ZcBanner o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcBanner();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
