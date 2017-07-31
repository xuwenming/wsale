<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>私信消息</title>
    <jsp:include page="../chat_inc.jsp"></jsp:include>
    <style>
        .official a{
            display: none;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos" id="main" style="background-color: #f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;">
                <div style="text-align: left;background-color: #fff;">
                    <a href="" class="sysinfo-link">
                        <div class="left-touxiang">
                            <img class="sysinfo-icon" src="${pageContext.request.contextPath}/wsale/images/sysinfo-icon.png" />
                        </div>
                        <div class="info-right">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text sysinfo-text">
                            系统消息
                        </div>
                    </a>
                    <c:if test="${not empty notice}">
                        <div class="qunfa-info official" onclick="href('api/apiChat/chat?isOfficial=true');" style="display: none;">
                            <div class="left-touxiang">
                                <img class="sysinfo-icon" src="${pageContext.request.contextPath}/wsale/images/logo.png"/>
                            </div>
                            <div class="text-right grayright-text"><fmt:formatDate value="${notice.addtime}" pattern="yyyy/MM/dd"/></div>
                            <div class="normal-text">
                                <div>
                                    <span>集东集西</span>
                                    <span style="padding: 0 5px;margin-left:5px;font-size: 12px;color:#ff0000;  border: 1px solid #ff0000;border-radius: 10px;">官方</span>
                                </div>
                                <c:if test="${notice_unread_count > 0}">
                                    <span class="infocenter-number unreadCount" style="position: inherit;">${notice_unread_count}</span>
                                </c:if>
                                <div class="grayright-text info-xinxi">${notice.content}</div>
                            </div>
                        </div>
                    </c:if>
                    <div id="chat_list">
                    </div>
                </div>
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    努力加载中
                </div>
            </div>


        </div><!-- /content -->


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
        var fromUser = '${own.id}' || '', hxPass = '${own.hxPassword}' || '', initTime = new Date().getTime();
        $(function(){
            drawChatList();
        });

        function drawChatList() {
            ajaxPost('api/apiChat/getChatList', {}, function(data){
                if(data.success) {
                    if($('.official').length != 0) $('.official').show();
                    var result = data.obj;
                    if(result.length != 0) {
                        for(var i in result) {
                            var dom = buildChat(result[i]);
                            $('#chat_list').append(dom);
                        }
                    }
                }
                $('.weui-infinite-scroll').hide();
            });
        }

        function buildChat(chat) {
            var viewData = Util.cloneJson(chat);
            viewData.lastContent = WebIM.utils.parseEmoji(chat.lastContent);
            //viewData.unreadCount = chat.unreadCount == 0 ? "0" : chat.unreadCount;
            var dom = Util.cloneDom("chat_list_template", chat, viewData);
            if(chat.unreadCount == 0) {
                dom.find('.unreadCount').hide();
            }
            dom.attr('data-userId', chat.friendUserId.toUpperCase());
            dom.click(chat.friendUserId, function(event){
                href('api/apiChat/chat?toUserId=' + event.data);
            });
            dom.on("swipeleft",function(){
                $(this).animate({marginLeft:'-80px'}, function(){
                    dom.find('[name=lastTimeStr]').hide();
                });

                $(this).find('.delete').animate({right:'0px'});
            });
            dom.on("swiperight",function(){
                $(this).animate({marginLeft:'0px'}, function(){
                    dom.find('[name=lastTimeStr]').show();
                });
                $(this).find('.delete').animate({right:'-80px'});
            });
            dom.find('.delete').click(chat.id, function(event){
                event.stopPropagation();
                ajaxPost('api/apiChat/delFriend', {id : event.data}, function(data){
                    if(data.success) {
                        dom.fadeOut("slow");
                    }
                });
            });
            return dom;
        }

        function handleMessage(msg, type) {
            if(!msg && !msg.id) return;
            setTimeout(function(){
                ajaxPost('api/apiChat/getMessage', {msgId : msg.id}, function(data){
                    if(data.success) {
                        var addtime = new Date(data.obj.addtime.replace(/-/g,"/")),
                            mtype = data.obj.mtype;
                        if(addtime.getTime() >= initTime) {
                            var fromUserId = msg.from.toUpperCase();
                            var $chat = $('.qunfa-info[data-userId='+fromUserId+']');
                            var content = '';
                            if(mtype == 'TEXT') {
                                content = WebIM.utils.parseEmoji(data.obj.content);
                            } else if(mtype == 'IMAGE'){
                                content = '[图片]';
                            } else if(mtype == 'AUDIO') {
                                content = '[语音]';
                            } else if(mtype == 'PRODUCT') {
                                content = '[拍品]';
                            } else if(mtype == 'BBS') {
                                content = '[帖子]';
                            }
                            if($chat.length == 0) {
                                var chat = {
                                    friendUserId : data.obj.fromUserId,
                                    lastContent : content,
                                    lastTimeStr : addtime.format('HH:mm'),
                                    unreadCount : 1,
                                    friendUser : {
                                        headImage : data.obj.user.headImage,
                                        nickname : data.obj.user.nickname
                                    }
                                };
                                var dom = buildChat(chat);
                                $('#chat_list').prepend(dom);
                            } else {
                                var $unreadCount = $chat.find('.unreadCount');
                                $unreadCount.html(parseInt($unreadCount.html()) + 1);
                                $unreadCount.show();

                                $chat.find('[name=lastContent]').html(content);
                                $chat.find('[name=lastTimeStr]').html(addtime.format('HH:mm'));
                                $('#chat_list').prepend($chat);
                            }
                        }
                    }
                });
            }, 1000);
        }
    </script>
</body>

</html>
