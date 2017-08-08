<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的粉丝</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color: #f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div style="border-bottom:10px solid #f5f5f5; background-color: #fff;">
                <div>
                    <ul class="tab-title">
                        <li class="titletab-active"style="width:20%;margin:0 10px;font-size: 15px;">粉丝</li>
                        <li style="width:20%;margin:0 10px;font-size: 15px;" onclick="replace('api/userController/shieldors');">屏蔽用户</li>
                    </ul>
                </div>
            </div>
            <div class="home-content" style="margin:0;">
                <div class="fensi-list" style="background-color: #fff;">
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

        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawUsers();
                }, 20);
            });

            drawUsers();
        });

        function drawUsers() {
            ajaxPost('api/userController/fans', {page:currPage, rows:rows}, function(data){
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
            viewData.btnName = user.attred ? '取消关注' : '+ 关注';
            viewData.bardian = viewData.bardian || '这个人很懒，什么也没说';
            var dom = Util.cloneDom("shieldorfans_template", user, viewData);
            $(".fensi-list").append(dom);
            if(user.attred) {
                dom.find('.guanzhu-btn').addClass('attred');
            }
            dom.find('.right-guanzhu').click(user.id, function(event){
                event.stopPropagation();
                var userId = event.data, url = 'api/userController/addShieldorfans', _this = $(this).find('.guanzhu-btn');
                if($(_this).hasClass('attred')) {
                    url = 'api/userController/delShieldorfans';
                    $.confirm("是否取消对该用户的关注?", "系统提示", function() {
                        attrFun(_this, url, userId);
                    }, function() {});
                } else {
                    attrFun(_this, url, userId);
                }
            });
            dom.click(user.id, function(event){
                href('api/userController/homePage?userId=' + event.data);
            });
        }

        function attrFun(elm, url, userId) {
            ajaxPost(url, {objectType:'FS', userId:userId}, function(data){
                if(data.success) {
                    if($(elm).hasClass('attred')) {
                        $(elm).removeClass('attred').html('+ 关注');
                    } else {
                        $(elm).addClass('attred').html('取消关注');
                    }
                }
            });
        }
    </script>
</body>

</html>
