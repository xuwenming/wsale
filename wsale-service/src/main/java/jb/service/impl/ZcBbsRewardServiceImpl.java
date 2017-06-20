package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.dao.ZcBbsRewardDaoI;
import jb.model.TzcBbsReward;
import jb.model.TzcBestProduct;
import jb.pageModel.ZcBbsReward;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcBestProduct;
import jb.service.ZcBbsRewardServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcBbsRewardServiceImpl extends BaseServiceImpl<ZcBbsReward> implements ZcBbsRewardServiceI {

	@Autowired
	private ZcBbsRewardDaoI zcBbsRewardDao;

	@Override
	public DataGrid dataGrid(ZcBbsReward zcBbsReward, PageHelper ph) {
		List<ZcBbsReward> ol = new ArrayList<ZcBbsReward>();
		String hql = "select t from TzcBbsReward t, TzcForumBbs b ";
		DataGrid dg = dataGridQuery(hql, ph, zcBbsReward, zcBbsRewardDao);
		@SuppressWarnings("unchecked")
		List<TzcBbsReward> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcBbsReward t : l) {
				ZcBbsReward o = new ZcBbsReward();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	protected DataGrid dataGridQuery(String hql, PageHelper ph, ZcBbsReward zcBbsReward, BaseDaoI dao) {
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcBbsReward, params);
		List<TzcBbsReward> l = dao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(dao.count("select count(*) " + hql.substring(hql.indexOf("from")) + where, params));
		dg.setRows(l);
		return dg;
	}
	

	protected String whereHql(ZcBbsReward zcBbsReward, Map<String, Object> params) {
		String whereHql = "";	
		if (zcBbsReward != null) {
			whereHql += " where t.bbsId = b.id ";
			if (!F.empty(zcBbsReward.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcBbsReward.getBbsId());
			}		
			if (!F.empty(zcBbsReward.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcBbsReward.getUserId());
			}		
			if (!F.empty(zcBbsReward.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcBbsReward.getPayStatus());
			}
			if(!F.empty(zcBbsReward.getByUserId())) {
				if(zcBbsReward.getAuth() != null && zcBbsReward.getAuth()) {
					whereHql += " and (b.addUserId = :byUserId or t.userId = :byUserId)";
				} else {
					whereHql += " and b.addUserId = :byUserId";
				}
				params.put("byUserId", zcBbsReward.getByUserId());
			}

		}	
		return whereHql;
	}

	@Override
	public void add(ZcBbsReward zcBbsReward) {
		zcBbsReward.setId(jb.absx.UUID.uuid());
		TzcBbsReward t = new TzcBbsReward();
		BeanUtils.copyProperties(zcBbsReward, t);
		zcBbsRewardDao.save(t);
	}

	@Override
	public ZcBbsReward get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcBbsReward t = zcBbsRewardDao.get("from TzcBbsReward t  where t.id = :id", params);
		ZcBbsReward o = new ZcBbsReward();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcBbsReward zcBbsReward) {
		TzcBbsReward t = zcBbsRewardDao.get(TzcBbsReward.class, zcBbsReward.getId());
		if (t != null) {
			zcBbsReward.setBbsId(t.getBbsId());
			MyBeanUtils.copyProperties(zcBbsReward, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcBbsRewardDao.delete(zcBbsRewardDao.get(TzcBbsReward.class, id));
	}

	@Override
	public List<ZcBbsReward> query(ZcBbsReward zcBbsReward) {
		List<ZcBbsReward> ol = new ArrayList<ZcBbsReward>();
		String hql = "select t from TzcBbsReward t, TzcForumBbs b ";
		@SuppressWarnings("unchecked")
		List<TzcBbsReward> l = query(hql, zcBbsReward, zcBbsRewardDao, "addtime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcBbsReward t : l) {
				ZcBbsReward o = new ZcBbsReward();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcBbsReward get(ZcBbsReward zcBbsReward) {
		String hql = "select t from TzcBbsReward t, TzcForumBbs b ";
		@SuppressWarnings("unchecked")
		List<TzcBbsReward> l = query(hql, zcBbsReward, zcBbsRewardDao);
		ZcBbsReward o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcBbsReward();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
