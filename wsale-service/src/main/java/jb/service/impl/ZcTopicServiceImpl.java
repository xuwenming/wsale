package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcTopicDaoI;
import jb.model.TzcTopic;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcTopic;
import jb.service.ZcTopicServiceI;
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
public class ZcTopicServiceImpl extends BaseServiceImpl<ZcTopic> implements ZcTopicServiceI {

	@Autowired
	private ZcTopicDaoI zcTopicDao;

	@Override
	public DataGrid dataGrid(ZcTopic zcTopic, PageHelper ph) {
		List<ZcTopic> ol = new ArrayList<ZcTopic>();
		String hql = " from TzcTopic t ";
		DataGrid dg = dataGridQuery(hql, ph, zcTopic, zcTopicDao);
		@SuppressWarnings("unchecked")
		List<TzcTopic> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcTopic t : l) {
				ZcTopic o = new ZcTopic();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcTopic zcTopic, Map<String, Object> params) {
		String whereHql = "";	
		if (zcTopic != null) {
			whereHql += " where t.isDeleted = 0 ";
			if (!F.empty(zcTopic.getTitle())) {
				whereHql += " and t.title like :title";
				params.put("title", "%%" + zcTopic.getTitle() + "%%");
			}		
			if (!F.empty(zcTopic.getIcon())) {
				whereHql += " and t.icon = :icon";
				params.put("icon", zcTopic.getIcon());
			}		
			if (!F.empty(zcTopic.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", zcTopic.getContent());
			}		
			if (!F.empty(zcTopic.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcTopic.getAddUserId());
			}		
			if (!F.empty(zcTopic.getUpdateUserId())) {
				whereHql += " and t.updateUserId = :updateUserId";
				params.put("updateUserId", zcTopic.getUpdateUserId());
			}
			if(zcTopic.getSeq() != null) {
				whereHql += " and t.seq >= :seq";
				params.put("seq", zcTopic.getSeq());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcTopic zcTopic) {
		zcTopic.setId(jb.absx.UUID.uuid());
		TzcTopic t = new TzcTopic();
		BeanUtils.copyProperties(zcTopic, t);
		zcTopicDao.save(t);
	}

	@Override
	public ZcTopic get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcTopic t = zcTopicDao.get("from TzcTopic t  where t.id = :id", params);
		ZcTopic o = new ZcTopic();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcTopic zcTopic) {
		TzcTopic t = zcTopicDao.get(TzcTopic.class, zcTopic.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcTopic, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcTopicDao.executeHql("update TzcTopic t set t.isDeleted = 1 where t.id = :id", params);
	}

	@Override
	public List<ZcTopic> query(ZcTopic zcTopic) {
		List<ZcTopic> ol = new ArrayList<ZcTopic>();
		String hql = " from TzcTopic t ";
		@SuppressWarnings("unchecked")
		List<TzcTopic> l = query(hql, zcTopic, zcTopicDao);
		if (l != null && l.size() > 0) {
			for (TzcTopic t : l) {
				ZcTopic o = new ZcTopic();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcTopic addReadAndDetail(String id) {
		updateCount(id, 1, "topic_read"); // uv
		return get(id);
	}

	public void updateCount(String id, int count, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcTopicDao.executeSql("update zc_topic t set t."+type+" = ifnull(t."+type+", 0) + "+count+" where t.id = :id", params);
	}

	@Override
	public ZcTopic get(ZcTopic zcTopic) {
		String hql = " from TzcTopic t ";
		@SuppressWarnings("unchecked")
		List<TzcTopic> l = query(hql, zcTopic, zcTopicDao);
		ZcTopic o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcTopic();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
