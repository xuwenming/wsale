package jb.service.impl;

import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.dao.ZcOrderDaoI;
import jb.model.TzcOrder;
import jb.pageModel.*;
import jb.service.BasedataServiceI;
import jb.service.ZcIntermediaryServiceI;
import jb.service.ZcOrderServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.order.OrderState;
import jb.util.Constants;
import jb.util.DateUtil;
import jb.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZcOrderServiceImpl extends BaseServiceImpl<ZcOrder> implements ZcOrderServiceI {

	@Autowired
	private ZcOrderDaoI zcOrderDao;

	@Resource
	Map<String,OrderState> orderStateMap;

	@Autowired
	private BasedataServiceI basedataService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcIntermediaryServiceI zcIntermediaryService;

	@Override
	public DataGrid dataGrid(ZcOrder zcOrder, PageHelper ph) {
		List<ZcOrder> ol = new ArrayList<ZcOrder>();
		String hql = " from TzcOrder t ";
		if(!F.empty(zcOrder.getPno()) || !F.empty(zcOrder.getSellerUserId()) || !F.empty(zcOrder.getBuyerUserId())) {
			hql = "select distinct t from TzcOrder t,TzcProduct p, TzcIntermediary im ";
		}
		DataGrid dg = dataGridQuery(hql, ph, zcOrder, zcOrderDao);
		@SuppressWarnings("unchecked")
		List<TzcOrder> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcOrder t : l) {
				ZcOrder o = new ZcOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	protected DataGrid dataGridQuery(String hql, PageHelper ph, ZcOrder zcOrder, BaseDaoI dao) {
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcOrder, params);
		List<TzcOrder> l = dao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		if(!F.empty(zcOrder.getPno()) || !F.empty(zcOrder.getSellerUserId()) || !F.empty(zcOrder.getBuyerUserId())) {
			dg.setTotal(dao.count("select count(distinct t.id) " + hql.substring(hql.indexOf("from")) + where, params));
		} else {
			dg.setTotal(dao.count("select count(*) " + hql + where, params));
		}

		dg.setRows(l);
		return dg;
	}

	protected String whereHql(ZcOrder zcOrder, Map<String, Object> params) {
		String whereHql = "";	
		if (zcOrder != null) {
			whereHql += " where 1=1 ";

			if(!F.empty(zcOrder.getPno()) || !F.empty(zcOrder.getSellerUserId()) || !F.empty(zcOrder.getBuyerUserId())) {
				if(!F.empty(zcOrder.getPno())) {
					whereHql += " and (t.productId = p.id and p.pno like :pno) or (t.productId = im.id and im.imNo like :pno)";
					params.put("pno", "%%" + zcOrder.getPno() + "%%");
				}
				if(!F.empty(zcOrder.getSellerUserId())) {
					whereHql += " and (t.productId = p.id and p.addUserId = :sellerUserId) or (t.productId = im.id and im.sellUserId = :sellerUserId)";
					params.put("sellerUserId", zcOrder.getSellerUserId());
				}
				if(!F.empty(zcOrder.getBuyerUserId())) {
					whereHql += " and (t.productId = p.id and p.userId = :buyerUserId) or (t.productId = im.id and im.userId = :buyerUserId)";
					params.put("buyerUserId", zcOrder.getBuyerUserId());
				}
			}

			if (!F.empty(zcOrder.getOrderNo())) {
				whereHql += " and t.orderNo like :orderNo";
				params.put("orderNo", "%%" + zcOrder.getOrderNo() + "%%");
			}		
			if (!F.empty(zcOrder.getProductId())) {
				whereHql += " and t.productId = :productId";
				params.put("productId", zcOrder.getProductId());
			}		
			if (!F.empty(zcOrder.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcOrder.getPayStatus());
			}		
			if (!F.empty(zcOrder.getSendStatus())) {
				whereHql += " and t.sendStatus = :sendStatus";
				params.put("sendStatus", zcOrder.getSendStatus());
			}		
			if (!F.empty(zcOrder.getBackStatus())) {
				whereHql += " and t.backStatus = :backStatus";
				params.put("backStatus", zcOrder.getBackStatus());
			}		
			if (!F.empty(zcOrder.getOrderStatus())) {
				whereHql += " and t.orderStatus = :orderStatus";
				params.put("orderStatus", zcOrder.getOrderStatus());
			}
			if(zcOrder.getIsCommented() != null) {
				whereHql += " and t.isCommented = :isCommented";
				params.put("isCommented", zcOrder.getIsCommented());
			}
			if(zcOrder.getIsIntermediary() != null) {
				whereHql += " and t.isIntermediary = :isIntermediary";
				params.put("isIntermediary", zcOrder.getIsIntermediary());
			}
			if (!F.empty(zcOrder.getAddUserId())) {
				whereHql += " and (exists (select 1 from TzcProduct p where p.id = t.productId and p.isDeleted = 0 and t.isIntermediary = 0 and (p.addUserId = :addUserId or p.userId = :addUserId))";
				whereHql += " or exists (select 1 from TzcIntermediary i where i.id = t.productId and t.isIntermediary = 1 and (i.sellUserId = :addUserId or i.userId = :addUserId)))";
				params.put("addUserId", zcOrder.getAddUserId());
			}
			if(zcOrder.getAddtime() != null) {
				whereHql += " and date_format(t.addtime, '%Y-%m-%d %H:%i:%s') = :addtime";
				params.put("addtime", DateUtil.format(zcOrder.getAddtime(), Constants.DATE_FORMAT));
			}
			if(zcOrder.getPaytime() != null) {
				whereHql += " and date_format(t.paytime, '%Y-%m-%d %H:%i:%s') = :paytime";
				params.put("paytime", DateUtil.format(zcOrder.getPaytime(), Constants.DATE_FORMAT));
			}
			if(zcOrder.getIsXiaoer() != null && zcOrder.getIsXiaoer()) {
				whereHql += " and exists (select 1 from TzcOrderXiaoer xr where xr.orderId = t.id and xr.status = 'XS01') and t.orderStatus != 'OS10' and t.orderStatus != 'OS15'";
			}
		}
		return whereHql;
	}

	@Override
	public void add(ZcOrder zcOrder) {
		zcOrder.setId(jb.absx.UUID.uuid());
		TzcOrder t = new TzcOrder();
		BeanUtils.copyProperties(zcOrder, t);
		zcOrderDao.save(t);
	}

	@Override
	public ZcOrder get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcOrder t = zcOrderDao.get("from TzcOrder t  where t.id = :id", params);
		ZcOrder o = new ZcOrder();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcOrder zcOrder) {
		TzcOrder t = zcOrderDao.get(TzcOrder.class, zcOrder.getId());
		if (t != null) {
			zcOrder.setProductId(t.getProductId());
			zcOrder.setOrderNo(t.getOrderNo());
			zcOrder.setIsIntermediary(t.getIsIntermediary());
			zcOrder.setTotalPrice(t.getTotalPrice());
			MyBeanUtils.copyProperties(zcOrder, t, new String[] { "id" , "addtime" },true);

			if(F.empty(zcOrder.getReturnApplyReason()) && !F.empty(t.getReturnApplyReason()))
				zcOrder.setReturnApplyReason(t.getReturnApplyReason());
		}
	}

	@Override
	public void delete(String id) {
		zcOrderDao.delete(zcOrderDao.get(TzcOrder.class, id));
	}

	@Override
	public List<ZcOrder> query(ZcOrder zcOrder) {
		List<ZcOrder> ol = new ArrayList<ZcOrder>();
		String hql = " from TzcOrder t ";
		@SuppressWarnings("unchecked")
		List<TzcOrder> l = query(hql, zcOrder, zcOrderDao);
		if (l != null && l.size() > 0) {
			for (TzcOrder t : l) {
				ZcOrder o = new ZcOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public List<ZcOrder> query(ZcOrder zcOrder, String otherWhere) {
		List<ZcOrder> ol = new ArrayList<ZcOrder>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TzcOrder t ";
		otherWhere = F.empty(otherWhere) ? "" : otherWhere;
		List<TzcOrder> l = zcOrderDao.find(hql + whereHql(zcOrder, params) + otherWhere, params);
		if (l != null && l.size() > 0) {
			for (TzcOrder t : l) {
				ZcOrder o = new ZcOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcOrder get(ZcOrder zcOrder) {
		String hql = " from TzcOrder t ";
		@SuppressWarnings("unchecked")
		List<TzcOrder> l = query(hql, zcOrder, zcOrderDao);
		ZcOrder o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcOrder();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public Map<String, Object> orderCount(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addUserId", userId);
		String sql = "select count(case when t.pay_status='PS01' and t.order_status='OS01' then t.id end)  unpay_count, "
				+ " count(case when t.send_status='SS03' and t.order_status='OS05' then t.id end)  unreceipt_count, "
				+ " count(case when t.send_status='SS01' and t.order_status='OS02' then t.id end)  undeliver_count, "
				+ " count(case when t.isCommented=0 and t.order_status='OS10' and (exists(select 1 from zc_product p1 where p1.id=t.product_id and p1.user_id = :addUserId) or exists(select 1 from zc_intermediary i1 where i1.id=t.product_id and i1.user_id = :addUserId)) then t.id end)  uncomment_count "
				+ " from zc_order t where (exists (select 1 from zc_product p where p.id = t.product_id and p.isDeleted = 0 and t.is_intermediary = 0 and (p.addUserId = :addUserId or p.user_id = :addUserId))"
				+ " or exists (select 1 from zc_intermediary i where i.id = t.product_id and t.is_intermediary = 1 and (i.sell_user_id = :addUserId or i.user_id = :addUserId)))";
		List<Map> l = zcOrderDao.findBySql2Map(sql, params);
		return l.get(0);
	}

	@Override
	public Map<String, Object> orderAmountCount(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addUserId", userId);
		String sql = "select ifnull(sum(case when t.pay_status='PS01' and t.order_status='OS01' then t.total_price end), 0) unpay_amount, "
				+ " ifnull(sum(case when t.send_status='SS03' and t.order_status='OS05' then t.total_price end), 0) unreceipt_amount, "
				+ " ifnull(sum(case when t.send_status='SS01' and t.order_status='OS02' then t.total_price end), 0) undeliver_amount "
				+ " from zc_order t "
				+ " left join zc_product p on p.id = t.product_id and t.is_intermediary = 0 "
				+ " left join zc_intermediary i on i.id = t.product_id and t.is_intermediary = 1 "
				+ " where (p.isDeleted = 0 and (p.addUserId = :addUserId or p.user_id = :addUserId)) or (i.sell_user_id = :addUserId or i.user_id = :addUserId)";
		List<Map> l = zcOrderDao.findBySql2Map(sql, params);
		return l.get(0);
	}

	@Override
	public Map<String, Object> orderStatusCount(String userId) {
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			BaseData baseData = new BaseData();
			baseData.setBasetypeCode("RA");
			List<BaseData> bds = basedataService.getBaseDatas(baseData);
			if(CollectionUtils.isNotEmpty(bds)) {
				for(BaseData bd : bds) {
					String desc = bd.getDescription().replaceAll("：", ":");
					if(userId.equals(desc.split(":")[1])) {
						String[] nums = bd.getName().split("-");
						result.put("OS10",   BigInteger.valueOf(Long.valueOf(nums[0])));
						result.put("B_OS15", BigInteger.valueOf(Long.valueOf(nums[1])));
						result.put("S_OS15", BigInteger.valueOf(Long.valueOf(nums[2])));
						break;
					}
				}
				if(!result.isEmpty()) return result;
			}

		} catch (Exception e) {
			System.out.println("用户-信誉违约设置方法orderStatusCount有错误！");
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addUserId", userId);
		String sql = "select count(case when t.order_status='OS10' and (t.face_status is null or t.face_status <> 'FS02') then t.id end) OS10, " // 信誉排除当面交易
				+ " count(case when p.addUserId = :addUserId and t.order_status='OS15' and t.order_close_reason = 'OC002' then t.id end)  S_OS15, "
				+ " count(case when p.user_id = :addUserId and t.order_status='OS15' and t.order_close_reason = 'OC001' then t.id end)  B_OS15 "
				+ " from zc_order t "
				+ " left join zc_product p on p.id = t.product_id and t.is_intermediary = 0 "
				+ " left join zc_intermediary i on i.id = t.product_id and t.is_intermediary = 1 "
				+ " where (p.isDeleted = 0 and (p.addUserId = :addUserId or p.user_id = :addUserId)) or (i.sell_user_id = :addUserId or i.user_id = :addUserId)";
		List<Map> l = zcOrderDao.findBySql2Map(sql, params);
		return l.get(0);
	}

	@Override
	public void transform(ZcOrder zcOrder) {
		OrderState orderState;
		String state;
		if(F.empty(zcOrder.getId())){
			orderState = orderStateMap.get("order01StateImpl");
			orderState.handle(zcOrder);
		}else{
			ZcOrder zcOrder1 = get(zcOrder.getId());
			state = zcOrder1.getOrderStatus().replace("OS","");
			orderState = orderStateMap.get("order"+state+"StateImpl");
			orderState.next(zcOrder).handle(zcOrder);
		}
	}

	@Override
	public OrderProductInfo getProductInfo(ZcOrder order) {
		OrderProductInfo info = new OrderProductInfo();
		if(order.getIsIntermediary()) {
			ZcIntermediary im = zcIntermediaryService.getDetail(order.getProductId());
			info.setId(im.getBbs().getId());
			info.setPno(im.getImNo());
			info.setIcon(im.getBbs().getIcon());
			info.setContent(im.getBbs().getBbsTitle());
			info.setSellerUserId(im.getSellUserId());
			info.setBuyerUserId(im.getUserId());
			info.setStartingTime(im.getAddtime());
			info.setHammerTime(im.getAddtime());
		} else {
			ZcProduct p = zcProductService.get(order.getProductId(), null);
			info.setId(p.getId());
			info.setPno(p.getPno());
			info.setIcon(p.getIcon());
			info.setContent(p.getContent());
			info.setSellerUserId(p.getAddUserId());
			info.setBuyerUserId(p.getUserId());
			info.setStartingTime(p.getStartingTime());
			info.setRealDeadline(p.getRealDeadline());
			info.setHammerTime(p.getHammerTime());
			info.setMargin(p.getMargin());
		}
		info.setTotalPrice(order.getTotalPrice());

		return info;
	}


	// TODO (james) 高级搜索(小二介入问题存在争议需探讨)
	@Override
	public DataGrid dataGridComp(ZcOrder zcOrder, ZcProduct zcProduct,Boolean isXiaoer, PageHelper ph) {
		List<ZcOrder> ol = new ArrayList<ZcOrder>();
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT z.id id,z.order_no orderNo,z.product_id productId,z.isCommented isCommented,z.delay_time delayTime," +
				"z.delay_times delayTimes,z.deliver_time deliverTime,z.receive_time receiveTime," +
				"z.return_apply_time returnApplyTime, z.return_time returnTime,z.compensation compensation," +
				"z.addUserId addUserId,z.addtime addtime,z.updateUserId updateUserId, z.updatetime updatetime," +
				"z.pay_status payStatus, z.paytime paytime,z.send_status sendStatus,z.back_status backStatus," +
				"z.order_status orderStatus, z.order_status_time orderStatusTime,z.face_to_face faceToFace FROM zc_order z,zc_product p ";
		String sql1 = "SELECT COUNT(*) FROM zc_order z,zc_product p 	";
		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer1 = new StringBuffer();
		buffer.append(sql);
		buffer1.append(sql1);
		if(isXiaoer!=null){
			buffer.append(",zc_order_xiaoer x WHERE z.product_id=p.id AND x.order_id=z.id AND x.status='XS01'");
			buffer1.append(",zc_order_xiaoer x WHERE z.product_id=p.id AND x.order_id=z.id AND x.status='XS01'");
		}else
		{
			buffer.append("WHERE z.product_id=p.id ");
			buffer1.append("WHERE z.product_id=p.id ");
		}
		if (zcProduct.getRealDeadline()!=null){
			params.put("realDeadline",zcProduct.getRealDeadline());
			buffer.append(" and (p.real_deadline BETWEEN :realDeadline AND CURRENT_DATE ())");
			buffer1.append(" and (p.real_deadline BETWEEN :realDeadline AND CURRENT_DATE ())");
		}
		if(!F.empty(zcProduct.getContent())){
			params.put("content","%%"+zcProduct.getContent()+"%%");
			buffer.append(" and p.content LIKE :content");
			buffer1.append(" and p.content LIKE :content");
		}

		//拍品状态
		if(!F.empty(zcProduct.getStatus())){
			buffer.append(" and p.status = :status " );
			buffer1.append(" and p.status = :status " );
			params.put("status", zcProduct.getStatus());
		}
		//是否包退
		if(!F.empty(zcProduct.getApprovalDays())){
			buffer.append(" AND p.approval_days = :approvalDays" );
			buffer1.append(" AND p.approval_days = :approvalDays" );
			params.put("approvalDays",zcProduct.getApprovalDays());
		}
		//是否包邮
		if(zcProduct.getIsFreeShipping()!=null){
			buffer.append(" AND p.is_free_shipping = :isFreeShipping" );
			buffer1.append(" AND p.is_free_shipping = :isFreeShipping" );
			params.put("isFreeShipping", zcProduct.getIsFreeShipping());
		}
		//交易中状态
		if(zcOrder.getOrderStatus()!=null){
			buffer.append(" AND z.order_status IS NULL" );
			buffer1.append(" AND z.order_status IS NULL" );
		}

		List<Map> l = zcOrderDao.findBySql2Map(buffer.toString(), params, ph.getPage(), ph.getRows());

		if (l != null && l.size() > 0) {
			for (Map m : l) {
				ZcOrder o = new ZcOrder();
				//去除Date为空的情况
				if(m.get("delayTime")==null){
					m.remove("delayTime");
				}
				if(m.get("updatetime")==null){
					m.remove("updatetime");
				}
				if(m.get("deliverTime")==null){
					m.remove("deliverTime");
				}
				if(m.get("receiveTime")==null){
					m.remove("receiveTime");
				}
				if(m.get("returnApplyTime")==null){
					m.remove("returnApplyTime");
				}
				if(m.get("addtime")==null){
					m.remove("addtime");
				}

				if(m.get("paytime")==null){
					m.remove("paytime");
				}
				if(m.get("returnTime")==null){
					m.remove("returnTime");
				}

				if(m.get("orderStatusTime")==null){
					m.remove("orderStatusTime");
				}
				try {
					org.apache.commons.beanutils.BeanUtils.populate(o, m);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				ol.add(o);
			}
		}
		dg.setRows(ol);
		BigInteger count = zcOrderDao.countBySql(buffer1.toString(), params);
		dg.setTotal(count == null ? 0 : count.longValue());
		return dg;
	}

}
