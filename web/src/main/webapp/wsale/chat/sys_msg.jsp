<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>系统消息</title>
    <jsp:include page="../inc.jsp"></jsp:include>

</head>
<body>
    <div data-role="page" data-title="系统消息" class="jqm-demos" style="background-color:#f5f5f5;">
    
        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="sysMsgList">
                </div>
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    正在加载中
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
        var loading = true, currPage = 1, rows = 5;
        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawSysMsg();
                }, 20);
            });

            drawSysMsg();

            $(".sysMsgList").on('click', '.sysinfo-more', function(){
                var $img = $(this).find('img');
                var infoflag = $img.attr("data-flag");
                if(infoflag == "down"){
                    $(this).parent().find(".hide-info").show();
                    $img.attr("data-flag","up");
                    $img.attr("src",base + "wsale/images/up-icon.png");
                }
                if(infoflag == "up"){
                    $(this).parent().find(".hide-info").hide();
                    $img.attr("data-flag","down");
                    $img.attr("src",base + "wsale/images/down-icon.png");
                }
            });
        });

        function drawSysMsg() {
            ajaxPost('api/apiSysMsg/sysMsgList', {page:currPage, rows:rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var sysMsg = result.rows[i];
                            buildSysMsg(sysMsg);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".sysMsgList").append(Util.noDate(1, '消息是空的<br>这里为您呈现历史交易消息'));
                    }
                    if(result.rows.length >= rows) {
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }
                } else {
                    $(document.body).destroyInfinite();
                    $(".home-content .weui-infinite-scroll").hide();
                }
            });
        }

        function buildSysMsg(sysMsg) {
            var viewData = Util.cloneJson(sysMsg);
            viewData.newtime = new Date(sysMsg.newtime.replace(/-/g,"/")).format('yyyy年MM月dd日');
            viewData.msgCount = sysMsg.sysMsgLogs.length;
            viewData.msgType = sysMsg.idType == 1 ? '买家最新消息' : '卖家最新消息';

            var dom = Util.cloneDom("sys_msg_template", sysMsg, viewData);
            dom.find('.info-content').addClass(sysMsg.idType == 2 ? 'info-one' : 'info-two');
            $(".sysMsgList").append(dom);

            drawSysMsgLog(dom.find('.sysMsgLogs'), sysMsg.sysMsgLogs);

            dom.find('.info-title').click(sysMsg, function(event){
                var sysMsg = event.data;
                if(sysMsg.objectType == 'PRODUCT')
                    href('api/apiProductController/productDetail?id=' + sysMsg.objectId);
            });

            dom.find(".lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
        }

        function drawSysMsgLog(elm, sysMsgLogs) {

            for(var i=0; i<sysMsgLogs.length; i++) {
                var hide = i == 0 ? '' : 'hide-info', sysMsgLog = sysMsgLogs[i];
                var html = '<div class="big-text sysinfo-contnet '+hide+'" onclick="href(\''+sysMsgLog.url+'\');">'
                        + '<img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>'+sysMsgLog.title+'</span>'
                        + '<div>'+sysMsgLog.content+'</div>'
                        + '<div class="sysinfo-time">'+Util.getTime(sysMsgLog.addtime)+'</div></div>';
                elm.append(html);
            }
        }
    </script>
</body>

</html>
