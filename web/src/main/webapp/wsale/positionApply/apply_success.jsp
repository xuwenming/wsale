<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>申请职务</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="申请职务" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div style="margin:30px 10px; text-align:left;">
                    <img src="${pageContext.request.contextPath}/wsale/images/ok-icon.png" style="width:30px;" />
                    <div style="width:88%; float:right; font-size:14px;">申请资料已提交后台审核，审核时长5-10天，请耐心等候，届时将会收到消息提醒。</div>
                    <a href="javascript:replace('api/apiCategoryController/forum?id=${categoryId}');" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; text-align:center; margin-top:30px;">确定</a>
                </div>
            </div>


        </div><!-- /content -->
    </div><!-- /page -->
    
    <script type="text/javascript">

    </script>
</body>

</html>
