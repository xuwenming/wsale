package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcReportDaoI;
import jb.model.TzcReport;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcReport;
import jb.service.ZcReportServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZcReportServiceImpl extends BaseServiceImpl<ZcReport> implements ZcReportServiceI {

	@Autowired
	private ZcReportDaoI zcReportDao;

	@Override
	public DataGrid dataGrid(ZcReport zcReport, PageHelper ph) {
		List<ZcReport> ol = new ArrayList<ZcReport>();
		String hql = " from TzcReport t ";
		DataGrid dg = dataGridQuery(hql, ph, zcReport, zcReportDao);
		@SuppressWarnings("unchecked")
		List<TzcReport> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcReport t : l) {
				ZcReport o = new ZcReport();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcReport zcReport, Map<String, Object> params) {
		String whereHql = "";	
		if (zcReport != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcReport.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcReport.getObjectType());
			}		
			if (!F.empty(zcReport.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcReport.getObjectId());
			}		
			if (!F.empty(zcReport.getReportReason())) {
				whereHql += " and t.reportReason = :reportReason";
				params.put("reportReason", zcReport.getReportReason());
			}		
			if (!F.empty(zcReport.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcReport.getUserId());
			}
			if (zcReport.getAddtimeBegin() != null) {
				whereHql += " and t.addtime >= :addtimeBegin";
				params.put("addtimeBegin", zcReport.getAddtimeBegin());
			}
			if (zcReport.getAddtimeEnd() != null) {
				whereHql += " and t.addtime <= :addtimeEnd";
				params.put("addtimeEnd", zcReport.getAddtimeEnd());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcReport zcReport) {
		TzcReport t = new TzcReport();
		BeanUtils.copyProperties(zcReport, t);
		t.setId(jb.absx.UUID.uuid());
		//t.setCreatedatetime(new Date());
		zcReportDao.save(t);
	}

	@Override
	public ZcReport get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcReport t = zcReportDao.get("from TzcReport t  where t.id = :id", params);
		ZcReport o = new ZcReport();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcReport zcReport) {
		TzcReport t = zcReportDao.get(TzcReport.class, zcReport.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcReport, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		zcReportDao.delete(zcReportDao.get(TzcReport.class, id));
	}

	@Override
	public List<ZcReport> query(ZcReport r) {
		List<ZcReport> ol = new ArrayList<ZcReport>();
		String hql = " from TzcReport t ";
		List<TzcReport> l = query(hql, r, zcReportDao);
		if (l != null && l.size() > 0) {
			for (TzcReport t : l) {
				ZcReport o = new ZcReport();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
