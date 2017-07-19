package job.task;

import jb.absx.F;
import jb.pageModel.ZcAddress;
import jb.pageModel.ZcAuction;
import jb.pageModel.ZcOrder;
import jb.pageModel.ZcProduct;
import jb.service.ZcAddressServiceI;
import jb.service.ZcAuctionServiceI;
import jb.service.ZcOrderServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.service.impl.order.OrderState;
import jb.util.DateUtil;
import jb.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wenming on 2016/9/27.
 */
public class OrderTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final static long TIME_1 = 3*24*60*60*1000;
    private final static long TIME_2 = 5*24*60*60*1000;
    private final static long TIME_3 = 10*24*60*60*1000;

    @Autowired
    private ZcOrderServiceI zcOrderService;

    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcAddressServiceI zcAddressService;

    @Resource(name = "order15StateImpl")
    private OrderState order15State;

    @Resource(name = "order10StateImpl")
    private OrderState order10State;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    public void work() {
        final CompletionService completionService = CompletionFactory.initCompletion();
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute1(); // 未支付超时处理
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute2(); // 未发货超时处理
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute3(); // 未确认收货超时处理
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute4(); // 买家申请退货，卖家3天不处理则判定同意退货
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute5(); // 卖家同意退货，买家5天不退货发货则判定交易成功
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
                @Override
            public Boolean call() throws Exception {
                execute6(); // 买家已退货发货，卖家10天不确认退款或不申请小二则判定退货成功
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute7(); // 卖家拒绝退货，买家3天不申请小二则判定交易完成
                return true;
            }
        });
    }

    private void execute1() {
        ZcOrder q = new ZcOrder();
        q.setPayStatus("PS01"); // 未支付
        q.setOrderStatus("OS01"); // 等待付款
        List<ZcOrder> orders = zcOrderService.query(q);
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除不满72小时的订单
                if(now.getTime() - order.getAddtime().getTime() < TIME_1) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        getD().setOrderCloseReason("OC001"); // 买家未付款
                        order15State.handle(getD());

                        return true;
                    }
                });
            }
        }

    }

    private void execute2() {
        ZcOrder q = new ZcOrder();
        q.setPayStatus("PS02"); // 已支付
        q.setSendStatus("SS01"); // 待发货
        q.setOrderStatus("OS02"); // 等待发货
        List<ZcOrder> orders = zcOrderService.query(q);
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除不满5天的订单
                if(now.getTime() - order.getPaytime().getTime() < TIME_2) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        getD().setOrderCloseReason("OC002"); // 卖家未发货
                        order15State.handle(getD());

                        return true;
                    }
                });
            }
        }
    }

    private void execute3() {
        ZcOrder q = new ZcOrder();
        q.setPayStatus("PS02"); // 已支付
        q.setSendStatus("SS03"); // 已发货
        q.setOrderStatus("OS05"); // 等待收货
        List<ZcOrder> orders = zcOrderService.query(q, " and t.backStatus is null "); // 排除申请退货
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除不满14天的订单
                if(now.getTime() - order.getDeliverTime().getTime() < TIME_3) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        order10State.handle(getD());
                        return true;
                    }
                });
            }
        }
    }

    private void execute4() {
        ZcOrder q = new ZcOrder();
        q.setBackStatus("RS01"); // 申请退货
        q.setOrderStatus("OS05"); // 等待收货
        List<ZcOrder> orders = zcOrderService.query(q);
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除申请退货不满3天的订单
                if(now.getTime() - order.getReturnApplyTime().getTime() < TIME_1) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        getD().setBackStatus("RS03");
                        getD().setReturnConfirmTime(new Date());
                        zcOrderService.edit(getD());

                        // 新增订单退货地址（卖家）
                        ZcProduct product = zcProductService.get(getD().getProductId());
                        ZcAddress address = new ZcAddress();
                        address.setUserId(product.getAddUserId());
                        address.setAtype(2); // 退货地址
                        address.setOrderId("-1");
                        address = zcAddressService.get(address);
                        if(address != null) {
                            address.setOrderId(getD().getId());
                            zcAddressService.add(address);
                        }

                        // 给买家发送退货申请结果通知-同意
                        sendWxMessage.sendBackResultTemplateMessage(getD());

                        return true;
                    }
                });
            }
        }
    }

    private void execute5() {
        ZcOrder q = new ZcOrder();
        q.setBackStatus("RS03"); // 同意退货
        q.setOrderStatus("OS05"); // 等待收货
        List<ZcOrder> orders = zcOrderService.query(q);
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除同意退货不满5天的订单
                if(now.getTime() - order.getReturnConfirmTime().getTime() < TIME_2) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        order10State.handle(getD());
                        return true;
                    }
                });
            }
        }
    }

    private void execute6() {
        ZcOrder q = new ZcOrder();
        q.setBackStatus("RS04"); // 退货发货
        q.setOrderStatus("OS05"); // 等待收货
        List<ZcOrder> orders = zcOrderService.query(q, " and not exists (select 1 from TzcOrderXiaoer xr where xr.orderId = t.id and xr.idType = 2 and xr.status = 'XS01')");
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除买家退货发货不满14天的订单
                if(now.getTime() - order.getReturnDeliverTime().getTime() < TIME_3) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        getD().setBackStatus("RS05");
                        getD().setReturnTime(new Date());
                        getD().setOrderCloseReason("OC003"); // 买家退货
                        order15State.handle(getD());
                        return true;
                    }
                });
            }
        }
    }

    private void execute7() {
        ZcOrder q = new ZcOrder();
        q.setBackStatus("RS02"); // 拒绝退货
        q.setOrderStatus("OS05"); // 等待收货
        List<ZcOrder> orders = zcOrderService.query(q, " and not exists (select 1 from TzcOrderXiaoer xr where xr.orderId = t.id and xr.idType = 1 and xr.status = 'XS01')");
        if(CollectionUtils.isNotEmpty(orders)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcOrder order : orders) {
                // 排除卖家拒绝退货不满3天的订单
                if(now.getTime() - order.getReturnConfirmTime().getTime() < TIME_1) continue;
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
                    @Override
                    public Boolean call() throws Exception {
                        order10State.handle(getD());
                        return true;
                    }
                });
            }
        }
    }
}
