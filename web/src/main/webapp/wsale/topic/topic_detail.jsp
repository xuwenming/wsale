<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                            <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" id="saveBtn">
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

            <div id="rewardPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 300px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden; ">

                    </div>
                </div>
            </div>

            <header>
                <h2>${topic.title}</h2>
                <span class="header_author">${topic.user.nickname}</span>
                <span class="header_time"><fmt:formatDate value="${topic.addtime}" pattern="yyyy-MM-dd"/></span>
                <a href="javascript:href('api/apiHomeController/home');">集东集西</a>
            </header>
            <hr/>
            <main>
                ${topic.content}
            </main>
            <div class="erweima" style="display: none;">
                <img src="images/ma.png" alt="这里放二维码"/>
            </div>
            <form>
                <fieldset>
                    <legend>赏心悦目！赏！</legend>
                    <div class="shang reward">赏</div>

                    <p>已有<span>${rewards.size()}</span>人打赏:</p>
                    <c:if test="${rewards.size() > 0}">
                        <ul class="reward">
                            <c:forEach items="${rewards}" var="reward" varStatus="vs">
                                <li><img src="${reward.user.headImage}"/></li>
                            </c:forEach>

                        </ul>
                        <div class="gengduo" style="display: none;"><span>更多</span></div>
                    </c:if>
                </fieldset>
            </form>
            <div class="jilu">
                <span>阅读</span>
                <span>${topic.topicRead}</span>&#x3000;
                <span>
                    <img class="zan" src="${pageContext.request.contextPath}/wsale/article/images/zan_a.png" alt=""/>
                    <img class="zan miss" src="${pageContext.request.contextPath}/wsale/article/images/zan_b.png" alt=""/>
                </span>
                <span>${topic.topicPraise}</span>
                <span class="right"><a href="javascript:void(0);">举报</a></span>
            </div>
            <footer>
                <span class="right comment">
                    <a href="#">写留言</a>
                    <img class="pencil" src="${pageContext.request.contextPath}/wsale/article/images/pencil.png" alt=""/>
                </span>

                <div class="footer_item">
                    <div class="footer_img"><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0" alt=""/></div>
                    <div class="footer_content">
                        <span class="footer_name">name</span>
                        <span class="footer_time">time</span>
                        <p>这里是评论内容在紫砂方面，希望微拍堂能够扶持一些比较优秀的店铺，让爱好者能先初步接触到好的紫砂实用作品，培养兴趣，循序渐进，慢慢进入到收藏领域。当他们在微拍堂买到好的作品，然后介绍新的玩家进来，就会形成良好的口碑口口相传，最终形成一个良性的循环</p>
                        <span class="delete">删除</span>
                    </div>
                </div>
                <div class="footer_item">
                    <div class="footer_img"><img src="http://image.zcys2016.cn/mmopen/ajNVdqHZLLBxXI4HfAFv6icLEqV2w3xxeEr4kO9RNnoUk31KU21Jh0Y1ic5oxwo15fic5NE3CvicPuNvZDGm3y0Pibw/0" alt=""/></div>
                    <div class="footer_content">
                        <span class="footer_name">name</span>
                        <span class="footer_time">time</span>
                        <p>这里是评论内容</p>
                        <span class="delete">删除</span>
                    </div>
                </div>
            </footer>
        </div>
    </div>

    <script type="text/javascript">
        $(function(){
            $('.comment').click(function(){
                $('#comment').val('');
                $('#commentPopup').wePopup();
            });

            $('.reward').click(function(){
                $('#rewardPopup').wePopup();
            });
        });
    </script>
</body>
</html>
