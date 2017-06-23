<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${payOrder.objectType == 'PO01'}">支付申请职务费用</c:when>
            <c:when test="${payOrder.objectType == 'PO02'}">支付实名认证费用</c:when>
            <c:when test="${payOrder.objectType == 'PO03'}">支付申请首页精拍费用</c:when>
            <c:when test="${payOrder.objectType == 'PO04'}">打赏</c:when>
            <c:when test="${payOrder.objectType == 'PO05'}">拍品订单支付</c:when>
            <c:when test="${payOrder.objectType == 'PO07'}">消保金缴纳</c:when>
            <c:when test="${payOrder.objectType == 'PO08'}">买家保证金</c:when>
            <c:when test="${payOrder.objectType == 'PO09'}">支付申请分类精拍费用</c:when>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/ladingPasswordConfirm.js" charset="utf-8"></script>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="mask-layer-2"></div>
                <div class="pwd-dialog" style="display: none;">
                    <div class="pay-pwd" style="position: relative">
                        <div class="big-text" style="text-align: center;">
                            输入您的支付密码
                        </div>
                        <div class="pwd-input">
                            <ul id="password">
                                <li>
                                    <input readonly class="pass" type="password"maxlength="1"value="">
                                </li><li>
                                    <input readonly class="pass" type="password"maxlength="1"value="">
                                </li><li>
                                    <input readonly class="pass" type="password"maxlength="1"value="">
                                </li><li>
                                    <input readonly class="pass" type="password"maxlength="1"value="">
                                </li><li>
                                    <input readonly class="pass" type="password"maxlength="1"value="">
                                </li><li style="border-right:1px solid #dc721c;">
                                    <input readonly class="pass" type="password"maxlength="1" value="">
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div id="keyboardDIV"></div>
                </div>

                <div class="yue-zhifu">
                    <div class="big-text pay-text">
                        <c:choose>
                            <c:when test="${payOrder.objectType == 'PO01'}">申请职务</c:when>
                            <c:when test="${payOrder.objectType == 'PO02'}">
                                <c:choose>
                                    <c:when test="${attachType == 'AT01'}">个人实名认证</c:when>
                                    <c:otherwise>企业实名认证</c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${payOrder.objectType == 'PO03'}">申请首页精拍</c:when>
                            <c:when test="${payOrder.objectType == 'PO04'}">
                                <c:choose>
                                    <c:when test="${attachType == 'BBS'}">帖子打赏</c:when>
                                    <c:when test="${attachType == 'TOPIC'}">专题打赏</c:when>
                                    <c:otherwise>打赏</c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${payOrder.objectType == 'PO05'}">拍品订单</c:when>
                            <c:when test="${payOrder.objectType == 'PO07'}">消保金</c:when>
                            <c:when test="${payOrder.objectType == 'PO08'}">保证金</c:when>
                            <c:when test="${payOrder.objectType == 'PO09'}">申请分类精拍</c:when>
                        </c:choose>
                    </div>
                    <div class="pay-value">￥${payOrder.totalFee}元</div>
                </div>
                <div class="pay-content">
                    <c:if test="${serviceFee > 0}">
                        <div class="pay-list">
                            <span class="big-text">技术服务费：</span><span class="pay-smallval">￥${serviceFee}元</span>
                        </div>
                    </c:if>
                    <div class="pay-list">
                        <span class="big-text">需要支付：</span><span class="pay-smallval">￥${payOrder.totalFee + serviceFee}元</span>
                        <c:if test="${payOrder.objectType == 'PO08'}">
                            &nbsp;<span class="pay-desc">("拍卖失败"或"付款"后退还)</span>
                        </c:if>
                    </div>
                    <div>
                        <div class="pay-list pay-choose">
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/xuanzhong-icon.png" data-flag="true" /> <img class="pay-way" src="${pageContext.request.contextPath}/wsale/images/weixin-icon.png" />
                            <div class="way-content">
                                <div class="big-text">微信支付</div>
                                <div class="pay-desc">单笔最高5,000-50,000元</div>
                            </div>
                        </div>
                        <!--<div class="pay-list pay-choose">
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/weixuanzhong-icon.png" data-flag="false" /> <img style="height:35px;" class="pay-way" src="${pageContext.request.contextPath}/wsale/images/yinlian-icon.png" />
                            <div class="way-content">
                                <div><span class="big-text">银行卡支付</span> <span class="pay-desc">仅支持储蓄卡</span></div>
                                <div class="pay-desc">单笔最高5,000-100,000元</div>
                            </div>
                        </div>-->
                        <div class="pay-list pay-choose">
                            <input type="hidden" value="${wallet.realName}" id="realName">
                            <input type="hidden" value="${wallet.idNo}" id="idNo">
                            <input type="hidden" value="${wallet.payPassword}" id="payPassword">
                            <a style="color:#fff;" class="chongzhi-btn">充值</a>
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/weixuanzhong-icon.png" data-flag="false" /> <img class="pay-way" src="${pageContext.request.contextPath}/wsale/images/yuee-icon.png" />
                            <div class="way-content">
                                <div class="big-text">
                                    <c:choose>
                                        <c:when test="${payOrder.totalFee <= wallet.amount}">余额支付</c:when>
                                        <c:otherwise>余额不足</c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="pay-desc">可用余额<fmt:formatNumber type="number" value="${wallet.amount}" pattern="0.00" maxFractionDigits="2"/>元</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="margin-top:30px;">
                    <a href="" class="bottom-btn" id="payBtn" style="color:#fff;">安全支付</a>
                </div>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        var index = 0;
        $(function(){
            $('#payBtn').bind('click', pay);

            $(".pay-choose").click(function(){
                var index = $(this).index();
                if(index == 1 && ${wallet.amount} < ${payOrder.totalFee}) {
                    $.modal({
                        title: "系统提示！",
                        text: "余额不足，您可用去充值后再来付款",
                        buttons: [
                            { text: "取消", className: "default"},
                            { text: "去充值", onClick: function(){
                                href('api/apiWallet/recharge');
                            } },
                        ]
                    });
                    return;
                }
                var imgFlag = $(this).find(".choose-icon").attr("data-flag");
                if(imgFlag == "false"){
                    $(this).find(".choose-icon").attr("data-flag","true");
                    $(this).siblings().find(".choose-icon").attr("data-flag","false");
                    $(this).find(".choose-icon").attr("src", base + "wsale/images/xuanzhong-icon.png");
                    $(this).siblings().find(".choose-icon").attr("src", base + "wsale/images/weixuanzhong-icon.png");
                }
            });

            $('.chongzhi-btn').click(function(){
                event.stopPropagation();
                href('api/apiWallet/recharge');
            });

            $('#keyboard li').click(function() {
                if ($(this).hasClass('delete')) {
                    $('#password li:eq('+(--index%6)+') input').val('');
                    if($('#password li:eq(0) input').val()==''){
                        index = 0;
                    }
                    return;
                }
                if ($(this).hasClass('cancle')) {
                    $('.mask-layer-2, .pwd-dialog').hide();
                    index = 0;
                    $('#password li input').val('');
                    return;
                }
                if ($(this).hasClass('symbol') || $(this).hasClass('tab')){
                    var character = $(this).text();
                    if($('#password li:eq(5) input').val() != ''){
                        index = 0;
                        return;
                    }
                    $('#password li:eq('+(index++%6)+') input').val(character);
                    if($('#password li:eq(5) input').val()!='') {
                        var check_pass_word = '', success = true;
                        $('#password li').each(function(){
                            check_pass_word += $(this).find('input').val();
                        });

                        if(md5(check_pass_word) != $('#payPassword').val()) {
                            success = false;
                            $.alert("支付密码输入错误，请再试一次！", "系统提示！", function(){
                                index = 0;
                                $('#password li input').val('');
                            });
                        }

                        if(success) {
                            $('.mask-layer-2, .pwd-dialog').hide();
                            var objectType = '${payOrder.objectType}', objectId = '${payOrder.objectId}', totalFee = ${payOrder.totalFee + serviceFee}, serviceFee = ('${serviceFee}'*100).toFixed(0);
                            var params = {totalFee : totalFee,serviceFee:serviceFee,objectType:objectType,objectId:objectId, channel:'CS02'};
                            if(${!empty attachType}) params.attachType = '${attachType}';
                            ajaxPost('api/pay/walletPay', params, function(data){
                                if(data.success) {
                                    $.toast("支付成功", function(){
                                        var url = document.referrer;
                                        if(objectType == 'PO02') {
                                            url = getUrl('api/apiAuth/authApply');
                                        } else if(objectType == 'PO03' || objectType == 'PO09') {
                                            url = getUrl('api/apiProductController/productDetail?id=${attachType}');
                                        } else if(objectType == 'PO07') {
                                            url = getUrl('api/apiWallet/myProtection');
                                        }

                                        if(objectType == 'PO08') {
                                            $.alert("保证金已支付，您还尚未出价哦！", "系统提示！", function(){
                                                window.location.replace(url);
                                            });
                                        } else {
                                            window.location.replace(url);
                                        }

                                    });
                                } else {
                                    alert(data.msg);
                                    index = 0;
                                    $('#password li input').val('');
                                }
                            }, function(){
                                $.loading.load({type:2, msg:'支付中...'});
                            });
                        }

                    }
                }
                return false;
            });
        });

        function pay() {
            var index = $('.choose-icon[data-flag=true]').closest('.pay-choose').index();
            if(index == 0) {
                var objectType = '${payOrder.objectType}', objectId = '${payOrder.objectId}', totalFee = ${payOrder.totalFee + serviceFee}, serviceFee = ('${serviceFee}'*100).toFixed(0);
                var params = {totalFee : totalFee,serviceFee:serviceFee,objectType:objectType,objectId:objectId, channel:'CS01'};
                if(${!empty attachType}) params.attachType = '${attachType}';
                wxPayCall(params, function(){
                    $.toast("支付成功", function(){
                        var url = document.referrer;
                        if(objectType == 'PO02') {
                            url = getUrl('api/apiAuth/authApply');
                        } else if(objectType == 'PO03' || objectType == 'PO09') {
                            url = getUrl('api/apiProductController/productDetail?id=${attachType}');
                        } else if(objectType == 'PO07') {
                            url = getUrl('api/apiWallet/myProtection');
                        }

                        if(objectType == 'PO08') {
                            $.alert("保证金已支付，您还尚未出价哦！", "系统提示！", function(){
                                window.location.replace(url);
                            });
                        } else {
                            window.location.replace(url);
                        }
                    });
                }, function(){
                    // 删除订单
                });
            } else {
                if(Util.checkEmpty($('#realName').val()) || Util.checkEmpty($('#idNo').val())) {
                    $.modal({
                        title: "系统提示！",
                        text: "您的身份信息尚不完善",
                        buttons: [
                            { text: "取消", className: "default" },
                            { text: "去完善", onClick: function(){
                                href('api/apiWallet/toEditIdentity');
                            } },
                        ]
                    });
                    return;
                }
                $('.mask-layer-2, .pwd-dialog').show();
            }

        }
    </script>
</body>

</html>
