<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${user.self}">我的主页</c:when>
            <c:otherwise>『${user.nickname}』的主页</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/qrcode.js" charset="utf-8"></script>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="mask-layer" style="z-index: 1001;"></div>
            <div class="guanzhu-dialog" style="z-index: 1002;bottom: 0px;">
                <div>
                    <c:choose>
                        <c:when test="${shieldored}">
                            <a class="pingbi-user pingbi-current shieldored">取消屏蔽该用户</a>
                        </c:when>
                        <c:otherwise>
                            <a class="pingbi-user pingbi-current">屏蔽该用户</a>
                        </c:otherwise>
                    </c:choose>
                    <a class="pingbi-user" href="javascript:href('api/userController/shieldors');">查看所有屏蔽用户</a>
                </div>
                <a class="pingbi-cancel">取消</a>
            </div>
            <div class="fensi-dialog" style="z-index: 1002;bottom: 0px;">
                <div class="msg"></div>
                <a class="bottom-btn guanzhu-cancel">知道了</a>
            </div>

            <div class="home-content" style="margin:0; ">
                <!--<div id="qrcode" style="display: none;"></div>-->
                <div class="xinxi-topInfo">
                    <c:if test="${!user.self}"><div class="zhuye-guanzhu"><img src="${pageContext.request.contextPath}/wsale/images/quxiaoguanzhu-icon.png" /></div></c:if>
                    <img src="${user.headImage}" class="wode-userimg xinxi-bgimg" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'"  onclick="href('api/apiShop/shop?userId=${user.id}');"/>
                    <img src="${pageContext.request.contextPath}/wsale/images/p-${user.positionId}.png" class="wode-userflag" />
                    <div class="xinxi-username">${user.nickname}</div>
                    <div class="xinxi-userinfo">
                        <div>${not empty user.bardian ? user.bardian : '这个人很懒，什么也没说'}</div>
                        <c:choose>
                            <c:when test="${user.self}">
                                <div>主题-回复：${commentNums}</div>
                                <div class="xinxi-userlink">
                                    <span onclick="href('api/apiShop/shop');"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/liulandianpu-icon.png" /> 浏览店铺</span>
                                    <span id="sendLinkBtn"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/lianjie-icon.png" /> 店铺链接</span>
                                    <span class="openQcode"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/erweima-icon.png" /> 二维码</span>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div><span>粉丝：${user.fans}</span> &nbsp;&nbsp;&nbsp;<span>所在地区：${user.area}</span></div>
                                <div class="xinxi-userlink">
                                    <span onclick="href('api/apiShop/shop?userId=${user.id}');"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/liulandianpu-icon.png" /> 浏览店铺</span>
                                    <span class="openQcode"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/erweima-icon.png" /> 二维码</span>
                                    <span onclick="href('api/apiChat/chat?toUserId=${user.id}&subscribe=true');"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/sixin-white.png" /> 私信</span>
                                    <c:choose>
                                        <c:when test="${user.attred}">
                                            <span class="pingbi-btn attred"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/quxiaoguanzhu-icon.png" /> <msg>取消关注</msg></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="pingbi-btn"><img class="xinxi-usericon" src="${pageContext.request.contextPath}/wsale/images/quxiaoguanzhu-icon.png" /> <msg>关注</msg></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="xinxi-level">
                    <div><span class="xinxi-leveltext">买家等级</span> <img src="${pageContext.request.contextPath}/wsale/images/v2.png" /></div>
                    <div class="xinxi-borderleft"><span class="xinxi-leveltext">卖家等级</span> <img src="${pageContext.request.contextPath}/wsale/images/v2.png" /></div>
                </div>
                <div class="xinxi-zhibiao">
                    <div class="xinxi-three">
                        <div class="xinxi-leveltext">信誉</div>
                        <div class="xinxi-leveltext xinxi-value">${order_status_count.OS10}</div>
                    </div>
                    <div class="xinxi-three xinxi-borderleft">
                        <div class="xinxi-leveltext">桃子</div>
                        <div class="xinxi-leveltext xinxi-value">100</div>
                    </div>
                    <div class="xinxi-three xinxi-borderleft">
                        <div class="xinxi-leveltext">违约</div>
                        <div class="xinxi-leveltext xinxi-value">${order_status_count.S_OS15 + order_status_count.B_OS15}</div>
                    </div>
                </div>
                <div class="xinxi-renzhengInfo">
                    <div class="xinxi-list">
                        <div class="xinxi-lefttext">实名认证</div>
                        <c:choose>
                            <c:when test="${user.isAuth}">
                                <img class="xinxi-renzhengicon" src="${pageContext.request.contextPath}/wsale/images/renzheng2-icon.png" /> <span>已通过个人认证</span>
                            </c:when>
                            <c:otherwise>未认证</c:otherwise>
                        </c:choose>

                    </div>
                    <div class="xinxi-list">
                        <div class="xinxi-lefttext">消保金</div>
                        <c:if test="${isPayBond}"><img class="xinxi-renzhengicon" src="${pageContext.request.contextPath}/wsale/images/baozhengjin-yellow.png" /></c:if>
                        <span>${protectionMsg}</span>
                    </div>
                    <c:if test="${user.isAuth}">
                        <div class="xinxi-list">
                            <div class="xinxi-lefttext">店铺介绍</div> <span>${shop.introduction}</span>
                        </div>
                    </c:if>

                </div>
                <div class="xinxi-ppzt">
                    <a class="faxian-link">
                        <div class="list-right" <c:if test="${productThemes.total >= 4}">onclick="href('api/apiShop/shop?userId=${user.id}');"</c:if>>
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
                        <div class="list-right" <c:if test="${textThemes.total >= 2}">onclick="href('api/userController/moreBbsTheme?themeType=TEXT&userId=${user.id}');"</c:if>>
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
                                            <div class="wenzizhuti-flag">
                                                <img src="${pageContext.request.contextPath}/wsale/images/jsp-icon2.png" />
                                            </div>
                                            <div class="tiezi-title blue-title">${textTheme.bbsTitle}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="tiezi-title">${textTheme.bbsTitle}</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="wzzt-info">
                                        <div style="float: right;" class="time">${textTheme.addtime}</div>
                                        <div class="info-xinxi">发帖人:${textTheme.addUserName}</div>
                                        <div>回复：${textTheme.bbsComment} &nbsp;&nbsp;围观：${textTheme.bbsRead}</div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>

                <div class="xinxi-ppzt">
                    <a class="faxian-link">
                        <div class="list-right" <c:if test="${audioThemes.total >= 2}">onclick="href('api/userController/moreBbsTheme?themeType=AUDIO&userId=${user.id}');"</c:if>>
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
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
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

            $("#sendLinkBtn").click(sendLinkBtn);

            $('.openQcode').click(openQrcode);

            // 设置参数方式
            //var qrcode = new QRCode('qrcode', {
            //    width: 110,
            //    height: 110
            //});
            // 使用 API
           //qrcode.clear();
            //qrcode.makeCode(removeTokenId(location.href));

            $('.pingbi-btn').click(function(){
                var url = 'api/userController/addShieldorfans', _this = this;
                if($(_this).hasClass('attred')) {
                    url = 'api/userController/delShieldorfans';
                    $.confirm("是否取消对该用户的关注?", "系统提示", function() {
                        attrFun(url, 'FS', function(){
                            $(_this).removeClass('attred');
                            $(_this).find('msg').html('关注');
                        });
                    }, function() {});
                } else {
                    attrFun(url, 'FS', function(){
                        $(_this).addClass('attred');
                        $(_this).find('msg').html('取消关注');
                    });
                }
            });

            $('.pingbi-current').click(function(){
                var url = 'api/userController/addShieldorfans', _this = this;
                if($(_this).hasClass('shieldored')) {
                    url = 'api/userController/delShieldorfans';
                    attrFun(url, 'SD', function(){
                        $('.guanzhu-dialog').hide();
                        $('.fensi-dialog').show();
                        $('.fensi-dialog .msg').html('取消屏蔽该用户成功！');
                        $(_this).removeClass('shieldored');
                        $(_this).html('屏蔽该用户');
                    });
                } else {
                    attrFun(url, 'SD', function(){
                        $('.guanzhu-dialog').hide();
                        $('.fensi-dialog').show();
                        $('.fensi-dialog .msg').html('屏蔽该用户成功！');
                        $(_this).addClass('shieldored');
                        $(_this).html('取消屏蔽该用户');
                    });
                }
            });
        });

        function attrFun(url, type, callback) {
            ajaxPost(url, {objectType:type, userId:'${user.id}'}, function(data){
                if(data.success && callback) {
                    callback();
                }
            });
        }

        function sendLinkBtn() {
            ajaxPost('api/apiCommon/sendLink', {openid : '${user.name}', link:removeQueDefault(location.href)}, function(data) {
                if(data.success) {
                    $.toast("发送成功", "text");
                }
            });
        }

        function openQrcode() {
            ajaxPost('api/userController/getQR', {content:removeQueDefault(location.href), objectId:'${user.id}', objectType:'USER'}, function(data) {
                if(data.success) {
                    try{
                        JWEIXIN.previewImage([data.obj + "?t=" + new Date().getTime()]);
                    } catch (e){}

                }
            }, function(){
                $.loading.load({type:3, msg:'正在打开...'});
            });
        }

        /*function openQrcode() {
            var qrcodeSrc = $("#qrcode img").attr('src');
            var canvas = document.createElement("canvas");
            var ctx = canvas.getContext('2d');

            var img = new Image();
            img.src = qrcodeSrc;
            img.onload = function () {
                ctx.drawImage(img, 0, 0);
            };

            setTimeout(function(){
                var dataUrl = canvas.toDataURL("image/png");
                var imageUrls = [dataUrl];
                JWEIXIN.previewImage(imageUrls);
            }, 200);

        }*/

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                //title:"臻藏艺术个人主页",
                //desc:"『${user.nickname}』的臻藏艺术个人主页",
                title:"『${user.nickname}』的集东集西个人主页",
                desc : "",
                link:removeQueDefault(location.href),
                imgUrl:'${user.headImage}'
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
