<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>集物专题</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
<div data-role="page" class="jqm-demos">
    <div role="main" class="ui-content jqm-content jqm-fullwidth">
        <div style="padding:0px 10px;" class="topicList">
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
                <li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
            </ul>
        </div><!-- /navbar -->
    </div><!-- /footer -->
    <jsp:include page="../template/topic_template.jsp"></jsp:include>
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
                drawTopicList();
            }, 20);
        });

        var obj = $.cookie('topic_list');
        if(obj != null) {
            $.cookie('topic_list', null);
            obj = $.parseJSON(obj);
            scrollTop = obj.scrollTop;
            drawTopicList(obj.currPage);
        } else {
            drawTopicList();
        }
    });

    function drawTopicList(page) {
        currPage = page || currPage;
        var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows};
        ajaxPost('api/apiTopic/topicList', params, function(data){
            if(data.success) {
                var result = data.obj;
                if(result.rows.length != 0) {
                    for(var i in result.rows) {
                        var topic = result.rows[i];
                        buildTopic(topic);
                    }

                    $(".topicList .lazy").lazyload({
                        placeholder : base + 'wsale/images/lazyload.png'
                    });

                    loading = false;
                    currPage ++;
                } else {
                    if(result.total == 0)
                        $(".topicList").append(Util.noDate(1, '没有相关专题'));
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

    function buildTopic(topic) {
        var viewData = Util.cloneJson(topic);

        viewData.name_time = '发布人:' + topic.user.nickname;
        viewData.time = Util.getTime(topic.addtime);
        viewData.count = '点赞：'+topic.topicPraise+'&nbsp;&nbsp;阅读：' + topic.topicRead;

        var dom = Util.cloneDom("topic_template", topic, viewData);
        dom.find('.icon').attr("data-original", viewData.icon);
        dom.find('.icon').css('background-image','url("'+viewData.icon+'")')
        if(viewData.time.indexOf('刚刚') != -1 || viewData.time.indexOf('前') != -1) {
            dom.find("[name=time]").css('color', 'rgb(252,79,30)');
        }
        $(".topicList").append(dom);
        // dom绑定事件
        dom.click(topic.id, function(event){
            $.cookie('topic_list', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
            href('api/apiTopic/topicDetail?id=' + event.data);
        });
    }
</script>

</body>

</html>
