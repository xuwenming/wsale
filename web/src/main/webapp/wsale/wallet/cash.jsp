<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额提现</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="chongzhi-value">
                    <div class="chongzhi-jine">
                        <span class="big-text" style="margin-left:10px;">金额</span>
                        <input type="tel" placeholder="请输入提现金额" id="amount" class="onlyNum" maxlength="10"/>
                    </div>
                </div>
                <div style="font-size: 12px;color: #999;text-align: right;margin: 8px;">
                    本次提现最大金额：
                    <c:choose>
                        <c:when test="${isAuth}">
                            ￥<fmt:formatNumber type="number" value="${wallet.amount}" pattern="0.00" maxFractionDigits="2"/>元
                        </c:when>
                        <c:when test="${!isAuth and wallet.amount < 1000}">
                            ￥<fmt:formatNumber type="number" value="${wallet.amount}" pattern="0.00" maxFractionDigits="2"/>元
                        </c:when>
                        <c:otherwise>
                            ￥<fmt:formatNumber type="number" value="1000" pattern="0.00" maxFractionDigits="2"/>元
                        </c:otherwise>
                    </c:choose>
                    ，今日余：${cashNum}次
                </div>
                <c:if test="${!isAuth and wallet.amount > 1000}">
                    <div style="font-size: 12px;color: #999;text-align: right;margin: 8px;">
                        实名可提现最大金额：￥<fmt:formatNumber type="number" value="${wallet.amount}" pattern="0.00" maxFractionDigits="2"/>元
                    </div>
                </c:if>
                <div style="margin-top:30px;">
                    <a class="bottom-btn" style="color:#fff; margin-bottom:10px;">下一步</a>
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
            $('.bottom-btn').click(function(){
                var amount = $("#amount").val();
                if(Util.checkEmpty(amount)) {
                    $("#amount").focus();
                    return;
                }
                if(amount < 1) {
                    $.alert("提现金额不能少于1元！", "系统提示！");
                    return;
                }
                var isAuth = ${isAuth}, wAmount = ${wallet.amount};
                if(!isAuth && wAmount > 1000) wAmount = 1000;
                if(amount > wAmount) {
                    $.alert("提现金额超限，请重新输入！", "系统提示！");
                    return;
                }
                if(${cashNum == 0}) {
                    $.alert("您今天的提现次数用完，请明天再来！", "系统提示！");
                    return;
                }

                href('api/pay/toCash?amount=' + amount);
            });
        });
    </script>
</body>

</html>
