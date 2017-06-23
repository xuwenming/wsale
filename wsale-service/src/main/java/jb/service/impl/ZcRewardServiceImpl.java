package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcRewardDaoI;
import jb.model.TzcReward;
import jb.pageModel.ZcReward;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcRewardServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcRewardServiceImpl extends BaseServiceImpl<ZcReward> implements ZcRewardServiceI {

	@Autowired
	private ZcRewardDaoI zcRewardDao;

	@Override
	public DataGrid dataGrid(ZcReward zcReward, PageHelper ph) {
		List<ZcReward> ol = new ArrayList<ZcReward>();
		String hql = " from TzcReward t ";
		DataGrid dg = dataGridQuery(hql, ph, zcReward, zcRewardDao);
		@SuppressWarnings("unchecked")
		List<TzcReward> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcReward t : l) {
				ZcReward o = new ZcReward();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcReward zcReward, Map<String, Object> params) {
		String whereHql = "";	
		if (zcReward != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcReward.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcReward.getObjectType());
			}		
			if (!F.empty(zcReward.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcReward.getObjectId());
			}
			if (!F.empty(zcReward.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcReward.getUserId());
			}		
			if (!F.empty(zcReward.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcReward.getPayStatus());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcReward zcReward) {
		zcReward.setId(jb.absx.UUID.uuid());
		TzcReward t = new TzcReward();
		BeanUtils.copyProperties(zcReward, t);
		zcRewardDao.save(t);
	}

	@Override
	public ZcReward get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcReward t = zcRewardDao.get("from TzcReward t  where t.id = :id", params);
		ZcReward o = new ZcReward();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcReward zcReward) {
		TzcReward t = zcRewardDao.get(TzcReward.class, zcReward.getId());
		if (t != null) {
			zcReward.setObjectId(t.getObjectId());
			MyBeanUtils.copyProperties(zcReward, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcRewardDao.delete(zcRewardDao.get(TzcReward.class, id));
	}

	@Override
	public List<ZcReward> query(ZcReward zcReward) {
		List<ZcReward> ol = new ArrayList<ZcReward>();
		String hql = " from TzcReward t ";
		@SuppressWarnings("unchecked")
		List<TzcReward> l = query(hql, zcReward, zcRewardDao);
		if (l != null && l.size() > 0) {
			for (TzcReward t : l) {
				ZcReward o = new ZcReward();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcReward get(ZcReward zcReward) {
		String hql = " from TzcReward t ";
		@SuppressWarnings("unchecked")
		List<TzcReward> l = query(hql, zcReward, zcRewardDao);
		ZcReward o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcReward();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
