package jb.service.impl.order;

import jb.pageModel.ZcOrder;
import jb.service.ZcOrderServiceI;
import jb.service.impl.SendWxMessageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 等待收货
 * Created by john on 16/10/30.
 */
@Service("order05StateImpl")
public class Order05StateImpl implements OrderState {
    @Autowired
    private ZcOrderServiceI orderService;
    @Autowired
    private SendWxMessageImpl sendWxMessage;
    @Override
    public String getStateName() {
        return "05";
    }

    @Override
    public void handle(ZcOrder zcOrder) {
        // 说明已发货，修改物流状态，填写物流单号
        Date now = new Date();
        zcOrder.setOrderStatus(prefix + getStateName());
        zcOrder.setOrderStatusTime(now);
        zcOrder.setSendStatus("SS03"); // 已发货，待收货
        zcOrder.setDeliverTime(now);
        orderService.edit(zcOrder);

        // 发货成功通知买家确认收货
        sendWxMessage.sendDeliverBTemplateMessage(zcOrder, 1);
    }

    @Override
    public OrderState next(ZcOrder zcOrder) {
        return null;
    }
}
