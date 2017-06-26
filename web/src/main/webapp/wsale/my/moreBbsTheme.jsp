<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${themeType == 'TEXT'}">更多文字主题</c:when>
            <c:otherwise>更多声音主题</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <input type="hidden" id="themeType" value="${themeType}"/>
        <input type="hidden" id="userId" value="${userId}"/>
        <input type="hidden" id="bbsStatus" value="${bbsStatus}"/>
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div style="padding:0px 10px;" class="bbsThemeList">

            </div>
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
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
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

            var obj = $.cookie('moreBbsTheme');
            if(obj != null) {
                $.cookie('moreBbsTheme', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                drawBbsList(obj.currPage);
            } else {
                drawBbsList();
            }
        });

        function drawBbsList(page) {
            currPage = page || currPage;
            var params = {
                themeType : $("#themeType").val(),
                addUserId : $("#userId").val(),
                bbsStatus : $("#bbsStatus").val(),
                page : (page && 1) || currPage,
                rows : (page && page*rows) || rows
            };
            ajaxPost('api/bbsController/bbsList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var bbs = result.rows[i];
                            buildBbs(bbs);
                        }

                        $(".bbsThemeList .lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

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
            //viewData.name_time = '发帖人:'+bbs.addUserName+'&nbsp;&nbsp;发帖时间:' + bbs.addtime.substring(0, 10);
            viewData.name_time = '发帖人:'+bbs.addUserName;
            viewData.time = Util.getTime(bbs.addtime);
            viewData.count = '回复：'+bbs.bbsComment+' &nbsp;&nbsp;围观：' + bbs.bbsRead;

            var dom = Util.cloneDom("bbs_template", bbs, viewData);
            dom.find("[name=bbsTitle]").css('color', color);
            if(d) dom.find("[name=spIcon]").show();
            $(".bbsThemeList").append(dom);
            // dom绑定事件
            dom.click(bbs.id, function(event){
                $.cookie('moreBbsTheme', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/bbsController/bbsDetail?id=' + event.data);
            });
            return dom;
        }

    </script>

</body>

</html>
