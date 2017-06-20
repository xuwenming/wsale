package jb.service.impl.order;

import jb.pageModel.ZcOrder;
import jb.pageModel.ZcPayOrder;
import jb.service.ZcOrderServiceI;
import jb.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 待付款状态
 * Created by john on 16/10/30.
 */
@Service("order01StateImpl")
public class Order01StateImpl implements OrderState {
    @Resource(name = "order02StateImpl")
    private OrderState order02State;
    @Resource(name = "order15StateImpl")
    private OrderState order15State;
    @Autowired
    private ZcOrderServiceI orderService;

    @Override
    public String getStateName() {
        return "01";
    }

    @Override
    public void handle(ZcOrder zcOrder) {
        // 生成订单
        zcOrder.setOrderNo(Util.CreateOrderNo());
        zcOrder.setOrderStatus(prefix + getStateName());
        zcOrder.setIsCommented(false); // 未评价
        zcOrder.setPayStatus("PS01"); // 待支付
        orderService.add(zcOrder);
    }

    @Override
    public OrderState next(ZcOrder zcOrder) {
        Object other = zcOrder.getOther();
        // 1、other 对象为支付表单，则下个状态是OS02
        if(other instanceof ZcPayOrder){
            return order02State;
        }else if("用户取消".equals(other.toString())){
            return order15State;
        }
        return null;
    }
}
