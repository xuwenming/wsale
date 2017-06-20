package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcOfflineTransferDaoI;
import jb.model.TzcOfflineTransfer;
import jb.pageModel.ZcOfflineTransfer;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcOfflineTransferServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcOfflineTransferServiceImpl extends BaseServiceImpl<ZcOfflineTransfer> implements ZcOfflineTransferServiceI {

	@Autowired
	private ZcOfflineTransferDaoI zcOfflineTransferDao;

	@Override
	public DataGrid dataGrid(ZcOfflineTransfer zcOfflineTransfer, PageHelper ph) {
		List<ZcOfflineTransfer> ol = new ArrayList<ZcOfflineTransfer>();
		String hql = " from TzcOfflineTransfer t ";
		DataGrid dg = dataGridQuery(hql, ph, zcOfflineTransfer, zcOfflineTransferDao);
		@SuppressWarnings("unchecked")
		List<TzcOfflineTransfer> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcOfflineTransfer t : l) {
				ZcOfflineTransfer o = new ZcOfflineTransfer();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcOfflineTransfer zcOfflineTransfer, Map<String, Object> params) {
		String whereHql = "";	
		if (zcOfflineTransfer != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcOfflineTransfer.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcOfflineTransfer.getUserId());
			}		
			if (!F.empty(zcOfflineTransfer.getTransferUserName())) {
				whereHql += " and t.transferUserName like :transferUserName";
				params.put("transferUserName", "%%" + zcOfflineTransfer.getTransferUserName() + "%%");
			}
			if (zcOfflineTransfer.getTransferAmount() != null) {
				whereHql += " and t.transferAmount = :transferAmount";
				params.put("transferAmount", zcOfflineTransfer.getTransferAmount());
			}
			if (!F.empty(zcOfflineTransfer.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", zcOfflineTransfer.getRemark());
			}		
			if (!F.empty(zcOfflineTransfer.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", zcOfflineTransfer.getHandleStatus());
			}		
			if (!F.empty(zcOfflineTransfer.getHandleUserId())) {
				whereHql += " and t.handleUserId = :handleUserId";
				params.put("handleUserId", zcOfflineTransfer.getHandleUserId());
			}		
			if (!F.empty(zcOfflineTransfer.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", zcOfflineTransfer.getHandleRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcOfflineTransfer zcOfflineTransfer) {
		zcOfflineTransfer.setId(jb.absx.UUID.uuid());
		TzcOfflineTransfer t = new TzcOfflineTransfer();
		BeanUtils.copyProperties(zcOfflineTransfer, t);
		zcOfflineTransferDao.save(t);
	}

	@Override
	public ZcOfflineTransfer get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcOfflineTransfer t = zcOfflineTransferDao.get("from TzcOfflineTransfer t  where t.id = :id", params);
		ZcOfflineTransfer o = new ZcOfflineTransfer();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcOfflineTransfer zcOfflineTransfer) {
		TzcOfflineTransfer t = zcOfflineTransferDao.get(TzcOfflineTransfer.class, zcOfflineTransfer.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcOfflineTransfer, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcOfflineTransferDao.delete(zcOfflineTransferDao.get(TzcOfflineTransfer.class, id));
	}

	@Override
	public List<ZcOfflineTransfer> query(ZcOfflineTransfer zcOfflineTransfer) {
		List<ZcOfflineTransfer> ol = new ArrayList<ZcOfflineTransfer>();
		String hql = " from TzcOfflineTransfer t ";
		@SuppressWarnings("unchecked")
		List<TzcOfflineTransfer> l = query(hql, zcOfflineTransfer, zcOfflineTransferDao);
		if (l != null && l.size() > 0) {
			for (TzcOfflineTransfer t : l) {
				ZcOfflineTransfer o = new ZcOfflineTransfer();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcOfflineTransfer get(ZcOfflineTransfer zcOfflineTransfer) {
		String hql = " from TzcOfflineTransfer t ";
		@SuppressWarnings("unchecked")
		List<TzcOfflineTransfer> l = query(hql, zcOfflineTransfer, zcOfflineTransferDao);
		ZcOfflineTransfer o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcOfflineTransfer();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
