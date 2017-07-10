<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="jb.listener.Application"%>
<%
    request.setAttribute("vEnter", "\n");
    String amSwitch = Application.getString("SV400");
    amSwitch = amSwitch == null ? "1" : amSwitch;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>拍品详情</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.product.detail.css?v=${staticVersion}"/>
    <style>
        .ui-header-fixed {
            position: absolute;
        }
        .renzheng-input .ui-input-text input {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div data-role="header" data-position="fixed" data-theme="b" data-tap-toggle="false" style="height:43px;z-index: 99;">
            <!--<a href="../toolbar/" data-rel="back" class="ui-btn ui-btn-left ui-alt-icon ui-nodisc-icon ui-corner-all ui-btn-icon-notext ui-icon-carat-l">Back</a>-->
            <h2></h2>
            <div class="ppxq-userinfo">
                <div class="ppxq-content">
                    <div style=" font-size:18px; font-weight:normal;">${user.nickname}</div>
                    <div class="ppxq-userdesc" style="margin-top:20px;color:#666;font-weight:normal; font-size:12px;">${not empty user.bardian ? user.bardian : '这个人很懒，什么也没说'}</div>
                </div>
                <img class="ppxq-title" src="${user.headImage}" onclick="href('api/userController/homePage?userId=${user.id}');"/>
            </div>
        </div><!-- /header -->
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <input type="hidden" id="bindMobile" value="${sessionInfo.mobile}" />
            <div class="mask-layer bbs-detail-layer" style="z-index: 1001;">
                <img src="${pageContext.request.contextPath}/wsale/images/subscribe/bid-icon.jpg" class="subscribe"/>
            </div>

            <!--<div class="more-dialog" style="width: 40%; z-index: 1002;">
                <ul class="more-content">
                    <c:choose>
                        <c:when test="${!user.attred}"><li data-value="1">关注</li></c:when>
                        <c:otherwise><li data-value="0">已关注</li></c:otherwise>
                    </c:choose>
                    <li onclick="href('api/apiChat/chat?toUserId=${user.id}&subscribe=true&productId=${product.id}');">私信</li>
                    <li>举报</li>
                </ul>
            </div>-->
            <div id="sharePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 145px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden;font-size: 14px; ">
                        <div>分享给小伙伴</div>
                        <div>
                            <ul class="share-list">
                                <li>
                                    <div class="share-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/code-icon.png" />
                                    </div>
                                    <div class="msg">二维码</div>
                                </li>
                                <li>
                                    <div class="share-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/link-icon.png" />
                                    </div>
                                    <div class="msg">发送链接</div>
                                </li>
                                <li>
                                    <div class="share-img">
                                        <img style="margin-top:23px" src="${pageContext.request.contextPath}/wsale/images/other-icon.png" />
                                    </div>
                                    <div class="msg">用微信分享</div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div id="maxPricePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 200px;overflow: hidden; text-align: center;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">请输入自动出价上限最高价</h1>
                        </div>
                    </div>

                    <div class="modal-content" style="overflow: hidden;">
                        <div class="renzheng-input" style="background-color: #fff;">
                            <a class="faxian-link" style="padding: 14px;">
                                <div class="list-right">
                                    <input type="tel" placeholder="请输入最高价" id="maxPrice" maxlength="10" class="onlyNum"/>
                                </div>
                                <div class="normal-text">最高价</div>
                            </a>
                        </div>
                        <a class="bottom-btn guanzhu-ok auto-bid" style="color: #fff;font-size: 16px;">自动出价</a>
                    </div>
                </div>
            </div>

            <div id="bindMobilePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 240px; overflow: hidden;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">绑定手机号</h1>
                        </div>
                    </div>
                    <div class="modal-content">
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="11" placeholder="请输入您的手机号码..." id="mobile"/>
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="6" placeholder="请输入验证码..."  id="vcode"/>
                        <div style="float:right;width:90px;text-align:center; margin: -45px 10px;font-size: 15px;border: 1px solid #f0f0f0;padding: 5px 10px" id="vcode-btn">
                            点击获取
                        </div>
                        <div style="text-align: center;">
                            <a class="bottom-btn" style="color: #fff;font-size: 16px;" id="bindMobileBtn">确认</a>
                        </div>
                    </div>
                </div>
            </div>

            <div id="reportPopup" class="weui-popup-container">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="overflow: hidden;">
                    <div class="modal-content" style="padding-top: 0; margin-top: 0px; overflow: hidden;">
                        <div style="background-color:#fff; padding: 0 5px;border-bottom:1px solid #ddd;">
                            <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" class="reportBtn">
                                举 报
                            </div>
                            <div style="width:80%; padding: 10px;" class="close-popup">
                                <span style="padding: 10px 0px;">关 闭</span>
                            </div>
                        </div>
                        <textarea style="margin:10px 0px; background-color: #fff;" maxlength="100" placeholder="请输入您的举报理由，不得出现不和谐文字..." id="reportReason"></textarea>
                    </div>
                </div>
            </div>

            <div class="ppxq-detail">
                <div class="ppxq-leftinfo">
                    <div>
                        <img class="ppxq-touxiang" src="${user.headImage}" onclick="href('api/userController/homePage?userId=${user.id}');"/>
                        <div class="ppxq-level">
                            <img src="${pageContext.request.contextPath}/wsale/images/p-${user.positionId}.png" style="width:50%;" />
                        </div>
                        <div style="line-height:1.5; text-align:left;">
                            <div style="color:#EF8326;font-size:12px;text-align:center;letter-spacing:0;">帖子：${user.bbsNums}</div>
                            <c:if test="${!user.self}">
                                <%--<div class="tieziInfo-more" style="font-size:12px;color:#888;margin-top:5px;">更多 <img src="${pageContext.request.contextPath}/wsale/images/more-icon.png" style="height:10px; vertical-align:middle;" /></div>--%>
                                <div class="sixin botton-b" onclick="href('api/apiChat/chat?toUserId=${user.id}&subscribe=true&productId=${product.id}');">私 信</div>
                                <div class="jubao report">举 报</div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="ppxq-rightinfo">
                    <div style="font-size:16px; color:#666;">
                        ${user.nickname}
                        <c:if test="${!user.self and !user.attred}">
                            <div class="product-detail-guanzhu attBtn">+ 关注</div>
                        </c:if>
                    </div>
                    <div style="margin-bottom: -8px;">
                        <c:if test="${user.isPayBond}">
                            <img class="ppxq-smallicon" src="${pageContext.request.contextPath}/wsale/images/baozhang-icon.png" />
                        </c:if>
                        <c:if test="${user.isAuth}">
                            <img class="ppxq-smallicon" src="${pageContext.request.contextPath}/wsale/images/renzheng2-icon.png" />
                        </c:if>
                        <img class="ppxq-smallicon" src="${pageContext.request.contextPath}/wsale/images/v2.png" />
                    </div>
                    <div class="ppxq-desc">
                        <input type="hidden" value="${product.contentLine}" id="content">
                        ${fn:replace(product.content, vEnter, '<br>')}
                    </div>
                    <div class="showMore hide">全文</div>
                    <div class="images">
                        <c:forEach items="${product.files}" var="file" varStatus="vs">
                            <%--<img class="lazy ppxq-imglist" data-original="${file.fileHandleUrl}" />--%>
                            <div class="product-detail-content-img lazy <c:if test="${product.files.size() == 1}">one-img</c:if>" data-original="${file.fileHandleUrl}"></div>
                        </c:forEach>
                    </div>
                    <div style="margin-top:10px;">
                        <div class="jingxuan-info" style="font-size:12px;">
                            <span><img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:12px;" /> ${product.readCount}</span>
                            <span class="likeCount">
                                <c:choose>
                                    <c:when test="${product.liked}">
                                        <img class="liked" src="${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png" style="width:14px;" />
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png" style="width:14px;" />
                                    </c:otherwise>
                                </c:choose>
                                <count>${product.likeCount}</count>
                            </span>
                            <span class="share-icon"><img src="${pageContext.request.contextPath}/wsale/images/fenxiang-icon.png" style="width:15px;height:15px;" />分享</span>
                        </div>
                        <c:if test="${product.isFreeShipping}">
                            <span class="paipin-baoyou">包邮</span>
                        </c:if>
                        <c:choose>
                            <c:when test="${product.approvalDays == 'AD03'}">
                                <span class="paipin-baoyou">3天包退</span>
                            </c:when>
                            <c:when test="${product.approvalDays == 'AD07'}">
                                <span class="paipin-baoyou">7天包退</span>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${!user.self}">
                                <span style="font-size:12px; letter-spacing: 0px;"><fmt:formatDate value="${product.startingTime}" pattern="MM月dd日"/></span>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${product.status == 'PT03' || product.status == 'PT05'}">
                                        <span style="font-size:12px;" class="p-off">下架</span>
                                    </c:when>
                                    <c:when test="${product.status == 'PT04' || product.status == 'PT06'}">
                                        <c:choose>
                                            <c:when test="${product.isClose}">
                                                <span style="font-size:12px;" class="p-close p-show">显示</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="font-size:12px;" class="p-close">隐藏</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="font-size:12px;"><fmt:formatDate value="${product.startingTime}" pattern="MM月dd日"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="likeList">
                        <c:choose>
                            <c:when test="${likes.total > 0}">
                                <div style="margin:5px 0px; background-color:#f0f0f0;padding:4px;">
                                    <c:forEach items="${likes.rows}" var="like" varStatus="vs">
                                        <img class="lazy touxiang-list" data-original="${like.user.headImage}" id="${like.user.id}" onclick="href('api/userController/homePage?userId=${like.user.id}');"/>
                                    </c:forEach>
                                    <c:if test="${likes.total > likes.rows.size()}">
                                        <img class="moreLike down" style="width: 29px; height: 29px; border: 1px solid #9A9DA5;" src="${pageContext.request.contextPath}/wsale/images/down-icon.png" />
                                    </c:if>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div style="margin:5px 0px;"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="bidStatus">
                        <c:choose>
                            <c:when test="${product.deadlineLen > 0}">
                                <div>
                                    <!--<img src="${pageContext.request.contextPath}/wsale/images/zhengzaipaimai-icon.png" class="remai-icon" />-->
                                    <div style="font-size:12px; vertical-align: middle; display:inline-block; width:75%;">
                                        <div class="deadline"><font style="color: #E3F3FE;">拍卖倒计时：</font><span class="cbp-vm-timenumber">0</span>时<span class="cbp-vm-timenumber">0</span>分<span class="cbp-vm-timenumber">0</span>秒</div>
                                        <!--<div>结束时间:<fmt:formatDate value="${product.realDeadline}" pattern="yyyy年MM月dd日HH:mm"/></div>-->
                                    </div>
                                </div>
                                <!--<div style="margin-top:5px;">
                                    <span class="jiage-operate" id="sub_btn">—</span>
                                    <span class="jiage-value">${(product.currentPrice == 0 ? (product.startingPrice == 0 ? rangePrice : product.startingPrice).longValue() : (product.currentPrice + rangePrice).longValue())}</span>
                                    <span style="padding:5px 7px;" class="jiage-operate" id="add_btn">+</span>
                                    <span style="padding:5px 10px;" class="jiage-operate" id="auction_btn">出价</span>
                                    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_auto_bid')}">
                                        <span class="jiage-operate" id="auto_auction_btn">自动出价</span>
                                    </c:if>
                                </div>-->

                                <div class="btn-con">
                                    <div class="btn-con-lf">
                                        <c:if test="${user.self}">
                                            <span class="share-icon jiage-operate" style="width: 100%;">分享给朋友们</span>
                                        </c:if>
                                        <c:if test="${!user.self}">
                                            <span class="jiage-operate lf" id="sub_btn">—</span>
                                            <input class="jiage-value onlyNum" maxlength="10" type="tel" value="${(product.currentPrice == 0 ? (product.startingPrice == 0 ? rangePrice : product.startingPrice).longValue() : (product.currentPrice + rangePrice).longValue())}"/>
                                            <span class="jiage-operate rg" id="add_btn">+</span>
                                        </c:if>
                                    </div>
                                    <div class="btn-con-rg">
                                        <c:if test="${user.self}">
                                            <span class="jiage-operate makeQr">生成二维码</span>
                                        </c:if>
                                        <c:if test="${!user.self}">
                                            <span class="jiage-operate" id="auction_btn">出价</span>
                                        </c:if>
                                    </div>

                                    <div class="updateBid">
                                        <span>更新</span><i class="newbidTM"></i>
                                    </div>
                                </div>

                                <div class="notify">
                                    <span onclick="javascript:location.href='http://mp.weixin.qq.com/s/gujJIeeMDDXvg8Y-crvhOA';">竞拍须知</span>
                                    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_auto_bid') and !user.self}">
                                        <a id="auto_auction_btn">设置自动出价</a>
                                    </c:if>
                                </div>

                                <%--<div class="updateBid">--%>
                                    <%--<div>更新<i class="newbidTM"></i></div>--%>
                                <%--</div>
                                <div>
                                    <a href="http://mp.weixin.qq.com/s?__biz=MzI4OTQzMDkwNQ==&mid=100000004&idx=1&sn=e74c1c2c5643186912dcedb562291519&scene=18#wechat_redirect  " class="jingpai-rule">
                                        <div style="float:right;">
                                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" style="height:15px; vertical-align:middle;" />
                                        </div>
                                        <div class="normal-text" style="font-size: 12px;">竞拍须知</div>
                                    </a>
                                </div>--%>
                            </c:when>
                            <c:otherwise>
                                <div class="paipin-done">
                                    <fmt:formatDate value="${product.realDeadline}" pattern="yyyy年MM月dd日 HH:mm"/>拍卖已结束
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                   <!-- <div>
                        <div class="price-desc">
                            <span class="icon-label">起</span>￥${product.startingPrice.longValue()}元
                            <span class="icon-label">加</span>￥<rangePrice class="rangePrice">${rangePrice.longValue()}</rangePrice>元
                            <span class="icon-label">保</span>￥${product.margin.longValue()}元
                        </div>
                        <div class="price-desc">
                            <span class="icon-label">参考价</span>￥${product.referencePrice.longValue()}元&nbsp;&nbsp;
                            <span class="icon-label">延</span> 5分钟/次
                        </div>
                    </div>-->

                    <div class="price-desc">
                        <div class="price-desc-item">
                            <span class="icon-label">起</span>
                            <b>￥${product.startingPrice.longValue()}</b>
                        </div>
                        <div class="price-desc-item">
                            <span class="icon-label">加</span>
                            <b>￥<rangePrice class="rangePrice">${rangePrice.longValue()}</rangePrice></b>
                        </div>
                        <div class="price-desc-item">
                            <span class="icon-label">保</span>
                            <b>￥${product.margin.longValue()}</b>
                        </div>
                        <div class="price-desc-item">
                            <span class="icon-label">参</span>
                            <b>￥${product.referencePrice.longValue()}</b>
                        </div>
                        <div class="price-desc-item">
                            <span class="icon-label">延</span>
                            <b>5分钟</b>
                        </div>
                        <div class="price-desc-item">
                            <span class="icon-label">一</span>
                            <b>￥${product.fixedPrice.longValue()}</b>
                        </div>
                    </div>


                    <!--<div style="background-color:#f0f0f0;">
                        <ul class="jingjia-order" id="auctions">
                        </ul>
                        <div class="check-more">
                            <a style="font-size:14px;">查看更多 ></a>
                        </div>
                    </div>-->

                    <div class="order-con">
                        <ul class="order-list" id="auctions">
                        </ul>
                        <div class="check-more">
                            <a style="font-size:14px;">查看更多 ></a>
                        </div>
                    </div>
                </div>
                <c:if test="${otherProducts.size() > 0}">
                    <div class="other-paipin">
                        <div>
                            <div class="other-line"></div>
                            <div class="other-title"><span style=" padding:5px; background-color:#fff; ">其他拍品</span></div>
                        </div>
                        <div id="cbp-vm" class="cbp-vm-switcher cbp-vm-view-grid" style="padding:0;">
                            <ul>
                                <c:forEach items="${otherProducts}" var="otherProduct" varStatus="vs">
                                    <li>
                                        <a href="javascript:replace('api/apiProductController/productDetail?id=${otherProduct.id}');" class="cbp-vm-image">
                                            <%--<img class="lazy" data-original="${otherProduct.icon}">--%>
                                            <div class="product-detail-list-img lazy" data-original="${otherProduct.icon}"></div>
                                        </a>
                                        <div class="cbp-vm-title">
                                            <span class="wupin-title info-xinxi" style="height: 25px;">${otherProduct.content}</span>
                                            <div>
                                                <div class="cbp-vm-right">
                                                    <span style="font-size:12px;"><img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:12px;" /> ${otherProduct.readCount}</span>
                                                </div>
                                                <div class="cbp-vm-price" style="margin-top: 0;">￥${otherProduct.currentPrice}</div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>
            </div>

        </div><!-- /content -->

        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->
        <jsp:include page="../template/product_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var currPage = 1;
        var rows = 5;
        var currentPrice = ${product.currentPrice} || 0;
        var startingPrice = ${product.startingPrice} || 0;
        var fixedPrice = ${product.fixedPrice} || 0;
        var shareUrl = removeQueDefault(location.href);
        var st = 0,time = 59, timeInterval;

        $(function(){
            if($(".ppxq-desc").height() == $(".ppxq-desc").css('max-height').replace('px', '')) {
                $(".showMore").removeClass("hide");
            }

            $(".showMore").click(function(e){
                e.stopPropagation();
                e.preventDefault();
                var self = $(this);
                setTimeout(function() {
                    $('.ppxq-desc').addClass("fullDesc");
                    self.remove();
                }, 200);
            });

            $(".lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });

            if(${product.deadlineLen > 0})
                addTimer($('.deadline'), ${product.deadlineLen}, '${product.id}');

            drawAuction(true);
            $(".check-more").bind('click', function(){
                drawAuction();
            });

            var items = [];
            $(".images div").each(function(){
                items.push($(this).attr("data-original"));
            });

            $(".images div").click(function(){
                if('${subscribe}' == 0) {
                    $('.mask-layer, .subscribe').show();
                    addSubscribeLog();
                    return;
                }
                JWEIXIN.previewImage(items, $(this).index());
            });
            $('.likeCount').click(function(){
                var url, _this = this, count = parseInt($(this).find('count').text() || 0);
                if($(this).find('img').hasClass('liked')) {
                    url = 'api/apiProductController/cancelLike';
                    count -= 1;
                } else {
                    url = 'api/apiProductController/addLike';
                    count += 1;
                }
                ajaxPost(url, {productId:'${product.id}'}, function(data){
                    if(data.success) {
                        $(_this).find('count').text(count);
                        if($(_this).find('img').hasClass('liked')) {
                            $(_this).find("img").removeClass('liked').attr('src', base + 'wsale/images/guanzhu-icon.png');
                            $(".likeList img[id='${sessionInfo.id}']").remove();
                            if($(".likeList img").length == 0) {
                                $('.likeList').html('<div style="margin:5px 0px;"></div>');
                            }
                        } else {
                            $(_this).find("img").addClass('liked').attr('src', base + 'wsale/images/yiguanzhu-icon.png');
                            var $div = $('.likeList div');
                            if($(".likeList img").length == 0) {
                                $div.css({'background-color':'#f0f0f0', 'padding': '4px'});
                            }
                            $div.prepend('<img class="touxiang-list" src="${sessionInfo.headImage}" id="${sessionInfo.id}" onclick="href(\'api/userController/homePage?userId=${sessionInfo.id}\');"/>\n');
                        }
                    }
                });
            });

//            $(".more-content li").click(moreFun);
            $('.report').click(function(){
                $('#reportPopup').wePopup();
            });
            $('.attBtn').click(attrFun);
            $(".moreLike").click(moreLikeFun);
            $("#auction_btn").click(auction);
            $(".auto-bid").click(autoAuction);
            $("#auto_auction_btn").click(function(){
                if('${subscribe}' == 0) {
                    $('.mask-layer, .subscribe').show();
                    addSubscribeLog();
                    return;
                }

                if(${user.self}) {
                    $.alert("请分享给朋友，自己不能出价！", "系统提示");
                    return;
                }

                if(!$("#bindMobile").val()) {
                    $('#bindMobilePopup').wePopup();
                    return;
                }

                updateBid();

                if(${marginFlag}) {
                    $.modal({
                        title: "系统提示",
                        text: "拍品已设置出价保护，您本次出价需支付保证金：${product.margin}元！如违约，保证金扣除赔偿给卖家。",
                        buttons: [
                            { text: "取消", className: "default"},
                            { text: "去支付", onClick: function(){
                                ajaxPost('api/apiProductController/addMargin', {productId: '${product.id}', margin: ${product.margin}}, function(data){
                                    if(data.success) {
                                        href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO08&totalFee=${product.margin}');
                                    }
                                });
                            } }
                        ]
                    });

                    return;
                }
                $('#maxPricePopup').wePopup();
            });
            $("#sub_btn").click(function(){
                var bid = parseFloat($('.jiage-value').val()),
                    rangePrice = parseFloat($('.rangePrice').html());
                if(isNaN(bid)) {
                    $('.jiage-value').val(currentPrice);
                    return;
                }
                var minPrice = currentPrice == 0 ? (startingPrice == 0 ? rangePrice : startingPrice) : (currentPrice + rangePrice);
                if(bid <= minPrice) {
                    if(fixedPrice == 0 || bid < fixedPrice) {
                        $.alert("出价不能再低了！", "系统提示");
                        return;
                    } else {
                        $('.jiage-value').val(fixedPrice);
                        return;
                    }
                }

                $('.jiage-value').val(bid - rangePrice);
            });
            $("#add_btn").click(function(){
                var bid = parseFloat($('.jiage-value').val()),
                    rangePrice = parseFloat($('.rangePrice').html());
                if(fixedPrice != 0 && bid + rangePrice > fixedPrice) {
                    $('.jiage-value').val(fixedPrice);
                    return;
                }
                $('.jiage-value').val(bid + rangePrice);
            });

            $('.share-list li').click(function(){
                var _this = this, num = $(this).index();
                if(num == 0) {
                    openQrcode();
                } else if(num == 1) {
                    sendLink(function(){
                        $(_this).find('.msg').html('发送成功');
                    });
                } else {
                    $(_this).find('.msg').html('可以分享了');
                }
            });

            $('.makeQr').bind('click', openQrcode);

            // 下架
            $('.p-off').click(xjFun);
            // 显示/影藏
            $('.p-close').click(closeFun);

            $('.updateBid span').click(updateBid);

            $(".subscribe").off('touchstart').on("touchstart", function (e) {
                e.stopPropagation();
                st = Date.now();
            });
            $(".subscribe").off('touchend').on("touchend", function (e) {
                e.stopPropagation();
                if(st > 0 && (Date.now() - st) < 300) {
                    $('.mask-layer').hide();
                    $(this).hide();
                }
            });

            $('#vcode-btn').bind('click', sendVCode);
            $('#bindMobileBtn').bind('click', bindMobile);
            $('.reportBtn').bind('click', report);
        });

        function bindMobile() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码');
                return;
            }
            var vcode = $('#vcode').val();
            if(Util.checkEmpty(vcode)) {
                $.toptip('请输入验证码');
                return;
            }
            ajaxPost('api/userController/edit', {mobile : mobile, vcode : vcode}, function(data){
                if(data.success) {
                    $.toptip('绑定成功', 'success');
                    $("#bindMobile").val(mobile);
                    $.closePopup();
                } else {
                    $.toptip(data.msg);
                }
            });
        }

        function sendVCode() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码', 'error');
                $('#mobile').focus();
                return;
            }
            $('#vcode-btn').unbind('click').html('重发（<span id=\"time\">'+time+'</span>）');
            time--;
            timeInterval = setInterval(function(){
                $("#time").html(time);
                if(time == 0) {
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                } else {
                    time -- ;
                }
            }, 1000);

            ajaxPost('api/userController/sendVCode', {mobile:mobile, checkMobile:true}, function(data){
                if(data.success) {
                    $.toptip('验证码已发送至手机', 'success');
                } else {
                    $.toptip(data.msg, 'error');
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                }
            });

        }

        // 手动更新
        function updateBid() {
            ajaxPostSync('api/apiProductController/updateBid', {id:'${product.id}', currentPrice:currentPrice}, function(data){
                var result = data.obj;
                if(result.product.deadlineLen > 0) {
                    addTimer($('.deadline'), result.product.deadlineLen, '${product.id}');
                } else {
                    $('.bidStatus').html('<div class="paipin-done">'+new Date().format("yyyy年MM月dd日 HH:mm")+'拍卖已结束</div>');
                    clearInterval(timerArr['interval_${product.id}']);
                }
                $('.updateBid .newbidTM').html(new Date().format('HH:mm:ss'));
                if(data.success) {
                    currentPrice = result.product.currentPrice;
                    $('.rangePrice').html(result.rangePrice);
                    $('.jiage-value').val(currentPrice == 0 ? (result.product.startingPrice == 0 ? result.rangePrice : result.product.startingPrice) : (currentPrice + result.rangePrice));

                    currPage = 1;
                    drawAuction(true);
                }

            });
        }

        // 下架
        function xjFun() {
            $.confirm("您确认下架此拍品吗？", "系统提示", function () {
                ajaxPost('api/apiProductController/productOff', {id:'${product.id}', status:'${product.status}'}, function(data){
                    if(data.success) {
                        replace('api/apiProductController/productManage?isDraft=true');
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                });
            });
        }

        // 影藏/显示
        function closeFun() {
            var _this = this, isClose = false;
            // 当前拍品为显示，修改为影藏
            if(!$(_this).hasClass('p-show')) {
                isClose = true;
            }
            var msg = isClose ? '确认隐藏此拍品？' : '确认显示此拍品？';
            $.confirm(msg, "系统提示", function() {
                ajaxPost('api/apiProductController/edit', {id:'${product.id}', isClose:isClose}, function(data){
                    if(data.success) {
                        if(isClose)
                            $(_this).addClass('p-show').html('显示');
                        else
                            $(_this).removeClass('p-show').html('隐藏');
                    }
                });
            });

        }

        function openQrcode() {
            ajaxPost('api/userController/getQR', {content:removeQueDefault(location.href), objectId:'${product.id}', objectType:'PRODUCT'}, function(data) {
                if(data.success) {
                    try{
                        JWEIXIN.previewImage([data.obj + "?t=" + new Date().getTime()]);
                    } catch (e){}

                }
            }, function(){
                $.loading.load({type:3, msg:'正在打开...'});
            });
        }

        function sendLink(callback) {
            ajaxPost('api/apiCommon/sendLink', {openid : '${sessionInfo.name}', link:removeQueDefault(location.href)}, function(data) {
                if(data.success && callback) {
                    callback();
                }
            });
        }

        function addSubscribeLog(){
            ajaxPost('api/apiCommon/addSubscribeLog', {objectType : 'PRODUCT', objectId : '${product.id}'}, function(data) {
                if(data.success) {
                }
            });
        }

        // 出价
        function auction() {
            if('${subscribe}' == 0) {
                $('.mask-layer, .subscribe').show();
                addSubscribeLog();
                return;
            }

            if(${user.self}) {
                $.alert("请分享给朋友，自己不能出价！", "系统提示");
                return;
            }

            if(!$("#bindMobile").val()) {
                $('#bindMobilePopup').wePopup();
                return;
            }

            updateBid();

            var bid = parseFloat($('.jiage-value').val()),
                rangePrice = parseFloat($('.rangePrice').html());
            if(isNaN(bid)) {
                $('.jiage-value').val(currentPrice);
                return;
            }
            var minPrice = currentPrice == 0 ? (startingPrice == 0 ? rangePrice : startingPrice) : (currentPrice + rangePrice);
            if(bid < minPrice) {
                if(fixedPrice == 0 || bid < fixedPrice) {
                    $.alert("加价幅度不能低于"+rangePrice+"元！", "系统提示", function(){
                        $('.jiage-value').val(minPrice);
                    });
                    return;
                }
            }
            if(fixedPrice != 0 && bid > fixedPrice) {
                $.alert("出价不能超过一口价！", "系统提示", function(){
                    $('.jiage-value').val(fixedPrice);
                });
                return;
            }

            if(${marginFlag}) {
                $.modal({
                    title: "系统提示",
                    text: "拍品已设置出价保护，您本次出价需支付保证金：${product.margin}元！如违约，保证金扣除赔偿给卖家。",
                    buttons: [
                        { text: "取消", className: "default"},
                        { text: "去支付", onClick: function(){
                            ajaxPost('api/apiProductController/addMargin', {productId: '${product.id}', margin: ${product.margin}}, function(data){
                                if(data.success) {
                                    href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO08&totalFee=${product.margin}');
                                }
                            });
                        } }
                    ]
                });

                return;
            }

            var $first = $("#auctions li:eq(0)");
            if('${sessionInfo.id}' == $first.find('div.avatr').attr('userId')) {
                $.alert("您的出价已领先，请勿连续出价！", "系统提示");
                return;
            }
            $.confirm("您确认出价此拍品吗？", "系统提示", function () {
                var bid = parseFloat($('.jiage-value').val());
                ajaxPost('api/apiProductController/bid', {productId:'${product.id}', bid : bid}, function(data){
                    var result = data.obj;
                    if(data.success) {
                        $.toast("出价成功", "text");
                        if($first.length != 0) {
                            $first.find(".order-sign").hide();
                            $first.find(".order-flag").attr('src', base + 'wsale/images/chuju-icon.png');
                        } else {
                            $("#auctions").parent().show();
                        }
                        var dom = buildAuction(result.auction);
                        $("#auctions").prepend(dom);
                        dom.find(".order-sign").show();

                        if(result.isDeal) {
                            $('.bidStatus').html('<div class="paipin-done">'+new Date().format("yyyy年MM月dd日 HH:mm")+'拍卖已结束</div>');
                            dom.find(".order-flag").attr('src', base + 'wsale/images/deal-icon.png');
                        } else {
                            currentPrice = bid;
                            $('.rangePrice').html(result.rangePrice);
                            $('.jiage-value').val(bid + result.rangePrice);
                            dom.find(".order-flag").attr('src', base + 'wsale/images/lingxian-icon.png');
                            if(result.deadlineLen > 0) {
                                addTimer($('.deadline'), result.deadlineLen, '${product.id}');
                            }
                        }
                    } else {
                        $.alert(data.msg, "系统提示");
                    }
                });
            });
        }

        var reg = /^[0-9]*(\.[0-9]{1,2})?$/;
        // 自动出价
        function autoAuction() {
            $.closePopup();
            var maxPrice = $('#maxPrice').val();
            $('#maxPrice').val('');
            if(Util.checkEmpty(maxPrice) || !reg.test(maxPrice)) {
                $.toast("<font size='2'>最高价格式不正确</font>", "text");
                return;
            }
            var bid = parseFloat($('.jiage-value').val());
            if(bid >= maxPrice) {
                $.alert("最高价不能低于当前出价！", "系统提示");
                return;
            }
            if(fixedPrice > 0 && maxPrice > fixedPrice) {
                $.alert("最高价不能超出一口价！", "系统提示");
                return;
            }

            ajaxPost('api/apiProductController/autoBid', {productId:'${product.id}', maxPrice : maxPrice}, function(data){
                if(data.success) {
                    $.alert("自动出价成功，请手动更新！", "系统提示");
                    //$('.maxPriceMsg').html('当前自动出价最高价为'+maxPrice+'元');
                } else {
                    $.alert(data.msg, "系统提示");
                }
            });

        }

        function moreLikeFun() {
            if($('.moreLike').hasClass('down')) {
                if($(".likeList img.more").length > 0) {
                    $(".likeList img.more").show();
                } else {
                    ajaxPost("api/apiProductController/likes", {productId:'${product.id}'}, function(data){
                        if(data.success) {
                            $(".likeList img:not(.moreLike)").remove();
                            var likes = data.obj;
                            for(var i=0; i<likes.length; i++) {
                                var like = likes[i], clazz = '';
                                if(i >= 12) clazz = 'more';
                                $('.moreLike').before('<img class="touxiang-list '+clazz+'" src="'+like.user.headImage+'" id="'+like.user.id+'" onclick="href(\'api/userController/homePage?userId='+like.user.id+'\');" />\n');
                            }
                        }
                    });
                }

                $('.moreLike').removeClass('down').attr('src', base + 'wsale/images/up-icon.png');
            } else {
                $(".likeList img.more").hide();
                $('.moreLike').addClass('down').attr('src', base + 'wsale/images/down-icon.png');
            }

        }

        function attrFun() {
            var _this = this;
            ajaxPost('api/userController/addShieldorfans', {objectType:'FS', userId:'${user.id}'}, function(data){
                if(data.success) {
                    $(_this).remove();
                }
            });
        }

        function report() {
            var reportReason = $('#reportReason').val();
            if(Util.checkEmpty(reportReason)) {
                $('#reportReason').focus();
                return;
            }
            ajaxPost('api/reportController/report', {objectType:'PRODUCT', objectId:'${product.id}', reportReason:reportReason}, function(data){
                $.closePopup();
                if(data.success) {
                    $.toast("举报成功");
                } else {
                    $.toast("<font size='1'>"+data.msg+"</font>", "text");
                }
            });
        }

        function drawAuction(load) {
            if(load) $('#auctions').empty();
            ajaxPost('api/apiProductController/auctions', {productId:'${product.id}', page:currPage, rows:rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var auction = result.rows[i];
                            buildAuction(auction);
                        }

                        $("#auctions li:eq(0)").find(".order-sign").show();
                        if('${product.status}' != 'PT03' || ${product.deadlineLen == 0}) {
                            $("#auctions li:eq(0)").find(".order-flag").attr('src', base + 'wsale/images/deal-icon.png');
                        } else {
                            $("#auctions li:eq(0)").find(".order-flag").attr('src', base + 'wsale/images/lingxian-icon.png');
                        }


                        currPage ++;
                    }

                    if(result.rows.length < rows) $('.check-more').hide();
                    else $('.check-more').show();

                    if(result.total == 0) $("#auctions").parent().hide();
                    else $("#auctions").parent().show();
                }
            });
        }

        function buildAuction(auction) {
            var flag = ${product.status == 'PT03'}, isClick = true;
            if('<%=amSwitch %>' == 1 && auction.user.id != '${sessionInfo.id}' && ${!user.self}) {
                auction.user.nickname = '匿名';
                auction.user.headImage = base + 'wsale/images/touxiang-img.png';
                isClick = false;
            }
            var viewData = Util.cloneJson(auction);
            viewData.addtime = auction.addtime.substring(2);
            viewData.bid = '￥' + auction.bid;
            var dom = Util.cloneDom("auction_template", auction, viewData);
            dom.find('div.avatr').attr('userId', auction.user.id).css('background-image', 'url('+auction.user.headImage+')');
            if(auction.isAuto) dom.find('.order-right-middle').show();
            $("#auctions").append(dom);
            if(isClick)
                dom.find("div.avatr").click(auction.user.id, function(event){
                    href('api/userController/homePage?userId=' + event.data);
                });
            return dom;
        }

        var timerArr = {};
        var addTimer = (function () {

            return function (dom, time, key) {
                timerArr['interval_time_' + key] = time + 1;
                if (!timerArr['interval_' + key]) {
                    timerArr['interval_' + key] = setInterval(function(){
                        go(dom, key);
                    }, 1000);

                    go(dom, key);
                }
            };

            function go(dom, key) {
                var time = timerArr['interval_time_' + key];
                var timerStr = getTimerString(time ? timerArr['interval_time_' + key] -= 1 : 0);
                if(timerStr == -1) {
                    updateBid();
                }
                else dom.html(timerStr);
            }

            function getTimerString(time) {
                var d = Math.floor(time / 86400),
                        h = Math.floor((time % 86400) / 3600),
                        m = Math.floor(((time % 86400) % 3600) / 60),
                        s = Math.floor(((time % 86400) % 3600) % 60);
                if (time > 0) {
                    var dh = d == 0 ? '' : '<span class="cbp-vm-timenumber">'+d+'</span>天';
                    return '<font style="color: #a8a8a8;">拍卖倒计时：</font>'+dh+'<span class="cbp-vm-timenumber">'+h+'</span>时<span class="cbp-vm-timenumber">'+m+'</span>分<span class="cbp-vm-timenumber">'+s+'</span>秒'
                }
                else return -1;
            }
        }) ();

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"『${sessionInfo.nickname}』发现了一件不错的集东集西拍品，快来围观！",
                desc:$('#content').val(),
                link:shareUrl,
                imgUrl:$(".images div:eq(0)").attr("data-original")
            };
            JWEIXIN.onMenuShareAppMessage(shareData);
            JWEIXIN.onMenuShareTimeline(shareData);
            JWEIXIN.onMenuShareQQ(shareData);
            JWEIXIN.onMenuShareWeibo(shareData);
            JWEIXIN.onMenuShareQZone(shareData);
        });
    </script>
</body>

</html>
