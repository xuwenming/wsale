package jb.service.impl.order;

import jb.pageModel.*;
import jb.service.ZcIntermediaryLogServiceI;
import jb.service.ZcOrderServiceI;
import jb.service.ZcPayOrderServiceI;
import jb.service.ZcWalletDetailServiceI;
import jb.service.impl.SendWxMessageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 交易完成
 * Created by john on 16/10/30.
 */
@Service("order10StateImpl")
public class Order10StateImpl implements OrderState {
    @Autowired
    private ZcOrderServiceI orderService;

    @Autowired
    private ZcWalletDetailServiceI zcWalletDetailService;

    @Autowired
    private ZcPayOrderServiceI zcPayOrderService;

    @Autowired
    private ZcIntermediaryLogServiceI zcIntermediaryLogService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void handle(ZcOrder zcOrder) {
        // 修改订单状态为交易完成，物流状态已收货
        Date now = new Date();
        zcOrder.setOrderStatus(prefix + getStateName());
        zcOrder.setOrderStatusTime(now);
        zcOrder.setSendStatus("SS02"); // 已收货
        zcOrder.setReceiveTime(now);
        orderService.edit(zcOrder);

        // 中介交易修改交易状态
        if(zcOrder.getIsIntermediary()) {
            ZcIntermediary im = new ZcIntermediary();
            im.setId(zcOrder.getProductId());
            im.setStatus("IS02");

            ZcIntermediaryLog log = new ZcIntermediaryLog();
            log.setLogType("IL05");
            log.setIntermediary(im);

            zcIntermediaryLogService.addAndUpdateIM(log);
        }

        // 钱打到商家账号
        ZcPayOrder po = new ZcPayOrder();
        po.setObjectId(zcOrder.getId());
        po.setObjectType("PO05");
        ZcPayOrder payOrder = zcPayOrderService.get(po);
        if(payOrder != null) {
            OrderProductInfo product = orderService.getProductInfo(zcOrder);
            // 新增钱包收支明细
            ZcWalletDetail walletDetail = new ZcWalletDetail();
            walletDetail.setUserId(product.getSellerUserId());
            walletDetail.setOrderNo(payOrder.getOrderNo());
            double serviceFee = 0;
            if(payOrder.getServiceFee() > 0) serviceFee = ((double)payOrder.getServiceFee())/100;
            walletDetail.setAmount(payOrder.getTotalFee() - serviceFee); // 收入扣除服务费
            walletDetail.setWtype("WT07"); // 拍品收入
            walletDetail.setDescription(zcOrder.getIsIntermediary() ? "中介交易" : "拍品订单");
            walletDetail.setChannel("CS02");
            zcWalletDetailService.addAndUpdateWallet(walletDetail);
        }

        // 交易完成通知卖家
        sendWxMessage.sendDealCompleteTemplateMessage(zcOrder);
    }

    @Override
    public OrderState next(ZcOrder zcOrder) {
        return null;
    }
}
