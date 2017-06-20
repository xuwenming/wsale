package job.task;

import jb.pageModel.ZcAuction;
import jb.pageModel.ZcOrder;
import jb.pageModel.ZcProduct;
import jb.service.ZcAuctionServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.order.OrderState;
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
public class ProductTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "order01StateImpl")
    private OrderState order01State;

    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcAuctionServiceI zcAuctionService;

    public void work() {
        execute();
    }

    public void execute() {
        ZcProduct q = new ZcProduct();
        q.setRealDeadline(new Date());
        q.setStatus("PT03");
        q.setIsDeleted(false);
        List<ZcProduct> products = zcProductService.query(q);
        if(CollectionUtils.isNotEmpty(products)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            for(ZcProduct product : products) {
                completionService.submit(new Task<ZcProduct, Boolean>(product) {
                    @Override
                    public Boolean call() throws Exception {
                        ZcAuction auction = new ZcAuction();
                        auction.setProductId(getD().getId());
                        auction = zcAuctionService.get(auction);

                        ZcProduct p = new ZcProduct();
                        p.setId(getD().getId());
                        if(auction == null) {
                            p.setStatus("PT05"); // 流拍
                            p.setRefund(true);
                            zcProductService.edit(p);
                        } else {
                            // 生成订单
                            ZcOrder order = new ZcOrder();
                            order.setProductId(getD().getId());
                            order01State.handle(order);

                            // 截拍
                            p.setStatus("PT04");
                            p.setUserId(auction.getBuyerId());
                            p.setHammerPrice(auction.getBid());
                            p.setHammerTime(new Date());
                            p.setRefund(true);
                            zcProductService.edit(p);
                        }

                        return true;
                    }
                });
            }
        }
    }
}
