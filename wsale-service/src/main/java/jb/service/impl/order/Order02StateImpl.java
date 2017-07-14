package jb.service.impl.order;

import jb.absx.F;
import jb.pageModel.ZcOrder;
import jb.pageModel.ZcPayOrder;
import jb.pageModel.ZcProduct;
import jb.pageModel.ZcProductMargin;
import jb.service.ZcOrderServiceI;
import jb.service.ZcPayOrderServiceI;
import jb.service.ZcProductMarginServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.Date;

/**
 * 等待发货
 * Created by john on 16/10/30.
 */
@Service("order02StateImpl")
public class Order02StateImpl implements OrderState {
    @Autowired
    private ZcOrderServiceI orderService;

    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcProductMarginServiceI zcProductMarginService;

    @Autowired
    private ZcPayOrderServiceI zcPayOrderService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;


    @Override
    public String getStateName() {
        return "02";
    }

    @Override
    public void handle(ZcOrder zcOrder) {
        //TODO 1调用支付接口
        //TODO 2生成支付记录
        //TODO 3修改账户余额
        //TODO 4修改订单状态，支付状态
        zcOrder.setSendStatus("SS01"); // 待发货
        zcOrder.setOrderStatus(prefix + getStateName());
        zcOrder.setOrderStatusTime(zcOrder.getPaytime());
        orderService.edit(zcOrder);

        // 退回付款人保证金
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
                            zcPayOrderService.refund(payOrder, "保证金退回", margin);

                            // 保证金退回通知
                            sendWxMessage.sendMarginRefundTemplateMessage(margin, "订单已支付");
                        }
                    }
                    return true;
                }
            });
        }

        // 付款成功通知卖家发货
        sendWxMessage.sendDeliverSTemplateMessage(zcOrder, 0);
    }

    @Override
    public OrderState next(ZcOrder zcOrder) {
        return null;
    }
}
