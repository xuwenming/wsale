package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcCommentDaoI;
import jb.model.TzcComment;
import jb.pageModel.ZcComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ZcCommentServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcCommentServiceImpl extends BaseServiceImpl<ZcComment> implements ZcCommentServiceI {

	@Autowired
	private ZcCommentDaoI zcCommentDao;

	@Override
	public DataGrid dataGrid(ZcComment zcComment, PageHelper ph) {
		List<ZcComment> ol = new ArrayList<ZcComment>();
		String hql = " from TzcComment t ";
		DataGrid dg = dataGridQuery(hql, ph, zcComment, zcCommentDao);
		@SuppressWarnings("unchecked")
		List<TzcComment> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcComment t : l) {
				ZcComment o = new ZcComment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcComment zcComment, Map<String, Object> params) {
		String whereHql = "";	
		if (zcComment != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcComment.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", zcComment.getOrderId());
			}		
			if (!F.empty(zcComment.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcComment.getProductId());
			}		
			if (!F.empty(zcComment.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", zcComment.getContent());
			}		
			if (!F.empty(zcComment.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcComment.getAddUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ZcComment zcComment) {
		zcComment.setId(jb.absx.UUID.uuid());
		TzcComment t = new TzcComment();
		BeanUtils.copyProperties(zcComment, t);
		zcCommentDao.save(t);
	}

	@Override
	public ZcComment get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcComment t = zcCommentDao.get("from TzcComment t  where t.id = :id", params);
		ZcComment o = new ZcComment();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcComment zcComment) {
		TzcComment t = zcCommentDao.get(TzcComment.class, zcComment.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcComment, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		zcCommentDao.delete(zcCommentDao.get(TzcComment.class, id));
	}

	@Override
	public List<ZcComment> query(ZcComment zcComment) {
		List<ZcComment> ol = new ArrayList<ZcComment>();
		String hql = " from TzcComment t ";
		@SuppressWarnings("unchecked")
		List<TzcComment> l = query(hql, zcComment, zcCommentDao);
		if (l != null && l.size() > 0) {
			for (TzcComment t : l) {
				ZcComment o = new ZcComment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcComment get(ZcComment zcComment) {
		String hql = " from TzcComment t ";
		@SuppressWarnings("unchecked")
		List<TzcComment> l = query(hql, zcComment, zcCommentDao);
		ZcComment o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcComment();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public float getGradeAvgByUserId(String userId) {
		float grade = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String sql = "select ifnull(avg(t.grade), 0) as grade from zc_comment t, zc_product p where t.product_id = p.id and p.addUserId = :userId";
		List<Map> l = zcCommentDao.findBySql2Map(sql, params);
		if(CollectionUtils.isNotEmpty(l)) {
			Map m = l.get(0);
			grade = ((Double)m.get("grade")).floatValue();
		}
		return grade;
	}

}
