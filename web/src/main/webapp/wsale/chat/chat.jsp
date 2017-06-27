<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    request.setAttribute("vEnter", "\n");
%>
<!DOCTYPE HTML>
<html>
<head>
    <title></title>
    <jsp:include page="../chat_inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="臻藏-${friend.nickname}" class="jqm-demos" style="background-color:#f5f5f5;">
        <div data-role="header" data-position="fixed" data-theme="b" data-tap-toggle="false" style="position: fixed; left: 0;right:0;top:0;z-index: 100;">
            <a href="javascript:href('api/apiChat/chat_list');" class="ui-btn ui-btn-left ui-alt-icon ui-nodisc-icon ui-corner-all ui-btn-icon-notext ui-icon-carat-l">Back</a>
            <h1></h1>

        </div><!-- /header -->
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="record-luyin">
                <img src="${pageContext.request.contextPath}/wsale/images/microphone_icon.png" />
                <div>手指上滑，取消发送</div>
            </div>
            <div class="home-content" style="margin:0;">
                <!--<div class="fanye" style="margin-top: 10px;">努力加载中</div>-->
                <div class="history-info">
                    <img src="${pageContext.request.contextPath}/wsale/images/historytime-icon.png" />&nbsp;
                    <span class="history-msg">努力加载中</span>
                </div>
                <div class="sixin-content" style="overflow: auto; margin: 0 10px;">

                </div>
                <div id="sixin-send" class="send-info">
                    <img src="${pageContext.request.contextPath}/wsale/images/yuyin-icon.png" class="send-yuyin" />
                    <img src="${pageContext.request.contextPath}/wsale/images/gengduo-icon.png" class="send-more" />
                    <!--<input type="text" id="content" />-->
                    <textarea id="content" rows="1" data-role="none"></textarea>
                    <div class="recordAudio" style="display: none; ">按住 说话</div>
                    <audio id="audio"></audio>
                    <a class="send-btn">发送</a>
                    <div class="qqface">
                        <div class="qqface-list"></div>
                        <div class="face-tab">
                            <div class="face-img"><img src="${pageContext.request.contextPath}/wsale/images/face-icon.png" /></div>
                            <div class="pic-img"><img src="${pageContext.request.contextPath}/wsale/images/pic-icon.png" /> 照片</div>
                        </div>
                    </div>
                </div>

            </div>
        </div><!-- /content -->
    </div><!-- /page -->
    <jsp:include page="../template/chat_template.jsp"></jsp:include>

    <script type="text/javascript">
        var scrollH = 0, rows = 10, initTime = new Date().getTime(), start, end,startY,endY, recordTimer, audio = document.getElementById("audio");
        var fromUser = '${own.id}', hxPass = '${own.hxPassword}', toUser = '${friend.id}';
        $(function(){
            drawMessages();

            $(".send-btn").bind('click', sendMsg);

            $('.send-more').click(function(){
                if(!$('.face-tab').is(':hidden')) {
                    $('.sixin-content').css('padding-bottom', '50px');
                } else {
                    $('.sixin-content').css('padding-bottom', '125px');
                    $.mobile.silentScroll($(document).height());
                }

            }).qqFace({
                id : 'facebox', //表情盒子的ID
                assign:'content', //给那个控件赋值
                path:'${pageContext.request.contextPath}/wsale/images/face/'	//表情存放的路径
            });

            // 点击其他部位关闭qq弹窗
            $("body:eq(0)").bind('click', function(e){
                var target = $(e.target);
                if(target.closest(".qqface").length == 0){
                    $('#facebox').hide();
                    $('#facebox').remove();
                    $(".face-tab").hide();
                    $('.sixin-content').css('padding-bottom', '50px');
                }
            });

            $(".pic-img").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    var msg = {
                        fromUserId : fromUser,
                        addtime : new Date().format('yyyy-MM-dd HH:mm'),
                        content : localIds[0],
                        mtype : 'WXIMAGE',
                        user : {
                            headImage : '${own.headImage}'
                        }
                    };
                    var dom = buildMessage(msg, true);
                    scroll();
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        ajaxPost('api/apiCommon/upload', {mediaId:serverId, type:'IMAGE', tag:'CHAT'}, function(data){
                            if(data.success) {
                                send(data.obj.path, function(message){
                                    //buildMessage(message, true);
                                    setTimeout(function(){
                                        dom.find('.imageMsg').attr('src', message.content);
                                        dom.find('.sending').remove();
                                    }, 200);
                                }, 'IMAGE');
                            }
                        });
                    });
                }, 1);
            });

            $('.send-yuyin').toggle(
                function() {
                    var $content = $("#content");
                    $content.hide();
                    var sx = ($content.height() - $(".recordAudio").height())/2,
                        zy = ($content.width() - $(".recordAudio").width())/2;
                    $(".recordAudio").css('padding', sx + 'px ' + zy + 'px').css("display","inline-block");
                    $(".send-yuyin").attr("src","${pageContext.request.contextPath}/wsale/images/keyboard-icon.png");
                },
                function() {
                    $(".recordAudio").css("display","none");
                    $(".send-yuyin").attr("src","${pageContext.request.contextPath}/wsale/images/yuyin-icon.png");
                    $("#content").show();
                    $("#content").val('').focus();
                }
            );

            $(".recordAudio").on('touchstart', function(event){
                $(this).css("background-color","#ddd");

                var touch = event.originalEvent.targetTouches[0];
                startY = touch.pageY;
                endY = touch.pageY;
               // event.preventDefault();
                //recordTimer = setTimeout(function(){
                    JWEIXIN.startRecord(function(){
                        start = new Date().getTime();
                        $('.record-luyin div').html('手指上滑，取消发送');
                        $('.record-luyin').show();
                    });
                //}, 300);
               // event.stopPropagation();
                return false;
            });
            $(".recordAudio").on('touchend', function(event){
                event.preventDefault();
                $(this).css("background-color","#fff");
                $('.record-luyin').hide();
                //end = new Date().getTime();
                //if(end - initStart < 300) {
                //    initStart = 0, end = 0;
                //    clearTimeout(recordTimer);
                //} else {
                    JWEIXIN.stopRecord(function(localId){
                        //(endY - )
                        uploadVoice(localId);
                    });
                //}
               // event.stopPropagation();
               // return false;
            });

            $(".recordAudio").on('touchmove', function(event){
                event.preventDefault();
                if(event.originalEvent.targetTouches.length > 1 || event.scale && event.scale !== 1) return;
                var touch = event.originalEvent.targetTouches[0];
                endY = touch.pageY;
                if(startY - endY > 30) {
                    $('.record-luyin div').html('松开手指，取消发送');
                } else {
                    $('.record-luyin div').html('手指上滑，取消发送');
                }
            });


            $(".sixin-content").on('click', '.audioMsg', function(){
                var _this = this;
                var src = $(_this).attr('data-src');
                if($(_this).hasClass('wx')) {
                    if($(_this).hasClass('play')) {
                        $(_this).removeClass('play');
                        $(_this).find('span').html('语音');
                        JWEIXIN.stopVoice(src);
                    } else {
                        if($('.play').length != 0) {
                            var playSrc = $('.play').attr('data-src');
                            if($('.play').hasClass('wx')) {
                                JWEIXIN.stopVoice(playSrc);
                            } else {
                                if(audio) audio.pause();
                            }
                            $('.play').find('span').html('语音');
                            $('.play').removeClass('play');
                        }
                        $(_this).addClass('play');
                        $(_this).find('span').html('收听中');
                        JWEIXIN.playVoice(src);
                    }
                } else {
                    if(!audio) {
                        audio = document.createElement('audio');
                    }
                    if($(_this).hasClass('play')) {
                        $(_this).removeClass('play');
                        $(_this).find('span').html('语音');
                        audio.pause();
                    } else {
                        if($('.play').length != 0) {
                            var playSrc = $('.play').attr('data-src');
                            if($('.play').hasClass('wx')) {
                                JWEIXIN.stopVoice(playSrc);
                            } else {
                                if(audio) audio.pause();
                            }
                            $('.play').find('span').html('语音');
                            $('.play').removeClass('play');
                        }
                        $(_this).addClass('play');
                        $(_this).find('span').html('收听中');
                        audio.src = src;
                        audio.play();
                        audio.onended = function() {
                            audio.src = '';
                            $(_this).find('span').html('语音');
                            $(_this).removeClass('play');
                        };
                    }
                }
            });

            $(".sixin-content").on('click', '.productMsg', function(){
                var productId = $(this).attr('data-product');
                href('api/apiProductController/productDetail?id=' + productId);
            });
            $(".sixin-content").on('click', '.sendProduct', function(event){
                event.stopPropagation();
                var _this = this, $productMsg = $(this).closest('.productMsg');
                send($productMsg.attr('data-product'), function(message){
                    $(_this).remove();
                }, 'PRODUCT');
            });

            $(".sixin-content").on('click', '.bbsMsg', function(){
                var bbsId = $(this).attr('data-bbs');
                href('api/bbsController/bbsDetail?id=' + bbsId);
            });
            $(".sixin-content").on('click', '.sendBbs', function(event){
                event.stopPropagation();
                var _this = this, $bbsMsg = $(this).closest('.bbsMsg');
                send($bbsMsg.attr('data-bbs'), function(message){
                    $(_this).remove();
                }, 'BBS');
            });

            $(".history-info").click(function(){
                if($('.history-msg').html() == '查看更多消息') {
                    $('.history-msg').html('努力加载中');
                    drawMessages($('.sixin-content').children().length);
                }
            });

            $('#content').autoHeight(5.6*2);

            $("#content").focus(function(){
                $.mobile.silentScroll($(document).height());
            });
        });

        wx.ready(function () {
            JWEIXIN.onVoiceRecordEnd(function(localId){
                uploadVoice(localId);
            });
            JWEIXIN.onVoicePlayEnd(function(localId){
                $('.audioMsg[data-src="'+localId+'"]').removeClass('play');
                $('.audioMsg[data-src="'+localId+'"]').find('span').html('语音');
            });
        });

        function replace_em(str){
            str = str.replace(/\</g,'&lt;');
            str = str.replace(/\>/g,'&gt;');
            str = str.replace(/\n/g,'<br/>');
            str = str.replace(/\[em_([0-9]*)\]/g,'<img src="${pageContext.request.contextPath}/wsale/images/face/$1.gif" border="0" />');
            return str;
        }

        function uploadVoice(localId) {
            if(startY - endY > 30) {
                return;
            }
            end = new Date().getTime();
            if(Math.floor((end - start)/1000) == 0) {
                $.toast("说话时间太短", "cancel");
                return;
            }
            var msg = {
                fromUserId : fromUser,
                addtime : new Date().format('yyyy-MM-dd HH:mm'),
                content : localId,
                mtype : 'WXAUDIO',
                user : {
                    headImage : '${own.headImage}'
                },
                duration : Math.floor((end - start)/1000)
            };
            var dom = buildMessage(msg, true);
            scroll();
            JWEIXIN.uploadVoice(localId, function(serverId){
                ajaxPost('api/apiCommon/upload', {mediaId:serverId, type:'AUDIO', tag:'CHAT'}, function(data){
                    if(data.success) {
                        send(data.obj.path, function(message){
                            //buildMessage(message, true);
                            //dom.find('.wxaudioMsg').addClass('audioMsg').removeClass('wxaudioMsg')
                            //       .attr('data-src', base + message.content)
                            //        .html('语音：' + message.duration + '\"');
                        }, 'AUDIO', data.obj.duration);
                    }
                });
            });
        }

        function drawMessages(page) {
            ajaxPost('api/apiChat/messages', {userId:toUser, page: page || 0, rows: rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            buildMessage(result.rows[i]);
                        }

                        if(${!empty product}) {
                            showProductMsg();
                        }

                        if(${!empty bbs}) {
                            showBbsMsg();
                        }

                        scroll(true);
                    }

                    if(result.rows.length < rows) {
                        $('.history-info').hide();
                    } else {
                        $('.history-msg').html('查看更多消息');
                    }
                } else {
                    $('.history-info').hide();
                }
            });
        }

        function showProductMsg() {
            var msg = {
                fromUserId : fromUser,
                addtime : new Date().format('yyyy-MM-dd HH:mm'),
                mtype : 'SHOWPRODUCT',
                user : {
                    headImage : '${own.headImage}'
                }
            };
            buildMessage(msg, true);
        }

        function showBbsMsg() {
            var msg = {
                fromUserId : fromUser,
                addtime : new Date().format('yyyy-MM-dd HH:mm'),
                mtype : 'SHOWBBS',
                user : {
                    headImage : '${own.headImage}'
                }
            };
            buildMessage(msg, true);
        }

        function scroll(load) {
            setTimeout(function(){
                if(load)
                    $.mobile.silentScroll($(document).height() - scrollH);
                else
                    $.mobile.silentScroll($(document).height());
                setTimeout(function(){
                    scrollH = $(document).height();//$(window).scrollTop();
                },20);
            }, 200);
        }

        function buildMessage(message, append) {
            var viewData = Util.cloneJson(message), dom, mtype = message.mtype;
            viewData.addtime = message.addtime.substring(2, 16);
            if(mtype == 'TEXT') {
                viewData.content = WebIM.utils.parseEmoji(message.content.replace(/[\r\n]/g, "<br/>"));
            } else if(mtype == 'IMAGE'){
                viewData.content = '<img data-original="'+message.content+'" class="send-img imageMsg lazy"/>';
            } else if(mtype == 'WXIMAGE') {
                viewData.content = '<img data-original="'+message.content+'" class="send-img imageMsg lazy"/>';
            } else if(mtype == 'AUDIO') {
                viewData.content = '<div class="audioMsg" data-src="'+message.content+'"><span>语音</span>：'+(message.duration||1)+'\"</div>';
            } else if(mtype == 'WXAUDIO') {
                viewData.content = '<div class="audioMsg wx" data-src="'+message.content+'"><span>语音</span>：'+message.duration+'\"</div>';
            } else if(mtype == 'PRODUCT') {
                viewData.content = '<div class="productMsg" data-product="'+message.product.id+'"><div data-original="'+message.product.icon+'" style="width: 180px;max-width:100%;height:0;padding-bottom:100px; background-position:center center;background-size:cover;" class="imageMsg lazy"></div><div style="width: 180px;max-width:100%;"><a style="float:left;">拍品：'+message.product.content+'</a></div></div>';
            } else if(mtype == 'SHOWPRODUCT') {
                viewData.content = '<div class="productMsg" data-product="${product.id}"><div data-original="${product.icon}" style="width: 180px;max-width:100%;height:0;padding-bottom:100px; background-position:center center;background-size:cover;" class="imageMsg lazy"></div><div style="width: 180px;max-width:100%;"><a href="#" style="max-height: 40px;overflow-y: hidden;">拍品：${fn:replace(product.content, vEnter, '')}</a><div class="sendProduct" style="float:right;padding-top:5px;">发送拍品</div></div></div>';
            } else if(mtype == 'BBS') {
                viewData.content = '<div class="bbsMsg" data-bbs="'+message.bbs.id+'"><div data-original="'+message.bbs.icon+'" style="width: 180px;max-width:100%;height:0;padding-bottom:100px; background-position:center center;background-size:cover;" class="imageMsg lazy"></div><div style="width: 180px;max-width:100%;"><a style="float:left;">帖子：'+message.bbs.bbsTitle+'</a></div></div>';
            } else if(mtype == 'SHOWBBS') {
                viewData.content = '<div class="bbsMsg" data-bbs="${bbs.id}"><div data-original="${bbs.icon}" style="width: 180px;max-width:100%;height:0;padding-bottom:100px; background-position:center center;background-size:cover"; class="imageMsg lazy"></div><div style="width: 180px;max-width:100%;"><a href="#" style="max-height: 40px;overflow-y: hidden;">帖子：${fn:replace(bbs.bbsTitle, vEnter, '')}</a><div class="sendBbs" style="float:right;padding-top:5px;">发送帖子</div></div></div>';
            }

            if(message.fromUserId == fromUser)
                dom = Util.cloneDom("chat_own_template", message, viewData);
            else {
                dom = Util.cloneDom("chat_friend_template", message, viewData);

                dom.find('.sixin-leftimg').click(message.fromUserId, function(event){
                    href('api/userController/homePage?userId=' + event.data);
                });
            }


            if(append) {
                $('.sixin-content').append(dom);
                // 相同时间只出现一次
                var $prev = dom.prev();
                if($prev.length != 0 && $prev.find('.sixin-time').text() == viewData.addtime) {
                    dom.find('.sixin-time').hide();
                }
            } else {
                $('.sixin-content').prepend(dom);
                // 相同时间只出现一次
                var $next = dom.next();
                if($next.length != 0 && $next.find('.sixin-time').text() == viewData.addtime) {
                    $next.find('.sixin-time').hide();
                }
            }

            dom.find('.imageMsg').click(function(){
                event.stopPropagation();
                var imageUrls = [$(this).attr('data-original')];
                JWEIXIN.previewImage(imageUrls);
            });
            if(mtype == 'WXIMAGE') {
               setTimeout(function(){
                   var w = dom.find('.sixin-record').width(), h = dom.find('.sixin-record').height();
                   dom.find('.imageMsg').after('<div class="sending-load sending" style="width: '+w+'px; height:'+h+'px; margin-top:-'+h+'px;"><img style="margin-top:'+(h/2 - 20)+'px;" src="'+base+'wsale/images/loading.gif" /><div>正在发送中...</div></div>');
               }, 200);
            }

            $(".lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
            //$.mobile.silentScroll($(document).height());
            return dom;
        }

        function sendMsg() {
            var content = $.trim($("#content").val());
            $("#content").val('').focus().blur();
            if(!Util.checkEmpty(content)) {
                send(content, function(message){
                    if(message.fromUserId == fromUser || message.fromUserId == toUser) {
                        buildMessage(message, true);
                        scroll();
                    }
                });
            }
        }

        function send(content, callback, type, duration) {
            if(conn == null || !conn.isOpened()) {
                $.toptip('正在重连，请重新发送！');
                reOpen = true;
                connInit();
                loginHx();
                return;
            }
            var msg = new WebIM.message('txt', conn.getUniqueId());
            msg.set({
                msg: content,
                to: toUser,
                success: function(id, serverMsgId) {
                    var message = {
                        mtype:type || 'TEXT',
                        fromUserId : fromUser,
                        toUserId : toUser,
                        content : content,
                        msgId : serverMsgId
                    };
                    if(duration) message.duration = duration;
                    addMessage(message, callback);
                }
            });
            conn.send(msg.body);
        }

        function handleMessage(msg, type) {
            if(!msg && !msg.id) return;
            setTimeout(function(){
                ajaxPost('api/apiChat/getMessage', {msgId : msg.id}, function(data){
                    if(data.success) {
                        var addtime = new Date(data.obj.addtime.replace(/-/g,"/"));
                        if(addtime.getTime() >= initTime && data.obj.fromUserId == toUser) {
                            buildMessage(data.obj, true);
                            updateUnRead(data.obj.id);
                            scroll();
                        }
                    }
                });
            }, 1000);
        }

        function addMessage(message, callback) {
            if(!message.content) return;
            ajaxPost('api/apiChat/addMessage', message, function(data){
                if(data.success) {
                    if(callback)
                        callback(data.obj);

                    // 推送客户提醒消息
                    pushMessage(message);
                }
            });
        }

        function updateUnRead(id) {
            ajaxPost('api/apiChat/updateUnRead', {id:id}, function(data){
                // 无操作
            });
        }

        function pushMessage(message) {
            ajaxPost('api/apiChat/pushMessage', message, function(data){
                // 无操作
            });
        }

    </script>
</body>

</html>
