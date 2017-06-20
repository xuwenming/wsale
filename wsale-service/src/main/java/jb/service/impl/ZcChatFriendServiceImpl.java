package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcChatFriendDaoI;
import jb.model.TzcChatFriend;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcChatFriend;
import jb.pageModel.ZcChatMsg;
import jb.service.ZcChatFriendServiceI;
import jb.util.EnumConstants;
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
public class ZcChatFriendServiceImpl extends BaseServiceImpl<ZcChatFriend> implements ZcChatFriendServiceI {

	@Autowired
	private ZcChatFriendDaoI zcChatFriendDao;

	@Override
	public DataGrid dataGrid(ZcChatFriend zcChatFriend, PageHelper ph) {
		List<ZcChatFriend> ol = new ArrayList<ZcChatFriend>();
		String hql = " from TzcChatFriend t ";
		DataGrid dg = dataGridQuery(hql, ph, zcChatFriend, zcChatFriendDao);
		@SuppressWarnings("unchecked")
		List<TzcChatFriend> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcChatFriend t : l) {
				ZcChatFriend o = new ZcChatFriend();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcChatFriend zcChatFriend, Map<String, Object> params) {
		String whereHql = "";	
		if (zcChatFriend != null) {
			whereHql += " where 1=1 ";

			if(zcChatFriend.getIsBoth() != null && zcChatFriend.getIsBoth()) {
				if(!F.empty(zcChatFriend.getUserId()) && !F.empty(zcChatFriend.getFriendUserId())) {
					whereHql += " and ((t.userId = :userId and t.friendUserId = :friendUserId) or (t.userId = :friendUserId and t.friendUserId = :userId))";
					params.put("userId", zcChatFriend.getUserId());
					params.put("friendUserId", zcChatFriend.getFriendUserId());
				}
			} else {
				if (!F.empty(zcChatFriend.getUserId())) {
					whereHql += " and t.userId = :userId";
					params.put("userId", zcChatFriend.getUserId());
				}
				if (!F.empty(zcChatFriend.getFriendUserId())) {
					whereHql += " and t.friendUserId = :friendUserId";
					params.put("friendUserId", zcChatFriend.getFriendUserId());
				}
			}
			if (zcChatFriend.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcChatFriend.getIsDeleted());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcChatFriend zcChatFriend) {
		zcChatFriend.setId(jb.absx.UUID.uuid());
		TzcChatFriend t = new TzcChatFriend();
		BeanUtils.copyProperties(zcChatFriend, t);
		zcChatFriendDao.save(t);
	}

	@Override
	public void addOrUpdate(ZcChatFriend zcChatFriend) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", zcChatFriend.getUserId());
		params.put("friendUserId", zcChatFriend.getFriendUserId());
		TzcChatFriend t = zcChatFriendDao.get("from TzcChatFriend t  where t.userId = :userId and t.friendUserId = :friendUserId", params);
		if(t == null) {
			add(zcChatFriend);
		} else {
			zcChatFriend.setId(t.getId());
			edit(zcChatFriend);
		}
	}

	@Override
	public void updateFriend(ZcChatMsg msg) {
		ZcChatFriend friend = new ZcChatFriend();
		friend.setUserId(msg.getFromUserId());
		friend.setFriendUserId(msg.getToUserId());
		friend.setIsDeleted(false);
		String content = msg.getContent();
		String mtype = msg.getMtype();
		if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(mtype)){
			content = "[图片]";
		} else if(EnumConstants.MSG_TYPE.AUDIO.getCode().equals(mtype)){
			content = "[语音]";
		} else if(EnumConstants.MSG_TYPE.PRODUCT.getCode().equals(mtype)){
			content = "[拍品]";
		} else if(EnumConstants.MSG_TYPE.BBS.getCode().equals(mtype)){
			content = "[帖子]";
		}
		friend.setLastContent(content);
		friend.setLastTime(msg.getAddtime());
		addOrUpdate(friend);

		friend.setUserId(msg.getToUserId());
		friend.setFriendUserId(msg.getFromUserId());
		addOrUpdate(friend);
	}

	@Override
	public ZcChatFriend get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcChatFriend t = zcChatFriendDao.get("from TzcChatFriend t  where t.id = :id", params);
		ZcChatFriend o = new ZcChatFriend();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcChatFriend zcChatFriend) {
		TzcChatFriend t = zcChatFriendDao.get(TzcChatFriend.class, zcChatFriend.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcChatFriend, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcChatFriendDao.executeHql("update TzcChatFriend t set t.isDeleted = 1 where t.id = :id", params);
		//zcChatFriendDao.delete(zcChatFriendDao.get(TzcChatFriend.class, id));
	}

	@Override
	public List<ZcChatFriend> query(ZcChatFriend zcChatFriend) {
		List<ZcChatFriend> ol = new ArrayList<ZcChatFriend>();
		String hql = " from TzcChatFriend t ";
		@SuppressWarnings("unchecked")
		List<TzcChatFriend> l = query(hql, zcChatFriend, zcChatFriendDao, "lastTime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcChatFriend t : l) {
				ZcChatFriend o = new ZcChatFriend();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcChatFriend get(ZcChatFriend zcChatFriend) {
		String hql = " from TzcChatFriend t ";
		@SuppressWarnings("unchecked")
		List<TzcChatFriend> l = query(hql, zcChatFriend, zcChatFriendDao);
		ZcChatFriend o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcChatFriend();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
