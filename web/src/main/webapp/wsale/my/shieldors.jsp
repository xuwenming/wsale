<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>屏蔽用户列表</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="mask-layer" style="z-index: 1001;"></div>
            <div class="fensi-dialog" style="z-index: 1002;bottom: 0px;">
                <div>
                    确认取消屏蔽该用户？
                </div>
                <a class="bottom-btn guanzhu-ok">确定</a>
                <a class="bottom-btn guanzhu-cancel">取消</a>
            </div>
            <div class="home-content" style="margin:0; ">
                <div class="fensi-list">

                </div>
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
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->
        <jsp:include page="../template/person_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var loading = true;
        var currPage = 1;
        var rows = 10;
        var userId;

        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawUsers();
                }, 20);
            });

            drawUsers();

            $('.guanzhu-ok').click(function(){
                if(!userId) return;
                ajaxPost('api/userController/delShieldorfans', {objectType:'SD', userId:userId}, function(data){
                    if(data.success) {
                        $('.faxian-link[userId='+userId+']').remove();
                        userId = null;
                    }
                });
            });
        });

        function drawUsers() {
            ajaxPost('api/userController/shieldorAttrs', {page:currPage, rows:rows, objectType:'SD'}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var user = result.rows[i];
                            buildUser(user);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".fensi-list").append(Util.noDate(1));
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

        function buildUser(user) {
            var viewData = Util.cloneJson(user);
            viewData.btnName = '取消屏蔽';
            viewData.bardian = viewData.bardian || '这个人很懒，什么也没说';
            var dom = Util.cloneDom("shieldorfans_template", user, viewData);
            $(".fensi-list").append(dom);
            dom.attr('userId', user.id);
            dom.find('.right-guanzhu').click(user.id, function(event){
                event.stopPropagation();
                userId = event.data;
                $(".mask-layer,.fensi-dialog").show();
            });
            dom.click(user.id, function(event){
                href('api/userController/homePage?userId=' + event.data);
            });
        }
    </script>
</body>

</html>
