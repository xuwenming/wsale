<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>拍品不存在</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div style="margin:30px;">
                    <img src="${pageContext.request.contextPath}/wsale/images/fengcun-icon.png" style="width:100px;" />
                </div>
                <div style="font-size:14px;margin:20px 0px;">
                    ${errorMsg}
                </div>
                <div>
                    <a href="javascript:href('api/apiShop/shop?userId=${userId}');" class="bottom-btn confirm-ok" style="color:#fff; text-align:center; margin:10px 20px;">查看该卖家的更多拍品</a>
                    <a href="javascript:href('api/apiHomeController/home');" class="bottom-btn confirm-cancel" style="color:#dc721c;border:1px solid #dc721c; background-color:#fff; text-align:center;margin:10px 20px;">返回集东集西</a>
                </div>
            </div>


        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        
    </script>
</body>

</html>
