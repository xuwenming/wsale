package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcOrderXiaoerDaoI;
import jb.model.TzcOrderXiaoer;
import jb.pageModel.ZcOrderXiaoer;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcOrderXiaoerServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcOrderXiaoerServiceImpl extends BaseServiceImpl<ZcOrderXiaoer> implements ZcOrderXiaoerServiceI {

	@Autowired
	private ZcOrderXiaoerDaoI zcOrderXiaoerDao;

	@Override
	public DataGrid dataGrid(ZcOrderXiaoer zcOrderXiaoer, PageHelper ph) {
		List<ZcOrderXiaoer> ol = new ArrayList<ZcOrderXiaoer>();
		String hql = " from TzcOrderXiaoer t ";
		DataGrid dg = dataGridQuery(hql, ph, zcOrderXiaoer, zcOrderXiaoerDao);
		@SuppressWarnings("unchecked")
		List<TzcOrderXiaoer> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcOrderXiaoer t : l) {
				ZcOrderXiaoer o = new ZcOrderXiaoer();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcOrderXiaoer zcOrderXiaoer, Map<String, Object> params) {
		String whereHql = "";	
		if (zcOrderXiaoer != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcOrderXiaoer.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", zcOrderXiaoer.getOrderId());
			}		
			if (!F.empty(zcOrderXiaoer.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", zcOrderXiaoer.getReason());
			}		
			if (!F.empty(zcOrderXiaoer.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", zcOrderXiaoer.getContent());
			}		
			if (!F.empty(zcOrderXiaoer.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcOrderXiaoer.getStatus());
			}		
			if (!F.empty(zcOrderXiaoer.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", zcOrderXiaoer.getRemark());
			}		
			if (!F.empty(zcOrderXiaoer.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcOrderXiaoer.getAddUserId());
			}		
			if (!F.empty(zcOrderXiaoer.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcOrderXiaoer.getUpdateUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcOrderXiaoer zcOrderXiaoer) {
		zcOrderXiaoer.setId(jb.absx.UUID.uuid());
		TzcOrderXiaoer t = new TzcOrderXiaoer();
		BeanUtils.copyProperties(zcOrderXiaoer, t);
		zcOrderXiaoerDao.save(t);
	}

	@Override
	public ZcOrderXiaoer get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcOrderXiaoer t = zcOrderXiaoerDao.get("from TzcOrderXiaoer t  where t.id = :id", params);
		ZcOrderXiaoer o = new ZcOrderXiaoer();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcOrderXiaoer zcOrderXiaoer) {
		TzcOrderXiaoer t = zcOrderXiaoerDao.get(TzcOrderXiaoer.class, zcOrderXiaoer.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcOrderXiaoer, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcOrderXiaoerDao.delete(zcOrderXiaoerDao.get(TzcOrderXiaoer.class, id));
	}

	@Override
	public List<ZcOrderXiaoer> query(ZcOrderXiaoer zcOrderXiaoer) {
		List<ZcOrderXiaoer> ol = new ArrayList<ZcOrderXiaoer>();
		String hql = " from TzcOrderXiaoer t ";
		@SuppressWarnings("unchecked")
		List<TzcOrderXiaoer> l = query(hql, zcOrderXiaoer, zcOrderXiaoerDao);
		if (l != null && l.size() > 0) {
			for (TzcOrderXiaoer t : l) {
				ZcOrderXiaoer o = new ZcOrderXiaoer();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcOrderXiaoer get(ZcOrderXiaoer zcOrderXiaoer) {
		String hql = " from TzcOrderXiaoer t ";
		@SuppressWarnings("unchecked")
		List<TzcOrderXiaoer> l = query(hql, zcOrderXiaoer, zcOrderXiaoerDao);
		ZcOrderXiaoer o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcOrderXiaoer();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
