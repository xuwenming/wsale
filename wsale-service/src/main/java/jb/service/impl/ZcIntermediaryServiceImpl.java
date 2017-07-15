package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcIntermediaryDaoI;
import jb.model.TzcIntermediary;
import jb.pageModel.*;
import jb.service.ZcFileServiceI;
import jb.service.ZcForumBbsServiceI;
import jb.service.ZcIntermediaryServiceI;
import jb.util.Constants;
import jb.util.DateUtil;
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
public class ZcIntermediaryServiceImpl extends BaseServiceImpl<ZcIntermediary> implements ZcIntermediaryServiceI {

	@Autowired
	private ZcIntermediaryDaoI zcIntermediaryDao;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Override
	public DataGrid dataGrid(ZcIntermediary zcIntermediary, PageHelper ph) {
		List<ZcIntermediary> ol = new ArrayList<ZcIntermediary>();
		String hql = " from TzcIntermediary t ";
		DataGrid dg = dataGridQuery(hql, ph, zcIntermediary, zcIntermediaryDao);
		@SuppressWarnings("unchecked")
		List<TzcIntermediary> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcIntermediary t : l) {
				ZcIntermediary o = new ZcIntermediary();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcIntermediary zcIntermediary, Map<String, Object> params) {
		String whereHql = "";	
		if (zcIntermediary != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcIntermediary.getImNo())) {
				whereHql += " and t.imNo like :imNo";
				params.put("imNo", "%%" + zcIntermediary.getImNo() + "%%");
			}		
			if (!F.empty(zcIntermediary.getBbsId())) {
				whereHql += " and t.bbsId = :bbsId";
				params.put("bbsId", zcIntermediary.getBbsId());
			}		
			if (!F.empty(zcIntermediary.getSellUserId())) {
				whereHql += " and (t.sellUserId = :sellUserId or t.userId = :sellUserId)";
				params.put("sellUserId", zcIntermediary.getSellUserId());
			}		
			if (!F.empty(zcIntermediary.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcIntermediary.getUserId());
			}
			if (!F.empty(zcIntermediary.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", zcIntermediary.getRemark());
			}		
			if (!F.empty(zcIntermediary.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcIntermediary.getStatus());
			}
			if(zcIntermediary.getAddtime() != null) {
				whereHql += " and date_format(t.addtime, '%Y-%m-%d %H:%i:%s') = :addtime";
				params.put("addtime", DateUtil.format(zcIntermediary.getAddtime(), Constants.DATE_FORMAT));
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcIntermediary zcIntermediary) {
		zcIntermediary.setId(jb.absx.UUID.uuid());
		TzcIntermediary t = new TzcIntermediary();
		BeanUtils.copyProperties(zcIntermediary, t);
		zcIntermediaryDao.save(t);
		zcIntermediary.setAddtime(t.getAddtime());
	}

	@Override
	public ZcIntermediary get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcIntermediary t = zcIntermediaryDao.get("from TzcIntermediary t  where t.id = :id", params);
		ZcIntermediary o = new ZcIntermediary();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcIntermediary zcIntermediary) {
		TzcIntermediary t = zcIntermediaryDao.get(TzcIntermediary.class, zcIntermediary.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcIntermediary, t, new String[] { "id" , "addtime" },true);
			zcIntermediary.setAmount(t.getAmount());
		}
	}

	@Override
	public void delete(String id) {
		zcIntermediaryDao.delete(zcIntermediaryDao.get(TzcIntermediary.class, id));
	}

	@Override
	public List<ZcIntermediary> query(ZcIntermediary zcIntermediary) {
		List<ZcIntermediary> ol = new ArrayList<ZcIntermediary>();
		String hql = " from TzcIntermediary t ";
		@SuppressWarnings("unchecked")
		List<TzcIntermediary> l = query(hql, zcIntermediary, zcIntermediaryDao);
		if (l != null && l.size() > 0) {
			for (TzcIntermediary t : l) {
				ZcIntermediary o = new ZcIntermediary();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcIntermediary getDetail(String id) {
		ZcIntermediary im = get(id);
		ZcForumBbs bbs = zcForumBbsService.get(im.getBbsId());
		ZcFile file = new ZcFile();
		file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
		file.setObjectId(bbs.getId());
		file.setFileType("FT01");
		List<ZcFile> files = zcFileService.queryFiles(file);
		bbs.setFiles(files);
		if (CollectionUtils.isNotEmpty(files)) {
			bbs.setIcon(files.get(0).getFileHandleUrl());
		}
		im.setBbs(bbs);
		return im;
	}

	@Override
	public ZcIntermediary get(ZcIntermediary zcIntermediary) {
		String hql = " from TzcIntermediary t ";
		@SuppressWarnings("unchecked")
		List<TzcIntermediary> l = query(hql, zcIntermediary, zcIntermediaryDao);
		ZcIntermediary o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcIntermediary();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
