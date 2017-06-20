package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcOrderDaoI;
import jb.model.TzcOrder;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcOrder;
import jb.pageModel.ZcProduct;
import jb.service.ZcOrderServiceI;
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

	@Override
	public DataGrid dataGrid(ZcOrder zcOrder, PageHelper ph) {
		List<ZcOrder> ol = new ArrayList<ZcOrder>();
		String hql = " from TzcOrder t ";
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

	protected String whereHql(ZcOrder zcOrder, Map<String, Object> params) {
		String whereHql = "";	
		if (zcOrder != null) {
			whereHql += " where 1=1 ";
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
			if (!F.empty(zcOrder.getAddUserId())) {
				whereHql += " and exists (select 1 from TzcProduct p where p.id = t.productId and (p.addUserId = :addUserId or p.userId = :addUserId))";
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
				+ " count(case when t.isCommented=0 and t.order_status='OS10' and exists(select 1 from zc_product p1 where p1.id=t.product_id and p1.user_id = :addUserId) then t.id end)  uncomment_count "
				+ " from zc_order t where exists (select 1 from zc_product p where p.id = t.product_id and (p.addUserId = :addUserId or p.user_id = :addUserId))";
		List<Map> l = zcOrderDao.findBySql2Map(sql, params);
		return l.get(0);
	}

	@Override
	public Map<String, Object> orderAmountCount(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addUserId", userId);
		String sql = "select ifnull(sum(case when t.pay_status='PS01' and t.order_status='OS01' then p.current_price end), 0) unpay_amount, "
				+ " ifnull(sum(case when t.send_status='SS03' and t.order_status='OS05' then p.current_price end), 0) unreceipt_amount, "
				+ " ifnull(sum(case when t.send_status='SS01' and t.order_status='OS02' then p.current_price end), 0) undeliver_amount "
				+ " from zc_product p left join zc_order t on t.product_id = p.id "
				+ " where p.addUserId = :addUserId or p.user_id = :addUserId";
		List<Map> l = zcOrderDao.findBySql2Map(sql, params);
		return l.get(0);
	}

	@Override
	public Map<String, Object> orderStatusCount(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addUserId", userId);
		String sql = "select count(case when t.order_status='OS10' and (t.face_status is null or t.face_status <> 'FS02') then t.id end) OS10, " // 信誉排除当面交易
				+ " count(case when p.addUserId = :addUserId and t.order_status='OS15' and t.order_close_reason = 'OC002' then t.id end)  S_OS15, "
				+ " count(case when p.user_id = :addUserId and t.order_status='OS15' and t.order_close_reason = 'OC001' then t.id end)  B_OS15 "
				+ " from zc_order t left join zc_product p on p.id = t.product_id where p.addUserId = :addUserId or p.user_id = :addUserId";
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

}
