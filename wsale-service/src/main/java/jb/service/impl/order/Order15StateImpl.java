package jb.service.impl.order;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.Date;

/**
 * 交易关闭
 * Created by john on 16/10/30.
 */
@Service("order15StateImpl")
public class Order15StateImpl implements OrderState {
    @Autowired
    private ZcOrderServiceI orderService;

    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcPayOrderServiceI zcPayOrderService;

    @Autowired
    private ZcWalletDetailServiceI zcWalletDetailService;

    @Autowired
    private ZcProductMarginServiceI zcProductMarginService;

    @Autowired
    private ZcIntermediaryLogServiceI zcIntermediaryLogService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    @Override
    public String getStateName() {
        return "15";
    }

    @Override
    public void handle(ZcOrder zcOrder) {
        try {
            // 修改订单状态为已关闭，关闭原因等
            zcOrder.setOrderStatus(prefix + getStateName());
            zcOrder.setOrderStatusTime(new Date());
            orderService.edit(zcOrder);

            // 修改拍品状态:已失败
            if (!F.empty(zcOrder.getProductId())) {
                if(zcOrder.getIsIntermediary()) {
                    ZcIntermediary im = new ZcIntermediary();
                    im.setId(zcOrder.getProductId());
                    im.setStatus("IS03");

                    ZcIntermediaryLog log = new ZcIntermediaryLog();
                    log.setLogType("IL06");
                    log.setContent(zcOrder.getOrderCloseReasonZh());
                    log.setIntermediary(im);

                    zcIntermediaryLogService.addAndUpdateIM(log);
                } else {
                    ZcProduct p = new ZcProduct();
                    p.setId(zcOrder.getProductId());
                    p.setStatus("PT06");
                    p.setUpdatetime(new Date());
                    zcProductService.edit(p);
                }
            }

            // 退款
            if ((!F.empty(zcOrder.getPayStatus()) && "PS02".equals(zcOrder.getPayStatus())) || "RS05".equals(zcOrder.getBackStatus())) {
                ZcPayOrder payOrder = new ZcPayOrder();
                payOrder.setObjectId(zcOrder.getId());
                payOrder.setObjectType("PO05");

                if("OC003".equals(zcOrder.getOrderCloseReason()) && !"RR99".equals(zcOrder.getReturnApplyReason())) {
                    payOrder.setRefundServiceFee(true);
                }

                payOrder = zcPayOrderService.refund(payOrder, "拍品退款");

                // 给买家发送退款通知
                sendWxMessage.sendRefundTemplateMessage(zcOrder, payOrder.getServiceFee() == null ? 0L : payOrder.getServiceFee());
            }

            // 买家违约，保证金打给卖家
            if(!F.empty(zcOrder.getOrderCloseReason()) && "OC001".equals(zcOrder.getOrderCloseReason())) {
                if(!zcOrder.getIsIntermediary()) {
                    final CompletionService completionService = CompletionFactory.initCompletion();
                    completionService.submit(new Task<ZcOrder, Boolean>(zcOrder) {
                        @Override
                        public Boolean call() throws Exception {
                            ZcProduct product = zcProductService.get(getD().getProductId());
                            if(product.getMargin() > 0 && !F.empty(product.getUserId())) {
                                ZcProductMargin q = new ZcProductMargin();
                                q.setProductId(product.getId());
                                q.setPayStatus("PS02");
                                q.setBuyUserId(product.getUserId());
                                ZcProductMargin margin = zcProductMarginService.get(q);
                                if(margin != null &&  F.empty(margin.getRefundNo())) {
                                    ZcPayOrder payOrder = new ZcPayOrder();
                                    payOrder.setObjectId(margin.getId());
                                    payOrder.setObjectType("PO08");
                                    payOrder = zcPayOrderService.get(payOrder);

                                    // 新增钱包收支明细
                                    ZcWalletDetail walletDetail = new ZcWalletDetail();
                                    walletDetail.setUserId(product.getAddUserId());
                                    walletDetail.setOrderNo(payOrder.getOrderNo());
                                    walletDetail.setAmount(payOrder.getTotalFee());
                                    walletDetail.setWtype("WT09"); // 保证金转入
                                    walletDetail.setDescription("买家保证金转入");
                                    walletDetail.setChannel("CS02");
                                    zcWalletDetailService.addAndUpdateWallet(walletDetail);

                                    // 保证金不退回通知
                                    sendWxMessage.sendMarginNonTemplateMessage(product);
                                }
                            }
                            return true;
                        }
                    });
                }

                // 给卖家推送未付款交易关闭提醒
                sendWxMessage.sendUnPayTemplateMessage(zcOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderState next(ZcOrder zcOrder) {
        return null;
    }
}
