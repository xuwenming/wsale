package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcChatMsgDaoI;
import jb.model.TzcChatMsg;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcChatMsg;
import jb.service.ZcChatMsgServiceI;
import jb.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZcChatMsgServiceImpl extends BaseServiceImpl<ZcChatMsg> implements ZcChatMsgServiceI {

	@Autowired
	private ZcChatMsgDaoI zcChatMsgDao;

	@Override
	public DataGrid dataGrid(ZcChatMsg zcChatMsg, PageHelper ph) {
		List<ZcChatMsg> ol = new ArrayList<ZcChatMsg>();
		String hql = " from TzcChatMsg t ";
		DataGrid dg = dataGridQuery(hql, ph, zcChatMsg, zcChatMsgDao);
		@SuppressWarnings("unchecked")
		List<TzcChatMsg> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcChatMsg t : l) {
				ZcChatMsg o = new ZcChatMsg();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid dataGridBy(ZcChatMsg zcChatMsg, PageHelper ph) {
		List<ZcChatMsg> ol = new ArrayList<ZcChatMsg>();
		String hql = " from TzcChatMsg t ";
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcChatMsg, params);
		List<TzcChatMsg> l = zcChatMsgDao.findBy(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(zcChatMsgDao.count("select count(*) " + hql + where, params));
		if (l != null && l.size() > 0) {
			for (TzcChatMsg t : l) {
				ZcChatMsg o = new ZcChatMsg();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid dataGridComplex(String userId, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String sql = "select t.*, u.nickname, u.head_image headImage from "
				+ "	(select r.* from ("
				+ " select id, to_user_id friendUserId, mtype, content, addtime, isDeleted, 1 as isFrom from zc_chat_msg where from_user_id = :userId and to_user_id != :userId "
				+ " UNION "
				+ " select id, from_user_id friendUserId, mtype, content, addtime, isDeleted, 0 as isFrom from zc_chat_msg where from_user_id != :userId and to_user_id = :userId "
				+ " ) r order by r.addtime desc) t left join tuser u on u.id = t.friendUserId "
				+ " where exists (select 1 from zc_chat_msg m where m.to_user_id = :userId and m.from_user_id = t.friendUserId) "
				+ " group by t.friendUserId order by t.addtime desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<Map> l = zcChatMsgDao.findBySql2Map(sql, params);
		dg.setRows(l);
		//BigInteger count = zcChatMsgDao.countBySql("select count(*) " + from + where, params);
		//dg.setTotal(count == null ? 0 : count.longValue());
		return dg;
	}
	

	protected String whereHql(ZcChatMsg zcChatMsg, Map<String, Object> params) {
		String whereHql = "";	
		if (zcChatMsg != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcChatMsg.getMtype())) {
				whereHql += " and t.mtype = :mtype";
				params.put("mtype", zcChatMsg.getMtype());
			}		
			if (!F.empty(zcChatMsg.getContent())) {
				whereHql += " and t.content like :content";
				params.put("content", "%%" + zcChatMsg.getContent() + "%%");
			}		
			if (!F.empty(zcChatMsg.getFromUserId()) && !F.empty(zcChatMsg.getToUserId())) {
				if(zcChatMsg.getUnread() != null) {
					whereHql += " and t.fromUserId = :fromUserId and t.toUserId = :toUserId ";
				} else {
					whereHql += " and ((t.fromUserId = :fromUserId and t.toUserId = :toUserId) or (t.fromUserId = :toUserId and t.toUserId = :fromUserId)) ";
				}
				params.put("fromUserId", zcChatMsg.getFromUserId());
				params.put("toUserId", zcChatMsg.getToUserId());
			} else {
				if(!F.empty(zcChatMsg.getFromUserId())) {
					whereHql += " and t.fromUserId = :fromUserId";
					params.put("fromUserId", zcChatMsg.getFromUserId());
				}
				if (!F.empty(zcChatMsg.getToUserId())) {
					whereHql += " and t.toUserId = :toUserId";
					params.put("toUserId", zcChatMsg.getToUserId());
				}
			}
			if (zcChatMsg.getUnread() != null) {
				whereHql += " and t.unread = :unread";
				params.put("unread", zcChatMsg.getUnread());
			}
			if (!F.empty(zcChatMsg.getMsgId())) {
				whereHql += " and t.msgId = :msgId";
				params.put("msgId", zcChatMsg.getMsgId());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcChatMsg zcChatMsg) {
		zcChatMsg.setId(jb.absx.UUID.uuid());
		zcChatMsg.setIsDeleted(0);
		TzcChatMsg t = new TzcChatMsg();
		BeanUtils.copyProperties(zcChatMsg, t);
		zcChatMsgDao.save(t);
		zcChatMsg.setAddtime(t.getAddtime());
	}

	@Override
	public ZcChatMsg get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcChatMsg t = zcChatMsgDao.get("from TzcChatMsg t  where t.id = :id", params);
		ZcChatMsg o = new ZcChatMsg();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcChatMsg zcChatMsg) {
		TzcChatMsg t = zcChatMsgDao.get(TzcChatMsg.class, zcChatMsg.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcChatMsg, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcChatMsgDao.delete(zcChatMsgDao.get(TzcChatMsg.class, id));
	}

	@Override
	public List<ZcChatMsg> query(ZcChatMsg zcChatMsg) {
		List<ZcChatMsg> ol = new ArrayList<ZcChatMsg>();
		String hql = " from TzcChatMsg t ";
		@SuppressWarnings("unchecked")
		List<TzcChatMsg> l = query(hql, zcChatMsg, zcChatMsgDao);
		if (l != null && l.size() > 0) {
			for (TzcChatMsg t : l) {
				ZcChatMsg o = new ZcChatMsg();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public void updateReaded(String fromUserId, String toUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromUserId", fromUserId);
		params.put("toUserId", toUserId);
		params.put("unread", false);
		zcChatMsgDao.executeHql("update from TzcChatMsg t set t.unread = :unread where t.fromUserId = :fromUserId and t.toUserId = :toUserId", params);
	}

	@Override
	public ZcChatMsg get(ZcChatMsg zcChatMsg) {
		String hql = " from TzcChatMsg t ";
		@SuppressWarnings("unchecked")
		List<TzcChatMsg> l = query(hql, zcChatMsg, zcChatMsgDao);
		ZcChatMsg o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcChatMsg();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public int count(ZcChatMsg zcChatMsg) {
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcChatMsg, params);
		Long count = zcChatMsgDao.count("select count(*) from TzcChatMsg t " + where, params);
		return count == null ? 0 : count.intValue();
	}

}
