<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%
    String shopIntro = Application.getString("SV500");
    shopIntro = shopIntro == null ? "甄选诚信店铺，真品保真！" : shopIntro;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>钻石店铺</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .
    </style>
</head>
<body>
    <div data-role="page" data-title="钻石店铺" class="jqm-demos" style="background-color: #f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div>
                <div>
                    <div style="background-color: #fff; padding: 10px;margin-top:5px; margin-bottom: 10px;color: #dc721c;"><%=shopIntro %></div>
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
        var rows = 20;

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
            ajaxPost('api/apiShop/starShopList', {page:currPage, rows:rows}, function(data){
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
                            $(".guanzhu-list").append(Util.noDate(1, "这里还没有内容"));
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
            var dom = Util.cloneDom("star_shop_template", user, viewData, "inline-block");
            $(".guanzhu-list").append(dom);
            dom.click(user.id, function(event){
                href('api/apiShop/shop?userId=' + event.data);
            });
        }

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"诚信店铺，真品保证！",
                desc:"集东集西--诚信店铺，真品保证！",
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
