<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>${topic.title}</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.topic.css?v=${staticVersion}" />
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main"  style="background-color:#fff;">
            <div id="commentPopup" class="weui-popup-container">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="overflow: hidden;">
                    <div class="modal-content" style="padding-top: 0; margin-top: 0px; overflow: hidden;">
                        <div style="background-color:#fff; padding: 0 5px;border-bottom:1px solid #ddd;">
                            <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" id="addComment">
                                发 表
                            </div>
                            <div style="width:80%; padding: 10px;" class="closeComment">
                                <span style="padding: 10px 0px;">关 闭</span>
                            </div>
                        </div>
                        <textarea style="margin:10px 0px; background-color: #fff;" maxlength="100" placeholder="发表您的留言..." id="comment"></textarea>
                    </div>
                </div>
            </div>

            <div id="reportPopup" class="weui-popup-container">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="overflow: hidden;">
                    <div class="modal-content" style="padding-top: 0; margin-top: 0px; overflow: hidden;">
                        <div style="background-color:#fff; padding: 0 5px;border-bottom:1px solid #ddd;">
                            <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" class="reportBtn">
                                举 报
                            </div>
                            <div style="width:80%; padding: 10px;" class="close-popup">
                                <span style="padding: 10px 0px;">关 闭</span>
                            </div>
                        </div>
                        <textarea style="margin:10px 0px; background-color: #fff;" maxlength="100" placeholder="请输入您的举报理由，不得出现不和谐文字..." id="reportReason"></textarea>
                    </div>
                </div>
            </div>

            <div id="rewardOtherPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 180px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden; ">
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="5" id="rewardFee" placeholder="请输入打赏金额(元)..."/>
                        <a class="bottom-btn payReward" style="color: #fff;font-size: 16px;">打赏</a>
                        <span class="fenlei-desc">赞赏是为表示鼓励而对文章内容的无偿赠与</span>
                    </div>
                </div>
            </div>

            <div id="rewardPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="background-color:transparent;height: 380px;text-align: center;overflow: hidden;">

                    <div class="modal-content" style="padding-top: 0;overflow: hidden">
                        <div class="shang-background-a close-popup"></div>
                        <div class="shang-active">
                            <p>
                                <c:choose>
                                    <c:when test="${topic.user.utype == 'UT02'}">
                                        <img src="${topic.user.headImage}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/wsale/images/logo.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="shang-background-b"></div>
                        <div class="shang-bottom">
                            <span>
                                <c:choose>
                                    <c:when test="${topic.user.utype == 'UT02'}">
                                        ${topic.user.nickname}
                                    </c:when>
                                    <c:otherwise>
                                        集东集西
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <div class="money-list">
                                <div data-fee="2">2 <sub>元</sub></div>
                                <div data-fee="5">5 <sub>元</sub></div>
                                <div data-fee="20">20 <sub>元</sub></div>
                                <div data-fee="50">50 <sub>元</sub></div>
                                <div data-fee="100">100 <sub>元</sub></div>
                                <div data-fee="200">200 <sub>元</sub></div>
                            </div>
                            <p style="padding-top: 20px;color:#6f83ec;" class="otherAmount">其他金额</p>
                            <p style="margin-top: 5px; color: #aaa;font-size: 12px;">赞赏是为表示鼓励而对文章内容的无偿赠与</p>
                        </div>
                    </div>
                </div>
            </div>

            <header>
                <h2 class="font-black">${topic.title}</h2>
                <span class="header_time"><fmt:formatDate value="${topic.addtime}" pattern="yyyy-MM-dd"/></span>
                <span>
                    <c:choose>
                        <c:when test="${topic.user.utype == 'UT02'}">
                            <a href="javascript:href('api/userController/homePage?userId=${topic.user.id}');">${topic.user.nickname}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:href('api/apiHomeController/home');">集东集西</a>
                        </c:otherwise>
                    </c:choose>
                </span>
                <c:if test="${topic.user.utype == 'UT02' and !topic.user.attred and !topic.user.self}">
                    <div class="right button-at attBtn">+ 关注</div>
                </c:if>
                <hr>
            </header>
            <main class="content">
                ${topic.content}
            </main>
            <div class="erweima">
                <img src="${pageContext.request.contextPath}/wsale/images/topic-qcode.jpg"/>
            </div>
            <form>
                <fieldset>
                    <div class="line"><hr>
                        <div><p>赏心悦目！赏！</p></div>
                    </div>
                    <div class="shang reward">赏</div>

                    <p>已有<span>${rewards.size()}</span>人打赏</p>

                    <ul class="rewardUsers">
                        <c:forEach items="${rewards}" var="reward" varStatus="vs">
                            <li><img src="${reward.user.headImage}"/></li>
                        </c:forEach>
                    </ul>
                    <div class="gengduo hide"><span>更多</span></div>
                </fieldset>
            </form>
            <div class="jilu">
                <span>阅读</span>
                <span>${topic.topicRead}</span>
                <div class="main-zan">
                     <span>
                        <c:choose>
                            <c:when test="${topic.praise}">
                                <img class="zan" src="${pageContext.request.contextPath}/wsale/images/zan_b.png"/>
                            </c:when>
                            <c:otherwise><img class="zan addPraise" src="${pageContext.request.contextPath}/wsale/images/zan_a.png"/></c:otherwise>
                        </c:choose>
                    </span>
                    <span>${topic.topicPraise}</span>
                </div>

                <span class="right report" style="margin-top: 10px;"><a href="javascript:void(0);">举报</a></span>
            </div>
            <footer>
                <span class="right comment">
                    <a href="javascript:void(0);">写留言</a>
                    <img class="pencil" src="${pageContext.request.contextPath}/wsale/images/pencil.png" alt=""/>

                </span>
                <div class="comments">

                </div>
                <div class="home-content">
                    <div class="weui-infinite-scroll">
                        <div class="infinite-preloader"></div>
                        留言加载中
                    </div>
                </div>
            </footer>
        </div>
    </div>
    <jsp:include page="../template/topic_template.jsp"></jsp:include>

    <script type="text/javascript">
        var loading = true, currPage = 1, rows = 10, self = ${topic.user.self}, replyComment = null;

        $(function(){
            $('.content img').css('width','100%');
            $('.content img').parent().css('text-indent', '0');
            if($(".rewardUsers").height() == $(".rewardUsers").css('max-height').replace('px', '')) {
                $(".gengduo").removeClass("hide");
            }

            var items = [];
            $(".content img[data-original]").each(function(){
                var src = $(this).attr("data-original");
                if($(this).parent().is('a')) {
                    $(this).removeAttr('data-original').attr('src', src);
                } else {
                    items.push(src);
                }
            });

            $(".content img[data-original]").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });

            $(".gengduo").click(function(e){
                e.stopPropagation();
                var self = $(this);
                setTimeout(function() {
                    $('.rewardUsers').addClass("fullDesc");
                    self.remove();
                }, 200);
            });

            $(".content img[data-original]").click(function(){
                if($(this).parent().is('a')) return;
                JWEIXIN.previewImage(items, items.indexOf($(this).attr("src")));
            });
            $('.comment').click(function(){
                $('#comment').val('');
                $('#commentPopup').wePopup();
            });

            $('.reward').click(function(){
                if(self) {
                    $.toast("<font size='2'>不可以对自己打赏哟</font>", "text");
                    return;
                }
                $('#rewardPopup').wePopup();
            });

            $('.closeComment').click(function(){
                $.closePopup();
                if(replyComment) {
                    replyComment = null;
                    $("#comment").attr("placeholder", "发表您的留言...")
                }
            });
            $('#addComment').bind('click', addComment);
            $('.main-zan').bind('click', addPraise);
            $('.attBtn').bind('click', attrFun);
            $('.report').click(function(){
                $('#reportPopup').wePopup();
            });
            $('.reportBtn').bind('click', report);

            $('.otherAmount').click(function(){
                $.closePopup();
                $('#rewardOtherPopup').wePopup();
            });

            $('.money-list div').click(function(){
                var rewardFee = $(this).attr('data-fee');
                if(rewardFee) payReward(rewardFee);
            });

            $('.payReward').click(function(){
                var rewardFee = $('#rewardFee').val();
                if(rewardFee) payReward(rewardFee);
                else $('#rewardFee').focus();
            });

            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawComments();
                }, 20);
            });
            drawComments();
        });

        function drawComments() {
            var params = {topicId : '${topic.id}', page:currPage, rows:rows};
            ajaxPost('api/apiTopic/topicComments', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    for(var i in result.rows) {
                        var comment = result.rows[i];
                        buildComment(comment)
                    }

                    if(result.rows.length >= rows) {
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }

                    loading = false;
                    currPage ++;
                } else {
                    $(document.body).destroyInfinite();
                    $(".home-content .weui-infinite-scroll").hide();
                }
            });
        }

        function buildComment(comment, isNew) {
            var viewData = Util.cloneJson(comment);
            viewData.addtime = Util.getTime(comment.addtime);
            viewData.comment = viewData.comment.replace(/[\r\n]/g, "<br/>");
            if(comment.replyComment) {
                viewData.replyComment.addtime = Util.getTime(comment.replyComment.addtime);
                viewData.replyComment.comment = viewData.replyComment.comment.replace(/[\r\n]/g, "<br/>");
            }

            var dom = Util.cloneDom("topic_comment_template", comment, viewData);
            if(comment.replyComment) dom.find('.topic-com-answer').show();

            if(isNew) $(".comments").prepend(dom);
            else $(".comments").append(dom);

            dom.find("img.lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });

            if(self && !comment.replyComment) {
                dom.find('.reply').show().click(comment, function(event){
                    replyComment = event.data;
                    $("#comment").attr("placeholder", "回复 " + replyComment.user.nickname).focus();
                    $('.comment').click();
                });
            }
        }

        function addComment() {
            $.closePopup();
            if(${sessionInfo.isGag}) {
                $.toast("你已被禁言", "forbidden");
                return;
            }
            var comment = $.trim($('#comment').val());
            if(Util.checkEmpty(comment)) {
                return;
            }
            $("#comment").val('');

            var params = {topicId : '${topic.id}', comment:comment, ctype:"TEXT"};
            if(replyComment) params.pid = replyComment.id;
            ajaxPost('api/apiTopic/addComment', params, function(data){
                if(data.success) {
                    if(replyComment) {
                        var $p = $('#topic_comment_template_' + replyComment.id);
                        $p.find('[data-name=comment]').html(data.obj.comment.replace(/[\r\n]/g, "<br/>"));
                        $p.find('[data-name=addtime]').html(Util.getTime(data.obj.addtime));
                        $p.find('.topic-com-answer').show();
                        $p.find('.reply').hide();

                        replyComment = null;
                        $("#comment").attr("placeholder", "发表您的留言...");
                    } else {
                        buildComment(data.obj, true);
                    }
                    $.toast("发表成功", "text");
                }
            });
        }

        function addPraise() {
            var params = {objectId : '${topic.id}', objectType:'TOPIC'}, _this = this;
            if(!$(_this).find('img').hasClass('addPraise')) {
                return;
            }
            ajaxPost('api/apiPoint/addPraise', params, function(data){
                if(data.success) {
                    $(_this).unbind('click').find('img').removeClass('addPraise').attr('src', base + 'wsale/images/zan_b.png');
                    var $num = $(_this).find('span:eq(1)');
                    $num.text(parseInt($num.text()) + 1);
                }
            });
        }

        function payReward(rewardFee) {
            ajaxPost('api/apiReward/reward', {objectId : '${topic.id}',objectType:'TOPIC', rewardFee:rewardFee*100}, function(data){
                if(data.success) {
                    href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO04&attachType=TOPIC&totalFee=' + rewardFee);
                }
            });
        }

        function attrFun() {
            var _this = this;
            ajaxPost('api/userController/addShieldorfans', {objectType:'FS', userId:'${topic.addUserId}'}, function(data){
                if(data.success) {
                    $(_this).remove();
                }
            });
        }

        function report() {
            var reportReason = $('#reportReason').val();
            if(Util.checkEmpty(reportReason)) {
                $('#reportReason').focus();
                return;
            }
            ajaxPost('api/reportController/report', {objectType:'TOPIC', objectId:'${topic.id}', reportReason:reportReason}, function(data){
                $.closePopup();
                if(data.success) {
                    $.toast("举报成功");
                } else {
                    $.toast("<font size='1'>"+data.msg+"</font>", "text");
                }
            });
        }

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"${topic.title}",
                desc:$('.content').html().replace(/[\r\n]|<[^>]+>|&nbsp;/g, ''),
                link:removeQueDefault(location.href),
                imgUrl:"${topic.icon}"
            };
            JWEIXIN.onMenuShareAppMessage(shareData);
            JWEIXIN.onMenuShareTimeline(shareData);
            JWEIXIN.onMenuShareQQ(shareData);
            JWEIXIN.onMenuShareWeibo(shareData);
            JWEIXIN.onMenuShareQZone(shareData);
        });
    </script>
</body>
</html>
