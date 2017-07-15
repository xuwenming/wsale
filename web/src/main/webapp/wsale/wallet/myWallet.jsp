<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的余额</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="yue-topInfo">
                    <a href="javascript:href('api/apiWallet/walletBillList');" class="yue-detail">余额明细<img src="${pageContext.request.contextPath}/wsale/images/jiantou-white.png" /></a>
                    <div class="yue-total"><fmt:formatNumber type="number" value="${wallet.amount}" pattern="0.00" maxFractionDigits="2"/></div>
                    <div>冻结款项：<fmt:formatNumber type="number" value="${wallet.frozenAmount}" pattern="0.00" maxFractionDigits="2"/></div>
                    <div class="">
                        <ul class="yue-type">
                            <li>
                                <div>待付款</div>
                                <div class="type-money"><fmt:formatNumber type="number" value="${order_amount_count.unpay_amount}" pattern="0.00" maxFractionDigits="2"/></div>
                            </li>
                            <li>
                                <div>未发货</div>
                                <div class="type-money"><fmt:formatNumber type="number" value="${order_amount_count.undeliver_amount}" pattern="0.00" maxFractionDigits="2"/></div>
                            </li>
                            <li>
                                <div>待收货</div>
                                <div class="type-money"><fmt:formatNumber type="number" value="${order_amount_count.unreceipt_amount}" pattern="0.00" maxFractionDigits="2"/></div>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="faxian-list">
                    <a href="javascript:href('api/apiWallet/recharge');" class="faxian-link">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/chongzhi-icon.png" class="faxian-lefticon" /> 充值</div>
                    </a>
                    <a class="faxian-link cash">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/tixian-icon.png" class="faxian-lefticon" /> 提现</div>
                    </a>
                </div>
                <div class="faxian-list" style="border-top:none;">

                    <!--<a href="javascript:href('api/apiWallet/bankManage');" class="faxian-link">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/card-icon.png" class="faxian-lefticon" /> 管理银行卡</div>
                    </a>-->

                    <a href="javascript:href('api/apiWallet/toSafetySet');" class="faxian-link">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/paysafe-icon.png" class="faxian-lefticon" /> 支付安全</div>
                    </a>
                </div>
                <div class="faxian-list" style="border-top:none;">

                    <a href="javascript:href('api/apiWallet/myProtection');" class="faxian-link">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/xiaobao-icon.png" class="faxian-lefticon" /> 消保金</div>
                    </a>

                    </a>
                </div>

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
            $('.cash').click(function(){
                ajaxPost('api/apiWallet/getWallet', {}, function(data){
                    if(data.success) {
                        var wallet = data.obj;
                        if(!wallet.realName || !wallet.idNo) {
                            $.modal({
                                title: "系统提示！",
                                text: "您的身份信息尚不完善",
                                buttons: [
                                    { text: "取消", className: "default" },
                                    { text: "去完善", onClick: function(){
                                        href('api/apiWallet/toEditIdentity');
                                    } }
                                ]
                            });
                        } else {
                            href('api/apiWallet/cash');
                        }
                    } else {
                        $.toptip(data.msg);
                    }
                });
            });
        });
    </script>
</body>

</html>
