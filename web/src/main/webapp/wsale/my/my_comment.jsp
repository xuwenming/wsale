<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的评论</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.my.comment.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/strophe.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/websdk-1.1.2.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/emoji.config.js?v=${staticVersion}" charset="utf-8"></script>
</head>
<body>
    <div data-role="page" data-title="我的评论" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="speak-main">
                <ul class="comments"></ul>
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
        <jsp:include page="../template/comment_template.jsp"></jsp:include>
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
                    drawList();
                }, 20);
            });

            var obj = $.cookie('myComment');
            if(obj != null) {
                $.cookie('myComment', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                drawList(obj.currPage);
            } else {
                drawList();
            }
        });

        function drawList(page) {
            currPage = page || currPage;
            var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows};
            ajaxPost('api/userController/comments', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var comment = result.rows[i];
                            build(comment);
                        }

                        $(".comments .lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".comments").append(Util.noDate(2, '这里还没有内容'));
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

        function build(comment) {
            var viewData = Util.cloneJson(comment);
            viewData.headImage = '${sessionInfo.headImage}';
            viewData.nickname = '${sessionInfo.nickname}';
            viewData.addtime = Util.getTime(comment.addtime);
            if(comment.ctype == 'IMAGE') {
                viewData.comment = '<img src="'+comment.comment+'" class="imageMsg" style="max-width:60%;" /><br>';
            } else {
                viewData.comment = WebIM.utils.parseEmoji(viewData.comment.replace(/[\r\n]/g, "<br/>"));
            }

            var dom = Util.cloneDom("my_comment_template", comment, viewData);
            $(".comments").append(dom);

            dom.find("[name=headImage]").click(function(){
                href('api/userController/homePage?userId=${sessionInfo.id}');
            });
            dom.find('.imageMsg').click(function(){
                event.stopPropagation();
                var imageUrls = [$(this).attr('src')];
                JWEIXIN.previewImage(imageUrls);
            });
            // dom绑定事件
            dom.find('.speak-li-right').click(comment.bbs, function(event){
                var bbs = event.data;
                if(bbs.bbsStatus == 'BS02' || bbs.isDeleted) {
                    $.alert("帖子已关闭或已删除！", "系统提示");
                    return;
                }
                $.cookie('myComment', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/bbsController/bbsDetail?id=' + bbs.id);
            });
        }
    </script>
</body>

</html>
