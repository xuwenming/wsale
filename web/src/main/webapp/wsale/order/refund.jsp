<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>同意退款</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="同意退款" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0">
                <a class="faxian-link">
                    <div class="normal-text" style="text-align:left;">
                        拍品信息
                    </div>
                </a>
                <div class="dingdan-list ">
                    <div class="dingdan-content">
                        <div class="dingdan-img">
                            <img src="${product.icon}" />
                        </div>
                        <div class="dingdan-content-flex">
                            <div class="dingdan-title">
                                ${product.content}
                            </div>
                            <div class="dingdan-info">
                                <div>成交金额：￥<fmt:formatNumber type="number" value="${product.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                <div>截止时间：<fmt:formatDate value="${order.returnDeliverTime}" pattern="MM-dd HH:mm"/></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border-bottom: 10px solid #f5f5f5;">
                <a style="padding: 10px;">
                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div class="dingdan-title" style="margin-right:20px;">申请退货理由</div>
                        <div class="dingdan-title" style="color:#dc721c;" >${order.returnApplyReasonZh}</div>
                    </div>
                    <c:if test="${order.returnApplyReason == 'RR99'}">
                        <div class="dingdan-title" style="margin-top:10px;color:#aaa">${order.returnApplyReasonOther}</div>
                    </c:if>
                </a>
            </div>

            <div style="border-bottom: 10px solid #f5f5f5;">
                <a style="padding: 10px;border-bottom:solid 1px #F5F5F5">
                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div  class="dingdan-title" style="margin-right:20px;">退货信息</div>
                    </div>
                </a>

                <a style="padding: 10px;border-bottom: 1px solid #f5f5f5;">
                    <div>
                        <div style="display: -webkit-box !important;display: box !important;position:relative;">
                            <div class="dingdan-title" style="margin-right:20px;">收货人：</div>
                            <div class="dingdan-title" >${address.userName} ${address.telNumber}</div>
                        </div>
                        <div class="dingdan-title" style="margin-top:10px;color:#aaa;font-size:12px;">${address.provinceName}${address.cityName}${address.countyName}${address.detailInfo}</div>
                    </div>
                </a>
            </div>
            <div style="border-bottom: 10px solid #f5f5f5;">
                <a style="padding: 10px;border-bottom:solid 1px #F5F5F5">
                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div class="dingdan-title" style="margin-right:20px;">发货方式</div>
                    </div>
                </a>
                <a style="padding: 10px;border-bottom: 1px solid #f5f5f5;">
                    <div>
                        <div style="display: -webkit-box !important;display: box !important;position:relative;">
                            <div class="dingdan-title" style="margin-right:20px;font-size:13px;">${order.returnExpressName}（单号：${order.returnExpressNo}）</div>
                        </div>
                    </div>
                </a>
            </div>

            <div>
                <a type="button" id="refundBtn" style="color:#fff;font-size:14px;display:block;height:35px; line-height:35px; background-color:#dc721c; margin:20px;text-align:center">同意退款</a>
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
            $('#refundBtn').click(function(){
                $.confirm("确认后此次交易将会关闭，是否继续?", "系统提示", function() {
                    ajaxPost('api/apiOrder/refund', {id:'${order.id}'}, function(data){
                        if(data.success) {
                            //$.alert("交易已关闭，钱款已退回买家！", "系统提示！", function(){
                            window.location.replace(document.referrer);
                            //});
                        } else {
                            $.alert(data.msg, "系统提示！");
                        }
                    },function(){
                        $.loading.load({type:2, msg:'退款中...'});
                    }, -1);
                }, function() {});
            });
        });
    </script>
</body>

</html>
