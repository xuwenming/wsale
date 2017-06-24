<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%@ page import="jb.pageModel.BaseData" %>
<%
    String cardNo = Application.getString("OT01"); // 卡号
    String username = Application.getString("OT02"); // 户名
    BaseData bd = Application.get("OT03"); // 开户行以及银行图标
    String bankName = "", bankIcon= "";
    if(bd != null) {
        bankName = bd.getName();
        bankIcon = bd.getIcon();
    }
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>转账汇款信息</title>
    <jsp:include page="../inc.jsp"></jsp:include>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.box.css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.pay.css" />
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <!--header-->
        <div class="header">
            <p>1. 请汇款至如下账号</p>
            <p>2. 汇款后输入真实汇款信息</p>
        </div>

        <!--bank list-->
        <ul class="list">
            <c:forEach items="${banks}" var="bank" varStatus="vs">
                <!--item-->
                <li class="ub line">
                    <div class="ub-f1 list-check-box">
                        <!--选中样式为 checked 未选中 为  uncheck-->
                        <i data-bank-code="${bank.bank_code}" class="uncheck"></i>
                    </div>
                    <div class="ub-f1 list-bank">
                        <img src="${bank.bank_icon}" />
                    </div>
                    <div class="ub-f1 list-info">
                        <h3>${bank.bank_card}</h3>
                        <p>${bank.bank_account}</p>
                        <p>${bank.bank_name}</p>
                    </div>
                </li>
            </c:forEach>

        </ul>

        <!--bottom-->
        <div class="pay-btn">
            <a id="nextBtn">下一步</a>
            <p>我们收到您的汇款后会在1-2个工作日内处理</p>
        </div>
    </div>

    <script type="text/javascript">
        $(function(){
            $('#nextBtn').click(function(){
                if($('.list li i.checked').length == 0) {
                    $.toast("<font size='3pt;'>请选择汇款银行</font>", "text");
                    return;
                }

                href('api/apiWallet/offline_transfer_two?transferAmount=${transferAmount}&bankCode=' + $('.list li i.checked').attr('data-bank-code'));
            });

            $('ul.list').on('click', '.ub', function(){
                $('i.checked').removeClass('checked').addClass('uncheck');
                $(this).find("i").removeClass('uncheck').addClass('checked');
            });
        });
    </script>
</body>

</html>
