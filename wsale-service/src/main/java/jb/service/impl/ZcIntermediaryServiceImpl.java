package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcIntermediaryDaoI;
import jb.model.TzcIntermediary;
import jb.pageModel.ZcIntermediary;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcIntermediaryServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcIntermediaryServiceImpl extends BaseServiceImpl<ZcIntermediary> implements ZcIntermediaryServiceI {

	@Autowired
	private ZcIntermediaryDaoI zcIntermediaryDao;

	@Override
	public DataGrid dataGrid(ZcIntermediary zcIntermediary, PageHelper ph) {
		List<ZcIntermediary> ol = new ArrayList<ZcIntermediary>();
		String hql = " from TzcIntermediary t ";
		DataGrid dg = dataGridQuery(hql, ph, zcIntermediary, zcIntermediaryDao);
		@SuppressWarnings("unchecked")
		List<TzcIntermediary> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcIntermediary t : l) {
				ZcIntermediary o = new ZcIntermediary();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcIntermediary zcIntermediary, Map<String, Object> params) {
		String whereHql = "";	
		if (zcIntermediary != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcIntermediary.getImNo())) {
				whereHql += " and t.imNo = :imNo";
				params.put("imNo", zcIntermediary.getImNo());
			}		
			if (!F.empty(zcIntermediary.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcIntermediary.getBbsId());
			}		
			if (!F.empty(zcIntermediary.getSellUserId())) {
				whereHql += " and t.sellUserId = :sellUserId";
				params.put("sellUserId", zcIntermediary.getSellUserId());
			}		
			if (!F.empty(zcIntermediary.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcIntermediary.getUserId());
			}
			if (!F.empty(zcIntermediary.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", zcIntermediary.getRemark());
			}		
			if (!F.empty(zcIntermediary.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcIntermediary.getStatus());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcIntermediary zcIntermediary) {
		zcIntermediary.setId(jb.absx.UUID.uuid());
		TzcIntermediary t = new TzcIntermediary();
		BeanUtils.copyProperties(zcIntermediary, t);
		zcIntermediaryDao.save(t);
	}

	@Override
	public ZcIntermediary get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcIntermediary t = zcIntermediaryDao.get("from TzcIntermediary t  where t.id = :id", params);
		ZcIntermediary o = new ZcIntermediary();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcIntermediary zcIntermediary) {
		TzcIntermediary t = zcIntermediaryDao.get(TzcIntermediary.class, zcIntermediary.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcIntermediary, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcIntermediaryDao.delete(zcIntermediaryDao.get(TzcIntermediary.class, id));
	}

	@Override
	public List<ZcIntermediary> query(ZcIntermediary zcIntermediary) {
		List<ZcIntermediary> ol = new ArrayList<ZcIntermediary>();
		String hql = " from TzcIntermediary t ";
		@SuppressWarnings("unchecked")
		List<TzcIntermediary> l = query(hql, zcIntermediary, zcIntermediaryDao);
		if (l != null && l.size() > 0) {
			for (TzcIntermediary t : l) {
				ZcIntermediary o = new ZcIntermediary();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcIntermediary get(ZcIntermediary zcIntermediary) {
		String hql = " from TzcIntermediary t ";
		@SuppressWarnings("unchecked")
		List<TzcIntermediary> l = query(hql, zcIntermediary, zcIntermediaryDao);
		ZcIntermediary o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcIntermediary();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
