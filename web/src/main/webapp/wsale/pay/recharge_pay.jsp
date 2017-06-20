<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额充值</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="yue-zhifu">
                    <div class="big-text pay-text">
                        余额充值
                    </div>
                    <div class="pay-value">￥${payOrder.totalFee}元</div>
                </div>
                <div class="pay-content">
                    <div class="pay-list">
                        <span class="big-text">需要支付：</span><span class="pay-smallval">￥${payOrder.totalFee}元</span>
                    </div>
                    <div>
                        <div class="pay-list pay-choose">
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/xuanzhong-icon.png" data-flag="true" /> <img class="pay-way" src="${pageContext.request.contextPath}/wsale/images/weixin-icon.png" />
                            <div class="way-content">
                                <div class="big-text">微信支付</div>
                                <div class="pay-desc">单笔最高5,000-50,000元</div>
                            </div>
                        </div>
                        <div class="pay-list pay-choose">
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/weixuanzhong-icon.png" data-flag="false" /> <img style="height:35px;" class="pay-way" src="${pageContext.request.contextPath}/wsale/images/yinlian-icon.png" />
                            <div class="way-content">
                                <div class="big-text">转账汇款</div>
                                <div class="pay-desc">ATM 柜台转账 网银转账 支付宝</div>
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
        $(function(){

            $(".pay-choose").click(function(){
                var imgFlag = $(this).find(".choose-icon").attr("data-flag");
                if(imgFlag == "false"){
                    $(this).find(".choose-icon").attr("data-flag","true");
                    $(this).siblings().find(".choose-icon").attr("data-flag","false");
                    $(this).find(".choose-icon").attr("src", base + "wsale/images/xuanzhong-icon.png");
                    $(this).siblings().find(".choose-icon").attr("src", base + "wsale/images/weixuanzhong-icon.png");
                }
            });

            $('#payBtn').click(function(){
                var num = $('.choose-icon[data-flag=true]').closest('.pay-choose').index();
                if(num == 0)
                    pay();
                else
                    href('api/apiWallet/offline_transfer_one?transferAmount=${payOrder.totalFee}');
            });
        });

        function pay() {
            var objectType = '${payOrder.objectType}', totalFee = ${payOrder.totalFee};
            //totalFee = 0.01; // TODO 测试专用
            var params = {totalFee : totalFee,objectType:objectType,channel:'CS01'};
            wxPayCall(params, function(){
                $.toast("充值成功", function(){
                    window.location.replace(decodeURIComponent('${backUrl}'));
                });
            }, function(){
                // 删除订单
            });
        }
    </script>
</body>

</html>
