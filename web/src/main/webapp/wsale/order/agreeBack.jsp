<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>同意退货</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="同意退货" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div id="msgPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 210px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden;">
                        <div>
                            同意后，请等待买家退货，<br>
                            请在收到买家退货后再确认退款
                        </div>
                        <a class="bottom-btn guanzhu-ok" style="color: #fff;font-size: 16px;">同意退货</a>
                        <a class="bottom-btn close-popup" style="color: #fff;font-size: 16px;background-color: #aaa;">取消</a>
                    </div>
                </div>
            </div>

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
                        <div>
                            <div class="dingdan-title">
                                ${product.content}
                            </div>
                            <div class="dingdan-info">
                                <div>成交金额：￥<fmt:formatNumber type="number" value="${product.hammerPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                <div>截止时间：<fmt:formatDate value="${order.returnApplyTime}" pattern="MM-dd HH:mm"/></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border-bottom: 10px solid #f5f5f5;">
                <a style="padding: 10px;">
                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div  class="dingdan-title" style="margin-right:20px;">申请退货理由</div>
                        <div  class="dingdan-title" style="color:#dc721c;" >${order.returnApplyReasonZh}</div>
                    </div>
                    <c:if test="${order.returnApplyReason == 'RR99'}">
                        <div class="dingdan-title" style="margin-top:10px;color:#aaa">${order.returnApplyReasonOther}</div>
                    </c:if>
                </a>
            </div>

            <div style="border-bottom: 10px solid #f5f5f5;">
                <a style="padding: 10px;border-bottom:solid 1px #F5F5F5">
                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div class="dingdan-title" style="margin-right:20px;">退货地址</div>
                    </div>
                </a>

                <a style="padding: 10px;border-bottom: 1px solid #f5f5f5;" class="returnAddress">
                    <div class="money-more" style="margin-top: 15px;">
                        <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                    </div>
                    <div style="<c:if test="${!empty address}">display: none;</c:if>" class="addr-none">
                        <div class="dingdan-title" style="margin-top:10px;color:#aaa;font-size:12px;">
                                尚未设置退货地址，去设置
                        </div>
                    </div>
                    <div style="<c:if test="${empty address}">display: none;</c:if>" class="addr">
                        <div style="display: -webkit-box !important;display: box !important;position:relative;">
                            <div class="dingdan-title" style="margin-right:20px;">收货人：</div>
                            <div class="dingdan-title username">
                                ${address.userName} ${address.telNumber}
                            </div>
                        </div>
                        <div class="dingdan-title address" style="margin-top:10px;color:#aaa;font-size:12px;">
                            ${address.provinceName}${address.cityName}${address.countyName}${address.detailInfo}
                        </div>
                    </div>
                </a>

            </div>

            <div>
                <a type="button" id="agreeBackBtn" style="color:#fff;font-size:14px;display:block;height:35px; line-height:35px; background-color:#dc721c; margin:20px;text-align:center">同意退货</a>
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
        var addressParams = {
            userName: '${address.userName}',
            postalCode: '${address.postalCode}',
            provinceName: '${address.provinceName}',
            cityName: '${address.cityName}',
            countyName: '${address.countyName}',
            detailInfo: '${address.detailInfo}',
            telNumber: '${address.telNumber}',
            atype: 2
        };
        $(function(){
            $('.returnAddress').click(function(){
                JWEIXIN.openAddress(function(data){
                    if(${address != null}) {
                        if(data.userName == '${address.userName}' && data.postalCode == '${address.postalCode}'
                                && data.provinceName == '${address.provinceName}' && data.cityName == '${address.cityName}'
                                && data.countryName == '${address.countyName}' && data.detailInfo == '${address.detailInfo}'
                                && data.telNumber == '${address.telNumber}') {
                            return;
                        }
                    }

                    addressParams.userName = data.userName;
                    addressParams.postalCode = data.postalCode;
                    addressParams.provinceName = data.provinceName;
                    addressParams.cityName = data.cityName;
                    addressParams.countyName = data.countryName;
                    addressParams.detailInfo = data.detailInfo.replace(/[\r\n]/g, "");
                    addressParams.telNumber = data.telNumber;

                    if($('.returnAddress .addr').is(':hidden')) {
                        $('.returnAddress .addr').show();
                        $('.returnAddress .addr-none').hide();
                    }
                    $('.username').html(data.userName + " " + data.telNumber);
                    $('.address').html(data.provinceName + data.cityName + data.countryName + data.detailInfo);
                });
            });

            $('#agreeBackBtn').click(function(){
                var username = $.trim($('.username').html());
                var address = $.trim($('.address').html());
                if(Util.checkEmpty(username) || Util.checkEmpty(address)) {
                    $.alert("您的退货信息尚未设置！", "系统提示！");
                    /*$.modal({
                        title: "系统提示",
                        text: "您的退货信息暂不完善，您可以去统一设置！",
                        buttons: [
                            { text: "取消", className: "default"},
                            { text: "去完善", onClick: function(){
                                href('api/apiShop/myShopSet');
                            } }
                        ]
                    });*/
                    return;
                }

                $("#msgPopup").wePopup();
            });

            $('.guanzhu-ok').click(function(){
                var params = $.extend({}, addressParams);
                params.orderId = '${order.id}';

                ajaxPost('api/apiOrder/agreeBack', params, function(data) {
                    if(data.success) {
                        window.location.replace(document.referrer);
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                },function(){
                    $.loading.load({type:3, msg:'正在提交...'});
                }, -1);
            });
        });
    </script>
</body>

</html>
