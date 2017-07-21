<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>认证申请</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="认证申请" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; text-align:left;">
                <div class="top-info">
                    <div class="wode-touxiang">
                        <img src="${user.headImage}" style="border:2px solid #fff;" class="wode-userimg" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'" />
                    </div>
                    <div class="renzheng-flag">
                        <c:choose>
                            <c:when test="${user.isAuth}">
                                <img src="${pageContext.request.contextPath}/wsale/images/renzheng2-icon.png" />
                                <div>已认证</div>
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/wsale/images/renzheng-icon.png" />
                                <div>未认证</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <div class="renzheng-username">
                            <span>${user.nickname}</span>
                        </div>
                        <div class="renzheng-note">
                            <span>立即实名认证享受更多特权服务</span>
                        </div>
                    </div>
                </div>
                <div class="fbpp-title">
                    申请认证
                </div>
                <div class="renzheng-area">
                    <ul class="renzheng-operate">
                        <li>
                            <img src="${pageContext.request.contextPath}/wsale/images/gerenrenzheng-icon.png" />
                            <div>个人实名认证</div>
                            <a href="javascript:href('api/apiAuth/toAuth?authType=AT01');" class="renzheng-apply">申请</a>
                        </li>
                        <li>
                            <img src="${pageContext.request.contextPath}/wsale/images/qiyerenzheng-icon.png" />
                            <div>企业实名认证</div>
                            <a href="javascript:href('api/apiAuth/toAuth?authType=AT02');" class="renzheng-apply">申请</a>
                        </li>
                    </ul>
                </div>
                <div class="fbpp-title">
                    认证特权
                </div>
                <div class="renzheng-tequan">
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/huiyuan-icon.png" style="width:20px;" /> <span>尊贵会员称号</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/cishuzengjia-icon.png" /> <span>提现次数增加到2次/天</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/renzhengbiaoji-icon.png" /> <span>店铺当中增加认证标记</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/pindaoshoulu-icon.png" /> <span>拍品可被分类频道收录</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/jdbfatie-icon.png" /> <span>进鉴定部发帖</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/canyuguanfanghuodong-icon.png" /> <span>参与官方活动</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/jiangtang-icon.png" /> <span>进讲堂</span>
                    </div>
                    <div class="tequan-list">
                        <img src="${pageContext.request.contextPath}/wsale/images/jingpaiping-icon.png" /> <span>拍品可被推到精拍屏</span>
                    </div>
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
        
    </script>
</body>

</html>
