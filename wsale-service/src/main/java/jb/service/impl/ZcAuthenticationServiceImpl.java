package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcAuthenticationDaoI;
import jb.model.TzcAuthentication;
import jb.pageModel.ZcAuthentication;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcAuthenticationServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcAuthenticationServiceImpl extends BaseServiceImpl<ZcAuthentication> implements ZcAuthenticationServiceI {

	@Autowired
	private ZcAuthenticationDaoI zcAuthenticationDao;

	@Override
	public DataGrid dataGrid(ZcAuthentication zcAuthentication, PageHelper ph) {
		List<ZcAuthentication> ol = new ArrayList<ZcAuthentication>();
		String hql = " from TzcAuthentication t ";
		DataGrid dg = dataGridQuery(hql, ph, zcAuthentication, zcAuthenticationDao);
		@SuppressWarnings("unchecked")
		List<TzcAuthentication> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcAuthentication t : l) {
				ZcAuthentication o = new ZcAuthentication();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcAuthentication zcAuthentication, Map<String, Object> params) {
		String whereHql = "";	
		if (zcAuthentication != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcAuthentication.getAuthType())) {
				whereHql += " and t.authType = :authType";
				params.put("authType", zcAuthentication.getAuthType());
			}		
			if (!F.empty(zcAuthentication.getUserName())) {
				whereHql += " and t.userName = :userName";
				params.put("userName", zcAuthentication.getUserName());
			}
			if (!F.empty(zcAuthentication.getIdNo())) {
				whereHql += " and t.idNo = :idNo";
				params.put("idNo", zcAuthentication.getIdNo());
			}		
			if (!F.empty(zcAuthentication.getPhone())) {
				whereHql += " and t.phone = :phone";
				params.put("phone", zcAuthentication.getPhone());
			}
			if (!F.empty(zcAuthentication.getCompanyName())) {
				whereHql += " and t.companyName = :companyName";
				params.put("companyName", zcAuthentication.getCompanyName());
			}		
			if (!F.empty(zcAuthentication.getCreditId())) {
				whereHql += " and t.creditId = :creditId";
				params.put("creditId", zcAuthentication.getCreditId());
			}		
			if (!F.empty(zcAuthentication.getLegalPersonName())) {
				whereHql += " and t.legalPersonName = :legalPersonName";
				params.put("legalPersonName", zcAuthentication.getLegalPersonName());
			}		
			if (!F.empty(zcAuthentication.getLegalPersonId())) {
				whereHql += " and t.legalPersonId = :legalPersonId";
				params.put("legalPersonId", zcAuthentication.getLegalPersonId());
			}
			if (!F.empty(zcAuthentication.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcAuthentication.getPayStatus());
			}		
			if (!F.empty(zcAuthentication.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcAuthentication.getAddUserId());
			}		
			if (!F.empty(zcAuthentication.getAuditStatus())) {
				whereHql += " and t.auditStatus = :auditStatus";
				params.put("auditStatus", zcAuthentication.getAuditStatus());
			}		
			if (!F.empty(zcAuthentication.getAuditUserId())) {
				whereHql += " and t.auditUserId = :auditUserId";
				params.put("auditUserId", zcAuthentication.getAuditUserId());
			}
			if (zcAuthentication.getAddtimeBegin() != null) {
				whereHql += " and t.addtime >= :addtimeBegin";
				params.put("addtimeBegin", zcAuthentication.getAddtimeBegin());
			}
			if (zcAuthentication.getAddtimeEnd() != null) {
				whereHql += " and t.addtime <= :addtimeEnd";
				params.put("addtimeEnd", zcAuthentication.getAddtimeEnd());
			}

		}	
		return whereHql;
	}

	@Override
	public void add(ZcAuthentication zcAuthentication) {
		zcAuthentication.setId(jb.absx.UUID.uuid());
		TzcAuthentication t = new TzcAuthentication();
		BeanUtils.copyProperties(zcAuthentication, t);
		zcAuthenticationDao.save(t);
	}

	@Override
	public ZcAuthentication get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcAuthentication t = zcAuthenticationDao.get("from TzcAuthentication t  where t.id = :id", params);
		ZcAuthentication o = new ZcAuthentication();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcAuthentication zcAuthentication) {
		TzcAuthentication t = zcAuthenticationDao.get(TzcAuthentication.class, zcAuthentication.getId());
		if (t != null) {
			zcAuthentication.setAddUserId(t.getAddUserId());
			MyBeanUtils.copyProperties(zcAuthentication, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcAuthenticationDao.delete(zcAuthenticationDao.get(TzcAuthentication.class, id));
	}

	@Override
	public List<ZcAuthentication> query(ZcAuthentication zcAuthentication) {
		List<ZcAuthentication> ol = new ArrayList<ZcAuthentication>();
		String hql = " from TzcAuthentication t ";
		@SuppressWarnings("unchecked")
		List<TzcAuthentication> l = query(hql, zcAuthentication, zcAuthenticationDao);
		if (l != null && l.size() > 0) {
			for (TzcAuthentication t : l) {
				ZcAuthentication o = new ZcAuthentication();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcAuthentication get(ZcAuthentication zcAuthentication) {
		String hql = " from TzcAuthentication t ";
		@SuppressWarnings("unchecked")
		List<TzcAuthentication> l = query(hql, zcAuthentication, zcAuthenticationDao);
		ZcAuthentication o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcAuthentication();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
