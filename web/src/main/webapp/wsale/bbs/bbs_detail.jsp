<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    request.setAttribute("vEnter", "\n");
%>
<!DOCTYPE HTML>
<html>
<head>
    <title></title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/audioplayer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.bbs.detail.css?v=${staticVersion}">
    <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/audioplayer.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/strophe.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/websdk-1.1.2.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/emoji.config.js?v=${staticVersion}" charset="utf-8"></script>

    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_tzsc')}">
        <script type="text/javascript">
            $.authTzsc = true; // 帖子删除
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_jy')}">
        <script type="text/javascript">
            $.authJy = true; // 禁言
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_on_off')}">
        <script type="text/javascript">
            $.authOnOff = true; // 关闭/打开
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_zd')}">
        <script type="text/javascript">
            $.authZd = true; // 置顶
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_jl')}">
        <script type="text/javascript">
            $.authJl = true; // 加亮
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_jj')}">
        <script type="text/javascript">
            $.authJJ = true; // 加精
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_gbhf')}">
        <script type="text/javascript">
            $.authGbhf = true; // 关闭回复
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_plsc')}">
        <script type="text/javascript">
            $.authPlsc = true; // 评论删除
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_jdhf')}">
        <script type="text/javascript">
            $.authJdhf = true; // 鉴定区回复
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_tzyd')}">
        <script type="text/javascript">
            $.authTzyd = true; // 帖子移动
        </script>
    </c:if>

</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div data-role="header" data-position="fixed" data-theme="b" data-tap-toggle="false" style="z-index: 99;">
            <div style="float:right; padding:5px 20px 5px 20px;" class="rightTop-more"><img src="${pageContext.request.contextPath}/wsale/images/add-icon-a.png" style="height:16px;margin-top:10px;z-index: 9;" /></div>
            <c:if test="${fromShare}">
                <a id="back" rel="external" href="javascript:href('api/apiHomeController/home');" style="padding-top: 15px;background-color: #3da8f5;">首页</a>
            </c:if>
            <h3 style="margin:0 30%">${bbs.bbsTitle}</h3>

        </div><!-- /header -->
        <div class="topmore-list new-topmore" style="z-index: 1002;position: fixed;">
            <ul>
                <!--<li><a>测试1</a></li>
                <li><a>测试2</a></li>
                <li><a>测试3</a></li>-->
            </ul>
        </div>
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <input type="hidden" id="bindMobile" value="${sessionInfo.mobile}" />
            <%--<img src="${pageContext.request.contextPath}/wsale/images/subscribe/bbs-icon.jpg" class="subscribe bbs-detail-layer-img"/>--%>

            <div class="mask-layer bbs-detail-layer" style="z-index: 1001;">
                <img src="${pageContext.request.contextPath}/wsale/images/subscribe/bbs-icon.jpg" class="subscribe"/>
            </div>
            <div class="dialog-content" style="z-index: 1002;">
                <div class="fenlei-liebiao" style="background-color:#eee;">
                    <span class="fenlei-title">分类</span>
                    <span class="fenlei-desc">请谨慎选择，切勿跨品类</span>
                </div>
                <div style="overflow-y: auto;" class="first-category">
                    <c:forEach items="${categorys}" var="category" varStatus="vs">
                        <div class="fenlei-liebiao first-style" categoryId="${category.id}" categoryName="${category.name}">
                            <img src="${pageContext.request.contextPath}/wsale/images/zhubao-icon.png" class="fenlei-img" /> <span class="fenlei-title">${category.name}</span>
                            <span class="fenlei-desc">${category.summary}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="second-style">
                <div class="second-title" style="background-color:#eee; text-align:center;">
                    <div class="retry-choose">重选</div>
                    <div class="choose-ok">完成</div>
                    <span class="fenlei-title"></span>
                </div>
                <div style="margin:10px;">
                    <div style="font-size:12px;color:#888;margin-bottom:10px;">请选择二级分类</div>
                    <div id="childCategory">
                        <c:forEach items="${childCategorys}" var="childCategory" varStatus="vs">
                            <div class="secondstyle-list" pid="${childCategory.pid}" categoryId="${childCategory.id}">${childCategory.name}</div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!--<div class="more-dialog" style="width: 40%; z-index: 1002;">
                <ul class="more-content">
                    <c:choose>
                        <c:when test="${!user.attred}"><li data-value="1">关注</li></c:when>
                        <c:otherwise><li data-value="0">已关注</li></c:otherwise>
                    </c:choose>
                    <li onclick="href('api/apiChat/chat?toUserId=${user.id}&subscribe=true&bbsId=${bbs.id}');">私信</li>
                    <li>举报</li>
                </ul>
            </div>-->

            <div id="bindMobilePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 240px; overflow: hidden;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">绑定手机号</h1>
                        </div>
                    </div>
                    <div class="modal-content">
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="11" placeholder="请输入您的手机号码..." id="mobile"/>
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="6" placeholder="请输入验证码..."  id="vcode"/>
                        <div style="float:right;width:90px;text-align:center; margin: -45px 10px;font-size: 15px;border: 1px solid #f0f0f0;padding: 5px 10px" id="vcode-btn">
                            点击获取
                        </div>
                        <div style="text-align: center;">
                            <a class="bottom-btn" style="color: #fff;font-size: 16px;" id="bindMobileBtn">确认</a>
                        </div>
                    </div>
                </div>
            </div>

            <div id="sharePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 145px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden;font-size: 14px; ">
                        <div>分享给小伙伴</div>
                        <div>
                            <ul class="share-list">
                                <li>
                                    <div class="share-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/code-icon.png" />
                                    </div>
                                    <div class="msg">二维码</div>
                                </li>
                                <li>
                                    <div class="share-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/link-icon.png" />
                                    </div>
                                    <div class="msg">发送链接</div>
                                </li>
                                <li>
                                    <div class="share-img">
                                        <img style="margin-top:23px" src="${pageContext.request.contextPath}/wsale/images/other-icon.png" />
                                    </div>
                                    <div class="msg">用微信分享</div>
                                </li>
                            </ul>
                        </div>
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
                        <a class="bottom-btn reward" style="color: #fff;font-size: 16px;">打赏</a>
                        <span class="fenlei-desc">赞赏是为表示鼓励而对帖子的无偿赠与</span>
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
                                    <c:when test="${user.utype == 'UT02'}">
                                        <img src="${user.headImage}"/>
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
                                    <c:when test="${user.utype == 'UT02'}">
                                        ${user.nickname}
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
                            <p style="margin-top: 5px; color: #aaa;font-size: 12px;">赞赏是为表示鼓励而对帖子的无偿赠与</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="imPopup" class="weui-popup-container">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="overflow: hidden;">
                    <div class="modal-content" style="padding-top: 0; margin-top: 0px; overflow: hidden;">
                        <div style="background-color:#fff; padding: 0 5px;margin-bottom: 10px;">
                            <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" class="intermediary">
                                提 交
                            </div>
                            <div style="width:80%; padding: 10px;" class="close-popup">
                                <span style="padding: 10px 0px;">关 闭</span>
                            </div>
                        </div>

                        <input class="onlyNum" style="margin:5px 0;background-color: #fff;" type="tel" maxlength="5" placeholder="必填，请输入交易金额..." id="amount"/>
                        <textarea style="margin:2px 0px; background-color: #fff;" maxlength="100" placeholder="非必填，请输入交易备注..." id="remark"></textarea>
                        <div style="font-size: 12px; padding: 5px;color: #a3621b;">
                            申请须知：<br>
                            1、请您申请前与卖家确认交易价格;<br>
                            2、一旦交易产生会影响买卖双方的信誉点或违约点;<br>
                            3、订单支付时存在一定的技术服务费，以官方公告为准;<br>
                            4、交易前请截下帖子图片以留作产生纠纷时的凭证。
                        </div>
                    </div>
                </div>
            </div>

            <div style="margin:10px;">
                <div style="width:20%; text-align:center;display:inline-block;vertical-align:top;">
                    <div>
                        <img class="lazy" data-original="${user.headImage}" style="width:100%;" onclick="href('api/userController/homePage?userId=${user.id}');"/>
                        <div style="margin-top:-20px; text-align:right;width:100%;">
                            <img src="${pageContext.request.contextPath}/wsale/images/p-${user.positionId}.png" style="width:50%;" />

                        </div>
                        <div style="line-height:1.5; text-ali☺gn:left;">
                            <div style="color:#F0401A;font-size:13px; letter-spacing:0;">${user.nickname}</div>
                            <div style="color:#EF8326;font-size:12px;letter-spacing:0;">帖子：${user.bbsNums}</div>
                            <c:if test="${!user.self}">
                                <!--<span class="guanzhu-btn" style="margin-top: 5px;padding:2px 5px;">+ 关注</span>-->
                                <%--<div class="tieziInfo-more" style="font-size:12px;color:#888;margin-top:5px;">更多 <img src="${pageContext.request.contextPath}/wsale/images/more-icon.png" style="height:10px; vertical-align:middle;" /></div>--%>
                                <c:if test="${!user.attred}"><div class="guanzhu" data-user-id="${user.id}">+ 关注</div></c:if>
                                <div class="sixin botton-b" onclick="href('api/apiChat/chat?toUserId=${user.id}&subscribe=true&bbsId=${bbs.id}');">私 信</div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div style="width:77%; margin-left:1%;text-align:left;display:inline-block; vertical-align:top;">
                    <div style="float:right;font-size:12px;color:#bbb;">1楼</div>
                    <div style="font-size:14px;">${bbs.bbsTitle}</div>
                    <div class="bbs-content">
                        <input type="hidden" value="${bbs.bbsContentLine}" id="bbsContent">
                        ${fn:replace(bbs.bbsContent, vEnter, '<br>')}
                    </div>
                    <div class="showMore hide">全文</div>
                    <div class="imageFiles" style="margin-top: 5px;">
                        <c:forEach items="${bbs.files}" var="file" varStatus="vs">
                            <%--<img class="lazy" data-original="${file.fileHandleUrl}" style="width:30%;height:80px;" />--%>
                            <div class="bbsDetailBackgroundImg lazy <c:if test="${bbs.files.size() == 1}">one-img</c:if>" data-original="${file.fileHandleUrl}"></div>
                        </c:forEach>
                    </div>
                    <c:if test="${!empty bbs.lastUpdateTime}">
                        <div style="color: #aaa;text-align:right;height:20px; line-height:20px; font-size:10px;margin-right:10px;">
                            ${bbs.lastUpdateUserName} 于<fmt:formatDate value="${bbs.lastUpdateTime}" pattern="yy/MM/dd HH:mm"/> 编辑
                        </div>
                    </c:if>
                    <div>
                        <c:choose>
                            <c:when test="${bbs.bbsType == 'BT03'}">
                                <div class="playBtn" style="width:95%;height:30px; line-height:30px;font-size:14px; background-color:#eee; border-radius:15px;margin:7px 0px; text-align:center; z-index: 1;">
                                    <div class="audioPlay" <c:if test="${!isShare && !user.self}">style="display:none;"</c:if>>
                                        <audio preload="auto" controls loop="loop" src="${pageContext.request.contextPath}/${bbs.audioUrl}"></audio>
                                    </div>
                                    <div class="msgDiv" <c:if test="${isShare || user.self}">style="display:none;"</c:if>>转发可免费听</div>
                                    <!--<img src="${pageContext.request.contextPath}/wsale/images/bofang-icon.png" style="width:30px; float:left;position: relative;" class="playProgress"/>
                                <div class="msgDiv">
                                    <c:choose>
                                        <c:when test="${isShare || user.self}">收听录音</c:when>
                                        <c:otherwise>转发可听</c:otherwise>
                                    </c:choose>
                                </div>
                                <audio id="audio"></audio>
                                <div style="display: none;" id="mp3List">
                                    <c:forEach items="${bbs.voiceFiles}" var="voiceFile" varStatus="vs">
                                        <div>
                                            <span class="path">${pageContext.request.contextPath}/${voiceFile.fileHandleUrl}</span>
                                            <span class="duration">${voiceFile.duration}</span>
                                        </div>
                                    </c:forEach>
                                </div>-->
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="btn-con">
                                    <div class="btn-con-lf"><span class="share-icon jiage-operate" style="width: 100%;">分享给朋友们</span></div>
                                    <c:choose>
                                        <c:when test="${user.self}">
                                            <div class="btn-con-rg"><span class="jiage-operate makeQr">生成二维码</span></div>
                                        </c:when>
                                        <c:when test="${!user.self and imable}">
                                            <div class="btn-con-rg btn-con-rg2"><span class="jiage-operate im-btn">中介交易</span></div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="btn-con-rg btn-con-rg3"><span class="jiage-operate im-btn">中介交易</span></div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </c:otherwise>
                        </c:choose>

                        <div>
                            <div style="border:1px solid rgb(245,187,43); color:rgb(245,187,43); text-align:center;height:20px; line-height:20px; font-size:12px; float:right;margin-right:10px;" class="isOffReply">关闭回复</div>
                            <c:if test="${bbs.bbsType == 'BT03'}">
                                <!--<div style="font-size:12px;color:#aaa;">收听 | 总时长  <time class="listened">00:00</time> | <time class="totalDuration">00:00</time></div>-->
                            </c:if>
                        </div>
                          <div style="padding-top:25px;">
                            <img src="${pageContext.request.contextPath}/wsale/images/dashang-icon.png" style="width:20px; vertical-align:middle;" class="rewardBtn" /> <span style="font-size:12px;color:#aaa;" class="rewardBtn">打赏 <count class="bbsReward">${bbs.bbsReward}</count> &nbsp;|&nbsp;</span>
                            <c:choose>
                                <c:when test="${isCollect}">
                                    <img class="collect collected" src="${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png" style="width:18px; vertical-align:middle;" />
                                    <span style="font-size:12px;color:#aaa;" class="collect collected collect-alt">取消收藏</span>
                                </c:when>
                                <c:otherwise>
                                    <img class="collect" src="${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png" style="width:18px; vertical-align:middle;" />
                                    <span style="font-size:12px;color:#aaa;" class="collect collect-alt">收藏</span>
                                </c:otherwise>
                            </c:choose>
                            <div style="float:right;font-size:12px;color:#aaa;margin-top:5px;margin-right:10px;">回复 <count class="bbsComment">${bbs.bbsComment}</count></div>
                        </div>
                        <div style="margin-left:10px;margin-top:5px;border-left: 5px solid transparent;border-right: 5px solid transparent;border-bottom: 8px solid #f0f0f0;width:0;height:0;<c:if test="${rewards.size() == 0}">display:none;</c:if>" class="dashang-sanjiao">
                        </div>
                        <div style="background-color:#f0f0f0;<c:if test="${rewards.size() == 0}">display:none;</c:if>" class="dashang-content">
                            <div style="display:inline-block; line-height: 1.2;" class="dashang-user">
                                <c:forEach items="${rewards}" var="reward" varStatus="vs">
                                    <!--<div class="dashang-list">-->
                                        <img style="width:30px;height:30px;" src="${reward.user.headImage}" onclick="href('api/userController/homePage?userId=${reward.user.id}');"/>
                                        <!--<div>￥${reward.rewardFee}</div>
                                    </div>-->
                                </c:forEach>
                            </div>
                            <!--<div style="color:#999;font-size:24px;display:inline-block;height:45px;line-height:45px;text-align:center;" class="dashang-more">
                                》
                            </div>-->
                        </div>
                    </div>
                </div>
            </div>
            <div class="comments">
            </div>
            <div class="home-content">
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    评论加载中
                </div>
            </div>
            <div class="cut" style="margin-bottom: 60px;"></div>
            <div id="fabuinfo" class="comment" style="font-size:14px;display:block; min-height:40px; background-color:#fff; border-top:1px solid #ddd; position:fixed;bottom:0;left:0;right:0;padding:5px 10px;z-index: 2;">
                <img src="${pageContext.request.contextPath}/wsale/images/xiaolian-icon.png" class="emotion" style="width:7%; vertical-align:top; margin-top: 4px;" />
                <!--<img src="${pageContext.request.contextPath}/wsale/images/add-icon.png" style="width:25px; vertical-align:middle;margin-left:5px;" />-->
                <!--<input type="text" placeholder="请输入" id="commentContent"/>-->
                <textarea placeholder="请输入" id="commentContent" rows="1" data-role="none"></textarea>
                <a id="sendBtn" style="float:right;display:inline-block;width:15%;height:28px; line-height:28px;color:#fff; background-color:#ff5700; font-size:14px; text-align:center; margin-top:2px;">发送</a>
                <div class="qqface">
                    <div class="qqface-list"></div>
                    <div class="face-tab">
                        <div class="face-img"><img src="${pageContext.request.contextPath}/wsale/images/face-icon.png" /></div>
                        <div class="pic-img"><img src="${pageContext.request.contextPath}/wsale/images/pic-icon.png" /> 照片</div>
                    </div>
                </div>
            </div>
        </div><!-- /content -->
        <jsp:include page="../template/comment_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var images;
        var loading = true;
        var currPage = 1;
        var rows = 10;
        var self = ${user.self};
        var $authDom = $(".topmore-list ul");
        var mp3List = [], durations = 0, progress;
        var isShare = ${isShare}, isCollect = ${isCollect};
        var replyComment = null, replyState = true;
        var categoryId = null;
        var st = 0, time = 59, timeInterval;
        $(function() {
            // 帖子关闭或删除返回首页
            if((!$.authOnOff && '${bbs.bbsStatus}' == 'BS02') || ${bbs.isDeleted}) {
                $('.ui-content').hide();
                $.alert("该帖子已关闭或已删除！", "系统提示", function(){
                    replace('api/apiHomeController/home');
                });
                return;
            }

            if($(".bbs-content").height() == $(".bbs-content").css('max-height').replace('px', '')) {
                $(".showMore").removeClass("hide");
            }

            $(".showMore").click(function(e){
                e.stopPropagation();
                e.preventDefault();
                var self = $(this);
                setTimeout(function() {
                    $('.bbs-content').addClass("fullDesc");
                    self.remove();
                }, 200);
            });

            $('audio').audioPlayer();

            document.title = '${title}';
            $(".lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
            authInit();

            $('.emotion').click(function(){
                if(!$('.face-tab').is(':hidden')) {
                    $('.cut').css('margin-bottom', '60px');
                } else {
                    $('.cut').css('margin-bottom', '135px');
                }
            }).qqFace({
                id : 'facebox', //表情盒子的ID
                assign:'commentContent', //给那个控件赋值
                path:'${pageContext.request.contextPath}/wsale/images/face/'	//表情存放的路径
            });

            // 点击其他部位关闭qq弹窗
            $("body:eq(0)").bind('click', function(e){
                var target = $(e.target);
                if(target.closest(".qqface").length == 0 && !$('.face-tab').is(':hidden')){
                    $('#facebox').hide();
                    $('#facebox').remove();
                    $(".face-tab").hide();
                    $('.cut').css('margin-bottom', '60px');
                }
            });

            $(".pic-img").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        ajaxPost('api/apiCommon/upload', {mediaId:serverId, type:'IMAGE', tag:'COMMENT'}, function(data){
                            if(data.success) {
                                addComment(data.obj.path, 'IMAGE');
                            }
                        });
                    });
                }, 1);
            });

            $('.emotion').click(function(){
                var reply = replyComment;
                if(reply != null) {
                    replyState = false;
                }
            });
            $("#fabuinfo").on('click', '#facebox table img', function(){
                var reply = replyComment;
                if(reply != null) {
                    replyState = false;
                }
            });


            if(${backCustom}) {
                //$("#back").removeAttr("data-rel").attr('href', getUrl('${pageContext.request.contextPath}/api/apiCategoryController/forum?id=${bbs.categoryId}'));
            }

            var items = [];
            $(".imageFiles div").each(function(){
                items.push($(this).attr("data-original"));
                //items.push('${pageContext.request.contextPath}/wsale/images/jsq-list2.png');
            });
            //images = $.photoBrowser({
            //    items: items
            //});
            $(".imageFiles div").click(function(){
                if('${subscribe}' == 0) {
                    $('.mask-layer, .subscribe').show();
                    addSubscribeLog();
                    return;
                }

                JWEIXIN.previewImage(items, $(this).index());
                //images.open($(this).index());
            });

            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawComments();
                }, 20);
            });
            drawComments(true);

            $("#sendBtn").bind("click", function(){
                if('${subscribe}' == 0) {
                    $('.mask-layer, .subscribe').show();
                    addSubscribeLog();
                    return;
                }
                addComment();
            });

            //$(".more-content li").click(moreFun);
            $('.guanzhu').click('${user.id}', attrFun);
            $('.collect').click(collectFun);

            $('.rewardBtn').bind('click', function(){
                if(self) {
                    $.toast("<font size='2'>不可以对自己打赏哟</font>", "text");
                    return;
                }
                //$(".mask-layer,.fensi-dialog").show();
                $('#rewardPopup').wePopup();
            });
            $('.otherAmount').click(function(){
                $.closePopup();
                $('#rewardOtherPopup').wePopup();
            });
            $('.money-list div').click(function(){
                var rewardFee = $(this).attr('data-fee');
                if(rewardFee) reward(rewardFee);
            });
            $('.reward').bind('click', function(){
                var rewardFee = $('#rewardFee').val();
                if(rewardFee) reward(rewardFee);
                else $('#rewardFee').focus();
            });
            $('.reportBtn').bind('click', report);

            $('.msgDiv').bind('click', function(){
                $.alert("请右上角转发朋友或分享朋友圈！", "系统提示");
            });

            /*$('#mp3List').children().each(function(){
                mp3List.push($(this).find('.path').text());
                durations += parseInt($(this).find('.duration').text());
                progress = ($('.msgDiv').width()-$('.playProgress').width())/durations;
            });

            if(mp3List.length > 0) {
                $('.playBtn').bind('click', playAudio);
                var totalDuration;
                durations = isNaN(durations) ? 0 : durations;
                if(durations < 60)
                    totalDuration = '00:' + (durations < 10 ? '0' + durations : durations);
                else {
                    var m = parseInt(durations/60), s = durations%60;
                    totalDuration = (m < 10 ? '0' + m : m) + ':' + (s < 10 ? '0' + s : s);
                }
                $('.totalDuration').html(totalDuration);
            }*/

            $('#commentContent').autoHeight(5.6*2);

            $("#commentContent").blur(function(){
                $(document.body).infinite();
                $(".home-content .weui-infinite-scroll").show();
                setTimeout(function(){
                    if(replyState) {
                        if(replyComment != null) replyComment = null;
                        $("#commentContent").attr('placeholder', '请输入');
                    }
                    replyState = true;
                }, 50);

            });

            $("#commentContent").focus(function(){
                $(document.body).destroyInfinite();
                $(".home-content .weui-infinite-scroll").hide();
                $.mobile.silentScroll($(document).height());
            });

            $(".first-style").click(function(){
                $('.second-title .fenlei-title').html($(this).attr("categoryName"));
                drawChildCategory($(this).attr("categoryId"));
            });

            $("#childCategory").on('click', '.secondstyle-list', function(){
                var _this = $(this);
                categoryId = _this.attr('categoryId');
            });

            $(".choose-ok").click(function(){
                if(categoryId != null && '${bbs.categoryId}' != categoryId) {
                    ajaxPost('api/bbsController/editBbs', {id : '${bbs.id}', categoryId : categoryId}, function(data){
                        if(data.success) {
                            $.toast("移动成功", function(){
                                window.location.reload();
                            });
                        }
                    });
                }
            });

            $(".subscribe").off('touchstart').on("touchstart", function (e) {
                e.stopPropagation();
                st = Date.now();
            });
            $(".subscribe").off('touchend').on("touchend", function (e) {
                e.stopPropagation();
                if(st > 0 && (Date.now() - st) < 300) {
                    $('.mask-layer').hide();
                    $(this).hide();
                }
            });

            $('.share-list li').click(function(){
                var _this = this, num = $(this).index();
                if(num == 0) {
                    openQrcode();
                } else if(num == 1) {
                    sendLink(function(){
                        $(_this).find('.msg').html('发送成功');
                    });
                } else {
                    $(_this).find('.msg').html('可以分享了');
                }
            });

            $('.makeQr').bind('click', openQrcode);

            $('.im-btn').click(function(){
                if($(this).parent().hasClass('btn-con-rg3')) {
                    $.alert("该帖子正在交易中或已交易完成！", "系统提示！");
                    return;
                }

                if(!$("#bindMobile").val()) {
                    $('#bindMobilePopup').wePopup();
                    return;
                }

                $('#imPopup').wePopup();
            });

            $('.intermediary').bind('click', intermediary);

            $('#vcode-btn').bind('click', sendVCode);
            $('#bindMobileBtn').bind('click', bindMobile);
        });

        function bindMobile() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码');
                return;
            }
            var vcode = $('#vcode').val();
            if(Util.checkEmpty(vcode)) {
                $.toptip('请输入验证码');
                return;
            }
            ajaxPost('api/userController/edit', {mobile : mobile, vcode : vcode}, function(data){
                if(data.success) {
                    $.toptip('绑定成功', 'success');
                    $("#bindMobile").val(mobile);
                    $.closePopup();
                } else {
                    $.toptip(data.msg);
                }
            });
        }

        function sendVCode() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码', 'error');
                $('#mobile').focus();
                return;
            }
            $('#vcode-btn').unbind('click').html('重发（<span id=\"time\">'+time+'</span>）');
            time--;
            timeInterval = setInterval(function(){
                $("#time").html(time);
                if(time == 0) {
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                } else {
                    time -- ;
                }
            }, 1000);

            ajaxPost('api/userController/sendVCode', {mobile:mobile, checkMobile:true}, function(data){
                if(data.success) {
                    $.toptip('验证码已发送至手机', 'success');
                } else {
                    $.toptip(data.msg, 'error');
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                }
            });

        }

        function intermediary() {
            var amount = $('#amount').val();
            if(Util.checkEmpty(amount) || amount == 0) {
                $('#amount').val('');
                $('#amount').focus();
                return;
            }

            $.closePopup();
            ajaxPost('api/apiIntermediary/addIntermediary', {bbsId:'${bbs.id}', sellUserId:'${bbs.addUserId}', amount: (amount*100).toFixed(0), remark:$('#remark').val()}, function(data) {
                if(data.success) {
                    $.alert("申请成功，等待卖家处理！", "系统提示！");
                } else {
                    $.alert(data.msg, "系统提示！");
                }
            }, function(){
                $.loading.load({type:3, msg:'正在提交...'});
            });
        }

        function openQrcode() {
            ajaxPost('api/userController/getQR', {content:removeQueDefault(location.href), objectId:'${bbs.id}', objectType:'BBS'}, function(data) {
                if(data.success) {
                    try{
                        JWEIXIN.previewImage([data.obj + "?t=" + new Date().getTime()]);
                    } catch (e){}

                }
            }, function(){
                $.loading.load({type:3, msg:'正在打开...'});
            });
        }

        function sendLink(callback) {
            ajaxPost('api/apiCommon/sendLink', {openid : '${sessionInfo.name}', link:removeQueDefault(location.href)}, function(data) {
                if(data.success && callback) {
                    callback();
                }
            });
        }

        function addSubscribeLog(){
            ajaxPost('api/apiCommon/addSubscribeLog', {objectType : 'BBS', objectId : '${bbs.id}'}, function(data) {
                if(data.success) {
                }
            });
        }

        function drawChildCategory(pid) {
            $("#childCategory").empty();
            ajaxPost('api/apiCategoryController/categorys', {pid : pid}, function(data){
                if(data.success) {
                    var result = data.obj;
                    for(var i in result) {
                        $("#childCategory").append('<div class="secondstyle-list" pid="'+pid+'" categoryId="'+result[i].id+'">'+result[i].name+'</div>');
                    }
                    $(".dialog-content").hide();
                    $(".second-style").show();
                }
            });
        }

        //play
        var index = 0;
        var oAudio = document.getElementById('audio');
        var played, playtime = 0;
        function playAudio() {
            if(!isShare && !self) {
                $.alert("您先分享给朋友才可以收听哟！", "系统提示！");
                return;
            }
            if(window.HTMLAudioElement) {
                try {
                    if(oAudio.paused) {
                        if(oAudio.currentSrc == '') oAudio.src = mp3List[index];
                        oAudio.play();
                        played = setInterval(listened, 1000);
                    } else {
                        oAudio.pause();
                        clearInterval(played);
                    }

                    oAudio.onended = function() {
                        clearInterval(played);
                        index ++;
                        if(index != mp3List.length) {
                            oAudio.src = mp3List[index];
                            oAudio.play();
                            played = setInterval(listened, 1000);
                        } else {
                            oAudio.src = '';
                            index = 0;
                            playtime = 0;
                            $('.playProgress').css('left', '0px');
                            $('.listened').html($('.totalDuration').html());
                        }
                    };
                } catch (e) {
                    if(window.console && console.error("Error:" + e));
                }
            }
        }

        function listened() {
            if(playtime == durations) {
                clearInterval(played);
            }
            var listened;
            if(playtime < 60)
                listened = '00:' + (playtime < 10 ? '0' + playtime : playtime);
            else {
                var m = parseInt(playtime/60), s = playtime%60;
                listened = (m < 10 ? '0' + m : m) + ':' + (s < 10 ? '0' + s : s);
            }
            $('.playProgress').css('left', playtime*progress + 'px');
            $('.listened').html(listened);
            playtime ++;
        }

        //查看结果
        function replace_em(str){
            str = str.replace(/\</g,'&lt;');
            str = str.replace(/\>/g,'&gt;');
            str = str.replace(/\n/g,'<br/>');
            str = str.replace(/\[em_([0-9]*)\]/g,'<img src="${pageContext.request.contextPath}/wsale/images/face/$1.gif" border="0" />');
            return str;
        }

        function authInit() {
            if(self) $authDom.append('<li class="edit"><a>编辑</a></li>');
            if($.authTzsc) $authDom.append('<li name="isDeleted" data-value="1"><a>删除</a></li>');
            if(!self && $.authJy) {
                if(${user.isGag}) $authDom.append('<li class="gag" data-value="0"><a>解除禁言</a></li>');
                else $authDom.append('<li class="gag" data-value="1"><a>禁言</a></li>');
            }
            if($.authOnOff) {
                if('${bbs.bbsStatus}' == 'BS01') $authDom.append('<li name="bbsStatus" data-value="BS02"><a>关闭</a></li>');
                else $authDom.append('<li name="bbsStatus" data-value="BS01"><a>打开</a></li>');
            }
            if($.authZd) {
                if(${bbs.isTop}) $authDom.append('<li name="isTop" data-value="0"><a>取消置顶</a></li>');
                else $authDom.append('<li name="isTop" data-value="1"><a>置顶</a></li>');
            }
            if($.authJl) {
                if(${bbs.isLight}) $authDom.append('<li name="isLight" data-value="0"><a>取消加亮</a></li>');
                else $authDom.append('<li name="isLight" data-value="1"><a>加亮</a></li>');
            }
            if($.authJJ) {
                if(${bbs.isEssence}) $authDom.append('<li name="isEssence" data-value="0"><a>取消加精</a></li>');
                else $authDom.append('<li name="isEssence" data-value="1"><a>加精</a></li>');
            }
            if($.authTzyd) $authDom.append('<li class="bbsMove"><a>移动</a></li>');

            if(isCollect) $authDom.append('<li class="collect collected"><a class="collect-alt">取消收藏</a></li>');
            else $authDom.append('<li class="collect"><a class="collect-alt">收藏</a></li>');

            if(!self) $authDom.append('<li class="report"><a>举报</a></li>');
            if($.authGbhf) {
                var isOffReply = 1;
                if(${bbs.isOffReply}) {
                    isOffReply = 0;
                    $(".isOffReply").html("打开回复");
                } else {
                    $(".isOffReply").html("关闭回复");
                }
                $(".isOffReply").click(function(){
                    var data = {};
                    data['isOffReply'] = isOffReply;
                    editBbs(data);
                });
            } else {
                if(${bbs.isOffReply})
                    $(".isOffReply").html("回复已关闭");
                else {
                    $(".isOffReply").hide();
                    $('.isOffReply').parent().next().css('padding-top', '5px');
                }
            }
            $authDom.find(".edit").click(function(){
                // 跳转编辑页
                href('api/bbsController/toEdit?id=${bbs.id}');
            });
            $authDom.find("li[name]").click(function(){
                var data = {};
                data[$(this).attr("name")] = $(this).attr("data-value");
                editBbs(data);
            });
            $authDom.find(".gag").click(function(){
                var isGag = $(this).attr("data-value");
                ajaxPost('api/userController/edit', {id:'${user.id}', isGag:isGag, bbsId:'${bbs.id}'}, function(data){
                    if(data.success) {
                        window.location.reload();
                    }
                });
            });
            $authDom.find('.bbsMove').click(function(){
                setTimeout(function(){
                    $(".mask-layer,.dialog-content").show();
                    categoryId = null;
                }, 20);

            });
            $authDom.find(".report").click(function(){
                $('#reportPopup').wePopup();
            });
            $authDom.click(function(){
                $('.mask-layer').hide();
            });
        }

        function attrFun(event) {
            window.event.stopPropagation();
            var _this = this, userId = event.data;
            ajaxPost('api/userController/addShieldorfans', {objectType:'FS', userId:userId}, function(data){
                if(data.success) {
                    $(_this).remove();
                    $('div[data-user-id='+userId+']').remove();
                }
            });
        }

        function collectFun() {
            var url = 'api/apiPoint/addCollect',_this = this;
            if($(_this).hasClass('collected')) {
                url = 'api/apiPoint/cancelCollect';
            }
            ajaxPost(url, {objectType:'BBS', objectId:'${bbs.id}'}, function(data){
                if(data.success) {
                    if($(_this).hasClass('collected')) {
                        if($('.collect').is('img')) {
                            $('.collect').attr('src', base + 'wsale/images/guanzhu-icon.png');
                        }
                        $('.collect').removeClass('collected');
                        $('.collect-alt').html('收藏');

                        $.toast("已取消收藏");
                    } else {
                        if($('.collect').is('img')) {
                            $('.collect').attr('src', base + 'wsale/images/yiguanzhu-icon.png');
                        }
                        $('.collect').addClass('collected');
                        $('.collect-alt').html('取消收藏');
                        $.toast("已收藏");
                    }
                }
            });
        }

        function report() {
            var reportReason = $('#reportReason').val();
            if(Util.checkEmpty(reportReason)) {
                $('#reportReason').focus();
                return;
            }
            ajaxPost('api/reportController/report', {objectType:'BBS', objectId:'${bbs.id}', reportReason:reportReason}, function(data){
                $.closePopup();
                if(data.success) {
                    $.toast("举报成功");
                } else {
                    $.toast("<font size='1'>"+data.msg+"</font>", "text");
                }
            });
        }

        function editBbs(data) {
            var isDeleted = typeof(data['isDeleted']) != "undefined";
            if(isDeleted) {
                $.confirm("您确定要删除该帖子吗?", "系统提示", function() {
                    edit(data, isDeleted);
                }, function() {
                    //取消操作
                });
            } else {
                edit(data);
            }

        }

        function edit(data, isDeleted) {
            var params = {id : '${bbs.id}'};
            params = $.extend(true, params, data || {});
            console.log(params);
            ajaxPost('api/bbsController/editBbs', params, function(data){
                if(data.success) {
                    if(isDeleted) {
                        replace('api/apiCategoryController/forum?id=${bbs.categoryId}');
                    } else {
                        window.location.reload();
                    }
                }
            });
        }

        function drawComments(load) {
            var params = {bbsId : '${bbs.id}', page:currPage, rows:rows};
            ajaxPost('api/bbsController/bbsComments', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    for(var i in result.rows) {
                        var comment = result.rows[i];
                        var floor = (currPage-1)*rows + (parseInt(i)+2);
                        buildComment(comment, floor)
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
            }, function(){
                //if(load) $.loading.load({type:2});
            });
        }

        function buildComment(comment, floor) {
            var viewData = Util.cloneJson(comment);
            viewData.addtime = Util.getTime(comment.addtime) + '&nbsp;'+floor+'楼';
            if('${bbs.bbsType}' == 'BT02') {
                viewData.user.nickname = '鉴定师' + (floor-1);
                viewData.user.headImage = base + 'wsale/images/touxiang-img.png';
            } else
                viewData.user.positionIcon = base + 'wsale/images/p-'+viewData.user.positionId+'.png';

            if(comment.ctype == 'IMAGE') {
                viewData.comment = '<img src="'+comment.comment+'" class="imageMsg" style="max-width:50%;" /><br>';
            } else {
                viewData.comment = WebIM.utils.parseEmoji(viewData.comment.replace(/[\r\n]/g, "<br/>"));
            }

            if(comment.pid) {
                var pComment = '';
                if(comment.parentComment.isDeleted)
                    pComment = '<font style="color: #aaa;">该条评论已被删除</font>';
                else {
                    if(comment.parentComment.ctype == 'IMAGE') {
                        pComment = '<br><img src="'+comment.parentComment.comment+'" class="imageMsg" style="max-width:50%;"/>';
                    } else {
                        pComment = WebIM.utils.parseEmoji(comment.parentComment.comment.replace(/[\r\n]/g, "<br/>"));
                    }
                }

                viewData.comment = viewData.comment + ' <font style="color: blue;">//@' + comment.parentComment.user.nickname + '</font>:' + pComment;
                //viewData.comment = '<font style="color: blue;">回复 ' + comment.parentComment.user.nickname + '</font>：' + comment.comment;
            }

            var dom = Util.cloneDom("comment_template", comment, viewData);
            //if(self || '${sessionInfo.id}' == comment.userId || $.authPlsc) dom.find('.del').show();
            if($.authPlsc) dom.find('.del').show();
            $(".comments").append(dom);
            if('${bbs.bbsType}' == 'BT02') {
                dom.find('.bbsNums').remove();
            } else {
                if(!comment.user.attred && !comment.user.self) {
                    dom.find('.attBtn').attr('data-user-id', comment.user.id).show();
                    dom.find('.attBtn').click(comment.user.id, attrFun);
                }
            }
            // dom绑定事件
            dom.find("div.del").click(comment.id, function(event){
                var _this = this;
                $.confirm("是否删除该条评论?", "系统提示", function() {
                    ajaxPost('api/bbsController/delBbsComment', {id : event.data}, function(data){
                        if(data.success) {
                            $(_this).parent().parent().remove();
                            $('.bbsComment').text(parseInt($('.bbsComment').text()) - 1);
                        }
                    });
                });
            });
            dom.find("img.lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            }).click(comment.user.id, function(event){
                if('${bbs.bbsType}' == 'BT02') return;
                href('api/userController/homePage?userId=' + event.data);
            });

            dom.find('.reply').click(comment, function(event){
                if('${bbs.bbsType}' != 'BT02' && replyComment == null) {
                    replyComment = event.data;
                    $("#commentContent").attr("placeholder", "回复 " + replyComment.user.nickname).focus();
                }
            });

            dom.find('.imageMsg').click(function(){
                event.stopPropagation();
                var imageUrls = [$(this).attr('src')];
                JWEIXIN.previewImage(imageUrls);
            });
        }

        function addComment(comment, type) {
            var reply = replyComment;
            type = type || 'TEXT';
            comment = comment || $.trim($("#commentContent").val());
            $("#commentContent").val('');
            $('#facebox').hide();
            $('#facebox').remove();
            $(".face-tab").hide();
            if(Util.checkEmpty(comment)) {
                return;
            }

            if(${sessionInfo.isGag}) {
                $.toast("你已被禁言", "forbidden");
                return;
            }

            if(${bbs.isOffReply}) {
                $.toast("回复已关闭", "forbidden");
                return;
            }
            if('${bbs.bbsType}' == 'BT02' && !$.authJdhf) {
                $.alert("您不能鉴定该帖子哟！", "系统提示！");
                return;
            }


            var params = {bbsId : '${bbs.id}', comment:comment, ctype:type};
            if(reply != null) {
                params.pid = reply.id;
                params.fid = reply.fid || reply.id;
            }
            ajaxPost('api/bbsController/addBbsComment', params, function(data){
                if(data.success) {
                    var len = $(".comments").children().length;
                    if(len == 0 || len%rows != 0) {
                        buildComment(data.obj, len+2);
                    } else {
                        $(document.body).infinite();
                        $(".home-content .weui-infinite-scroll").show();
                    }
                    $('.bbsComment').text(parseInt($('.bbsComment').text()) + 1);

                    if(replyComment != null) replyComment = null;
                    $("#commentContent").attr('placeholder', '请输入');

                    $.toast("回复成功", "text");
                }
            });
        }

        var reg = /^[0-9]*(\.[0-9]{1,2})?$/;
        function reward(rewardFee) {
//            var rewardFee = $('#rewardFee').val();
//            if(Util.checkEmpty(rewardFee) || !reg.test(rewardFee)) {
//                $.toptip("金额格式不正确");
//                return;
//            }
            ajaxPost('api/apiReward/reward', {objectId : '${bbs.id}',objectType:'BBS', rewardFee:rewardFee*100}, function(data){
                if(data.success) {
                    href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO04&attachType=BBS&totalFee=' + rewardFee);
                }
            });
        }

        function pay(objectId, rewardFee) {
            $.modal({
                title: "打赏",
                text: "本次打赏金额为" + parseFloat(rewardFee).toFixed(2) + "元！",
                buttons: [
                    { text: "微信支付", onClick: function(){
                        var params = {totalFee : rewardFee,objectType:'PO04',objectId:objectId, channel:'CS01'};
                        wxPayCall(params, function(){
                            $('.bbsReward').text(parseInt($('.bbsReward').text()) + 1);
                            //$('.dashang-user').prepend('<div class="dashang-list"><img style="width:35px;height:35px;" src="${sessionInfo.headImage}" /><div>￥'+rewardFee+'</div></div>');
                            $('.dashang-user').prepend('<img style="width:35px;height:35px;" src="${sessionInfo.headImage}" />');
                            if($('.dashang-sanjiao').is(':hidden')) {
                                $('.dashang-sanjiao, .dashang-content').show();
                            }
                            $.toast("非常感谢");
                        });
                    } },
                    { text: "其他", onClick: function(){
                        $.alert("主人，人家还没准备好", function(){
                            pay(objectId, rewardFee);
                        });

                    } },
                    { text: "取消", className: "default"}
                ]
            });
        }

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                //title:"『${sessionInfo.nickname}』转发了一条有趣的臻藏艺术帖子，快来围观！",
                //desc:"${bbs.bbsTitle}",
                title:"${bbs.bbsTitle}",
                desc:$('#bbsContent').val(),
                link:removeQueDefault(location.href) + "&fromShare=1",
                imgUrl:$(".imageFiles div:eq(0)").attr("data-original")
            };
            JWEIXIN.onMenuShareAppMessage(shareData, addShare);
            JWEIXIN.onMenuShareTimeline(shareData, addShare);
            JWEIXIN.onMenuShareQQ(shareData, addShare);
            JWEIXIN.onMenuShareWeibo(shareData, addShare);
            JWEIXIN.onMenuShareQZone(shareData, addShare);
        });

        function addShare(channel) {
            ajaxPost('api/bbsController/addShare', {bbsId : '${bbs.id}', shareChannel:channel}, function(data){
                if(data.success) {
                    //isShare = true;
                    $('.bbsShare').text(parseInt($('.bbsShare').text()) + 1);
                    //$('.msgDiv').html('收听录音');
                    $('.msgDiv').hide();
                    $('.audioPlay').show();
                }
            });
        }
    </script>
</body>

</html>
