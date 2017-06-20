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

            <!--item-->
            <li class="ub line">
                <div class="ub-f1 list-check-box">
                    <!--选中样式为 checked 未选中 为  uncheck-->
                    <i class="checked"></i>
                </div>
                <div class="ub-f1 list-bank">
                    <img src="${pageContext.request.contextPath}/<%=bankIcon %>" onerror="this.src='${pageContext.request.contextPath}/wsale/images/gsyh-icon.png'" />
                </div>
                <div class="ub-f1 list-info">
                    <h3><%=cardNo %></h3>
                    <p><%=username %></p>
                    <p><%=bankName %></p>
                </div>
            </li>

            <!--item
            <li class="ub line">
                <div class="ub-f1 list-check-box">
                    <i class="uncheck"></i>
                </div>
                <div class="ub-f1 list-bank">
                    <img src="${pageContext.request.contextPath}/wsale/images/gsyh-icon.png" />
                </div>
                <div class="ub-f1 list-info">
                    <h3>3951451444</h3>
                    <p>义务集东集西网络科技有限公司</p>
                    <p>中国银行义乌支行</p>
                </div>
            </li>-->

        </ul>

        <!--bottom-->
        <div class="pay-btn">
            <a href="javascript:href('api/apiWallet/offline_transfer_two?transferAmount=${transferAmount}');">下一步</a>
            <p>我们收到您的汇款后会在1-2个工作日内处理</p>
        </div>
    </div>

    <script type="text/javascript">
    </script>
</body>

</html>
