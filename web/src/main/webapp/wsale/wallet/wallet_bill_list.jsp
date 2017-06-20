<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额历史清单</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0px 0px 50px 0px;">
                <div class="fensi-list">
                </div>
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    正在加载中
                </div>
            </div>

            <div class="yue-bottom">
                <div class="money-more">
                    余额（元）：<span class="yue-sum"><fmt:formatNumber type="number" value="${wallet.amount}" pattern="0.00" maxFractionDigits="2"/></span>
                </div>
                <div class="shaixuan-btn">
                    <img class="shaixuan-img" src="${pageContext.request.contextPath}/wsale/images/shaixuan-icon.png" /> 款项筛选
                </div>
                <div class="shaixuan-type">
                    <ul>
                        <li>全部款项</li>
                        <li>冻结中款项</li>
                        <li>已解冻款项</li>
                        <li>已提现款项</li>
                    </ul>
                </div>
            </div>

        </div><!-- /content -->
        <jsp:include page="../template/wallet_template.jsp"></jsp:include>

    </div><!-- /page -->

    <script type="text/javascript">
        var loading = true, currPage = 1, rows = 10, type = 1;

        $(function(){
            $(document.body).on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawBills();
                }, 20);
            });

            drawBills();

            $('.shaixuan-type ul li').click(function(){
                type = $(this).index() + 1;
                currPage = 1;
                $(document.body).destroyInfinite();
                $(".fensi-list").empty();
                $(".home-content .weui-infinite-scroll").show();
                drawBills();
            });
        });

        function drawBills() {
            var params = {page:currPage, rows:rows};
            if(type == 2) {
                params.wtype = 'WT08';
                params.handleStatus = 'HS01';
            } else if(type == 3) {
                params.wtype = 'WT08';
                params.handleStatus = 'HS03';
            } else if(type == 4) {
                params.wtype = 'WT02';
                params.handleStatus = 'HS03';
            }
            ajaxPost('api/apiWallet/bills', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var bill = result.rows[i];
                            buildBill(bill);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".fensi-list").append(Util.noDate(1, '没有相关记录'));
                    }
                    if(result.rows.length >= rows) {
                        $(document.body).infinite();
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }
                } else {
                    $(document.body).destroyInfinite();
                    $(".home-content .weui-infinite-scroll").hide();
                }
            });
        }

        function buildBill(bill) {
            var viewData = Util.cloneJson(bill);
            if(bill.isIncome) {
                var sign = bill.amount > 0 ? "+" : "";
                if(bill.wtype == 'WT01' && bill.amount < 0) {
                    viewData.wtypeZh = '扣款';
                    bill.channelZh = '后台扣款';
                    viewData.amount = '<span class="out-money">' + sign +bill.amount.toFixed(2)+'</span>';
                } else {
                    viewData.amount = '<span class="in-money">' + sign +bill.amount.toFixed(2)+'</span>';
                }

            } else {
                var sign = bill.amount > 0 ? "-" : "";
                if(bill.wtype == 'WT02' && bill.handleStatus != 'HS03') {
                    var msg = bill.handleStatus == 'HS04' ? '提现失败' : '处理中';
                    viewData.amount = '<span>'+sign+bill.amount.toFixed(2)+'</span><div class="yue-smalltext" style="margin-top: 0;">'+msg+'</div>';
                } else {
                    viewData.amount = '<span class="out-money">'+sign+bill.amount.toFixed(2)+'</span>';
                }
            }
            viewData.info = bill.channelZh + ' - ' + new Date(bill.addtime.replace(/-/g,"/")).format('yyyy-MM-dd HH:mm');
            var dom = Util.cloneDom("wallet_detail_template", bill, viewData);
            $(".fensi-list").append(dom);

            dom.click(bill.id, function(event){
                href('api/apiWallet/walletBillDetail?id=' + event.data);
            });
        }
    </script>
</body>

</html>
