package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.ZcFileDaoI;
import jb.model.TzcFile;
import jb.model.TzcShieldorfans;
import jb.pageModel.ZcFile;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcShieldorfans;
import jb.service.ZcFileServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class ZcFileServiceImpl extends BaseServiceImpl<ZcFile> implements ZcFileServiceI {

	@Autowired
	private ZcFileDaoI zcFileDao;

	@Override
	public DataGrid dataGrid(ZcFile zcFile, PageHelper ph) {
		List<ZcFile> ol = new ArrayList<ZcFile>();
		String hql = " from TzcFile t ";
		DataGrid dg = dataGridQuery(hql, ph, zcFile, zcFileDao);
		@SuppressWarnings("unchecked")
		List<TzcFile> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcFile t : l) {
				ZcFile o = new ZcFile();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcFile zcFile, Map<String, Object> params) {
		String whereHql = "";	
		if (zcFile != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcFile.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcFile.getObjectType());
			}		
			if (!F.empty(zcFile.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcFile.getObjectId());
			}		
			if (!F.empty(zcFile.getFileType())) {
				whereHql += " and t.fileType = :fileType";
				params.put("fileType", zcFile.getFileType());
			}		
			if (!F.empty(zcFile.getFileOriginalUrl())) {
				whereHql += " and t.fileOriginalUrl = :fileOriginalUrl";
				params.put("fileOriginalUrl", zcFile.getFileOriginalUrl());
			}		
			if (!F.empty(zcFile.getFileHandleUrl())) {
				whereHql += " and t.fileHandleUrl = :fileHandleUrl";
				params.put("fileHandleUrl", zcFile.getFileHandleUrl());
			}		
		}
		return whereHql;
	}

	@Override
	public void add(ZcFile zcFile) {
		TzcFile t = new TzcFile();
		BeanUtils.copyProperties(zcFile, t);
		t.setId(jb.absx.UUID.uuid());
		//t.setCreatedatetime(new Date());
		zcFileDao.save(t);
	}

	@Override
	public ZcFile get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcFile t = zcFileDao.get("from TzcFile t  where t.id = :id", params);
		ZcFile o = new ZcFile();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcFile zcFile) {
		TzcFile t = zcFileDao.get(TzcFile.class, zcFile.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcFile, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		zcFileDao.delete(zcFileDao.get(TzcFile.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZcFile> queryFiles(ZcFile file) {
		List<ZcFile> ol = new ArrayList<ZcFile>();
		String hql = " from TzcFile t ";
		List<TzcFile> l = query(hql, file, zcFileDao, "seq asc, t.addtime", "asc");
		if (l != null && l.size() > 0) {
			for (TzcFile t : l) {
				ZcFile o = new ZcFile();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ZcFile get(ZcFile file) {
		String hql = " from TzcFile t ";
		List<TzcFile> l = query(hql, file, zcFileDao, "seq asc, t.addtime", "asc");
		ZcFile o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcFile();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public int getMaxSeq(ZcFile zcFile) {
		String hql = " from TzcFile t ";
		List<TzcFile> l = query(hql, zcFile, zcFileDao, "seq", "desc");
		ZcFile o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcFile();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o == null ? 0 : (o.getSeq() == null ? 0 : o.getSeq());
	}

	@Override
	public Map<String, String> queryIcons(String objectType, String... objectIds) {
		return zcFileDao.queryIcons(objectType, objectIds);
	}
}
