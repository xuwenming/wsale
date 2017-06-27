<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的主题</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="xinxi-ppzt">
                    <a class="faxian-link">
                        <div class="list-right" <c:if test="${productThemes.total >= 4}">onclick="href('api/apiShop/shop');"</c:if>>
                            <c:choose>
                                <c:when test="${productThemes.total == 0}">
                                    <span class="grayright-text">暂无拍品</span>
                                </c:when>
                                <c:when test="${productThemes.total < 4}">
                                    <span class="grayright-text">暂无更多</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="grayright-text">更多拍品</span>
                                </c:otherwise>
                            </c:choose>

                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">拍品主题</div>
                    </a>
                    <div>
                        <ul class="paipin-imglist">
                            <c:forEach items="${productThemes.rows}" var="productTheme" varStatus="vs">
                                <li onclick="href('api/apiProductController/productDetail?id=${productTheme.id}');">
                                    <img src="${productTheme.icon}" />
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="xinxi-ppzt">
                    <a class="faxian-link">
                        <div class="list-right" <c:if test="${textThemes.total >= 2}">onclick="href('api/userController/moreBbsTheme?themeType=TEXT');"</c:if>>
                            <c:choose>
                                <c:when test="${textThemes.total == 0}">
                                    <span class="grayright-text">暂无主题</span>
                                </c:when>
                                <c:when test="${textThemes.total < 2}">
                                    <span class="grayright-text">暂无更多</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="grayright-text">更多主题</span>
                                </c:otherwise>
                            </c:choose>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">文字主题</div>
                    </a>
                    <div class="zhuti-list">
                        <c:forEach items="${textThemes.rows}" var="textTheme" varStatus="vs">
                            <a class="wenzizhuti-content" href="javascript:href('api/bbsController/bbsDetail?id=${textTheme.id}');">
                                <div class="wenzizhuti-img">
                                    <img src="${textTheme.icon}" />
                                </div>
                                <div class="wenzizhuti-info">
                                    <c:choose>
                                        <c:when test="${textTheme.isEssence}">
                                            <div class="wenzizhuti-flag">
                                                <img src="${pageContext.request.contextPath}/wsale/images/jsp-icon3.png" />
                                            </div>
                                            <div class="tiezi-title orange-title">${textTheme.bbsTitle}</div>
                                        </c:when>
                                        <c:when test="${textTheme.isLight}">
                                            <div class="tiezi-title wenzizhuti-flag">
                                                <img src="${pageContext.request.contextPath}/wsale/images/jsp-icon2.png" />
                                            </div>
                                            <div class="blue-title">${textTheme.bbsTitle}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="tiezi-title">${textTheme.bbsTitle}</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="wzzt-info">
                                        <div style="float: right;" class="time">${textTheme.addtime}</div>
                                        <div class="info-xinxi">发帖人:${textTheme.addUserName}</div>
                                        <!--<div>发帖人:${textTheme.addUserName}&nbsp;&nbsp;发帖时间:<fmt:formatDate value="${textTheme.addtime}" pattern="yyyy-MM-dd"/></div>-->
                                        <div>回复：${textTheme.bbsComment} &nbsp;&nbsp;围观：${textTheme.bbsRead}</div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>

                <div class="xinxi-ppzt">
                    <a class="faxian-link">
                        <div class="list-right" <c:if test="${audioThemes.total >= 2}">onclick="href('api/userController/moreBbsTheme?themeType=AUDIO');"</c:if>>
                            <c:choose>
                                <c:when test="${audioThemes.total == 0}">
                                    <span class="grayright-text">暂无主题</span>
                                </c:when>
                                <c:when test="${audioThemes.total < 2}">
                                    <span class="grayright-text">暂无更多</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="grayright-text">更多主题</span>
                                </c:otherwise>
                            </c:choose>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">声音主题</div>
                    </a>
                    <div class="zhuti-list">
                        <c:forEach items="${audioThemes.rows}" var="audioTheme" varStatus="vs">
                            <a class="wenzizhuti-content" href="javascript:href('api/bbsController/bbsDetail?id=${audioTheme.id}');">
                                <div class="wenzizhuti-img">
                                    <img src="${audioTheme.icon}" />
                                </div>
                                <div class="wenzizhuti-info">
                                    <c:choose>
                                        <c:when test="${audioTheme.isEssence}">
                                            <div class="wenzizhuti-flag">
                                                <img src="${pageContext.request.contextPath}/wsale/images/jsp-icon3.png" />
                                            </div>
                                            <div class="tiezi-title orange-title">${audioTheme.bbsTitle}</div>
                                        </c:when>
                                        <c:when test="${audioTheme.isLight}">
                                            <div class="wenzizhuti-flag">
                                                <img src="${pageContext.request.contextPath}/wsale/images/jsp-icon2.png" />
                                            </div>
                                            <div class="tiezi-title blue-title">${audioTheme.bbsTitle}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="tiezi-title">${audioTheme.bbsTitle}</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="wzzt-info">
                                        <div style="float: right;" class="time">${audioTheme.addtime}</div>
                                        <div class="info-xinxi">发帖人:${audioTheme.addUserName}</div>
                                        <!--<div>发帖人:${audioTheme.addUserName}&nbsp;&nbsp;发帖时间:<fmt:formatDate value="${audioTheme.addtime}" pattern="yyyy-MM-dd"/></div>-->
                                        <div>回复：${audioTheme.bbsComment} &nbsp;&nbsp;围观：${audioTheme.bbsRead}</div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>

                <div class="xinxi-ppzt">
                    <a class="faxian-link">
                        <div class="list-right" <c:if test="${topicThemes.total >= 2}">onclick="href('api/apiTopic/topic?addUserId=${user.id}');"</c:if>>
                            <c:choose>
                                <c:when test="${topicThemes.total == 0}">
                                    <span class="grayright-text">暂无专题</span>
                                </c:when>
                                <c:when test="${topicThemes.total < 2}">
                                    <span class="grayright-text">暂无更多</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="grayright-text">更多专题</span>
                                </c:otherwise>
                            </c:choose>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">专题主题</div>
                    </a>
                    <div class="zhuti-list">
                        <c:forEach items="${topicThemes.rows}" var="topicTheme" varStatus="vs">
                            <a class="wenzizhuti-content" href="javascript:href('api/apiTopic/topicDetail?id=${topicTheme.id}');">
                                <div class="wenzizhuti-img">
                                    <img src="${topicTheme.icon}" />
                                </div>
                                <div class="wenzizhuti-info">
                                    <div class="tiezi-title">${topicTheme.title}</div>
                                    <div class="wzzt-info">
                                        <div style="float: right;" class="time">${topicTheme.addtime}</div>
                                        <div class="info-xinxi">发布人:${topicTheme.user.nickname}</div>
                                        <div>点赞：${topicTheme.topicPraise} &nbsp;&nbsp;阅读：${topicTheme.topicRead}</div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
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
        $(function(){
            $('.time').each(function(){
                var time = $(this).html();
                if(time.length > 19)
                    time = time.substring(0, 19);
                $(this).html(Util.getTime(time));
            });
        });
    </script>
</body>

</html>
