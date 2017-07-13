package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcProductDaoI;
import jb.model.TzcProduct;
import jb.pageModel.*;
import jb.service.*;
import jb.util.Constants;
import jb.util.DateUtil;
import jb.util.EnumConstants;
import jb.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.*;

@Service
public class ZcProductServiceImpl extends BaseServiceImpl<ZcProduct> implements ZcProductServiceI {

	@Autowired
	private ZcProductDaoI zcProductDao;

	@Autowired
	private ZcReadRecordServiceI zcReadRecordService;


	@Autowired
	private ZcFileServiceI zcFileService;
	@Autowired
	private ZcProductLikeServiceI zcProductLikeService;

	@Autowired
	private ZcProductMarginServiceI zcProductMarginService;

	@Autowired
	private ZcPayOrderServiceI zcPayOrderService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Override
	public DataGrid dataGrid(ZcProduct zcProduct, PageHelper ph) {
		List<ZcProduct> ol = new ArrayList<ZcProduct>();
		String hql = " from TzcProduct t ";
		DataGrid dg = dataGridQuery(hql, ph, zcProduct, zcProductDao);
		@SuppressWarnings("unchecked")
		List<TzcProduct> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcProduct t : l) {
				ZcProduct o = new ZcProduct();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid dataGridComplex(ZcProduct zcProduct, PageHelper ph) {
		List<ZcProduct> ol = new ArrayList<ZcProduct>();
		String hql = " from TzcProduct t ";
		if(!F.empty(zcProduct.getAtteId())) {
			hql = "select distinct t from TzcProduct t, TzcShieldorfans sf ";
		}

		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcProduct, params);
		List<TzcProduct> l = zcProductDao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(zcProductDao.count("select count(distinct t.id) " + hql.substring(hql.indexOf("from")) + where, params));
		if (l != null && l.size() > 0) {
			for (TzcProduct t : l) {
				ZcProduct o = new ZcProduct();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcProduct zcProduct, Map<String, Object> params) {
		String whereHql = "";	
		if (zcProduct != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcProduct.getPno())) {
				whereHql += " and t.pno like :pno";
				params.put("pno", "%%" + zcProduct.getPno() + "%%");
			}		
			if (!F.empty(zcProduct.getCategoryId())) {
				whereHql += " and t.categoryId = :categoryId";
				params.put("categoryId", zcProduct.getCategoryId());
			}
			if(zcProduct.getIsFreeShipping() != null) {
				whereHql += " and t.isFreeShipping = :isFreeShipping";
				params.put("isFreeShipping", zcProduct.getIsFreeShipping());
			}
			if (!F.empty(zcProduct.getApprovalDays())) {
				whereHql += " and t.approvalDays = :approvalDays";
				params.put("approvalDays", zcProduct.getApprovalDays());
			}		
			if (!F.empty(zcProduct.getContent())) {
				whereHql += " and t.content like :content";
				params.put("content", "%%" + zcProduct.getContent() + "%%");
			}		
			if (!F.empty(zcProduct.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", zcProduct.getStatus());
			} else {
				whereHql += " and t.status != 'PT01'";
			}
			if(zcProduct.getRealDeadline() != null) {
				whereHql += " and t.realDeadline <= :realDeadline";
				params.put("realDeadline", zcProduct.getRealDeadline());
			}
			if(zcProduct.getStartingTime() != null) {
				whereHql += " and t.startingTime >= :startingTime";
				params.put("startingTime", zcProduct.getStartingTime());
			}
			if(zcProduct.getQtype() != null) {
				whereHql += " and t.status = 'PT03'";
				if(zcProduct.getQtype() == 2) {
					// 距截拍24小时
					Calendar c = Calendar.getInstance();
					Date now = c.getTime();
					c.add(Calendar.HOUR_OF_DAY, 24);
					whereHql += " and t.realDeadline <= :endTime and t.realDeadline >= :startTime";
					params.put("startTime", now);
					params.put("endTime", c.getTime());
				}
			}
			if(zcProduct.getRemindLen() != null) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MINUTE, zcProduct.getRemindLen());
				whereHql += " and date_format(t.realDeadline, '%Y-%m-%d %H:%i') = :remindTime ";
				params.put("remindTime", DateUtil.format(c.getTime(), Constants.DATE_FORMAT_YMDHM));
			}
			if (!F.empty(zcProduct.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcProduct.getUserId());
			}		
			if (!F.empty(zcProduct.getAddUserId())) {
				whereHql += " and t.addUserId = :addUserId";
				params.put("addUserId", zcProduct.getAddUserId());
			}
			if(zcProduct.getIsDeleted() != null) {
				whereHql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", zcProduct.getIsDeleted());
			}
			if(zcProduct.getSeq() != null) {
				if(zcProduct.getSeq() == 0) {
					whereHql += " and t.seq = 0";
				} else {
					whereHql += " and t.seq >= :seq";
					params.put("seq", zcProduct.getSeq());
				}
			}
			if(zcProduct.getOthers() != null && zcProduct.getOthers() && !F.empty(zcProduct.getId())) {
				whereHql += " and t.id != :id";
				params.put("id", zcProduct.getId());
			}

			// 查询关注人拍品列表条件
			if(!F.empty(zcProduct.getAtteId())) {
				whereHql += " and t.addUserId = sf.objectById and sf.objectType='FS' and (sf.objectId = :atteId or t.addUserId = :atteId) ";
				params.put("atteId", zcProduct.getAtteId());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcProduct zcProduct) {
		zcProduct.setId(jb.absx.UUID.uuid());
		TzcProduct t = new TzcProduct();
		BeanUtils.copyProperties(zcProduct, t);
		zcProductDao.save(t);
	}

	@Override
	public ZcProduct get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcProduct t = zcProductDao.get("from TzcProduct t  where t.id = :id", params);
		ZcProduct o = new ZcProduct();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public ZcProduct get(String id, String userId) {
		ZcProduct product = get(id);

		// 获取封面、图片集合
		ZcFile file = new ZcFile();
		file.setObjectType(EnumConstants.OBJECT_TYPE.PRODUCT.getCode());
		file.setObjectId(id);
		file.setFileType("FT01");
		List<ZcFile> files = zcFileService.queryFiles(file);
		product.setFiles(files);
		if (CollectionUtils.isNotEmpty(files)) {
			product.setIcon(files.get(0).getFileHandleUrl());
		}

		if(!F.empty(userId)) {
			// 是否点赞
			ZcProductLike like = new ZcProductLike();
			like.setProductId(id);
			like.setUserId(userId);
			if (zcProductLikeService.get(like) == null) product.setLiked(false);
			else product.setLiked(true);
		}

		return product;
	}

	@Override
	public void edit(ZcProduct zcProduct) {
		TzcProduct t = zcProductDao.get(TzcProduct.class, zcProduct.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcProduct, t, new String[] {"id"},true);
			zcProduct.setContent(t.getContent());

			// 退回非交易人的保证金
			if(zcProduct.isRefund() && t.getMargin() > 0) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				completionService.submit(new Task<TzcProduct, Object>(t) {
					@Override
					public Boolean call() throws Exception {
						ZcProductMargin q = new ZcProductMargin();
						q.setProductId(getD().getId());
						q.setPayStatus("PS02");
						List<ZcProductMargin> margins = zcProductMarginService.query(q);
						for(ZcProductMargin margin : margins) {
							// 排除
							if((getD().getUserId() != null && getD().getUserId().equals(margin.getBuyUserId())) || !F.empty(margin.getRefundNo())) continue;

							completionService.submit(new Task<ZcProductMargin, Object>(margin) {
								@Override
								public Boolean call() throws Exception {
									ZcPayOrder payOrder = new ZcPayOrder();
									payOrder.setObjectId(getD().getId());
									payOrder.setObjectType("PO08");
									zcPayOrderService.refund(payOrder, "保证金退回", getD());

									// 保证金退回通知
									sendWxMessage.sendMarginRefundTemplateMessage(getD(), "拍品已截拍");

									return true;
								}
							});
						}
						return true;
					}
				});
			}

			if(zcProduct.isRefund() && !F.empty(zcProduct.getStatus())) {
				if("PT04".equals(zcProduct.getStatus())) {
					if(zcProduct.getRealDeadline() == null) zcProduct.setRealDeadline(t.getRealDeadline());
					if(F.empty(zcProduct.getPno())) zcProduct.setPno(t.getPno());
					if(F.empty(zcProduct.getAddUserId())) zcProduct.setAddUserId(t.getAddUserId());
					// 拍卖结果通知
					sendWxMessage.sendDealTemplateMessage(zcProduct);
				} else if("PT05".equals(zcProduct.getStatus())) {
					// 流拍通知
					sendWxMessage.sendTemplateMessage(zcProduct.getId(), "UNSOLD");
				}
			}
		}

	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcProductDao.executeHql("update TzcProduct t set t.isDeleted = 1 where t.id = :id", params);
		//zcProductDao.delete(zcProductDao.get(TzcProduct.class, id));
	}

	@Override
	public List<ZcProduct> query(ZcProduct zcProduct) {
		List<ZcProduct> ol = new ArrayList<ZcProduct>();
		String hql = " from TzcProduct t ";
		@SuppressWarnings("unchecked")
		List<TzcProduct> l = query(hql, zcProduct, zcProductDao, "startingTime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcProduct t : l) {
				ZcProduct o = new ZcProduct();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcProduct addReadAndDetail(String id, String userId) {
		ZcReadRecord read = new ZcReadRecord();
		read.setObjectType(EnumConstants.OBJECT_TYPE.PRODUCT.getCode());
		read.setObjectId(id);
		read.setUserId(userId);
		if(zcReadRecordService.get(read) == null) {
			zcReadRecordService.add(read);// pv

//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("id", id);
//			zcProductDao.executeSql("update zc_product t set t.read_count = ifnull(t.read_count, 0) + 1 where t.id = :id", params);
		}

		updateCount(id, 1, "read_count"); // uv

		return get(id, userId);
	}

	@Override
	public void updateCount(String id, int count, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		zcProductDao.executeSql("update zc_product t set t."+type+" = ifnull(t."+type+", 0) + "+count+" where t.id = :id", params);
	}

	@Override
	public void updateState(String productIds, String status) {
		zcProductDao.executeSql("update zc_product t set t.status = '"+status+"' where t.id in (" + productIds + ")");
	}

	@Override
	public long getCount(ZcProduct product) {
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(product, params);
		return zcProductDao.count("select count(*) from TzcProduct t " + where, params);
	}

	@Override
	public List<ZcProduct> getListByIds(String... productIds) {
		List<ZcProduct> ol = new ArrayList<ZcProduct>();
		List<TzcProduct> l = zcProductDao.getListByIds(productIds);
		if (l != null && l.size() > 0) {
			for (TzcProduct t : l) {
				ZcProduct o = new ZcProduct();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcProduct get(ZcProduct zcProduct) {
		String hql = " from TzcProduct t ";
		@SuppressWarnings("unchecked")
		List<TzcProduct> l = query(hql, zcProduct, zcProductDao);
		ZcProduct o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcProduct();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
