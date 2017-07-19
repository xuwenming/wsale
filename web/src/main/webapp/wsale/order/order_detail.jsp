<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>订单详情</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; text-align:left; ">
                <div class="process">
                    <c:choose>
                        <c:when test="${order.orderStatus == 'OS15'}">
                            <a class="shouhuo-content">
                                <div class="pingjia-img">
                                    <img src="${pageContext.request.contextPath}/wsale/images/fail-icon.png" />
                                </div>
                                <div class="pingjia-text">
                                    <span class="pingjia-status">交易失败</span>
                                    <div class="pingjia-time">原因：${order.orderCloseReasonZh}</div>
                                    <div class="pingjia-time">结束时间：<fmt:formatDate value="${order.orderStatusTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                </div>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${order.orderStatus == 'OS10' && order.isCommented}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/pingjia-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">已评价</span>
                                        <div class="pingjia-time">评价时间：<fmt:formatDate value="${order.comment.addtime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus == 'OS10' && empty order.faceStatus}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jiaoyiok-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">已确认收货，交易完成</span>
                                        <div class="pingjia-time">收货时间：<fmt:formatDate value="${order.receiveTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus == 'OS10' && !empty order.faceStatus}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jiaoyiok-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">交易完成</span>
                                        <div class="pingjia-time">交易类型：当面交易</div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus != 'OS10' && !empty order.backStatus && order.backStatus == 'RS01'}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jiaoyiok-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">退货申请</span>
                                        <div class="pingjia-time">申请时间：<fmt:formatDate value="${order.returnApplyTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus != 'OS10' && !empty order.backStatus && order.backStatus == 'RS02'}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jiaoyiok-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">拒绝退货</span>
                                        <div class="pingjia-time">拒绝时间：<fmt:formatDate value="${order.returnConfirmTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus != 'OS10' && !empty order.backStatus && order.backStatus == 'RS03'}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jiaoyiok-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">同意退货</span>
                                        <div class="pingjia-time">同意时间：<fmt:formatDate value="${order.returnConfirmTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus != 'OS10' && !empty order.backStatus && order.backStatus == 'RS04'}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jiaoyiok-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">退货发货</span>
                                        <div class="pingjia-time">发货时间：<fmt:formatDate value="${order.returnDeliverTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus == 'OS05' || (order.orderStatus == 'OS10' && empty order.faceStatus)}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/fahuo-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">已发货</span>
                                        <div class="pingjia-time">发货时间：<fmt:formatDate value="${order.deliverTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${order.orderStatus == 'OS02' || order.orderStatus == 'OS05' || (order.orderStatus == 'OS10' && empty order.faceStatus)}">
                                <a class="shouhuo-content">
                                    <div class="pingjia-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/fukuan-icon.png" />
                                    </div>
                                    <div class="pingjia-text">
                                        <span class="pingjia-status">已付款</span>
                                        <div class="pingjia-time">付款时间：<fmt:formatDate value="${order.paytime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                </a>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${order.isIntermediary}">
                            <a class="shouhuo-content">
                                <div class="pingjia-img">
                                    <img src="${pageContext.request.contextPath}/wsale/images/jingpaichenggong-icon.png" />
                                </div>
                                <div class="pingjia-text">
                                    <span class="pingjia-status">创建交易</span>
                                    <div class="pingjia-time">创建时间：<fmt:formatDate value="${order.product.startingTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                </div>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a class="shouhuo-content">
                                <div class="pingjia-img">
                                    <img src="${pageContext.request.contextPath}/wsale/images/jingpaichenggong-icon.png" />
                                </div>
                                <div class="pingjia-text">
                                    <span class="pingjia-status">竞拍成功</span>
                                    <div class="pingjia-time">成功时间：<fmt:formatDate value="${order.product.hammerTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                </div>
                            </a>
                            <a class="shouhuo-content">
                                <div class="pingjia-img">
                                    <img src="${pageContext.request.contextPath}/wsale/images/jingpai-icon.png" />
                                </div>
                                <div class="pingjia-text">
                                    <span class="pingjia-status">开始竞拍</span>
                                    <div class="pingjia-time">开始时间：<fmt:formatDate value="${order.product.startingTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                </div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="dingdan-product">
                    <div class="faxian-link">
                        <div class="dingdan-right">
                            <a style="display: inline;padding:10px 0 10px 5px" <c:choose><c:when test="${order.isBuyer}">href="tel:${order.seller.mobile}"</c:when><c:otherwise>href="tel:${order.buyer.mobile}"</c:otherwise></c:choose>><img class="phone-icon" src="${pageContext.request.contextPath}/wsale/images/phone-icon.png" /></a>
                            <span class="status-text">
                                <c:choose>
                                    <c:when test="${order.isXiaoer && order.xiaoer.status == 'XS01'}">
                                        等待小二处理
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${order.backStatus == 'RS04' && order.orderStatus != 'OS10' && order.orderStatus != 'OS15'}">
                                                买家退货已发
                                            </c:when>
                                            <c:otherwise>${order.orderStatusZh}</c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="normal-text" <c:choose><c:when test="${order.isBuyer}">onclick="href('api/userController/homePage?userId=${order.seller.id}')"</c:when><c:otherwise>onclick="href('api/userController/homePage?userId=${order.buyer.id}')"</c:otherwise></c:choose>>
                            <img class="dianpu-icon" src="${pageContext.request.contextPath}/wsale/images/dianpu-icon.png" />
                            <c:choose><c:when test="${order.isBuyer}">${order.seller.nickname}</c:when><c:otherwise>${order.buyer.nickname}</c:otherwise></c:choose>
                            <c:if test="${order.isIntermediary}"><span class="intermediary" style="padding: 0 5px;font-size: 12px;color:#ff0000;  border: 1px solid #ff0000;border-radius: 10px;">中介</span></c:if>
                            <img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                    </div>
                    <div class="dingdan-content"
                         <c:choose>
                             <c:when test="${order.isIntermediary}">
                                 onclick="href('api/bbsController/bbsDetail?id=${order.product.id}')"
                             </c:when>
                             <c:otherwise>
                                 onclick="href('api/apiProductController/productDetail?id=${order.product.id}')"
                             </c:otherwise>
                         </c:choose>>
                        <div class="dingdan-img">
                            <img src="${order.product.icon}" />
                        </div>
                        <div class="dingdan-content-flex">
                            <div class="dingdan-title" style="-webkit-line-clamp:1">
                                ${order.product.content}
                            </div>
                            <div class="dingdan-info">
                                <c:choose>
                                    <c:when test="${order.orderStatus == 'OS01'}">
                                        <div>成交金额：￥<fmt:formatNumber type="number" value="${order.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                        <div class="payEnd"></div>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'OS02'}">
                                        <div>交易金额：￥<fmt:formatNumber type="number" value="${order.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                        <div>付款时间：<fmt:formatDate value="${order.paytime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                        <div class="deliverEnd"></div>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'OS05'}">
                                        <div>交易金额：￥<fmt:formatNumber type="number" value="${order.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                        <c:choose>
                                            <c:when test="${order.backStatus == 'RS04'}">
                                                <div>退货时间：<fmt:formatDate value="${order.returnDeliverTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div>发货时间：<fmt:formatDate value="${order.deliverTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:if test="${!order.isXiaoer || (order.isXiaoer && order.xiaoer.status == 'XS02')}">
                                            <div class="receiveEnd"></div>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'OS10'}">
                                        <div>交易金额：￥<fmt:formatNumber type="number" value="${order.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                        <c:choose>
                                            <c:when test="${!empty order.faceStatus && order.faceStatus == 'FS02'}">
                                                <div>交易类型：当面交易</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div>收货时间：<fmt:formatDate value="${order.receiveTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'OS15'}">
                                        <div>失败原因：${order.orderCloseReasonZh}</div>
                                        <div>失败时间：<fmt:formatDate value="${order.orderStatusTime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </c:when>
                                </c:choose>

                                <!--<a class="money-more"><img class="money-icon" src="${pageContext.request.contextPath}/wsale/images/qiankuan-icon.png" /> 钱款 <img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" /></a>-->
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="dingdan-detail">订单信息</div>
                    <div class="maijia-dingdan">
                        <c:choose>
                            <c:when test="${order.isBuyer}"><div>卖家电话：<a href="tel:${order.seller.mobile}" style="display: inline;">${order.seller.mobile}</a></div></c:when>
                            <c:otherwise><div>买家电话：<a href="tel:${order.buyer.mobile}" style="display: inline;">${order.buyer.mobile}</a></div></c:otherwise>
                        </c:choose>
                        <div>拍品编号：${order.product.pno}</div>
                        <div>货款交易号：${order.orderNo}</div>
                    </div>
                </div>
                <c:if test="${order.payStatus == 'PS02' and !empty address}">
                    <div class="fahuo-info">
                        <a class="faxian-link">
                            <!--<div class="dingdan-right">
                                <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>-->
                            <div style="font-size:12px;" class="normal-text">发货信息</div>
                        </a>
                        <div class="maijia-dingdan">
                            <div style="float:right;" class="grayright-text">${address.telNumber}</div>
                            <div>收件人：${address.userName}</div>
                            <div>收货地址：${address.provinceName}${address.cityName}${address.countyName}${address.detailInfo}</div>
                        </div>
                        <c:if test="${!empty order.expressName && !empty order.expressNo}">
                            <div class="fahuo-style">
                                发货方式：${order.expressName}（${order.expressNo}）
                            </div>
                        </c:if>
                    </div>
                </c:if>

                <c:if test="${!empty order.returnApplyReason}">
                    <div class="fahuo-info">
                        <a class="faxian-link">
                            <div style="font-size:12px;" class="normal-text">争议信息</div>
                        </a>
                        <div class="maijia-dingdan">
                            <div style="float:right;" class="grayright-text"><fmt:formatDate value="${order.returnApplyTime}" pattern="MM-dd HH:mm"/></div>
                            <div style="width: 60%;">
                                买家-${order.returnApplyReasonZh}
                                <c:if test="${order.returnApplyReason == 'RR99'}">
                                    - ${order.returnApplyReasonOther}
                                </c:if>
                            </div>
                        </div>
                        <c:if test="${!empty order.refuseReturnReason}">
                            <div class="maijia-dingdan">
                                <div style="float:right;" class="grayright-text"><fmt:formatDate value="${order.returnConfirmTime}" pattern="MM-dd HH:mm"/></div>
                                <div style="width: 60%;">
                                    卖家-${order.refuseReturnReasonZh}
                                    <c:if test="${order.refuseReturnReason == 'RF99'}">
                                        - ${order.refuseReturnReasonOther}
                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </c:if>

                <c:if test="${backAddress != null}">
                    <div class="fahuo-info">
                        <a class="faxian-link">
                            <!--<div class="dingdan-right">
                                <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>-->
                            <div style="font-size:12px;" class="normal-text">退货信息</div>
                        </a>
                        <div class="maijia-dingdan">
                            <div style="float:right;" class="grayright-text">${backAddress.telNumber}</div>
                            <div>收件人：${backAddress.userName}</div>
                            <div>收货地址：${backAddress.provinceName}${backAddress.cityName}${backAddress.countyName}${backAddress.detailInfo}</div>
                        </div>
                        <c:if test="${!empty order.returnExpressName && !empty order.returnExpressNo}">
                            <div class="fahuo-style">
                                发货方式：${order.returnExpressName}（${order.returnExpressNo}）
                            </div>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${order.orderStatus == 'OS10' && order.isCommented}">
                    <div class="comment">
                        <div class="dingdan-star">
                            <div id="star"></div>
                        </div>
                        <div class="dingdan-detail">评分评价</div>
                        <div class="maijia-dingdan">
                            <div>${order.comment.content}</div>
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

    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('.process a:first').removeClass('shouhuo-content').addClass('pingjia-content');
            if($('.payEnd').length > 0) {
                var time = '${order.addtime}';
                if(time.length > 19)
                    time = time.substring(0, 19);
                var payDownTime = (new Date(time.replace(/-/g,"/")).getTime() + 72*60*60*1000) - new Date().getTime();
                addTimer($('.payEnd'), payDownTime/1000, '付款截止');
            }
            if($('.deliverEnd').length > 0) {
                var time = '${order.paytime}';
                if(time.length > 19)
                    time = time.substring(0, 19);
                var deliverDownTime = (new Date(time.replace(/-/g,"/")).getTime() + 5*24*60*60*1000) - new Date().getTime();
                addTimer($('.deliverEnd'), deliverDownTime/1000, '发货截止');
            }
            if($('.receiveEnd').length > 0) {
                var time = '${order.deliverTime}', msg = '确认收货剩余';
                if('${order.backStatus}' && '${order.backStatus}' == 'RS04') {
                    time = '${order.returnDeliverTime}';
                    msg = '确认截止';
                }
                if(time.length > 19)
                    time = time.substring(0, 19);
                var receiveDownTime = (new Date(time.replace(/-/g,"/")).getTime() + 10*24*60*60*1000) - new Date().getTime();
                addTimer($('.receiveEnd'), receiveDownTime/1000, msg);
            }
            if($('.comment').length > 0) {
                $.fn.raty.defaults.path = base + 'wsale/images';
                $('#star').raty({
                    score:'${order.comment.grade}' || 0,
                    readOnly:true
                });
            }
        });

        var addTimer = (function () {
            var list = [], interval;

            return function (dom, time, msg) {
                if (!interval)
                    interval = setInterval(go, 1000);
                list.push({ ele: dom, time: time, msg: msg });
                go();
            }

            function go() {
                for (var i = 0; i < list.length; i++) {
                    var dom = list[i].ele, time = list[i].time, msg = list[i].msg;
                    dom.html(getTimerString(time ? list[i].time -= 1 : 0, msg));
                    if (!time)
                        list.splice(i--, 1);
                }
            }

            function getTimerString(time, msg) {
                var h = Math.floor(time / 3600),
                        m = Math.floor(((time % 86400) % 3600) / 60),
                        s = Math.floor(((time % 86400) % 3600) % 60);
                m = m < 10 ? '0' + m : m, s = s < 10 ? '0' + s : s;
                if (time > 0)
                    return msg + '：<span class="nopay-money">'+h+'</span>时<span class="nopay-money">'+m+'</span>分<span class="nopay-money">'+s+'</span>秒';
                else return "交易关闭";
            }
        }) ();

    </script>
</body>

</html>
