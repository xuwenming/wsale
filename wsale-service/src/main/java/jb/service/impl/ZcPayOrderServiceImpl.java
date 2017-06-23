package jb.service.impl;

import jb.absx.F;
import jb.dao.ZcPayOrderDaoI;
import jb.model.TzcPayOrder;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.order.OrderState;
import jb.util.EnumConstants;
import jb.util.MyBeanUtils;
import jb.util.PathUtil;
import jb.util.Util;
import jb.util.wx.HttpUtil;
import jb.util.wx.PayCommonUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.XMLUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ZcPayOrderServiceImpl extends BaseServiceImpl<ZcPayOrder> implements ZcPayOrderServiceI {

	@Autowired
	private ZcPayOrderDaoI zcPayOrderDao;

	@Autowired
	private ZcPositionApplyServiceI zcPositionApplyService;

	@Autowired
	private ZcAuthenticationServiceI zcAuthenticationService;

	@Autowired
	private ZcBestProductServiceI zcBestProductService;

	@Autowired
	private ZcRewardServiceI zcRewardService;

	@Autowired
	private ZcTopicServiceI zcTopicService;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Resource(name = "order02StateImpl")
	private OrderState order02State;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private ZcWalletDetailServiceI zcWalletDetailService;

	@Autowired
	private ZcProtectionServiceI zcProtectionService;

	@Autowired
	private ZcProductMarginServiceI zcProductMarginService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Autowired
	private UserServiceI userService;

	@Override
	public DataGrid dataGrid(ZcPayOrder zcPayOrder, PageHelper ph) {
		List<ZcPayOrder> ol = new ArrayList<ZcPayOrder>();
		String hql = " from TzcPayOrder t ";
		DataGrid dg = dataGridQuery(hql, ph, zcPayOrder, zcPayOrderDao);
		@SuppressWarnings("unchecked")
		List<TzcPayOrder> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcPayOrder t : l) {
				ZcPayOrder o = new ZcPayOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ZcPayOrder zcPayOrder, Map<String, Object> params) {
		String whereHql = "";	
		if (zcPayOrder != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcPayOrder.getOrderNo())) {
				whereHql += " and t.orderNo = :orderNo";
				params.put("orderNo", zcPayOrder.getOrderNo());
			}		
			if (!F.empty(zcPayOrder.getObjectType())) {
				whereHql += " and t.objectType = :objectType";
				params.put("objectType", zcPayOrder.getObjectType());
			}		
			if (!F.empty(zcPayOrder.getObjectId())) {
				whereHql += " and t.objectId = :objectId";
				params.put("objectId", zcPayOrder.getObjectId());
			}		
			if (!F.empty(zcPayOrder.getChannel())) {
				whereHql += " and t.channel = :channel";
				params.put("channel", zcPayOrder.getChannel());
			}		
			if (!F.empty(zcPayOrder.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", zcPayOrder.getUserId());
			}		
			if (!F.empty(zcPayOrder.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcPayOrder.getPayStatus());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ZcPayOrder zcPayOrder) {
		zcPayOrder.setId(jb.absx.UUID.uuid());
		TzcPayOrder t = new TzcPayOrder();
		BeanUtils.copyProperties(zcPayOrder, t);
		zcPayOrderDao.save(t);
	}

	@Override
	public ZcPayOrder get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcPayOrder t = zcPayOrderDao.get("from TzcPayOrder t  where t.id = :id", params);
		ZcPayOrder o = new ZcPayOrder();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcPayOrder zcPayOrder) {
		TzcPayOrder t = zcPayOrderDao.get(TzcPayOrder.class, zcPayOrder.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcPayOrder, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void editByParam(ZcPayOrder payOrder) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", payOrder.getOrderNo());
		TzcPayOrder t = zcPayOrderDao.get("from TzcPayOrder t where t.orderNo = :orderNo", params);
		if (t != null) {
			if("PS01".equals(t.getPayStatus())) {
				payOrder.setPaytime(new Date());
				MyBeanUtils.copyProperties(payOrder, t, new String[] {"id"},true);

				if("PO01".equals(t.getObjectType())) {
					ZcPositionApply pa = new ZcPositionApply();
					pa.setId(t.getObjectId());
					pa.setPayStatus(payOrder.getPayStatus());
					pa.setPaytime(payOrder.getPaytime());
					zcPositionApplyService.edit(pa);
				} else if("PO02".equals(t.getObjectType())) {
					ZcAuthentication auth = new ZcAuthentication();
					auth.setId(t.getObjectId());
					auth.setPayStatus(payOrder.getPayStatus());
					auth.setPaytime(payOrder.getPaytime());
					zcAuthenticationService.edit(auth);
				} else if("PO03".equals(t.getObjectType()) || "PO09".equals(t.getObjectType())){
					ZcBestProduct bp = new ZcBestProduct();
					bp.setId(t.getObjectId());
					bp.setPayStatus(payOrder.getPayStatus());
					bp.setPaytime(payOrder.getPaytime());
					zcBestProductService.edit(bp);
				} else if("PO04".equals(t.getObjectType())) {
					ZcReward reward = new ZcReward();
					reward.setId(t.getObjectId());
					reward.setPayStatus(payOrder.getPayStatus());
					reward.setPaytime(payOrder.getPaytime());
					zcRewardService.edit(reward);

					String userId = null, desc = "";
					StringBuffer buffer = new StringBuffer();
					if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(payOrder.getAttachType())) {
						zcForumBbsService.updateCount(reward.getObjectId(), 1, "bbs_reward");
						userId = zcForumBbsService.get(reward.getObjectId()).getAddUserId();
						desc = "帖子打赏";

						User rewardUser = userService.getByZc(t.getUserId()); // 打赏人
						buffer.append("您收到来自『" + rewardUser.getNickname() + "』的赏金\"" + new DecimalFormat("#,###0.00").format(t.getTotalFee()) + "元。\"").append("\n\n");
						buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + reward.getObjectId()) +"'>点击查看</a>");

					} else if(EnumConstants.OBJECT_TYPE.TOPIC.getCode().equals(payOrder.getAttachType())) {
						zcTopicService.updateCount(reward.getObjectId(), 1, "topic_reward");
						userId = zcTopicService.get(reward.getObjectId()).getAddUserId();
						desc = "专题打赏";

						User rewardUser = userService.getByZc(t.getUserId()); // 打赏人
						buffer.append("您收到来自『" + rewardUser.getNickname() + "』的赏金\"" + new DecimalFormat("#,###0.00").format(t.getTotalFee()) + "元。\"").append("\n\n");
						buffer.append("<a href='"+ PathUtil.getUrlPath("api/apiTopic/topicDetail?id=" + reward.getObjectId()) +"'>点击查看</a>");

					}

					if(!F.empty(userId)) {
						// 更新发帖人钱包余额
						// 新增钱包收支明细
						ZcWalletDetail walletDetail = new ZcWalletDetail();
						walletDetail.setUserId(userId);
						walletDetail.setOrderNo(t.getOrderNo());
						walletDetail.setAmount(t.getTotalFee());
						walletDetail.setWtype("WT05"); // 打赏收入
						walletDetail.setDescription(desc);
						walletDetail.setChannel(t.getChannel());
						zcWalletDetailService.addAndUpdateWallet(walletDetail);

						// 推送打赏消息
						if(!F.empty(buffer.toString()))
							sendWxMessage.sendCustomMessageByUserId(userId, buffer.toString());
							//sendWxMessage.sendCustomMessage(t.getObjectId(), "bbs_r", null);
					}

				} else if("PO05".equals(t.getObjectType())) {
					ZcOrder order = new ZcOrder();
					order.setId(t.getObjectId());
					order.setPayStatus(payOrder.getPayStatus());
					order.setPaytime(payOrder.getPaytime());

					order02State.handle(order);
				} else if("PO06".equals(t.getObjectType())) {
					// 修改钱包余额
//					updateWallet(t.getUserId(), t.getTotalFee());

					// 新增钱包收支明细
					ZcWalletDetail walletDetail = new ZcWalletDetail();
					walletDetail.setUserId(t.getUserId());
					walletDetail.setOrderNo(t.getOrderNo());
					walletDetail.setAmount(t.getTotalFee());
					walletDetail.setWtype("WT01"); // 充值
					walletDetail.setDescription("余额充值");
					walletDetail.setChannel(t.getChannel());
					zcWalletDetailService.addAndUpdateWallet(walletDetail);

					// 设置支付订单的业务id
					payOrder.setId(t.getId());
					payOrder.setObjectId(walletDetail.getId());
					edit(payOrder);
				} else if("PO07".equals(t.getObjectType())) {
					// 修改用户消保金余额
					zcWalletService.updateProtection(t.getUserId(), t.getTotalFee());

					// 修改消保金流水记录支付状态
					ZcProtection protection = new ZcProtection();
					protection.setId(t.getObjectId());
					protection.setPayStatus(payOrder.getPayStatus());
					protection.setPaytime(payOrder.getPaytime());
					zcProtectionService.edit(protection);
				} else if("PO08".equals(t.getObjectType())) {
					// 修改保证金支付状态
					ZcProductMargin margin = new ZcProductMargin();
					margin.setId(t.getObjectId());
					margin.setPayStatus(payOrder.getPayStatus());
					margin.setPaytime(payOrder.getPaytime());
					zcProductMarginService.edit(margin);
				}
			}
		}
	}



	@Override
	public void updateWallet(String userId, double amount) {
		ZcWallet q = new ZcWallet();
		q.setUserId(userId);
		ZcWallet wallet = zcWalletService.get(q);
		if(wallet == null) {
			wallet = q;
			wallet.setAmount(amount);
			wallet.setFrozenAmount(0.0);
			wallet.setUserId(userId);
			zcWalletService.add(wallet);
		} else {
			wallet.setAmount(wallet.getAmount() + amount);
			wallet.setUpdatetime(new Date());
			zcWalletService.edit(wallet);
		}
	}

	@Override
	public void delete(String id) {
		zcPayOrderDao.delete(zcPayOrderDao.get(TzcPayOrder.class, id));
	}

	@Override
	public List<ZcPayOrder> query(ZcPayOrder zcPayOrder) {
		List<ZcPayOrder> ol = new ArrayList<ZcPayOrder>();
		String hql = " from TzcPayOrder t ";
		@SuppressWarnings("unchecked")
		List<TzcPayOrder> l = query(hql, zcPayOrder, zcPayOrderDao);
		if (l != null && l.size() > 0) {
			for (TzcPayOrder t : l) {
				ZcPayOrder o = new ZcPayOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcPayOrder get(ZcPayOrder zcPayOrder) {
		String hql = " from TzcPayOrder t ";
		@SuppressWarnings("unchecked")
		List<TzcPayOrder> l = query(hql, zcPayOrder, zcPayOrderDao);
		ZcPayOrder o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcPayOrder();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public ZcPayOrder refund(ZcPayOrder payOrder, String walletDesc) {
		return refund(payOrder, walletDesc, null);
	}

	@Override
	public ZcPayOrder refund(ZcPayOrder payOrder, String walletDesc, ZcProductMargin margin) {
		try {
			boolean isRefundServiceFee = payOrder.isRefundServiceFee();
			payOrder = get(payOrder);

			if (payOrder != null) {
				long total_fee = (long)(payOrder.getTotalFee()*100);
				long refund_fee = total_fee;
				if(payOrder.getServiceFee() > 0 && !isRefundServiceFee) { // 不退回技术服务费
					refund_fee = total_fee - payOrder.getServiceFee();
				}
				boolean flag = true;
				String refund_no = !F.empty(payOrder.getRefundNo()) ? payOrder.getRefundNo() : Util.CreateRefundNo();
				if ("CS01".equals(payOrder.getChannel())) { // 微信退款
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("total_fee", total_fee);
					params.put("refund_fee", refund_fee);
					params.put("trade_no", payOrder.getOrderNo());
					params.put("refund_no", refund_no);
					String requestXml = PayCommonUtil.requestRefundXML(params);
					System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
					String result = HttpUtil.httpsRequestSSL(WeixinUtil.REFUND_URL, requestXml);
					System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
					Map<String, String> resultMap = XMLUtil.doXMLParse(result);
					if (resultMap == null || F.empty(resultMap.get("result_code")) || !resultMap.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
						flag = false;
					}
				} else if ("CS02".equals(payOrder.getChannel())) { // 钱包退款
//					updateWallet(payOrder.getUserId(), payOrder.getTotalFee());
					// 新增钱包收支明细
					ZcWalletDetail walletDetail = new ZcWalletDetail();
					walletDetail.setUserId(payOrder.getUserId());
					walletDetail.setOrderNo(payOrder.getOrderNo());
					walletDetail.setAmount(((double)refund_fee)/100);
					walletDetail.setWtype("WT04"); // 退款
					if(payOrder.getServiceFee() > 0) {
						double serviceFee = ((double)payOrder.getServiceFee())/100;
						if(isRefundServiceFee) {
							walletDesc += "(含"+serviceFee+"元技术服务费)";
						} else {
							walletDesc += "(已扣除"+serviceFee+"元技术服务费)";
						}
					}
					walletDetail.setDescription(walletDesc);
					walletDetail.setChannel("CS02");
					zcWalletDetailService.addAndUpdateWallet(walletDetail);
				}
				if(flag) {
					Date refundtime = new Date();
					ZcPayOrder o = new ZcPayOrder();
					o.setId(payOrder.getId());
					o.setRefundNo(refund_no);
					o.setRefundFee(refund_fee);
					o.setRefundtime(refundtime);
					edit(o);

					if(margin != null && !F.empty(margin.getId())) {
						margin.setRefundNo(refund_no);
						margin.setRefundtime(refundtime);
						zcProductMarginService.edit(margin);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return payOrder;
	}

}
