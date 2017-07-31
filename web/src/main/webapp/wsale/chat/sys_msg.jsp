<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>系统消息</title>
    <jsp:include page="../chat_inc.jsp"></jsp:include>

</head>
<body>
    <div data-role="page" data-title="系统消息" class="jqm-demos" style="background-color:#f5f5f5;">
    
        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div>
                    <div class="xinpin-datetime">
                        <span class="xinpin-time">2015-02-02 12:33</span>
                    </div>
                    <div class="info-content info-two">
                        <div class="info-title">
                            <img src="${pageContext.request.contextPath}/wsale/images/touxiang1-icon.png" />
                            <span class="normal-text">和田玉</span>
                        </div>
                        <div class="sysinfo-detail">
                            <div class="sysinfo-more">
                                <img class="info-show" data-flag="down" src="${pageContext.request.contextPath}/wsale/images/down-icon.png" />
                            </div>
                            <div>
                                <span style="background-color: #f6bb2b; color: #fff; padding: 1px 4px;">2</span>
                                <span class="normal-text">卖家最新消息</span>
                            </div>
                            <div class="grayright-text sysinfo-contnet">
                                <img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>sorry，拍卖失败！</span>
                                <div>
                                    尊敬的李玉先生，你的拍品“和田玉”在截止时间内无人出价，你可以在右下角“客户服务”寻求帮助。
                                </div>
                                <div class="sysinfo-time">
                                    16小时前
                                </div>
                            </div>
                            <div class="grayright-text sysinfo-contnet hide-info">
                                <img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>拍卖一小时结束</span>
                                <div>
                                    您好，您的拍品“和田玉”将于23:00结束，分享到微信有助于成交
                                </div>
                                <div class="sysinfo-time">
                                    16小时前
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div>
                    <div class="xinpin-datetime">
                        <span class="xinpin-time">2015-02-02 12:33</span>
                    </div>
                    <div class="info-content info-one">
                        <div class="info-title">
                            <img src="${pageContext.request.contextPath}/wsale/images/touxiang1-icon.png" />
                            <span class="normal-text">和田玉</span>
                        </div>
                        <div class="sysinfo-detail">
                            <div class="sysinfo-more">
                                <img class="info-show" data-flag="down" src="${pageContext.request.contextPath}/wsale/images/down-icon.png" />
                            </div>
                            <div>
                                <span style="background-color: #fc4f1e; color: #fff; padding: 1px 4px;">1</span>
                                <span class="normal-text">卖家最新消息</span>
                            </div>
                            <div class="grayright-text sysinfo-contnet">
                                <img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>sorry，拍卖失败！</span>
                                <div>
                                    尊敬的李玉先生，你的拍品“和田玉”在截止时间内无人出价，你可以在右下角“客户服务”寻求帮助。
                                </div>
                                <div class="sysinfo-time">
                                    16小时前
                                </div>
                            </div>
                            <div class="grayright-text sysinfo-contnet hide-info">
                                <img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>拍卖一小时结束</span>
                                <div>
                                    您好，您的拍品“和田玉”将于23:00结束，分享到微信有助于成交
                                </div>
                                <div class="sysinfo-time">
                                    16小时前
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->
        <jsp:include page="../template/chat_template.jsp"></jsp:include>
    </div><!-- /page -->
    <script type="text/javascript">
        $(function(){
            /*系统信息*/
            $(".info-show").click(function(){
                var infoflag = $(this).attr("data-flag");
                if(infoflag == "down"){
                    $(this).parent().parent().find(".hide-info").show();
                    $(this).attr("data-flag","up");
                    $(this).attr("src",base + "wsale/images/up-icon.png");
                }
                if(infoflag == "up"){
                    $(this).parent().parent().find(".hide-info").hide();
                    $(this).attr("data-flag","down");
                    $(this).attr("src",base + "wsale/images/down-icon.png");
                }
            });
        });
    </script>
</body>

</html>
