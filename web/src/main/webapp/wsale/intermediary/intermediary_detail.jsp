<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>中介交易详情</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">

            <div id="refusePopup" class="weui-popup-container">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="overflow: hidden;">
                    <div class="modal-content" style="padding-top: 0; margin-top: 0px; overflow: hidden;">
                        <div style="background-color:#fff; padding: 0 5px;border-bottom:1px solid #ddd;">
                            <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" class="refusePopupBtn">
                                确 认
                            </div>
                            <div style="width:80%; padding: 10px;" class="close-popup">
                                <span style="padding: 10px 0px;">关 闭</span>
                            </div>
                        </div>
                        <textarea style="margin:10px 0px; background-color: #fff;" maxlength="100" placeholder="请输入拒绝原因..." id="content"></textarea>
                    </div>
                </div>
            </div>

            <div class="home-content" style="margin:0; text-align:left; ">
                <div class="process">
                    <c:forEach items="${intermediary.logs}" var="log" varStatus="vs">
                        <a class="shouhuo-content">
                            <div class="pingjia-img">
                                <c:choose>
                                    <c:when test="${log.logType == 'IL01'}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jingpai-icon.png" />
                                    </c:when>
                                    <c:when test="${log.logType == 'IL02'}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/jingpaichenggong-icon.png" />
                                    </c:when>
                                    <c:when test="${log.logType == 'IL03'}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/fail-icon.png" />
                                    </c:when>
                                    <c:when test="${log.logType == 'IL04'}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/fail-icon.png" />
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="pingjia-text">
                                <span class="pingjia-status">${log.logTypeZh}</span>
                                <div class="pingjia-time">
                                    <c:choose>
                                        <c:when test="${log.logType == 'IL01'}">
                                            创建时间：
                                        </c:when>
                                        <c:when test="${log.logType == 'IL02'}">
                                            同意时间：
                                        </c:when>
                                        <c:when test="${log.logType == 'IL03'}">
                                            拒绝时间：
                                        </c:when>
                                        <c:when test="${log.logType == 'IL04'}">
                                            取消时间：
                                        </c:when>
                                    </c:choose>
                                    <fmt:formatDate value="${log.addtime}" pattern="yyyy-MM-dd HH:mm"/>
                                </div>
                                <c:if test="${log.logType == 'IL03' and !empty log.content}">
                                    <div class="pingjia-time">
                                        拒绝理由：${log.content}
                                    </div>
                                </c:if>
                            </div>
                        </a>
                    </c:forEach>
                </div>
                <div class="dingdan-product">
                    <div class="faxian-link">
                        <div class="dingdan-right">
                            <a style="display: inline;padding:10px 0 10px 5px" href="tel:${intermediary.imUser.mobile}"><img class="phone-icon" src="${pageContext.request.contextPath}/wsale/images/phone-icon.png" /></a>
                            <span class="status-text">
                                ${intermediary.statusZh}
                            </span>
                        </div>
                        <div class="normal-text" onclick="href('api/userController/homePage?userId=${intermediary.imUser.id}')">
                            <img class="dianpu-icon" src="${pageContext.request.contextPath}/wsale/images/dianpu-icon.png" />
                            ${intermediary.imUser.nickname}(<c:choose><c:when test="${intermediary.isBuyer}">卖家</c:when><c:otherwise>买家</c:otherwise></c:choose>)
                            <img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                    </div>
                    <div class="dingdan-content" onclick="href('api/bbsController/bbsDetail?id=${intermediary.bbsId}')">
                        <div class="dingdan-img">
                            <img src="${intermediary.bbs.icon}" />
                        </div>
                        <div class="dingdan-content-flex">
                            <div class="dingdan-title" style="-webkit-line-clamp:1">
                                ${intermediary.bbs.bbsTitle}
                            </div>
                            <div class="dingdan-info">
                                <div>交易金额：￥<fmt:formatNumber type="number" value="${intermediary.amount/100}" pattern="0.00" maxFractionDigits="2"/></div>
                                <c:choose>
                                    <c:when test="${intermediary.status == 'IS01'}">
                                        <div>申请时间：<fmt:formatDate value="${intermediary.lastLog.addtime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                        <div class="agreeEnd"></div>
                                    </c:when>
                                    <c:when test="${intermediary.status == 'IS02'}">
                                        <div>同意时间：<fmt:formatDate value="${intermediary.lastLog.addtime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </c:when>
                                    <c:when test="${intermediary.status == 'IS03'}">
                                        <div>取消时间：<fmt:formatDate value="${intermediary.lastLog.addtime}" pattern="yyyy-MM-dd HH:mm"/></div>
                                        <div>
                                            取消原因：${intermediary.lastLog.logTypeZh}
                                            <c:if test="${!empty intermediary.lastLog.content}">
                                                - ${intermediary.lastLog.content}
                                            </c:if>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="paipin-guanli dingdan-opearte">
                        <c:if test="${intermediary.status == 'IS01'}">
                            <c:choose>
                                <c:when test="${intermediary.isBuyer}">
                                    <li class="cancelBtn">取消交易</li>
                                </c:when>
                                <c:otherwise>
                                    <li class="agreeBtn">同意交易</li>
                                    <li class="refuseBtn">拒绝交易</li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
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
        var intermediaryId = '${intermediary.id}';
        $(function(){
            $('.process a:first').removeClass('shouhuo-content').addClass('pingjia-content');
            if($('.agreeEnd').length > 0) {
                var time = '${intermediary.lastLog.addtime}';
                if(time.length > 19)
                    time = time.substring(0, 19);
                var agreeDownTime = (new Date(time.replace(/-/g,"/")).getTime() + 72*60*60*1000) - new Date().getTime();
                addTimer($('.agreeEnd'), agreeDownTime/1000, '同意截止');
            }

            $('.cancelBtn').click(cancelFun);
            $('.agreeBtn').click(agreeFun);
            $('.refuseBtn').click(refuseFun);

            $('.refusePopupBtn').bind('click', refuseIM);
        });

        // 取消交易
        function cancelFun() {
            var $p = $(this).closest('.dingdan-list');
            $.confirm("您确认要取消该中介交易?", "系统提示", function() {
                ajaxPost('api/apiIntermediary/cancelIM', {id:intermediaryId}, function(data){
                    if(data.success) {
                        $.toast("取消成功", function(){
                            window.location.reload();
                        });
                    }
                });
            }, function() {});
        }

        function agreeFun() {
            $.confirm("确认后生成订单，是否同意?", "系统提示", function() {
                ajaxPost('api/apiIntermediary/agreeIM', {id:intermediaryId}, function(data){
                    if(data.success) {
                        $.toast("已同意", function(){
                            window.location.reload();
                        });
                    }
                });
            }, function() {});
        }

        function refuseFun() {
            $.confirm("本次操作拒绝交易，是否继续?", "系统提示", function() {
                $('#refusePopup').wePopup();
            });
        }

        function refuseIM() {
            var content = $.trim($('#content').val());
            if(Util.checkEmpty(content)) {
                $.toptip("请输入拒绝原因");
                return;
            }

            $.closePopup();
            ajaxPost('api/apiIntermediary/refuseIM', {id:intermediaryId, content:content}, function(data){
                if(data.success) {
                    $.toast("已拒绝", function(){
                        window.location.reload();
                    });
                }
            });
        }

        var addTimer = (function () {
            var list = [], interval;

            return function (dom, time, msg) {
                if (!interval)
                    interval = setInterval(go, 1000);
                list.push({ ele: dom, time: time, msg: msg });
                go();
            };

            function go() {
                for (var i = 0; i < list.length; i++) {
                    var dom = list[i].ele, time = list[i].time, msg = list[i].msg;
                    dom.html(getTimerString(time ? list[i].time -= 1 : 0, msg));
                    if (!time)
                        list.splice(i--, 1);
                }
            }

            function getTimerString(time, msg) {
                var h = Math.floor(time / 3600),
                        m = Math.floor(((time % 86400) % 3600) / 60),
                        s = Math.floor(((time % 86400) % 3600) % 60);
                m = m < 10 ? '0' + m : m, s = s < 10 ? '0' + s : s;
                if (time > 0)
                    return msg + '：<span class="nopay-money">'+h+'</span>时<span class="nopay-money">'+m+'</span>分<span class="nopay-money">'+s+'</span>秒';
                else return "交易关闭";
            }
        }) ();

    </script>
</body>

</html>
