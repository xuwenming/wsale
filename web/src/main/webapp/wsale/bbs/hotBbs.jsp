<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${isHomeHot}">帖子推荐</c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${empty bbsType}">热门主题</c:when>
                    <c:otherwise>热门讲堂</c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <c:if test="${!isHomeHot}">
                <div style="border-bottom:10px solid #f5f5f5;">
                    <div>
                        <ul class="tab-title">
                            <li <c:if test="${empty bbsType}">class="titletab-active"</c:if> style="width:20%;margin:0 10px;font-size: 15px;" onclick="replace('api/bbsController/hotBbs');">主题</li>
                            <li <c:if test="${!empty bbsType}">class="titletab-active"</c:if> style="width:20%;margin:0 10px;font-size: 15px;" onclick="replace('api/bbsController/hotBbs?bbsType=BT03');">讲堂</li>
                        </ul>
                    </div>
                </div>
            </c:if>

            <div style="padding:0px 10px;" class="hotBbsList"></div>

            <div class="home-content">
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    正在加载中
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
        <jsp:include page="../template/bbs_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var loading = true;
        var currPage = 1;
        var rows = 10;
        var scrollTop = 0;

        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawBbsList();
                }, 20);
            });

            var obj = $.cookie('hotBbs');
            if(obj != null) {
                $.cookie('hotBbs', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                drawBbsList(obj.currPage);
            } else {
                drawBbsList();
            }
        });

        function drawBbsList(page) {
            currPage = page || currPage;
            var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows};
            if(${isHomeHot}) {
                params.isHomeHot = true;
                params.sort = 'seq desc, t.addtime';
                params.order = 'desc';
            } else {
                params.themeType = 'TEXT';
                if('${bbsType}') {
                    params.themeType = 'AUDIO';
                }
                params.isHot = true;
            }

            params.bbsStatus = 'BS01';
            ajaxPost('api/bbsController/bbsList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var bbs = result.rows[i];
                            buildBbs(bbs);
                        }

                        loading = false;
                        currPage ++;
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

                if(scrollTop > 0) {
                    $.mobile.silentScroll(scrollTop);
                    scrollTop = 0;
                }
            });
        }

        function buildBbs(bbs) {
            var viewData = Util.cloneJson(bbs);
            var color = '', d = false;
            viewData.spIcon = '';
            if(bbs.isLight) {
                color = 'rgb(19,172,189)';
                d = true;
                viewData.spIcon += '<img src="${pageContext.request.contextPath}/wsale/images/jsp-icon2.png" style="width:20px;" />';
            }
            if(bbs.isEssence) {
                color = 'rgb(252,79,30)';
                d = true;
                viewData.spIcon += '<img src="${pageContext.request.contextPath}/wsale/images/jsp-icon3.png" style="width:20px;" />';
            }
            viewData.name_time = '发帖人:'+bbs.addUserName;
            viewData.time = Util.getTime(bbs.addtime);
            viewData.count = '回复：'+bbs.bbsComment+' &nbsp;&nbsp;围观：' + bbs.bbsRead;

            var dom = Util.cloneDom("bbs_template", bbs, viewData);
            dom.find("[name=bbsTitle]").css('color', color);
            if(d) dom.find("[name=spIcon]").show();
            if(viewData.time.indexOf('刚刚') != -1 || viewData.time.indexOf('前') != -1) {
                dom.find("[name=time]").css('color', 'rgb(252,79,30)');
            }
            $(".hotBbsList").append(dom);
            // dom绑定事件
            dom.click(bbs.id, function(event){
                $.cookie('hotBbs', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/bbsController/bbsDetail?id=' + event.data);
            });

            dom.find(".lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
        }
    </script>

</body>

</html>
