<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>发现</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .infocenter-number {
            float: none;
            position: inherit;
            margin: 0 8px;
        }

        .redPoint {
            min-width: 0;
            min-height: 0;
            width: 8px;
            height: 8px;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color: #f5f5f5;">

        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;background-color: #fff; ">
                <div class="faxian-list">
                    <a class="faxian-link" href="javascript:href('api/apiFindController/newProduct');">
                        <div style="float:right;">
                            <c:if test="${new_unread_count > 0}">
                                <span class="infocenter-number redPoint"></span>
                            </c:if>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" style="" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/xinpinkaipai-icon.png" class="faxian-lefticon" /> 新品开拍</div>
                    </a>
                    <a class="faxian-link" href="javascript:href('api/apiBestProductController/homeBest');">
                        <div style="float:right;">
                            <c:if test="${home_best_count > 0}">
                                <span class="infocenter-number redPoint"></span>
                            </c:if>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" style="" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/jingxuan-icon.png" class="faxian-lefticon" /> 精选拍品</div>
                    </a>
                    <a class="faxian-link" href="javascript:href('api/apiChat/chat_list');">
                        <div style="float:right;">
                            <c:if test="${chat_unread_count > 0}">
                                <span class="infocenter-number">${chat_unread_count}</span>
                            </c:if>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" style="" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/xiaoxizhongxin-icon.png" class="faxian-lefticon" /> 消息中心</div>
                    </a>
                </div>
            </div>
        </div><!-- /content -->

        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find')" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->

    </div><!-- /page -->

    <script type="text/javascript">

    </script>
</body>

</html>
