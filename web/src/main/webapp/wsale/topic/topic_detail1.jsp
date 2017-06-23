<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>${topic.title}</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.topic.css" />
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
                        <div style="width:80%; padding: 10px;" class="close-popup">
                            <span style="padding: 10px 0px;">取 消</span>
                        </div>
                    </div>
                    <textarea style="margin:10px 0px; background-color: #fff;" maxlength="50" placeholder="发表您的留言..." id="comment"></textarea>
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
                    <div class="shang-background-a"></div>
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
                        <p style="padding-top: 20px;" class="otherAmount">其他金额</p>
                        <p style="margin-top: 5px; color: #aaa;">赞赏是为表示鼓励而对文章内容的无偿赠与</p>

                    </div>
                </div>
            </div>
        </div>

        <header>
            <h2>${topic.title}</h2>
            <c:if test="${topic.user.utype == 'UT02'}">
                <span class="header_author ">
                    <c:choose>
                        <c:when test="${fn:length(topic.user.nickname) > 5}">
                            <c:out value="${fn:substring(topic.user.nickname, 0, 5)}..." />
                        </c:when>
                        <c:otherwise>${topic.user.nickname}</c:otherwise>
                    </c:choose>
                </span>
            </c:if>
            <span class="header_time"><fmt:formatDate value="${topic.addtime}" pattern="yyyy-MM-dd"/></span>
            <span><a href="javascript:href('api/apiHomeController/home');">集东集西</a></span>
            <div class="right button-at">+关注</div>
            <hr>
        </header>
        <main>
            ${topic.content}
        </main>
        <div class="erweima" style="display: none;">
            <!--<img src="images/ma.png" alt="这里放二维码"/>-->
        </div>
        <form>
            <fieldset>

                <div class="line"><hr>
                    <div><p>赏心悦目！赏！</p></div>
                </div>
                <div class="shang reward">赏</div>

                <p>已有<span>${rewards.size()}</span>人打赏:</p>
                <c:if test="${rewards.size() > 0}">
                    <ul class="reward">
                        <c:forEach items="${rewards}" var="reward" varStatus="vs">
                            <li><img src="${reward.user.headImage}"/></li>
                        </c:forEach>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                        <li><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0"></li>
                    </ul>
                    <div class="gengduo"><span>更多</span></div>
                </c:if>
            </fieldset>
        </form>
        <div class="jilu">
            <span>阅读</span>
            <span>${topic.topicRead}</span>&#x3000;
            <span>
                <c:choose>
                    <c:when test="${topic.praise}">
                        <img class="zan" src="${pageContext.request.contextPath}/wsale/images/zan_b.png"/>
                    </c:when>
                    <c:otherwise><img class="zan addPraise" src="${pageContext.request.contextPath}/wsale/images/zan_a.png"/></c:otherwise>
                </c:choose>
            </span>
            <span>${topic.topicPraise}</span>
            <span class="right"><a href="javascript:void(0);">举报</a></span>
        </div>
        <footer>
                <span class="right comment">
                    <a href="#">写留言</a>
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
    var loading = true, currPage = 1, rows = 10;
    $(function(){
        $('.comment').click(function(){
            $('#comment').val('');
            $('#commentPopup').wePopup();
        });

        $('.reward').click(function(){
            $('#rewardPopup').wePopup();
        });

        $('#addComment').bind('click', addComment);
        $('.addPraise').bind('click', addPraise);

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

        var dom = Util.cloneDom("topic_comment_template", comment, viewData);
        if(isNew) $(".comments").prepend(dom)
        else $(".comments").append(dom);

        dom.find("img.lazy").lazyload({
            placeholder : base + 'wsale/images/lazyload.png'
        });
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
        ajaxPost('api/apiTopic/addComment', params, function(data){
            if(data.success) {
                buildComment(data.obj, true);
                $.toast("发表成功", "text");
            }
        });
    }

    function addPraise() {
        var params = {objectId : '${topic.id}', objectType:'TOPIC'}, _this = this;
        ajaxPost('api/apiPoint/addPraise', params, function(data){
            if(data.success) {
                $(_this).unbind('click').removeClass('addPraise').attr('src', base + 'wsale/images/zan_b.png');
                var $num = $(_this).parent().next();
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
</script>
</body>
</html>