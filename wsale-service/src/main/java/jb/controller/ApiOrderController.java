package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.service.impl.order.OrderState;
import jb.util.DateUtil;
import jb.util.EnumConstants;
import jb.util.ImageUtils;
import jb.util.wx.DownloadMediaUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 *TODO  Created by james on 2016/8/31 0026.
 */
@Controller
@RequestMapping("/api/apiOrder")
public class ApiOrderController extends BaseController {

    @Autowired
    private ZcOrderServiceI zcOrderService;

    @Autowired
    private ZcAuctionServiceI zcAuctionService;

    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcCommentServiceI zcCommentService;

    @Autowired
    private UserServiceI userServiceI;

    @Resource(name = "order05StateImpl")
    private OrderState order05State;

    @Resource(name = "order10StateImpl")
    private OrderState order10State;

    @Resource(name = "order15StateImpl")
    private OrderState order15State;

    @Autowired
    private ZcAddressServiceI zcAddressService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    @Autowired
    private BasedataServiceI basedataService;

    @Autowired
    private ZcOrderXiaoerServiceI zcOrderXiaoerService;

    @Autowired
    private ZcFileServiceI zcFileService;

    @Autowired
    private ZcProductMarginServiceI zcProductMarginService;

    @Autowired
    private ZcPayOrderServiceI zcPayOrderService;

    @Autowired
    private ZcIntermediaryServiceI zcIntermediaryService;

    /**
     * 跳转至我的订单
     * type 1-待付款；2-待收货；3-未发货；4-待评价；5-售后
     * @return
     */
    @RequestMapping("/myOrder")
    public String myOrder(int type, HttpServletRequest request) {
        request.setAttribute("type", type);
        return "/wsale/order/order_list";
    }

    /**
     * 跳转至订单详情
     * @return
     */
    @RequestMapping("/orderDetail")
    public String orderDetail(String id, HttpServletRequest request) {
        SessionInfo s = getSessionInfo(request);
        ZcOrder order = zcOrderService.get(id);

        OrderProductInfo product = zcOrderService.getProductInfo(order);

        order.setProduct(product);

        String sellerUserId = product.getSellerUserId();
        String buyerUserId = product.getBuyerUserId();

        if(s.getId().equals(sellerUserId)) {
            order.setIsBuyer(false);
        } else {
            order.setIsBuyer(true);
        }
        order.setSeller(userServiceI.getByZc(sellerUserId));
        order.setBuyer(userServiceI.getByZc(buyerUserId));

        if(order.getIsCommented()) {
            ZcComment c = new ZcComment();
            c.setOrderId(id);
            order.setComment(zcCommentService.get(c));
        }

        order.setIsXiaoer(false);
        // 检查买家是否申请小二介入条件：卖家拒绝退货且订单未结束
        if("RS02".equals(order.getBackStatus()) && !"OS10".equals(order.getOrderStatus()) && !"OS15".equals(order.getOrderStatus())) {
            ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
            xiaoerQ.setOrderId(order.getId());
            xiaoerQ.setIdType(1);
            ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
            if(xiaoer != null) {
                order.setIsXiaoer(true);
                order.setXiaoer(xiaoer);
            }
        }

        // 检查卖家是否申请小二介入条件：买家已退货发货且订单未结束
        if("RS04".equals(order.getBackStatus()) && !"OS10".equals(order.getOrderStatus()) && !"OS15".equals(order.getOrderStatus())) {
            ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
            xiaoerQ.setOrderId(order.getId());
            xiaoerQ.setIdType(2);
            ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
            if(xiaoer != null) {
                order.setIsXiaoer(true);
                order.setXiaoer(xiaoer);
            }
        }

        request.setAttribute("order", order);

        // 买家收货地址
        ZcAddress address = new ZcAddress();
        address.setUserId(buyerUserId);
        address.setAtype(1); // 1:收货地址; 2:退货地址
        address.setOrderId(id);
        address = zcAddressService.get(address);
        request.setAttribute("address", address);

        // 卖家退货地址
        ZcAddress backAddress = new ZcAddress();
        backAddress.setUserId(sellerUserId);
        backAddress.setAtype(2); // 1:收货地址; 2:退货地址
        backAddress.setOrderId(id);
        backAddress = zcAddressService.get(backAddress);
        request.setAttribute("backAddress", backAddress);

        return "/wsale/order/order_detail";
    }

    /**
     * 跳转至评价页
     * @return
     */
    @RequestMapping("/comment")
    public String comment(String orderId, String productId, HttpServletRequest request) {
        request.setAttribute("orderId", orderId);
        request.setAttribute("productId", productId);
        return "/wsale/order/comment";
    }

    /**
     * 订单列表
     * http://localhost:8080/api/apiOrder/orderList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10
     * @return
     */
    @RequestMapping("/orderList")
    @ResponseBody
    public Json orderList(ZcOrder order, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            ph.setSort("addtime");
            ph.setOrder("desc");
            order.setAddUserId(s.getId());
            DataGrid dataGrid = zcOrderService.dataGrid(order, ph);
            List<ZcOrder> orders = (List<ZcOrder>) dataGrid.getRows();
            if(CollectionUtils.isNotEmpty(orders)) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                final String curUserId = s.getId();
                for(ZcOrder o : orders) {
                    completionService.submit(new Task<ZcOrder, OrderProductInfo>(o) {
                        @Override
                        public OrderProductInfo call() throws Exception {
                            return zcOrderService.getProductInfo(getD());
                        }
                        protected void set(ZcOrder d, OrderProductInfo v) {
                            if(v != null) {
                                d.setProduct(v);

                                final String sellerUserId = v.getSellerUserId(); // 卖家ID
                                final String buyerUserId = v.getBuyerUserId(); // 买家ID
                                if(curUserId.equals(sellerUserId)) {
                                    d.setIsBuyer(false);
                                } else {
                                    d.setIsBuyer(true);
                                }
                                completionService.submit(new Task<ZcOrder, User>(d) {
                                    @Override
                                    public User call() throws Exception {
                                        User user = userServiceI.getByZc(sellerUserId);
                                        return user;
                                    }
                                    protected void set(ZcOrder d, User v) {
                                        if(v != null) {
                                            d.setSeller(v);
                                        }
                                    }
                                });
                                completionService.submit(new Task<ZcOrder, User>(d) {
                                    @Override
                                    public User call() throws Exception {
                                        User user = userServiceI.getByZc(buyerUserId);
                                        return user;
                                    }
                                    protected void set(ZcOrder d, User v) {
                                        if(v != null) {
                                            d.setBuyer(v);
                                        }
                                    }
                                });
                            }

                            // 检查买家是否申请小二介入条件：卖家拒绝退货且订单未结束
                            if("RS02".equals(d.getBackStatus()) && !"OS10".equals(d.getOrderStatus()) && !"OS15".equals(d.getOrderStatus())) {
                                ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
                                xiaoerQ.setOrderId(d.getId());
                                xiaoerQ.setIdType(1);
                                ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
                                if(xiaoer != null) {
                                    d.setIsXiaoer(true);
                                    d.setXiaoer(xiaoer);
                                } else {
                                    d.setIsXiaoer(false);
                                }
                            }

                            // 检查卖家是否申请小二介入条件：买家已退货发货且订单未结束
                            if("RS04".equals(d.getBackStatus()) && !"OS10".equals(d.getOrderStatus()) && !"OS15".equals(d.getOrderStatus())) {
                                ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
                                xiaoerQ.setOrderId(d.getId());
                                xiaoerQ.setIdType(2);
                                ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
                                if(xiaoer != null) {
                                    d.setIsXiaoer(true);
                                    d.setXiaoer(xiaoer);
                                } else {
                                    d.setIsXiaoer(false);
                                }
                            }
                        }
                    });
                }
                completionService.sync();
            }

            j.setObj(dataGrid);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 取消订单（james）TODO 什么时候可以取消呢？
     * http://localhost:8080/api/apiOrder/cancelOrder?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=aasdasdasdasdasd
     * @return
     */
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public Json cancelOrder(ZcOrder order , HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            ZcOrder zcOrder = zcOrderService.get(order);
            zcOrder.setUpdateUserId(s.getId());
            //设置订单为交易失败状态
            zcOrder.setUpdatetime(new Date());
            zcOrder.setOrderStatus("OS02");
            zcOrderService.edit(zcOrder);
            j.success();
            j.setMsg("取消成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 订单详情（james）
     * http://localhost:8080/api/apiOrder/orderInfo?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=aasdasdasdasdasd
     * @return
     */
    @RequestMapping("/orderInfo")
    @ResponseBody
    public Json orderInfo(ZcOrder order , HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            ZcOrder zcOrder = zcOrderService.get(order);
            String productId = zcOrder.getProductId();
            if (!F.empty(productId)) {
                ZcAuction zcAuction = new ZcAuction();
                zcAuction.setProductId(productId);
                zcAuction.setStatus("DS02");
                ZcAuction auction = zcAuctionService.get(zcAuction);
                if (s.getId().equals(auction.getBuyerId())) {
                    //zcOrder.setIsPurchaser(true);
                    User user = userServiceI.get(auction.getAddUserId());
                    //zcOrder.setTel(user.getMobile());
                } else if (s.getId().equals(auction.getAddUserId())) {
                   // zcOrder.setIsPurchaser(false);
                    User user = userServiceI.get(auction.getBuyerId());
                    //zcOrder.setTel(user.getMobile());
                }
                ZcProduct zcProduct = zcProductService.get(productId,s.getId());
                ZcComment zcComment=new ZcComment();
                zcComment.setProductId(productId);
                ZcComment comment = zcCommentService.get(zcComment);
                //zcOrder.setZcAuction(auction);
                //zcOrder.setZcProduct(zcProduct);
                //zcOrder.setZcComment(comment);

            }
            j.setObj(zcOrder);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }
    /**
     * 订单列表（james）高级搜索 TODO(小二介入问题)
     * http://localhost:8080/api/apiOrder/orderListSearch?tokenId=1D96DACB84F21890ED9F4928FA8B352B&faceToFace=1&page=1&rows=10
     * @return
     */
    @RequestMapping("/orderListSearch")
    @ResponseBody
    public Json orderListSearch(PageHelper ph,  ZcOrder zcOrder,ZcProduct product , Boolean isXiaoer, HttpServletRequest request) {
        Json j = new Json();
        try{
            final SessionInfo s = getSessionInfo(request);
            ph.setSort("addtime");
            ph.setOrder("asc");
            DataGrid dataGrid =zcOrderService.dataGridComp(zcOrder, product,isXiaoer, ph);
            List<ZcOrder> zc = (List<ZcOrder>) dataGrid.getRows();

            if(!CollectionUtils.isEmpty(zc)) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                final String curUserId = s.getId();
                for (ZcOrder order : zc) {


                    completionService.submit(new Task<ZcOrder, ZcAuction>(order) {
                        @Override
                        public ZcAuction call() throws Exception {
                            ZcAuction zcAuction=new ZcAuction();
                            zcAuction.setProductId(getD().getProductId());
                            ZcAuction zc=zcAuctionService.get(zcAuction);

                            return zc == null ? null : zc;
                        }

                        protected void set(ZcOrder d, ZcAuction v) {
                            if (v != null)
                                if (s.getId().equals(v.getBuyerId())) {
                                    //d.setIsPurchaser(true);
                                    User user = userServiceI.get(v.getAddUserId());
                                    //d.setTel(user.getMobile());
                                } else if (s.getId().equals(v.getAddUserId())) {
                                    //d.setIsPurchaser(false);
                                    User user = userServiceI.get(v.getBuyerId());
                                    //d.setTel(user.getMobile());
                                }
                                //d.setZcAuction(v);

                        }
                    });

                    completionService.submit(new Task<ZcOrder, ZcProduct>(order) {
                        @Override
                        public ZcProduct call() throws Exception {

                            ZcProduct zc=zcProductService.get(getD().getProductId(),curUserId);

                            return zc == null ? null : zc;
                        }

                        protected void set(ZcOrder d, ZcProduct v) {
                            //if (v != null)
                                //d.setZcProduct(v);

                        }
                    });

                }

                completionService.sync();
            }
            j.setObj(dataGrid);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 买家申请当面交易
     * @return
     */
    @RequestMapping("/faceApply")
    @ResponseBody
    public Json faceApply(ZcOrder order) {
        Json j = new Json();
        try{
            order.setFaceStatus("FS01"); // 提交申请中
            order.setFaceTime(new Date());
            zcOrderService.edit(order);

            // 给卖家发送当面交易提醒
            sendWxMessage.sendFacePayTemplateMessage(order);

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 卖家同意当面交易
     * @return
     */
    @RequestMapping("/agreeFace")
    @ResponseBody
    public Json agreeFace(ZcOrder order) {
        Json j = new Json();
        try{
            order.setFaceStatus("FS02"); // 同意
            order.setOrderStatus("OS10");
            order.setOrderStatusTime(new Date());
            zcOrderService.edit(order);

            // 给买家发送申请当面交易成功通知
            sendWxMessage.sendFaceResultTemplateMessage(order);

            if(!order.getIsIntermediary()) {
                // 退回买家保证金
                final CompletionService completionService = CompletionFactory.initCompletion();
                completionService.submit(new Task<ZcOrder, Boolean>(order) {
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
                                sendWxMessage.sendMarginRefundTemplateMessage(margin, "订单当面交易");
                            }
                        }
                        return true;
                    }
                });
            }
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 卖家拒绝当面交易
     * @return
     */
    @RequestMapping("/refuseFace")
    @ResponseBody
    public Json refuseFace(ZcOrder order) {
        Json j = new Json();
        try{
            order.setFaceStatus("FS03"); // 拒绝
            zcOrderService.edit(order);

            // 给买家发送申请当面交易拒绝通知
            sendWxMessage.sendFaceResultTemplateMessage(order);

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 添加订单收货地址
     * @return
     */
    @RequestMapping("/addAddress")
    @ResponseBody
    public Json addAddress(ZcAddress address, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            ZcAddress exist = new ZcAddress();
            exist.setUserId(s.getId());
            exist.setAtype(1);
            exist.setOrderId(address.getOrderId());
            exist = zcAddressService.get(exist);
            if(exist == null) {
                address.setUserId(s.getId());
                address.setAtype(1);
                zcAddressService.add(address);
            } else {
                address.setId(exist.getId());
                zcAddressService.edit(address);
            }

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }


    /**
     * 跳转至卖家发货页
     * @return
     */
    @RequestMapping("/toDeliver")
    public String toDeliver(String orderId, HttpServletRequest request) {
        ZcOrder order = zcOrderService.get(orderId);
        request.setAttribute("order", order);

        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("product", product);

        // 买家收货地址
        ZcAddress address = new ZcAddress();
        address.setUserId(product.getBuyerUserId()); // 买家
        address.setAtype(1); // 收货地址
        address.setOrderId(orderId);
        address = zcAddressService.get(address);
        request.setAttribute("address", address);

        return "/wsale/order/deliver";
    }

    /**
     * 发货
     * @return
     */
    @RequestMapping("/deliver")
    @ResponseBody
    public Json deliver(ZcOrder order) {
        Json j = new Json();
        try{
            order05State.handle(order);

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 确认收货
     * @return
     */
    @RequestMapping("/receive")
    @ResponseBody
    public Json receive(ZcOrder order) {
        Json j = new Json();
        try{
            order10State.handle(order);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 跳转至买家申请退款页
     * @return
     */
    @RequestMapping("/toBackApply")
    public String toBackApply(String orderId, String productId, HttpServletRequest request) {
        ZcOrder order = zcOrderService.get(orderId);
        Date receiveDownTime = DateUtil.addDayToDate(order.getDeliverTime(), 10);
        request.setAttribute("receiveDownTime", receiveDownTime);

        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("orderId", orderId);
        request.setAttribute("product", product);

        BaseData baseData = new BaseData();
        baseData.setBasetypeCode("RR");
        List<BaseData> returnApplyReasons = basedataService.getBaseDatas(baseData);
        request.setAttribute("returnApplyReasons", returnApplyReasons);

        return "/wsale/order/backApply";
    }

    /**
     * 申请退货
     * @return
     */
    @RequestMapping("/backApply")
    @ResponseBody
    public Json backApply(ZcOrder order) {
        Json j = new Json();
        try{
//            ZcOrder o = zcOrderService.get(order.getId());
//            if(o != null && !"OS02".equals(o.getOrderStatus())) {
//                j.fail();
//                j.setMsg("卖家已发货，无法申请退货");
//                return j;
//            }
            order.setBackStatus("RS01");
            order.setReturnApplyTime(new Date());
            zcOrderService.edit(order);

            // 给卖家发送退货申请提醒
            sendWxMessage.sendBackApplyTemplateMessage(order);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 跳转至卖家同意退货页
     * @return
     */
    @RequestMapping("/toAgreeBack")
    public String toAgreeBack(String orderId, HttpServletRequest request) {
        SessionInfo s = getSessionInfo(request);
        ZcAddress address = new ZcAddress();
        address.setUserId(s.getId());
        address.setAtype(2); // 退货地址
        address.setOrderId("-1");
        address = zcAddressService.get(address);
        request.setAttribute("address", address);

        ZcOrder order = zcOrderService.get(orderId);
        Date returnApplyTime = DateUtil.addDayToDate(order.getReturnApplyTime(), 3);
        order.setReturnApplyTime(returnApplyTime);
        request.setAttribute("order", order);

        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("product", product);

        return "/wsale/order/agreeBack";
    }

    /**
     * 同意退货
     * @return
     */
    @RequestMapping("/agreeBack")
    @ResponseBody
    public Json agreeBack(String orderId, ZcAddress address, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);

            ZcOrder order = new ZcOrder();
            order.setId(orderId);
            order.setBackStatus("RS03");
            order.setReturnConfirmTime(new Date());
            zcOrderService.edit(order);
            //order.setReturnTime(new Date());
            //order.setOrderCloseReason("OC003"); // 买家已退货
            //order15State.handle(order);

            // 新增订单退货地址
            address.setUserId(s.getId());
            address.setOrderId(orderId);
            address.setAtype(2);
            zcAddressService.add(address);

            // 给买家发送退货申请结果通知-同意
            sendWxMessage.sendBackResultTemplateMessage(order);

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 跳转至卖家拒绝退货页
     * @return
     */
    @RequestMapping("/toRefuseBack")
    public String toRefuseBack(String orderId, HttpServletRequest request) {

        ZcOrder order = zcOrderService.get(orderId);
        Date endTime = DateUtil.addDayToDate(order.getReturnApplyTime(), 3);
        request.setAttribute("order", order);
        request.setAttribute("endTime", endTime);

        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("product", product);

        BaseData baseData = new BaseData();
        baseData.setBasetypeCode("RF");
        List<BaseData> refuseReturnReasons = basedataService.getBaseDatas(baseData);
        request.setAttribute("refuseReturnReasons", refuseReturnReasons);

        return "/wsale/order/refuseBack";
    }

    /**
     * 拒绝退货
     * @return
     */
    @RequestMapping("/refuseBack")
    @ResponseBody
    public Json refuseBack(ZcOrder order) {
        Json j = new Json();
        try{
            order.setBackStatus("RS02");
            order.setReturnConfirmTime(new Date());
            zcOrderService.edit(order);

            // 给买家发送退货申请结果通知-拒绝
            sendWxMessage.sendBackResultTemplateMessage(order);

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 跳转至买家立即退货页
     * @return
     */
    @RequestMapping("/toBack")
    public String toBack(String orderId, HttpServletRequest request) {
        ZcOrder order = zcOrderService.get(orderId);
        request.setAttribute("order", order);

        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("product", product);

        ZcAddress address = new ZcAddress();
        address.setUserId(product.getSellerUserId()); // 卖家
        address.setAtype(2); // 退货地址
        address.setOrderId(orderId);
        address = zcAddressService.get(address);
        request.setAttribute("address", address);

        return "/wsale/order/back";
    }

    /**
     * 买家立即退货
     * @return
     */
    @RequestMapping("/back")
    @ResponseBody
    public Json back(ZcOrder order) {
        Json j = new Json();
        try{
            order.setBackStatus("RS04");
            order.setReturnDeliverTime(new Date());
            zcOrderService.edit(order);

            // 买家退货发货提醒卖家
            sendWxMessage.sendDeliverBTemplateMessage(order, 2);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 跳转至卖家同意退款页
     * @return
     */
    @RequestMapping("/toRefund")
    public String toRefund(String orderId, HttpServletRequest request) {
        ZcOrder order = zcOrderService.get(orderId);
        Date returnDeliverTime = DateUtil.addDayToDate(order.getReturnDeliverTime(), 10);
        order.setReturnDeliverTime(returnDeliverTime);
        request.setAttribute("order", order);

        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("product", product);

        ZcAddress address = new ZcAddress();
        address.setUserId(product.getSellerUserId()); // 卖家
        address.setAtype(2); // 退货地址
        address.setOrderId(orderId);
        address = zcAddressService.get(address);
        request.setAttribute("address", address);

        return "/wsale/order/refund";
    }

    /**
     * 同意退款
     * @return
     */
    @RequestMapping("/refund")
    @ResponseBody
    public Json refund(ZcOrder order) {
        Json j = new Json();
        try{
            order.setBackStatus("RS05");
            order.setReturnTime(new Date());
            order.setOrderCloseReason("OC003"); // 买家已退货
            order15State.handle(order);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 跳转至申请小二介入
     * @return
     */
    @RequestMapping("/xiaoer")
    public String xiaoer(String orderId, HttpServletRequest request) {
        ZcOrder order = zcOrderService.get(orderId);
        OrderProductInfo product = zcOrderService.getProductInfo(order);
        request.setAttribute("product", product);
        request.setAttribute("orderId", orderId);

        SessionInfo s = getSessionInfo(request);
        int idType = 1; // 买家
        if(s.getId().equals(product.getSellerUserId())) {
            idType = 2;
        }
        request.setAttribute("idType", idType);
        return "/wsale/order/xiaoer";
    }

    /**
     * 申请小二介入
     * @return
     */
    @RequestMapping("/applyXr")
    @ResponseBody
    public Json applyXr(ZcOrderXiaoer xiaoer, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            xiaoer.setAddUserId(s.getId());
            xiaoer.setStatus("XS01");
            zcOrderXiaoerService.add(xiaoer);
            if(!F.empty(xiaoer.getMediaIds())) {
                String[] mediaIds = xiaoer.getMediaIds().split(",");
                final CompletionService completionService = CompletionFactory.initCompletion();
                final String realPath = request.getSession().getServletContext().getRealPath("/");
                int index = 0;
                for(final String mediaId : mediaIds) {
                    ZcFile zcFile = new ZcFile();
                    zcFile.setObjectType(EnumConstants.OBJECT_TYPE.XR.getCode()); // 对象类型：小二介入
                    zcFile.setObjectId(xiaoer.getId());
                    zcFile.setFileType("FT01"); // 文件类型：图片
                    zcFile.setSeq(++index);
                    completionService.submit(new Task<ZcFile, Object>(zcFile){
                        @Override
                        public Boolean call() throws Exception {
                            String filePath = DownloadMediaUtil.downloadMedia(realPath, mediaId, "XR");
                            getD().setFileOriginalUrl(filePath);
                            getD().setFileHandleUrl(ImageUtils.pressImage(filePath, realPath));
                            zcFileService.add(getD());
                            return true;
                        }
                    });
                }
                completionService.sync();
            }
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 撤销小二介入
     * @return
     */
    @RequestMapping("/cancelXr")
    @ResponseBody
    public Json cancelXr(ZcOrderXiaoer xiaoer, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            xiaoer.setUpdateUserId(s.getId());
            xiaoer.setUpdatetime(new Date());
            xiaoer.setStatus("XS02");
            zcOrderXiaoerService.edit(xiaoer);
            ZcOrder order = zcOrderService.get(xiaoer.getOrderId());
            if(xiaoer.getIdType() == 1) {
                // 卖家拒绝退货超过3天则交易完成结束订单
                if(!"OS10".equals(order.getOrderStatus()) && new Date().getTime() - order.getReturnConfirmTime().getTime() > 72*60*60*1000) {
                    return receive(order);
                }
            } else {
                // 买家退货发货超过14天则自动退款结束订单
                if(!"OS15".equals(order.getOrderStatus()) && new Date().getTime() - order.getReturnDeliverTime().getTime() > 10*24*60*60*1000) {
                    return refund(order);
                }
            }

            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 添加评价
     * @return
     */
    @RequestMapping("/addComment")
    @ResponseBody
    public Json addComment(ZcComment comment, HttpServletRequest request) {
        Json j = new Json();
        try{
            comment.setAddUserId(getSessionInfo(request).getId());
            zcCommentService.add(comment);

            ZcOrder order = new ZcOrder();
            order.setId(comment.getOrderId());
            order.setIsCommented(true);
            zcOrderService.edit(order);

            j.success();
            j.setMsg("评价成功");
        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }
}
