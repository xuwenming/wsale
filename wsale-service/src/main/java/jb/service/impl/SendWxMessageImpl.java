package jb.service.impl;

import com.alibaba.fastjson.JSONObject;
import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.util.Constants;
import jb.util.DateUtil;
import jb.util.PathUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.bean.Text;
import jb.util.wx.bean.TextMessage;
import jb.util.wx.message.req.templateMessage.TemplateData;
import jb.util.wx.message.req.templateMessage.WxTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by wenming on 2016/8/25.
 */
@Service
public class SendWxMessageImpl {

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ZcForumBbsServiceI zcForumBbsService;

    @Autowired
    private ZcCategoryServiceI zcCategoryService;

    @Autowired
    private ZcBbsRewardServiceI zcBbsRewardService;

    @Autowired
    private ZcAuthenticationServiceI zcAuthenticationService;

    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcAddressServiceI zcAddressService;

    @Autowired
    private ZcAuctionServiceI zcAuctionService;

    @Autowired
    private ZcProductLikeServiceI zcProductLikeService;

    @Autowired
    private ZcOfflineTransferServiceI zcOfflineTransferService;

    @Autowired
    private ZcWalletServiceI zcWalletService;

    @Autowired
    private ZcWalletDetailServiceI zcWalletDetailService;

    @Autowired
    private ZcOrderServiceI zcOrderService;

    /**
     *objectType:bbs_e=帖子加精；bbs_l=帖子加亮；bbs_t=帖子置顶；bbs_o=帖子关闭；bbs_m=帖子移动;bbs_c=帖子回复;bbs_r=帖子打赏;bbs_d=帖子删除
     */
    public void sendCustomMessage(final String objectId, final String objectType, final String optUserName) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                StringBuffer buffer = new StringBuffer();
                String openid = null;
                if("bbs_e".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());
                    buffer.append("您的主题帖\"" + bbs.getBbsTitle() + "\"被『" + optUserName + "』加精。").append("\n\n");
                    buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + bbs.getId()) +"'>点击查看</a>");
                    openid = user.getName();
                } else if("bbs_l".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());
                    buffer.append("您的主题帖\"" + bbs.getBbsTitle() + "\"被『" + optUserName + "』加亮。").append("\n\n");
                    buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + bbs.getId()) +"'>点击查看</a>");
                    openid = user.getName();
                } else if("bbs_t".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());
                    buffer.append("您的主题帖\"" + bbs.getBbsTitle() + "\"被『" + optUserName + "』置顶。").append("\n\n");
                    buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + bbs.getId()) +"'>点击查看</a>");
                    openid = user.getName();
                } else if("bbs_o".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());
                    buffer.append("您的主题帖\"" + bbs.getBbsTitle() + "\"被管理员关闭，申诉请移步“站务公告”申诉专版。");
                    openid = user.getName();
                } else if("bbs_m".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());

                    ZcCategory category = zcCategoryService.get(optUserName);
                    ZcCategory pc = null;
                    if(!F.empty(category.getPid())) {
                        pc = zcCategoryService.get(category.getPid());
                    }
                    String categoryName = (pc != null ? pc.getName() + "/" : "") + category.getName();
                    buffer.append("您的主题帖\"" + bbs.getBbsTitle() + "\"被管理员移动到 "+categoryName+" (板块)，申诉请移步“站务公告”申诉专版。");

                    openid = user.getName();
                } else if("bbs_c".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());
                    String username = "BT02".equals(bbs.getBbsType()) ? "鉴定员" : optUserName;
                    buffer.append("『" + username + "』回复了您的主题帖\"" + bbs.getBbsTitle() + "\"").append("\n\n");
                    buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + bbs.getId()) +"'>点击查看</a>");
                    openid = user.getName();
                } else if("bbs_r".equals(objectType)) {
                    ZcBbsReward reward = zcBbsRewardService.get(objectId);
                    ZcForumBbs bbs = zcForumBbsService.get(reward.getBbsId());
                    User rewardUser = userService.getByZc(reward.getUserId());
                    User user = userService.getByZc(bbs.getAddUserId());
                    buffer.append("您收到来自『" + rewardUser.getNickname() + "』的赏金\"" + new DecimalFormat("#,###0.00").format(reward.getRewardFee()) + "元。\"").append("\n\n");
                    buffer.append("<a href='"+ PathUtil.getUrlPath("api/bbsController/bbsDetail?id=" + bbs.getId()) +"'>点击查看</a>");

                    openid = user.getName();
                } else if("bbs_d".equals(objectType)) {
                    ZcForumBbs bbs = zcForumBbsService.get(objectId);
                    User user = userService.getByZc(bbs.getAddUserId());
                    buffer.append("您的主题帖\"" + bbs.getBbsTitle() + "\"被管理员删除，申诉请移步“站务公告”申诉专版。");
                    openid = user.getName();
                }

                TextMessage tm = new TextMessage();
                Text text = new Text();
                text.setContent(buffer.toString());
                tm.setTouser(openid);
                tm.setMsgtype("text");
                tm.setText(text);
                WeixinUtil.sendCustomMessage(JSONObject.toJSONString(tm));
                return true;
            }
        });
    }

    public void sendCustomMessage(final String openid, final String content) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                TextMessage tm = new TextMessage();
                Text text = new Text();
                text.setContent(content);
                tm.setTouser(openid);
                tm.setMsgtype("text");
                tm.setText(text);
                WeixinUtil.sendCustomMessage(JSONObject.toJSONString(tm));
                return true;
            }
        });
    }

    public void sendCustomMessageByUserId(final String userId, final String content) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                User user = userService.getByZc(userId);
                TextMessage tm = new TextMessage();
                Text text = new Text();
                text.setContent(content);
                tm.setTouser(user.getName());
                tm.setMsgtype("text");
                tm.setText(text);
                WeixinUtil.sendCustomMessage(JSONObject.toJSONString(tm));
                return true;
            }
        });
    }

    /**
     *
     * @param objectId
     * @param objectType AUTH=认证成功通知 UNSOLD=实时交易提醒-流拍
     */
    public void sendTemplateMessage(final String objectId, final String objectType) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                if("AUTH".equals(objectType)) {
                    System.out.println("认证成功通知sendTemplateMessage----start!");
                    ZcAuthentication zcAuthentication = zcAuthenticationService.get(objectId);
                    User user = userService.getByZc(zcAuthentication.getAddUserId());

                    temp.setTouser(user.getName());
                    temp.setUrl(PathUtil.getUrlPath("api/userController/homePage?userId=" + user.getId()));
                    temp.setTemplate_id(WeixinUtil.AUTH_TEMPLATE_ID);

                    Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                    TemplateData first = new TemplateData();
                    first.setValue("恭喜您成为认证会员，拥有了专属标识和多项特权。\n");
                    //first.setColor("#0000E3");
                    data.put("first", first);
                    // 认证类型
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(Application.getString(zcAuthentication.getAuthType()));
                    keyword1.setColor("#0000E3");
                    data.put("keyword1", keyword1);
                    // 审核结果
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue("认证成功");
                    keyword2.setColor("#0000E3");
                    data.put("keyword2", keyword2);
                    // 审核时间
                    TemplateData keyword3 = new TemplateData();
                    keyword3.setValue(DateUtil.format(zcAuthentication.getAuditTime(), Constants.DATE_FORMAT));
                    data.put("keyword3", keyword3);

                    temp.setData(data);
                } else if("UNSOLD".equals(objectType)) {
                    System.out.println("流拍通知sendTemplateMessage----推送卖家start!");
                    ZcProduct product = zcProductService.get(objectId);
                    // 卖家
                    User seller = userService.getByZc(product.getAddUserId());

                    temp.setTouser(seller.getName());
                    temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
                    temp.setTemplate_id(WeixinUtil.TRANSACTION_TEMPLATE_ID);

                    Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                    TemplateData first = new TemplateData();
                    String content = product.getContentLine();
                    content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
                    first.setValue("尊敬的『"+seller.getNickname()+"』，您的拍品\""+content+"\"在截止时间内无人出价，您可以在帮助中心查询运营推广技巧。");
                    data.put("first", first);
                    // 交易时间
                    TemplateData tradeDateTime = new TemplateData();
                    tradeDateTime.setValue(DateUtil.format(product.getRealDeadline(), "MM月dd日 HH:mm"));
                    data.put("tradeDateTime", tradeDateTime);
                    // 交易类型
                    TemplateData tradeType = new TemplateData();
                    tradeType.setValue("流拍");
                    tradeType.setColor("#0000E3");
                    data.put("tradeType", tradeType);
                    // 交易金额
                    TemplateData curAmount = new TemplateData();
                    curAmount.setValue("无人出价");
                    data.put("curAmount", curAmount);

                    temp.setData(data);

                }

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("认证成功通知/流拍通知 sendTemplateMessage----end!");
                return true;
            }
        });
    }

    /**
     *  给卖家推送未付款交易关闭提醒
     */
    public void sendUnPayTemplateMessage(final ZcOrder order) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                System.out.println("未付款交易关闭提醒sendUnPayTemplateMessage----推送卖家start!");
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 卖家
                User seller = userService.getByZc(product.getSellerUserId());

                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=0"));
                temp.setTemplate_id(WeixinUtil.TRANSACTION_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
                first.setValue("尊敬的『" + seller.getNickname() + "』，您的拍品\"" + content + "\"买家三天未付款，系统已关闭交易。");
                data.put("first", first);
                // 交易时间
                TemplateData tradeDateTime = new TemplateData();
                tradeDateTime.setValue(DateUtil.format(product.getHammerTime(), "MM月dd日 HH:mm"));
                data.put("tradeDateTime", tradeDateTime);
                // 交易类型
                TemplateData tradeType = new TemplateData();
                tradeType.setValue("交易关闭");
                tradeType.setColor("#0000E3");
                data.put("tradeType", tradeType);
                // 交易金额
                TemplateData curAmount = new TemplateData();
                curAmount.setValue("未付款");
                data.put("curAmount", curAmount);
                if(product.getMargin() != null && product.getMargin() > 0) {
                    // 备注
                    TemplateData remark = new TemplateData();
                    remark.setValue("\n买家违约保证金已转入到您的余额，请注意查收！");
                    data.put("remark", remark);
                }


                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("未付款交易关闭提醒sendUnPayTemplateMessage----推送卖家end!");
                return true;
            }
        });
    }

    /**
     *
     */
    public void sendDealTemplateMessage(final ZcProduct product) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                System.out.println("拍卖结果通知sendDealTemplateMessage----推送给卖家start!");
                WxTemplate temp = new WxTemplate();
                // 卖家
                User seller = userService.getByZc(product.getAddUserId());
                // 买家
                User buyer = userService.getByZc(product.getUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? (content.substring(0, 20) + "...") : content;
                System.out.println("拍卖结果通知sendDealTemplateMessage----3!");
                // 推送给卖家
                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
                temp.setTemplate_id(WeixinUtil.TRANSACTION_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『" + seller.getNickname() + "』，您的拍品\"" + content + "\"拍卖成功！拍得者『" + buyer.getNickname() + "』。");
                data.put("first", first);
                // 交易时间
                TemplateData tradeDateTime = new TemplateData();
                tradeDateTime.setValue(DateUtil.format(product.getHammerTime(), "MM月dd日 HH:mm"));
                data.put("tradeDateTime", tradeDateTime);
                // 交易类型
                TemplateData tradeType = new TemplateData();
                tradeType.setValue("成交");
                data.put("tradeType", tradeType);
                // 交易金额
                TemplateData curAmount = new TemplateData();
                curAmount.setValue("￥" + product.getHammerPrice());
                curAmount.setColor("#0000E3");
                data.put("curAmount", curAmount);

                temp.setData(data);
                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("拍卖结果通知sendDealTemplateMessage----推送给卖家end!");

                System.out.println("拍卖结果通知sendDealTemplateMessage----推送给买家start!");
                // 推送给买家
                temp = new WxTemplate();
                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=1"));
                temp.setTemplate_id(WeixinUtil.DEAL_TEMPLATE_ID);

                data = new HashMap<String, TemplateData>();
                String payDownTime = DateUtil.format(DateUtil.addDayToDate(product.getHammerTime(), 3), "yyyy年MM月dd日HH时mm分");
                first = new TemplateData();
                first.setValue("『" + buyer.getNickname() + "』您好，恭喜您已竞拍成功！请于" + payDownTime + "前付款至网站，逾期将会被扣除保证金。\n");
                data.put("first", first);
                // 拍品编号
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(product.getPno());
                data.put("keyword1", keyword1);
                // 拍品名称
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 成交价格
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue("￥" + product.getHammerPrice());
                keyword3.setColor("#0000E3");
                data.put("keyword3", keyword3);
                // 结束时间
                TemplateData keyword4 = new TemplateData();
                keyword4.setValue(DateUtil.format(product.getRealDeadline(), "MM月dd日 HH:mm"));
                data.put("keyword4", keyword4);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("拍卖结果通知sendDealTemplateMessage----推送给买家end!");

                return true;
            }
        });
    }

    /**
     *  付款提醒
     */
    public void sendPayTemplateMessage(final ZcOrder order, final int h) {
        System.out.println("付款提醒sendPayTemplateMessage----推送买家start!------付款" + h + "小时提醒");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 买家
                User buyer = userService.getByZc(product.getBuyerUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=1"));
                temp.setTemplate_id(WeixinUtil.PAY_REMIND_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『"+buyer.getNickname()+"』，您的交易\""+content+"\"离付款还有 "+h+" 小时，请尽快付款，逾期会增加您的违约次数如已交保证金将会被扣除。\n");
                data.put("first", first);
                // 订单号
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(order.getOrderNo());
                data.put("keyword1", keyword1);
                // 订单金额
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue("￥" + product.getTotalPrice());
                keyword2.setColor("#0000E3");
                data.put("keyword2", keyword2);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n请您及时付款！");
                data.put("remark", remark);
                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("付款提醒sendPayTemplateMessage----推送买家end!");
                return true;
            }
        });
    }

    /**
     *  卖家发货提醒
     */
    public void sendDeliverSTemplateMessage(final ZcOrder order, final int h) {
        System.out.println("发货提醒sendDeliverSTemplateMessage----推送卖家start!------付款" + h + "小时提醒发货");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 卖家
                User seller = userService.getByZc(product.getSellerUserId());

                ZcAddress address = new ZcAddress();
                address.setUserId(product.getBuyerUserId());
                address.setAtype(1); // 1:收货地址; 2:退货地址
                address.setOrderId(order.getId());
                address = zcAddressService.get(address);

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=3"));
                temp.setTemplate_id(WeixinUtil.DELIVER_S_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                String firstValue = "";
                if(h == 0) {
                    firstValue = "您有一笔交易\""+content+"\"买家已完成支付，请于5天内货物发出。";
                } else {
                    firstValue = "您有一笔交易\""+content+"\"等待您的发货，买方已在"+h+"小时前付款。";
                }
                first.setValue("尊敬的『"+seller.getNickname()+"』，" + firstValue + "\n");
                data.put("first", first);
                // 订单金额
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue("￥" + product.getTotalPrice());
                keyword1.setColor("#0000E3");
                data.put("keyword1", keyword1);
                // 商品详情
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 收货信息
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue(address.getUserName() + "，" + address.getTelNumber() + "，" + address.getProvinceName() + address.getCityName() + address.getCountyName() +address.getDetailInfo());
                keyword3.setColor("#0000E3");
                data.put("keyword3", keyword3);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒：收货信息仅供参考，具体请根据订单详情中收货信息");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("发货提醒sendDeliverSTemplateMessage----推送卖家end!");
                return true;
            }
        });
    }

    /**
     *  type=1:卖家订单发货提醒买家 type=2:买家退货发货提醒卖家
     */
    public void sendDeliverBTemplateMessage(final ZcOrder order, final int type) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                if(type == 1) {
                    System.out.println("发货成功通知买家确认收货sendDeliverBTemplateMessage----推送买家start!");
                    sendDeliverTemplateMessageS_B(order);
                    System.out.println("发货成功通知买家确认收货sendDeliverBTemplateMessage----推送买家end!");
                } else {
                    System.out.println("买家退货发货提醒卖家sendDeliverBTemplateMessage----推送卖家start!");
                    sendDeliverTemplateMessageB_S(order);
                    System.out.println("买家退货发货提醒卖家sendDeliverBTemplateMessage----推送卖家end!");
                }

                return true;
            }
        });
    }

    // 买家退货发货提醒卖家
    private void sendDeliverTemplateMessageB_S(ZcOrder order) {
        WxTemplate temp = new WxTemplate();
        OrderProductInfo product = zcOrderService.getProductInfo(order);
        // 卖家
        User seller = userService.getByZc(product.getSellerUserId());

        String content = product.getContentLine();
        content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        temp.setTouser(seller.getName());
        temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=2"));
        temp.setTemplate_id(WeixinUtil.DELIVER_B_TEMPLATE_ID);

        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        TemplateData first = new TemplateData();
        first.setValue("尊敬的『"+seller.getNickname()+"』，买家申请退货的拍品\""+content+"\"已发货。\n");
        data.put("first", first);
        // 订单编号
        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(order.getOrderNo());
        data.put("keyword1", keyword1);
        // 物流公司
        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(order.getReturnExpressName());
        keyword2.setColor("#0000E3");
        data.put("keyword2", keyword2);
        // 物流单号
        TemplateData keyword3 = new TemplateData();
        keyword3.setValue(order.getReturnExpressNo());
        keyword3.setColor("#0000E3");
        data.put("keyword3", keyword3);
        // 备注
        TemplateData remark = new TemplateData();
        remark.setValue("\n提醒：请您再收到退货后，再退款给买家(发货14天后，将自动确认退款给买家)");
        data.put("remark", remark);

        temp.setData(data);
        WeixinUtil.sendTemplateMessage(temp);
    }

    // 卖家订单发货提醒买家
    private void sendDeliverTemplateMessageS_B(ZcOrder order) {
        WxTemplate temp = new WxTemplate();
        OrderProductInfo product = zcOrderService.getProductInfo(order);
        // 买家
        User buyer = userService.getByZc(product.getBuyerUserId());

        ZcAddress address = new ZcAddress();
        address.setUserId(product.getBuyerUserId());
        address.setAtype(1); // 1:收货地址; 2:退货地址
        address.setOrderId(order.getId());
        address = zcAddressService.get(address);

        String content = product.getContentLine();
        content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        temp.setTouser(buyer.getName());
        temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=2"));
        temp.setTemplate_id(WeixinUtil.DELIVER_B_TEMPLATE_ID);

        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        TemplateData first = new TemplateData();
        first.setValue("尊敬的『"+buyer.getNickname()+"』，卖家已将拍品\""+content+"\"发出，请于14天内进行确认收货或者退回操作，逾期将自动结束交易并打款给卖家。\n");
        data.put("first", first);
        // 订单编号
        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(order.getOrderNo());
        data.put("keyword1", keyword1);
        // 物流公司
        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(order.getExpressName());
        keyword2.setColor("#0000E3");
        data.put("keyword2", keyword2);
        // 物流单号
        TemplateData keyword3 = new TemplateData();
        keyword3.setValue(order.getExpressNo());
        keyword3.setColor("#0000E3");
        data.put("keyword3", keyword3);
        // 备注
        TemplateData remark = new TemplateData();
        remark.setValue("收货信息：" + address.getUserName() + "，" + address.getTelNumber() + "，" + address.getProvinceName() + address.getCityName() + address.getCountyName() +address.getDetailInfo());
        data.put("remark", remark);

        temp.setData(data);
        WeixinUtil.sendTemplateMessage(temp);
    }

    /**
     *  交易完成通知卖家
     */
    public void sendDealCompleteTemplateMessage(final ZcOrder order) {
        System.out.println("交易完成通知sendDealCompleteTemplateMessage----推送卖家start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 卖家
                User seller = userService.getByZc(product.getSellerUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/orderDetail?id=" + order.getId()));
                temp.setTemplate_id(WeixinUtil.DEAL_COMPLETE_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『" + seller.getNickname() + "』，买家已确认收到\"" + content + "\"，交易完成，双方款项已结，信誉点已加。\n");
                data.put("first", first);
                // 订单金额
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue("￥" + product.getTotalPrice());
                keyword1.setColor("#0000E3");
                data.put("keyword1", keyword1);
                // 商品详情
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 订单编号
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue(order.getOrderNo());
                data.put("keyword3", keyword3);
                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("交易完成通知sendDealCompleteTemplateMessage----推送卖家end!");
                return true;
            }
        });
    }

    /**
     *  拍卖结束提醒
     */
    public void sendAuctionEndTemplateMessage(final ZcProduct product, final int h, final int m) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                final String time = h == 0 ? (m+"分钟") : (h+"小时");
                WxTemplate temp = new WxTemplate();
                // 卖家
                User seller = userService.getByZc(product.getAddUserId());
                System.out.println("拍卖结束提醒sendAuctionEndTemplateMessage----推送卖家start!");
                sendAuctionEndTemplateMessage(product, seller, 1, time);
                System.out.println("拍卖结束提醒sendAuctionEndTemplateMessage----推送卖家end!");

                if(h == 0) {
                    // 提醒所有参拍用户
                    ZcAuction a = new ZcAuction();
                    a.setProductId(product.getId());
                    List<ZcAuction> auctions = zcAuctionService.query(a);
                    List<String> buyerIds = new ArrayList<String>();
                    if(CollectionUtils.isNotEmpty(auctions)) {
                        System.out.println("拍卖结束提醒sendAuctionEndTemplateMessage----推送所有参拍用户start!");
                        for(ZcAuction auction : auctions) {
                            // 排除重复出价人
                            if(!buyerIds.contains(auction.getBuyerId())) {
                                buyerIds.add(auction.getBuyerId());
                                completionService.submit(new Task<String, Boolean>(auction.getBuyerId()) {
                                    @Override
                                    public Boolean call() throws Exception {
                                        User user = userService.getByZc(getD());
                                        sendAuctionEndTemplateMessage(product, user, 2, time);
                                        return true;
                                    }
                                });
                            }
                        }
                        System.out.println("拍卖结束提醒sendAuctionEndTemplateMessage----推送所有参拍用户end!");
                    }

                    // 提醒所有围观用户
                    ZcProductLike l = new ZcProductLike();
                    l.setProductId(product.getId());
                    List<ZcProductLike> likes = zcProductLikeService.query(l);
                    if(CollectionUtils.isNotEmpty(likes)) {
                        List<String> likerIds = new ArrayList<String>();
                        System.out.println("拍卖结束提醒sendAuctionEndTemplateMessage----推送所有围观用户start!");
                        for(ZcProductLike like : likes) {
                            if(buyerIds.contains(like.getUserId())) continue; // 排除参拍用户避免重复发送
                            // 排除重复围观人
                            if(!likerIds.contains(like.getUserId())) {
                                likerIds.add(like.getUserId());
                                completionService.submit(new Task<String, Boolean>(like.getUserId()) {
                                    @Override
                                    public Boolean call() throws Exception {
                                        User user = userService.getByZc(getD());
                                        sendAuctionEndTemplateMessage(product, user, 3, time);
                                        return true;
                                    }
                                });
                            }
                        }
                        System.out.println("拍卖结束提醒sendAuctionEndTemplateMessage----推送所有围观用户end!");
                    }
                }

                return true;
            }
        });
    }

    private void sendAuctionEndTemplateMessage(ZcProduct product, User user, int type, String time) {
        WxTemplate temp = new WxTemplate();

        String content = product.getContentLine();
        content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        temp.setTouser(user.getName());
        temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
        temp.setTemplate_id(WeixinUtil.AUCTION_END_TEMPLATE_ID);

        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        TemplateData first = new TemplateData();
        String firstValue = "";
        if (type == 1) firstValue = "您的\"" + content + "\"拍品距离结束还有" + time + "，分享到微信群和朋友圈有助于成交！";
        else if (type == 2) firstValue = "您参拍的\"" + content + "\"拍品距离结束还有" + time + "。";
        else firstValue = "您围观的\"" + content + "\"拍品距离结束还有" + time + "。";
        first.setValue("『" + user.getNickname() + "』您好，" + firstValue);
        data.put("first", first);
        // 拍卖期数
        TemplateData number = new TemplateData();
        number.setValue(product.getPno());
        data.put("number", number);
        // 拍品名称
        TemplateData name = new TemplateData();
        name.setValue(content);
        data.put("name", name);
        // 预定结束时间
        TemplateData deadline = new TemplateData();
        deadline.setValue(DateUtil.format(product.getRealDeadline(), "MM月dd日 HH:mm"));
        deadline.setColor("#0000E3");
        data.put("deadline", deadline);
        // 目前出价
        TemplateData remark = new TemplateData();
        remark.setValue("目前出价：" + product.getCurrentPrice() + " 元");
        data.put("remark", remark);

        temp.setData(data);
        WeixinUtil.sendTemplateMessage(temp);
    }

    /**
     *  给卖家发送退货申请提醒
     */
    public void sendBackApplyTemplateMessage(final ZcOrder order) {
        System.out.println("退货申请提醒sendBackApplyTemplateMessage----推送卖家start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 卖家
                User seller = userService.getByZc(product.getSellerUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                String backReason = Application.getString(order.getReturnApplyReason());
                if("RR99".equals(order.getReturnApplyReason())) {
                    backReason = order.getReturnApplyReasonOther();
                }

                Date endTime = DateUtil.addDayToDate(order.getReturnApplyTime(), 3);

                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=2"));
                temp.setTemplate_id(WeixinUtil.BACK_APPLY_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("『"+seller.getNickname()+"』您好，您的拍品买家申请退货。\n退货理由：" + backReason + "\n");
                data.put("first", first);
                // 订单编号
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(product.getPno());
                data.put("keyword1", keyword1);
                // 商品名称
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 订单金额
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue("￥" + product.getTotalPrice());
                data.put("keyword3", keyword3);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒："+DateUtil.format(endTime, "MM月dd日 HH:mm")+"前未处理，将自动同意退货");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("退货申请提醒sendBackApplyTemplateMessage----推送卖家end!");
                return true;
            }
        });
    }

    /**
     *  保证金不退还通知
     */
    public void sendMarginNonTemplateMessage(final ZcProduct product) {
        System.out.println("违约保证金不退还通知sendMarginNonTemplateMessage----推送买家start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                // 买家
                User buyer = userService.getByZc(product.getUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
                temp.setTemplate_id(WeixinUtil.MARGIN_NON_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『"+buyer.getNickname()+"』\n");
                data.put("first", first);
                // 内容
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue("您在拍场因违反竞拍规则，保证金"+product.getMargin()+"元不予以退回");
                data.put("keyword1", keyword1);
                // 拍品名称
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒：如有疑问请联系集东集西客服");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("违约保证金不退还通知sendMarginNonTemplateMessage----推送买家end!");
                return true;
            }
        });
    }

    /**
     *  保证金退还通知
     */
    public void sendMarginRefundTemplateMessage(final ZcProductMargin margin, final String msg) {
        System.out.println("保证金退还通知sendMarginRefundTemplateMessage----推送买家start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();

                ZcProduct product = zcProductService.get(margin.getProductId());
                // 买家
                User buyer = userService.getByZc(margin.getBuyUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
                temp.setTemplate_id(WeixinUtil.MARGIN_REFUND_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『"+buyer.getNickname()+"』\n");
                data.put("first", first);
                // 内容
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(msg + "，保证金"+product.getMargin()+"元已退回");
                data.put("keyword1", keyword1);
                // 拍品名称
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒：请注意查收，如有疑问请联系集东集西客服");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("保证金退还通知sendMarginRefundTemplateMessage----推送买家end!");
                return true;
            }
        });
    }

    /**
     *  给买家发送退款通知
     */
    public void sendRefundTemplateMessage(final ZcOrder order, final long serviceFee) {
        System.out.println("退款通知sendRefundTemplateMessage----推送买家start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 买家
                User buyer = userService.getByZc(product.getBuyerUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                String firstValue = "尊敬的『"+buyer.getNickname()+"』,\""+content+"\"拍品的退货申请处理成功，货款已退回\n";
                String reasonValue = "申请退货";
                if("OC002".equals(order.getOrderCloseReason())) {
                    firstValue = "尊敬的『"+buyer.getNickname()+"』,\""+content+"\"拍品卖家未发货，货款已退回\n";
                    reasonValue = "未发货";
                }

                // 技术服务费
                double sf = 0, refundFee = product.getTotalPrice();
                String str = "";
                if(serviceFee > 0 && "OC003".equals(order.getOrderCloseReason())) {
                    sf = ((double)serviceFee)/100;
                    if(!"RR99".equals(order.getReturnApplyReason())) {
                        str = "退款含" + sf + "元技术服务费，";
                        refundFee += sf;
                    } else {
                        str = "已扣除" + sf + "元技术服务费，";
                    }
                }

                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/orderDetail?id=" + order.getId()));
                temp.setTemplate_id(WeixinUtil.REFUND_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue(firstValue);
                data.put("first", first);
                // 退款原因
                TemplateData reason = new TemplateData();
                reason.setValue(reasonValue);
                data.put("reason", reason);
                // 退款金额
                TemplateData refund = new TemplateData();
                refund.setValue("￥" + refundFee + "元");
                refund.setColor("#0000E3");
                data.put("refund", refund);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒："+str+"如未收到退款，请联系集东集西客服");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("退款通知sendRefundTemplateMessage----推送买家end!");
                return true;
            }
        });
    }

    /**
     *  给买家发送退货申请结果通知
     *  type:1-卖家同意退货；2-卖家拒绝退货
     */
    public void sendBackResultTemplateMessage(final ZcOrder order) {
        System.out.println("退货申请结果通知sendBackResultTemplateMessage----推送买家start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 买家
                User buyer = userService.getByZc(product.getBuyerUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                String firstValue = "尊敬的『"+buyer.getNickname()+"』,您的申请退货已受理，卖家同意您的退货\n";
                String remarkValue = "\n提醒：请于5个工作日内将原货物退回";
                if("RS02".equals(order.getBackStatus())) {
                    String refuseReturnReason = Application.getString(order.getRefuseReturnReason());
                    if("RF99".equals(order.getRefuseReturnReason())) {
                        refuseReturnReason = order.getRefuseReturnReasonOther();
                    }
                    firstValue = "尊敬的『"+buyer.getNickname()+"』,您的申请退货已被卖家拒绝退货。\n理由：" + refuseReturnReason + "\n";
                    remarkValue = "\n提醒：如有疑问您可以申请小二介入或联系集东集西客服";
                }

                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=2"));
                temp.setTemplate_id(WeixinUtil.BACK_RESULT_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue(firstValue);
                data.put("first", first);
                // 订单编号
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(order.getOrderNo());
                data.put("keyword1", keyword1);
                // 商品信息
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(content);
                data.put("keyword2", keyword2);
                // 商品数量
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue("1");
                data.put("keyword3", keyword3);
                // 商品金额
                TemplateData keyword4 = new TemplateData();
                keyword4.setValue("￥" + product.getTotalPrice() + "元");
                keyword4.setColor("#0000E3");
                data.put("keyword4", keyword4);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue(remarkValue);
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("退货申请结果通知sendBackResultTemplateMessage----推送买家end!");
                return true;
            }
        });
    }

    /**
     *  给用户发送转账通知
     *  type:1-转账成功；2-转账失败
     */
    public void sendOfflineTransferTemplateMessage(final String offlineTransferId, final int type) {
        System.out.println("转账通知sendOfflineTransferTemplateMessage----start!");
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();

                ZcOfflineTransfer offlineTransfer = zcOfflineTransferService.get(offlineTransferId);
                ZcWallet wallet = new ZcWallet();
                wallet.setUserId(offlineTransfer.getUserId());
                wallet = zcWalletService.get(wallet);

                // 汇款用户
                User user = userService.getByZc(offlineTransfer.getUserId());

                String firstValue = "尊敬的『"+user.getNickname()+"』,您在集东集西中提交的线下汇款信息我方核实无误，余额充值成功。\n";
                if(type == 2) {
                    firstValue = "尊敬的『"+user.getNickname()+"』,您在集东集西中提交的线下汇款信息我方核实有误，余额充值失败。\n";
                }

                temp.setTouser(user.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiWallet/myWallet"));
                temp.setTemplate_id(WeixinUtil.OFFLINE_TRANSFER_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue(firstValue);
                data.put("first", first);
                // 转账状态
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(type == 1 ? "成功" : "失败");
                keyword1.setColor(type == 1 ? "#0000E3" : "#ff0000");
                data.put("keyword1", keyword1);
                // 转账金额
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue("￥" + offlineTransfer.getTransferAmount() + "元");
                keyword2.setColor("#0000E3");
                data.put("keyword2", keyword2);
                // 订单号
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue(offlineTransfer.getTransferNo());
                data.put("keyword3", keyword3);
                // 转账后账户余额
                TemplateData keyword4 = new TemplateData();
                keyword4.setValue("￥" + (wallet == null ? 0 : wallet.getAmount()) + "元");
                data.put("keyword4", keyword4);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒：如有疑问请联系集东集西客服");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                System.out.println("转账通知sendOfflineTransferTemplateMessage----end!");
                return true;
            }
        });
    }

    /**
     *  给用户发送提现通知
     *  cashFlag:true-提现成功；false-提现失败
     */
    public void sendCashTemplateMessage(final String walletDetailId, final boolean cashFlag) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();

                ZcWalletDetail zcWalletDetail = zcWalletDetailService.get(walletDetailId);

                // 提现用户
                User user = userService.getByZc(zcWalletDetail.getUserId());

                String firstValue = "尊敬的『"+user.getNickname()+"』,";
                if(cashFlag && "CS01".equals(zcWalletDetail.getChannel())) {
                    firstValue += "您在集东集西申请的提现已成功放款到微信零钱。\n";
                } else if(cashFlag && "CS03".equals(zcWalletDetail.getChannel())) {
                    String bankCard = zcWalletDetail.getBankCard();
                    firstValue += "您在集东集西申请的提现已放款到尾号"+bankCard.substring(bankCard.length() - 4)+"银行卡中。\n";
                } else if(!cashFlag && "CS01".equals(zcWalletDetail.getChannel())) {
                    firstValue += "您在集东集西提现至微信零钱失败。\n";
                } else if(!cashFlag && "CS03".equals(zcWalletDetail.getChannel())) {
                    String bankCard = zcWalletDetail.getBankCard();
                    firstValue += "您在集东集西提现至尾号"+bankCard.substring(bankCard.length() - 4)+"银行卡中失败。\n";
                }

                temp.setTouser(user.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiWallet/myWallet"));
                temp.setTemplate_id(cashFlag ? WeixinUtil.CASH_SUCCESS_TEMPLATE_ID : WeixinUtil.CASH_FAIL_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue(firstValue);
                data.put("first", first);
                if(cashFlag) {
                    // 提现金额
                    TemplateData money = new TemplateData();
                    money.setValue(zcWalletDetail.getAmount() + "元");
                    money.setColor("#0000E3");
                    data.put("money", money);
                    // 提现时间
                    TemplateData timet = new TemplateData();
                    timet.setValue(DateUtil.format(zcWalletDetail.getAddtime(), "yyyy年MM月dd日 HH:mm"));
                    data.put("timet", timet);
                } else {
                    // 提现金额
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(zcWalletDetail.getAmount() + "元");
                    keyword1.setColor("#0000E3");
                    data.put("keyword1", keyword1);
                    // 提现时间
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue(DateUtil.format(zcWalletDetail.getAddtime(), "yyyy年MM月dd日 HH:mm"));
                    data.put("keyword2", keyword2);
                    // 原因说明
                    TemplateData keyword3 = new TemplateData();
                    keyword3.setValue(zcWalletDetail.getHandleRemark());
                    data.put("keyword3", keyword3);
                }
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒：如有疑问请联系集东集西客服");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);

                return true;
            }
        });
    }

    /**
     * 给卖家发送当面交易提醒
     * @param order
     */
    public void sendFacePayTemplateMessage(final ZcOrder order) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 卖家
                User seller = userService.getByZc(product.getSellerUserId());
                // 买家
                User buyer = userService.getByZc(product.getBuyerUserId());

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;


                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=1"));
                temp.setTemplate_id(WeixinUtil.TRANSACTION_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『" + seller.getNickname() + "』，您的拍品\"" + content + "\"由拍得者『" + buyer.getNickname() + "』发起当面交易，请您及时处理。");
                data.put("first", first);
                // 交易时间
                TemplateData tradeDateTime = new TemplateData();
                tradeDateTime.setValue(DateUtil.format(order.getFaceTime(), "MM月dd日 HH:mm"));
                data.put("tradeDateTime", tradeDateTime);
                // 交易类型
                TemplateData tradeType = new TemplateData();
                tradeType.setValue("当面交易");
                tradeType.setColor("#0000E3");
                data.put("tradeType", tradeType);
                // 交易金额
                TemplateData curAmount = new TemplateData();
                curAmount.setValue("￥" + product.getTotalPrice());
                data.put("curAmount", curAmount);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                return true;
            }
        });
    }

    /**
     * 给卖家发送中介交易提醒
     * @param
     */
    public void sendIMPayTemplateMessage(final ZcIntermediary intermediary, final int h) {
        final CompletionService completionService = CompletionFactory.initCompletion();
        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                ZcForumBbs bbs = zcForumBbsService.get(intermediary.getBbsId());
                // 卖家
                User seller = userService.getByZc(intermediary.getSellUserId());
                // 买家
                User buyer = userService.getByZc(intermediary.getUserId());

                String bbsTitle = bbs.getBbsTitle();
                bbsTitle = bbsTitle.length() > 20 ? bbsTitle.substring(0, 20) + "..." : bbsTitle;


                temp.setTouser(seller.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiIntermediary/intermediaryDetail?id=" + intermediary.getId()));
                temp.setTemplate_id(WeixinUtil.TRANSACTION_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                String firstValue = "";
                if(h == 0) {
                    firstValue = "尊敬的『" + seller.getNickname() + "』，您的帖子\"" + bbsTitle + "\"由『" + buyer.getNickname() + "』发起中介交易，请您及时处理。";
                } else {
                    firstValue = "尊敬的『" + seller.getNickname() + "』，您的帖子\""+bbsTitle+"\"有一笔中介交易离结束还有"+h+"小时，请您及时处理。";
                }
                first.setValue(firstValue);
                data.put("first", first);
                // 交易时间
                TemplateData tradeDateTime = new TemplateData();
                tradeDateTime.setValue(DateUtil.format(intermediary.getAddtime(), "MM月dd日 HH:mm"));
                data.put("tradeDateTime", tradeDateTime);
                // 交易类型
                TemplateData tradeType = new TemplateData();
                tradeType.setValue("中介交易");
                tradeType.setColor("#0000E3");
                data.put("tradeType", tradeType);
                // 交易金额
                TemplateData curAmount = new TemplateData();
                curAmount.setValue("￥" + new BigDecimal(intermediary.getAmount()).divide(new BigDecimal(100)));
                curAmount.setColor("#0000E3");
                data.put("curAmount", curAmount);

                if(!F.empty(intermediary.getRemark())) {
                    // 备注
                    TemplateData remark = new TemplateData();
                    remark.setValue("交易备注：" + intermediary.getRemark());
                    data.put("remark", remark);
                }

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                return true;
            }
        });
    }

    public void sendFaceResultTemplateMessage(final ZcOrder order) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                OrderProductInfo product = zcOrderService.getProductInfo(order);
                // 买家
                User buyer = userService.getByZc(product.getBuyerUserId());
                boolean agree = true;
                if("FS03".equals(order.getFaceStatus())) agree = false;

                String content = product.getContentLine();
                content = content.length() > 20 ? content.substring(0, 20) + "..." : content;


                temp.setTouser(buyer.getName());
                temp.setUrl(PathUtil.getUrlPath(agree ? "api/apiOrder/orderDetail?id=" + order.getId() : "api/apiOrder/myOrder?type=1"));
                temp.setTemplate_id(WeixinUtil.APPLY_RESULT_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『" + buyer.getNickname() + "』，您在拍品\"" + content + "\"中申请的当面交易卖家"+(agree ? "已同意" : "已拒绝")+"。\n");
                data.put("first", first);
                // 申请内容
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue("当面交易");
                keyword1.setColor("#0000E3");
                data.put("keyword1", keyword1);
                // 申请结果
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(agree ? "同意" : "拒绝");
                keyword2.setColor(agree ? "#0000E3" : "#ff0000");
                data.put("keyword2", keyword2);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                return true;
            }
        });
    }

    public void sendIMResultTemplateMessage(final ZcIntermediary intermediary) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                ZcForumBbs bbs = zcForumBbsService.get(intermediary.getBbsId());
                // 买家
                User buyer = userService.getByZc(intermediary.getUserId());
                boolean agree = false, refuse = false;
                if("IS02".equals(intermediary.getStatus())) agree = true;
                else if("IS04".equals(intermediary.getStatus())) refuse = true;

                String bbsTitle = bbs.getBbsTitle();
                bbsTitle = bbsTitle.length() > 20 ? bbsTitle.substring(0, 20) + "..." : bbsTitle;


                temp.setTouser(buyer.getName());
                if(agree) {
                    temp.setUrl(PathUtil.getUrlPath("api/apiOrder/myOrder?type=1"));
                } else {
                    temp.setUrl(PathUtil.getUrlPath("api/apiIntermediary/intermediaryDetail?id=" + intermediary.getId()));
                }
                temp.setTemplate_id(WeixinUtil.APPLY_RESULT_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『" + buyer.getNickname() + "』，您在帖子\"" + bbsTitle + "\"中申请的中介交易卖家"+(agree ? "已同意，请前往我的订单进行支付" : (refuse ? "已拒绝" : "未处理，交易已取消"))+"。\n");
                data.put("first", first);
                // 申请内容
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue("中介交易");
                keyword1.setColor("#0000E3");
                data.put("keyword1", keyword1);
                // 申请结果
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(agree ? "同意" : (refuse ? "拒绝" : "已取消"));
                keyword2.setColor(agree ? "#0000E3" : "#ff0000");
                data.put("keyword2", keyword2);
                if(!agree && refuse && !F.empty(intermediary.getContent())) {
                    // 备注
                    TemplateData remark = new TemplateData();
                    remark.setValue("拒绝备注：" + intermediary.getContent());
                    data.put("remark", remark);
                }

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                return true;
            }
        });
    }

    public void sendAuthFailTemplateMessage(final String authId) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                ZcAuthentication zcAuthentication = zcAuthenticationService.get(authId);
                User user = userService.getByZc(zcAuthentication.getAddUserId());

                temp.setTouser(user.getName());
                temp.setUrl(PathUtil.getUrlPath("api/userController/homePage?userId=" + user.getId()));
                temp.setTemplate_id(WeixinUtil.AUTH_FAIL_TEMPLATE_ID);

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();
                TemplateData first = new TemplateData();
                first.setValue("尊敬的『" + user.getNickname() + "』，您申请的实名认证失败。\n");
                data.put("first", first);
                // 认证类型
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(Application.getString(zcAuthentication.getAuthType()));
                keyword1.setColor("#0000E3");
                data.put("keyword1", keyword1);
                // 审核结果
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue("认证失败");
                keyword2.setColor("#ff0000");
                data.put("keyword2", keyword2);
                // 审核时间
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue(DateUtil.format(zcAuthentication.getAuditTime(), Constants.DATE_FORMAT));
                data.put("keyword3", keyword3);
                // 审核时间
                TemplateData keyword4 = new TemplateData();
                keyword4.setValue(zcAuthentication.getAuditRemark());
                data.put("keyword4", keyword4);
                // 备注
                TemplateData remark = new TemplateData();
                remark.setValue("\n提醒：请重新上传资料或联系集东集西客服");
                data.put("remark", remark);

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                return true;
            }
        });
    }

    /**
     * 充值扣款通知
     * @param zcWalletDetail
     */
    public void sendRechargeTemplateMessage(final ZcWalletDetail zcWalletDetail) {
        final CompletionService completionService = CompletionFactory.initCompletion();

        completionService.submit(new Task<Object, Boolean>(null) {
            @Override
            public Boolean call() throws Exception {
                WxTemplate temp = new WxTemplate();
                User user = userService.getByZc(zcWalletDetail.getUserId());
                boolean recharge = true;
                double amount = zcWalletDetail.getAmount();
                if(amount < 0) {
                    recharge = false;
                    amount = -amount;
                }

                Map<String, TemplateData> data = new HashMap<String, TemplateData>();

                temp.setTouser(user.getName());
                temp.setUrl(PathUtil.getUrlPath("api/apiWallet/myWallet"));
                if(recharge) {
                    temp.setTemplate_id(WeixinUtil.RECHARGE_TEMPLATE_ID);

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的『" + user.getNickname() + "』，您有一笔余额充值到账。");
                    data.put("first", first);

                    // 充值金额
                    TemplateData money = new TemplateData();
                    money.setValue(amount + "元");
                    money.setColor("#0000E3");
                    data.put("money", money);

                    // 充值方式
                    TemplateData product = new TemplateData();
                    product.setValue("后台充值");
                    product.setColor("#0000E3");
                    data.put("product", product);

                    // 备注
                    if(!F.empty(zcWalletDetail.getDescription())) {
                        TemplateData remark = new TemplateData();
                        remark.setValue("\n备注：" + zcWalletDetail.getDescription());
                        data.put("remark", remark);
                    }

                } else {
                    temp.setTemplate_id(WeixinUtil.DEBIT_TEMPLATE_ID);

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的『" + user.getNickname() + "』，您有一笔扣款产生。\n");
                    data.put("first", first);

                    // 扣款金额
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(amount + "元");
                    keyword1.setColor("#0000E3");
                    data.put("keyword1", keyword1);

                    // 扣款原因
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue(zcWalletDetail.getDescription());
                    data.put("keyword2", keyword2);

                    // 备注
                    TemplateData remark = new TemplateData();
                    remark.setValue("\n提醒：如有疑问请联系集东集西客服");
                    data.put("remark", remark);
                }

                temp.setData(data);

                WeixinUtil.sendTemplateMessage(temp);
                return true;
            }
        });
    }
}
