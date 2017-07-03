package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcShieldorfansDaoI;
import jb.model.TzcShieldorfans;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.User;
import jb.pageModel.ZcShieldorfans;
import jb.service.ZcShieldorfansServiceI;
import jb.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ZcShieldorfansServiceImpl extends BaseServiceImpl<ZcShieldorfans> implements ZcShieldorfansServiceI {

	@Autowired
	private ZcShieldorfansDaoI zcShieldorfansDao;

	@Override
	public DataGrid dataGrid(ZcShieldorfans zcShieldorfans, PageHelper ph) {
		List<ZcShieldorfans> ol = new ArrayList<ZcShieldorfans>();
		String hql = " from TzcShieldorfans t ";
		DataGrid dg = dataGridQuery(hql, ph, zcShieldorfans, zcShieldorfansDao);
		@SuppressWarnings("unchecked")
		List<TzcShieldorfans> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcShieldorfans t : l) {
				ZcShieldorfans o = new ZcShieldorfans();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcShieldorfans zcShieldorfans, Map<String, Object> params) {
		String whereHql = "";	
		if (zcShieldorfans != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcShieldorfans.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcShieldorfans.getObjectType());
			}		
			if (!F.empty(zcShieldorfans.getObjectById())) {
				whereHql += " and t.objectById = :objectById";
				params.put("objectById", zcShieldorfans.getObjectById());
			}		
			if (!F.empty(zcShieldorfans.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcShieldorfans.getObjectId());
			}
			if(zcShieldorfans.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcShieldorfans.getIsDeleted());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcShieldorfans zcShieldorfans) {
		TzcShieldorfans t = new TzcShieldorfans();
		BeanUtils.copyProperties(zcShieldorfans, t);
		t.setId(jb.absx.UUID.uuid());
		//t.setCreatedatetime(new Date());
		zcShieldorfansDao.save(t);
	}

	@Override
	public ZcShieldorfans get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcShieldorfans t = zcShieldorfansDao.get("from TzcShieldorfans t  where t.id = :id", params);
		ZcShieldorfans o = new ZcShieldorfans();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcShieldorfans zcShieldorfans) {
		TzcShieldorfans t = zcShieldorfansDao.get(TzcShieldorfans.class, zcShieldorfans.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcShieldorfans, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcShieldorfansDao.executeHql("update TzcShieldorfans t set t.isDeleted = 1 where t.id = :id", params);
//		zcShieldorfansDao.delete(zcShieldorfansDao.get(TzcShieldorfans.class, id));
	}

	/**
	 *
	 * @param shieldorfans
	 * @return true:推送通知消息
	 */
	@Override
	public boolean addOrUpdate(ZcShieldorfans shieldorfans) {
		return addOrUpdate(shieldorfans, false);
	}

	/**
	 *
	 * @param shieldorfans
	 * @return true:推送通知消息
	 */
	@Override
	public boolean addOrUpdate(ZcShieldorfans shieldorfans, boolean isHomePage) {
		boolean result = false;
		ZcShieldorfans exist = get(shieldorfans);
		if(exist == null) {
			add(shieldorfans);
			result = true;
		} else {
			if(exist.getIsDeleted()) {
				Date now = new Date();
				boolean isToday = DateUtil.format(now, Constants.DATE_FORMAT_YMD).equals(DateUtil.format(exist.getAddtime(), Constants.DATE_FORMAT_YMD));
				if(!isHomePage || !isToday) {
					shieldorfans.setId(exist.getId());
					shieldorfans.setIsDeleted(false);
					shieldorfans.setAddtime(now);
					edit(shieldorfans);
					if(!isToday)
						result = true;
				}
			}
		}

		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public ZcShieldorfans get(ZcShieldorfans shieldorfans) {
		String hql = " from TzcShieldorfans t ";
		List<TzcShieldorfans> l = query(hql, shieldorfans, zcShieldorfansDao);
		ZcShieldorfans o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcShieldorfans();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public List<ZcShieldorfans> query(ZcShieldorfans shieldorfans) {
		List<ZcShieldorfans> ol = new ArrayList<ZcShieldorfans>();
		String hql = " from TzcShieldorfans t ";
		@SuppressWarnings("unchecked")
		List<TzcShieldorfans> l = query(hql, shieldorfans, zcShieldorfansDao);
		if (l != null && l.size() > 0) {
			for (TzcShieldorfans t : l) {
				ZcShieldorfans o = new ZcShieldorfans();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
