package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcBbsCommentDaoI;
import jb.model.TzcBbsComment;
import jb.model.TzcCategory;
import jb.model.TzcChatMsg;
import jb.pageModel.ZcBbsComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcBbsCommentServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcBbsCommentServiceImpl extends BaseServiceImpl<ZcBbsComment> implements ZcBbsCommentServiceI {

	@Autowired
	private ZcBbsCommentDaoI zcBbsCommentDao;

	@Override
	public DataGrid dataGrid(ZcBbsComment zcBbsComment, PageHelper ph) {
		List<ZcBbsComment> ol = new ArrayList<ZcBbsComment>();
		String hql = "select t from TzcBbsComment t, TzcForumBbs b ";
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHqlComplex(zcBbsComment, params);
//		DataGrid dg = dataGridQuery(hql, ph, zcBbsComment, zcBbsCommentDao);
		List<TzcBbsComment> l = zcBbsCommentDao.find(hql + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(zcBbsCommentDao.count("select count(*) from TzcBbsComment t, TzcForumBbs b " + where, params));
//		@SuppressWarnings("unchecked")
//		List<TzcBbsComment> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcBbsComment t : l) {
				ZcBbsComment o = new ZcBbsComment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcBbsComment zcBbsComment, Map<String, Object> params) {
		String whereHql = "";	
		if (zcBbsComment != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcBbsComment.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcBbsComment.getBbsId());
			}		
			if (!F.empty(zcBbsComment.getComment())) {
				whereHql += " and t.comment like :comment";
				params.put("comment", "%%" + zcBbsComment.getComment() + "%%");
			}		
			if (!F.empty(zcBbsComment.getPid())) {
				whereHql += " and t.pid = :pid";
				params.put("pid", zcBbsComment.getPid());
			}		
			if (!F.empty(zcBbsComment.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcBbsComment.getUserId());
			}
			if (zcBbsComment.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcBbsComment.getIsDeleted());
			}
		}	
		return whereHql;
	}

	protected String whereHqlComplex(ZcBbsComment zcBbsComment, Map<String, Object> params) {
		String whereHql = " where t.bbsId = b.id ";
		if (zcBbsComment != null) {
			if (!F.empty(zcBbsComment.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcBbsComment.getBbsId());
			}
			if (!F.empty(zcBbsComment.getComment())) {
				whereHql += " and t.comment like :comment";
				params.put("comment", "%%" + zcBbsComment.getComment() + "%%");
			}
			if (!F.empty(zcBbsComment.getPid())) {
				whereHql += " and t.pid = :pid";
				params.put("pid", zcBbsComment.getPid());
			}
			if (!F.empty(zcBbsComment.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcBbsComment.getUserId());
			}
			if (!F.empty(zcBbsComment.getCategoryId())) {
				whereHql += " and b.categoryId = :categoryId";
				params.put("categoryId", zcBbsComment.getCategoryId());
			}
			if (zcBbsComment.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcBbsComment.getIsDeleted());
			}
		}
		return whereHql;
	}

	@Override
	public void add(ZcBbsComment zcBbsComment) {
		zcBbsComment.setId(jb.absx.UUID.uuid());
		TzcBbsComment t = new TzcBbsComment();
		BeanUtils.copyProperties(zcBbsComment, t);

		zcBbsCommentDao.save(t);
		zcBbsComment.setAddtime(t.getAddtime());
	}

	@Override
	public ZcBbsComment get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcBbsComment t = zcBbsCommentDao.get("from TzcBbsComment t  where t.id = :id", params);
		ZcBbsComment o = new ZcBbsComment();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcBbsComment zcBbsComment) {
		TzcBbsComment t = zcBbsCommentDao.get(TzcBbsComment.class, zcBbsComment.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcBbsComment, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		zcBbsCommentDao.delete(zcBbsCommentDao.get(TzcBbsComment.class, id));
	}

	@Override
	public void delete(ZcBbsComment comment) {
		comment.setIsDeleted(true);
		edit(comment);
	}

}
