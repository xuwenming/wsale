<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>管理我的银行卡</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="bank-list">
                    <div class="bank-content">
                        <div>
                            <img class="bank-logo" src="${pageContext.request.contextPath}/wsale/images/bank-logo.png" /> 中国工商银行
                        </div>
                        <div class="bank-number">
                            <div class="bank-type">储蓄卡</div>
                            <div>2392 **** **** 323</div>
                        </div>
                    </div>
                    <div class="grayright-text">
                        提示：支持借记卡、信用卡，支持银行清单
                    </div>

                    <div class="search-btn">
                        <a href="javascript:href('api/apiWallet/toAddBank');" class="bottom-btn">添加银行卡</a>
                    </div>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        
    </script>
</body>

</html>
