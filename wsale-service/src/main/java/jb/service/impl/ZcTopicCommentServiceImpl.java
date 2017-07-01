package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcTopicCommentDaoI;
import jb.model.TzcTopicComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcTopicComment;
import jb.service.ZcTopicCommentServiceI;
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
public class ZcTopicCommentServiceImpl extends BaseServiceImpl<ZcTopicComment> implements ZcTopicCommentServiceI {

	@Autowired
	private ZcTopicCommentDaoI zcTopicCommentDao;

	@Override
	public DataGrid dataGrid(ZcTopicComment zcTopicComment, PageHelper ph) {
		List<ZcTopicComment> ol = new ArrayList<ZcTopicComment>();
		String hql = " from TzcTopicComment t ";
		DataGrid dg = dataGridQuery(hql, ph, zcTopicComment, zcTopicCommentDao);
		@SuppressWarnings("unchecked")
		List<TzcTopicComment> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcTopicComment t : l) {
				ZcTopicComment o = new ZcTopicComment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcTopicComment zcTopicComment, Map<String, Object> params) {
		String whereHql = "";	
		if (zcTopicComment != null) {
			whereHql += " where t.isDeleted = 0 ";
			if (!F.empty(zcTopicComment.getTopicId())) {
				whereHql += " and t.topicId = :topicId";
				params.put("topicId", zcTopicComment.getTopicId());
			}		
			if (!F.empty(zcTopicComment.getComment())) {
				whereHql += " and t.comment = :comment";
				params.put("comment", zcTopicComment.getComment());
			}		
			if (!F.empty(zcTopicComment.getCtype())) {
				whereHql += " and t.ctype = :ctype";
				params.put("ctype", zcTopicComment.getCtype());
			}		
			if (!F.empty(zcTopicComment.getPid())) {
				whereHql += " and t.pid = :pid";
				params.put("pid", zcTopicComment.getPid());
			} else {
				whereHql += " and t.pid is null ";
			}
			if (!F.empty(zcTopicComment.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcTopicComment.getUserId());
			}		
			if (!F.empty(zcTopicComment.getAuditStatus())) {
				whereHql += " and t.auditStatus = :auditStatus";
				params.put("auditStatus", zcTopicComment.getAuditStatus());
			}		
			if (!F.empty(zcTopicComment.getAuditUserId())) {
				whereHql += " and t.auditUserId = :auditUserId";
				params.put("auditUserId", zcTopicComment.getAuditUserId());
			}		
			if (!F.empty(zcTopicComment.getAuditRemark())) {
				whereHql += " and t.auditRemark = :auditRemark";
				params.put("auditRemark", zcTopicComment.getAuditRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcTopicComment zcTopicComment) {
		zcTopicComment.setId(jb.absx.UUID.uuid());
		TzcTopicComment t = new TzcTopicComment();
		BeanUtils.copyProperties(zcTopicComment, t);
		zcTopicCommentDao.save(t);
		zcTopicComment.setAddtime(t.getAddtime());
	}

	@Override
	public ZcTopicComment get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcTopicComment t = zcTopicCommentDao.get("from TzcTopicComment t  where t.id = :id", params);
		ZcTopicComment o = new ZcTopicComment();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcTopicComment zcTopicComment) {
		TzcTopicComment t = zcTopicCommentDao.get(TzcTopicComment.class, zcTopicComment.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcTopicComment, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcTopicCommentDao.executeHql("update TzcTopicComment t set t.isDeleted = 1 where t.id = :id", params);
//		zcTopicCommentDao.delete(zcTopicCommentDao.get(TzcTopicComment.class, id));
	}

	@Override
	public List<ZcTopicComment> query(ZcTopicComment zcTopicComment) {
		List<ZcTopicComment> ol = new ArrayList<ZcTopicComment>();
		String hql = " from TzcTopicComment t ";
		@SuppressWarnings("unchecked")
		List<TzcTopicComment> l = query(hql, zcTopicComment, zcTopicCommentDao);
		if (l != null && l.size() > 0) {
			for (TzcTopicComment t : l) {
				ZcTopicComment o = new ZcTopicComment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcTopicComment get(ZcTopicComment zcTopicComment) {
		String hql = " from TzcTopicComment t ";
		@SuppressWarnings("unchecked")
		List<TzcTopicComment> l = query(hql, zcTopicComment, zcTopicCommentDao);
		ZcTopicComment o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcTopicComment();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
