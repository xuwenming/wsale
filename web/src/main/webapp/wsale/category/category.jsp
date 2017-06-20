<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>分类</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>

    <div data-role="page" data-title="分类" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;">
                <div style="width:25%; display:inline-block; vertical-align:top;">
                    <ul id="fenlei-list">
                        <c:forEach items="${categorys}" var="category" varStatus="vs">
                            <li <c:if test="${vs.index == 0}">class="fenlei-active"</c:if> categoryId="${category.id}">${category.name} </li>
                        </c:forEach>
                    </ul>
                </div>
                <div style="width:73%;margin-top:10px; text-align:left;display:inline-block; vertical-align:top; float:right;">
                    <div style="position: fixed; width: 73%;">
                        <div id="childCategory">
                            <c:forEach items="${childCategorys}" var="childCategory" varStatus="vs">
                                <a class="fenlei-imglist" style="display:inline-block;" categoryId="${childCategory.id}">
                                    <img class="lazy" data-original="${childCategory.icon}" width="72" height="72"/>
                                    <div>${childCategory.name}</div>
                                </a>
                            </c:forEach>
                        </div>
                        <div class="weui-infinite-scroll" style="display: none;">
                            <div class="infinite-preloader"></div>
                            数据加载中
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /content -->
        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find')" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->

    </div>
    <script type="text/javascript">
        var scrollTop = 0;
        $(function(){
            $( "[data-role='navbar'] ul li:eq(1) a").addClass('ui-btn-active');
            $("img.lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
            $("#fenlei-list li").bind("click", function(){
                if($(this).hasClass("fenlei-active")) return;
                $(this).addClass("fenlei-active");
                $(this).siblings().removeClass("fenlei-active");
                drawChildCategory($(this).attr("categoryId"));
            });

            $("#childCategory").on("click", "a", function(){
                $.cookie('category', JSON.stringify({scrollTop:$(window).scrollTop(), categoryId:$("#fenlei-list li.fenlei-active").attr('categoryId')}));
                href('api/apiCategoryController/forum?id=' + $(this).attr("categoryId"));
            });

            var obj = $.cookie('category');
            if(obj != null) {
                $.cookie('category', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                var categoryId = obj.categoryId;
                $("#fenlei-list li[categoryId="+categoryId+"]").click();
            }
        });

        function drawChildCategory(pid) {
            $("#childCategory").empty();
            $(".home-content .weui-infinite-scroll").show();
            ajaxPost('api/apiCategoryController/categorys', {pid : pid}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.length > 0) {
                        for(var i in result) {
                            var $a = $("<a></a>").addClass("fenlei-imglist").css({display:'inline-block'}).attr({'categoryId':result[i].id});
                            $a.append('<img class="lazy" data-original="'+result[i].icon+'" width="72" height="72"/>');
                            $a.append('<div>'+result[i].name+'</div>');
                            $("#childCategory").append($a);
                        }
                        $("img.lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        if(scrollTop > 0) {
                            $.mobile.silentScroll(scrollTop);
                            scrollTop = 0;
                        }
                    } else {
                        $("#childCategory").append(Util.noDate(1, '暂无相关分类'));
                    }

                    $(".home-content .weui-infinite-scroll").hide();
                }
            });
        }
        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"这里应有尽有，尽情挑吧！",
                desc:"集东集西--不仅珍宝立刻拍回家还可以玩转贴吧哟！",
                link:removeQueDefault(location.href),
                imgUrl:server_url + base + 'wsale/images/logo.png'
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
