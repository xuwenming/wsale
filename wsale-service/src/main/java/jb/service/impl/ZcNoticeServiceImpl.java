package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcNoticeDaoI;
import jb.model.TzcNotice;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcLastViewLogServiceI;
import jb.service.ZcNoticeServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcNoticeServiceImpl extends BaseServiceImpl<ZcNotice> implements ZcNoticeServiceI {

	@Autowired
	private ZcNoticeDaoI zcNoticeDao;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcLastViewLogServiceI zcLastViewLogService;

	@Override
	public DataGrid dataGrid(ZcNotice zcNotice, PageHelper ph) {
		List<ZcNotice> ol = new ArrayList<ZcNotice>();
		String hql = " from TzcNotice t ";
		DataGrid dg = dataGridQuery(hql, ph, zcNotice, zcNoticeDao);
		@SuppressWarnings("unchecked")
		List<TzcNotice> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcNotice t : l) {
				ZcNotice o = new ZcNotice();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcNotice zcNotice, Map<String, Object> params) {
		String whereHql = "";	
		if (zcNotice != null) {
			whereHql += " where t.isDeleted = 0 ";
			if (!F.empty(zcNotice.getContent())) {
				whereHql += " and t.content like :content";
				params.put("content", "%%" + zcNotice.getContent() + "%%");
			}		
			if (!F.empty(zcNotice.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcNotice.getStatus());
			}		
			if (!F.empty(zcNotice.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcNotice.getAddUserId());
			}		
			if (!F.empty(zcNotice.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcNotice.getUpdateUserId());
			}
			if(zcNotice.getAddtime() != null) {
				whereHql += " and t.addtime >= :addtime";
				params.put("addtime", zcNotice.getAddtime());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcNotice zcNotice) {
		zcNotice.setId(jb.absx.UUID.uuid());
		TzcNotice t = new TzcNotice();
		BeanUtils.copyProperties(zcNotice, t);
		zcNoticeDao.save(t);
	}

	@Override
	public ZcNotice get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcNotice t = zcNoticeDao.get("from TzcNotice t  where t.id = :id", params);
		ZcNotice o = new ZcNotice();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcNotice zcNotice) {
		TzcNotice t = zcNoticeDao.get(TzcNotice.class, zcNotice.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcNotice, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcNoticeDao.executeHql("update TzcNotice t set t.isDeleted = 1 where t.id = :id", params);
	}

	@Override
	public List<ZcNotice> query(ZcNotice zcNotice) {
		List<ZcNotice> ol = new ArrayList<ZcNotice>();
		String hql = " from TzcNotice t ";
		@SuppressWarnings("unchecked")
		List<TzcNotice> l = query(hql, zcNotice, zcNoticeDao);
		if (l != null && l.size() > 0) {
			for (TzcNotice t : l) {
				ZcNotice o = new ZcNotice();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public int getUnreadCount(String userId) {
		Date addtime = null;
		ZcLastViewLog log = new ZcLastViewLog();
		log.setBusinessType("NOTICE"); // 官方消息
		log.setUserId(userId);
		ZcLastViewLog exist = zcLastViewLogService.get(log);
		if(exist == null) {
			User user = userService.getByZc(userId);
			addtime = user.getCreatedatetime();
		} else {
			addtime = exist.getLastViewTime();
		}

		ZcNotice notice= new ZcNotice();
		notice.setStatus("ST01");
		notice.setAddtime(addtime);
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(notice, params);
		return zcNoticeDao.count("select count(*) from TzcNotice t " + where, params).intValue();
	}

	@Override
	public ZcNotice get(ZcNotice zcNotice) {
		String hql = " from TzcNotice t ";
		@SuppressWarnings("unchecked")
		List<TzcNotice> l = query(hql, zcNotice, zcNoticeDao, "addtime", "desc");
		ZcNotice o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcNotice();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
