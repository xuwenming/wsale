<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提交汇款信息</title>
    <jsp:include page="../inc.jsp"></jsp:include>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.box.css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.pay.info.css" />
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div class="header">
            <p>1. 金额：请填写汇款的实际金额，如银行那边有手续费请填写扣除后的金额</p>
            <p>2. 时间：请填写汇款日期</p>
            <p>3. 在您汇款信息提交后，我们将在24小时内处理，法定节假日48小时内处理完成</p>
        </div>

        <form method="post" id="transfer_form">
            <ul class="list">
                <li class="ub line">
                    <div class="ub-f1 list-text">
                        <span class="ub ub-ac">汇款人</span>
                    </div>
                    <div class="ub-f1 list-input">
                        <input type="text" placeholder="请输入汇款人姓名" name="transferUserName" id="transferUserName"/>
                    </div>
                </li>
                <li class="ub line">
                    <div class="ub-f1 list-text">
                        <span class="ub ub-ac">金额</span>
                    </div>
                    <div class="ub-f1 list-input">
                        <input type="tel" placeholder="请输入金额" name="transferAmount" id="transferAmount" value="${transferAmount}"/>
                    </div>
                </li>
                <li class="ub line">
                    <div class="ub-f1 list-text">
                        <span class="ub ub-ac">时间</span>
                    </div>
                    <div class="ub-f1 list-label">
                        <input type="text" placeholder="请选择时间" id="transferTime" name="transferTimeStr" readonly style="text-align: right;">
                        <!--<span>请选款日期</span>-->
                    </div>
                    <!--<div class="ub-f1 arrow">
                        <img src="${pageContext.request.contextPath}/wsale/images/right.png" />
                    </div>-->
                </li>
                <li class="ub line">
                    <div class="ub-f1 list-text">
                        <span class="ub ub-ac">备注</span>
                    </div>
                    <div class="ub-f1 list-input">
                        <input type="text" placeholder="请输入备注" name="remark" id="remark"/>
                    </div>
                </li>
            </ul>
        </form>

        <!--bottom-->
        <div class="pay-btn">
            <a id="transferBtn">提交</a>
            <p>此单仅用于财务核对，不会自动从银行扣款</p>
        </div>
    </div>

    <script type="text/javascript">
        $(function(){
            $("#transferBtn").bind("click", transfer);

            $("#transferTime").val(new Date().format('yyyy-MM-dd HH:mm')).datetimePicker({
                title: '汇款日期',
                min: "1990-12-12",
                max: "2050-12-12 12:12",
                onChange: function (picker, values, displayValues) {
                    //console.log(values);
                }
            });
        });

        function transfer() {
            var transferUserName = $.trim($('#transferUserName').val());
            if(Util.checkEmpty(transferUserName)) {
                $.toptip('请填写汇款人姓名');
                return;
            }
            var transferAmount = $.trim($('#transferAmount').val());
            if(Util.checkEmpty(transferAmount)) {
                $.toptip('请填写汇款金额');
                return;
            }
            var remark = $.trim($('#remark').val());
            if(Util.checkEmpty(remark)) {
                $.toptip('请填写汇款备注');
                return;
            }

            ajaxPost('api/apiWallet/addOfflineTransfer', $('#transfer_form').serialize(), function(data){
                if(data.success) {
                    $.loading.close();
                    $.alert('提交成功！核对无误后，将自动完成充值。', function(){
                        href('api/apiWallet/myWallet');
                    });

                }
            }, function(){
                $.loading.load({type:2, msg:'提交中...'});
            }, -1);
        }
    </script>
</body>

</html>
