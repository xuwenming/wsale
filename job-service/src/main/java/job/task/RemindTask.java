package job.task;

import jb.pageModel.ZcOrder;
import jb.pageModel.ZcProduct;
import jb.service.ZcOrderServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */
public class RemindTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ZcOrderServiceI zcOrderService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    @Autowired
    private ZcProductServiceI zcProductService;

    public void work() {
        final CompletionService completionService = CompletionFactory.initCompletion();
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                payRemind(24); // 离付款24小时提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                payRemind(3); // 离付款3小时提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                payRemind(1); // 离付款1小时提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                deliverRemind(24); // 付款24小时提醒发货
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                deliverRemind(12); // 付款12小时提醒发货
                return true;
            }
        });
        // 拍品整分提醒
        Calendar cal = Calendar.getInstance();
        if(cal.get(cal.SECOND) == 0) {
            completionService.submit(new Task<Object, Object>(null) {
                @Override
                public Boolean call() throws Exception {
                    productDeadlineRemind(3, 0); // 拍品距截拍3小时提醒
                    return true;
                }
            });
            completionService.submit(new Task<Object, Object>(null) {
                @Override
                public Boolean call() throws Exception {
                    productDeadlineRemind(1, 0); // 拍品距截拍1小时提醒
                    return true;
                }
            });
            completionService.submit(new Task<Object, Object>(null) {
                @Override
                public Boolean call() throws Exception {
                    productDeadlineRemind(0, 30); // 拍品距截拍30分钟提醒
                    return true;
                }
            });
            completionService.submit(new Task<Object, Object>(null) {
                @Override
                public Boolean call() throws Exception {
                    productDeadlineRemind(0, 10); // 拍品距截拍10分钟提醒
                    return true;
                }
            });
        }

    }

    private void productDeadlineRemind(int h, int m) {
        ZcProduct q = new ZcProduct();
        q.setStatus("PT03");
        q.setIsDeleted(false);
        m = h*60 + m;
        q.setRemindLen(m);
        List<ZcProduct> products = zcProductService.query(q);
        if(CollectionUtils.isNotEmpty(products)) {
            for(ZcProduct product : products) {
               sendWxMessage.sendAuctionEndTemplateMessage(product, h, m);
            }
        }
    }

    private void deliverRemind(int h) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -h);
        ZcOrder o = new ZcOrder();
        o.setPayStatus("PS02"); // 已付款
        o.setOrderStatus("OS02"); // 等待发货
        o.setSendStatus("SS01"); // 待发货
        o.setPaytime(cal.getTime());
        List<ZcOrder> orders = zcOrderService.query(o);
        if(CollectionUtils.isNotEmpty(orders)) {
            for(ZcOrder order : orders) {
                sendWxMessage.sendDeliverSTemplateMessage(order, h);
            }
        }
    }

    private void payRemind(int h) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -(72-h));
        ZcOrder o = new ZcOrder();
        o.setPayStatus("PS01");
        o.setOrderStatus("OS01");
        o.setAddtime(cal.getTime());
        List<ZcOrder> orders = zcOrderService.query(o);
        if(CollectionUtils.isNotEmpty(orders)) {
            for(ZcOrder order : orders) {
                sendWxMessage.sendPayTemplateMessage(order, h);
            }
        }
    }
}
