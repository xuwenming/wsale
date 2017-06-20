<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的关注</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="我的关注" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div>
                <div style="border-bottom:10px solid #f5f5f5;">
                    <!--<a href="" style="float:right;margin:10px;text-align:center;">
                        <img src="${pageContext.request.contextPath}/wsale/images/xiangji-icon.png" style="width:20px;" />
                    </a>-->
                    <div>
                        <ul class="tab-title">
                            <li class="titletab-active" style="width:20%;margin:0 10px;">关注</li>
                        </ul>
                    </div>
                </div>
                <div>
                    <div>
                        <div class="guanzhu-list">
                        </div>
                    </div>
                </div>
            </div>

            <div class="home-content">
                <div class="weui-infinite-scroll" style="display: none;">
                    <div class="infinite-preloader"></div>
                    正在加载
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
        <jsp:include page="../template/person_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var loading = true;
        var currPage = 1;
        var rows = 30;

        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawUsers();
                }, 20);
            });

            drawUsers(true);
        });

        function drawUsers(load) {
            ajaxPost('api/userController/shieldorAttrs', {page:currPage, rows:rows, objectType:'FS'}, function(data){
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
                            $(".guanzhu-list").append(Util.noDate(1, "暂无关注"));
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
            }, function(){
                if(load) $.loading.load({type:1});
            });
        }

        function buildUser(user) {
            var viewData = Util.cloneJson(user);
            var dom = Util.cloneDom("my_atted_template", user, viewData, "inline-block");
            $(".guanzhu-list").append(dom);
            dom.click(user.id, function(event){
                href('api/userController/homePage?userId=' + event.data);
            });
        }
    </script>
</body>

</html>
