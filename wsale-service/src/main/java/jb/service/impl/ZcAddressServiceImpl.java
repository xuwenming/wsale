package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcAddressDaoI;
import jb.model.TzcAddress;
import jb.pageModel.ZcAddress;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcAddressServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcAddressServiceImpl extends BaseServiceImpl<ZcAddress> implements ZcAddressServiceI {

	@Autowired
	private ZcAddressDaoI zcAddressDao;

	@Override
	public DataGrid dataGrid(ZcAddress zcAddress, PageHelper ph) {
		List<ZcAddress> ol = new ArrayList<ZcAddress>();
		String hql = " from TzcAddress t ";
		DataGrid dg = dataGridQuery(hql, ph, zcAddress, zcAddressDao);
		@SuppressWarnings("unchecked")
		List<TzcAddress> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcAddress t : l) {
				ZcAddress o = new ZcAddress();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcAddress zcAddress, Map<String, Object> params) {
		String whereHql = "";	
		if (zcAddress != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcAddress.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcAddress.getUserId());
			}
			if (!F.empty(zcAddress.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", zcAddress.getOrderId());
			}
			if (!F.empty(zcAddress.getUserName())) {
				whereHql += " and t.userName = :userName";
				params.put("userName", zcAddress.getUserName());
			}		
			if (!F.empty(zcAddress.getTelNumber())) {
				whereHql += " and t.telNumber = :telNumber";
				params.put("telNumber", zcAddress.getTelNumber());
			}		
			if (!F.empty(zcAddress.getProvinceName())) {
				whereHql += " and t.provinceName = :provinceName";
				params.put("provinceName", zcAddress.getProvinceName());
			}		
			if (!F.empty(zcAddress.getCityName())) {
				whereHql += " and t.cityName = :cityName";
				params.put("cityName", zcAddress.getCityName());
			}		
			if (!F.empty(zcAddress.getCountyName())) {
				whereHql += " and t.countyName = :countyName";
				params.put("countyName", zcAddress.getCountyName());
			}		
			if (!F.empty(zcAddress.getDetailInfo())) {
				whereHql += " and t.detailInfo = :detailInfo";
				params.put("detailInfo", zcAddress.getDetailInfo());
			}		
			if (!F.empty(zcAddress.getPostalCode())) {
				whereHql += " and t.postalCode = :postalCode";
				params.put("postalCode", zcAddress.getPostalCode());
			}
			if (zcAddress.getAtype() != null) {
				whereHql += " and t.atype = :atype";
				params.put("atype", zcAddress.getAtype());
			}
		}
		return whereHql;
	}

	@Override
	public void add(ZcAddress zcAddress) {
		zcAddress.setId(jb.absx.UUID.uuid());
		TzcAddress t = new TzcAddress();
		BeanUtils.copyProperties(zcAddress, t);
		zcAddressDao.save(t);
	}

	@Override
	public ZcAddress get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcAddress t = zcAddressDao.get("from TzcAddress t  where t.id = :id", params);
		ZcAddress o = new ZcAddress();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcAddress zcAddress) {
		TzcAddress t = zcAddressDao.get(TzcAddress.class, zcAddress.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcAddress, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcAddressDao.delete(zcAddressDao.get(TzcAddress.class, id));
	}

	@Override
	public List<ZcAddress> query(ZcAddress zcAddress) {
		List<ZcAddress> ol = new ArrayList<ZcAddress>();
		String hql = " from TzcAddress t ";
		@SuppressWarnings("unchecked")
		List<TzcAddress> l = query(hql, zcAddress, zcAddressDao);
		if (l != null && l.size() > 0) {
			for (TzcAddress t : l) {
				ZcAddress o = new ZcAddress();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcAddress get(ZcAddress zcAddress) {
		String hql = " from TzcAddress t ";
		@SuppressWarnings("unchecked")
		List<TzcAddress> l = query(hql, zcAddress, zcAddressDao);
		ZcAddress o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcAddress();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
